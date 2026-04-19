package appointment.factory;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Component;

import appointment.model.Appointment;

@Component
public class AppointmentFactory {

    public Appointment createBooked(String patientName, String doctorName, LocalDate date, LocalTime slotTime, String reason) {
        Appointment appointment = new Appointment();
        appointment.setPatientName(patientName);
        appointment.setDoctorName(doctorName);
        appointment.setAppointmentDate(date);
        appointment.setSlotTime(slotTime);
        appointment.setReason(reason);
        appointment.setStatus("BOOKED");
        return appointment;
    }

    public void markRescheduled(Appointment appointment, LocalDate date, LocalTime slotTime) {
        appointment.setAppointmentDate(date);
        appointment.setSlotTime(slotTime);
        appointment.setStatus("RESCHEDULED");
    }
}
