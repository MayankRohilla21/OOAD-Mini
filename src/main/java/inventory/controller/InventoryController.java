package inventory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import inventory.model.Inventory;
import inventory.service.InventoryService;

@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public String listInventory(Model model) {
        model.addAttribute("items", inventoryService.getAllItems());
        return "inventory-list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("inventory", new Inventory());
        return "add-inventory";
    }

    @PostMapping("/save")
    public String saveInventory(@ModelAttribute Inventory inventory) {
        inventoryService.saveItem(inventory);
        return "redirect:/inventory";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("inventory", inventoryService.getItemById(id));
        return "edit-inventory";
    }

    @PostMapping("/update/{id}")
    public String updateInventory(@PathVariable Long id, @ModelAttribute Inventory inventory) {
        inventory.setId(id);
        inventoryService.saveItem(inventory);
        return "redirect:/inventory";
    }

    @GetMapping("/delete/{id}")
    public String deleteInventory(@PathVariable Long id) {
        inventoryService.deleteItem(id);
        return "redirect:/inventory";
    }

    @GetMapping("/remove-expired")
    public String removeExpiredInventory() {
        inventoryService.removeExpiredItems();
        return "redirect:/inventory";
    }
}
