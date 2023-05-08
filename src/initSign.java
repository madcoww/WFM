
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dawoo
 */
public class initSign {
    //DB연동 객체
    static DB_MAN DBM = new DB_MAN();
    
    /*테이블 읽는 메소드*/
    static DefaultTableModel selectSignTable(){
        JTable SignTable = new JTable();
        DefaultTableModel signTableModel = new DefaultTableModel(); // 반환할 모델
        
        ArrayList<signConDTO> list = new ArrayList<>();
    
        String sql = "SELECT * FROM sign ORDER BY emp_no";
        return setTableBySql(list, sql); // 테이블 생성해서 모델 반환
    }
    
    /*구분으로 테이블 검색*/
    static DefaultTableModel searchSignTableByCheck(String check, String signOk){
        
        String signOkStatus = null; // 승인처리상태 변환
        
        if(signOk.equals("대기")){
            signOkStatus = "0";
        }else if(signOk.equals("승인")){
            signOkStatus = "1";
        }else if(signOk.equals("반려")){
            signOkStatus = "2";
        }
        ArrayList<signConDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM sign where sign_check = '" + check + "' and sign_ok = " + signOkStatus +" ORDER BY emp_no";
        System.out.println(sql);
        
        
        return setTableBySql(list, sql); // 테이블 생성해서 모델 반환
    }
    
    //컬럼명 생성하는 메소드
    static void setColumn(DefaultTableModel signTableModel){
        signTableModel.addColumn("번호");
        signTableModel.addColumn("구분");
        signTableModel.addColumn("시작일");
        signTableModel.addColumn("종료일");
        signTableModel.addColumn("기간");
        signTableModel.addColumn("처리상태");
        signTableModel.addColumn("승인자");
    }
    
    //컬럼명 생성하는 메소드
    static DefaultTableModel setTableBySql(ArrayList<signConDTO> list, String sql){
        JTable SignTable = new JTable();
        DefaultTableModel signTableModel = new DefaultTableModel(); // 반환할 모델
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                signConDTO userInfo = new signConDTO(DBM.rs.getString("sign_id"), DBM.rs.getString("emp_no"),
                                                        DBM.rs.getString("sign_start"),DBM.rs.getString("sign_end"),
                                                        DBM.rs.getString("sign_check"), DBM.rs.getString("sign_content"),
                                                        DBM.rs.getString("sign_mgr"), DBM.rs.getInt("sign_ok"), DBM.rs.getString("sign_per"));
                list.add(userInfo);
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        
        signTableModel = (DefaultTableModel)SignTable.getModel();
        setColumn(signTableModel);
        
        int cnt = 1; // 번호
        for(int i=0; i<list.size(); i++){
            signConDTO searchRes = list.get(i);
            
            String signOk = null;
            if(searchRes.getSign_ok() == 0){
                signOk = "대기";
            }else if(searchRes.getSign_ok() == 1){
                signOk = "승인";
            }else if(searchRes.getSign_ok() == 2){
                signOk = "반려";
            }
            
            signTableModel.insertRow(i, new Object[]{cnt, searchRes.getSign_check(), searchRes.getSign_start(), searchRes.getSign_end(), searchRes.getSign_per(), signOk, searchRes.getSign_mgr()});
            cnt++;
        }
        
        return signTableModel;
    }
    
    //컬럼명 업데이트하는 메소드
    static void updateTableBySql(String sql){
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.pstmt.executeUpdate(sql);
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    // id값 구하기
    static String selectSignId(String startDate, String endDate, String check) {
        String sql = "select sign_id FROM sign where sign_start = '" + startDate + "' and sign_end = '" + endDate + "' and sign_check = '" + check +"'";
        String sign_id = null;
        System.out.println(sql);
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                sign_id = DBM.rs.getString("sign_id");
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return sign_id;
    }
    
    static signConDTO selectOne(String sign_id){
        String sql = "select * FROM sign where sign_id = '" + sign_id + "'";
        signConDTO userInfo = null;
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                userInfo = new signConDTO(DBM.rs.getString("sign_id"), DBM.rs.getString("emp_no"),
                                                        DBM.rs.getString("sign_start"),DBM.rs.getString("sign_end"),
                                                        DBM.rs.getString("sign_check"), DBM.rs.getString("sign_content"),
                                                        DBM.rs.getString("sign_mgr"), DBM.rs.getInt("sign_ok"), DBM.rs.getString("sign_per"));
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return userInfo;
    }
    
    // 삭제 메소드   
    static void deleteSign(String startDate, String endDate, String check){   
        String sign_id = selectSignId(startDate, endDate, check); // id값 구하기
        
        String sql = "delete FROM sign where sign_id = '" + sign_id +"'";
        updateTableBySql(sql);
    }
      //전자결제함 읽는 메소드
    static DefaultTableModel selectAppBoxTable(String emp_no){
        JTable AppBoxTable = new JTable();
        DefaultTableModel AppBoxTableModel = new DefaultTableModel(); // 반환할 모델
        
        ArrayList<signConDTO> list = new ArrayList<>();
    
        String sql = "SELECT * FROM sign WHERE sign_mgr = '" + emp_no + "' ORDER BY emp_no";
        return setAppBoxTableBySql(list, sql); // 테이블 생성해서 모델 반환
    }
    
        //컬럼명 생성하는 메소드
    static void setAppBoxColumn(DefaultTableModel signTableModel){
        signTableModel.addColumn("번호");
        signTableModel.addColumn("구분");
        signTableModel.addColumn("시작일");
        signTableModel.addColumn("종료일");
        signTableModel.addColumn("기간");
        signTableModel.addColumn("기안자");
        signTableModel.addColumn("처리상태");
    }
    
    static DefaultTableModel setAppBoxTableBySql(ArrayList<signConDTO> list, String sql){
        JTable AppBoxTable = new JTable();
        DefaultTableModel AppBoxTableModel = new DefaultTableModel(); // 반환할 모델
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                signConDTO userInfo = new signConDTO(DBM.rs.getString("sign_id"), DBM.rs.getString("emp_no"),
                                                        DBM.rs.getString("sign_start"),DBM.rs.getString("sign_end"),
                                                        DBM.rs.getString("sign_check"), DBM.rs.getString("sign_content"),
                                                        DBM.rs.getString("sign_mgr"), DBM.rs.getInt("sign_ok"), DBM.rs.getString("sign_per"));
                list.add(userInfo);
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        
        AppBoxTableModel = (DefaultTableModel)AppBoxTable.getModel();
        setAppBoxColumn(AppBoxTableModel);
        
        int cnt = 1; // 번호
        for(int i=0; i<list.size(); i++){
            signConDTO searchRes = list.get(i);
            int searchResSignOk = list.get(i).getSign_ok();
            String searchResSignOkString = "";
            switch (searchResSignOk){
                case 0: searchResSignOkString = "대기"; break;
                case 1: searchResSignOkString = "승인"; break;
                case 2: searchResSignOkString = "반려"; break;
            }
            AppBoxTableModel.insertRow(i, new Object[]{cnt, searchRes.getSign_check(), searchRes.getSign_start(), searchRes.getSign_end(), searchRes.getSign_per(), searchRes.getEmp_no(), searchResSignOkString});
            cnt++;
        }
        
        return AppBoxTableModel;
    }
    
    static DefaultTableModel rejectSign(String sign_id, String emp_no){   
        ArrayList<signConDTO> list = new ArrayList<>();
        String updateSql = "UPDATE sign SET sign_ok = 2 WHERE sign_id ='" + sign_id+"'";
        updateTableBySql(updateSql);
        
        String selectSql = "SELECT * FROM sign WHERE sign_mgr = '" + emp_no + "'";
        return setAppBoxTableBySql(list, selectSql);
    }
    
    static DefaultTableModel applySign(String sign_id, String emp_no){   
        ArrayList<signConDTO> list = new ArrayList<>();
        String updateSql = "UPDATE sign SET sign_ok = 1 WHERE sign_id ='" + sign_id+"'";
        updateTableBySql(updateSql);
        
        String selectSql = "SELECT * FROM sign WHERE sign_mgr = '" + emp_no + "'";
        return setAppBoxTableBySql(list, selectSql);
    }
    
}
