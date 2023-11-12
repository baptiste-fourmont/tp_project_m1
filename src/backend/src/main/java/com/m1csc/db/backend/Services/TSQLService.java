package com.m1csc.db.backend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

    static public Path PATH = Paths.get("./","..","transactions/");
    static public String ADD_TRANSACTION = "add_stock_transaction.sql";
    static public String MOD_TRANSACTION = "modifier_stock_transaction.sql";
    static public String DEL_TRANSACTION = "del_stock_transaction.sql";
    static public String TEST_QUERY =
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



    public String readFile(String filename) throws IOException {
        //Lire les fichiers présents dans le dossier transactions a l'exterieur du projet
        Path filePath = PATH.resolve(filename).toAbsolutePath().normalize();
        Resource resource = new PathResource(filePath);
        return Files.lines(filePath, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
    }

    public void executeCustomTransaction(String query) {
        jdbcTemplate.execute(query);
    }
}
