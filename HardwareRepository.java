import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HardwareRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/hardware_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Prettyme_3";

    public List<Hardware> getAllHardware() {
        List<Hardware> hardwareList = new ArrayList<>();
        String sql = "SELECT id, brand, spec FROM hardware";

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String brand = rs.getString("brand");
                int spec = rs.getInt("spec");

                String lowerBrand = brand.toLowerCase();

                if (lowerBrand.contains("iphone")
                        || lowerBrand.contains("samsung")
                        || lowerBrand.contains("pixel")
                        || lowerBrand.contains("huawei")
                        || lowerBrand.contains("xperia")) {

                    hardwareList.add(new Phone(id, brand, spec));

                } else {
                    hardwareList.add(new Laptop(id, brand, spec));
                }
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return hardwareList;
    }

    public void addHardware(String brand, int spec) {
        String sql = "INSERT INTO hardware (brand, spec) VALUES (?, ?)";

        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, brand);
            ps.setInt(2, spec);

            ps.executeUpdate();

            System.out.println("New hardware added successfully.");

            ps.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Insert error: " + e.getMessage());
        }
    }
}