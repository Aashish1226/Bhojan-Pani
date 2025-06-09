package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CommonResponse {

    private final String status;
    private final String message;
    private final String errorCode;

    public CommonResponse(String status, String message, String errorCode) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static CommonResponse success(String message) {
        return new CommonResponse("success", message, null);
    }

    public static CommonResponse error(String message, String errorCode) {
        return new CommonResponse("error", message, errorCode);
    }
}
