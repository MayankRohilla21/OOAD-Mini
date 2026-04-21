package inventory.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import inventory.model.Inventory;
import inventory.repository.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> getAllItems() {
        return inventoryRepository.findAll();
    }

    public Inventory getItemById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory saveItem(Inventory item) {
        return inventoryRepository.save(item);
    }

    public void deleteItem(Long id) {
        inventoryRepository.deleteById(id);
    }

    public List<Inventory> getLowStockItems(int threshold) {
        return inventoryRepository.findByQuantityLessThanEqual(threshold)
                .stream()
                .filter(item -> item.getExpiryDate() == null || !item.getExpiryDate().isBefore(LocalDate.now()))
                .toList();
    }

    public long removeExpiredItems() {
        List<Inventory> expiredItems = inventoryRepository.findByExpiryDateBefore(LocalDate.now());
        long removedCount = expiredItems.size();
        inventoryRepository.deleteAll(expiredItems);
        return removedCount;
    }
}
