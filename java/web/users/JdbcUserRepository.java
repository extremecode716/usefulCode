package web.users;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcUserRepository implements com.github.prgrms.users.UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void update(com.github.prgrms.users.User user) {
        jdbcTemplate.update(
                "UPDATE web.users SET passwd=?,login_count=?,last_login_at=? WHERE seq=?",
                user.getPassword(),
                user.getLoginCount(),
                user.getLastLoginAt().orElse(null),
                user.getSeq()
        );
    }

    @Override
    public Optional<com.github.prgrms.users.User> findById(long id) {
        List<com.github.prgrms.users.User> results = jdbcTemplate.query(
                "SELECT * FROM web.users WHERE seq=?",
                mapper,
                id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Optional<com.github.prgrms.users.User> findByEmail(com.github.prgrms.users.Email email) {
        List<com.github.prgrms.users.User> results = jdbcTemplate.query(
                "SELECT * FROM web.users WHERE email=?",
                mapper,
                email.getAddress()
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    static RowMapper<com.github.prgrms.users.User> mapper = (rs, rowNum) ->
            new com.github.prgrms.users.User.Builder()
                    .seq(rs.getLong("seq"))
                    .name(rs.getString("name"))
                    .email(new com.github.prgrms.users.Email(rs.getString("email")))
                    .password(rs.getString("passwd"))
                    .loginCount(rs.getInt("login_count"))
                    .lastLoginAt(dateTimeOf(rs.getTimestamp("last_login_at")))
                    .createAt(dateTimeOf(rs.getTimestamp("create_at")))
                    .build();

}