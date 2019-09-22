package database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static final String url = "jdbc:mysql://192.168.0.104:3306/zonarecreativacr";
    public static final String username = "Andrey";
    public static final String password = "Andrey";
    public static Connection dbc = null;

    public static Connection getInstance(){
        if(dbc == null){
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                dbc = DriverManager.getConnection(url, username, password);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbc;
    }

}
