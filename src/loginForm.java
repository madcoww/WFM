import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.awt.event.KeyEvent;

public class loginForm extends javax.swing.JFrame {

    //DB연동 객체
    static DB_MAN DBM = new DB_MAN();
    
    public loginForm() {
        initComponents();
    }
    //
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblId_l = new javax.swing.JLabel();
        lblPass_l = new javax.swing.JLabel();
        btnLogin_l = new javax.swing.JButton();
        btnJoin_l = new javax.swing.JButton();
        txtId_l = new javax.swing.JTextField();
        lblTitle_l = new javax.swing.JLabel();
        txtPass_l = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblId_l.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblId_l.setText("아이디");

        lblPass_l.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblPass_l.setText("패스워드");

        btnLogin_l.setBackground(new java.awt.Color(204, 204, 204));
        btnLogin_l.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnLogin_l.setText("로그인");
        btnLogin_l.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogin_lActionPerformed(evt);
            }
        });

        btnJoin_l.setBackground(new java.awt.Color(0, 0, 0));
        btnJoin_l.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnJoin_l.setForeground(new java.awt.Color(255, 255, 255));
        btnJoin_l.setText("회원가입");
        btnJoin_l.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJoin_lActionPerformed(evt);
            }
        });

        txtId_l.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        lblTitle_l.setFont(new java.awt.Font("맑은 고딕", 3, 24)); // NOI18N
        lblTitle_l.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle_l.setText("WFM Company");

        txtPass_l.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPass_lKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(87, 87, 87)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitle_l, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblId_l)
                            .addComponent(lblPass_l))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtId_l, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                            .addComponent(txtPass_l)))
                    .addComponent(btnJoin_l, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogin_l, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(lblTitle_l)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtId_l, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblId_l))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPass_l)
                    .addComponent(txtPass_l, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogin_l, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnJoin_l, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static String empNo; // 넘겨줄 사원번호
    
    private void btnLogin_lActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogin_lActionPerformed
        loginProc();
    }//GEN-LAST:event_btnLogin_lActionPerformed

    private void btnJoin_lActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJoin_lActionPerformed
        moveJoin();
    }//GEN-LAST:event_btnJoin_lActionPerformed

    private void txtPass_lKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPass_lKeyTyped
        if(evt.getKeyChar() == KeyEvent.VK_ENTER){
            loginProc();
        }
    }//GEN-LAST:event_txtPass_lKeyTyped
       
    public void loginProc(){
          String id = txtId_l.getText();
        char[] pw = txtPass_l.getPassword();
        String pass = "";
        String member_id = "";
        for(int i = 0; i < pw.length; i++){
            pass += pw[i];
        }
        
        String strQuery = String.format("select member_pw, member_id from member where member_uid = '%s' and member_pw ='%s'", id , pass); 
        String strQuery2 = String.format("select level_id from emp, member where emp.member_id = member.member_id and member_uid = '%s'", id );
        
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(strQuery);
            
            if(DBM.rs.next()){
                if(pass.equals(DBM.rs.getString(1))){
                    member_id = DBM.rs.getString(2); // member_id
                    DBM.rs = DBM.stmt.executeQuery(strQuery2);
                    if(DBM.rs.next()){
                        if(DBM.rs.getString(1) == null){
                            JOptionPane.showMessageDialog(null,"승인 대기 상태입니다.");
                        }else{
                            JOptionPane.showMessageDialog(null,"로그인 성공!");
                            setEmpNo(member_id); // 전역변수에 사원번호 세팅
                            dispose();
                            moveMain(); // 메인으로 이동
                        }
                    }    
                }else{
                    JOptionPane.showMessageDialog(null,"아이디 또는 패스워드가 올바르지 않습니다.");
                }
            }else{
                JOptionPane.showMessageDialog(null,"아이디 또는 패스워드가 올바르지 않습니다.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"데이터 가져오기에 실패하였습니다.");
            System.out.println("SQLException : "+e.getMessage());
        }
    }

    // joinForm으로 넘어가는 메서드
    public void moveJoin(){
        joinForm join = new joinForm();
        join.setVisible(true);
    }
    // MainFrame으로 넘어가는 메서드
    public void moveMain(){
        MainFrame main = new MainFrame();
        main.setVisible(true);
    }
    
    // MainFrame에 사원번호 넘기는 메서드
    static String getEmpNo(){
        return empNo;
    }
    
    // MainFrame에 레벨 넘기는 메서드
    static int getLevel(){
        int level_num = 0;
        String sql = "SELECT level_num FROM emp a, level b WHERE a.level_id = b.level_id and a.emp_no= ?";
        try{
            DBM.getCon();
            PreparedStatement pstmt = DBM.conn.prepareStatement(sql);
            pstmt.setString(1, empNo);
            DBM.rs = pstmt.executeQuery();
            DBM.rs.next();
            level_num = DBM.rs.getInt("level_num");
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e ){
           System.out.println("SQLException : "+e.getMessage());
        }
        return level_num;
    }
    
     // 전역변수 empNo에 사원번호 세팅하는 메서드
    static void setEmpNo(String id){
        String sql = "SELECT emp_no FROM emp WHERE member_id = ?";
        try{
            DBM.getCon();
            PreparedStatement pstmt = DBM.conn.prepareStatement(sql);
            pstmt.setString(1, id);
            DBM.rs = pstmt.executeQuery();
            DBM.rs.next();
            empNo = DBM.rs.getString("emp_no");
            
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e ){
           System.out.println("SQLException : "+e.getMessage());
        }
    }
    
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
            java.util.logging.Logger.getLogger(loginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(loginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(loginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(loginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new loginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnJoin_l;
    private javax.swing.JButton btnLogin_l;
    private javax.swing.JLabel lblId_l;
    private javax.swing.JLabel lblPass_l;
    private javax.swing.JLabel lblTitle_l;
    private javax.swing.JTextField txtId_l;
    private javax.swing.JPasswordField txtPass_l;
    // End of variables declaration//GEN-END:variables
}
