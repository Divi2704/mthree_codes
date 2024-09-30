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

/**
 * Hello world!
 *
 */
public class AdvancedDatabaseCachingBenchmark 
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Divija@#2704";
    private static final int NUM_ELEMENTS = 100000;
    private static final int L1_CACHE_SIZE = 10000;
    private static final int L2_CACHE_SIZE = 100000;
    //private static final int L3_CACHE_SIZE = 1000000;
    private static final int L2_CACHE_DURATTION_MINUTES = 10;

    private static Connection connection;
    private static Map<Integer, String> l1Cache;
    private static Cache<Integer, String> l2Cache;

    public static void main( String[] args )
    {
        try{
            setupDatabase();
        setupCaches();
        long dbInsertTime = benchmarkDatabaseInsert();
        long dbRetrieveTime = benchMarkDatabaseRetrieve();
        long l1CacheInsertTime = benchMarkL1CacheInsert();
        long l1CacheRetrieveTime = benchMarkL1CacheRetrieve();
        long l2CacheInsertTime = benchMarkL2CacheInsert();
        long l2CacheRetrieveTime = benchMarkL2CacheRetrieve();
        long multilevelCacheRetrieveTime = benchmarkMultilevelCacheRetrieve();
        printResults(dbInsertTime, dbRetrieveTime, l1CacheInsertTime, l1CacheRetrieveTime, l2CacheInsertTime, l2CacheRetrieveTime, multilevelCacheRetrieveTime);

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    private static void setupDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE test_table");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS test_table (id INT PRIMARY KEY, value VARCHAR(255))");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void setupCaches() {
        l1Cache = new LinkedHashMap<Integer,String>(L1_CACHE_SIZE, 0.75f, true)     {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
                return size() > L1_CACHE_SIZE;
            }
        };
        l2Cache = CacheBuilder.newBuilder()
            .maximumSize(L2_CACHE_SIZE)
            .expireAfterAccess(L2_CACHE_DURATTION_MINUTES, TimeUnit.MINUTES)
            .build();   
    }
    private static long benchmarkDatabaseInsert() throws SQLException {
        long startTime = System.nanoTime();
        String sql ="INSERT INTO test_table (id, value) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            statement.setInt(1, i);
            statement.setString(2, "Value" + i);
            statement.addBatch();
            if(i %100 ==0){
                statement.executeBatch();
            }
        }
        statement.executeBatch();
        statement.close();
        long endTime = System.nanoTime();
        return endTime - startTime;        
    }
    private static long benchMarkDatabaseRetrieve() throws SQLException {
        long startTime = System.nanoTime();
        String sql = "SELECT * FROM test_table WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            statement.setInt(1, i);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
             resultSet.getString("value");
            }
            resultSet.close();
            
        }
        statement.close();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    private static long benchMarkL1CacheInsert()
{
    long startTime = System.nanoTime();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
        l1Cache.put(i, "Value" + i);
    }
    long endTime = System.nanoTime();
    return endTime - startTime;
}
private static long benchMarkL1CacheRetrieve() {
    long startTime = System.nanoTime();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
        l1Cache.get(i);
    }
    long endTime = System.nanoTime();
    return endTime - startTime;
}
private static long benchMarkL2CacheInsert() {
    long startTime = System.nanoTime();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
        l2Cache.put(i, "Value" + i);
    }
    long endTime = System.nanoTime();
    return endTime - startTime;
}
private static long benchMarkL2CacheRetrieve() {
    long startTime = System.nanoTime();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
        l2Cache.getIfPresent(i);
    }
    long endTime = System.nanoTime();
    return endTime - startTime;
}
private static long benchmarkMultilevelCacheRetrieve() throws SQLException{
    long startTime = System.nanoTime();
    for (int i = 0; i < NUM_ELEMENTS; i++) {
        String value = l1Cache.get(i);
        if(value == null){
            value = l2Cache.getIfPresent(i);
            if(value == null){
                String sql = "SELECT * FROM test_table WHERE id = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, i);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    value = resultSet.getString("value");
                    l2Cache.put(i, value);
                    l1Cache.put(i, value);
                }
                resultSet.close();
                statement.close();
                
            }
            l1Cache.put(i, value);
        }else{
            l2Cache.put(i, value);
            l1Cache.put(i, value);
        }
    }
    long endTime = System.nanoTime();
    return endTime - startTime;
}
private static void printResults(long dbInsertTime, long dbRetrieveTime,
 long l1CacheInsertTime, long l1CacheRetrieveTime, long l2CacheInsertTime,
  long l2CacheRetrieveTime, long multilevelCacheRetrieveTime){
    System.out.println("Database Insert Time: " + dbInsertTime + " nanoseconds");
    System.out.println("Database Retrieve Time: " + dbRetrieveTime + " nanoseconds");
    System.out.println("L1 Cache Insert Time: " + l1CacheInsertTime + " nanoseconds");
    System.out.println("L1 Cache Retrieve Time: " + l1CacheRetrieveTime + " nanoseconds");
    System.out.println("L2 Cache Insert Time: " + l2CacheInsertTime + " nanoseconds");
    System.out.println("L2 Cache Retrieve Time: " + l2CacheRetrieveTime + " nanoseconds");
    System.out.println("Multilevel Cache Retrieve Time: " + multilevelCacheRetrieveTime + " nanoseconds");
}
}