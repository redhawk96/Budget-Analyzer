/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import budget.analyzer.Expense;
import budget.analyzer.Income;
import budget.analyzer.Capital;
import budget.analyzer.Statistics;
import java.awt.Color;
import javax.swing.JPanel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Redhw
 */
public class Home extends javax.swing.JFrame {

    private String current_date;
    private String current_month;
    private String current_year;

    public Home() {
        initComponents();

        try {
            getDate();
            getStatistics();

            dashboard_panel.setVisible(true);
            capital_panel.setVisible(false);
            income_panel.setVisible(false);
            expense_panel.setVisible(false);
            administrator_panel.setVisible(false);
            admin_dashboard_panel.setVisible(false);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }

    }

    void setColor(JPanel panel) {

        try {

            panel.setBackground(new Color(12, 135, 235));

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void resetColor(JPanel panel) {

        try {

            panel.setBackground(new Color(11, 120, 209));

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void getDate() throws ParseException {

        try {
            Date date = new Date();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            current_date = dateFormat.format(date);

            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
            current_month = monthFormat.format(date);

            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
            current_year = yearFormat.format(date);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void refreshCapitalPanel() {

        try {

            Capital cap = new Capital();
            ResultSet rs = cap.getCurrentMonthCapitalTransactions(current_month);
            capital_summary_table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void refreshIncomePanel() {

        try {

            Income inc = new Income();
            ResultSet rs = inc.getCurrentMonthIncomeTransactions(current_month);
            income_summary_table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void refreshExpensePanel() {

        try {

            Expense exp = new Expense();
            ResultSet rs = exp.getCurrentMonthExpenseTransactions(current_month);
            expense_summary_table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void resetCapitalValues() {

        try {

            input_capital_group.clearSelection();
            input_capital_bank_name.setText(null);
            input_capital_date.setDate(null);
            update_capital_date.setText(null);
            input_capital_amount.setText(null);
            input_capital_addtional_information.setText(null);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void resetIncomeValues() {

        try {

            input_income_group.clearSelection();
            input_income_type.setText(null);
            input_income_date.setDate(null);
            update_income_date.setText(null);
            input_income_amount.setText(null);
            input_income_addtional_information.setText(null);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void resetExpenseValues() {

        try {

            input_expense_type.setSelectedIndex(0);
            input_expense_item_name.setText(null);
            input_expense_date.setDate(null);
            update_expense_date.setText(null);
            update_expense_date.setText(null);
            input_expense_amount.setText(null);
            input_expense_addtional_information.setText(null);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void setCapitalSubmitInterface() {

        try {

            input_capital_date.setVisible(true);
            update_capital_date.setVisible(false);
            button_submit_capital.setVisible(true);
            button_update_capital.setVisible(false);
            button_reset_capital_fields.setVisible(false);
            capital_transaction_label.setVisible(false);
            capital_transaction_id.setVisible(false);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void setIncomeSubmitInterface() {

        try {

            input_income_date.setVisible(true);
            update_income_date.setVisible(false);
            button_submit_income.setVisible(true);
            button_update_income.setVisible(false);
            button_reset_income_fields.setVisible(false);
            income_transaction_label.setVisible(false);
            income_transaction_id.setVisible(false);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void setExpenseSubmitInterface() {

        try {

            input_expense_date.setVisible(true);
            update_expense_date.setVisible(false);
            button_submit_expense.setVisible(true);
            button_update_expense.setVisible(false);
            button_reset_expense_fields.setVisible(false);
            expense_transaction_label.setVisible(false);
            expense_transaction_id.setVisible(false);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void getStatistics() {

        try {
            Statistics stat = new Statistics(current_month);

            current_month_savings_stat.setText(stat.getCapitalBalance().toString());

            current_month_income_stat.setText(stat.getCurrentMonthIncomeBalance().toString());

            current_month_expense_stat.setText(stat.getCurrentMonthExpenseBalance().toString());

            current_month_balance_stat.setText(stat.getCurrentMonthBalance().toString());

            ResultSet rs = stat.getCurrentMonthExpenseTotal();
            expense_statistics_table.setModel(DbUtils.resultSetToTableModel(rs));

            ResultSet rs1 = stat.getCurrentMonthIncomeTotal();
            income_statistics_table.setModel(DbUtils.resultSetToTableModel(rs1));

            ResultSet rs2 = stat.getCurrentMonthCapitalTotal();
            capital_statistics_table.setModel(DbUtils.resultSetToTableModel(rs2));

            ResultSet rs3 = stat.getDetailedCapitalBlance();
            detailed_capital_statistics_table.setModel(DbUtils.resultSetToTableModel(rs3));

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void displaySuccessMessage(String message) {

        try {

            Success_Message s_message = new Success_Message();
            s_message.setMessage(message);
            s_message.setVisible(true);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    void displayErrorMessage(Exception e) {

        try {

            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);

        } catch (Exception ex) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(ex.getMessage());
            e_message.setVisible(true);
        }
    }

    void displayCustomErrorMessage(String message) {

        try {

            Error_Message e_message = new Error_Message();
            e_message.setMessage(message);
            e_message.setVisible(true);

        } catch (Exception e) {
            Error_Message e_message = new Error_Message();
            e_message.setMessage(e.getMessage());
            e_message.setVisible(true);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        input_capital_group = new javax.swing.ButtonGroup();
        input_income_group = new javax.swing.ButtonGroup();
        navigation_panel = new javax.swing.JPanel();
        logo_label = new javax.swing.JLabel();
        logo_separator = new javax.swing.JSeparator();
        logo_icon = new javax.swing.JLabel();
        button_capital = new javax.swing.JPanel();
        capital_icon = new javax.swing.JLabel();
        capital_label = new javax.swing.JLabel();
        button_dashboard = new javax.swing.JPanel();
        dashboard_icon = new javax.swing.JLabel();
        dashboard_label = new javax.swing.JLabel();
        button_income = new javax.swing.JPanel();
        income_icon = new javax.swing.JLabel();
        income_label = new javax.swing.JLabel();
        button_administrator = new javax.swing.JPanel();
        administrator_icon = new javax.swing.JLabel();
        administrator_label = new javax.swing.JLabel();
        button_expenses = new javax.swing.JPanel();
        expenses_icon = new javax.swing.JLabel();
        expenses_label = new javax.swing.JLabel();
        content_panel = new javax.swing.JPanel();
        dashboard_panel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        current_month_savings_stat = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        current_month_expense_stat = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        balance_btn = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        current_month_balance_stat = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        current_month_income_stat = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        income_statistics_table_scroll = new javax.swing.JScrollPane();
        income_statistics_table = new javax.swing.JTable();
        expense_statistics_table_scroll = new javax.swing.JScrollPane();
        expense_statistics_table = new javax.swing.JTable();
        capital_statistics_table_scroll = new javax.swing.JScrollPane();
        capital_statistics_table = new javax.swing.JTable();
        detailed_capital_statistics_table_scroll = new javax.swing.JScrollPane();
        detailed_capital_statistics_table = new javax.swing.JTable();
        capital_panel = new javax.swing.JPanel();
        capital_summary_table_scroll = new javax.swing.JScrollPane();
        capital_summary_table = new javax.swing.JTable();
        capital_method_label = new javax.swing.JLabel();
        capital_type_label = new javax.swing.JLabel();
        capital_date_label = new javax.swing.JLabel();
        capital_transaction_id = new javax.swing.JLabel();
        input_savings_capital = new javax.swing.JRadioButton();
        input_withdrawals_capital = new javax.swing.JRadioButton();
        input_capital_bank_name = new javax.swing.JTextField();
        update_capital_date = new javax.swing.JTextField();
        button_submit_capital = new javax.swing.JButton();
        input_capital_addtional_information_scroll = new javax.swing.JScrollPane();
        input_capital_addtional_information = new javax.swing.JTextArea();
        capital_addtional_information_label2 = new javax.swing.JLabel();
        input_capital_date = new org.jdesktop.swingx.JXDatePicker();
        input_capital_amount = new javax.swing.JTextField();
        button_update_capital = new javax.swing.JButton();
        button_reset_capital_fields = new javax.swing.JButton();
        capital_amount_label3 = new javax.swing.JLabel();
        capital_transaction_label = new javax.swing.JLabel();
        income_panel = new javax.swing.JPanel();
        income_method_label = new javax.swing.JLabel();
        income_type_label = new javax.swing.JLabel();
        income_date_label = new javax.swing.JLabel();
        income_amount_label = new javax.swing.JLabel();
        input_main_income = new javax.swing.JRadioButton();
        input_other_income = new javax.swing.JRadioButton();
        update_income_date = new javax.swing.JTextField();
        input_income_amount = new javax.swing.JTextField();
        button_reset_income_fields = new javax.swing.JButton();
        income_addtional_information_scroll = new javax.swing.JScrollPane();
        input_income_addtional_information = new javax.swing.JTextArea();
        income_addtional_information_label = new javax.swing.JLabel();
        incomel_summary_table_scroll = new javax.swing.JScrollPane();
        income_summary_table = new javax.swing.JTable();
        input_income_date = new org.jdesktop.swingx.JXDatePicker();
        input_income_type = new javax.swing.JTextField();
        button_submit_income = new javax.swing.JButton();
        button_update_income = new javax.swing.JButton();
        income_transaction_id = new javax.swing.JLabel();
        income_transaction_label = new javax.swing.JLabel();
        expense_panel = new javax.swing.JPanel();
        expense_type_label = new javax.swing.JLabel();
        expense_item_name_label = new javax.swing.JLabel();
        expense_date_label = new javax.swing.JLabel();
        expense_amount_label = new javax.swing.JLabel();
        input_expense_type = new javax.swing.JComboBox<>();
        input_expense_item_name = new javax.swing.JTextField();
        update_expense_date = new javax.swing.JTextField();
        expense_addtional_information_scroll = new javax.swing.JScrollPane();
        input_expense_addtional_information = new javax.swing.JTextArea();
        expense_addtional_information_label = new javax.swing.JLabel();
        expense_summary_table_scroll = new javax.swing.JScrollPane();
        expense_summary_table = new javax.swing.JTable();
        input_expense_date = new org.jdesktop.swingx.JXDatePicker();
        expense_transaction_id = new javax.swing.JLabel();
        expense_transaction_label = new javax.swing.JLabel();
        button_reset_expense_fields = new javax.swing.JButton();
        button_submit_expense = new javax.swing.JButton();
        button_update_expense = new javax.swing.JButton();
        input_expense_amount = new javax.swing.JTextField();
        administrator_panel = new javax.swing.JPanel();
        admin_icon = new javax.swing.JLabel();
        input_administrator_password = new javax.swing.JPasswordField();
        button_administrator_login = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        incorrect_password_label = new javax.swing.JLabel();
        admin_dashboard_panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Budget Analyzer");
        setMaximumSize(new java.awt.Dimension(1005, 620));
        setMinimumSize(new java.awt.Dimension(1005, 620));
        setResizable(false);
        setSize(new java.awt.Dimension(1005, 620));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navigation_panel.setBackground(new java.awt.Color(51, 51, 51));
        navigation_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logo_label.setFont(new java.awt.Font("Lato Semibold", 1, 28)); // NOI18N
        logo_label.setForeground(new java.awt.Color(204, 204, 204));
        logo_label.setText("BA");
        navigation_panel.add(logo_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));
        navigation_panel.add(logo_separator, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 64, 140, 10));

        logo_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Ledger_48px_1.png"))); // NOI18N
        navigation_panel.add(logo_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 50, 40));

        button_capital.setBackground(new java.awt.Color(11, 120, 209));
        button_capital.setMaximumSize(new java.awt.Dimension(260, 50));
        button_capital.setMinimumSize(new java.awt.Dimension(260, 50));
        button_capital.setPreferredSize(new java.awt.Dimension(260, 50));
        button_capital.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_capitalMouseClicked(evt);
            }
        });

        capital_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        capital_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Money_Box_25px_3.png"))); // NOI18N

        capital_label.setBackground(new java.awt.Color(255, 255, 255));
        capital_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        capital_label.setForeground(new java.awt.Color(204, 204, 204));
        capital_label.setText("CAPITAL ");

        javax.swing.GroupLayout button_capitalLayout = new javax.swing.GroupLayout(button_capital);
        button_capital.setLayout(button_capitalLayout);
        button_capitalLayout.setHorizontalGroup(
            button_capitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_capitalLayout.createSequentialGroup()
                .addComponent(capital_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(capital_label)
                .addGap(0, 141, Short.MAX_VALUE))
        );
        button_capitalLayout.setVerticalGroup(
            button_capitalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(capital_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(button_capitalLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(capital_label)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        navigation_panel.add(button_capital, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 250, -1));

        button_dashboard.setBackground(new java.awt.Color(12, 135, 235));
        button_dashboard.setMaximumSize(new java.awt.Dimension(260, 50));
        button_dashboard.setMinimumSize(new java.awt.Dimension(260, 50));
        button_dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_dashboardMouseClicked(evt);
            }
        });

        dashboard_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dashboard_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Combo_Chart_25px.png"))); // NOI18N

        dashboard_label.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        dashboard_label.setForeground(new java.awt.Color(204, 204, 204));
        dashboard_label.setText("DASHBOARD");

        javax.swing.GroupLayout button_dashboardLayout = new javax.swing.GroupLayout(button_dashboard);
        button_dashboard.setLayout(button_dashboardLayout);
        button_dashboardLayout.setHorizontalGroup(
            button_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_dashboardLayout.createSequentialGroup()
                .addComponent(dashboard_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dashboard_label)
                .addGap(0, 116, Short.MAX_VALUE))
        );
        button_dashboardLayout.setVerticalGroup(
            button_dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dashboard_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(button_dashboardLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(dashboard_label)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        navigation_panel.add(button_dashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 250, -1));

        button_income.setBackground(new java.awt.Color(11, 120, 209));
        button_income.setMaximumSize(new java.awt.Dimension(260, 50));
        button_income.setMinimumSize(new java.awt.Dimension(260, 50));
        button_income.setPreferredSize(new java.awt.Dimension(260, 50));
        button_income.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_incomeMouseClicked(evt);
            }
        });

        income_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        income_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Receive_Cash_25px_1.png"))); // NOI18N

        income_label.setBackground(new java.awt.Color(255, 255, 255));
        income_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        income_label.setForeground(new java.awt.Color(204, 204, 204));
        income_label.setText("INCOME");

        javax.swing.GroupLayout button_incomeLayout = new javax.swing.GroupLayout(button_income);
        button_income.setLayout(button_incomeLayout);
        button_incomeLayout.setHorizontalGroup(
            button_incomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_incomeLayout.createSequentialGroup()
                .addComponent(income_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(income_label)
                .addGap(0, 145, Short.MAX_VALUE))
        );
        button_incomeLayout.setVerticalGroup(
            button_incomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(income_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(button_incomeLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(income_label)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        navigation_panel.add(button_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 250, -1));

        button_administrator.setBackground(new java.awt.Color(11, 120, 209));
        button_administrator.setMaximumSize(new java.awt.Dimension(260, 50));
        button_administrator.setMinimumSize(new java.awt.Dimension(260, 50));
        button_administrator.setPreferredSize(new java.awt.Dimension(260, 50));
        button_administrator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_administratorMouseClicked(evt);
            }
        });

        administrator_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        administrator_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Microsoft_Admin_25px.png"))); // NOI18N

        administrator_label.setBackground(new java.awt.Color(255, 255, 255));
        administrator_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        administrator_label.setForeground(new java.awt.Color(204, 204, 204));
        administrator_label.setText("ADMINISTRATOR");

        javax.swing.GroupLayout button_administratorLayout = new javax.swing.GroupLayout(button_administrator);
        button_administrator.setLayout(button_administratorLayout);
        button_administratorLayout.setHorizontalGroup(
            button_administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_administratorLayout.createSequentialGroup()
                .addComponent(administrator_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(administrator_label)
                .addGap(0, 92, Short.MAX_VALUE))
        );
        button_administratorLayout.setVerticalGroup(
            button_administratorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(administrator_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(button_administratorLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(administrator_label)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        navigation_panel.add(button_administrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 320, 250, -1));

        button_expenses.setBackground(new java.awt.Color(11, 120, 209));
        button_expenses.setMaximumSize(new java.awt.Dimension(260, 50));
        button_expenses.setMinimumSize(new java.awt.Dimension(260, 50));
        button_expenses.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_expensesMouseClicked(evt);
            }
        });

        expenses_icon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        expenses_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Card_Payment_25px_1.png"))); // NOI18N

        expenses_label.setBackground(new java.awt.Color(255, 255, 255));
        expenses_label.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        expenses_label.setForeground(new java.awt.Color(204, 204, 204));
        expenses_label.setText("EXPENSE");

        javax.swing.GroupLayout button_expensesLayout = new javax.swing.GroupLayout(button_expenses);
        button_expenses.setLayout(button_expensesLayout);
        button_expensesLayout.setHorizontalGroup(
            button_expensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_expensesLayout.createSequentialGroup()
                .addComponent(expenses_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(expenses_label)
                .addGap(0, 142, Short.MAX_VALUE))
        );
        button_expensesLayout.setVerticalGroup(
            button_expensesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(expenses_icon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(button_expensesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(expenses_label)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        navigation_panel.add(button_expenses, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 250, -1));

        getContentPane().add(navigation_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 240, 620));

        content_panel.setBackground(new java.awt.Color(255, 255, 255));
        content_panel.setMaximumSize(new java.awt.Dimension(760, 620));
        content_panel.setMinimumSize(new java.awt.Dimension(760, 620));
        content_panel.setPreferredSize(new java.awt.Dimension(760, 620));
        content_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dashboard_panel.setBackground(new java.awt.Color(255, 255, 255));
        dashboard_panel.setMaximumSize(new java.awt.Dimension(760, 620));
        dashboard_panel.setMinimumSize(new java.awt.Dimension(760, 620));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(33, 150, 243), 3));
        jPanel8.setForeground(new java.awt.Color(102, 102, 102));
        jPanel8.setMaximumSize(new java.awt.Dimension(204, 76));
        jPanel8.setMinimumSize(new java.awt.Dimension(204, 76));
        jPanel8.setPreferredSize(new java.awt.Dimension(180, 76));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        current_month_savings_stat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        current_month_savings_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_savings_stat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_month_savings_stat.setText("Rs. 0.00");
        jPanel8.add(current_month_savings_stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, -1));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(102, 102, 102));
        jLabel39.setText("SAVINGS");
        jPanel8.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Get_Cash_48px_1.png"))); // NOI18N
        jPanel8.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 60, 50));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(244, 67, 54), 3));
        jPanel7.setMaximumSize(new java.awt.Dimension(204, 76));
        jPanel7.setMinimumSize(new java.awt.Dimension(204, 76));
        jPanel7.setPreferredSize(new java.awt.Dimension(180, 76));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        current_month_expense_stat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        current_month_expense_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_expense_stat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_month_expense_stat.setText("Rs. 0.00");
        jPanel7.add(current_month_expense_stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, -1));

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_POS_Terminal_48px.png"))); // NOI18N
        jPanel7.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 14, -1, -1));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText("EXPENSES");
        jPanel7.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        balance_btn.setBackground(new java.awt.Color(255, 255, 255));
        balance_btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 152, 0), 3));
        balance_btn.setPreferredSize(new java.awt.Dimension(180, 76));
        balance_btn.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(102, 102, 102));
        jLabel60.setText("BALANCE");
        balance_btn.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        current_month_balance_stat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        current_month_balance_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_balance_stat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_month_balance_stat.setText("Rs. 0.00");
        balance_btn.add(current_month_balance_stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, -1));

        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Scales_48px.png"))); // NOI18N
        balance_btn.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 14, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 150, 136), 3));
        jPanel6.setMaximumSize(new java.awt.Dimension(204, 76));
        jPanel6.setMinimumSize(new java.awt.Dimension(204, 76));
        jPanel6.setPreferredSize(new java.awt.Dimension(180, 76));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(102, 102, 102));
        jLabel40.setText("INCOME");
        jPanel6.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 40, -1, -1));

        current_month_income_stat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        current_month_income_stat.setForeground(new java.awt.Color(102, 102, 102));
        current_month_income_stat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        current_month_income_stat.setText("Rs. 0.00");
        jPanel6.add(current_month_income_stat, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 110, -1));

        jLabel63.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Refund_48px.png"))); // NOI18N
        jPanel6.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 14, -1, 51));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        income_statistics_table.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_statistics_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        income_statistics_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        income_statistics_table.setEnabled(false);
        income_statistics_table.setFillsViewportHeight(true);
        income_statistics_table.setGridColor(new java.awt.Color(255, 255, 255));
        income_statistics_table.setIntercellSpacing(new java.awt.Dimension(10, 10));
        income_statistics_table.setRowHeight(32);
        income_statistics_table.setSelectionBackground(new java.awt.Color(102, 102, 102));
        income_statistics_table.setShowHorizontalLines(false);
        income_statistics_table.setShowVerticalLines(false);
        income_statistics_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                income_statistics_tableMouseClicked(evt);
            }
        });
        income_statistics_table_scroll.setViewportView(income_statistics_table);

        expense_statistics_table.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_statistics_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        expense_statistics_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        expense_statistics_table.setEnabled(false);
        expense_statistics_table.setFillsViewportHeight(true);
        expense_statistics_table.setGridColor(new java.awt.Color(255, 255, 255));
        expense_statistics_table.setIntercellSpacing(new java.awt.Dimension(10, 10));
        expense_statistics_table.setRowHeight(32);
        expense_statistics_table.setSelectionBackground(new java.awt.Color(102, 102, 102));
        expense_statistics_table.setShowHorizontalLines(false);
        expense_statistics_table.setShowVerticalLines(false);
        expense_statistics_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expense_statistics_tableMouseClicked(evt);
            }
        });
        expense_statistics_table_scroll.setViewportView(expense_statistics_table);

        capital_statistics_table.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_statistics_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        capital_statistics_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        capital_statistics_table.setEnabled(false);
        capital_statistics_table.setFillsViewportHeight(true);
        capital_statistics_table.setGridColor(new java.awt.Color(255, 255, 255));
        capital_statistics_table.setIntercellSpacing(new java.awt.Dimension(10, 10));
        capital_statistics_table.setRowHeight(32);
        capital_statistics_table.setSelectionBackground(new java.awt.Color(102, 102, 102));
        capital_statistics_table.setShowHorizontalLines(false);
        capital_statistics_table.setShowVerticalLines(false);
        capital_statistics_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                capital_statistics_tableMouseClicked(evt);
            }
        });
        capital_statistics_table_scroll.setViewportView(capital_statistics_table);

        detailed_capital_statistics_table.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        detailed_capital_statistics_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        detailed_capital_statistics_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        detailed_capital_statistics_table.setEnabled(false);
        detailed_capital_statistics_table.setFillsViewportHeight(true);
        detailed_capital_statistics_table.setGridColor(new java.awt.Color(255, 255, 255));
        detailed_capital_statistics_table.setIntercellSpacing(new java.awt.Dimension(10, 10));
        detailed_capital_statistics_table.setRowHeight(32);
        detailed_capital_statistics_table.setSelectionBackground(new java.awt.Color(102, 102, 102));
        detailed_capital_statistics_table.setShowHorizontalLines(false);
        detailed_capital_statistics_table.setShowVerticalLines(false);
        detailed_capital_statistics_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                detailed_capital_statistics_tableMouseClicked(evt);
            }
        });
        detailed_capital_statistics_table_scroll.setViewportView(detailed_capital_statistics_table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(detailed_capital_statistics_table_scroll)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(expense_statistics_table_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(income_statistics_table_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(capital_statistics_table_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(expense_statistics_table_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(income_statistics_table_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(capital_statistics_table_scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(detailed_capital_statistics_table_scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout dashboard_panelLayout = new javax.swing.GroupLayout(dashboard_panel);
        dashboard_panel.setLayout(dashboard_panelLayout);
        dashboard_panelLayout.setHorizontalGroup(
            dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(balance_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        dashboard_panelLayout.setVerticalGroup(
            dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboard_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(balance_btn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        content_panel.add(dashboard_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        capital_panel.setBackground(new java.awt.Color(255, 255, 255));
        capital_panel.setMaximumSize(new java.awt.Dimension(760, 620));
        capital_panel.setMinimumSize(new java.awt.Dimension(760, 620));
        capital_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        capital_summary_table.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        capital_summary_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        capital_summary_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        capital_summary_table.setFillsViewportHeight(true);
        capital_summary_table.setGridColor(new java.awt.Color(255, 255, 255));
        capital_summary_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        capital_summary_table.setRowHeight(25);
        capital_summary_table.setSelectionBackground(new java.awt.Color(102, 102, 102));
        capital_summary_table.setShowHorizontalLines(false);
        capital_summary_table.setShowVerticalLines(false);
        capital_summary_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                capital_summary_tableMouseClicked(evt);
            }
        });
        capital_summary_table_scroll.setViewportView(capital_summary_table);

        capital_panel.add(capital_summary_table_scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 11, 718, 303));

        capital_method_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_method_label.setText("Capital Method");
        capital_panel.add(capital_method_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 352, -1, -1));

        capital_type_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_type_label.setText("Bank Name");
        capital_panel.add(capital_type_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 398, -1, -1));

        capital_date_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_date_label.setText("Capital Date");
        capital_panel.add(capital_date_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 443, -1, -1));

        capital_transaction_id.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_transaction_id.setText("00");
        capital_transaction_id.setPreferredSize(new java.awt.Dimension(263, 27));
        capital_panel.add(capital_transaction_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 540, -1, -1));

        input_savings_capital.setBackground(new java.awt.Color(255, 255, 255));
        input_capital_group.add(input_savings_capital);
        input_savings_capital.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_savings_capital.setText("Deposits");
        input_savings_capital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_savings_capitalActionPerformed(evt);
            }
        });
        capital_panel.add(input_savings_capital, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 348, -1, -1));

        input_withdrawals_capital.setBackground(new java.awt.Color(255, 255, 255));
        input_capital_group.add(input_withdrawals_capital);
        input_withdrawals_capital.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_withdrawals_capital.setText("Withdrawals");
        input_withdrawals_capital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_withdrawals_capitalActionPerformed(evt);
            }
        });
        capital_panel.add(input_withdrawals_capital, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 348, 123, -1));

        input_capital_bank_name.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_capital_bank_name.setMaximumSize(new java.awt.Dimension(263, 27));
        input_capital_bank_name.setMinimumSize(new java.awt.Dimension(263, 27));
        input_capital_bank_name.setPreferredSize(new java.awt.Dimension(263, 27));
        capital_panel.add(input_capital_bank_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 395, -1, -1));

        update_capital_date.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        update_capital_date.setMaximumSize(new java.awt.Dimension(263, 27));
        update_capital_date.setMinimumSize(new java.awt.Dimension(263, 27));
        update_capital_date.setPreferredSize(new java.awt.Dimension(263, 27));
        capital_panel.add(update_capital_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 440, -1, -1));

        button_submit_capital.setBackground(new java.awt.Color(33, 150, 243));
        button_submit_capital.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_submit_capital.setText("Submit");
        button_submit_capital.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_submit_capitalMouseClicked(evt);
            }
        });
        button_submit_capital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_submit_capitalActionPerformed(evt);
            }
        });
        capital_panel.add(button_submit_capital, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 540, 112, 41));

        input_capital_addtional_information.setColumns(20);
        input_capital_addtional_information.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_capital_addtional_information.setLineWrap(true);
        input_capital_addtional_information.setRows(4);
        input_capital_addtional_information.setWrapStyleWord(true);
        input_capital_addtional_information_scroll.setViewportView(input_capital_addtional_information);

        capital_panel.add(input_capital_addtional_information_scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 395, 254, 118));

        capital_addtional_information_label2.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_addtional_information_label2.setText("Addtional Information");
        capital_panel.add(capital_addtional_information_label2, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 352, -1, -1));
        capital_panel.add(input_capital_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 443, 263, 27));

        input_capital_amount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_capital_amount.setMaximumSize(new java.awt.Dimension(263, 27));
        input_capital_amount.setMinimumSize(new java.awt.Dimension(263, 27));
        input_capital_amount.setPreferredSize(new java.awt.Dimension(263, 27));
        capital_panel.add(input_capital_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 489, -1, -1));

        button_update_capital.setBackground(new java.awt.Color(33, 150, 243));
        button_update_capital.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_update_capital.setText("Update");
        button_update_capital.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_update_capitalMouseClicked(evt);
            }
        });
        button_update_capital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_update_capitalActionPerformed(evt);
            }
        });
        capital_panel.add(button_update_capital, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 540, 112, 41));

        button_reset_capital_fields.setBackground(new java.awt.Color(33, 150, 243));
        button_reset_capital_fields.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_reset_capital_fields.setText("Reset");
        button_reset_capital_fields.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_reset_capital_fieldsMouseClicked(evt);
            }
        });
        button_reset_capital_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_reset_capital_fieldsActionPerformed(evt);
            }
        });
        capital_panel.add(button_reset_capital_fields, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 540, 112, 41));

        capital_amount_label3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_amount_label3.setText("Amount (Rs.)");
        capital_panel.add(capital_amount_label3, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 488, -1, 28));

        capital_transaction_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        capital_transaction_label.setText("Capital ID");
        capital_panel.add(capital_transaction_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, -1, 28));

        content_panel.add(capital_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 620));

        income_panel.setBackground(new java.awt.Color(255, 255, 255));
        income_panel.setMaximumSize(new java.awt.Dimension(760, 620));
        income_panel.setMinimumSize(new java.awt.Dimension(760, 620));
        income_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        income_method_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_method_label.setText("Income Method");
        income_panel.add(income_method_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 352, -1, -1));

        income_type_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_type_label.setText("Income Type");
        income_panel.add(income_type_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 398, -1, -1));

        income_date_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_date_label.setText("Income Date");
        income_panel.add(income_date_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 443, -1, -1));

        income_amount_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_amount_label.setText("Amount (Rs.)");
        income_panel.add(income_amount_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 488, -1, 28));

        input_main_income.setBackground(new java.awt.Color(255, 255, 255));
        input_income_group.add(input_main_income);
        input_main_income.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_main_income.setText("Main Income");
        input_main_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_main_incomeActionPerformed(evt);
            }
        });
        income_panel.add(input_main_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 348, -1, -1));

        input_other_income.setBackground(new java.awt.Color(255, 255, 255));
        input_income_group.add(input_other_income);
        input_other_income.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_other_income.setText("Other");
        input_other_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_other_incomeActionPerformed(evt);
            }
        });
        income_panel.add(input_other_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(339, 348, 95, -1));

        update_income_date.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_panel.add(update_income_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 440, 263, -1));

        input_income_amount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_panel.add(input_income_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 489, 263, -1));

        button_reset_income_fields.setBackground(new java.awt.Color(33, 150, 243));
        button_reset_income_fields.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_reset_income_fields.setText("Reset");
        button_reset_income_fields.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_reset_income_fieldsMouseClicked(evt);
            }
        });
        button_reset_income_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_reset_income_fieldsActionPerformed(evt);
            }
        });
        income_panel.add(button_reset_income_fields, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 540, 112, 41));

        input_income_addtional_information.setColumns(20);
        input_income_addtional_information.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_income_addtional_information.setLineWrap(true);
        input_income_addtional_information.setRows(4);
        input_income_addtional_information.setWrapStyleWord(true);
        income_addtional_information_scroll.setViewportView(input_income_addtional_information);

        income_panel.add(income_addtional_information_scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 395, 254, 120));

        income_addtional_information_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_addtional_information_label.setText("Addtional Information");
        income_panel.add(income_addtional_information_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 352, -1, -1));

        income_summary_table.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        income_summary_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        income_summary_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        income_summary_table.setFillsViewportHeight(true);
        income_summary_table.setGridColor(new java.awt.Color(255, 255, 255));
        income_summary_table.setIntercellSpacing(new java.awt.Dimension(0, 0));
        income_summary_table.setRowHeight(25);
        income_summary_table.setSelectionBackground(new java.awt.Color(102, 102, 102));
        income_summary_table.setShowHorizontalLines(false);
        income_summary_table.setShowVerticalLines(false);
        income_summary_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                income_summary_tableMouseClicked(evt);
            }
        });
        incomel_summary_table_scroll.setViewportView(income_summary_table);

        income_panel.add(incomel_summary_table_scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 11, 718, 303));

        input_income_date.setPreferredSize(new java.awt.Dimension(6, 27));
        income_panel.add(input_income_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 443, 263, -1));

        input_income_type.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_panel.add(input_income_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(171, 395, 263, -1));

        button_submit_income.setBackground(new java.awt.Color(33, 150, 243));
        button_submit_income.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_submit_income.setText("Submit");
        button_submit_income.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_submit_incomeMouseClicked(evt);
            }
        });
        button_submit_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_submit_incomeActionPerformed(evt);
            }
        });
        income_panel.add(button_submit_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 540, 112, 41));

        button_update_income.setBackground(new java.awt.Color(33, 150, 243));
        button_update_income.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_update_income.setText("Update");
        button_update_income.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_update_incomeMouseClicked(evt);
            }
        });
        button_update_income.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_update_incomeActionPerformed(evt);
            }
        });
        income_panel.add(button_update_income, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 540, 112, 41));

        income_transaction_id.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_transaction_id.setText("00");
        income_transaction_id.setPreferredSize(new java.awt.Dimension(263, 27));
        income_panel.add(income_transaction_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 540, -1, -1));

        income_transaction_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        income_transaction_label.setText("Income ID");
        income_panel.add(income_transaction_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, -1, 28));

        content_panel.add(income_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 620));

        expense_panel.setBackground(new java.awt.Color(255, 255, 255));
        expense_panel.setMaximumSize(new java.awt.Dimension(760, 620));
        expense_panel.setMinimumSize(new java.awt.Dimension(760, 620));
        expense_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        expense_type_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_type_label.setText("Expense Type");
        expense_panel.add(expense_type_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 355, -1, -1));

        expense_item_name_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_item_name_label.setText("Item Name");
        expense_panel.add(expense_item_name_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 398, -1, -1));

        expense_date_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_date_label.setText("Transaction Date");
        expense_panel.add(expense_date_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 443, -1, -1));

        expense_amount_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_amount_label.setText("Amount (Rs.)");
        expense_panel.add(expense_amount_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 488, -1, 28));

        input_expense_type.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_expense_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bills", "Tax", "Grocery", "Dry Food", "Medication", "Transport", "Emergency" }));
        input_expense_type.setMaximumSize(new java.awt.Dimension(6, 27));
        input_expense_type.setMinimumSize(new java.awt.Dimension(6, 27));
        input_expense_type.setPreferredSize(new java.awt.Dimension(6, 27));
        input_expense_type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_expense_typeActionPerformed(evt);
            }
        });
        expense_panel.add(input_expense_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 352, 263, -1));

        input_expense_item_name.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_expense_item_name.setMaximumSize(new java.awt.Dimension(6, 27));
        expense_panel.add(input_expense_item_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 397, 263, -1));

        update_expense_date.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        update_expense_date.setMaximumSize(new java.awt.Dimension(6, 27));
        update_expense_date.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                update_expense_dateMouseClicked(evt);
            }
        });
        expense_panel.add(update_expense_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 263, -1));

        input_expense_addtional_information.setColumns(20);
        input_expense_addtional_information.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_expense_addtional_information.setLineWrap(true);
        input_expense_addtional_information.setRows(4);
        input_expense_addtional_information.setWrapStyleWord(true);
        expense_addtional_information_scroll.setViewportView(input_expense_addtional_information);

        expense_panel.add(expense_addtional_information_scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(483, 393, 254, 123));

        expense_addtional_information_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_addtional_information_label.setText("Addtional Information");
        expense_panel.add(expense_addtional_information_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 354, -1, -1));

        expense_summary_table.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        expense_summary_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        expense_summary_table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        expense_summary_table.setFillsViewportHeight(true);
        expense_summary_table.setGridColor(new java.awt.Color(255, 255, 255));
        expense_summary_table.setRowHeight(25);
        expense_summary_table.setSelectionBackground(new java.awt.Color(102, 102, 102));
        expense_summary_table.setShowHorizontalLines(false);
        expense_summary_table.setShowVerticalLines(false);
        expense_summary_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                expense_summary_tableMouseClicked(evt);
            }
        });
        expense_summary_table_scroll.setViewportView(expense_summary_table);

        expense_panel.add(expense_summary_table_scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 11, 718, 303));

        input_expense_date.setPreferredSize(new java.awt.Dimension(6, 27));
        expense_panel.add(input_expense_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 443, 263, -1));

        expense_transaction_id.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_transaction_id.setText("00");
        expense_transaction_id.setPreferredSize(new java.awt.Dimension(263, 27));
        expense_panel.add(expense_transaction_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 540, -1, -1));

        expense_transaction_label.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        expense_transaction_label.setText("Expense ID");
        expense_panel.add(expense_transaction_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, -1, 28));

        button_reset_expense_fields.setBackground(new java.awt.Color(33, 150, 243));
        button_reset_expense_fields.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_reset_expense_fields.setText("Reset");
        button_reset_expense_fields.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_reset_expense_fieldsMouseClicked(evt);
            }
        });
        button_reset_expense_fields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_reset_expense_fieldsActionPerformed(evt);
            }
        });
        expense_panel.add(button_reset_expense_fields, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 540, 112, 41));

        button_submit_expense.setBackground(new java.awt.Color(33, 150, 243));
        button_submit_expense.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_submit_expense.setText("Submit");
        button_submit_expense.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_submit_expenseMouseClicked(evt);
            }
        });
        button_submit_expense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_submit_expenseActionPerformed(evt);
            }
        });
        expense_panel.add(button_submit_expense, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 540, 112, 41));

        button_update_expense.setBackground(new java.awt.Color(33, 150, 243));
        button_update_expense.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        button_update_expense.setText("Update");
        button_update_expense.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_update_expenseMouseClicked(evt);
            }
        });
        button_update_expense.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_update_expenseActionPerformed(evt);
            }
        });
        expense_panel.add(button_update_expense, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 540, 112, 41));

        input_expense_amount.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_expense_amount.setMaximumSize(new java.awt.Dimension(6, 27));
        expense_panel.add(input_expense_amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(159, 487, 263, -1));

        content_panel.add(expense_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        administrator_panel.setBackground(new java.awt.Color(255, 255, 255));
        administrator_panel.setMaximumSize(new java.awt.Dimension(760, 620));
        administrator_panel.setMinimumSize(new java.awt.Dimension(760, 620));

        admin_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_SQL_Database_Administrators_Group_Skin_Type_7_96px_1.png"))); // NOI18N

        input_administrator_password.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        input_administrator_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                input_administrator_passwordActionPerformed(evt);
            }
        });

        button_administrator_login.setBackground(new java.awt.Color(102, 102, 102));
        button_administrator_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                button_administrator_loginMouseClicked(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interfaces/Images/icons8_Encrypt_25px.png"))); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("LOGIN");

        javax.swing.GroupLayout button_administrator_loginLayout = new javax.swing.GroupLayout(button_administrator_login);
        button_administrator_login.setLayout(button_administrator_loginLayout);
        button_administrator_loginLayout.setHorizontalGroup(
            button_administrator_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(button_administrator_loginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(12, Short.MAX_VALUE))
        );
        button_administrator_loginLayout.setVerticalGroup(
            button_administrator_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, button_administrator_loginLayout.createSequentialGroup()
                .addGap(0, 8, Short.MAX_VALUE)
                .addGroup(button_administrator_loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addGap(4, 4, 4))
        );

        incorrect_password_label.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        incorrect_password_label.setForeground(new java.awt.Color(153, 0, 51));
        incorrect_password_label.setText("Incorrent Credentials");

        javax.swing.GroupLayout administrator_panelLayout = new javax.swing.GroupLayout(administrator_panel);
        administrator_panel.setLayout(administrator_panelLayout);
        administrator_panelLayout.setHorizontalGroup(
            administrator_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(administrator_panelLayout.createSequentialGroup()
                .addGroup(administrator_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(administrator_panelLayout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(admin_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(administrator_panelLayout.createSequentialGroup()
                        .addGap(306, 306, 306)
                        .addComponent(button_administrator_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(administrator_panelLayout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(input_administrator_password, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(administrator_panelLayout.createSequentialGroup()
                        .addGap(280, 280, 280)
                        .addComponent(incorrect_password_label)))
                .addContainerGap(284, Short.MAX_VALUE))
        );
        administrator_panelLayout.setVerticalGroup(
            administrator_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(administrator_panelLayout.createSequentialGroup()
                .addGap(147, 147, 147)
                .addComponent(admin_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(input_administrator_password, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(button_administrator_login, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(incorrect_password_label)
                .addContainerGap(200, Short.MAX_VALUE))
        );

        content_panel.add(administrator_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        admin_dashboard_panel.setBackground(new java.awt.Color(255, 255, 255));
        admin_dashboard_panel.setMaximumSize(new java.awt.Dimension(760, 620));
        admin_dashboard_panel.setMinimumSize(new java.awt.Dimension(760, 620));

        javax.swing.GroupLayout admin_dashboard_panelLayout = new javax.swing.GroupLayout(admin_dashboard_panel);
        admin_dashboard_panel.setLayout(admin_dashboard_panelLayout);
        admin_dashboard_panelLayout.setHorizontalGroup(
            admin_dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 760, Short.MAX_VALUE)
        );
        admin_dashboard_panelLayout.setVerticalGroup(
            admin_dashboard_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
        );

        content_panel.add(admin_dashboard_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(content_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void button_capitalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_capitalMouseClicked

        try {

            resetColor(button_dashboard);
            setColor(button_capital);
            resetColor(button_income);
            resetColor(button_expenses);
            resetColor(button_administrator);

            dashboard_panel.setVisible(false);
            capital_panel.setVisible(true);
            income_panel.setVisible(false);
            expense_panel.setVisible(false);
            administrator_panel.setVisible(false);
            admin_dashboard_panel.setVisible(false);

            refreshCapitalPanel();
            setCapitalSubmitInterface();

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_capitalMouseClicked

    private void button_dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_dashboardMouseClicked

        try {

            getStatistics();

            setColor(button_dashboard);
            resetColor(button_capital);
            resetColor(button_income);
            resetColor(button_expenses);
            resetColor(button_administrator);

            dashboard_panel.setVisible(true);
            capital_panel.setVisible(false);
            income_panel.setVisible(false);
            expense_panel.setVisible(false);
            administrator_panel.setVisible(false);
            admin_dashboard_panel.setVisible(false);

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_dashboardMouseClicked

    private void button_incomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_incomeMouseClicked

        try {
            resetColor(button_capital);
            resetColor(button_dashboard);
            setColor(button_income);
            resetColor(button_expenses);
            resetColor(button_administrator);

            dashboard_panel.setVisible(false);
            capital_panel.setVisible(false);
            income_panel.setVisible(true);
            expense_panel.setVisible(false);
            administrator_panel.setVisible(false);
            admin_dashboard_panel.setVisible(false);

            refreshIncomePanel();
            setIncomeSubmitInterface();

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_incomeMouseClicked

    private void button_administratorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_administratorMouseClicked

        try {

            resetColor(button_capital);
            resetColor(button_dashboard);
            resetColor(button_income);
            resetColor(button_expenses);
            setColor(button_administrator);

            dashboard_panel.setVisible(false);
            capital_panel.setVisible(false);
            income_panel.setVisible(false);
            expense_panel.setVisible(false);
            administrator_panel.setVisible(true);
            admin_dashboard_panel.setVisible(false);

            incorrect_password_label.setVisible(false);
            
        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_administratorMouseClicked

    private void button_expensesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_expensesMouseClicked

        try {

            resetColor(button_capital);
            resetColor(button_dashboard);
            resetColor(button_income);
            setColor(button_expenses);
            resetColor(button_administrator);

            dashboard_panel.setVisible(false);
            capital_panel.setVisible(false);
            income_panel.setVisible(false);
            expense_panel.setVisible(true);
            administrator_panel.setVisible(false);
            admin_dashboard_panel.setVisible(false);

            refreshExpensePanel();
            setExpenseSubmitInterface();

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_expensesMouseClicked

    private void input_main_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_main_incomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_main_incomeActionPerformed

    private void input_other_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_other_incomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_other_incomeActionPerformed

    private void button_reset_income_fieldsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_reset_income_fieldsMouseClicked

        try {

            resetIncomeValues();
            setIncomeSubmitInterface();

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_reset_income_fieldsMouseClicked

    private void button_reset_income_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_reset_income_fieldsActionPerformed

    }//GEN-LAST:event_button_reset_income_fieldsActionPerformed

    private void input_savings_capitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_savings_capitalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_savings_capitalActionPerformed

    private void input_withdrawals_capitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_withdrawals_capitalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_withdrawals_capitalActionPerformed

    private void button_submit_capitalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_submit_capitalMouseClicked

        try {

            Capital cap = new Capital();

//      Getting capital type
            input_savings_capital.setActionCommand("Deposit");
            input_withdrawals_capital.setActionCommand("Withdrawal");
            String capital_type = input_capital_group.getSelection().getActionCommand();

//      Gettting bank name
            String bank_name = input_capital_bank_name.getText();

//      Getting capital date
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            formater.format(input_capital_date.getDate());
            String capital_date = formater.format(cal.getTime()).toString();

//      Getting capital value
            String unformatted_capital_value = input_capital_amount.getText();
            String formatted_capital_value = unformatted_capital_value.replaceAll("[^0-9.]", "");
            Double capital_value = Double.parseDouble(formatted_capital_value);

//      Getting capital addtional details
            String capital_details = input_capital_addtional_information.getText();

            int result = cap.setCapital(capital_date, capital_type, bank_name, capital_details, capital_value);

            if (result == 1) {
                displaySuccessMessage("Capital transaction successfully added !");
                refreshCapitalPanel();
                resetCapitalValues();
                setCapitalSubmitInterface();
            } else {
                displayCustomErrorMessage("Error : Unable to add capital transaction into the system");
            }

        } catch (Exception e) {
            displayErrorMessage(e);
        }

    }//GEN-LAST:event_button_submit_capitalMouseClicked

    private void button_submit_capitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_submit_capitalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_submit_capitalActionPerformed

    private void input_expense_typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_expense_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_expense_typeActionPerformed

    private void button_administrator_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_administrator_loginMouseClicked

        try {
            String admin_password = new String(input_administrator_password.getPassword());

            if (admin_password.equals("c1")) {

                dashboard_panel.setVisible(false);
                capital_panel.setVisible(false);
                income_panel.setVisible(false);
                expense_panel.setVisible(false);
                administrator_panel.setVisible(false);
                admin_dashboard_panel.setVisible(true);
            }else{
                incorrect_password_label.setVisible(true);
            }

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_administrator_loginMouseClicked

    private void input_administrator_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_input_administrator_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_input_administrator_passwordActionPerformed

    private void capital_summary_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_capital_summary_tableMouseClicked

        try {

            int capital_table_row = capital_summary_table.getSelectedRow();

            String capital_id = capital_summary_table.getValueAt(capital_table_row, 0).toString();
            String capital_date = capital_summary_table.getValueAt(capital_table_row, 1).toString();
            String capital_type = capital_summary_table.getValueAt(capital_table_row, 2).toString();
            String bank_name = capital_summary_table.getValueAt(capital_table_row, 3).toString();
            String capital_details = capital_summary_table.getValueAt(capital_table_row, 4).toString();
            String capital_amount = capital_summary_table.getValueAt(capital_table_row, 5).toString();

            input_capital_date.setVisible(false);
            update_capital_date.setVisible(true);

            button_reset_capital_fields.setVisible(true);
            button_submit_capital.setVisible(false);
            button_update_capital.setVisible(true);

            capital_transaction_label.setVisible(true);
            capital_transaction_id.setVisible(true);

            if (capital_type.equals("Deposit")) {
                input_savings_capital.setSelected(true);
            } else {
                input_withdrawals_capital.setSelected(true);
            }

            input_capital_bank_name.setText(bank_name);
            update_capital_date.setText(capital_date);
            input_capital_amount.setText(capital_amount);
            input_capital_addtional_information.setText(capital_details);
            capital_transaction_id.setText(capital_id);

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_capital_summary_tableMouseClicked

    private void button_update_capitalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_update_capitalMouseClicked

        try {

            Capital cap = new Capital();

//      Getting capital transaction id
            String capital_id = capital_transaction_id.getText();

//      Getting capital type
            input_savings_capital.setActionCommand("Deposit");
            input_withdrawals_capital.setActionCommand("Withdrawal");
            String capital_type = input_capital_group.getSelection().getActionCommand();

//      Gettting bank name
            String bank_name = input_capital_bank_name.getText();

//      Getting capital date
            String capital_date = update_capital_date.getText();

//      Getting capital value
            String unformatted_capital_value = input_capital_amount.getText();
            String formatted_capital_value = unformatted_capital_value.replaceAll("[^0-9.]", "");
            Double capital_value = Double.parseDouble(formatted_capital_value);

//      Getting capital addtional details
            String capital_details = input_capital_addtional_information.getText();

            int result = cap.updateCapital(capital_id, capital_date, capital_type, bank_name, capital_details, capital_value);

            if (result == 1) {
                displaySuccessMessage("Capital transaction successfully updated !");
                refreshCapitalPanel();
                resetCapitalValues();
                setCapitalSubmitInterface();

            } else {
                displayCustomErrorMessage("Error : Unable to update capital transaction");
            }

        } catch (Exception e) {
            displayErrorMessage(e);
        }

    }//GEN-LAST:event_button_update_capitalMouseClicked

    private void button_update_capitalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_update_capitalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_update_capitalActionPerformed

    private void button_reset_capital_fieldsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_reset_capital_fieldsMouseClicked

        try {

            resetCapitalValues();
            setCapitalSubmitInterface();

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_reset_capital_fieldsMouseClicked

    private void button_reset_capital_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_reset_capital_fieldsActionPerformed

    }//GEN-LAST:event_button_reset_capital_fieldsActionPerformed

    private void income_summary_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_income_summary_tableMouseClicked

        try {

            int income_table_row = income_summary_table.getSelectedRow();

            String income_id = income_summary_table.getValueAt(income_table_row, 0).toString();
            String income_date = income_summary_table.getValueAt(income_table_row, 1).toString();
            String income_category = income_summary_table.getValueAt(income_table_row, 2).toString();
            String income_type = income_summary_table.getValueAt(income_table_row, 3).toString();
            String income_details = income_summary_table.getValueAt(income_table_row, 4).toString();
            String income_amount = income_summary_table.getValueAt(income_table_row, 5).toString();

            input_income_date.setVisible(false);
            update_income_date.setVisible(true);

            button_reset_income_fields.setVisible(true);
            button_submit_income.setVisible(false);
            button_update_income.setVisible(true);

            income_transaction_label.setVisible(true);
            income_transaction_id.setVisible(true);

            if (income_category.equals("Main")) {
                input_main_income.setSelected(true);
            } else {
                input_other_income.setSelected(true);
            }

            input_income_type.setText(income_type);
            update_income_date.setText(income_date);
            input_income_amount.setText(income_amount);
            input_income_addtional_information.setText(income_details);
            income_transaction_id.setText(income_id);

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_income_summary_tableMouseClicked

    private void button_submit_incomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_submit_incomeMouseClicked

        try {

            Income inc = new Income();

//      Getting income category
            input_main_income.setActionCommand("Main");
            input_other_income.setActionCommand("Other");
            String income_category = input_income_group.getSelection().getActionCommand();

//      Gettting income method
            String income_type = input_income_type.getText();

//      Getting income date
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            formater.format(input_income_date.getDate());
            String income_date = formater.format(cal.getTime()).toString();

//      Getting income value
            String unformatted_income_value = input_income_amount.getText();
            String formatted_income_value = unformatted_income_value.replaceAll("[^0-9.]", "");
            Double income_value = Double.parseDouble(formatted_income_value);

//      Getting Income addtional details
            String income_details = input_income_addtional_information.getText();

            int result = inc.setIncome(income_date, income_category, income_type, income_details, income_value);

            if (result == 1) {
                displaySuccessMessage("Income transaction successfully added !");
                refreshIncomePanel();
                resetIncomeValues();
                setIncomeSubmitInterface();
            } else {
                displayCustomErrorMessage("Error : Unable to add income transaction into the system");
            }

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_submit_incomeMouseClicked

    private void button_submit_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_submit_incomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_submit_incomeActionPerformed

    private void button_update_incomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_update_incomeMouseClicked

        try {

            Income inc = new Income();

//      Getting income transaction id
            String income_id = income_transaction_id.getText();

//      Getting income category
            input_main_income.setActionCommand("Main");
            input_other_income.setActionCommand("Other");
            String income_category = input_income_group.getSelection().getActionCommand();

//      Gettting income type
            String income_type = input_income_type.getText();

//      Getting income date
            String income_date = update_income_date.getText();

//      Getting income value
            String unformatted_income_value = input_income_amount.getText();
            String formatted_income_value = unformatted_income_value.replaceAll("[^0-9.]", "");
            Double income_value = Double.parseDouble(formatted_income_value);

//      Getting income addtional details
            String income_details = input_income_addtional_information.getText();

            int result = inc.updateIncome(income_id, income_date, income_category, income_type, income_details, income_value);

            if (result == 1) {
                displaySuccessMessage("Income transaction successfully updated !");
                refreshIncomePanel();
                resetIncomeValues();
                setIncomeSubmitInterface();

            } else {
                displayCustomErrorMessage("Error : Unable to update income transaction");
            }

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_update_incomeMouseClicked

    private void button_update_incomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_update_incomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_update_incomeActionPerformed

    private void button_reset_expense_fieldsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_reset_expense_fieldsMouseClicked

        try {

            resetExpenseValues();
            setExpenseSubmitInterface();

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_reset_expense_fieldsMouseClicked

    private void button_reset_expense_fieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_reset_expense_fieldsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_reset_expense_fieldsActionPerformed

    private void button_submit_expenseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_submit_expenseMouseClicked

        try {

            Expense exp = new Expense();

//      Getting expense category
            String expense_type = input_expense_type.getSelectedItem().toString();

//      Gettting expense item
            String expense_item = input_expense_item_name.getText();

//      Getting expense date
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            formater.format(input_expense_date.getDate());
            String expense_date = formater.format(cal.getTime()).toString();

//      Getting expense value
            String unformatted_expense_value = input_expense_amount.getText();
            String formatted_expense_value = unformatted_expense_value.replaceAll("[^0-9.]", "");
            Double expense_value = Double.parseDouble(formatted_expense_value);

//      Getting expense addtional details
            String expense_details = input_expense_addtional_information.getText();

            int result = exp.setExpense(expense_date, expense_type, expense_item, expense_details, expense_value);

            if (result == 1) {
                displaySuccessMessage("Expense transaction successfully added !");
                refreshExpensePanel();
                resetExpenseValues();
                setExpenseSubmitInterface();
            } else {
                displayCustomErrorMessage("Error : Unable to add expense transaction into the system");
            }

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_submit_expenseMouseClicked

    private void button_submit_expenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_submit_expenseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_submit_expenseActionPerformed

    private void button_update_expenseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button_update_expenseMouseClicked

        try {

            Expense exp = new Expense();

//      Getting expense transaction id
            String expense_id = expense_transaction_id.getText();

//      Getting expense category
            String expense_type = input_expense_type.getSelectedItem().toString();

//      Gettting expense item
            String expense_item = input_expense_item_name.getText();

//      Getting expense date
            String expense_date = update_expense_date.getText();

//      Getting expense value
            String unformatted_expense_value = input_expense_amount.getText();
            String formatted_expense_value = unformatted_expense_value.replaceAll("[^0-9.]", "");
            Double expense_value = Double.parseDouble(formatted_expense_value);

//      Getting expense addtional details
            String expense_details = input_expense_addtional_information.getText();

            int result = exp.updateExpense(expense_id, expense_date, expense_type, expense_item, expense_details, expense_value);

            if (result == 1) {
                displaySuccessMessage("Expense transaction successfully updated !");
                refreshExpensePanel();
                resetExpenseValues();
                setExpenseSubmitInterface();

            } else {
                displayCustomErrorMessage("Error : Unable to update expense transaction");
            }

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_button_update_expenseMouseClicked

    private void button_update_expenseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_update_expenseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_button_update_expenseActionPerformed

    private void expense_summary_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expense_summary_tableMouseClicked

        try {

            int expense_table_row = expense_summary_table.getSelectedRow();

            String expense_id = expense_summary_table.getValueAt(expense_table_row, 0).toString();
            String expense_date = expense_summary_table.getValueAt(expense_table_row, 1).toString();
            String expense_category = expense_summary_table.getValueAt(expense_table_row, 2).toString();
            String expense_type = expense_summary_table.getValueAt(expense_table_row, 3).toString();
            String expense_details = expense_summary_table.getValueAt(expense_table_row, 4).toString();
            String expense_amount = expense_summary_table.getValueAt(expense_table_row, 5).toString();

            input_expense_date.setVisible(false);
            update_expense_date.setVisible(true);

            button_reset_expense_fields.setVisible(true);
            button_submit_expense.setVisible(false);
            button_update_expense.setVisible(true);

            expense_transaction_label.setVisible(true);
            expense_transaction_id.setVisible(true);

            input_expense_type.setSelectedItem(expense_category);
            input_expense_item_name.setText(expense_type);
            update_expense_date.setText(expense_date);
            input_expense_amount.setText(expense_amount);
            input_expense_addtional_information.setText(expense_details);
            expense_transaction_id.setText(expense_id);

        } catch (Exception e) {
            displayErrorMessage(e);
        }
    }//GEN-LAST:event_expense_summary_tableMouseClicked

    private void update_expense_dateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_update_expense_dateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_update_expense_dateMouseClicked

    private void income_statistics_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_income_statistics_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_income_statistics_tableMouseClicked

    private void expense_statistics_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expense_statistics_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_expense_statistics_tableMouseClicked

    private void capital_statistics_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_capital_statistics_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_capital_statistics_tableMouseClicked

    private void detailed_capital_statistics_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailed_capital_statistics_tableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_detailed_capital_statistics_tableMouseClicked

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
    private javax.swing.JPanel admin_dashboard_panel;
    private javax.swing.JLabel admin_icon;
    private javax.swing.JLabel administrator_icon;
    private javax.swing.JLabel administrator_label;
    private javax.swing.JPanel administrator_panel;
    private javax.swing.JPanel balance_btn;
    private javax.swing.JPanel button_administrator;
    private javax.swing.JPanel button_administrator_login;
    private javax.swing.JPanel button_capital;
    private javax.swing.JPanel button_dashboard;
    private javax.swing.JPanel button_expenses;
    private javax.swing.JPanel button_income;
    private javax.swing.JButton button_reset_capital_fields;
    private javax.swing.JButton button_reset_expense_fields;
    private javax.swing.JButton button_reset_income_fields;
    private javax.swing.JButton button_submit_capital;
    private javax.swing.JButton button_submit_expense;
    private javax.swing.JButton button_submit_income;
    private javax.swing.JButton button_update_capital;
    private javax.swing.JButton button_update_expense;
    private javax.swing.JButton button_update_income;
    private javax.swing.JLabel capital_addtional_information_label2;
    private javax.swing.JLabel capital_amount_label3;
    private javax.swing.JLabel capital_date_label;
    private javax.swing.JLabel capital_icon;
    private javax.swing.JLabel capital_label;
    private javax.swing.JLabel capital_method_label;
    private javax.swing.JPanel capital_panel;
    private javax.swing.JTable capital_statistics_table;
    private javax.swing.JScrollPane capital_statistics_table_scroll;
    private javax.swing.JTable capital_summary_table;
    private javax.swing.JScrollPane capital_summary_table_scroll;
    private javax.swing.JLabel capital_transaction_id;
    private javax.swing.JLabel capital_transaction_label;
    private javax.swing.JLabel capital_type_label;
    private javax.swing.JPanel content_panel;
    private javax.swing.JLabel current_month_balance_stat;
    private javax.swing.JLabel current_month_expense_stat;
    private javax.swing.JLabel current_month_income_stat;
    private javax.swing.JLabel current_month_savings_stat;
    private javax.swing.JLabel dashboard_icon;
    private javax.swing.JLabel dashboard_label;
    private javax.swing.JPanel dashboard_panel;
    private javax.swing.JTable detailed_capital_statistics_table;
    private javax.swing.JScrollPane detailed_capital_statistics_table_scroll;
    private javax.swing.JLabel expense_addtional_information_label;
    private javax.swing.JScrollPane expense_addtional_information_scroll;
    private javax.swing.JLabel expense_amount_label;
    private javax.swing.JLabel expense_date_label;
    private javax.swing.JLabel expense_item_name_label;
    private javax.swing.JPanel expense_panel;
    private javax.swing.JTable expense_statistics_table;
    private javax.swing.JScrollPane expense_statistics_table_scroll;
    private javax.swing.JTable expense_summary_table;
    private javax.swing.JScrollPane expense_summary_table_scroll;
    private javax.swing.JLabel expense_transaction_id;
    private javax.swing.JLabel expense_transaction_label;
    private javax.swing.JLabel expense_type_label;
    private javax.swing.JLabel expenses_icon;
    private javax.swing.JLabel expenses_label;
    private javax.swing.JLabel income_addtional_information_label;
    private javax.swing.JScrollPane income_addtional_information_scroll;
    private javax.swing.JLabel income_amount_label;
    private javax.swing.JLabel income_date_label;
    private javax.swing.JLabel income_icon;
    private javax.swing.JLabel income_label;
    private javax.swing.JLabel income_method_label;
    private javax.swing.JPanel income_panel;
    private javax.swing.JTable income_statistics_table;
    private javax.swing.JScrollPane income_statistics_table_scroll;
    private javax.swing.JTable income_summary_table;
    private javax.swing.JLabel income_transaction_id;
    private javax.swing.JLabel income_transaction_label;
    private javax.swing.JLabel income_type_label;
    private javax.swing.JScrollPane incomel_summary_table_scroll;
    private javax.swing.JLabel incorrect_password_label;
    private javax.swing.JPasswordField input_administrator_password;
    private javax.swing.JTextArea input_capital_addtional_information;
    private javax.swing.JScrollPane input_capital_addtional_information_scroll;
    private javax.swing.JTextField input_capital_amount;
    private javax.swing.JTextField input_capital_bank_name;
    private org.jdesktop.swingx.JXDatePicker input_capital_date;
    private javax.swing.ButtonGroup input_capital_group;
    private javax.swing.JTextArea input_expense_addtional_information;
    private javax.swing.JTextField input_expense_amount;
    private org.jdesktop.swingx.JXDatePicker input_expense_date;
    private javax.swing.JTextField input_expense_item_name;
    private javax.swing.JComboBox<String> input_expense_type;
    private javax.swing.JTextArea input_income_addtional_information;
    private javax.swing.JTextField input_income_amount;
    private org.jdesktop.swingx.JXDatePicker input_income_date;
    private javax.swing.ButtonGroup input_income_group;
    private javax.swing.JTextField input_income_type;
    private javax.swing.JRadioButton input_main_income;
    private javax.swing.JRadioButton input_other_income;
    private javax.swing.JRadioButton input_savings_capital;
    private javax.swing.JRadioButton input_withdrawals_capital;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel logo_icon;
    private javax.swing.JLabel logo_label;
    private javax.swing.JSeparator logo_separator;
    private javax.swing.JPanel navigation_panel;
    private javax.swing.JTextField update_capital_date;
    private javax.swing.JTextField update_expense_date;
    private javax.swing.JTextField update_income_date;
    // End of variables declaration//GEN-END:variables
}
