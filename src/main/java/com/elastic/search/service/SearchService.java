package com.elastic.search.service;

import com.elastic.search.entity.AuditLog;
import com.elastic.search.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SearchService {

    private AuditLogRepository auditLogRepository;

    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public SearchService(AuditLogRepository auditLogRepository, ElasticsearchOperations elasticsearchOperations) {
        super();
        this.auditLogRepository = auditLogRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }



    public List<AuditLog> fetchAuditLogId(final String id){

        return auditLogRepository.findByName(id);
    }


    public List<AuditLog> fetchAuditLogNames(final String name){

        return auditLogRepository.findAuditLogByNameAndDescription(name, "");
    }

    public List<AuditLog> fetchAuditLogNamesContaining(final String name){

        return auditLogRepository.findByNameContaining(name);
    }

}
