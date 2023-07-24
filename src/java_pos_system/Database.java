package java_pos_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private Connection conn;
    private ResultSet result;

    public void runCommand(String cmd) throws SQLException {
        if(!this.conn.isClosed())
            this.conn.close();
        this.conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pos_system", "root", "");
        Statement statement = conn.createStatement();
        this.result = statement.executeQuery(cmd);
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
