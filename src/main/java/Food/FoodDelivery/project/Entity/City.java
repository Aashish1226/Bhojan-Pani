package Food.FoodDelivery.project.Entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "cities")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class City {

    @Id
    private Long id;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date", nullable = false)
    @LastModifiedDate
    private LocalDateTime updateDate;

    @Column(name = "name" , nullable = false)
    private String name;
    private String latitude;
    private String longitude;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;



}
