package deliveryservice.deliveryservice.servicesproject.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseModels {// ABSTRACT BASEMODEL
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private long id;// AUTO-INCREMENTED PRIMARY ID
@CreationTimestamp
private LocalDateTime createdAt;// CREATED AT TIME STAMP
@UpdateTimestamp
private LocalDateTime updatedAt;// UPDATED AT TIME STAMP
    // GETTERS AND SETTERS
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
