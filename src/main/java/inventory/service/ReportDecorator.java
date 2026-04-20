package inventory.service;

import inventory.model.Report;

/**
 * Decorator Pattern base class for adding filter/export features to reports.
 * Design Pattern: Decorator – wraps a Report and extends its behaviour
 * without modifying the Report entity itself.
 */
public abstract class ReportDecorator {

    protected Report report;

    public ReportDecorator(Report report) {
        this.report = report;
    }

    public Report getReport() {
        return report;
    }

    /**
     * Apply additional processing / filtering on the wrapped report.
     * Concrete decorators override this to add CSV export, PDF export,
     * date-range filtering, etc.
     */
    public abstract Report decorate();

    /**
     * Concrete decorator: adds a date-range filter label to report data.
     */
    public static class DateRangeFilterDecorator extends ReportDecorator {

        private final String fromDate;
        private final String toDate;

        public DateRangeFilterDecorator(Report report, String fromDate, String toDate) {
            super(report);
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        @Override
        public Report decorate() {
            String existing = report.getReportData() != null ? report.getReportData() : "{}";
            // Append filter metadata to the JSON string
            String filtered = existing.replace("}", ",\"fromDate\":\"" + fromDate
                    + "\",\"toDate\":\"" + toDate + "\"}");
            report.setReportData(filtered);
            return report;
        }
    }

    /**
     * Concrete decorator: marks a report as exported to CSV.
     */
    public static class CsvExportDecorator extends ReportDecorator {

        public CsvExportDecorator(Report report) {
            super(report);
        }

        @Override
        public Report decorate() {
            String existing = report.getReportData() != null ? report.getReportData() : "{}";
            String exported = existing.replace("}", ",\"exportFormat\":\"CSV\"}");
            report.setReportData(exported);
            return report;
        }
    }
}
