package paymentservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;
    @CreationTimestamp
private LocalDateTime createdAt;
    @UpdateTimestamp
private LocalDateTime updatedAt;
}