package billing.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "appointment.repository",
        "consultation.repository",
        "billing.repository",
        "inventory.repository"
})
@EntityScan(basePackages = {
        "appointment.model",
        "consultation.model",
        "billing.model",
        "inventory.model"
})
public class BillingConfig {
}