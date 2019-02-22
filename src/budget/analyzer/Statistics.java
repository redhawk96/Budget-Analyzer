package budget.analyzer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Statistics {

    private String query;
    private int queryResult;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String month = null;

    public Statistics(String month) {
        this.month = month;
    }

    public ResultSet getCurrentMonthExpenseTotal() throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT e_category AS 'Expense Category', e_amount AS 'Amount' FROM expenses WHERE MONTH(e_date) = '" + month + "' GROUP BY e_category";
            rs = st.executeQuery(query);
        }
        return rs;
    }

    public ResultSet getCurrentMonthIncomeTotal() throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT `i_category` AS 'Income Category', SUM(i_amount) AS 'Amount' FROM income WHERE MONTH(i_date) = '" + month + "' GROUP BY `i_category`";
            rs = st.executeQuery(query);
        }
        return rs;
    }

    public ResultSet getCurrentMonthCapitalTotal() throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT t_category AS 'Capital Category', SUM(t_amount) AS 'Amount' FROM savings WHERE MONTH(t_date) = '" + month + "' GROUP BY t_category";
            rs = st.executeQuery(query);
        }
        return rs;
    }
    
    public ResultSet getDetailedCapitalBlance() throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT t_date AS 'Capita Transaction Date', t_category AS 'Capital Category', t_type AS 'Bank Name', t_amount AS 'Capital Value' FROM savings ORDER BY savings.t_date DESC";
            rs = st.executeQuery(query);
        }
        return rs;
    }

    public Double getCapitalBalance() throws SQLException, ClassNotFoundException {

        DB_Connect.setConnection();

        Double capital_balance = null;
        Double deposit_balance = null;
        Double withdrawal_balance = null;

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            String get_deposit_query = "SELECT SUM(t_amount) AS 'Amount' FROM savings WHERE t_category = 'Deposit'";
            rs = st.executeQuery(get_deposit_query);

            while (rs.next()) {
                deposit_balance = rs.getDouble("Amount");
            }

            String get_withdrawals_query = "SELECT SUM(t_amount) AS 'Amount' FROM savings WHERE t_category = 'Withdrawal'";
            rs = st.executeQuery(get_withdrawals_query);

            while (rs.next()) {
                withdrawal_balance = rs.getDouble("Amount");
            }
        }
        return capital_balance = deposit_balance - withdrawal_balance;
    }

    public Double getCurrentMonthIncomeBalance() throws SQLException, ClassNotFoundException {

        DB_Connect.setConnection();

        Double income_balance = null;

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT SUM(i_amount) AS 'Amount' FROM income WHERE MONTH(i_date) = '" + month + "'";
            rs = st.executeQuery(query);

            while (rs.next()) {
                income_balance = rs.getDouble("Amount");
            }
        }
        return income_balance;
    }

    public Double getCurrentMonthExpenseBalance() throws SQLException, ClassNotFoundException {

        DB_Connect.setConnection();

        Double income_balance = null;

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT SUM(e_amount) AS 'Amount' FROM expenses WHERE MONTH(e_date) = '" + month + "'";
            rs = st.executeQuery(query);

            while (rs.next()) {
                income_balance = rs.getDouble("Amount");
            }
        }
        return income_balance;
    }

    public Double getCurrentMonthBalance() throws SQLException, ClassNotFoundException {

        Double month_bal = getCurrentMonthIncomeBalance() - getCurrentMonthExpenseBalance();

        return month_bal;
    }

//    public static void main(String args[]) throws SQLException, ClassNotFoundException {
//
//        try {
//            Statistics s = new Statistics();
//            System.out.println(s.getCurrentMonthIncomeBalance("02"));
//            System.out.println(s.getCurrentMonthExpenseBalance("02"));
//            System.out.println(s.getCurrentMonthCurrentMonthBalance("02"));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
}
