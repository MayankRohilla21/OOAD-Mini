package inventory.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.model.Inventory;
import inventory.repository.InventoryRepository;

/**
 * Service for inventory operations.
 * Design Principle: Interface Segregation – only inventory concerns here,
 * no report logic mixed in.
 */
@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Inventory getById(Long id) {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inventory item not found: " + id));
    }

    public Inventory save(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    public void updateStock(Long id, Integer quantity) {
        Inventory item = getById(id);
        item.setStockQuantity(quantity);
        inventoryRepository.save(item);
    }

    /** Returns items whose stock is below their minimum stock level. */
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findAll().stream()
                .filter(Inventory::isLowStock)
                .collect(Collectors.toList());
    }

    /** Deletes all items whose expiry date is before today. */
    public int removeExpiredItems() {
        List<Inventory> expired = inventoryRepository.findByExpiryDateBefore(LocalDate.now());
        inventoryRepository.deleteAll(expired);
        return expired.size();
    }

    public List<Inventory> getExpiredItems() {
        return inventoryRepository.findByExpiryDateBefore(LocalDate.now());
    }
}
