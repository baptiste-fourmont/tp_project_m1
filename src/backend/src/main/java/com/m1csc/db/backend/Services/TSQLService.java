package com.m1csc.db.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

/**
 * Custom SQL Transaction Queries service
 * Made it to test homemade transactions :-)
 */

@Service
public class TSQLService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    static public String path = "sql/queries.sql";
    static public String testQuery =
            "DO $$ " +
                "BEGIN " +
                "UPDATE products SET quantity = 42069 WHERE product_id = 1; " +
                "UPDATE transactions SET quantity_changed = 42069 WHERE transaction_id = 1; " +
                "IF (SELECT quantity FROM products WHERE product_id = 1) < 0 THEN " +
                    "ROLLBACK; " +
                "ELSE " +
                    "COMMIT; " +
                "END IF; " +
            "END $$;";

    public static  String readResource(String path) throws IOException {
        Resource resource = new ClassPathResource(path);
        return Files.lines(Paths.get(resource.getURI())).collect(Collectors.joining("\n"));
    }

    public void executeCustomTransaction(String query) {
        jdbcTemplate.execute(query);
    }
}
