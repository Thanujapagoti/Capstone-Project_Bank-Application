package com.bank.audit.service;

import com.bank.audit.entity.AuditLog;
import com.bank.audit.entity.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public AuditLog saveLog(String serviceName, String action, String details) {
        AuditLog log = AuditLog.builder()
                .serviceName(serviceName)
                .action(action)
                .details(details)
                .timestamp(LocalDateTime.now())
                .build();
        return auditLogRepository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll();
    }
}
