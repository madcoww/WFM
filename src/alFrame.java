
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;

/**
 *
 * @author kim-wonjun
 */
public class alFrame extends javax.swing.JFrame {

    //DB Connect
    DB_MAN DBM = new DB_MAN();
    String empNo;
    String selectedSignId;
    
    public alFrame() {
        initComponents();

    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        btnLeave1_a = new javax.swing.JRadioButton();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        lblLeave_a = new javax.swing.JLabel();
        btnLeave2_a = new javax.swing.JRadioButton();
        lblDur_a = new javax.swing.JLabel();
        lblDur_a1 = new javax.swing.JLabel();
        cbxApp_a = new javax.swing.JComboBox<>();
        btnWriteOk_a = new javax.swing.JButton();
        lblName_a = new javax.swing.JLabel();
        lblRes_a = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtRes_a = new javax.swing.JTextArea();
        txtName_a = new javax.swing.JTextField();
        lblTitle = new javax.swing.JLabel();
        lblApp_a = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttonGroup1.add(btnLeave1_a);
        btnLeave1_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnLeave1_a.setText("연차");

        lblLeave_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblLeave_a.setText("구분");

        buttonGroup1.add(btnLeave2_a);
        btnLeave2_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnLeave2_a.setText("병가");

        lblDur_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblDur_a.setText("시작일");

        lblDur_a1.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblDur_a1.setText("종료일");

        cbxApp_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        cbxApp_a.setPreferredSize(new java.awt.Dimension(150, 30));

        btnWriteOk_a.setBackground(new java.awt.Color(204, 204, 204));
        btnWriteOk_a.setFont(new java.awt.Font("굴림", 1, 12)); // NOI18N
        btnWriteOk_a.setText("신청완료");
        btnWriteOk_a.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWriteOk_aActionPerformed(evt);
            }
        });

        lblName_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblName_a.setText("기안자");

        lblRes_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblRes_a.setText("신청사유");

        txtRes_a.setColumns(20);
        txtRes_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        txtRes_a.setRows(5);
        jScrollPane3.setViewportView(txtRes_a);

        txtName_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        txtName_a.setPreferredSize(new java.awt.Dimension(64, 30));

        lblTitle.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        lblTitle.setText("연차 신청");

        lblApp_a.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblApp_a.setText("승인자");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnWriteOk_a, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLeave_a, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLeave1_a)
                                .addGap(18, 18, 18)
                                .addComponent(btnLeave2_a))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblRes_a)
                                    .addComponent(lblDur_a)
                                    .addComponent(lblName_a))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(320, 320, 320)
                                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(208, 208, 208)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(102, 102, 102)
                                .addComponent(txtName_a, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(lblDur_a1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblApp_a, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addComponent(cbxApp_a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(lblTitle)
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName_a)
                    .addComponent(lblApp_a)
                    .addComponent(txtName_a, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxApp_a, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLeave_a)
                    .addComponent(btnLeave1_a)
                    .addComponent(btnLeave2_a))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDur_a1)
                                    .addComponent(lblDur_a)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(19, 19, 19))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRes_a)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnWriteOk_a, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //mainFrame 으로부터 접속중인 사원번호 가져오기
    public alFrame(String empNo){
        this.empNo = empNo;
        initComponents();
        getEmpInfo();        
        System.out.println("alFrame: "+ empNo);
        
        
        //jDatachooser1 형식 포멧팅
        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser2.setDateFormatString("yyyy-MM-dd");
        
        txtName_a.setText(empNo);
        txtName_a.enable(false);
    }
    public alFrame(String empNo, String sign_id, int flag)  throws ParseException {
        this.empNo = empNo;
        this.selectedSignId = sign_id;
        initComponents();
        
        signConDTO userInfo = initSign.selectOne(sign_id);
        
        //내용 불러오기
        String check1 = btnLeave1_a.getText();
        String check2 = btnLeave2_a.getText();
        String checked = userInfo.getSign_check();
        txtName_a.setText(userInfo.getEmp_no());
        getEmpInfo();        
        if(check1.equals(checked)){
            btnLeave1_a.setSelected(true);
        }else if(check2.equals(checked)){
            btnLeave2_a.setSelected(true);
        }        
        String dateStr1 = userInfo.getSign_start();
        String dateStr2 = userInfo.getSign_end();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date_start = formatter.parse(dateStr1);
        Date date_end = formatter.parse(dateStr2);               
        //jDatachooser1 형식 포멧팅
        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser2.setDateFormatString("yyyy-MM-dd");        
        jDateChooser1.setDate(date_start);
        jDateChooser2.setDate(date_end);        
        txtRes_a.setText(userInfo.getSign_content());
        
        //변경 막게
        txtName_a.setEditable(false);
        
        if(flag == 1){ //읽기모드
            //변경 막게
            btnLeave1_a.setEnabled(false);
            btnLeave2_a.setEnabled(false);
            cbxApp_a.setEnabled(false);
            jDateChooser1.setEnabled(false);
            jDateChooser2.setEnabled(false);
            txtRes_a.setEditable(false);

            btnWriteOk_a.setText("확인");
        }else if(flag == 0){  //수정막기
            btnWriteOk_a.setText("수정완료");
        }
    }
    
    //null 체크 후 null이 아니면 insertSign()실행
    private void btnWriteOk_aActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWriteOk_aActionPerformed
        if(btnWriteOk_a.getText().equals("신청완료")){
            if(nullChk() == true){
                insertSign();
                dispose();
            }
        }else if(btnWriteOk_a.getText().equals("수정완료")){
            Date start = jDateChooser1.getDate();
            Date end = jDateChooser2.getDate();
                           
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
            //원하는 데이터 포맷 지정
            String startDate = simpleDateFormat.format(start); 
            String ednDate = simpleDateFormat.format(end); 
            
            String sql = "update sign set sign_start = '" + startDate
                        +"', sign_end = '" + ednDate
                        +"', sign_content = '" + txtRes_a.getText()+ "' where sign_id = '" + selectedSignId + "'";
                System.out.println("upd : "+sql);
            try{
                DBM.getCon();
                DBM.pstmt = DBM.conn.prepareStatement(sql);
                DBM.pstmt.executeUpdate(sql);
                DBM.dbClose();
            }catch(Exception e){
                System.out.println("SQLException : "+e.getMessage());
            }
            dispose();
        }else{
            dispose();
        }
    }//GEN-LAST:event_btnWriteOk_aActionPerformed
    //null check
    public boolean nullChk(){
        boolean chk = false;
        if(txtName_a.getText() == null){
            JOptionPane.showMessageDialog(null,"기안자가 비워져있습니다.");
            txtName_a.requestFocus();
            return chk;
        }
        if(startDate() == null){
            JOptionPane.showMessageDialog(null,"시작일이 비워져있습니다.");
            txtName_a.requestFocus();
            return chk;
        }
        if(endDate() == null){
            JOptionPane.showMessageDialog(null,"종료일이 비워져있습니다.");
            txtName_a.requestFocus();
            return chk;
        }
        if(divRes() == null){
            JOptionPane.showMessageDialog(null,"휴가 구분이 비워져있습니다.");
            txtName_a.requestFocus();
            return chk;
        }
        if(txtRes_a.getText().length() == 0){
            JOptionPane.showMessageDialog(null,"휴가사유가 비워져있습니다.");
            txtName_a.requestFocus();
            return chk;
        }
        if(cbxApp_a.getSelectedItem() == null){
            JOptionPane.showMessageDialog(null,"승인자가 비워져있습니다.");
            txtName_a.requestFocus();
            return chk;
        }
        chk = true;
        return chk;
    }
    
    //insert sign table
    public void insertSign(){
        
        String signId = signId(); //sign_id
        String req_a = txtName_a.getText();//기안자
        String startDate = startDate();//시작일
        String endDate = endDate();//종료일
        String div = divRes();//구분
        String res = txtRes_a.getText();//사유
        String acp_a = cbxApp_a.getSelectedItem().toString();//승인자
        //승인여부
        String per = alCal();
        
        String strQuery = String.format("insert into sign values('%s','%s','%s','%s','%s','%s','%s',0,'%s')", signId , req_a, startDate, endDate, div, res, acp_a, per); 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(strQuery);
            
            JOptionPane.showMessageDialog(null,"신청 완료되었습니다.");
            DBM.rs.close();
            DBM.conn.close();  
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    //sign_id 구하기 count(*) 이용
    public String signId(){
        String strQuery = "select count(*)+1 from sign";
        String memberId;
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(strQuery);
            DBM.rs.next();
            memberId = DBM.rs.getString(1);
            
            DBM.rs.close();
            DBM.conn.close();      
            
            return memberId;
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return null;
    }  
    //승인자 정보(emp_no)가져오기
    public void getEmpInfo(){
        String strQuery = "Select emp_no From emp";  
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(strQuery);
            
            while(DBM.rs.next()){
                cbxApp_a.addItem(DBM.rs.getString(1));
            }
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    //휴가 사유 구분
    public String divRes(){
        String div;
        if(btnLeave1_a.isSelected()){
            div = "연차";
        }else{
            div = "병가";
        }
        return div;
    }
    //시작일 구하기
    public String startDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stDate = null;
        String reqDate = null;
        String reqYear = null;
        if(jDateChooser1.getDate() != null){
            reqDate = sdf.format(jDateChooser1.getCalendar().getTime());
            reqYear = reqDate.split("-")[0];
            stDate = reqDate;
        }
        return stDate;
    }
    //종료일 구하기
    public String endDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stDate = null;
        String reqDate = null;
        String reqYear = null;
        if(jDateChooser2.getDate() != null){
            reqDate = sdf.format(jDateChooser2.getCalendar().getTime());
            reqYear = reqDate.split("-")[0];
            stDate = reqDate;
        }
        return stDate;
    }
    
    //연차 기간 구하기
    public String alCal(){
        String per;//기간 
        String startDate = startDate();
        String endDate = endDate();
        
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
         
        try{
            Date stDate = transFormat.parse(startDate);
            Date edDate = transFormat.parse(endDate);  
            
            long Sec = (edDate.getTime() - stDate.getTime()) / 1000; // 초
            long Days = Sec / (24*60*60) + 1; // 일자수
            per = Long.toString(Days);
            return per;
        }catch(Exception e ){
           System.out.println("SQLException : "+e.getMessage());
        }
        return null;
    }
            
            
            
    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new alFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton btnLeave1_a;
    private javax.swing.JRadioButton btnLeave2_a;
    private javax.swing.JButton btnWriteOk_a;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbxApp_a;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblApp_a;
    private javax.swing.JLabel lblDur_a;
    private javax.swing.JLabel lblDur_a1;
    private javax.swing.JLabel lblLeave_a;
    private javax.swing.JLabel lblName_a;
    private javax.swing.JLabel lblRes_a;
    private javax.swing.JLabel lblTitle;
    public javax.swing.JTextField txtName_a;
    private javax.swing.JTextArea txtRes_a;
    // End of variables declaration//GEN-END:variables
}
