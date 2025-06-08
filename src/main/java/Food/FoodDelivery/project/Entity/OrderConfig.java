package Food.FoodDelivery.project.Entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "order_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfig {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tax_rate", nullable = false)
    private double taxRate;

    @Column(name = "default_discount", nullable = false)
    private double defaultDiscount;

    @Column(name = "is_active" , nullable = false)
    private Boolean isActive = true;

    @Column(name = "delivery_charges" , nullable = false)
    private BigDecimal deliveryCharges ;
}
