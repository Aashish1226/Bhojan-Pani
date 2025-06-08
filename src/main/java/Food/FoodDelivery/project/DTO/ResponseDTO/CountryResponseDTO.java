package Food.FoodDelivery.project.DTO.ResponseDTO;

import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountryResponseDTO {
    private Long id;
    private String name;
    private String iso2;
    private String iso3;
    private String phoneCode;
    private String capital;
    private String currency;
    private String currencySymbol;
    private String tld;
    private String nativeName;
    private String flag;
    private String emojiU;
    private String latitude;
    private String longitude;
    private Integer regionId;
    private Integer subRegionId;
}
