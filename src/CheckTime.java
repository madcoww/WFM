
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dawoo
 */
public class CheckTime {
    //DB연동 객체
    static DB_MAN DBM = new DB_MAN();
    
    // 출근시간 삽입
    static void insertStartTime(String emp_no, String work_date){
        String sql = "update work set work_start = SYSDATE() where emp_no = '"+emp_no+"' and work_date = '"+work_date+"'"; 
        insert(sql);
    }
    
    // 퇴근시간 삽입
    static void insertEndTime(String emp_no, String work_date){
        String sql = "update work set work_end = SYSDATE() where emp_no = '"+emp_no+"' and work_date = '"+work_date+"'"; 
        insert(sql);
    }
    
    // 근무시간 삽입
    static void insertWorkTime(String emp_no, String work_date, String work_time){
        String sql = "update work set work_time = '"+ work_time +"' where emp_no = '"+emp_no+"' and work_date = '"+work_date+"'"; 
        insert(sql);
    }
    
    // 삽입 메소드
    static void insert(String sql){
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(sql);
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    // 출근시간 생성여부 확인
    static boolean isStarted(String emp_no, String work_date){
        String sql = "Select work_start From work where emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        return isNow("work_start", sql);
    }
    
    // 퇴근시간 생성여부 확인
    static boolean isEned(String emp_no, String work_date){
        String sql = "Select work_end From work where emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        return isNow("work_end", sql);
    }
    
    //생성여부 확인
    static boolean isNow(String type, String sql){
        String time;
        int cnt = 0;
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            
            while(DBM.rs.next()){
                time = DBM.rs.getString(type);
                if(time != null) {
                    cnt++; // 정보 있을경우 1
                    System.out.println("정보 있음");
                }
            }
            if(cnt>0) return true;
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return false;
    }
    
    // 해당 사원의 해당일 근무시간 찾기
    static String getWorkTime(String emp_no, String work_date){
        String sql = "Select work_time From work where emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        return getTime("work_time", sql);
    }
    
    // 해당 사원의 해당일 출근시간 찾기
    static String getStartime(String emp_no, String work_date){
        String sql = "Select work_start From work where emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        return getTime("work_start", sql);
    }
    
    // 해당 사원의 해당일 퇴근시간 찾기
    static String getEndTime(String emp_no, String work_date){
        String sql = "Select work_end From work where emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        return getTime("work_end", sql);
    }
    
    // 시간 찾기
    static String getTime(String type, String sql){ 
        String time = "";
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            
            while(DBM.rs.next()){
                time = DBM.rs.getString(type).trim();
            }
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            //System.out.println("getStartime : "+e.getMessage());
        }
        return time;
    }
    
    // 해당 사원의 해당일 근무 id 찾기
    static String getWorkId(String emp_no, String work_date){
        String work_id = "";
        String sql = "Select work_id From work where emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            
            while(DBM.rs.next()){
                work_id = DBM.rs.getString("work_id").trim();
            }            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("getWorkId : "+e.getMessage());
        }
        return work_id;
    }

    // 해당 사원 근무시간 정보 텍스트 set
    static String getTimeInfoString(String emp_no, String work_date){
        String timeInfo = "";
        String startTime = CheckTime.getStartime(emp_no, work_date);
        String endTime = CheckTime.getEndTime(emp_no, work_date);
        String workTime = CheckTime.getWorkTime(emp_no, work_date);
        
        startTime = startTime.equals("")? "없음":startTime;  // 출근시간
        endTime = endTime.equals("")? "없음":endTime;   // 퇴근시간
        workTime = workTime.equals("")? "없음":workTime;     // 근무시간
        
        if(startTime.equals("없음") && endTime.equals("없음") && workTime.equals("없음")){
            timeInfo = "출근전";
        }else{
            timeInfo += "출근시간 : " + startTime +"\n";
            timeInfo += "퇴근시간 : " + endTime +"\n";
            timeInfo += "총 근무시간 : " + workTime +"\n";
        }
        return timeInfo;
    }
    
    // 해당 사원 근무시간 정보 텍스트 set
    static String getTimeInfoString(String emp_no, String work_date, int dateDay, String todayStr) throws ParseException{
        
        //입사일 찾기
        String emp_hiredate = null;
        try{
            String sql = "Select emp_hiredate From emp where emp_no = '"+emp_no+"'"; 
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            
            while(DBM.rs.next()){
                emp_hiredate = DBM.rs.getString("emp_hiredate");
            }
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        
        String timeInfo = "";
        String startTime = CheckTime.getStartime(emp_no, work_date);
        String endTime = CheckTime.getEndTime(emp_no, work_date);
        String workTime = CheckTime.getWorkTime(emp_no, work_date);
        
        startTime = startTime.equals("")? "없음":startTime;  // 출근시간
        endTime = endTime.equals("")? "없음":endTime;   // 퇴근시간
        workTime = workTime.equals("")? "없음":workTime;     // 근무시간
                    
        //오늘날짜 변환
        Date today = null;
        SimpleDateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
        today = dfm.parse(todayStr);
        
        //입사일 변환
        Date hireDate = null;
        hireDate = dfm.parse(emp_hiredate);
        
        //선택된 날짜 변환
        Date selectedDay = null;
        selectedDay = dfm.parse(work_date);
                
        
        if(!startTime.equals("없음") || !endTime.equals("없음") || !workTime.equals("없음")){
            timeInfo += "출근시간 : " + startTime +"\n";
            timeInfo += "퇴근시간 : " + endTime +"\n";
            timeInfo += "총 근무시간 : " + workTime +"\n";
        }else if(selectedDay.before(hireDate)){
            timeInfo = "입사전"; // 입사 전
        }else if(selectedDay.equals(today)){
            timeInfo = "출근전"; // 입사 전
        }else if(selectedDay.after(today)){
            timeInfo = ""; // 아직 근무일 아님
        }else if(dateDay ==1 || dateDay ==7){
            timeInfo = "휴무"; // 토, 일은 휴무
        }else{
            timeInfo = "결근"; // 결근일
        }
        return timeInfo;
    }
}
