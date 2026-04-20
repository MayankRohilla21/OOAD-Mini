package inventory.service;

import inventory.model.Report;

/**
 * Decorator Pattern - Concrete Decorator:
 * Exports report data as a CSV-formatted string.
 */
public class CsvExportDecorator extends ReportDecorator {

    public CsvExportDecorator(Report report) {
        super(report);
    }

    @Override
    public String export() {
        return "report_type,report_date,total_appointments,total_revenue,low_stock_items\n"
                + report.getReportType() + ","
                + report.getReportDate() + ","
                + (report.getTotalAppointments() != null ? report.getTotalAppointments() : "") + ","
                + (report.getTotalRevenue() != null ? report.getTotalRevenue() : "") + ","
                + (report.getLowStockItems() != null ? report.getLowStockItems() : "");
    }
}
