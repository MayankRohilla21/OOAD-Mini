package billing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import billing.model.Bill;
import billing.service.BillingService;
import billing.service.PaymentService;

@Controller
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String viewBills(Model model) {
        model.addAttribute("bills", billingService.getAllBills());
        return "bills";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("bill", new Bill());
        return "bill-form";
    }

    @PostMapping("/save")
    public String saveBill(@ModelAttribute Bill bill) {
        billingService.generateBill(
                bill.getConsultationId(),
                bill.getConsultationFee(),
                bill.getMedicineCharges(),
                bill.getLabCharges());
        return "redirect:/billing";
    }

    @PostMapping("/pay")
    public String pay(@RequestParam Long billId,
            @RequestParam double amount,
            @RequestParam String method) {

        paymentService.makePayment(billId, amount, method);
        return "payment-success";
    }

    @GetMapping("/payments")
    public String viewPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "payments";
    }

    @GetMapping("/from-consultation/{id}")
    public String handleConsultationBill(@PathVariable Long id, Model model) {

        // Check if bill already exists
        Bill existingBill = billingService.findByConsultationId(id);

        if (existingBill != null) {
            // If already exists → go to dashboard
            return "redirect:/billing";
        }

        // If not → open bill form with prefilled consultationId
        Bill bill = new Bill();
        bill.setConsultationId(id);

        model.addAttribute("bill", bill);

        return "bill-form";
    }
}