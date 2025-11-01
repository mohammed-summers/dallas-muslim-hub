package dev.mohdsummers.dallasmuslimhub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/monitor")
public class MonitoringController {

    private final JdbcTemplate jdbcTemplate;

    public MonitoringController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> checkHealth() {
        Map<String, Object> response = new HashMap<>();

        try {
            // Check DB connectivity
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            response.put("database", "UP");
            response.put("status", "OK");

            // Add basic DB metrics
            response.put("establishments_count",
                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM establishment", Long.class));

            response.put("pending_count",
                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM establishment WHERE status = 'PENDING'", Long.class));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("database", "DOWN");
            response.put("status", "ERROR");
            response.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/db-stats")
    public ResponseEntity<Map<String, Object>> getDatabaseStats() {
        Map<String, Object> stats = new HashMap<>();

        try {
            stats.put("total_establishments",
                    jdbcTemplate.queryForObject("SELECT COUNT(*) FROM establishment", Long.class));

            stats.put("status_distribution",
                    jdbcTemplate.queryForList("SELECT status, COUNT(*) as count FROM establishment GROUP BY status"));

            stats.put("cities_count",
                    jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT city) FROM establishment", Long.class));

            stats.put("cuisines_count",
                    jdbcTemplate.queryForObject("SELECT COUNT(DISTINCT cuisine) FROM establishment", Long.class));

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            stats.put("error", e.getMessage());
            return ResponseEntity.internalServerError().body(stats);
        }
    }
}