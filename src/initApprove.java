

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kim-wonjun
 */
public class initApprove {
    
    //DB연동 객체
    static DB_MAN DBM = new DB_MAN(); 

    
    static DefaultTableModel selectCond(String check, String start, String end){
       
    ArrayList<appBoxDTO> list = new ArrayList<>();
    
    
    String sql = null;
    
    if(start == null && end == null){
        sql = String.format("select * from sign where sign_check='%s'", check);
    }else if(start != null && end == null){
        sql = String.format("select * from sign where sign_check='%s'and sign_start='%s'", check, start);
    }else if(start == null && end != null){
        sql = String.format("select * from sign where sign_check='%s'and sign_end='%s'", check, end);
    }else{
        sql = String.format("select * from sign where sign_check='%s'and sign_start='%s' and sign_end='%s'", check, start, end);
    }
    
    return setTableBySql(list, sql);  
    }
    
    static DefaultTableModel setTableBySql(ArrayList<appBoxDTO> list, String sql){
        JTable approveTable = new JTable();
        DefaultTableModel approveTableModel = new DefaultTableModel(); // 반환할 모델
        try{
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(sql);
            DBM.rs = DBM.pstmt.executeQuery();
            
            while(DBM.rs.next()){
                
               
                
                
                appBoxDTO searchInfo = new appBoxDTO(DBM.rs.getString("sign_id"), DBM.rs.getString("sign_check"),
                                        DBM.rs.getString("sign_start"), DBM.rs.getString("sign_end"),
                                        DBM.rs.getString("sign_per"), DBM.rs.getString("emp_no"), Integer.parseInt(DBM.rs.getString("sign_ok")));
                list.add(searchInfo);
            }
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        
        approveTableModel = (DefaultTableModel)approveTable.getModel();
        setColumn(approveTableModel);
        
        int cnt = 0; // 번호
        for(int i=0; i<list.size(); i++){
            appBoxDTO searchRes = list.get(i);
            
            int signOk = searchRes.getSign_ok();
            String result = null;
            if(signOk == 0){
                result = "대기";
            }else if(signOk == 1){
                result = "승인";
            }else{
                result = "반려";
            }
            
            approveTableModel.insertRow(i, new Object[]{cnt, searchRes.getSign_check(), searchRes.getSign_start(), searchRes.getSign_end(), searchRes.getSign_per(), searchRes.getEmp_no() ,result});
            cnt++;
        }
        
        return approveTableModel;
    }
    static void setColumn(DefaultTableModel signTableModel){
        signTableModel.addColumn("번호");
        signTableModel.addColumn("구분");
        signTableModel.addColumn("시작일");
        signTableModel.addColumn("종료일");
        signTableModel.addColumn("기간");
        signTableModel.addColumn("기안자");
        signTableModel.addColumn("처리상태");
    }
}
