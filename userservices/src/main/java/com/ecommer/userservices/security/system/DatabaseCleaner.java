//package com.ecommer.userservices.security.auth2server.customization;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DatabaseCleaner implements CommandLineRunner {
//    private final JdbcTemplate jdbcTemplate;
//
//    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
//        jdbcTemplate.execute("DROP TABLE IF EXISTS roles;");
//        jdbcTemplate.execute("DROP TABLE IF EXISTS users;");
//        // Add more tables as needed
//        jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
//    }
//}
