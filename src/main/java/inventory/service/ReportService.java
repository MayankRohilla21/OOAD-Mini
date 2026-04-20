package inventory.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import billing.repository.BillRepository;
import inventory.model.Report;
import inventory.repository.InventoryRepository;
import inventory.repository.ReportRepository;

/**
 * Service for report generation.
 * Design Principle: Interface Segregation – only report concerns here,
 * no inventory CRUD mixed in.
 */
@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BillRepository billRepository;

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public List<Report> getReportsByType(String reportType) {
        return reportRepository.findByReportType(reportType);
    }

    public List<Report> getReportsByDateRange(LocalDate start, LocalDate end) {
        return reportRepository.findByReportDateBetween(start, end);
    }

    /**
     * Generates and persists a revenue report for today based on all bills.
     */
    public Report generateRevenueReport() {
        BigDecimal totalRevenue = billRepository.findAll().stream()
                .map(b -> BigDecimal.valueOf(b.getTotalAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Report report = new Report();
        report.setReportType("REVENUE");
        report.setReportDate(LocalDate.now());
        report.setTotalRevenue(totalRevenue);
        report.setLowStockItems(getLowStockCount());
        report.setReportData("{\"type\":\"revenue\",\"generated\":\"" + LocalDate.now() + "\"}");
        return reportRepository.save(report);
    }

    /**
     * Generates and persists a low-stock report.
     */
    public Report generateLowStockReport() {
        int lowStockCount = getLowStockCount();

        Report report = new Report();
        report.setReportType("LOW_STOCK");
        report.setReportDate(LocalDate.now());
        report.setLowStockItems(lowStockCount);
        report.setReportData("{\"type\":\"low_stock\",\"count\":" + lowStockCount + "}");
        return reportRepository.save(report);
    }

    /**
     * Generates and persists a daily summary report.
     */
    public Report generateDailySummaryReport() {
        BigDecimal totalRevenue = billRepository.findAll().stream()
                .map(b -> BigDecimal.valueOf(b.getTotalAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int lowStockCount = getLowStockCount();

        Report report = new Report();
        report.setReportType("DAILY_SUMMARY");
        report.setReportDate(LocalDate.now());
        report.setTotalRevenue(totalRevenue);
        report.setLowStockItems(lowStockCount);
        report.setReportData("{\"type\":\"daily_summary\",\"date\":\"" + LocalDate.now() + "\"}");
        return reportRepository.save(report);
    }

    private int getLowStockCount() {
        return (int) inventoryRepository.findAll().stream()
                .filter(inventory.model.Inventory::isLowStock)
                .count();
    }
}
