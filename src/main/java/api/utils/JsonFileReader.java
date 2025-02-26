package api.utils;

import api.base.RequestBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileReader {
    public static final Logger logger = LogManager.getLogger(JsonFileReader.class);

    public static String readJson(String filePath)  {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            logger.error("Error in reading Json file: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
