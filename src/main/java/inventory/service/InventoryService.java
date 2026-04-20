package inventory.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inventory.model.Inventory;
import inventory.repository.InventoryRepository;

@Service
public class InventoryService implements InventoryOperations {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    @Override
    public Inventory saveMedicine(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteMedicine(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public void updateStock(Long id, Integer quantity) {
        Inventory item = inventoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Medicine not found with id: " + id));
        item.setStockQuantity(quantity);
        inventoryRepository.save(item);
    }

    @Override
    public List<Inventory> getLowStockItems() {
        return inventoryRepository.findLowStockItems();
    }

    @Override
    public List<Inventory> getExpiredItems() {
        return inventoryRepository.findByExpiryDateBefore(LocalDate.now());
    }

    @Override
    public int removeExpiredItems() {
        List<Inventory> expired = getExpiredItems();
        int count = expired.size();
        inventoryRepository.deleteAll(expired);
        return count;
    }
}
