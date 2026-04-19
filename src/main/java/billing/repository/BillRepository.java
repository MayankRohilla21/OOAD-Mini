package billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import billing.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findByConsultationId(Long consultationId);
}