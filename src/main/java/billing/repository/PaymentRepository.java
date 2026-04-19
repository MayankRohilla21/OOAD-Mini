package billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import billing.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}