
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dawoo
 */
public class initWork {
    //DB연동 객체
    static DB_MAN DBM = new DB_MAN();
    
    // 근무시간 레코드 삽입
    static void insertWork(String emp_no, String today){
        String work_id; // 근무ID 
        String todayStr = ""; // 오늘 날짜 텍스트화
        String[] strList = today.split("-");
        for(String str : strList){
            todayStr += str;
        }
        work_id = emp_no + todayStr; // 근무ID = 사원번호+오늘날짜
        String sql = "insert into work values('"+work_id+"','"+emp_no+"','"+today+"','','','')"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(sql);
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    // 근무시간 삽입여부 체크
    static boolean isCreated(String today){
        String sql = "Select work_id From work where work_date ='"+today+"'"; 
        String work_id;
        int cnt = 0;
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            
            while(DBM.rs.next()){
                work_id = DBM.rs.getString("work_id");
                cnt++; // 정보 있을경우 1
                return false;
            }
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return true;
    }
    
    // 파일 레코드 삽입
    static String insertFile(String today){
        String file_id = null; // 파일ID 
        String todayStr = ""; // 오늘 날짜 텍스트화
        String[] strList = today.split("-");
        for(String str : strList){
            todayStr += str;
        }
               
        // 파일 레코드 삽입
        file_id = "file" + todayStr; // 근무일지ID = day+오늘날짜
        String sql = "insert into file values('"+file_id+"','');"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(sql);
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return file_id;
    }
    
    // 업무일지 레코드 삽입
    static void insertDay(String emp_no, String today){
        String day_id=""; // 근무일지ID 
        String work_id=""; // 근무ID 
        String day_content=null; // 근무일지 내용 
        String file_id= insertFile(today); // 파일 레코드 생성, 파일 id 리턴
        
        String todayStr = ""; // 오늘 날짜 텍스트화
        String[] strList = today.split("-");
        for(String str : strList){
            todayStr += str;
        }
        
        // 근무아이디 검색
        String sql = "select work_id from work where work_date ='"+today+"' and emp_no ='"+emp_no+"';"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            while(DBM.rs.next()){
                work_id = DBM.rs.getString("work_id");
            }
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
              
        // 업무 레코드 삽입
        day_id = "day" + todayStr; // 근무일지ID = day+오늘날짜
        sql = "insert into day values('"+day_id+"','"+work_id+"','"+day_content+"','"+file_id+"');"; 
        try{
            DBM.stmt.executeUpdate(sql);
            DBM.stmt.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    // 업무일지 삽입여부 체크
    static boolean isCreatedDaily(String emp_no, String today){
        String sql = "select count(*) from day a, work b where a.work_id = b.work_id and work_date ='"+today+"' and emp_no ='"+emp_no+"'"; 
        int cnt = 0;
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            while(DBM.rs.next()){
                cnt = DBM.rs.getInt(1);
                if(cnt > 0)
                   return true; // 있음
            }
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return false; //없음
    }
}