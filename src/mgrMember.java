
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author 박상용
 */
public class mgrMember {
    //DB연동 객체
    DB_MAN DBM = new DB_MAN();
    
    /*DB 핸들링 시작*/
    
    /*검색 시작*/
    public ArrayList<String> getDeptInfo (){    
        ArrayList<String> dept = new ArrayList<>();
        
        String strQuery = "Select dept_name From dept";  
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(strQuery);
            
            while(DBM.rs.next()){
                dept.add(DBM.rs.getString("dept_name"));
            }
            DBM.rs.close();
            DBM.conn.close();
            return dept;
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return null; //DB 오류
    }
    
    public String getDeptNameByDeptId(String deptId){
        String sql = "SELECT dept_name FROM dept WHERE dept_id = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, deptId);
            DBM.rs = DBM.pstmt.executeQuery();
            
            if(DBM.rs.next()){
              String result = DBM.rs.getString("dept_name");
              DBM.dbClose();
              return result;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; // DB 오류
    }
    
    public String getDeptIdByDeptName(String deptName){
        String sql = "SELECT dept_id FROM dept WHERE dept_name = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, deptName);
            DBM.rs = DBM.pstmt.executeQuery();
            
            if(DBM.rs.next()){
              String result = DBM.rs.getString("dept_id");
              DBM.dbClose();
              return result;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; // DB 오류
    }
     public String getLevelIdByLevelNum(String LevelNum){
          String sql = "SELECT level_id FROM level WHERE level_num = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, LevelNum);
            DBM.rs = DBM.pstmt.executeQuery();
            
            if(DBM.rs.next()){
              String result = DBM.rs.getString("level_id");
              DBM.dbClose();
              return result;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; // DB 오류
    }
    
    public String getLevelNumByLevelId(String LevelId){
          String sql = "SELECT level_num FROM level WHERE level_id = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, LevelId);
            DBM.rs = DBM.pstmt.executeQuery();
            
            if(DBM.rs.next()){
              String result = DBM.rs.getString("level_num");
              DBM.dbClose();
              return result;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; // DB 오류
    }
    
    public ArrayList<mgrMemberDTO> getInitMemberList(){
        ArrayList<mgrMemberDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM emp ORDER BY emp_no";
         try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                mgrMemberDTO userInfo = new mgrMemberDTO(DBM.rs.getString("emp_no"), DBM.rs.getString("emp_name"),
                                                        DBM.rs.getString("emp_mail"),DBM.rs.getString("emp_num"),
                                                        DBM.rs.getString("dept_id"), DBM.rs.getString("emp_rankno"),
                                                        DBM.rs.getString("emp_hiredate"), DBM.rs.getString("level_id"), DBM.rs.getString("member_id"));
                list.add(userInfo);
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return list;
    }
    
    public ArrayList<mgrMemberDTO> getNameByUserInfo(String name){
        ArrayList<mgrMemberDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM emp JOIN dept ON emp.dept_id = dept.dept_id WHERE emp_name = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, name);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                mgrMemberDTO userInfo = new mgrMemberDTO(DBM.rs.getString("emp_no"), DBM.rs.getString("emp_name"),
                                                        DBM.rs.getString("emp_mail"),DBM.rs.getString("emp_num"),
                                                        DBM.rs.getString("dept_id"), DBM.rs.getString("emp_rankno"),
                                                        DBM.rs.getString("emp_hiredate"), DBM.rs.getString("level_id"), DBM.rs.getString("member_id"));
                list.add(userInfo);
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return list;
    }
        
    public ArrayList<mgrMemberDTO> getDeptByUserInfo(String dept){
        ArrayList<mgrMemberDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM emp JOIN dept ON emp.dept_id = dept.dept_id WHERE dept_name = ? ";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, dept);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                mgrMemberDTO userInfo = new mgrMemberDTO(DBM.rs.getString("emp_no"), DBM.rs.getString("emp_name"),
                                                        DBM.rs.getString("emp_mail"),DBM.rs.getString("emp_num"),
                                                        DBM.rs.getString("dept_id"), DBM.rs.getString("emp_rankno"),
                                                        DBM.rs.getString("emp_hiredate"), DBM.rs.getString("level_id"), DBM.rs.getString("member_id"));
                list.add(userInfo);
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        
        return list;
    }
    /*검색 끝*/
    
    /*수정 시작*/
    
    public int updateMember(String emp_no, String emp_name, String emp_mail, String emp_num, String dept_id, String emp_rankno, String emp_hiredate, String level_id){
        String sql = "UPDATE emp SET emp_name = ?, emp_mail = ?, emp_num = ?, dept_id = ?, emp_rankno = ?, emp_hiredate = ?, level_id = ? WHERE emp_no = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, emp_name);
            DBM.pstmt.setString(2, emp_mail);
            DBM.pstmt.setString(3, emp_num);
            DBM.pstmt.setString(4, dept_id);
            DBM.pstmt.setString(5, emp_rankno);
            DBM.pstmt.setString(6, emp_hiredate);
            DBM.pstmt.setString(7, level_id);
            DBM.pstmt.setString(8, emp_no);
            int result = DBM.pstmt.executeUpdate();
            DBM.dbClose();
            return result; // 성공적 수행 시 0이상의 값 반환
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1; // DB 오류
    }
    /*수정 종료*/
    
    /*삭제 시작*/
    public int deleteEmp(String emp_no){
        String sql = "DELETE FROM emp where emp_no = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, emp_no);
            int result = DBM.pstmt.executeUpdate();
            DBM.dbClose();
            return result; //성공적 수행 시 0이상의 값 반환
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1; //DB 오류
    }
    public int deleteMember(String member_id){
         String sql = "DELETE FROM member where member_id = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, member_id);
            int result = DBM.pstmt.executeUpdate();
            DBM.dbClose();
            return result; //성공적 수행 시 0이상의 값 반환
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1; //DB 오류
    }
    /*삭제 종료*/
    /*비밀번호 초기화*/
    
    public String getMemberIdByEmpNo(String emp_no){
        String sql = "SELECT member_id FROM emp where emp_no = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, emp_no);
            DBM.rs = DBM.pstmt.executeQuery();
            
            if(DBM.rs.next()){
                String result = DBM.rs.getString("member_id");
                DBM.dbClose();
                return result; //성공적으로 member_id 반환
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null; //오류
    }
    public int restPass(String member_id){
        String sql = "UPDATE member SET member_pw = ? WHERE member_id = ?";
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.setString(1, "123456789a");
            DBM.pstmt.setString(2, member_id);
            int result = DBM.pstmt.executeUpdate();
            DBM.dbClose();
            return result;// 성공적으로 수행 시 반환값 0이상
           
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1; // DB 오류
    }
    
}
