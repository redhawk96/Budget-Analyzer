package budget.analyzer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Capital {

    private String query;
    private int queryResult;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public int setCapital(String date, String category, String bank_name, String descripition, Double amount) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "INSERT INTO savings(t_date, t_category, t_type, t_add_details, t_amount) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, date);
            ps.setString(2, category);
            ps.setString(3, bank_name);
            ps.setString(4, descripition);
            ps.setDouble(5, amount);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }

    public int updateCapital(String id, String date, String category, String bank_name, String descripition, Double amount) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "UPDATE savings SET t_date=?, t_category=?, t_type=?, t_add_details=?, t_amount=? WHERE t_id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, date);
            ps.setString(2, category);
            ps.setString(3, bank_name);
            ps.setString(4, descripition);
            ps.setDouble(5, amount);
            ps.setString(6, id);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }

    public ResultSet getCurrentMonthCapitalTransactions(String month) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT t_id AS ID, t_date AS Date, t_category AS 'Type', t_type AS 'Bank Name', t_add_details AS 'Addtional Details', t_amount AS 'Amount (Rs)' FROM savings WHERE MONTH(t_date) = '" + month + "'";
            rs = st.executeQuery(query);
        }
        return rs;
    }
}
