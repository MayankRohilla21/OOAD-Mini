package inventory.service;

import java.util.List;

import inventory.model.Report;

/**
 * Interface Segregation Principle:
 * Separate interface for reporting operations only.
 */
public interface ReportOperations {

    Report generateDailyReport();

    Report generateRevenueReport();

    Report generateLowStockReport();

    List<Report> getAllReports();

    Report getReportById(Long id);
}
