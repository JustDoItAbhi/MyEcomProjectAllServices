package orderservice.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseModels {// COMMON BASEMODEL
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
private long id;// AUTO-INCREMENTED ID , AS PRIMARY KEY
    @CurrentTimestamp
private LocalDateTime createdAt;// TIME STAMP FOR ORDER CREATED AT

}
