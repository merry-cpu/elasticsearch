package com.elastic.search.service;

import com.elastic.search.entity.AuditLog;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuditLogSearchService {

    private static final String AUDITLOG_INDEX = "auditlogindex";

    private ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public AuditLogSearchService(final ElasticsearchOperations elasticsearchOperations) {
        super();
        this.elasticsearchOperations = elasticsearchOperations;
    }

//    public List<String> createProductIndexBulk(final List<AuditLog> auditLogs) {
//
//        List<IndexQuery> queries = auditLogs.stream()
//                .map(auditLog -> new IndexQueryBuilder().withId(auditLog.getId().toString()).withObject(auditLog).build())
//                .collect(Collectors.toList());
//        ;
//
//        return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(AUDITLOG_INDEX));
//
//    }

    public String createAuditLogIndex(AuditLog auditLog) {

        IndexQuery indexQuery = new IndexQueryBuilder().withId(auditLog.getId().toString()).withObject(auditLog).build();
        String documentId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of(AUDITLOG_INDEX));

        return documentId;
    }

    public void findAuditLogsByName(final String name) {
        QueryBuilder queryBuilder = QueryBuilders
                .matchQuery("description", name);
        // .fuzziness(0.8)
        // .boost(1.0f)
        // .prefixLength(0)
        // .fuzzyTranspositions(true);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<AuditLog> auditLogHits =
                elasticsearchOperations
                        .search(searchQuery, AuditLog.class,
                                IndexCoordinates.of(AUDITLOG_INDEX));

        log.info("auditLogHits {} {}", auditLogHits.getSearchHits().size(), auditLogHits.getSearchHits());

        List<SearchHit<AuditLog>> searchHits =
                auditLogHits.getSearchHits();
        int i = 0;
        for (SearchHit<AuditLog> searchHit : searchHits) {
            log.info("searchHit {}", searchHits);
        }

    }

    public void findByAuditLogName(final String name) {
        Query searchQuery = new StringQuery(
                "{\"match\":{\"name\":{\"query\":\""+ name + "\"}}}\"");

        SearchHits<AuditLog> auditLogSearchHits = elasticsearchOperations.search(searchQuery, AuditLog.class,
                IndexCoordinates.of(AUDITLOG_INDEX));
    }

    public void findByProductPrice(final String productPrice) {
        Criteria criteria = new Criteria("price").greaterThan(10.0).lessThan(100.0);
        Query searchQuery = new CriteriaQuery(criteria);

        SearchHits<AuditLog> auditLog = elasticsearchOperations.search(searchQuery, AuditLog.class,
                IndexCoordinates.of(AUDITLOG_INDEX));
    }

    public List<AuditLog> processSearch(final String query) {
        log.info("Search with query {}", query);

        // 1. Create query on multiple fields enabling fuzzy search
        QueryBuilder queryBuilder =
                QueryBuilders
                        .multiMatchQuery(query, "name", "description")
                        .fuzziness(Fuzziness.AUTO);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .build();

        // 2. Execute search
        SearchHits<AuditLog> auditLogHits =
                elasticsearchOperations
                        .search(searchQuery, AuditLog.class,
                                IndexCoordinates.of(AUDITLOG_INDEX));

        // 3. Map searchHits to product list
        List<AuditLog> auditLogMatches = new ArrayList<AuditLog>();
        auditLogHits.forEach(srchHit->{
            auditLogMatches.add(srchHit.getContent());
        });
        return auditLogMatches;
    }




    public List<String> fetchSuggestions(String query) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("name", query+"*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<AuditLog> searchSuggestions =
                elasticsearchOperations.search(searchQuery,
                        AuditLog.class,
                        IndexCoordinates.of(AUDITLOG_INDEX));

        List<String> suggestions = new ArrayList<String>();

        searchSuggestions.getSearchHits().forEach(searchHit->{
            suggestions.add(searchHit.getContent().getName());
        });
        return suggestions;
    }

}
