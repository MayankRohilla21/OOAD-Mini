package billing.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import billing.model.Bill;
import billing.model.Payment;
import billing.repository.BillRepository;
import billing.repository.PaymentRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BillRepository billRepository;

    public Payment makePayment(Long billId, double amount, String method) {

        Payment payment = new Payment();
        payment.setBillId(billId);
        payment.setAmount(amount);
        payment.setPaymentMethod(method);
        payment.setStatus("PAID");

        // Update bill status
        Bill bill = billRepository.findById(billId).orElseThrow();
        bill.setStatus("PAID");
        billRepository.save(bill);

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}