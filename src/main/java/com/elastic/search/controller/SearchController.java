package com.elastic.search.controller;

import com.elastic.search.entity.AuditLog;
import com.elastic.search.repository.AuditLogRepository;
import com.elastic.search.service.AuditLogSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@Slf4j
public class SearchController {

    private AuditLogSearchService searchService;
    private AuditLogRepository auditLogRepository;

    @Autowired
    public SearchController( AuditLogSearchService searchService, AuditLogRepository auditLogRepository) {
        this.searchService = searchService;
        this.auditLogRepository = auditLogRepository;
    }

    @GetMapping("/logs")
    @ResponseBody
    public List<AuditLog> fetchByNameOrDesc(@RequestParam(value = "q", required = false) String query) {
        log.info("searching by name {}",query);
        List<AuditLog> logs = searchService.processSearch(query) ;
        System.out.println(logs);
        System.out.println("-----------------------");
        System.out.println(query);
        log.info("logs {}",logs);
        return logs;
    }

    @GetMapping("/suggestions")
    @ResponseBody
    public List<String> fetchSuggestions(@RequestParam(value = "q", required = false) String query) {
        log.info("fetch suggests {}",query);
        List<String> suggests = searchService.fetchSuggestions(query);
        log.info("suggests {}",suggests);

        return suggests;
    }


    @PostMapping("/add")
    @ResponseBody
    public String addItem(@RequestBody AuditLog auditLog) {
        auditLogRepository.save(auditLog);
        return "added";
    }

    @GetMapping("/getAll")
    @ResponseBody
    public Iterable<AuditLog> getAll() {
        Iterable<AuditLog> logs = this.auditLogRepository.findAll();
        return logs;
    }


}