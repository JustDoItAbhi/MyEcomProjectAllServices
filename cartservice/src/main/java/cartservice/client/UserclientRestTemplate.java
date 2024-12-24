package cartservice.client;

import cartservice.repository.UserDetailsReposirtoy;
import cartservice.userdtos.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserclientRestTemplate {
    @Autowired
    private UserDetailsReposirtoy reposirtoy;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;


    public List<UserResponseDto> getAllUser(){
        RestTemplate restTemplate=restTemplateBuilder.build();
        String url="http://localhost:8090/user/";
        ResponseEntity<UserResponseDto[]> template=restTemplate.getForEntity(url,UserResponseDto[].class);
        if (template.getBody() != null) {
            return Arrays.asList(template.getBody());
        }
        return new ArrayList<>();
    }
    public UserResponseDto getUserById(String email){
        RestTemplate restTemplate=restTemplateBuilder.build();
        String url="http://localhost:8090/user/getUserByid/";
        ResponseEntity<UserResponseDto> response=restTemplate.getForEntity(url,UserResponseDto.class);
        if(response==null){
            throw new RuntimeException("USER NOT FETCEHED "+email);
        }
        return response.getBody();
    }

    private String getJwtToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken) {
            JwtAuthenticationToken jwtAuth = (JwtAuthenticationToken) authentication;
            return jwtAuth.getToken().getTokenValue();
        }
        throw new IllegalStateException("JWT token is not available");
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*");
        System.out.println("ENTERED BY CART SERVICE");
    }
}
