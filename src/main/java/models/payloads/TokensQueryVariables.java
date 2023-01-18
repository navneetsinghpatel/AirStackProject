package models.payloads;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class TokensQueryVariables {
    Input input;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Input {
        Filter filter;
        Integer limit;
        String cursor;
        public Input(Filter filter, int limit) {
            this.filter = filter;
            this.limit = limit;
        }
        public Input(Filter filter, String cursor) {
            this.filter = filter;
            this.cursor = cursor;
        }
        public Input(Filter filter) {
            this.filter = filter;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Filter {
            String chainId;
            String address;
            public Filter(String chainId) {
                this.chainId = chainId;
            }
        }

    }
}
