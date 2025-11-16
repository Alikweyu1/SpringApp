package apharm.co.ke.fskcb.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LookupRequest {

    private String staffNo;
    private String phoneNo;
}
