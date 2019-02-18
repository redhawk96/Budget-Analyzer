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

    public Expense() {}
    
    public int setExpense(String category, String item, String date, String amount) throws ClassNotFoundException, SQLException{
        
        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "INSERT INTO expenses(e_category, e_item, e_date, e_amount) VALUES (?,?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, category);
            ps.setString(2, item);
            ps.setString(3, date);
            ps.setString(4, amount);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }
    
    public String getExpenseCategoryTotal(String category, String month) throws SQLException, ClassNotFoundException{
        
        DB_Connect.setConnection();

        String expense_total = "";
        
        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT SUM(e_amount) AS expense_total FROM expenses WHERE e_category = '"+category+"' AND MONTH(e_date) = '"+month+"'";
            rs = st.executeQuery(query);
            
            while (rs.next()) {
                expense_total = rs.getString("expense_total");
            }
        }
        return expense_total;
    }
    
    
    public Double getMonthlyTotalExpenses(String month) throws SQLException, ClassNotFoundException{
        
        DB_Connect.setConnection();

        Double expense_total = null;
        
        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT SUM(e_amount) AS expense_total FROM expenses WHERE MONTH(e_date) ="+month;
            rs = st.executeQuery(query);
            
            while (rs.next()) {
                expense_total = Double.parseDouble(rs.getString("expense_total"));
            }
        }
        return expense_total;
    }
}
