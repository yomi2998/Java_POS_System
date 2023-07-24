package java_pos_system;
import java.sql.*;
import java.util.List;
import java.text.DecimalFormat;
public class IDGenerator {
    private final String type;
    IDGenerator(String type) {
        this.type = type;
    }
    public String getID(String targetCol) throws SQLException {
        Database data = new Database();
        data.runCommand("SELECT * FROM " + type);
        List<String> out = data.getString(targetCol);
        String lastID = "";
        for(String s : out) {
            lastID = s;
        }
        int newID = Integer.parseInt(lastID.substring(4)) + 1;
        DecimalFormat format = new DecimalFormat("000000");
        String formedID = this.type.substring(0,4) + format.format(newID);
        return formedID;
    }
}
