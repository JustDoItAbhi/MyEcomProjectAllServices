package orderservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class UserDto {
    private long userId;
    private String userName;
    private String userPhone;
    private String userEmail;
    private String userCity;
    private String userState;
    private String userCountry;
    private int userPostelCode;
    private String userHouseNumber;
    private String userStreet;
    private String userLandMark;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private long totalAmount;
}
