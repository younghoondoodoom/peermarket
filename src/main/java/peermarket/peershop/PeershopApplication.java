package peermarket.peershop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class PeershopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeershopApplication.class, args);
	}

}
