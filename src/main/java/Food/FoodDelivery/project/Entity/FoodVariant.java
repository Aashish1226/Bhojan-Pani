package Food.FoodDelivery.project.Entity;

import Food.FoodDelivery.project.Enum.*;
import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "food_variants")
@EntityListeners(AuditingEntityListener.class)
public class FoodVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    private String label;

    private String quantityInfo;

    @Column(nullable = false)
    private Double price;

    @Enumerated(EnumType.STRING)
    private ServingSize servingSize;

    private Boolean isAvailable = true;

    @Enumerated(EnumType.STRING)
    private ServingUnit servingUnit;

    private Boolean isActive = true;

    @Column(name = "serving_quantity")
    private Integer servingQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
