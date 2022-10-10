package com.elastic.search.service;


import com.elastic.search.entity.AuditLog;
import com.elastic.search.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AuditLogSearchServiceWithRepo {


    private AuditLogRepository auditLogRepository;

    @Autowired
    public AuditLogSearchServiceWithRepo(final AuditLogRepository auditLogRepository) {
        super();
        this.auditLogRepository = auditLogRepository;
    }

    public void createProductIndexBulk(final List<AuditLog> logs) {
        auditLogRepository.saveAll(logs);
    }

    public void createProductIndex(final AuditLog auditLog) {
        auditLogRepository.save(auditLog);
    }

    public List<AuditLog> findAuditLogByNameAndDescription(final String name, final String description) {
        return auditLogRepository.findAuditLogByNameAndDescription(name, description);
    }

    public List<AuditLog> findByName(final String name) {
        return auditLogRepository.findByName(name);
    }

    public List<AuditLog> findByAuditLogMatchingNames(final String name) {
        return auditLogRepository.findByNameContaining(name);
    }
}
