package com.elastic.search.config;

import com.elastic.search.entity.AuditLog;
import com.elastic.search.repository.AuditLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

@SpringBootApplication
@Slf4j

public class ElasticsearchappApplication {



        private static final String COMMA_DELIMITER = ",";

        @Autowired
        private ElasticsearchOperations esOps;

        @Autowired
        private AuditLogRepository auditRepo;

        public static void main(String[] args) {
            SpringApplication.run(ElasticsearchappApplication.class, args);
        }

//        @PreDestroy
//        public void deleteIndex() {
//            esOps.indexOps(AuditLog.class).delete();
//        }
//
//
//        @PostConstruct
//        public void buildIndex() {
//
//            AuditLog auditLog= AuditLog.builder().id("1"),name("test2").description("testdesc2").build();
//
//            esOps.indexOps(AuditLog.class).refresh();
//            //productRepo.deleteAll();
//            //productRepo.saveAll(product);
//            auditRepo.save(auditLog);
//        }

//        private Collection<AuditLog> prepareDataset() {
//            Resource resource = new ClassPathResource("fashion-products.csv");
//            List<AuditLog> productList = new ArrayList<AuditLog>();
//
//            try (
//                    InputStream input = resource.getInputStream();
//                    Scanner scanner = new Scanner(resource.getInputStream());) {
//                int lineNo = 0;
//                while (scanner.hasNextLine()) {
//                    ++lineNo;
//                    String line = scanner.nextLine();
//                    if(lineNo == 1) continue;
//                    Optional<AuditLog> auditLog =
//                            csvRowToAuditLogMapper(line);
//                    if(auditLog.isPresent())
//                        productList.add(auditLog.get());
//                }
//            } catch (Exception e) {
//                log.error("File read error {}",e);;
//            }
//            return productList;
//        }
//
//        private Optional<Product> csvRowToProductMapper(final String line) {
//            try (
//                    Scanner rowScanner = new Scanner(line)) {
//                rowScanner.useDelimiter(COMMA_DELIMITER);
//                while (rowScanner.hasNext()) {
//                    String name = rowScanner.next();
//                    String description = rowScanner.next();
//                    String manufacturer = rowScanner.next();
//                    return Optional.of(
//                            Product.builder()
//                                    .name(name)
//                                    .description(description)
//                                    .manufacturer(manufacturer)
//                                    .build());
//
//                }
//            }
//            return Optional.of(null);
//        }

    }


