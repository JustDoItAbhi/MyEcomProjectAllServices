package cartservice.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.annotation.EnableCaching;

import java.util.List;

@Getter
@Setter
@Entity
public class UserDetails extends BaseModels {
    private long userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userPassword;
    private List<String> rolesList;
    private String userCity;
    private String userState;
    private String userCountry;
    private int userPostelCode;
    private String userHouseNumber;
    private String userStreet;
    private String userLandMark;

}
