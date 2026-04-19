package billing.config;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "consultation.repository",
        "billing.repository"
})
@EntityScan(basePackages = {
        "consultation.model",
        "billing.model"
})
public class BillingConfig {
}