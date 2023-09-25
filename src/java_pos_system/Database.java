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

    public boolean next() {
        try {
            return this.result.next();
        } catch (Exception e) {
            return false;
        }
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
        PreparedStatement statement = this.conn.prepareStatement(cmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

        if (statement.execute(cmd)) {
            // Process the result set if needed
            this.result = statement.getResultSet();
        } else {
            // No result set, e.g., for INSERT, UPDATE, or DELETE statements
            this.result = null;
        }
    }

    public boolean hasResult() throws SQLException {
        return this.result != null && this.result.next();
    }

    public List<String> getStringList(String col) throws SQLException {
        List<String> str = new ArrayList<>();
        while (this.result.next()) {
            str.add(this.result.getString(col));
        }
        return str;
    }

    public List<Integer> getIntList(String col) throws SQLException {
        List<Integer> i = new ArrayList<>();
        while (this.result.next()) {
            i.add(this.result.getInt(col));
        }
        return i;
    }

    public List<Double> getDoubleList(String col) throws SQLException {
        List<Double> d = new ArrayList<>();
        while (this.result.next()) {
            d.add(this.result.getDouble(col));
        }
        return d;
    }

    public List<Boolean> getBooleanList(String col) throws SQLException {
        List<Boolean> b = new ArrayList<>();
        while (this.result.next()) {
            b.add(this.result.getBoolean(col));
        }
        return b;
    }

    public String getString(String col) throws SQLException {
        return this.result.getString(col);
    }

    public int getInt(String col) throws SQLException {
        return this.result.getInt(col);
    }

    public double getDouble(String col) throws SQLException {
        return this.result.getDouble(col);
    }

    public boolean getBoolean(String col) throws SQLException {
        return this.result.getBoolean(col);
    }
}
