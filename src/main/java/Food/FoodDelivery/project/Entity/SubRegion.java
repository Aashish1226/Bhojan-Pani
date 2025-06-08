package Food.FoodDelivery.project.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "sub_regions")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class SubRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    @Column(name = "name" , nullable = false , unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @Column(name = "wiki_data_id" , nullable = false , unique = true)
    private String wikiDataId;

}
