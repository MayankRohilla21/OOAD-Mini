package inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import inventory.model.Report;
import inventory.service.InventoryService;
import inventory.service.ReportService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String dashboard(Model model) {
        List<Report> allReports = reportService.getAllReports();
        model.addAttribute("reports", allReports);
        model.addAttribute("lowStockCount", inventoryService.getLowStockItems().size());
        model.addAttribute("lowStockItems", inventoryService.getLowStockItems());
        return "reports-dashboard";
    }

    @GetMapping("/generate/revenue")
    public String generateRevenue(RedirectAttributes ra) {
        Report report = reportService.generateRevenueReport();
        ra.addFlashAttribute("success", "Revenue report generated for " + report.getReportDate());
        return "redirect:/reports";
    }

    @GetMapping("/generate/low-stock")
    public String generateLowStock(RedirectAttributes ra) {
        Report report = reportService.generateLowStockReport();
        ra.addFlashAttribute("success",
                "Low stock report generated – " + report.getLowStockItems() + " item(s) below minimum.");
        return "redirect:/reports";
    }

    @GetMapping("/generate/daily")
    public String generateDaily(RedirectAttributes ra) {
        Report report = reportService.generateDailySummaryReport();
        ra.addFlashAttribute("success", "Daily summary report generated for " + report.getReportDate());
        return "redirect:/reports";
    }
}
