package budget.analyzer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connect {
    
        //Creating a static variable 
	private static DB_Connect instance;
        static public Connection con = null;
        private static String url = "jdbc:mysql://127.0.0.1/";
        private static String dbname = "budget_cal";
        private static String username = "root";
        private static String password = "";
        
	
	//Creating a private constructor(outside classes cannot access this)
	private DB_Connect(){}
	
	public static DB_Connect getInstance() {
            if(instance == null) {
                instance = new DB_Connect();
            }
            return instance;
	}
        
        public static void setConnection()throws ClassNotFoundException, SQLException {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url+dbname,username,password);
        }
        
        public static Connection getConnection(){
            return con;
        }
        
        public static boolean getConnectionStatus(){
            if(con != null){
                return true;
            }
            else {
                return false;
            }
        }
}
