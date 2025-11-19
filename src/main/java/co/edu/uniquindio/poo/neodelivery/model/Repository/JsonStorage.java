package co.edu.uniquindio.poo.neodelivery.model.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;

public class JsonStorage {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public static <T> void save(String filePath, T object) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T load(String filePath, Class<T> type) {
        try {
            File file = new File(filePath);
            if (!file.exists()) return null;
            return mapper.readValue(file, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
