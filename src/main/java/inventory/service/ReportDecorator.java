package inventory.service;

import java.util.Locale;

import inventory.model.Report;

public class ReportDecorator {

    public interface ReportDetailsFormatter {
        String format(Report report);
    }

    public static class BaseFormatter implements ReportDetailsFormatter {
        @Override
        public String format(Report report) {
            return "Report Date: " + report.getReportDate()
                    + " | Appointments Today: " + report.getTotalAppointments();
        }
    }

    public abstract static class FormatterDecorator implements ReportDetailsFormatter {
        private final ReportDetailsFormatter delegate;

        protected FormatterDecorator(ReportDetailsFormatter delegate) {
            this.delegate = delegate;
        }

        @Override
        public String format(Report report) {
            return decorate(delegate.format(report), report);
        }

        protected abstract String decorate(String currentDetails, Report report);
    }

    public static class RevenueSectionDecorator extends FormatterDecorator {
        public RevenueSectionDecorator(ReportDetailsFormatter delegate) {
            super(delegate);
        }

        @Override
        protected String decorate(String currentDetails, Report report) {
            return currentDetails
                    + " | Revenue: INR "
                    + String.format(Locale.US, "%.2f", report.getTotalRevenue());
        }
    }

    public static class LowStockSectionDecorator extends FormatterDecorator {
        public LowStockSectionDecorator(ReportDetailsFormatter delegate) {
            super(delegate);
        }

        @Override
        protected String decorate(String currentDetails, Report report) {
            return currentDetails + " | Low Stock Items: " + report.getLowStockItems();
        }
    }
}
