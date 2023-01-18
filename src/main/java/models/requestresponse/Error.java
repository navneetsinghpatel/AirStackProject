package models.requestresponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@lombok.Data
public class Error {
    public String message;
    public ArrayList<String> path;
    public Extensions extensions;

    @lombok.Data
    @NoArgsConstructor
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Extensions {
        @JsonProperty("code")
        String code;
        @JsonProperty("INTERNAL_ERROR_CODE")
        String iNTERNAL_ERROR_CODE;
        @JsonProperty("Reference")
        String reference;

    }
}
