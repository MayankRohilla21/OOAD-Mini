package billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import billing.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findByConsultationId(Long consultationId);

    @Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b WHERE b.status = 'PAID'")
    Double sumPaidAmount();
}