package consultation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import consultation.model.Consultation;
import consultation.model.Prescription;
import consultation.repository.PrescriptionRepository;
import consultation.service.ConsultationService;

@Controller
public class ConsultationController {

    private final ConsultationService consultationService;
    private final PrescriptionRepository prescriptionRepository;

    public ConsultationController(
        ConsultationService consultationService,
        PrescriptionRepository prescriptionRepository) {

    this.consultationService = consultationService;
    this.prescriptionRepository = prescriptionRepository;
}

    @GetMapping("/consultations")
    public String viewConsultations(Model model) {
        model.addAttribute("consultations",
                consultationService.getAllConsultations());
        return "consultations";
    }
    @GetMapping("/consultations/new")
    public String showForm(Model model) {
        model.addAttribute("consultation", new Consultation());
        return "consultation-form";
}
    @PostMapping("/consultations/save")
    public String saveConsultation(@ModelAttribute Consultation consultation) {
        consultationService.saveConsultation(consultation);
    return "redirect:/consultations";
}
    @GetMapping("/consultations/delete/{id}")
    public String deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
    return "redirect:/consultations";
    }
    @GetMapping("/consultations/edit/{id}")
    public String editConsultation(@PathVariable Long id, Model model) {
        model.addAttribute("consultation",
            consultationService.getConsultationById(id));
    return "consultation-form";
    }
    @GetMapping("/consultations/complete/{id}")
    public String completeConsultation(@PathVariable Long id) {
    Consultation consultation =
            consultationService.getConsultationById(id);

    if (consultation != null) {
        consultation.setStatus("Completed");
        consultationService.saveConsultation(consultation);
    }

    return "redirect:/consultations";
    }
    @PostMapping("/prescriptions/save")
    public String savePrescription(@ModelAttribute Prescription prescription) {
    prescriptionRepository.save(prescription);
    return "redirect:/prescriptions";
    }
    @GetMapping("/prescriptions/new")
    public String showPrescriptionForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("consultations",consultationService.getAllConsultations());
    return "prescription-form";
    }
    @GetMapping("/prescriptions")
    public String viewPrescriptions(Model model) {
        model.addAttribute("prescriptions",prescriptionRepository.findAll());
    return "prescriptions";
    }
    @GetMapping("/prescriptions/search")
    public String searchPrescription(
        @RequestParam String patientName,
        Model model) {

    model.addAttribute("prescriptions",
            prescriptionRepository.findAll()
            .stream()
            .filter(p -> p.getPatientName()
            .equalsIgnoreCase(patientName))
            .toList());

    return "prescriptions";
    }
    @GetMapping("/prescriptions/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionRepository.deleteById(id);
    return "redirect:/prescriptions";
    }
    @GetMapping("/prescriptions/edit/{id}")
    public String editPrescription(@PathVariable Long id, Model model) {
        model.addAttribute("prescription", prescriptionRepository.findById(id).orElse(null));
        model.addAttribute("consultations", consultationService.getAllConsultations());
        return "prescription-form";
    }
}