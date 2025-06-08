package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;

@Getter
@Setter
public class TimezoneResponseDTO {
    private Long id;
    private String zoneName;
    private Integer gmtOffset;
    private String gmtOffsetName;
    private String abbreviation;
    private String tzName;
    private Long countryId;
    private String countryName;
}
