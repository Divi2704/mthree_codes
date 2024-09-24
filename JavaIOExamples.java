import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.stream.Stream;

public class JavaIOExamples {

    public static void main(String[] args) {
        String fileName = "example.txt";
        String content = "Hello, Java I/O!";

        // Writing to a file
        writeFile(fileName, content);

        // Reading from a file using various methods
        System.out.println("1. Using FileReader and BufferedReader:");
        readWithBufferedReader(fileName);

        System.out.println("\n2. Using FileInputStream:");
        readWithFileInputStream(fileName);

        System.out.println("\n3. Using Scanner:");
        readWithScanner(fileName);

        System.out.println("\n4. Using Files.readAllLines (Java NIO):");
        readWithFilesReadAllLines(fileName);

        System.out.println("\n5. Using Files.lines (Java NIO) with Stream API:");
        readWithFilesLines(fileName);

        // Demonstrating file operations
        fileOperations(fileName);
    }

    public static void writeFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            System.out.println("Content written to file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void readWithBufferedReader(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void readWithFileInputStream(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            int content;
            while ((content = fis.read()) != -1) {
                System.out.print((char) content);
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void readWithScanner(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    public static void readWithFilesReadAllLines(String fileName) {
        try {
            Files.readAllLines(Paths.get(fileName)).forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void readWithFilesLines(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void fileOperations(String fileName) {
        File file = new File(fileName);

        System.out.println("\nFile Operations:");
        System.out.println("Exists: " + file.exists());
        System.out.println("Is File: " + file.isFile());
        System.out.println("Is Directory: " + file.isDirectory());
        System.out.println("Can Read: " + file.canRead());
        System.out.println("Can Write: " + file.canWrite());
        System.out.println("Absolute Path: " + file.getAbsolutePath());

        // Rename the file
        File newFile = new File("new_" + fileName);
        if (file.renameTo(newFile)) {
            System.out.println("File renamed successfully.");
        } else {
            System.out.println("Failed to rename the file.");
        }

        // Delete the file
        if (newFile.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
    }
}
