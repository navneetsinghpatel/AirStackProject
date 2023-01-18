package dataproviders;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.requestresponse.RequestResponse;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static commons.BaseTest.log;

public class TestDataProviders {

    private static final String InputFieldValidationFilePath = "src/test/resources/InvalidVariableInputRequestsAndResponses.json";
    public static final String[] inputFieldValidationFilePaths = {
            InputFieldValidationFilePath
    };

    @DataProvider(name="inputFieldNegativeTests", parallel = false)
    public static Object[] getNegativeTestDataForVariableInputs() {
        log.info("Getting Data from InvalidVariableInputRequestsAndResponses.json file for negative tests");
        return readAQueryAndResult();
    }

    private static Object[] readAQueryAndResult() {
        log.info("Reading JSON file data and converting in into RequestResponse Class Object Array to be used in Data provider");
        return Arrays.stream(inputFieldValidationFilePaths)
                .map(filePath -> readFileContent(filePath, RequestResponse[].class))
                .map(Arrays::asList)
                .flatMap(List::stream)
                .collect(Collectors.toList())
                .toArray(Object[]::new);
    }

    public static<T> T readFileContent(String filePath, Class<T> clazz){
        try {
            log.info("Reading Data from JSON File and converting it into POJO of Class "+ clazz.getSimpleName());
            return new ObjectMapper().readValue(Files.readAllBytes(Paths.get(filePath)), clazz);
        } catch (IOException e) {
            log.error("Failed to convert json file data into POJO of Class " + clazz.getSimpleName());
            throw new RuntimeException(e);
        }

    }
}
