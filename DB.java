import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    private Connection conn;

    public void connect() throws Exception {
       
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

    
                conn = DriverManager.getConnection(
                    "jdbc:sqlserver://MEL_\\SQLEXPRESS;databaseName=aulajava;encrypt=false",
                    "sa",
                    "123456"
                );


        System.out.println("Conexao estabelecida com sucesso!");
    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexao fechada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        try {
            DB db = new DB();
            db.connect();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
