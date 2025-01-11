package practicekafka.practicekafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class PracticekafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticekafkaApplication.class, args);
	}

}
