package web.configures;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import web.security.Jwt;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class ServiceConfigure {

    @Bean
    public Jwt jwt(JwtTokenConfigure jwtTokenConfigure) {
        return new Jwt(jwtTokenConfigure.getIssuer(), jwtTokenConfigure.getClientSecret(), jwtTokenConfigure.getExpirySeconds());
    }

    @Bean
    public DataSource dataSource(DataSourceProperties properties) {
        DataSourceBuilder<?> factory = DataSourceBuilder
                .create()
                .driverClassName(properties.getDriverClassName())
                .url(properties.getUrl())
                .username(properties.getUsername())
                .password(properties.getPassword());
        HikariDataSource dataSource = (HikariDataSource) factory.build();
        dataSource.setPoolName("H2_DB");
        dataSource.setMinimumIdle(1);
        dataSource.setMaximumPoolSize(5);
        return new Log4jdbcProxyDataSource(dataSource);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            ParameterNamesModule pnm = new ParameterNamesModule(JsonCreator.Mode.PROPERTIES);
            JavaTimeModule jtm = new JavaTimeModule();
            jtm.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            jtm.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            jtm.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            jtm.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            builder.visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
            builder.visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE);
            builder.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

            builder.serializationInclusion(JsonInclude.Include.ALWAYS);
            builder.modulesToInstall(pnm, jtm);
        };
    }

}