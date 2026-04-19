package appointment.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import appointment.model.Appointment;
import appointment.model.DoctorSchedule;

public interface AppointmentService {
    List<Appointment> getAllAppointments();

    Appointment bookAppointment(String patientName, String doctorName, LocalDate date, LocalTime slotTime, String reason);

    Appointment rescheduleAppointment(Long appointmentId, LocalDate newDate, LocalTime newSlotTime);

    void cancelAppointment(Long appointmentId);

    List<LocalTime> getBookedSlots(String doctorName, LocalDate date);

    List<DoctorSchedule> getAllSchedules();

    DoctorSchedule addDoctorAvailability(String doctorName, LocalDate date, LocalTime startTime, LocalTime endTime);

    void markDoctorUnavailable(Long scheduleId);
}
