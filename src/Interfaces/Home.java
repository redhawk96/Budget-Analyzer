package Interfaces;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import budget.analyzer.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Redhw
 */
public class Home extends javax.swing.JFrame {

    private String currentDate;
    private String currentMonth;
    private String currentYear;
    private ResultSet rs;
    private Double month_balance;
    private Double month_expenses;
    private Double month_income;
    private DecimalFormat doubleFormat = new DecimalFormat("#.00");

    public Home() {
        initComponents();

        home_panel.setVisible(true);
        income_panel.setVisible(false);
        savings_panel.setVisible(false);
        excenses_panel.setVisible(false);

        try {
            getDate();
            getMonth();
            getYear();
            setNewMonth();
            yearlyStatistics();
            monthlyExpenses();
            monthlyStatistics();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    void setColor(JPanel panel) {
        panel.setBackground(new Color(12, 135, 235));
    }

    void resetColor(JPanel panel) {
        panel.setBackground(new Color(11, 120, 209));
    }

    void getDate() throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = dateFormat.format(date);
    }

    void getYear() throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        currentYear = dateFormat.format(date);
    }

    void getMonth() throws ParseException {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        currentMonth = dateFormat.format(date);
    }

    void setNewMonth() throws ClassNotFoundException, SQLException {

        Counter counter = new Counter();

        boolean hasMonth = counter.getMonthFirst(Integer.parseInt(currentYear));

        if (hasMonth == false) {
            Income i = new Income();
            Expense e = new Expense();
            Transaction t = new Transaction();

            counter.setMonthFirst(Integer.parseInt(currentYear));

            for (int r = 01; r <= 12; r++) {
                i.setIncome("MS", "0", "2019-" + r + "-01");
                e.setExpense("MS", "Item", "2019-" + r + "-01", "0");
                t.setTransaction("Deposit", "2019-" + r + "-01", "0");
                t.setTransaction("Withdrawal", "2019-" + r + "-01", "0");
            }
        }
    }

    void monthlyIncome() throws SQLException, ClassNotFoundException {
        Income income = new Income();

        jan_month_income.setText(income.monthlyIncome(1));
        feb_month_income.setText(income.monthlyIncome(2));
        mar_month_income.setText(income.monthlyIncome(3));
        apr_month_income.setText(income.monthlyIncome(4));
        may_month_income.setText(income.monthlyIncome(5));
        jun_month_income.setText(income.monthlyIncome(6));
        jul_month_income.setText(income.monthlyIncome(7));
        aug_month_income.setText(income.monthlyIncome(8));
        sep_month_income.setText(income.monthlyIncome(9));
        oct_month_income.setText(income.monthlyIncome(10));
        nov_month_income.setText(income.monthlyIncome(11));
        dec_month_income.setText(income.monthlyIncome(12));

        String monthly_income = income.yearTotal().toString();
        current_year_income.setText(monthly_income + "0");
    }

    void monthlyTransactions() throws ClassNotFoundException, SQLException {

        Transaction transaction = new Transaction();
        Income income_balance = new Income();
        
        rs = transaction.getMonthlyTransactions(currentMonth);
        transaction_summary.setModel(DbUtils.resultSetToTableModel(rs));
        transaction_balance.setText(transaction.getYearBalance() + "0".toString());
    }

    void monthlyExpenses() throws SQLException, ClassNotFoundException {
        Expense expense = new Expense();
        Income income_balance = new Income();
        
        e_bills.setText(expense.getExpenseCategoryTotal("Bills", currentMonth));
        e_tax.setText(expense.getExpenseCategoryTotal("Tax", currentMonth));
        e_grocery.setText(expense.getExpenseCategoryTotal("Grocery", currentMonth));
        e_dry_food.setText(expense.getExpenseCategoryTotal("Dry Food", currentMonth));
        e_medication.setText(expense.getExpenseCategoryTotal("Medication", currentMonth));
        e_transport.setText(expense.getExpenseCategoryTotal("Transport", currentMonth));
        e_emergency.setText(expense.getExpenseCategoryTotal("Emergency", currentMonth));

        month_income = Double.parseDouble(income_balance.monthlyIncome(Integer.parseInt(currentMonth)));
        month_expenses = expense.getMonthlyTotalExpenses(currentMonth);
        month_balance = month_income-month_expenses;
        
        current_month_income.setText(month_income.toString());
        current_month_expenses.setText(month_expenses.toString());
        current_month_balance.setText(month_balance.toString());
    }

    void yearlyStatistics() throws SQLException, ClassNotFoundException {

        Income monthlyIncomeStats = new Income();
        Expense monthlyExpenseStats = new Expense();
        Transaction monthlyTransactions = new Transaction();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("1"), "Savings", "JAN");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(1)), "Income", "JAN");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("1"), "Expenses", "JAN");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("2"), "Savings", "FEB");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(2)), "Income", "FEB");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("2"), "Expenses", "FEB");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("3"), "Savings", "MAR");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(3)), "Income", "MAR");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("3"), "Expenses", "MAR");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("4"), "Savings", "APR");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(4)), "Income", "APR");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("4"), "Expenses", "APR");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("5"), "Savings", "MAY");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(5)), "Income", "MAY");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("5"), "Expenses", "MAY");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("6"), "Savings", "JUN");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(6)), "Income", "JUN");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("6"), "Expenses", "JUN");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("7"), "Savings", "JUL");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(7)), "Income", "JUL");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("7"), "Expenses", "JUL");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("8"), "Savings", "AUG");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(8)), "Income", "AUG");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("8"), "Expenses", "AUG");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("9"), "Savings", "SEP");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(9)), "Income", "SEP");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("9"), "Expenses", "SEP");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("10"), "Savings", "OCT");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(10)), "Income", "OCT");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("10"), "Expenses", "OCT");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("11"), "Savings", "NOV");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(11)), "Income", "NOV");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("11"), "Expenses", "NOV");

        // Population in 2005  
        dataset.addValue(monthlyTransactions.getMonthlyBalance("12"), "Savings", "DEC");
        dataset.addValue(Integer.parseInt(monthlyIncomeStats.monthlyIncome(12)), "Income", "DEC");
        dataset.addValue(monthlyExpenseStats.getMonthlyTotalExpenses("12"), "Expenses", "DEC");

        JFreeChart chart = ChartFactory.createBarChart("", "", "", dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.BLACK);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);

        ChartPanel chartPanel = new ChartPanel(chart);

        budget_summary_chart.removeAll();
        budget_summary_chart.add(chartPanel);
        budget_summary_chart.updateUI();

    }
    
    void monthlyStatistics() throws SQLException, ClassNotFoundException{
        
        Transaction transaction_stat = new Transaction();
        
        current_month_savings_stat.setText("Rs. "+transaction_stat.getMonthlyBalance(currentMonth).toString());
        current_month_balance_stat.setText("Rs. "+month_balance.toString());
        current_month_income_stat.setText("Rs. "+month_income.toString());
        current_month_expense_stat.setText("Rs. "+month_expenses.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        income_category = new javax.swing.ButtonGroup();
        transaction_category = new javax.swing.ButtonGroup();
        sidepane = new javax.swing.JPanel();
        btn_savings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btn_home = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btn_income = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btn_expenses = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        contentpane = new javax.swing.JPanel();
        home_panel = new javax.swing.JPanel();
        budget_summary_chart = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        balance_btn = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        current_month_balance_stat = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        current_month_income_stat = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        current_month_expense_stat = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        current_month_savings_stat = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        savings_panel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        t_savings = new javax.swing.JRadioButton();
        t_withdrawals = new javax.swing.JRadioButton();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        transaction_amount = new javax.swing.JTextField();
        transaction_date = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel50 = new javax.swing.JLabel();
        transaction_balance = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        transaction_summary = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        income_panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        main_income = new javax.swing.JRadioButton();
        other_income = new javax.swing.JRadioButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        income_amount = new javax.swing.JTextField();
        income_date = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        current_year_income = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        feb_month_income = new javax.swing.JLabel();
        jun_month_income = new javax.swing.JLabel();
        jan_month_income = new javax.swing.JLabel();
        oct_month_income = new javax.swing.JLabel();
        mar_month_income = new javax.swing.JLabel();
        jul_month_income = new javax.swing.JLabel();
        aug_month_income = new javax.swing.JLabel();
        sep_month_income = new javax.swing.JLabel();
        apr_month_income = new javax.swing.JLabel();
        nov_month_income = new javax.swing.JLabel();
        may_month_income = new javax.swing.JLabel();
        dec_month_income = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jButton1 = new javax.swing.JButton();
        excenses_panel = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        expense_amount = new javax.swing.JTextField();
        expense_date = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel55 = new javax.swing.JLabel();
        current_month_balance = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        e_bills = new javax.swing.JLabel();
        e_tax = new javax.swing.JLabel();
        e_dry_food = new javax.swing.JLabel();
        e_medication = new javax.swing.JLabel();
        e_grocery = new javax.swing.JLabel();
        e_transport = new javax.swing.JLabel();
        e_emergency = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        current_month_income = new javax.swing.JLabel();
        current_month_expenses = new javax.swing.JLabel();
        e_emergency1 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jButton4 = new javax.swing.JButton();
        expense_category = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        expense_item = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Budget Analyzer");
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1185, 725));
        setMinimumSize(new java.awt.Dimension(1185, 725));
        setPreferredSize(new java.awt.Dimension(1185, 725));
        setResizable(false);

        sidepane.setBackground(new java.awt.Color(51, 51, 51));
        sidepane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_savings.setBackground(new java.awt.Color(11, 120, 209));
        btn_savings.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_savingsMouseClicked(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Money_Box_25px.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("SAVINGS");

        javax.swing.GroupLayout btn_savingsLayout = new javax.swing.GroupLayout(btn_savings);
        btn_savings.setLayout(btn_savingsLayout);
        btn_savingsLayout.setHorizontalGroup(
            btn_savingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_savingsLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(0, 151, Short.MAX_VALUE))
        );
        btn_savingsLayout.setVerticalGroup(
            btn_savingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_savingsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        sidepane.add(btn_savings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 280, 60));

        btn_home.setBackground(new java.awt.Color(12, 135, 235));
        btn_home.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_homeMouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Home_25px.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("HOME");

        javax.swing.GroupLayout btn_homeLayout = new javax.swing.GroupLayout(btn_home);
        btn_home.setLayout(btn_homeLayout);
        btn_homeLayout.setHorizontalGroup(
            btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_homeLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(0, 169, Short.MAX_VALUE))
        );
        btn_homeLayout.setVerticalGroup(
            btn_homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_homeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        sidepane.add(btn_home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 280, 60));

        btn_income.setBackground(new java.awt.Color(11, 120, 209));
        btn_income.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_incomeMouseClicked(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Money_25px.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("INCOME");

        javax.swing.GroupLayout btn_incomeLayout = new javax.swing.GroupLayout(btn_income);
        btn_income.setLayout(btn_incomeLayout);
        btn_incomeLayout.setHorizontalGroup(
            btn_incomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_incomeLayout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addGap(0, 156, Short.MAX_VALUE))
        );
        btn_incomeLayout.setVerticalGroup(
            btn_incomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_incomeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        sidepane.add(btn_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 280, -1));

        btn_expenses.setBackground(new java.awt.Color(11, 120, 209));
        btn_expenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_expensesMouseClicked(evt);
            }
        });

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Card_Payment_25px.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("EXPENSE");

        javax.swing.GroupLayout btn_expensesLayout = new javax.swing.GroupLayout(btn_expenses);
        btn_expenses.setLayout(btn_expensesLayout);
        btn_expensesLayout.setHorizontalGroup(
            btn_expensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btn_expensesLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addGap(0, 153, Short.MAX_VALUE))
        );
        btn_expensesLayout.setVerticalGroup(
            btn_expensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(btn_expensesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel8)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        sidepane.add(btn_expenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 280, -1));

        jLabel9.setFont(new java.awt.Font("Lato Semibold", 1, 28)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("BA");
        sidepane.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));
        sidepane.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 64, 140, 10));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Ledger_48px_1.png"))); // NOI18N
        sidepane.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 50, 40));

        contentpane.setBackground(new java.awt.Color(255, 255, 255));
        contentpane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        home_panel.setBackground(new java.awt.Color(255, 255, 255));
        home_panel.setMaximumSize(new java.awt.Dimension(905, 714));
        home_panel.setMinimumSize(new java.awt.Dimension(905, 714));
        home_panel.setPreferredSize(new java.awt.Dimension(905, 714));
        home_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        budget_summary_chart.setLayout(new javax.swing.BoxLayout(budget_summary_chart, javax.swing.BoxLayout.LINE_AXIS));
        home_panel.add(budget_summary_chart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 350));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        balance_btn.setBackground(new java.awt.Color(255, 255, 255));
        balance_btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 152, 0), 3));

        jLabel60.setFont(new java.awt.Font("Lato", 0, 36)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(102, 102, 102));
        jLabel60.setText("BALANCE");

        current_month_balance_stat.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_month_balance_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_balance_stat.setText("Rs. 0.00");

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Scales_96px.png"))); // NOI18N

        javax.swing.GroupLayout balance_btnLayout = new javax.swing.GroupLayout(balance_btn);
        balance_btn.setLayout(balance_btnLayout);
        balance_btnLayout.setHorizontalGroup(
            balance_btnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(balance_btnLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(balance_btnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(current_month_balance_stat)
                    .addComponent(jLabel60))
                .addGap(35, 35, 35))
        );
        balance_btnLayout.setVerticalGroup(
            balance_btnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(balance_btnLayout.createSequentialGroup()
                .addGroup(balance_btnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(balance_btnLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel60)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(current_month_balance_stat))
                    .addGroup(balance_btnLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 150, 136), 3));

        jLabel40.setFont(new java.awt.Font("Lato", 0, 36)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 102, 102));
        jLabel40.setText("INCOME");

        current_month_income_stat.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_month_income_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_income_stat.setText("Rs. 0.00");

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Refund_96px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel63)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel40)
                    .addComponent(current_month_income_stat))
                .addGap(36, 36, 36))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel40)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(current_month_income_stat)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(244, 67, 54), 3));

        current_month_expense_stat.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_month_expense_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_expense_stat.setText("Rs. 0.00");

        jLabel45.setFont(new java.awt.Font("Lato", 0, 36)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText("EXPENSES");

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Receipt_96px.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel64)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(current_month_expense_stat, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(31, 31, 31))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(current_month_expense_stat)))
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(33, 150, 243), 3));
        jPanel8.setForeground(new java.awt.Color(102, 102, 102));

        current_month_savings_stat.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_month_savings_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_savings_stat.setText("Rs. 0.00");

        jLabel39.setFont(new java.awt.Font("Lato", 0, 36)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText("SAVINGS");

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Profit_96px_3.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel62)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(current_month_savings_stat, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(36, 36, 36))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(current_month_savings_stat)
                .addGap(22, 22, 22))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(balance_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(balance_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(98, Short.MAX_VALUE))
        );

        home_panel.add(jPanel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 940, 410));

        contentpane.add(home_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        savings_panel.setBackground(new java.awt.Color(255, 255, 255));
        savings_panel.setMaximumSize(new java.awt.Dimension(905, 714));
        savings_panel.setMinimumSize(new java.awt.Dimension(905, 714));
        savings_panel.setPreferredSize(new java.awt.Dimension(905, 714));
        savings_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(102, 102, 102));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Money_Box_64px.png"))); // NOI18N

        jLabel59.setFont(new java.awt.Font("Lato", 0, 24)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(255, 255, 255));
        jLabel59.setText("SAVINGS");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(jLabel58)
                .addGap(18, 18, 18)
                .addComponent(jLabel59)
                .addContainerGap(407, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel58))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jLabel59)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        savings_panel.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 120));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel46.setText("Transaction Method");
        jPanel10.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 122, -1, -1));

        t_savings.setBackground(new java.awt.Color(255, 255, 255));
        transaction_category.add(t_savings);
        t_savings.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        t_savings.setText("Savings");
        t_savings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_savingsActionPerformed(evt);
            }
        });
        jPanel10.add(t_savings, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, -1, -1));

        t_withdrawals.setBackground(new java.awt.Color(255, 255, 255));
        transaction_category.add(t_withdrawals);
        t_withdrawals.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        t_withdrawals.setText("Withdrawals");
        t_withdrawals.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                t_withdrawalsActionPerformed(evt);
            }
        });
        jPanel10.add(t_withdrawals, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 120, -1, -1));

        jLabel47.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel47.setText("Amount (Rs.)");
        jPanel10.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 276, -1, 28));

        jLabel48.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel48.setText("Transaction Date");
        jPanel10.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 200, -1, -1));

        transaction_amount.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jPanel10.add(transaction_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 280, 263, -1));

        transaction_date.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jPanel10.add(transaction_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 263, -1));

        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel49.setText("TOTAL (Rs.)");
        jPanel11.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, -1, 28));
        jPanel11.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 501, 321, 10));

        jLabel50.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel50.setText("Monthly Transactions Summary");
        jPanel11.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 28));

        transaction_balance.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        transaction_balance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        transaction_balance.setText("0.00");
        jPanel11.add(transaction_balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 530, 125, 28));
        jPanel11.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 558, 110, 10));
        jPanel11.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 560, 110, 10));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        transaction_summary.setBackground(new java.awt.Color(240, 240, 240));
        transaction_summary.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        transaction_summary.setFont(new java.awt.Font("Lato", 0, 14)); // NOI18N
        transaction_summary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        transaction_summary.setGridColor(new java.awt.Color(240, 240, 240));
        transaction_summary.setIntercellSpacing(new java.awt.Dimension(5, 5));
        transaction_summary.setRowHeight(28);
        transaction_summary.setRowSelectionAllowed(false);
        transaction_summary.setSelectionForeground(new java.awt.Color(240, 240, 240));
        transaction_summary.setSurrendersFocusOnKeystroke(true);
        jScrollPane1.setViewportView(transaction_summary);

        jPanel11.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 300, 440));

        jPanel10.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, -1, 640));

        jButton3.setBackground(new java.awt.Color(33, 150, 243));
        jButton3.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jButton3.setText("Submit");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton3MouseEntered(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, 112, 41));

        savings_panel.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 940, 640));

        contentpane.add(savings_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        income_panel.setBackground(new java.awt.Color(255, 255, 255));
        income_panel.setMaximumSize(new java.awt.Dimension(905, 714));
        income_panel.setMinimumSize(new java.awt.Dimension(905, 714));
        income_panel.setOpaque(false);
        income_panel.setPreferredSize(new java.awt.Dimension(905, 714));
        income_panel.setRequestFocusEnabled(false);
        income_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        jPanel17.setBackground(new java.awt.Color(33, 150, 243));
        jPanel17.setOpaque(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Money_64px.png"))); // NOI18N

        jLabel42.setFont(new java.awt.Font("Lato", 0, 24)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("SAVINGS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(346, Short.MAX_VALUE)
                .addComponent(jLabel41)
                .addGap(18, 18, 18)
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel41)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        income_panel.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 120));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(899, 714));
        jPanel3.setPreferredSize(new java.awt.Dimension(899, 714));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel12.setText("Income Method");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 122, -1, -1));

        main_income.setBackground(new java.awt.Color(255, 255, 255));
        income_category.add(main_income);
        main_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        main_income.setText("Main Income");
        main_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                main_incomeActionPerformed(evt);
            }
        });
        jPanel3.add(main_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 118, -1, -1));

        other_income.setBackground(new java.awt.Color(255, 255, 255));
        income_category.add(other_income);
        other_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        other_income.setText("Other");
        other_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                other_incomeActionPerformed(evt);
            }
        });
        jPanel3.add(other_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(436, 118, -1, -1));

        jLabel13.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel13.setText("Amount (Rs.)");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 276, -1, 28));

        jLabel14.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel14.setText("Income Date");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 200, -1, -1));

        income_amount.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jPanel3.add(income_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 276, 263, -1));

        income_date.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jPanel3.add(income_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(246, 197, 263, -1));

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel15.setText("TOTAL (Rs.)");
        jPanel4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, -1, 28));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 521, 321, 10));

        jLabel17.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel17.setText("Monthly Income Summary");
        jPanel4.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 11, -1, 28));

        current_year_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_year_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_year_income.setText("0.00");
        jPanel4.add(current_year_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, 125, 28));

        jLabel18.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel18.setText("January");
        jPanel4.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 58, -1, 28));

        jLabel19.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel19.setText("Feburary");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 98, -1, 28));

        jLabel20.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel20.setText("March");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 133, -1, 28));

        jLabel21.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel21.setText("May");
        jPanel4.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 213, -1, 28));

        jLabel22.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel22.setText("April");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 173, -1, 28));

        jLabel23.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel23.setText("July");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 293, -1, 28));

        jLabel24.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel24.setText("June");
        jPanel4.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 253, -1, 28));

        jLabel25.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel25.setText("October");
        jPanel4.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 410, -1, 28));

        jLabel26.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel26.setText("August");
        jPanel4.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 332, -1, 28));

        jLabel27.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel27.setText("December");
        jPanel4.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 488, -1, 28));

        jLabel28.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel28.setText("November");
        jPanel4.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(33, 449, -1, 28));

        jLabel29.setFont(new java.awt.Font("Lato", 0, 15)); // NOI18N
        jLabel29.setText("September");
        jPanel4.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 371, -1, 28));

        feb_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        feb_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        feb_month_income.setText("0.00");
        feb_month_income.setToolTipText("");
        jPanel4.add(feb_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 97, 125, 28));

        jun_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jun_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jun_month_income.setText("0.00");
        jun_month_income.setToolTipText("");
        jPanel4.add(jun_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 252, 125, 28));

        jan_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jan_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jan_month_income.setText("0.00");
        jan_month_income.setToolTipText("");
        jPanel4.add(jan_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 57, 125, 28));

        oct_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        oct_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        oct_month_income.setText("0.00");
        oct_month_income.setToolTipText("");
        jPanel4.add(oct_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 410, 125, 28));

        mar_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        mar_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mar_month_income.setText("0.00");
        mar_month_income.setToolTipText("");
        jPanel4.add(mar_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 132, 125, 28));

        jul_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jul_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jul_month_income.setText("0.00");
        jul_month_income.setToolTipText("");
        jPanel4.add(jul_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 292, 125, 28));

        aug_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        aug_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        aug_month_income.setText("0.00");
        aug_month_income.setToolTipText("");
        jPanel4.add(aug_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 332, 125, 28));

        sep_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        sep_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sep_month_income.setText("0.00");
        sep_month_income.setToolTipText("");
        jPanel4.add(sep_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 371, 125, 28));

        apr_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        apr_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        apr_month_income.setText("0.00");
        apr_month_income.setToolTipText("");
        jPanel4.add(apr_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 172, 125, 28));

        nov_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        nov_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nov_month_income.setText("0.00");
        nov_month_income.setToolTipText("");
        jPanel4.add(nov_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 449, 125, 28));

        may_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        may_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        may_month_income.setText("0.00");
        may_month_income.setToolTipText("");
        jPanel4.add(may_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 212, 125, 28));

        dec_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        dec_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dec_month_income.setText("0.00");
        dec_month_income.setToolTipText("");
        jPanel4.add(dec_month_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 488, 125, 28));
        jPanel4.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 568, 100, 10));
        jPanel4.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(188, 570, 100, 10));

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 310, 630));

        jButton1.setBackground(new java.awt.Color(33, 150, 243));
        jButton1.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jButton1.setText("Submit");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(397, 361, 112, 41));

        income_panel.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 900, 640));

        contentpane.add(income_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        excenses_panel.setBackground(new java.awt.Color(255, 255, 255));
        excenses_panel.setMaximumSize(new java.awt.Dimension(905, 714));
        excenses_panel.setMinimumSize(new java.awt.Dimension(905, 714));
        excenses_panel.setPreferredSize(new java.awt.Dimension(905, 714));
        excenses_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(102, 102, 102));

        jLabel43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Card_Payment_50px.png"))); // NOI18N

        jLabel44.setFont(new java.awt.Font("Lato", 0, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("EXPENSES");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(355, 355, 355)
                .addComponent(jLabel43)
                .addGap(18, 18, 18)
                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(392, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        excenses_panel.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 120));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel51.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel51.setText("Expense Type");

        jLabel52.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel52.setText("Amount (Rs.)");

        jLabel53.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel53.setText("Transaction Date");

        expense_amount.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N

        expense_date.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N

        jLabel54.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel54.setText("Balance (Rs.)");

        jLabel55.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel55.setText("Monthly Expense Summary");

        current_month_balance.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_month_balance.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_month_balance.setText("0.00");

        jLabel11.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel11.setText("Bills");

        jLabel16.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel16.setText("Tax");

        jLabel30.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel30.setText("Grocery");

        jLabel31.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel31.setText("Dry Food");

        jLabel32.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel32.setText("Medication");

        jLabel33.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel33.setText("Transport");

        jLabel34.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel34.setText("Emergency");

        e_bills.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        e_bills.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_bills.setText("0.00");

        e_tax.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        e_tax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_tax.setText("0.00");

        e_dry_food.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        e_dry_food.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_dry_food.setText("0.00");

        e_medication.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        e_medication.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_medication.setText("0.00");

        e_grocery.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        e_grocery.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_grocery.setText("0.00");

        e_transport.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        e_transport.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_transport.setText("0.00");

        e_emergency.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        e_emergency.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_emergency.setText("0.00");

        jLabel35.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel35.setText("Expenses");

        jLabel36.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel36.setText("Income");

        current_month_income.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_month_income.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_month_income.setText("0.00");

        current_month_expenses.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        current_month_expenses.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_month_expenses.setText("0.00");

        e_emergency1.setFont(new java.awt.Font("Lato", 0, 24)); // NOI18N
        e_emergency1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        e_emergency1.setText("-");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(jLabel34)
                    .addComponent(jLabel33)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(e_bills, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e_tax, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e_dry_food, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e_medication, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e_grocery, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e_transport, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e_emergency, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(195, 195, 195)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel55))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addGap(97, 97, 97))
                                    .addGroup(jPanel14Layout.createSequentialGroup()
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(e_emergency1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(current_month_expenses, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(current_month_income, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jLabel54)
                                .addGap(52, 52, 52)
                                .addComponent(current_month_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(e_bills))
                .addGap(29, 29, 29)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(e_tax))
                .addGap(26, 26, 26)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(e_grocery))
                .addGap(30, 30, 30)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(e_dry_food))
                .addGap(36, 36, 36)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(e_medication))
                .addGap(33, 33, 33)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(e_transport))
                .addGap(32, 32, 32)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(e_emergency))
                .addGap(18, 18, 18)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(current_month_income, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(current_month_expenses, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(e_emergency1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(current_month_balance, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jButton4.setBackground(new java.awt.Color(33, 150, 243));
        jButton4.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jButton4.setText("Submit");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        expense_category.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        expense_category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bills", "Tax", "Grocery", "Dry Food", "Medication", "Transport", "Emergency" }));
        expense_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expense_categoryActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N
        jLabel56.setText("Item Name");

        expense_item.setFont(new java.awt.Font("Lato", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel51)
                    .addComponent(jLabel53)
                    .addComponent(jLabel52)
                    .addComponent(jLabel56))
                .addGap(48, 48, 48)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(expense_amount, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(expense_date, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(expense_item, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(expense_category, javax.swing.GroupLayout.Alignment.LEADING, 0, 260, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(119, 119, 119)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(expense_category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel56)
                    .addComponent(expense_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel53)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(expense_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(expense_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        excenses_panel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 940, 640));

        contentpane.add(excenses_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sidepane, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sidepane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(contentpane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_homeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_homeMouseClicked
        setColor(btn_home);
        resetColor(btn_savings);
        resetColor(btn_income);
        resetColor(btn_expenses);

        home_panel.setVisible(true);
        income_panel.setVisible(false);
        savings_panel.setVisible(false);
        excenses_panel.setVisible(false);

        try {
            yearlyStatistics();
            monthlyStatistics();
        } catch (Exception e) {
            Error_Message message = new Error_Message();
            message.setMessage("Error :" + e.getMessage());
            message.setVisible(true);
        }
    }//GEN-LAST:event_btn_homeMouseClicked

    private void btn_savingsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_savingsMouseClicked
        setColor(btn_savings);
        resetColor(btn_home);
        resetColor(btn_income);
        resetColor(btn_expenses);

        home_panel.setVisible(false);
        savings_panel.setVisible(true);
        income_panel.setVisible(false);
        excenses_panel.setVisible(false);

        try {
            monthlyTransactions();
        } catch (Exception e) {
            Error_Message message = new Error_Message();
            message.setMessage("Error :" + e.getMessage());
            message.setVisible(true);
        }

        transaction_date.setText(currentDate);
    }//GEN-LAST:event_btn_savingsMouseClicked

    private void btn_incomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_incomeMouseClicked
        setColor(btn_income);
        resetColor(btn_home);
        resetColor(btn_savings);
        resetColor(btn_expenses);

        home_panel.setVisible(false);
        savings_panel.setVisible(false);
        income_panel.setVisible(true);
        excenses_panel.setVisible(false);

        try {
            monthlyIncome();
        } catch (Exception e) {
            Error_Message message = new Error_Message();
            message.setMessage("Error :" + e.getMessage());
            message.setVisible(true);
        }

        income_date.setText(currentDate);
    }//GEN-LAST:event_btn_incomeMouseClicked

    private void btn_expensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_expensesMouseClicked
        setColor(btn_expenses);
        resetColor(btn_home);
        resetColor(btn_savings);
        resetColor(btn_income);

        home_panel.setVisible(false);
        income_panel.setVisible(false);
        savings_panel.setVisible(false);
        excenses_panel.setVisible(true);

        try {
            monthlyExpenses();
        } catch (Exception e) {
            Error_Message message = new Error_Message();
            message.setMessage("Error :" + e.getMessage());
            message.setVisible(true);
        }

        expense_date.setText(currentDate);
    }//GEN-LAST:event_btn_expensesMouseClicked

    private void other_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_other_incomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_other_incomeActionPerformed

    private void main_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_main_incomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_main_incomeActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void t_savingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_savingsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_savingsActionPerformed

    private void t_withdrawalsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_t_withdrawalsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_t_withdrawalsActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        t_savings.setActionCommand("Deposit");
        t_withdrawals.setActionCommand("Withdrawal");

        String t_category = transaction_category.getSelection().getActionCommand();
        String t_date = transaction_date.getText();
        String t_amount = transaction_amount.getText();

        Transaction transaction = new Transaction();

        try {
            int result = transaction.setTransaction(t_category, t_date, t_amount);

            if (result == 1) {
                Success_Message message = new Success_Message();
                message.setMessage("Transaction has been successfully added !");
                message.setVisible(true);
            } else {
                Error_Message message = new Error_Message();
                message.setMessage("We've encountered a problem in recording the transaction. Please contact system administrator");
                message.setVisible(true);
            }

            monthlyTransactions();

        } catch (Exception e) {
            Error_Message message = new Error_Message();
            message.setMessage("Error :" + e.getMessage());
            message.setVisible(true);
        } finally {
            transaction_category.clearSelection();
            transaction_date.setText(currentDate);
            transaction_amount.setText(null);
        }
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked

        main_income.setActionCommand("Main");
        other_income.setActionCommand("Other");

        String i_category = income_category.getSelection().getActionCommand();
        String i_date = income_date.getText();
        String i_amount = income_amount.getText();

        Income new_income = new Income();

        try {
            int result = new_income.setIncome(i_category, i_amount, i_date);

            if (result == 1) {
                Success_Message message = new Success_Message();
                message.setMessage("Income has been successfully added !");
                message.setVisible(true);
            } else {
                Error_Message message = new Error_Message();
                message.setMessage("We've encountered a problem in recording the incone. Please contact system administrator");
                message.setVisible(true);
            }

            monthlyIncome();

        } catch (Exception e) {
            Error_Message message = new Error_Message();
            message.setMessage("Error :" + e.getMessage());
            message.setVisible(true);
        } finally {
            income_category.clearSelection();
            income_date.setText(currentDate);
            income_amount.setText(null);
        }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseEntered

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked

        String e_category = expense_category.getSelectedItem().toString();
        String e_item = expense_item.getText();
        String e_date = expense_date.getText();
        String e_amount = expense_amount.getText();

        Expense expense = new Expense();

        try {
            int result = expense.setExpense(e_category, e_item, e_date, e_amount);

            if (result == 1) {
                Success_Message message = new Success_Message();
                message.setMessage("Expense has been successfully added !");
                message.setVisible(true);
            } else {
                Error_Message message = new Error_Message();
                message.setMessage("We've encountered a problem in recording the expense. Please contact system administrator");
                message.setVisible(true);
            }

            monthlyExpenses();

        } catch (Exception e) {
            Error_Message message = new Error_Message();
            message.setMessage("Error :" + e.getMessage());
            message.setVisible(true);
        } finally {

            expense_date.setText(currentDate);
            expense_item.setText(null);
            expense_amount.setText(null);
        }
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseEntered

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void expense_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expense_categoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_expense_categoryActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel apr_month_income;
    private javax.swing.JLabel aug_month_income;
    private javax.swing.JPanel balance_btn;
    private javax.swing.JPanel btn_expenses;
    private javax.swing.JPanel btn_home;
    private javax.swing.JPanel btn_income;
    private javax.swing.JPanel btn_savings;
    private javax.swing.JPanel budget_summary_chart;
    private javax.swing.JPanel contentpane;
    private javax.swing.JLabel current_month_balance;
    private javax.swing.JLabel current_month_balance_stat;
    private javax.swing.JLabel current_month_expense_stat;
    private javax.swing.JLabel current_month_expenses;
    private javax.swing.JLabel current_month_income;
    private javax.swing.JLabel current_month_income_stat;
    private javax.swing.JLabel current_month_savings_stat;
    private javax.swing.JLabel current_year_income;
    private javax.swing.JLabel dec_month_income;
    private javax.swing.JLabel e_bills;
    private javax.swing.JLabel e_dry_food;
    private javax.swing.JLabel e_emergency;
    private javax.swing.JLabel e_emergency1;
    private javax.swing.JLabel e_grocery;
    private javax.swing.JLabel e_medication;
    private javax.swing.JLabel e_tax;
    private javax.swing.JLabel e_transport;
    private javax.swing.JPanel excenses_panel;
    private javax.swing.JTextField expense_amount;
    private javax.swing.JComboBox<String> expense_category;
    private javax.swing.JTextField expense_date;
    private javax.swing.JTextField expense_item;
    private javax.swing.JLabel feb_month_income;
    private javax.swing.JPanel home_panel;
    private javax.swing.JTextField income_amount;
    private javax.swing.ButtonGroup income_category;
    private javax.swing.JTextField income_date;
    private javax.swing.JPanel income_panel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel jan_month_income;
    private javax.swing.JLabel jul_month_income;
    private javax.swing.JLabel jun_month_income;
    private javax.swing.JRadioButton main_income;
    private javax.swing.JLabel mar_month_income;
    private javax.swing.JLabel may_month_income;
    private javax.swing.JLabel nov_month_income;
    private javax.swing.JLabel oct_month_income;
    private javax.swing.JRadioButton other_income;
    private javax.swing.JPanel savings_panel;
    private javax.swing.JLabel sep_month_income;
    private javax.swing.JPanel sidepane;
    private javax.swing.JRadioButton t_savings;
    private javax.swing.JRadioButton t_withdrawals;
    private javax.swing.JTextField transaction_amount;
    private javax.swing.JLabel transaction_balance;
    private javax.swing.ButtonGroup transaction_category;
    private javax.swing.JTextField transaction_date;
    private javax.swing.JTable transaction_summary;
    // End of variables declaration//GEN-END:variables

}
