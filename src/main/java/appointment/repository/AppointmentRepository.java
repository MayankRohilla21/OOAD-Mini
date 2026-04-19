package appointment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import appointment.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByDoctorNameAndAppointmentDateAndStatusNot(String doctorName, LocalDate appointmentDate, String status);
}
