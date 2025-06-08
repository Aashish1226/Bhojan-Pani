package Food.FoodDelivery.project.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "countries")
@EntityListeners(AuditingEntityListener.class)
public class Country {
    @Id
    private Long id;
    @Column(name = "name" , nullable = false)
    private String name;
    @Column(name = "iso2" , nullable = false)
    private String iso2;
    @Column(name = "iso3" , nullable = false)
    private String iso3;
    @JsonProperty("phone_code")
    @Column(name = "phone_code")
    private String phoneCode;
    private String capital;
    private String currency;
    @JsonProperty("currency_symbol")
    @Column(name = "currency_symbol")
    private String currencySymbol;
    private String tld;
    @Column(name = "native")
    @JsonProperty("native")
    private String nativeName;
    private String emoji;
    private String emojiU;
    private String latitude;
    private String longitude;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<State> states = new ArrayList<>();

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timezone> timezones = new ArrayList<>();

    @JsonProperty("region_id")
    private Long regionId;

    @JsonProperty("subregion_id")
    @Column(name = "subregion_id")
    private Integer subRegionId;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

}
