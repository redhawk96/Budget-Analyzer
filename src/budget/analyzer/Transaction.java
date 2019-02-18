package budget.analyzer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Transaction {
    
    private String query; 
    private int queryResult;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public Transaction() {}
    
    public int setTransaction(String category, String date, String amount) throws ClassNotFoundException, SQLException{
        
        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "INSERT INTO savings(t_category, t_date, t_amount) VALUES (?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, category);
            ps.setString(2, date);
            ps.setString(3, amount);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }

    public ResultSet getMonthlyTransactions(String month) throws ClassNotFoundException, SQLException {
        
        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT t_category AS Type, t_date AS Date, t_amount AS Amount FROM savings WHERE MONTH(t_date) = '"+month+"'";
            rs = st.executeQuery(query);
            
        }
        return rs;
    }
    
    
    public Double getYearBalance() throws SQLException, ClassNotFoundException{
        
        DB_Connect.setConnection();

        Double year_balance = null;
        Double total_savings = null;
        Double total_withdrawals = null;
        
        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            String query_01 = "SELECT SUM(t_amount)AS savings_total FROM savings WHERE t_category = 'Deposit'";
            rs = st.executeQuery(query_01);
            
            while (rs.next()) {
                total_savings = Double.parseDouble(rs.getString("savings_total"));
            }
            
            String query_02 = "SELECT SUM(t_amount)AS withdrawal_total FROM savings WHERE t_category = 'Withdrawal'";
            rs = st.executeQuery(query_02);
            
            while (rs.next()) {
                total_withdrawals = Double.parseDouble(rs.getString("withdrawal_total"));
            }
            
            year_balance = total_savings - total_withdrawals;
        }
        return year_balance;
    }
    
    public Double getMonthlyBalance(String month) throws SQLException, ClassNotFoundException{
        
        DB_Connect.setConnection();

        Double monthly_balance = null;
        Double monthly_savings = null;
        Double monthly_withdrawals = null;
        
        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            String query_01 = "SELECT SUM(t_amount)AS monthly_savings FROM savings WHERE t_category = 'Deposit' AND  MONTH(t_date) = '"+month+"'";
            rs = st.executeQuery(query_01);
            
            while (rs.next()) {
                monthly_savings = Double.parseDouble(rs.getString("monthly_savings"));
            }
            
            String query_02 = "SELECT SUM(t_amount)AS monthly_withdrawals FROM savings WHERE t_category = 'Withdrawal' AND  MONTH(t_date) = '"+month+"'";
            rs = st.executeQuery(query_02);
            
            while (rs.next()) {
                monthly_withdrawals = Double.parseDouble(rs.getString("monthly_withdrawals"));
            }
            
            monthly_balance = monthly_savings - monthly_withdrawals;
        }
        return monthly_balance;
    }
}
