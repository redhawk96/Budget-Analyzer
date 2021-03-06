package budget.analyzer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Expense {

    private String query;
    private int queryResult;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    public int setExpense(String date, String category, String item, String descripition, Double amount) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "INSERT INTO expenses(e_date, e_category, e_item, e_add_details, e_amount) VALUES (?,?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, date);
            ps.setString(2, category);
            ps.setString(3, item);
            ps.setString(4, descripition);
            ps.setDouble(5, amount);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }
    
    public int updateExpense(String id, String date, String category, String item, String descripition, Double amount) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "UPDATE expenses SET e_date=?, e_category=?, e_item=?, e_add_details=?, e_amount=? WHERE e_id=?";
            ps = con.prepareStatement(query);
            ps.setString(1, date);
            ps.setString(2, category);
            ps.setString(3, item);
            ps.setString(4, descripition);
            ps.setDouble(5, amount);
            ps.setString(6, id);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }

    public ResultSet getCurrentMonthExpenseTransactions(String month) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT e_id AS ID, e_date AS 'Date', e_category AS 'Type', e_item AS 'Item', e_add_details AS 'Addtional Details', e_amount AS 'Amount (Rs)' FROM expenses WHERE MONTH(e_date) = '" + month + "'";
            rs = st.executeQuery(query);
        }
        return rs;
    }

}
