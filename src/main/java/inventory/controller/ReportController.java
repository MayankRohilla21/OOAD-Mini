package inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import inventory.model.Report;
import inventory.service.CsvExportDecorator;
import inventory.service.ReportDecorator;
import inventory.service.ReportService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping
    public String dashboard(Model model) {
        List<Report> reports = reportService.getAllReports();
        model.addAttribute("reports", reports);
        return "reports-dashboard";
    }

    @GetMapping("/daily")
    public String generateDailyReport(Model model) {
        Report report = reportService.generateDailyReport();
        model.addAttribute("report", report);
        model.addAttribute("reportTitle", "Daily Appointments Report");
        return "reports-dashboard";
    }

    @GetMapping("/revenue")
    public String generateRevenueReport(Model model) {
        Report report = reportService.generateRevenueReport();
        model.addAttribute("report", report);
        model.addAttribute("reportTitle", "Revenue Summary Report");
        return "reports-dashboard";
    }

    @GetMapping("/low-stock")
    public String generateLowStockReport(Model model) {
        Report report = reportService.generateLowStockReport();
        model.addAttribute("report", report);
        model.addAttribute("reportTitle", "Low Stock Report");
        return "reports-dashboard";
    }

    @GetMapping("/export/{id}")
    public String exportReport(@PathVariable Long id, Model model) {
        Report report = reportService.getReportById(id);
        if (report == null) {
            return "redirect:/reports";
        }
        ReportDecorator decorator = new CsvExportDecorator(report);
        model.addAttribute("csvData", decorator.export());
        model.addAttribute("report", report);
        return "reports-dashboard";
    }
}
