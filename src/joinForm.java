
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author 박상용
 */

public class joinForm extends javax.swing.JFrame {
    private static int CheckID = -1;
    private static char[] pass = null;
    private static char[] passCheck = null;
    private static boolean isPassNull = true;
    private static boolean isPassChecked = false;
    
    /**
     * Creates new form test
     */
    public joinForm() {
        initComponents();
        getDeptInfo (); // 부서정보 불러오기
    }
   /* DB 핸들링 시작 */ 
    
     //DB연동 객체
    DB_MAN DBM = new DB_MAN();
    
    //부서정보 Read
    private void getDeptInfo (){    
        String strQuery = "Select dept_name From dept";  
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(strQuery);
            
            while(DBM.rs.next()){
                combo_dept.addItem(DBM.rs.getString("dept_name"));
            }
            
            DBM.dbClose();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    private String getDeptCode (String dept_name){    //DB에서 부서코드 가져오는 메소드
    String strQuery = "SELECT dept_id FROM dept WHERE dept_name = ?";  
    try{
        DBM.getCon();
        DBM.pstmt = DBM.conn.prepareStatement(strQuery);
        DBM.pstmt.setString(1, dept_name);
        DBM.rs = DBM.pstmt.executeQuery();
        
        if(DBM.rs.next()){
            String result = DBM.rs.getString("dept_id");
            DBM.dbClose();
            return result;
        }
    }catch(Exception e){
        System.out.println("SQLException : "+e.getMessage());
    }
    return ""; // DB오류
}
    
    private int getUserID(String id){ //DB에서 ID를 가져오는 메소드 유효성 검사할 때 사용함
        String sql = "SELECT member_uid FROM member WHERE member_uid = ?";
        int result = 1;
        try{
            DBM.getCon();
            PreparedStatement pstmt = DBM.conn.prepareStatement(sql);
            pstmt.setString(1, id);
            DBM.rs = pstmt.executeQuery();
            
            if(DBM.rs.next()){
               result =  -1; //중복된 아이디 
            }
            DBM.dbClose();
        }catch(Exception e ){
           System.out.println("SQLException : "+e.getMessage());
        }
        return result;
    }
    
private String getNextRandNo() { //랜덤번호 카운트 1씩 증가하도록 설정하는 메소드
            try {
                    DBM.getCon();
                    String randNo;
                    String sql = "SELECT member_id FROM member WHERE member_id like ? ORDER BY member_id DESC";
                    PreparedStatement pstmt = DBM.conn.prepareStatement(sql);
                    pstmt.setString(1, "wfm%");
                    DBM.rs = pstmt.executeQuery();
                    if(DBM.rs.next()) {
                            randNo = String.format("%04d", Integer.parseInt(DBM.rs.getString("member_id").substring(3))+1);
                            DBM.dbClose();
                            return randNo; // 제일 첫번째 멤버가 아니라면 가장 최근에 추가된 멤버아이디에 1 더하기
                    } else{
                            randNo = String.format("%04d", 1);
                            DBM.dbClose();
                            return randNo; // 첫번째라면 1리턴
                    }
            }catch(Exception e) {
                    e.printStackTrace();
            }
            return ""; //DB 오류
    }
    
    private int InsertMember(String memberId, String userId, String userPass){ //MEMBER 테이블에 컬럼 추가하는 메소드
        String sql = "INSERT INTO MEMBER VALUES(?,?,?)";
        try{
            DBM.getCon();
            PreparedStatement pstmt = DBM.conn.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.setString(2, userId);
            pstmt.setString(3, userPass);
            int result = pstmt.executeUpdate();
            DBM.dbClose();
            return result; // 1이상 
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return -1; //DB오류
    }
    
    private int InsertEmp(String empNo, String Name, String empEmail, String empNum, String empDept, String empHireDate, String memberId){
        //EMP테이블에 컬럼 추가하는 메소드
        String sql = "INSERT INTO emp values(?, ?, ?, ?, ?, null, ?, null, ?)";
        try{
            DBM.getCon();
            PreparedStatement pstmt = DBM.conn.prepareStatement(sql);
            pstmt.setString(1, empNo);
            pstmt.setString(2, Name);
            pstmt.setString(3, empEmail);
            pstmt.setString(4, empNum);
            pstmt.setString(5, empDept);
            pstmt.setString(6, empHireDate);
            pstmt.setString(7, memberId);
            int result = pstmt.executeUpdate();
            DBM.dbClose();
            return result;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1; // DB 오류
    }
/* DB 핸들링 끝 */
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMain_j = new javax.swing.JLabel();
        lblPassCheck_j = new javax.swing.JLabel();
        txtPassCheck_j = new javax.swing.JPasswordField();
        lblName_j = new javax.swing.JLabel();
        txtName_j = new javax.swing.JTextField();
        txtMail_j = new javax.swing.JTextField();
        lblMail_j = new javax.swing.JLabel();
        lblNum_j = new javax.swing.JLabel();
        txtNum_j = new javax.swing.JTextField();
        lblDept_j = new javax.swing.JLabel();
        btnJoin_j = new javax.swing.JButton();
        btnCheck_j = new javax.swing.JButton();
        txtPass_j = new javax.swing.JPasswordField();
        txtID_j = new javax.swing.JTextField();
        lblId_j = new javax.swing.JLabel();
        lblPass_j = new javax.swing.JLabel();
        combo_dept = new javax.swing.JComboBox<>();
        lblHireDate_j = new javax.swing.JLabel();
        hireDateChoose_j = new com.toedter.calendar.JDateChooser();
        lblCheck_j = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblMain_j.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        lblMain_j.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMain_j.setText("회원가입");

        lblPassCheck_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblPassCheck_j.setText("비밀번호확인");

        txtPassCheck_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        txtPassCheck_j.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassCheck_jFocusLost(evt);
            }
        });
        txtPassCheck_j.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPassCheck_jKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPassCheck_jKeyReleased(evt);
            }
        });

        lblName_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblName_j.setText("성 명");

        txtName_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        txtMail_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        lblMail_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblMail_j.setText("이메일");

        lblNum_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblNum_j.setText("전화 번호");

        txtNum_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        lblDept_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblDept_j.setText("부서");

        btnJoin_j.setBackground(new java.awt.Color(204, 204, 204));
        btnJoin_j.setFont(new java.awt.Font("굴림", 1, 13)); // NOI18N
        btnJoin_j.setText("회원 가입 완료");
        btnJoin_j.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoin_jActionPerformed(evt);
            }
        });

        btnCheck_j.setBackground(new java.awt.Color(204, 204, 204));
        btnCheck_j.setFont(new java.awt.Font("굴림", 1, 12)); // NOI18N
        btnCheck_j.setText("중복 확인");
        btnCheck_j.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheck_jActionPerformed(evt);
            }
        });

        txtPass_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        txtPass_j.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPass_jFocusLost(evt);
            }
        });
        txtPass_j.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPass_jKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPass_jKeyReleased(evt);
            }
        });

        txtID_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        lblId_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblId_j.setText("아이디");

        lblPass_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblPass_j.setText("비밀번호");

        lblHireDate_j.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblHireDate_j.setText("입사일");

        lblCheck_j.setForeground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(lblMain_j, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnJoin_j, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNum_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDept_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMail_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblName_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPass_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblId_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblPassCheck_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHireDate_j, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMail_j, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(txtName_j, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(txtPassCheck_j)
                                    .addComponent(txtPass_j, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtID_j, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(txtNum_j, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(combo_dept, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hireDateChoose_j, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnCheck_j, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 62, Short.MAX_VALUE))
                                    .addComponent(lblCheck_j, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(lblMain_j)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCheck_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addComponent(txtPass_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPassCheck_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCheck_j))
                        .addGap(12, 12, 12)
                        .addComponent(txtName_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtMail_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(txtNum_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(combo_dept, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblId_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblPass_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblPassCheck_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblName_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblMail_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(lblNum_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblDept_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblHireDate_j, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hireDateChoose_j, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnJoin_j, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnJoin_jActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoin_jActionPerformed
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String hireDate = null;
        String hireYear = null;
        if(hireDateChoose_j.getDate() != null){
            hireDate = sdf.format(hireDateChoose_j.getCalendar().getTime());
            hireYear = hireDate.split("-")[0];
        }
        String DeptCode = getDeptCode(combo_dept.getSelectedItem().toString());
        String randNo = getNextRandNo();
        String memberId = "wfm" + randNo;
        String empNo = hireYear + DeptCode + randNo;
        
                        /* 예외처리1. 각 텍스트 필드마다 빈칸일 경우 */
                        if(txtID_j.getText().equals("")){ // 아이디
                            JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
                            txtID_j.requestFocus();
                            btnJoin_j.setEnabled(false);
                            return;
                        }
                        if(isPassNull == true){ // 비밀번호
                            JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요");
                            txtPass_j.requestFocus();
                            return;
                        }
                        if(txtName_j.getText().equals("")){
                            JOptionPane.showMessageDialog(null, " 성명을 입력하세요");
                            txtName_j.requestFocus();
                            return;
                        }
                        if(txtMail_j.getText().equals("")){
                            JOptionPane.showMessageDialog(null, " 이메일을 입력하세요");
                            txtMail_j.requestFocus();
                            return;
                        }
                        if(txtNum_j.getText().equals("")){
                            JOptionPane.showMessageDialog(null, " 전화번호를 입력하세요");
                            txtNum_j.requestFocus();
                            return;
                        }
                        if(hireDate == null){
                            JOptionPane.showMessageDialog(null, " 입사일을 입력하세요");
                            return;
                        }
                        
                        /* 예외처리2. 중복된아이디일 경우 */
                        //2022-11-17 427~ 428 LINE 추가
                        String ID = txtID_j.getText();
                        CheckID = getUserID(ID);
                        
                        if(CheckID == -1){
                            JOptionPane.showMessageDialog(null, "중복된 아이디입니다.");
                            btnJoin_j.setEnabled(false); // 아이디 중복이면 버튼 비활성화
                        }
                        
                        /* 예외처리3. 비밀번호 유효성검사 */
                        if(isPassChecked == false){
                            JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.");
                        }
                        
                        /* 예외 사항 모두 통과 시 INSERT 실행 */
                        if(isPassChecked == true && isPassNull == false && CheckID == 1){
                            String CheckedPass = "";
                            for(int i=0; i<pass.length; i++){
                                CheckedPass += pass[i];
                            }
                            int memberInsertResult = InsertMember(memberId,txtID_j.getText(),CheckedPass);
                            if(memberInsertResult < 0){
                                JOptionPane.showMessageDialog(null, "DB오류[멤버]");
                            }
                            int empInsertResult = InsertEmp(empNo, txtName_j.getText(), txtMail_j.getText(), txtNum_j.getText(), DeptCode, hireDate, memberId);
                            if(empInsertResult < 0){
                                JOptionPane.showMessageDialog(null, "DB오류[사원]");
                            }else{
                                JOptionPane.showMessageDialog(null, "회원가입 완료");
                                dispose();
                            }
                        }
    }//GEN-LAST:event_btnJoin_jActionPerformed

    private void btnCheck_jActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheck_jActionPerformed
        String ID = txtID_j.getText();
        CheckID = getUserID(ID);
        
        //예외처리2 아이디 부분
        if(ID.equals("")){ // 빈칸일 때
            JOptionPane.showMessageDialog(null, "아이디를 입력하세요.");
            btnJoin_j.setEnabled(false);
        }else{
            if(CheckID == -1){
                JOptionPane.showMessageDialog(null, "중복된 아이디입니다.");
                btnJoin_j.setEnabled(false); // 아이디 중복이면 버튼 비활성화
            }else{
                JOptionPane.showMessageDialog(null, "사용가능한 아이디입니다.");
                btnJoin_j.setEnabled(true); // 아이디 사용가능하면 다시 활성화
            }
        }
        

    }//GEN-LAST:event_btnCheck_jActionPerformed

    private void txtPass_jFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPass_jFocusLost
        isPassChecked = checkPass();
        passAlert();
    }//GEN-LAST:event_txtPass_jFocusLost

    private void txtPassCheck_jFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassCheck_jFocusLost
        isPassChecked = checkPass();
        passAlert();
    }//GEN-LAST:event_txtPassCheck_jFocusLost

    private void txtPass_jKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPass_jKeyPressed
        pass = txtPass_j.getPassword();
        if(pass != null && passCheck != null)
            isPassNull = false;
        isPassChecked = checkPass();
        passAlert();
    }//GEN-LAST:event_txtPass_jKeyPressed

    private void txtPassCheck_jKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassCheck_jKeyPressed
        passCheck = txtPassCheck_j.getPassword();
        if(pass != null && passCheck != null)
            isPassNull = false;     
        isPassChecked = checkPass();
        passAlert();
    }//GEN-LAST:event_txtPassCheck_jKeyPressed

    private void txtPass_jKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPass_jKeyReleased
        pass = txtPass_j.getPassword();
        if(pass != null && passCheck != null)
            isPassNull = false;
        isPassChecked = checkPass();
        passAlert();
    }//GEN-LAST:event_txtPass_jKeyReleased

    private void txtPassCheck_jKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassCheck_jKeyReleased
       passCheck = txtPassCheck_j.getPassword();
        if(pass != null && passCheck != null)
            isPassNull = false;     
        isPassChecked = checkPass();
        passAlert();
    }//GEN-LAST:event_txtPassCheck_jKeyReleased

 
    //비밀번호 체크
    public boolean checkPass(){
        // 비밀번호 중복 처리
         boolean check = false;  // 비밀번호 일치여부 확인

        if(pass != null && passCheck != null){ // 빈 값이 아닐때
            //비밀번호 길이 체크
            if(pass.length == passCheck.length){      
                //비밀번호 일치여부 체크
                for(int i=0; i < pass.length; i++){
                    if(pass[i] == passCheck[i])
                        check = true;
                    if(pass[i] != passCheck[i])
                        check = false;
                }
            }
        }
        return check;
    }
    //비밀번호 체크 레이블 텍스트 설정 메소드
    public void passAlert(){
        if(isPassChecked == false){
            lblCheck_j.setText("비밀번호가 일치하지 않습니다.");
        }else{
            lblCheck_j.setText("비밀번호가 일치합니다.");
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(joinForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(joinForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(joinForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(joinForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new joinForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheck_j;
    private javax.swing.JButton btnJoin_j;
    private javax.swing.JComboBox<String> combo_dept;
    private com.toedter.calendar.JDateChooser hireDateChoose_j;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCheck_j;
    private javax.swing.JLabel lblDept_j;
    private javax.swing.JLabel lblHireDate_j;
    private javax.swing.JLabel lblId_j;
    private javax.swing.JLabel lblMail_j;
    private javax.swing.JLabel lblMain_j;
    private javax.swing.JLabel lblName_j;
    private javax.swing.JLabel lblNum_j;
    private javax.swing.JLabel lblPassCheck_j;
    private javax.swing.JLabel lblPass_j;
    private javax.swing.JTextField txtID_j;
    private javax.swing.JTextField txtMail_j;
    private javax.swing.JTextField txtName_j;
    private javax.swing.JTextField txtNum_j;
    private javax.swing.JPasswordField txtPassCheck_j;
    private javax.swing.JPasswordField txtPass_j;
    // End of variables declaration//GEN-END:variables
}
