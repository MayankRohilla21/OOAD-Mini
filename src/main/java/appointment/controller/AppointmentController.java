package appointment.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import appointment.service.AppointmentService;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        return "appointments/list";
    }

    @GetMapping("/new")
    public String showBookForm() {
        return "appointments/book";
    }

    @PostMapping("/save")
    public String bookAppointment(
            @RequestParam String patientName,
            @RequestParam String doctorName,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime slotTime,
            @RequestParam(required = false) String reason) {
        appointmentService.bookAppointment(patientName, doctorName, appointmentDate, slotTime, reason);
        return "redirect:/appointments";
    }

    @GetMapping("/reschedule/{id}")
    public String showRescheduleForm(@PathVariable("id") Long appointmentId, Model model) {
        model.addAttribute("appointmentId", appointmentId);
        return "appointments/reschedule";
    }

    @PostMapping("/reschedule")
    public String rescheduleAppointment(
            @RequestParam Long appointmentId,
            @RequestParam LocalDate appointmentDate,
            @RequestParam LocalTime slotTime) {
        appointmentService.rescheduleAppointment(appointmentId, appointmentDate, slotTime);
        return "redirect:/appointments";
    }

    @GetMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable("id") Long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return "redirect:/appointments";
    }

    @GetMapping("/slots")
    public String getBookedSlots(
            @RequestParam String doctorName,
            @RequestParam LocalDate appointmentDate,
            Model model) {
        model.addAttribute("bookedSlots", appointmentService.getBookedSlots(doctorName, appointmentDate));
        model.addAttribute("doctorName", doctorName);
        model.addAttribute("appointmentDate", appointmentDate);
        return "appointments/slots";
    }

    @GetMapping("/schedules")
    public String listSchedules(Model model) {
        model.addAttribute("schedules", appointmentService.getAllSchedules());
        return "appointments/schedule-manage";
    }

    @PostMapping("/schedules/save")
    public String addSchedule(
            @RequestParam String doctorName,
            @RequestParam LocalDate availableDate,
            @RequestParam LocalTime startTime,
            @RequestParam LocalTime endTime) {
        appointmentService.addDoctorAvailability(doctorName, availableDate, startTime, endTime);
        return "redirect:/appointments/schedules";
    }

    @GetMapping("/schedules/unavailable/{id}")
    public String markUnavailable(@PathVariable("id") Long scheduleId) {
        appointmentService.markDoctorUnavailable(scheduleId);
        return "redirect:/appointments/schedules";
    }
}
