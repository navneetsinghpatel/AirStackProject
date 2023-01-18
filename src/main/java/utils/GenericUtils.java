package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import models.payloads.VariableData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static commons.BaseTest.log;

public class GenericUtils {
    public static HashMap getQueryStringsInAMap(String queryStringFilePath){
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(queryStringFilePath));
            log.info("Got queries.json into byte array.");
            log.info("Converting byte array into HashMap Class using Object Mapper.");
            return new ObjectMapper().readValue(jsonData, HashMap.class);
        } catch (IOException e) {
            log.error("Failed to load queries.json into a map: ", e);
            throw new RuntimeException(e);
        }
    }

    public static<T> T deserializeJsonStringToPojo(String jsonString, Class<T> clazz){
        log.info("Converting Json String into Pojo Class " + clazz.getSimpleName()+ " using Object mapper.");
        try {
            return new ObjectMapper().readValue(jsonString, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to convert Json String into Pojo Class " + clazz.getSimpleName());
            throw new RuntimeException(e);
        }
    }

    public static String serializePojoToJsonString(Object object){
        log.info("Converting POJO of class " + object.getClass().getSimpleName() + " into json string using Object mapper.");
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed convert POJO of class " + object.getClass().getSimpleName() + " into json string");
            throw new RuntimeException(e);
        }
    }

    public static boolean isEthereumAddressValid(String address){
        log.info("Verifying if the given address follow the format of Ethereum Address or not.");
        return address.matches("^0x[0-9a-f]{40}$");
    }

    public static HashMap<String, VariableData> getCSVDataIntoMap(String filePath){
        log.info("Trying to load CSV Data into a Map of Scenarios and VariableData Class Object using Jackson data format library.");
        try {
            MappingIterator<VariableData> iterator = new CsvMapper()
                    .readerWithTypedSchemaFor(VariableData.class).readValues(new File(filePath));
            List<VariableData> list = iterator.readAll();
            log.info("Stored all the csv rows into VariableData class objects which are in turn stored in a List");
            HashMap<String, VariableData> scenarioDataMap = new HashMap<>();
            log.info("Iterating through the List to fetch Scenario Name and using that as key to the map while storing Object as Value");
            for (VariableData variableData : list) {
                scenarioDataMap.put(variableData.getScenario(), variableData);
            }
            log.info("Returning the Datamap object of Hashmap");
            return scenarioDataMap;
        } catch (IOException e) {
            log.info("Failed to get CSV Data into a Map of Scenarios and VariableData Class Object");
            throw new RuntimeException(e);
        }


    }
}
