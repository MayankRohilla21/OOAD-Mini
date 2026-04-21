package inventory.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import inventory.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    List<Inventory> findByQuantityLessThanEqual(Integer threshold);

    List<Inventory> findByExpiryDateBefore(LocalDate date);
}
