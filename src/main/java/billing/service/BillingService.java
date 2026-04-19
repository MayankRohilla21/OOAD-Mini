package billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import billing.model.Bill;
import billing.repository.BillRepository;

@Service
public class BillingService {

    @Autowired
    private BillRepository billRepository;

    public Bill generateBill(Long consultationId, double consultationFee,
            double medicineCharges, double labCharges) {

        Bill bill = new Bill();

        bill.setConsultationId(consultationId);
        bill.setConsultationFee(consultationFee);
        bill.setMedicineCharges(medicineCharges);
        bill.setLabCharges(labCharges);

        double total = consultationFee + medicineCharges + labCharges;
        bill.setTotalAmount(total);

        bill.setStatus("PENDING");

        return billRepository.save(bill);
    }

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill findByConsultationId(Long consultationId) {
        return billRepository.findByConsultationId(consultationId);
    }

    public boolean isBillPaid(Long consultationId) {
        Bill bill = billRepository.findByConsultationId(consultationId);
        return bill != null && "PAID".equals(bill.getStatus());
    }
}