package inventory.controller;

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
        model.addAttribute("items", inventoryService.getAllInventory());
        model.addAttribute("lowStockItems", inventoryService.getLowStockItems());
        return "inventory-list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "add-inventory";
    }

    @PostMapping("/save")
    public String saveMedicine(@ModelAttribute Inventory inventory, RedirectAttributes ra) {
        inventoryService.save(inventory);
        ra.addFlashAttribute("success", "Medicine saved successfully!");
        return "redirect:/inventory";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("inventory", inventoryService.getById(id));
        return "edit-inventory";
    }

    @PostMapping("/update/{id}")
    public String updateMedicine(@PathVariable Long id, @ModelAttribute Inventory inventory,
            RedirectAttributes ra) {
        Inventory existing = inventoryService.getById(id);
        existing.setMedicineName(inventory.getMedicineName());
        existing.setStockQuantity(inventory.getStockQuantity());
        existing.setUnitPrice(inventory.getUnitPrice());
        existing.setExpiryDate(inventory.getExpiryDate());
        existing.setMinStockLevel(inventory.getMinStockLevel());
        existing.setCategory(inventory.getCategory());
        inventoryService.save(existing);
        ra.addFlashAttribute("success", "Medicine updated successfully!");
        return "redirect:/inventory";
    }

    @GetMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id, RedirectAttributes ra) {
        inventoryService.deleteById(id);
        ra.addFlashAttribute("success", "Medicine deleted.");
        return "redirect:/inventory";
    }

    @PostMapping("/update-stock/{id}")
    public String updateStock(@PathVariable Long id, @RequestParam Integer quantity,
            RedirectAttributes ra) {
        inventoryService.updateStock(id, quantity);
        ra.addFlashAttribute("success", "Stock updated.");
        return "redirect:/inventory";
    }

    @GetMapping("/remove-expired")
    public String removeExpired(RedirectAttributes ra) {
        int count = inventoryService.removeExpiredItems();
        ra.addFlashAttribute("success", count + " expired item(s) removed.");
        return "redirect:/inventory";
    }

    @GetMapping("/low-stock")
    public String lowStockReport(Model model) {
        model.addAttribute("lowStockItems", inventoryService.getLowStockItems());
        return "low-stock-report";
    }
}
