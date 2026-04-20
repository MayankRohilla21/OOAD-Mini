package billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import billing.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Bill findByConsultationId(Long consultationId);

    @org.springframework.data.jpa.repository.Query("SELECT COALESCE(SUM(b.totalAmount), 0) FROM Bill b")
    double sumTotalRevenue();
}