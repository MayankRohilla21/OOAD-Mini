package consultation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import consultation.model.Prescription;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
}