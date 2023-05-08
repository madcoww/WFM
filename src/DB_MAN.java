import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 박상용*/
public class DB_MAN {
    String strDriver = "com.mysql.cj.jdbc.Driver";
    
    String strURL = "jdbc:mysql://localhost:3306/wfmdb";
    String dbUser = "root";
    String dbPass = "938271456aq";
    
    Connection conn;
    ResultSet rs;
    Statement stmt;
    PreparedStatement pstmt;
    
    public void getCon() throws IOException{
        try{
            Class.forName(strDriver);
            conn = DriverManager.getConnection(strURL, dbUser, dbPass);
        }catch(Exception e){
            e.printStackTrace(); // DB연동 오류
        }
    }
    
    
    public void dbClose() throws IOException{
        try{
            rs.close();
            if(pstmt != null){
                pstmt.close();
            }else{
                stmt.close();
            }
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
