package inventory.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    // Find items where stock is at or below the minimum stock level
    @Query("SELECT i FROM Inventory i WHERE i.minStockLevel IS NOT NULL AND i.stockQuantity <= i.minStockLevel")
    List<Inventory> findLowStockItems();

    // Find items that have expired (expiry date before today)
    List<Inventory> findByExpiryDateBefore(LocalDate date);

    // Find items by category
    List<Inventory> findByCategory(String category);
}
