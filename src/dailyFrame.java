/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author dawoo
 */
public class dailyFrame extends javax.swing.JFrame {
    String empNo;
    String day;
  
    public dailyFrame(){
        initComponents();
    }
    public dailyFrame(String empNo, String day) {
        this.empNo = empNo;
        this.day = day;
        initComponents();
        
        //업무일지
        String work_id = CheckTime.getWorkId(empNo, day);
        String con = dailyFrame.selectDayContent(work_id);
        String file = dailyFrame.selectFilePath(empNo, day);
        txtCon_w.setText(con); // 오늘 업무일지 정보 생성
        lblFileName_w1.setText(file); // 오늘 업무일지 정보 생성
        if(!dailyFrame.ishasDayCon(empNo, day)){
            btnWriteOk_w.setText("작성완료");
        }else{
            btnWriteOk_w.setText("수정완료");
        }
    }
    
    //DB연동 객체
    static DB_MAN DBM = new DB_MAN();
    
    // 근무일지 id 읽기
    static String selectDayId(String dayStr){
        String dayId = "";
        String sql = "select day_id from work a, day b where a.work_id = b.work_id and work_date = '"+ dayStr +"'"; 
        
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            while(DBM.rs.next()){
                dayId = DBM.rs.getString("day_id");
            }
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return dayId;
    }
    
    // 근무일지 파일경로 읽기
    static String selectFilePath(String emp_no, String work_date){
        String file_path = "";
        String sql = "Select file_path From day a, work b, file c where a.work_id = b.work_id and a.file_id = c.file_id and emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            while(DBM.rs.next()){
                file_path = DBM.rs.getString("file_path");
            }
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return file_path;
    }
    
    static String selectDayContent(String work_id){
        String day_content = "";
        String query = "select day_content from day where work_id = '"+work_id+"'";
        try {
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(query);
            DBM.rs = DBM.pstmt.executeQuery();
            if(DBM.rs.next()) {
                day_content = DBM.rs.getString("day_content");
            }
        }catch(Exception e){
            System.out.println("selectDayContent : "+e.getMessage());
        }
        return day_content;
    }
    
    static boolean ishasDayCon(String emp_no, String work_date){
        String sql = "Select day_content From day a, work b where a.work_id = b.work_id and emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        String day_content = "";
        int cnt = 0;
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            
            while(DBM.rs.next()){
                day_content = DBM.rs.getString("day_content");
                if(!day_content.equals("")) {
                    cnt++; // 정보 있을경우 1
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
    
    static boolean ishasDayFile(String emp_no, String work_date){
        String sql = "Select file_path From day a, work b, file c where a.work_id = b.work_id and a.file_id = c.file_id and emp_no = '"+emp_no+"' and work_date ='"+work_date+"'"; 
        String file_path = "";
        int cnt = 0;
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            while(DBM.rs.next()){
                file_path = DBM.rs.getString("file_path");
                if(!file_path.equals("")) {
                    cnt++; // 정보 있을경우 1
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
    
    public void selectDaily(String work_id){
        String query = "select * from booking where work_id = ?";
        try {
            DBM.getCon();
            DBM.pstmt = DBM.conn.prepareStatement(query);
            DBM.pstmt.setString(1, work_id);
            DBM.rs = DBM.pstmt.executeQuery();
            if(DBM.rs.next()) {
                txtCon_w.setText(DBM.rs.getString("day_con"));
                txtCon_w.setText(DBM.rs.getString("lblFileName_w1"));
            }
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    static String selectFileId(String day_id){
        String file_id = "";
        String sql = "select file_id from day where day_id = '"+ day_id +"'"; 
        System.out.print(sql);
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(sql);
            while(DBM.rs.next()){
                file_id = DBM.rs.getString("file_id");
            }
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        return file_id;
    }
    
    public void updateDaily(){
        String day_id = selectDayId(day);
        String sql = "UPDATE day SET day_content = '"+txtCon_w.getText()+"' where day_id = '"+day_id+"'"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(sql);
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        String file_id= selectFileId(day_id);
        sql = "UPDATE file SET file_path = '"+lblFileName_w1.getText()+"' where file_id = '"+ file_id +"'"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(sql);
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    static void deleteDaily(String emp_no, String work_date){
        String day_id = selectDayId(work_date);
        String sql = "UPDATE day SET day_content = null where day_id = '"+day_id+"'"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(sql);
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
        String file_id= selectFileId(day_id);
        sql = "UPDATE file SET file_path = null where file_id = '"+ file_id +"'"; 
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.stmt.executeUpdate(sql);
            DBM.conn.close();
        }catch(Exception e){
            System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        lblTitle = new javax.swing.JLabel();
        lblCon_w = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtCon_w = new javax.swing.JTextArea();
        btnWriteOk_w = new javax.swing.JButton();
        lblFile_w = new javax.swing.JLabel();
        lblFileName_w1 = new javax.swing.JLabel();
        btnOpen = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnWriteCancle_w1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        lblTitle.setText("업무일지 작성");

        lblCon_w.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblCon_w.setText("업무내용");

        txtCon_w.setColumns(20);
        txtCon_w.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        txtCon_w.setRows(5);
        jScrollPane3.setViewportView(txtCon_w);

        btnWriteOk_w.setBackground(new java.awt.Color(204, 204, 204));
        btnWriteOk_w.setFont(new java.awt.Font("굴림", 1, 12)); // NOI18N
        btnWriteOk_w.setText("완료");
        btnWriteOk_w.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWriteOk_wActionPerformed(evt);
            }
        });

        lblFile_w.setText("파일첨부");

        btnOpen.setBackground(new java.awt.Color(204, 204, 204));
        btnOpen.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnOpen.setText("열기");
        btnOpen.setToolTipText("");
        btnOpen.setPreferredSize(new java.awt.Dimension(100, 30));
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(204, 204, 204));
        btnDelete.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnDelete.setText("삭제");
        btnDelete.setToolTipText("");
        btnDelete.setPreferredSize(new java.awt.Dimension(100, 30));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnWriteCancle_w1.setBackground(new java.awt.Color(204, 204, 204));
        btnWriteCancle_w1.setFont(new java.awt.Font("굴림", 1, 12)); // NOI18N
        btnWriteCancle_w1.setText("취소");
        btnWriteCancle_w1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWriteCancle_w1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(233, 233, 233)
                        .addComponent(lblTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnWriteOk_w, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnWriteCancle_w1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCon_w)
                                        .addGap(54, 54, 54))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblFile_w)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblFileName_w1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(68, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(lblTitle)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblCon_w)
                        .addGap(56, 56, 56)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFile_w)
                    .addComponent(lblFileName_w1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpen, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnWriteOk_w, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWriteCancle_w1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnWriteOk_wActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWriteOk_wActionPerformed
        updateDaily();
        dispose();
    }//GEN-LAST:event_btnWriteOk_wActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        jFileChooser1.showOpenDialog(null);
        lblFileName_w1.setText(jFileChooser1.getSelectedFile().getPath());
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnWriteCancle_w1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWriteCancle_w1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnWriteCancle_w1ActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        lblFileName_w1.setText("");
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(dailyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dailyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dailyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dailyFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dailyFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnWriteCancle_w1;
    private javax.swing.JButton btnWriteOk_w;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCon_w;
    private javax.swing.JLabel lblFileName_w1;
    private javax.swing.JLabel lblFile_w;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextArea txtCon_w;
    // End of variables declaration//GEN-END:variables
}
