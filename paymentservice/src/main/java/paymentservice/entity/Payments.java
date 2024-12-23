package paymentservice.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Payments extends BaseModels{
private double amount;
private String userId;
private String transectionalId;
private  Long orderID;
private long productId;
}
