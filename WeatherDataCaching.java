import java.io.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

class WeatherData implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String location;
    private final String weatherInfo;
    private final long timestamp;
    transient private String requestOrigin; // Not serialized

    public WeatherData(String location, String weatherInfo, long timestamp, String requestOrigin) {
        this.location = location;
        this.weatherInfo = weatherInfo;
        this.timestamp = timestamp;
        this.requestOrigin = requestOrigin;
    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "location='" + location + '\'' +
                ", weatherInfo='" + weatherInfo + '\'' +
                ", timestamp=" + timestamp +
                ", requestOrigin='" + requestOrigin + '\'' +
                '}';
    }
}

class WeatherCache {
    private final ConcurrentHashMap<String, WeatherData> cache = new ConcurrentHashMap<>();
    private final AtomicInteger cacheHits = new AtomicInteger(0);
    private final AtomicInteger cacheMisses = new AtomicInteger(0);

    public WeatherData getWeatherData(String location, ExecutorService executorService) throws ExecutionException, InterruptedException {
        WeatherData cachedData = cache.get(location);
        if (cachedData != null) {
            cacheHits.incrementAndGet();
            return cachedData;
        } else {
            cacheMisses.incrementAndGet();
            Future<WeatherData> futureWeather = fetchWeatherDataAsync(location, executorService);
            return futureWeather.get();
        }
    }

    private Future<WeatherData> fetchWeatherDataAsync(String location, ExecutorService executorService) {
        return executorService.submit(() -> {
            Thread.sleep(2000); // Simulate delay
            return new WeatherData(location, "Sunny, 25°C", System.currentTimeMillis(), "API Call");
        });
    }

    public void saveToCache(String location, WeatherData weatherData) {
        cache.put(location, weatherData);
    }

    public void printCacheStats() {
        System.out.println("Cache Hits: " + cacheHits.get());
        System.out.println("Cache Misses: " + cacheMisses.get());
    }


public class WeatherSystem {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        WeatherCache weatherCache = new WeatherCache();

        // Fetch weather data for multiple locations
        WeatherData data1 = weatherCache.getWeatherData("New York", executorService);
        weatherCache.saveToCache("New York", data1);
        System.out.println("Weather data: " + data1);

        WeatherData data2 = weatherCache.getWeatherData("San Francisco", executorService);
        weatherCache.saveToCache("San Francisco", data2);
        System.out.println("Weather data: " + data2);

        // Fetching from cache
        WeatherData data3 = weatherCache.getWeatherData("New York", executorService);
        System.out.println("Weather data: " + data3);

        // Display cache statistics
        weatherCache.printCacheStats();

        // Shutdown the executor
        executorService.shutdown();
    }
}
}
