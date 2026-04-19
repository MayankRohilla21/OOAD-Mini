package appointment.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import appointment.factory.AppointmentFactory;
import appointment.model.Appointment;
import appointment.model.DoctorSchedule;
import appointment.repository.AppointmentRepository;
import appointment.repository.DoctorScheduleRepository;
import appointment.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final AppointmentFactory appointmentFactory;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            DoctorScheduleRepository doctorScheduleRepository,
            AppointmentFactory appointmentFactory) {
        this.appointmentRepository = appointmentRepository;
        this.doctorScheduleRepository = doctorScheduleRepository;
        this.appointmentFactory = appointmentFactory;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Appointment::getAppointmentDate).reversed())
                .toList();
    }

    @Override
    public Appointment bookAppointment(String patientName, String doctorName, LocalDate date, LocalTime slotTime, String reason) {
        Appointment appointment = appointmentFactory.createBooked(patientName, doctorName, date, slotTime, reason);
        return appointmentRepository.save(appointment);
    }

    @Override
    public Appointment rescheduleAppointment(Long appointmentId, LocalDate newDate, LocalTime newSlotTime) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElse(null);
        if (appointment == null) {
            return null;
        }

        appointmentFactory.markRescheduled(appointment, newDate, newSlotTime);
        return appointmentRepository.save(appointment);
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        appointmentRepository.findById(appointmentId).ifPresent(appointment -> {
            appointment.setStatus("CANCELLED");
            appointmentRepository.save(appointment);
        });
    }

    @Override
    public List<LocalTime> getBookedSlots(String doctorName, LocalDate date) {
        return appointmentRepository.findByDoctorNameAndAppointmentDateAndStatusNot(doctorName, date, "CANCELLED")
                .stream()
                .map(Appointment::getSlotTime)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorSchedule> getAllSchedules() {
        return doctorScheduleRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(DoctorSchedule::getAvailableDate).reversed())
                .toList();
    }

    @Override
    public DoctorSchedule addDoctorAvailability(String doctorName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        DoctorSchedule doctorSchedule = new DoctorSchedule();
        doctorSchedule.setDoctorName(doctorName);
        doctorSchedule.setAvailableDate(date);
        doctorSchedule.setStartTime(startTime);
        doctorSchedule.setEndTime(endTime);
        doctorSchedule.setAvailable(true);
        return doctorScheduleRepository.save(doctorSchedule);
    }

    @Override
    public void markDoctorUnavailable(Long scheduleId) {
        doctorScheduleRepository.findById(scheduleId).ifPresent(schedule -> {
            schedule.setAvailable(false);
            doctorScheduleRepository.save(schedule);
        });
    }
}
