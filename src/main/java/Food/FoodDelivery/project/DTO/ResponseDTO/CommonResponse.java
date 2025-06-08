package Food.FoodDelivery.project.DTO.ResponseDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse {

    private String status;
    private String message;

    public CommonResponse(String status , String message){
        this.status = status;
        this.message = message;
    }

    public static CommonResponse success(String message){return new CommonResponse("Success" , message);}
    public static CommonResponse error(String message) {
        return new CommonResponse("error", message);
    }
    public String toJson(){
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            return "{\"message\": \"Error converting to JSON\"}";
        }
    }
}
