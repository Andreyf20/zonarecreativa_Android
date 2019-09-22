package database;

import android.util.Log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class RequestLogin {
    private Connection con;

    public RequestLogin(Connection con) {
        this.con = con;
    }

    public int loginAttempt(String username, String password){
        String call = "{call spLogin(?, ?, ?)}";
        CallableStatement cs = null;
        int result;
        try {
            cs = con.prepareCall(call);
            cs.setString(1, username);
            cs.setString(2, password);
            cs.registerOutParameter(3, Types.INTEGER);

            cs.execute();

            result =  cs.getInt(3);
        } catch (SQLException e) {
            e.printStackTrace();
            result = -2;
        }
        return result;
    }
}
