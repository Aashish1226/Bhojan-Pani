package Food.FoodDelivery.project.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "timezones")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Timezone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    private String zoneName;
    private Integer gmtOffset;
    private String gmtOffsetName;
    private String abbreviation;
    private String tzName;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
