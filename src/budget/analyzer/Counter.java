package budget.analyzer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Counter {

    private String query;
    private int queryResult;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public void setMonthFirst(int year) throws ClassNotFoundException, SQLException{
        
        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "INSERT INTO system(year) VALUES (?)";
            ps = con.prepareStatement(query);
            ps.setInt(1, year);
            queryResult = ps.executeUpdate();
        }
    }
    
    
    public boolean getMonthFirst(int year) throws SQLException, ClassNotFoundException {

        DB_Connect.setConnection();

        String hasValue = "";
        boolean result = false;

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT year FROM system WHERE year ="+year;
            rs = st.executeQuery(query);

            while (rs.next()) {
                hasValue = rs.getString("year");
            }
           

            if (hasValue.equals("")) {
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }
    
}
