package deliveryservice.deliveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ComponentScan
public class DeliveryserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryserviceApplication.class, args);
	}
	@Bean
	public RestTemplateBuilder restTemplate(){
		return new RestTemplateBuilder();
	}
	@Bean
	public RestTemplate restTemplates() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add((request, body, execution) -> {
			// Add your custom User-Agent header to the request
			request.getHeaders().add("User-Agent", "DeliveryserviceForUniveristyProject/1.0");
			return execution.execute(request, body);  // Proceed with the request
		});
		return restTemplate;
	}

}
