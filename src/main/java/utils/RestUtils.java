package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.payloads.Query;;import static commons.BaseTest.log;

public class RestUtils {
    static final String baseURI = "https://devapi.airstack.xyz/gql";
    public static Response Query(String queryString, Object variables){
        Query query;
        log.info("Creating Query Object by checking availability of queryString and Variables Object");
        if(variables == null && queryString ==  null){
            //As a negative test we are not passing  query string and variable, so we create empty query object
            query = new Query();
            log.info("Calling Query without any Query String and Variables");
        } else if (variables == null) {
            //As a negative test we are not passing variables, so we are only using the query string.
            // This will be used when we don't need query variables as well
            query = new Query(queryString);
            log.info("Calling Query without any Query Variables");
        }else {
            //This is used when both query and query variables are provided
            query = new Query(queryString, variables);
            log.info("Calling Query with Query String and Variables");
        }
        log.info("Returning Response of Query: " + query);
        return RestAssured.given().log().all().baseUri(baseURI).contentType(ContentType.JSON).body(query).post()/*.then().log().all().extract().response()*/;
    }

}
