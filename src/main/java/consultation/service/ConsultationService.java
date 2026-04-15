package consultation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import consultation.model.Consultation;
import consultation.repository.ConsultationRepository;

@Service
public class ConsultationService {

    private final ConsultationRepository consultationRepository;

    public ConsultationService(ConsultationRepository consultationRepository) {
        this.consultationRepository = consultationRepository;
    }

    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    public List<Consultation> getAllConsultations() {
        return consultationRepository.findAll();
    }
    public void deleteConsultation(Long id) {
    consultationRepository.deleteById(id);
    }
    public Consultation getConsultationById(Long id) {
        return consultationRepository.findById(id).orElse(null);
    }
}