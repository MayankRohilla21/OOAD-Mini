package inventory.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import inventory.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByReportDateBetween(LocalDate start, LocalDate end);

    List<Report> findByReportType(String reportType);
}
