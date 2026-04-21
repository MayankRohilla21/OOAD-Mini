package inventory.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import appointment.repository.AppointmentRepository;
import billing.repository.BillRepository;
import inventory.model.Inventory;
import inventory.model.Report;
import inventory.repository.ReportRepository;

@Service
public class ReportService {

    private static final int DEFAULT_LOW_STOCK_THRESHOLD = 10;

    private final ReportRepository reportRepository;
    private final AppointmentRepository appointmentRepository;
    private final BillRepository billRepository;
    private final InventoryService inventoryService;

    public ReportService(ReportRepository reportRepository,
            AppointmentRepository appointmentRepository,
            BillRepository billRepository,
            InventoryService inventoryService) {
        this.reportRepository = reportRepository;
        this.appointmentRepository = appointmentRepository;
        this.billRepository = billRepository;
        this.inventoryService = inventoryService;
    }

    public Report generateDashboardReport() {
        Report report = new Report();
        report.setReportDate(LocalDate.now());
        report.setReportType("DASHBOARD");
        report.setTotalAppointments(appointmentRepository.countByAppointmentDate(LocalDate.now()));
        report.setTotalRevenue(getRevenueSummary());
        report.setLowStockItems(getLowStockItems(DEFAULT_LOW_STOCK_THRESHOLD).size());
        report.setDetails(buildDetails(report));
        return reportRepository.save(report);
    }

    public Report generateLowStockReport(int threshold) {
        List<Inventory> lowStockItems = getLowStockItems(threshold);

        Report report = new Report();
        report.setReportDate(LocalDate.now());
        report.setReportType("LOW_STOCK");
        report.setTotalAppointments(appointmentRepository.countByAppointmentDate(LocalDate.now()));
        report.setTotalRevenue(getRevenueSummary());
        report.setLowStockItems(lowStockItems.size());
        report.setDetails(buildDetails(report));

        return reportRepository.save(report);
    }

    public List<Inventory> getLowStockItems(int threshold) {
        return inventoryService.getLowStockItems(threshold);
    }

    public double getRevenueSummary() {
        Double totalRevenue = billRepository.sumPaidAmount();
        return totalRevenue == null ? 0.0 : totalRevenue;
    }

    public List<Report> getLatestReports() {
        return reportRepository.findTop10ByOrderByGeneratedAtDesc();
    }

    private String buildDetails(Report report) {
        ReportDecorator.ReportDetailsFormatter formatter =
                new ReportDecorator.LowStockSectionDecorator(
                        new ReportDecorator.RevenueSectionDecorator(
                                new ReportDecorator.BaseFormatter()));
        return formatter.format(report);
    }
}
