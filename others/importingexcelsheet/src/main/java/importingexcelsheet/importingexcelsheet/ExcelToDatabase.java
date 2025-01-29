package importingexcelsheet.importingexcelsheet;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class ExcelToDatabase {
    public static void main(String[] args) {
        try {
            // Step 1: Read Excel file
            FileInputStream fis = new FileInputStream("C:\\Users\\Admin\\Desktop\\Destinations.xlsx");
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0); // Read first sheet
            fis.close();

            // Step 2: Connect to MySQL
            String url = "jdbc:mysql://localhost:3306/deliveryservice";
            String user = "root";
            String password = "bunty";
            Connection connection = DriverManager.getConnection(url, user, password);

            // Step 3: Prepare SQL statement for inserting data
            String sql = "INSERT INTO Destinations (country, capital_city, country_distance) VALUES ( ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            // Step 4: Iterate through Excel rows and save data
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row if exists

                // Example: Read three columns from the row
//           int id=(int) row.getCell(0).getNumericCellValue();

                String country = row.getCell(0).getStringCellValue();
                String capitalCity=row.getCell(1).getStringCellValue();
                String city=row.getCell(2).getStringCellValue();
                int countryDistance = (int) row.getCell(3).getNumericCellValue(); // Correctly cast the numeric value to long

                // Set values for SQL statement
//                stmt.setLong(1, id);

                stmt.setString(1, country);
                stmt.setString(2, capitalCity);
                stmt.setInt(3, countryDistance);

                // Execute the insert query
                stmt.executeUpdate();
            }

            // Step 5: Close connection
            stmt.close();
            connection.close();

            System.out.println("Excel data saved to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
