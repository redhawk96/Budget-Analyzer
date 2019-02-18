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

    public Income() {
    }

    public int setIncome(String category, String amount, String date) throws ClassNotFoundException, SQLException {

        DB_Connect.setConnection();

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "INSERT INTO income(i_category, i_date, i_amount) VALUES (?,?,?)";
            ps = con.prepareStatement(query);
            ps.setString(1, category);
            ps.setString(2, date);
            ps.setString(3, amount);
            queryResult = ps.executeUpdate();
        }
        return queryResult;
    }

    public String monthlyIncome(int month) throws SQLException, ClassNotFoundException {

        DB_Connect.setConnection();

        String month_income = "";

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT SUM(i_amount) AS monthly_income FROM income WHERE MONTH(i_date) = " + month;
            rs = st.executeQuery(query);

            while (rs.next()) {
                month_income = rs.getString("monthly_income");
            }
        }
        return month_income;
    }

    public Double yearTotal() throws SQLException, ClassNotFoundException {

        DB_Connect.setConnection();

        Double month_income = null;

        if (DB_Connect.getConnectionStatus()) {
            Connection con = DB_Connect.getConnection();
            Statement st = con.createStatement();

            query = "SELECT SUM(i_amount)AS monthly_total FROM income";
            rs = st.executeQuery(query);

            while (rs.next()) {
                month_income = Double.parseDouble(rs.getString("monthly_total"));
            }
        }
        return month_income;
    }

}
