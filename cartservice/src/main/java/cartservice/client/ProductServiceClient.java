package cartservice.client;

import cartservice.client.dto.ProductResponseDto;
import cartservice.expcetions.expectionsfiles.ProductNotFoundException;
import org.springframework.boot.web.client.RestTemplateBuilder;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ProductServiceClient {// class to call product service

  private final RestTemplateBuilder restTemplateBuilder;// REST TEMPLATE FIELD DELARATION FOR PRODUCT SERVCIE
    private final DiscoveryClient discoveryClient;// DECLARATION FOR EUREKA SERVER

    public ProductServiceClient(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {// DEPENDENCY INJECTION FOR REST TEMPLATE AND EUREKA SERVER
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }
    public ProductResponseDto fetchProductById(long id) {// METHOD TO CALL PRODUCT SERVICE
        RestTemplate restTemplate=restTemplateBuilder.build();

        ServiceInstance serviceInstance = discoveryClient.getInstances("productservice").get(0);
        String serviceAUri = serviceInstance.getUri() + "/product/get/"+id;
//        String serviceAUri = "http://localhost:8089/product/get/"+id;
        ResponseEntity<ProductResponseDto> response=restTemplate.getForEntity(serviceAUri, ProductResponseDto.class);
//        ResponseEntity<ProductResponseDto> response=restTemplate.getForEntity(ProductResponseDto.class);
        if(response.getBody()==null){
            throw new ProductNotFoundException("PRODUCT NOT FOUND "+ id);// VALIDATION FOR PRODUCT
        }
        response.getBody();
        return response.getBody();
    }

//    public ProductResponseDto fetchProductById(long id) {// METHOD TO CALL PRODUCT SERVICE
//        RestTemplate restTemplate=restTemplateBuilder.build();
//        Jwt jwt=(Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String token= jwt.getTokenValue();
//        HttpHeaders headers=new HttpHeaders();
//        headers.setBearerAuth(token);
//        HttpEntity<?>entity=new HttpEntity<>(headers);
//        ServiceInstance serviceInstance = discoveryClient.getInstances("productservice").get(0);
//        String serviceAUri = serviceInstance.getUri() + "/product/get/"+id;
////        String serviceAUri = "http://localhost:8089/product/get/"+id;
////        ResponseEntity<ProductResponseDto> response=restTemplate.getForEntity(serviceAUri, ProductResponseDto.class);
//        ResponseEntity<ProductResponseDto> response=restTemplate.exchange(serviceAUri, HttpMethod.GET,entity, ProductResponseDto.class);
//        if(response.getBody()==null){
//            throw new ProductNotFoundException("PRODUCT NOT FOUND "+ id);// VALIDATION FOR PRODUCT
//        }
//        response.getBody();
//        return response.getBody();
//    }
}
