package consultation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "appointment", "consultation", "billing" })
public class ConsultationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultationApplication.class, args);
	}

}
