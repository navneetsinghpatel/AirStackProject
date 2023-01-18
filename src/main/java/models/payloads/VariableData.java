package models.payloads;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "scenario", "chainID", "address", "limit", "cursor"})
public class VariableData {
    String scenario;
    String chainID;
    String address;
    int limit;
    String cursor;

}
