package inventory.service;

import inventory.model.Report;

/**
 * Decorator Pattern:
 * Abstract base decorator for adding features to Report objects
 * (e.g., CSV export, PDF export, filtering).
 */
public abstract class ReportDecorator {

    protected Report report;

    public ReportDecorator(Report report) {
        this.report = report;
    }

    public abstract String export();

    public Report getReport() {
        return report;
    }
}
