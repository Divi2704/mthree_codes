package com.example;

import java.sql.Connection;
import java.util.Map;
import com.google.common.cache.Cache;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.concurrent.TimeUnit;
import java.util.LinkedHashMap;
import com.google.common.cache.CacheBuilder;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.ArrayList;
import java.util.List;

public class AdvancedDatabaseCachingBenchmark1 {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Divija@#2704";
    private static final int NUM_ELEMENTS = 100000;
    private static final int L1_CACHE_SIZE = 10000;
    private static final int L2_CACHE_SIZE = 100000;
    private static final int L2_CACHE_DURATION_MINUTES = 10;
    private static final int PROGRESS_INTERVAL = 10000; // Log progress every 10,000 operations

    private static Connection connection;
    private static Map<Integer, String> l1Cache;
    private static Cache<Integer, String> l2Cache;
    private static final Logger LOGGER = Logger.getLogger(AdvancedDatabaseCachingBenchmark.class.getName());

    public static void main(String[] args) {
        setupLogging();
        try {
            setupDatabase();
            setupCaches();
            
            List<BenchmarkResult> results = new ArrayList<>();
            results.add(new BenchmarkResult("Database Insert", benchmarkDatabaseInsert()));
            results.add(new BenchmarkResult("Database Retrieve", benchmarkDatabaseRetrieve()));
            results.add(new BenchmarkResult("L1 Cache Insert", benchmarkL1CacheInsert()));
            results.add(new BenchmarkResult("L1 Cache Retrieve", benchmarkL1CacheRetrieve()));
            results.add(new BenchmarkResult("L2 Cache Insert", benchmarkL2CacheInsert()));
            results.add(new BenchmarkResult("L2 Cache Retrieve", benchmarkL2CacheRetrieve()));
            results.add(new BenchmarkResult("Multilevel Cache Retrieve", benchmarkMultilevelCacheRetrieve()));

            analyzeAndLogResults(results);
            prepareGraphData(results);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during benchmark execution", e);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Error closing database connection", e);
            }
        }
    }

    private static void setupLogging() {
        try {
            FileHandler fileHandler = new FileHandler("benchmark_log.txt");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error setting up logging", e);
        }
    }

    private static void setupDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE test_table");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, value VARCHAR(255))");
            statement.close();
            LOGGER.info("Database setup completed successfully.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error setting up database", e);
        }
    }

    private static void setupCaches() {
        l1Cache = new LinkedHashMap<Integer, String>(L1_CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > L1_CACHE_SIZE;
            }
        };
        l2Cache = CacheBuilder.newBuilder()
                .maximumSize(L2_CACHE_SIZE)
                .expireAfterAccess(L2_CACHE_DURATION_MINUTES, TimeUnit.MINUTES)
                .build();
        LOGGER.info("Caches setup completed successfully.");
    }

    private static long benchmarkDatabaseInsert() throws SQLException {
        LOGGER.info("Starting Database Insert benchmark...");
        long startTime = System.nanoTime();
        String sql = "INSERT INTO test_table (id, value) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            statement.setInt(1, i);
            statement.setString(2, "Value" + i);
            statement.addBatch();
            if (i % 100 == 0) {
                statement.executeBatch();
            }
            logProgress("Database Insert", i);
        }
        statement.executeBatch();
        statement.close();
        long endTime = System.nanoTime();
        LOGGER.info("Database Insert benchmark completed.");
        return endTime - startTime;
    }

    private static long benchmarkDatabaseRetrieve() throws SQLException {
        LOGGER.info("Starting Database Retrieve benchmark...");
        long startTime = System.nanoTime();
        String sql = "SELECT * FROM test_table WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            statement.setInt(1, i);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                resultSet.getString("value");
            }
            resultSet.close();
            logProgress("Database Retrieve", i);
        }
        statement.close();
        long endTime = System.nanoTime();
        LOGGER.info("Database Retrieve benchmark completed.");
        return endTime - startTime;
    }

    private static long benchmarkL1CacheInsert() {
        LOGGER.info("Starting L1 Cache Insert benchmark...");
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            l1Cache.put(i, "Value" + i);
            logProgress("L1 Cache Insert", i);
        }
        long endTime = System.nanoTime();
        LOGGER.info("L1 Cache Insert benchmark completed.");
        return endTime - startTime;
    }

    private static long benchmarkL1CacheRetrieve() {
        LOGGER.info("Starting L1 Cache Retrieve benchmark...");
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            l1Cache.get(i);
            logProgress("L1 Cache Retrieve", i);
        }
        long endTime = System.nanoTime();
        LOGGER.info("L1 Cache Retrieve benchmark completed.");
        return endTime - startTime;
    }

    private static long benchmarkL2CacheInsert() {
        LOGGER.info("Starting L2 Cache Insert benchmark...");
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            l2Cache.put(i, "Value" + i);
            logProgress("L2 Cache Insert", i);
        }
        long endTime = System.nanoTime();
        LOGGER.info("L2 Cache Insert benchmark completed.");
        return endTime - startTime;
    }

    private static long benchmarkL2CacheRetrieve() {
        LOGGER.info("Starting L2 Cache Retrieve benchmark...");
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            l2Cache.getIfPresent(i);
            logProgress("L2 Cache Retrieve", i);
        }
        long endTime = System.nanoTime();
        LOGGER.info("L2 Cache Retrieve benchmark completed.");
        return endTime - startTime;
    }

    private static long benchmarkMultilevelCacheRetrieve() throws SQLException {
        LOGGER.info("Starting Multilevel Cache Retrieve benchmark...");
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            String value = l1Cache.get(i);
            if (value == null) {
                value = l2Cache.getIfPresent(i);
                if (value == null) {
                    String sql = "SELECT * FROM test_table WHERE id = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setInt(1, i);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        value = resultSet.getString("value");
                        l2Cache.put(i, value);
                        l1Cache.put(i, value);
                    }
                    resultSet.close();
                    statement.close();
                }
                l1Cache.put(i, value);
            } else {
                l2Cache.put(i, value);
                l1Cache.put(i, value);
            }
            logProgress("Multilevel Cache Retrieve", i);
        }
        long endTime = System.nanoTime();
        LOGGER.info("Multilevel Cache Retrieve benchmark completed.");
        return endTime - startTime;
    }

    private static void logProgress(String operation, int currentElement) {
        if (currentElement % PROGRESS_INTERVAL == 0) {
            LOGGER.info(operation + " progress: " + currentElement + " / " + NUM_ELEMENTS);
        }
    }

    private static void analyzeAndLogResults(List<BenchmarkResult> results) {
        LOGGER.info("Performance Analysis:");
        for (BenchmarkResult result : results) {
            logPerformance(result.operation, result.time);
        }

        LOGGER.info("\nPerformance Improvements:");
        BenchmarkResult dbRetrieve = results.stream()
                .filter(r -> r.operation.equals("Database Retrieve"))
                .findFirst()
                .orElseThrow();

        results.stream()
                .filter(r -> !r.operation.equals("Database Retrieve") && !r.operation.equals("Database Insert"))
                .forEach(r -> logImprovement(r.operation + " vs Database Retrieve", dbRetrieve.time, r.time));
    }

    private static void logPerformance(String operation, long timeNanos) {
        double timeMinutes = timeNanos / (60.0 * 1e9);
        LOGGER.info(String.format("%s: %.4f minutes", operation, timeMinutes));
    }

    private static void logImprovement(String comparison, long baseTime, long improvedTime) {
        double improvement = (baseTime - improvedTime) / (double) baseTime * 100;
        LOGGER.info(String.format("%s: %.2f%% faster", comparison, improvement));
    }

    private static void prepareGraphData(List<BenchmarkResult> results) {
        StringBuilder graphData = new StringBuilder("Operation,Time (minutes)\n");
        for (BenchmarkResult result : results) {
            double timeMinutes = result.time / (60.0 * 1e9);
            graphData.append(String.format("%s,%.4f\n", result.operation, timeMinutes));
        }
        LOGGER.info("Graph Data:\n" + graphData.toString());
    }

    private static class BenchmarkResult {
        String operation;
        long time;

        BenchmarkResult(String operation, long time) {
            this.operation = operation;
            this.time = time;
        }
    }
}