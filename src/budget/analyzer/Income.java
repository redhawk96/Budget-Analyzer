package budget.analyzer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Income {

    private String query;
    private int queryResult;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int setIncome(String date, String category, String type, String descripition, Double amount) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "INSERT INTO income(i_date, i_category, i_type, i_add_details, i_amount) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, date);
            ps.setString(2, category);
            ps.setString(3, type);
            ps.setString(4, descripition);
            ps.setDouble(5, amount);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }
    
    public int updateIncome(String id, String date, String category, String type, String descripition, Double amount) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "UPDATE income SET i_date=?, i_category=?, i_type=?, i_add_details=?, i_amount=? WHERE i_id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, date);
            ps.setString(2, category);
            ps.setString(3, type);
            ps.setString(4, descripition);
            ps.setDouble(5, amount);
            ps.setString(6, id);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }
    
    public ResultSet getCurrentMonthIncomeTransactions(String month) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT i_id AS ID, i_date AS Date, i_category AS 'Category', i_type AS 'Type', i_add_details AS 'Addtional Details', i_amount AS 'Amount (Rs)' FROM income WHERE MONTH(i_date) = '" + month + "'";
            rs = st.executeQuery(query);
        }
        return rs;
    }

}
