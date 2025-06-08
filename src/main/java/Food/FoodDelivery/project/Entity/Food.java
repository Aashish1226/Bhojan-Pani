package Food.FoodDelivery.project.Entity;

import Food.FoodDelivery.project.Enum.FoodType;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "foods", indexes = {
        @Index(name = "idx_food_category_active", columnList = "category_id, is_active"),
        @Index(name = "idx_food_type_available", columnList = "food_type, is_available"),
        @Index(name = "idx_food_price_range", columnList = "price"),
        @Index(name = "idx_food_name_search", columnList = "name"),
        @Index(name = "idx_food_composite", columnList = "is_active, category_id, food_type, is_available, price"),
        @Index(name = "idx_food_order_count", columnList = "total_order_count")
})
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000, nullable = false)
    private String description;

    // Used only when hasVariants = false
    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private String imageUrl;

    @NotNull
    @Column(nullable = false)
    private Boolean isAvailable = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FoodType foodType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(name = "has_variants", nullable = false)
    private Boolean hasVariants = false;

    @Column(nullable = false)
    private Integer totalOrderCount = 0;

    @Column(name = "average_prep_time_in_minutes", nullable = false)
    private Integer averagePrepTimeInMinutes = 0;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodVariant> variants = new ArrayList<>();

}
