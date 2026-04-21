package inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import inventory.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findTop10ByOrderByGeneratedAtDesc();
}
