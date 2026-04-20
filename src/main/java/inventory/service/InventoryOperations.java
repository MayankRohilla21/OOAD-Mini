package inventory.service;

import java.util.List;

import inventory.model.Inventory;

/**
 * Interface Segregation Principle:
 * Separate interface for inventory CRUD operations only.
 */
public interface InventoryOperations {

    List<Inventory> getAllInventory();

    Inventory getInventoryById(Long id);

    Inventory saveMedicine(Inventory inventory);

    void deleteMedicine(Long id);

    void updateStock(Long id, Integer quantity);

    List<Inventory> getLowStockItems();

    List<Inventory> getExpiredItems();

    int removeExpiredItems();
}
