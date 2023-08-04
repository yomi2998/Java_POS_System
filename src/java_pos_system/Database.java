package java_pos_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// This code uses XAMPP MySQL
public class Database {

    private Connection conn;
    private ResultSet result;

    public void closeConnection() throws SQLException {
        this.conn.close();
    }

    public void runCommand(String cmd) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception ex) {
            System.out.println("err");
        }
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pos_system", "root", "");
        }
        Statement statement = this.conn.createStatement();

        if (statement.execute(cmd)) {
            // Process the result set if needed
            this.result = statement.getResultSet();
        } else {
            // No result set, e.g., for INSERT, UPDATE, or DELETE statements
            this.result = null;
        }
    }

    public List<String> getString(String col) throws SQLException {
        List<String> str = new ArrayList<>();
        while (this.result.next()) {
            str.add(this.result.getString(col));
        }
        return str;
    }

    public List<Integer> getInt(String col) throws SQLException {
        List<Integer> i = new ArrayList<>();
        while (this.result.next()) {
            i.add(this.result.getInt(col));
        }
        return i;
    }

    public List<Double> getDouble(String col) throws SQLException {
        List<Double> d = new ArrayList<>();
        while (this.result.next()) {
            d.add(this.result.getDouble(col));
        }
        return d;
    }

    public List<Boolean> getBoolean(String col) throws SQLException {
        List<Boolean> b = new ArrayList<>();
        while (this.result.next()) {
            b.add(this.result.getBoolean(col));
        }
        return b;
    }
}
