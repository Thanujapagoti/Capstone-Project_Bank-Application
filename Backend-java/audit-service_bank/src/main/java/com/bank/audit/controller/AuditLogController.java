package com.bank.audit.controller;

import com.bank.audit.entity.AuditLog;
import com.bank.audit.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping("/log")
    public ResponseEntity<AuditLog> createLog(@RequestParam String serviceName,
                                              @RequestParam String action,
                                              @RequestParam String details) {
        return ResponseEntity.ok(auditLogService.saveLog(serviceName, action, details));
    }

    @GetMapping("/logs")
    public ResponseEntity<List<AuditLog>> getAllLogs() {
        return ResponseEntity.ok(auditLogService.getAllLogs());
    }
}
