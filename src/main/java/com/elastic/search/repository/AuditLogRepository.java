package com.elastic.search.repository;


import com.elastic.search.entity.AuditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditLogRepository extends ElasticsearchRepository<AuditLog,String> {



    List<AuditLog> findByName(String name);

    List<AuditLog> findByNameContaining(String name);

    List<AuditLog> findAuditLogByNameAndDescription(String name, String description);
}
