
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author 박상용
 */

public class manageUpdate extends javax.swing.JFrame {
    mgrMemberDTO transMember; //memberDTO 선언
    public manageUpdate() {
        initComponents();
    }
    public manageUpdate(mgrMemberDTO Member) {
        initComponents();
        txtName_mu.requestFocus();
        this.transMember = Member; // 전달 받은 member정보
        
        txtNo_mu.setText(transMember.getEmp_no());
        txtName_mu.setText(transMember.getEmp_name());
        txtMail_mu.setText(transMember.getEmp_mail());
        txtNum_mu.setText(transMember.getEmp_num());
        
        /*부서코드를 통해 부서이름을 셋팅하는 과정*/
        if(transMember.getDept_id() == null || transMember.getDept_id().equals("10")){
            comboDept_mu.setSelectedIndex(0);
        }else if(transMember.getDept_id().equals("20")){
            comboDept_mu.setSelectedIndex(1);
        }else if(transMember.getDept_id().equals("30")){
            comboDept_mu.setSelectedIndex(2);
        }
        
        if(transMember.getEmp_rankno() == null){
          comboRankNo_mu.setSelectedIndex(0);
        }else{
            comboRankNo_mu.setSelectedItem(transMember.getEmp_rankno());
        }
       
        /*넘어온 입사일자를 셋팅하는 과정*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        if(transMember.getEmp_hiredate() == null){
            Date noDate = new Date();
            String stringNoDate = sdf.format(noDate);
            try{
            noDate = sdf.parse(stringNoDate);
            }catch(Exception e){
                e.printStackTrace();
            }
            hireDateChoose_mu.setDate(noDate);
        }else{
            Date hireDate = null;
            String stringDate = transMember.getEmp_hiredate();
            try{
            hireDate = sdf.parse(stringDate);
            }catch(Exception e){
                e.printStackTrace();
            }
            hireDateChoose_mu.setDate(hireDate);
        }
        /*넘어온 권한을 셋팅하는 과정*/
        if(transMember.getLevel_id() == null || transMember.getLevel_id().equals("lv2")){
            comboLevel_mu.setSelectedIndex(2);
        }else if(transMember.getLevel_id().equals("lv1")){
            comboLevel_mu.setSelectedIndex(1);
        }else if(transMember.getLevel_id().equals("lv0")){
            comboLevel_mu.setSelectedIndex(0);
        }
    }
    
     //DB연동 객체
    mgrMember mgr = new mgrMember();
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMain_mu = new javax.swing.JLabel();
        lblNum_mu = new javax.swing.JLabel();
        lblDept_mu = new javax.swing.JLabel();
        lblRankNo_mu = new javax.swing.JLabel();
        lblLevel_mu = new javax.swing.JLabel();
        btnUpdate_mu = new javax.swing.JButton();
        txtName_mu = new javax.swing.JTextField();
        lblName_mu = new javax.swing.JLabel();
        lblMail_mu = new javax.swing.JLabel();
        comboDept_mu = new javax.swing.JComboBox<>();
        lblHireDate_mu = new javax.swing.JLabel();
        hireDateChoose_mu = new com.toedter.calendar.JDateChooser();
        lblCheck_j = new javax.swing.JLabel();
        lblNo_mu = new javax.swing.JLabel();
        txtNo_mu = new javax.swing.JTextField();
        comboLevel_mu = new javax.swing.JComboBox<>();
        comboRankNo_mu = new javax.swing.JComboBox<>();
        txtMail_mu = new javax.swing.JTextField();
        txtNum_mu = new javax.swing.JTextField();
        btnExit_mu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblMain_mu.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        lblMain_mu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMain_mu.setText("회원정보 수정");

        lblNum_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblNum_mu.setText("전화번호");

        lblDept_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblDept_mu.setText("부서");

        lblRankNo_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblRankNo_mu.setText("직급");

        lblLevel_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblLevel_mu.setText("회원상태");

        btnUpdate_mu.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate_mu.setFont(new java.awt.Font("굴림", 1, 13)); // NOI18N
        btnUpdate_mu.setText("회원 정보 수정");
        btnUpdate_mu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_muActionPerformed(evt);
            }
        });

        txtName_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        lblName_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblName_mu.setText("이름");

        lblMail_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblMail_mu.setText("이메일");

        comboDept_mu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "영업", "마케팅", "회계" }));

        lblHireDate_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblHireDate_mu.setText("입사일");

        lblCheck_j.setForeground(new java.awt.Color(255, 51, 51));

        lblNo_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblNo_mu.setText("사원번호");

        txtNo_mu.setEditable(false);
        txtNo_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        comboLevel_mu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "1", "0" }));

        comboRankNo_mu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "사원", "대리", "과장", "팀장", "사장" }));

        txtMail_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        txtNum_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        btnExit_mu.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnExit_mu.setText("종료");
        btnExit_mu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExit_muActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(lblMain_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit_mu))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblLevel_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblRankNo_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblDept_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblMail_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblName_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNum_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblHireDate_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(comboDept_mu, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hireDateChoose_mu, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(comboLevel_mu, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboRankNo_mu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblCheck_j, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnUpdate_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblNo_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(txtNo_mu)
                                                .addGap(132, 132, 132))
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(txtMail_mu, javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtName_mu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                                .addComponent(txtNum_mu)))))
                                .addGap(0, 62, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(lblMain_mu))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnExit_mu)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNo_mu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblNo_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblMail_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNum_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblDept_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblRankNo_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHireDate_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLevel_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtMail_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNum_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblCheck_j)
                        .addGap(18, 18, 18)
                        .addComponent(comboDept_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(comboRankNo_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(hireDateChoose_mu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(comboLevel_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(btnUpdate_mu, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdate_muActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_muActionPerformed
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
      String modifyEmpNo = txtNo_mu.getText();
      String modifyEmpName = txtName_mu.getText();
      String modifyEmpMail = txtMail_mu.getText();
      String modifyEmpNum = txtNum_mu.getText();
      String modifyDeptId = mgr.getDeptIdByDeptName(comboDept_mu.getSelectedItem().toString());
      String modifyEmpRankNo = comboRankNo_mu.getSelectedItem().toString();
      String modifyHireDate = sdf.format(hireDateChoose_mu.getDate());
      String modifyLevelId = mgr.getLevelIdByLevelNum(comboLevel_mu.getSelectedItem().toString());
      int result = mgr.updateMember(modifyEmpNo, modifyEmpName, modifyEmpMail, modifyEmpNum, modifyDeptId, modifyEmpRankNo, modifyHireDate, modifyLevelId);
      if(result <0){
          JOptionPane.showMessageDialog(null, "DB 오류입니다.");
      }else{
          JOptionPane.showMessageDialog(null, "성공적으로 수정했습니다. 재검색해주세요.");
          dispose();
      }
      
    }//GEN-LAST:event_btnUpdate_muActionPerformed

    private void btnExit_muActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExit_muActionPerformed
        dispose();
    }//GEN-LAST:event_btnExit_muActionPerformed

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
                new manageUpdate().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExit_mu;
    private javax.swing.JButton btnUpdate_mu;
    private javax.swing.JComboBox<String> comboDept_mu;
    private javax.swing.JComboBox<String> comboLevel_mu;
    private javax.swing.JComboBox<String> comboRankNo_mu;
    private com.toedter.calendar.JDateChooser hireDateChoose_mu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCheck_j;
    private javax.swing.JLabel lblDept_mu;
    private javax.swing.JLabel lblHireDate_mu;
    private javax.swing.JLabel lblLevel_mu;
    private javax.swing.JLabel lblMail_mu;
    private javax.swing.JLabel lblMain_mu;
    private javax.swing.JLabel lblName_mu;
    private javax.swing.JLabel lblNo_mu;
    private javax.swing.JLabel lblNum_mu;
    private javax.swing.JLabel lblRankNo_mu;
    private javax.swing.JTextField txtMail_mu;
    private javax.swing.JTextField txtName_mu;
    private javax.swing.JTextField txtNo_mu;
    private javax.swing.JTextField txtNum_mu;
    // End of variables declaration//GEN-END:variables
}
