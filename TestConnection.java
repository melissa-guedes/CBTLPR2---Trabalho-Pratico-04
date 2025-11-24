import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=aulajava;encrypt=false",
                "sa",
                "123456"
            );
            System.out.println("Conexão OK!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
