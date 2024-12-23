//package cartservice.client;
//
//import cartservice.userdtos.UserResponseDto;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class UserCallings {
//    private final RestTemplateBuilder restTemplateBuilder;
//
//    public UserCallings(RestTemplateBuilder restTemplateBuilder) {
//        this.restTemplateBuilder = restTemplateBuilder;
//    }
//
//    public List<UserResponseDto> getAllUser(){
//        RestTemplate restTemplate=restTemplateBuilder.build();
//        String url="http://localhost:8090/user/";
//        ResponseEntity<UserResponseDto[]> template=restTemplate.getForEntity(url,UserResponseDto[].class);
//        if (template.getBody() != null) {
//            return Arrays.asList(template.getBody());
//        }
//        return new ArrayList<>();
//    }
//}
