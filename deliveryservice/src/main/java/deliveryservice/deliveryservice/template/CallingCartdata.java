package deliveryservice.deliveryservice.template;

import deliveryservice.deliveryservice.dto.CartResposneDtos;
import deliveryservice.deliveryservice.dto.DeliveryServiceNotification;
import jakarta.persistence.Column;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
@Component
public class CallingCartdata {
    private RestTemplateBuilder restTemplateBuilder;
    private DiscoveryClient discoveryClient;

    public CallingCartdata(RestTemplateBuilder restTemplateBuilder, DiscoveryClient discoveryClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.discoveryClient = discoveryClient;
    }

    public CartResposneDtos fetchingFromCartServcie(long cartId){
        RestTemplate restTemplate=restTemplateBuilder.build();
        ServiceInstance serviceInstance=discoveryClient.getInstances("cartservice").get(0);
        String url=serviceInstance.getUri()+"/cart/getCartById/"+cartId;
        ResponseEntity<CartResposneDtos>response=restTemplate.getForEntity(url, CartResposneDtos.class);
        if(response.getBody()==null){
            throw new RuntimeException("CART NOT FETCHIED ");
        }
        return response.getBody();
    }
//    public ResponseEntity<List<DeliveryServiceNotification>>getAll(){
//        RestTemplate restTemplate=restTemplateBuilder.build();
//        ServiceInstance serviceInstance=discoveryClient.getInstances("paymentservice").get(0);
//        String url=serviceInstance.getUri()+"/delevery/notifyPaymentDetails";
//        ResponseEntity<DeliveryServiceNotification>response=restTemplate.getForEntity(url, DeliveryServiceNotification.class);
//        if(response.getBody()==null){
//            throw new RuntimeException("PAYMENT NOT FETCHIED ");
//        }
//        return response.getBody();
//    }
//    }
}
