package Json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class JsonUtils {

    private static final ObjectMapper om = new ObjectMapper();

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return om.readValue(json, type);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return om.writeValueAsString(obj);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

    static final TypeReference<Map<String, Object>> typeOfMap = new TypeReference<Map<String, Object>>() {};

    public static Map<String, Object> toMap(String json) {
        try {
            return om.readValue(json, typeOfMap);
        } catch (Exception e) {
            throw new JsonException(e);
        }
    }

}