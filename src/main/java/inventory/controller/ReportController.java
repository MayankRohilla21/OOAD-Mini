package inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import inventory.service.ReportService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public String viewDashboard(Model model) {
        model.addAttribute("dashboardReport", reportService.generateDashboardReport());
        model.addAttribute("latestReports", reportService.getLatestReports());
        return "reports-dashboard";
    }

    @GetMapping("/low-stock")
    public String viewLowStockReport(
            @RequestParam(defaultValue = "10") int threshold,
            Model model) {

        model.addAttribute("lowStockItems", reportService.getLowStockItems(threshold));
        model.addAttribute("lowStockReport", reportService.generateLowStockReport(threshold));
        model.addAttribute("threshold", threshold);

        return "low-stock-report";
    }
}
