package orderservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class BaseModels {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
private long id;
    @CurrentTimestamp
private LocalDateTime createdAt;
@UpdateTimestamp
    private LocalDateTime updatedAt;
}