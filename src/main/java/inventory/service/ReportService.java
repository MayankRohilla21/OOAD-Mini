package inventory.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import appointment.repository.AppointmentRepository;
import billing.repository.BillRepository;
import inventory.model.Report;
import inventory.repository.InventoryRepository;
import inventory.repository.ReportRepository;

@Service
public class ReportService implements ReportOperations {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private BillRepository billRepository;

    @Override
    public Report generateDailyReport() {
        LocalDate today = LocalDate.now();

        long appointmentCount = appointmentRepository.countByAppointmentDate(today);

        Report report = new Report();
        report.setReportType("DAILY_APPOINTMENTS");
        report.setReportDate(today);
        report.setTotalAppointments((int) appointmentCount);
        report.setReportData("Daily appointments on " + today + ": " + appointmentCount);

        return reportRepository.save(report);
    }

    @Override
    public Report generateRevenueReport() {
        LocalDate today = LocalDate.now();

        double totalRevenue = billRepository.sumTotalRevenue();

        Report report = new Report();
        report.setReportType("REVENUE");
        report.setReportDate(today);
        report.setTotalRevenue(BigDecimal.valueOf(totalRevenue));
        report.setReportData("Total revenue as of " + today + ": " + totalRevenue);

        return reportRepository.save(report);
    }

    @Override
    public Report generateLowStockReport() {
        LocalDate today = LocalDate.now();

        List<?> lowStockItems = inventoryRepository.findLowStockItems();
        int lowStockCount = lowStockItems.size();

        Report report = new Report();
        report.setReportType("LOW_STOCK");
        report.setReportDate(today);
        report.setLowStockItems(lowStockCount);
        report.setReportData("Low stock items as of " + today + ": " + lowStockCount);

        return reportRepository.save(report);
    }

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    public Report getReportById(Long id) {
        return reportRepository.findById(id).orElse(null);
    }
}
