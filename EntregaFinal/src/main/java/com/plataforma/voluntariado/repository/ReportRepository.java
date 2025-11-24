package com.plataforma.voluntariado.repository;

import com.plataforma.voluntariado.domain.Report;
import com.plataforma.voluntariado.domain.enums.ReportType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReportRepository extends MongoRepository<Report, UUID> {

  List<Report> findByReportType(ReportType type);
}
