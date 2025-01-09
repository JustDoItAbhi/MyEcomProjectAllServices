package paymentservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"paymentservice", "paymentservice/clinets"})
public class PaymentserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PaymentserviceApplication.class, args);
	}
}
