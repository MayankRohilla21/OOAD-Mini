package inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import inventory.model.Inventory;
import inventory.service.InventoryService;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public String listInventory(Model model) {
        List<Inventory> all = inventoryService.getAllInventory();
        List<Inventory> lowStock = inventoryService.getLowStockItems();
        model.addAttribute("medicines", all);
        model.addAttribute("lowStockCount", lowStock.size());
        return "inventory-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("medicine", new Inventory());
        return "add-inventory";
    }

    @PostMapping("/save")
    public String saveMedicine(@ModelAttribute Inventory inventory, RedirectAttributes redirectAttributes) {
        inventoryService.saveMedicine(inventory);
        redirectAttributes.addFlashAttribute("success", "Medicine saved successfully!");
        return "redirect:/inventory";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Inventory medicine = inventoryService.getInventoryById(id);
        if (medicine == null) {
            return "redirect:/inventory";
        }
        model.addAttribute("medicine", medicine);
        return "edit-inventory";
    }

    @PostMapping("/update/{id}")
    public String updateMedicine(@PathVariable Long id, @ModelAttribute Inventory inventory,
            RedirectAttributes redirectAttributes) {
        inventory.setId(id);
        inventoryService.saveMedicine(inventory);
        redirectAttributes.addFlashAttribute("success", "Medicine updated successfully!");
        return "redirect:/inventory";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        inventoryService.deleteMedicine(id);
        redirectAttributes.addFlashAttribute("success", "Medicine deleted successfully!");
        return "redirect:/inventory";
    }

    @PostMapping("/update-stock/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam Integer quantity,
            RedirectAttributes redirectAttributes) {
        inventoryService.updateStock(id, quantity);
        redirectAttributes.addFlashAttribute("success", "Stock updated successfully!");
        return "redirect:/inventory";
    }

    @GetMapping("/low-stock")
    public String lowStockReport(Model model) {
        model.addAttribute("lowStockItems", inventoryService.getLowStockItems());
        return "low-stock-report";
    }

    @GetMapping("/remove-expired")
    public String removeExpired(RedirectAttributes redirectAttributes) {
        int count = inventoryService.removeExpiredItems();
        redirectAttributes.addFlashAttribute("success", count + " expired item(s) removed.");
        return "redirect:/inventory";
    }
}
