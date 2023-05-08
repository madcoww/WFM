/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author inhatc
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class MainFrame extends javax.swing.JFrame {
    //DB연동 객체
    static DB_MAN DBM = new DB_MAN();
    
    private static final int empLevel; // 로그인한 사원의 권한
    
    private static final String empNo; // 로그인한 사원의 사원번호
    static {
        empNo = loginForm.getEmpNo(); // 로그인한 사원번호 초기화
        empLevel = loginForm.getLevel();
    }
    
    /*관리자 테이블 콤보박스 생성하는 부분*/
    JComboBox deptComboBox;
    JComboBox levelComboBox;
    JComboBox acceptComboBox;
    
    public MainFrame() {
        initComponents();
        setLocation(300, 300);
        
        //콤보박스로 변환        
        changeCellEditor(Table_mgr, Table_mgr.getColumnModel().getColumn(4));
        changeCellEditor(Table_mgr, Table_mgr.getColumnModel().getColumn(5));
        changeCellEditor(Table_mgr, Table_mgr.getColumnModel().getColumn(7));
        MemoCalendar cal = new MemoCalendar(); //달력 생성
        
        
        String todayStr = lblToday.getText();
        lblSelectedDate1.setText(todayStr);
        areaTimes.setText(CheckTime.getTimeInfoString(empNo, todayStr)); // 오늘 근무시간 정보 생성
        
        // 업무 개시, 업무 테이블에 오늘 근무정보 레코드 초기화 Insert
        if(initWork.isCreated(todayStr)){
            initWork.insertWork(empNo, todayStr); // 근무테이블 삽입
        }
        if(!initWork.isCreatedDaily(empNo, todayStr)){
            initWork.insertDay(empNo, todayStr); // 업무테이블 삽입
        }
         
        // 출퇴근버튼 초기화
        boolean startChk = CheckTime.isStarted(empNo, todayStr);
        boolean endChk = CheckTime.isEned(empNo, todayStr);
        
        if(startChk){  //출근정보 있음
            btnStart.setEnabled(false);// 퇴근버튼 시간 활성화
            btnEnd.setEnabled(true);// 퇴근버튼 시간 활성화
        }
        if(endChk){
            //퇴근정보 있음
            btnEnd.setEnabled(false);// 퇴근버튼 시간 비활성화
            txtTimer.setText(CheckTime.getWorkTime(empNo, todayStr)); // 근무시간 insert
        }else{
            
        }
        if(!startChk && !endChk){ // 출근정보 없음, 퇴근정보 없음
            btnStart.setEnabled(true); // 출근버튼 활성화
            btnEnd.setEnabled(false);// 퇴근버튼 시간 비활성화
        }
        
        //업무일지 초기화
        SetWorkCon();
        //결제함 로드
        lookupAppbox();
        
        //관리자 테이블 초기화
        mgrSearchRes(mgrInitList);
        
        //연차 테이블 초기화
        jTable2.setModel(initSign.selectSignTable()); // 셀렉트된 테이블 모델 get
        
        
        //관리자가 아니면 관리자 탭지우기
        if(empLevel != 0) jTabbedPane1.removeTabAt(3);
//        switch (empLevel){
//            case 0 : jTabbedPane1.removeTabAt(3); break;
//            case 1 : jTabbedPane1.setEnabledAt(2, false); break; //전자결재함
//            case 2 : break;
//        }
    }
    
    public void SetWorkCon(){
        String selectDayStr = lblSelectedDate1.getText();
        String work_id = CheckTime.getWorkId(empNo, selectDayStr);
        String con = dailyFrame.selectDayContent(work_id);
        String file = dailyFrame.selectFilePath(empNo, selectDayStr);
        areaWorks.setText(con); // 오늘 업무일지 정보 생성
        lblFilePath.setText(file); // 오늘 업무일지 정보 생성
        if(!dailyFrame.ishasDayCon(empNo, selectDayStr) && !dailyFrame.ishasDayFile(empNo, selectDayStr)){
            btnAddWork.setText("작성");
        }else{
            btnAddWork.setText("수정");
        }
    }

    class CalendarDataManager{
            static final int CAL_WIDTH = 7;
            final static int CAL_HEIGHT = 6;
            int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
            int calYear;
            int calMonth;
            int calDayOfMon;
            final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31}; // 달의 마지막 date 배열
            int calLastDate;
            Calendar today = Calendar.getInstance(); // 현재 날짜와 시간으로 Calendar 인스턴스를 생성하여 반환
            Calendar cal;

            public CalendarDataManager(){ 
                setToday();
            }
            
            //오늘 날짜 생성
            public void setToday(){
                calYear = today.get(Calendar.YEAR);             // 현재 년도
                calMonth = today.get(Calendar.MONTH);           // 현재 월
                calDayOfMon = today.get(Calendar.DAY_OF_MONTH); // 현재 월의 날짜
                makeCalData(today);
            }
            
            //달력 date 생성
            private void makeCalData(Calendar cal){
                int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7; // 해당 날짜의 1일 위치
                
                if(calMonth == 1) { // 2월달 일때 윤년/평년 계산
                    calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
                }
                else {
                    calLastDate = calLastDateOfMonth[calMonth];
                }
                
                for(int i = 0 ; i<CAL_HEIGHT ; i++){
                    for(int j = 0 ; j<CAL_WIDTH ; j++){
                        calDates[i][j] = 0;
                    }
                }
                
                //캘린더 date 정보 생성
                for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
                    if(i == 0) k = calStartingPos;
                    else k = 0;
                    for(int j = k ; j<CAL_WIDTH ; j++){
                        if(num <= calLastDate) calDates[i][j]=num++;
                    }
                }
            }
            
            //윤년 평년 계산
            private int leapCheck(int year){ 
                    if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1; // 윤년
                    else return 0; // 평년
            }
            
            // Month 이동시켜서 달력 다시 로드
            public void moveMonth(int mon){
                calMonth += mon;
                if(calMonth>11) while(calMonth>11){ // 마지막 달이면
                    calYear++; // 1년 증가
                    calMonth -= 12; // 1월달로 초기화
                } else if (calMonth<0) while(calMonth<0){
                    calYear--; // 1년 감소
                    calMonth += 12; // 1월달로 초기화
                }
                cal = new GregorianCalendar(calYear,calMonth,calDayOfMon); // 년/월/일로 날짜 데이터 생성.
                makeCalData(cal); // 생성된 날짜로 캘린더 생성
            }
    }

    class MemoCalendar extends CalendarDataManager{
            JPanel calOpPanel;
            ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();

            JButton weekDaysName[];   // 월-화 표기할 btn 생성
            JButton btnDates[][] = new JButton[6][7];  // date 표기할 btn 생성
            listenForDateButs lForDateButs = new listenForDateButs(); 

            JPanel infoPanel;
            JPanel memoSubPanel;
            final String WEEK_DAY_NAME[] = { "일", "월", "화", "수", "목", "금", "토" }; //요일 데이터

            // 작업일지 메모 가능한 캘린더 생성
            public MemoCalendar(){ 
                calOpPanel = pnCal;
                    btnToday.setText("Today");
                    btnToday.setToolTipText("Today");
                    btnToday.addActionListener(lForCalOpButtons);
                    lblToday.setText(today.get(Calendar.YEAR)+"-"+(today.get(Calendar.MONTH)+1)+"-"+(today.get(Calendar.DAY_OF_MONTH)<10?"0"+today.get(Calendar.DAY_OF_MONTH):""+today.get(Calendar.DAY_OF_MONTH)));
                    btnYearPre.setToolTipText("Previous Year");
                    btnYearPre.addActionListener(lForCalOpButtons);
                    btnMonthPre.setToolTipText("Previous Month");
                    btnMonthPre.addActionListener(lForCalOpButtons);
                    lblcurYYYYMM.setText("<html><table width=100><tr><th><font size=5>"+((calMonth+1)<10?"&nbsp;":"")+calYear+"-"+(calMonth+1)+"</th></tr></table></html>");
                    btnMonthNext.setToolTipText("Next Month");
                    btnMonthNext.addActionListener(lForCalOpButtons);
                    btnYearNext.setToolTipText("Next Year");
                    btnYearNext.addActionListener(lForCalOpButtons);
                    calOpPanel.setLayout(new GridBagLayout());
                    GridBagConstraints calOpGC = new GridBagConstraints();
                    calOpGC.gridx = 1;
                    calOpGC.gridy = 1;
                    calOpGC.gridwidth = 2;
                    calOpGC.gridheight = 1;
                    calOpGC.weightx = 1;
                    calOpGC.weighty = 1;
                    calOpGC.insets = new Insets(5,5,0,0);
                    calOpGC.anchor = GridBagConstraints.WEST;
                    calOpGC.fill = GridBagConstraints.NONE;
                    calOpPanel.add(btnToday,calOpGC);
                    calOpGC.gridwidth = 3;
                    calOpGC.gridx = 2;
                    calOpGC.gridy = 1;
                    calOpPanel.add(lblToday,calOpGC);
                    calOpGC.anchor = GridBagConstraints.CENTER;
                    calOpGC.gridwidth = 1;
                    calOpGC.gridx = 1;
                    calOpGC.gridy = 2;
                    calOpPanel.add(btnYearPre,calOpGC);
                    calOpGC.gridwidth = 1;
                    calOpGC.gridx = 2;
                    calOpGC.gridy = 2;
                    calOpPanel.add(btnMonthPre,calOpGC);
                    calOpGC.gridwidth = 2;
                    calOpGC.gridx = 3;
                    calOpGC.gridy = 2;
                    calOpPanel.add(lblcurYYYYMM,calOpGC);
                    calOpGC.gridwidth = 1;
                    calOpGC.gridx = 5;
                    calOpGC.gridy = 2;
                    calOpPanel.add(btnMonthNext,calOpGC);
                    calOpGC.gridwidth = 1;
                    calOpGC.gridx = 6;
                    calOpGC.gridy = 2;
                    calOpPanel.add(btnYearNext,calOpGC);

                    //calPanel
                    weekDaysName = new JButton[7];
                    for(int i=0 ; i<CAL_WIDTH ; i++){
                        weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
                        weekDaysName[i].setBorderPainted(false);
                        weekDaysName[i].setContentAreaFilled(false);
                        weekDaysName[i].setForeground(Color.WHITE);
                        if(i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50));
                        else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));
                        else weekDaysName[i].setBackground(new Color(150, 150, 150));
                        weekDaysName[i].setOpaque(true);
                        weekDaysName[i].setFocusPainted(false);
                        calPanel.add(weekDaysName[i]);
                    }
                    for(int i=0 ; i<CAL_HEIGHT ; i++){
                        for(int j=0 ; j<CAL_WIDTH ; j++){
                            btnDates[i][j]=new JButton();
                            btnDates[i][j].setBorderPainted(false);
                            btnDates[i][j].setContentAreaFilled(false);
                            btnDates[i][j].setBackground(Color.WHITE);
                            btnDates[i][j].setOpaque(true);
                            btnDates[i][j].addActionListener(lForDateButs);
                            calPanel.add(btnDates[i][j]);
                        }
                    }
                    calPanel.setLayout(new GridLayout(0,7,2,2));
                    calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                    showCal(); // �޷��� ǥ��

                infoPanel = new JPanel();
                    infoPanel.setLayout(new BorderLayout());

                    //areaTimes
                    areaTimes.setLineWrap(true);
                    areaTimes.setWrapStyleWord(true);

                    memoSubPanel=new JPanel();
                    pnWork.setLayout(new BorderLayout());
                    pnWork.add(lblWorkTitle);
                    pnWork.add(memoSubPanel,BorderLayout.SOUTH);

		//calOpPanel, calPanel��  frameSubPanelWest�� ��ġ
		JPanel frameSubPanelWest = new JPanel();
		Dimension calOpPanelSize = calOpPanel.getPreferredSize();
		calOpPanelSize.height = 90;
		calOpPanel.setPreferredSize(calOpPanelSize);
		frameSubPanelWest.setLayout(new BorderLayout());
		frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
		frameSubPanelWest.add(calPanel,BorderLayout.CENTER);

		//infoPanel, memoPanel��  frameSubPanelEast�� ��ġ
		JPanel frameSubPanelEast = new JPanel();
		Dimension infoPanelSize=infoPanel.getPreferredSize();
		infoPanelSize.height = 65;
		infoPanel.setPreferredSize(infoPanelSize);
		frameSubPanelEast.setLayout(new BorderLayout());
		frameSubPanelEast.add(infoPanel,BorderLayout.NORTH);

		Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
		frameSubPanelWestSize.width = 410;
		frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);
		
		
		//프레임 생성
		jPanel1.setLayout(new BorderLayout());
		jPanel1.add(frameSubPanelWest, BorderLayout.WEST);
		jPanel1.add(frameSubPanelEast, BorderLayout.CENTER);
		jPanel1.setVisible(true);

		focusToday(); //현재 날짜
		
		//Thread 로 시간 
		ThreadConrol threadCnl = new ThreadConrol();
		threadCnl.start();	
            }
            
            //현재 날짜
            private void focusToday(){
                if(today.get(Calendar.DAY_OF_WEEK) == 1)
                    btnDates[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
                else
                    btnDates[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
            }
            private void showCal(){
                for(int i=0;i<CAL_HEIGHT;i++){
                    for(int j=0;j<CAL_WIDTH;j++){
                        // 글자색
                        if(j==0) btnDates[i][j].setForeground(Color.red);
                        else if(j==6) btnDates[i][j].setForeground(Color.blue);

                        // 각 날짜 데이터 Set
                        btnDates[i][j].setText(Integer.toString(calDates[i][j]));

                        JLabel todayMark = new JLabel("*");
                        btnDates[i][j].removeAll();
                        btnDates[i][j].setBackground(Color.white); // 오류잡기 :  추가되었음..
                        if(calMonth == today.get(Calendar.MONTH) &&
                                    calYear == today.get(Calendar.YEAR) &&
                                    calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
                            Color color = new Color(255, 255, 231);
                            btnDates[i][j].add(todayMark);  
                            btnDates[i][j].setBackground(color);
                            btnDates[i][j].setToolTipText("Today");
                        }

                        if(calDates[i][j] == 0) btnDates[i][j].setVisible(false);
                        else btnDates[i][j].setVisible(true);
                    }
                }
            }
            private class ListenForCalOpButtons implements ActionListener{
                public void actionPerformed(ActionEvent e) {
                    // e.getSource() => 이벤트가 처음 발생한 개체 리턴
                    if(e.getSource() == btnToday){
                            setToday();
                            lForDateButs.actionPerformed(e);
                            focusToday();
                    }
                    else if(e.getSource() == btnYearPre) moveMonth(-12);
                    else if(e.getSource() == btnMonthPre) moveMonth(-1);
                    else if(e.getSource() == btnMonthNext) moveMonth(1);
                    else if(e.getSource() == btnYearNext) moveMonth(12);

                    lblcurYYYYMM.setText(calYear+"-"+((calMonth+1)<10?"0":"")+(calMonth+1));
                    showCal();
                }
            }
            private class listenForDateButs implements ActionListener{
                public void actionPerformed(ActionEvent e) {
                    int k=0,l=0;
                    for(int i=0 ; i<CAL_HEIGHT ; i++){
                        for(int j=0 ; j<CAL_WIDTH ; j++){
                            if(e.getSource() == btnDates[i][j]){ 
                                k=i;
                                l=j;
                            }
                        }
                    }

                    if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l];

                    cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);

                    String dDayString = new String();
                    int dDay=((int)((cal.getTimeInMillis() - today.getTimeInMillis())/1000/60/60/24));
                    if(dDay == 0 && (cal.get(Calendar.YEAR) == today.get(Calendar.YEAR)) 
                                    && (cal.get(Calendar.MONTH) == today.get(Calendar.MONTH))
                                    && (cal.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH))) ;
                    String dateStr = calYear+"-"+((calMonth+1)<10?"0"+(calMonth+1):""+(calMonth+1))+"-"+(calDayOfMon<10?"0"+calDayOfMon:""+calDayOfMon);
                    
                    String todayStr = lblToday.getText();
                    int dateDay = cal.get(Calendar.DAY_OF_WEEK);
                    lblSelectedDate1.setText(dateStr); // 날짜Set
                    try {
                        areaTimes.setText(CheckTime.getTimeInfoString(empNo, dateStr, dateDay, todayStr)); // 해당날짜의 근무 시간 정보 로드
                    } catch (ParseException ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SetWorkCon();
                }
            }
            private class ThreadConrol extends Thread{
                public void run(){
                    String curStr = new String();
                    while(true){
                        try{
                            today = Calendar.getInstance();
                            String amPm = (today.get(Calendar.AM_PM)==0?"AM":"PM");
                            String hour;
                            if(today.get(Calendar.HOUR) == 0) hour = "12"; 
                            else if(today.get(Calendar.HOUR) == 12) hour = " 0";
                            else hour = (today.get(Calendar.HOUR)<10?" ":"")+today.get(Calendar.HOUR);
                            String min = (today.get(Calendar.MINUTE)<10?"0":"")+today.get(Calendar.MINUTE);
                            String sec = (today.get(Calendar.SECOND)<10?"0":"")+today.get(Calendar.SECOND);
                            lblNowTime.setText(amPm+" "+hour+":"+min+":"+sec);

                            sleep(1000);	
                        }
                        catch(InterruptedException e){
                            //예외처리
                        }
                    }
                }
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

        btnStart = new javax.swing.JButton();
        btnEnd = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtTimer = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        pnCal = new javax.swing.JPanel();
        btnToday = new javax.swing.JButton();
        lblToday = new javax.swing.JLabel();
        lblcurYYYYMM = new javax.swing.JLabel();
        btnMonthPre = new javax.swing.JButton();
        btnYearPre = new javax.swing.JButton();
        btnMonthNext = new javax.swing.JButton();
        btnYearNext = new javax.swing.JButton();
        calPanel = new javax.swing.JPanel();
        pnWork = new javax.swing.JPanel();
        lblWorkTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaTimes = new javax.swing.JTextArea();
        lblSelectedDate1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaWorks = new javax.swing.JTextArea();
        btnAddWork = new javax.swing.JButton();
        btnRemoverBtn = new javax.swing.JButton();
        lblFile = new javax.swing.JLabel();
        lblFilePath = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnAl3 = new javax.swing.JButton();
        btnAl4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnAl5 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        btnAl6 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnAl1 = new javax.swing.JButton();
        btnAl2 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnSearch5 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel10 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnDel_mgr = new javax.swing.JButton();
        btnUpdate_mgr = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        Table_mgr = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        cbxDept_mgr = new javax.swing.JComboBox<>();
        txtSearch_mgr = new javax.swing.JTextField();
        btnSearch_mgr = new javax.swing.JButton();
        resetPass_mgr = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblNowTime = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        btnStart.setBackground(new java.awt.Color(204, 204, 204));
        btnStart.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnStart.setText("출근");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        btnEnd.setBackground(new java.awt.Color(204, 204, 204));
        btnEnd.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnEnd.setText("퇴근");
        btnEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        jButton3.setText("종료");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtTimer.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        txtTimer.setText("00:00:00");
        txtTimer.setToolTipText("");

        btnToday.setText("Today");

        lblToday.setFont(new java.awt.Font("맑은 고딕", 0, 14)); // NOI18N
        lblToday.setText("2011-11-05");

        lblcurYYYYMM.setFont(new java.awt.Font("맑은 고딕", 1, 18)); // NOI18N
        lblcurYYYYMM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblcurYYYYMM.setText("2022-11");

        btnMonthPre.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btnMonthPre.setText("<");
        btnMonthPre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnYearPre.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btnYearPre.setText("<<");
        btnYearPre.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnMonthNext.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btnMonthNext.setText(">");
        btnMonthNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnYearNext.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        btnYearNext.setText(">>");
        btnYearNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout calPanelLayout = new javax.swing.GroupLayout(calPanel);
        calPanel.setLayout(calPanelLayout);
        calPanelLayout.setHorizontalGroup(
            calPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        calPanelLayout.setVerticalGroup(
            calPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 332, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnCalLayout = new javax.swing.GroupLayout(pnCal);
        pnCal.setLayout(pnCalLayout);
        pnCalLayout.setHorizontalGroup(
            pnCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCalLayout.createSequentialGroup()
                .addComponent(btnToday)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblToday, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnCalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(pnCalLayout.createSequentialGroup()
                .addComponent(btnYearPre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(btnMonthPre, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblcurYYYYMM, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMonthNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(btnYearNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        pnCalLayout.setVerticalGroup(
            pnCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCalLayout.createSequentialGroup()
                .addGroup(pnCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnToday)
                    .addComponent(lblToday, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnCalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnYearNext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMonthNext, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblcurYYYYMM, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMonthPre, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnYearPre, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblWorkTitle.setFont(new java.awt.Font("굴림", 1, 14)); // NOI18N
        lblWorkTitle.setText("업무내용");

        areaTimes.setEditable(false);
        areaTimes.setColumns(20);
        areaTimes.setRows(5);
        jScrollPane1.setViewportView(areaTimes);

        lblSelectedDate1.setFont(new java.awt.Font("굴림", 1, 14)); // NOI18N
        lblSelectedDate1.setText("2022-11-06");

        areaWorks.setEditable(false);
        areaWorks.setColumns(20);
        areaWorks.setRows(5);
        jScrollPane2.setViewportView(areaWorks);

        btnAddWork.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnAddWork.setText("작성");
        btnAddWork.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddWorkActionPerformed(evt);
            }
        });

        btnRemoverBtn.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnRemoverBtn.setText("삭제");
        btnRemoverBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverBtnActionPerformed(evt);
            }
        });

        lblFile.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblFile.setText("첨부파일 : ");

        lblFilePath.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N

        javax.swing.GroupLayout pnWorkLayout = new javax.swing.GroupLayout(pnWork);
        pnWork.setLayout(pnWorkLayout);
        pnWorkLayout.setHorizontalGroup(
            pnWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnWorkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnWorkLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddWork)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRemoverBtn))
                    .addGroup(pnWorkLayout.createSequentialGroup()
                        .addGroup(pnWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSelectedDate1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblWorkTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnWorkLayout.createSequentialGroup()
                                .addComponent(lblFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnWorkLayout.setVerticalGroup(
            pnWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnWorkLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(lblSelectedDate1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(lblWorkTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnWorkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddWork)
                    .addComponent(btnRemoverBtn))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(pnCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnWork, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnWork, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("업무관리", jPanel1);

        btnAl3.setBackground(new java.awt.Color(204, 204, 204));
        btnAl3.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnAl3.setText("삭제");
        btnAl3.setToolTipText("");
        btnAl3.setPreferredSize(new java.awt.Dimension(100, 30));

        btnAl4.setBackground(new java.awt.Color(204, 204, 204));
        btnAl4.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnAl4.setText("수정");
        btnAl4.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAl4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAl4ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "번호", "구분", "시작일", "종료일", "기간", "처리상태", "승인자"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setIntercellSpacing(new java.awt.Dimension(1, 1));
        jScrollPane4.setViewportView(jTable2);

        btnAl5.setBackground(new java.awt.Color(204, 204, 204));
        btnAl5.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnAl5.setText("연차신청");
        btnAl5.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAl5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAl5ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "연차", "병가" }));

        btnSearch.setBackground(new java.awt.Color(204, 204, 204));
        btnSearch.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnSearch.setText("검색");
        btnSearch.setToolTipText("");
        btnSearch.setPreferredSize(new java.awt.Dimension(100, 30));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "대기", "승인", "반려" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnAl6.setBackground(new java.awt.Color(204, 204, 204));
        btnAl6.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnAl6.setText("내용보기");
        btnAl6.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAl6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAl6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(btnAl5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 294, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1002, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnAl6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAl3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnAl5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAl3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAl6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("연차신청", jPanel2);

        btnAl1.setBackground(new java.awt.Color(204, 204, 204));
        btnAl1.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnAl1.setText("반려처리");
        btnAl1.setToolTipText("");
        btnAl1.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAl1ActionPerformed(evt);
            }
        });

        btnAl2.setBackground(new java.awt.Color(204, 204, 204));
        btnAl2.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnAl2.setText("승인처리");
        btnAl2.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAl2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAl2ActionPerformed(evt);
            }
        });

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "번호", "구분", "시작일", "종료일", "기간", "기안자", "처리상태"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.setIntercellSpacing(new java.awt.Dimension(1, 1));
        jScrollPane5.setViewportView(jTable3);

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "연차", "병가" }));

        jLabel9.setText("시작일");

        jLabel10.setText("종료일");

        btnSearch5.setBackground(new java.awt.Color(204, 204, 204));
        btnSearch5.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnSearch5.setText("검색");
        btnSearch5.setToolTipText("");
        btnSearch5.setPreferredSize(new java.awt.Dimension(100, 30));
        btnSearch5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                        .addContainerGap(15, Short.MAX_VALUE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnSearch5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 414, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addComponent(jScrollPane5)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("전자결재함", jPanel3);

        btnDel_mgr.setBackground(new java.awt.Color(204, 204, 204));
        btnDel_mgr.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnDel_mgr.setText("삭제");
        btnDel_mgr.setToolTipText("");
        btnDel_mgr.setPreferredSize(new java.awt.Dimension(100, 30));
        btnDel_mgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDel_mgrActionPerformed(evt);
            }
        });

        btnUpdate_mgr.setBackground(new java.awt.Color(204, 204, 204));
        btnUpdate_mgr.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnUpdate_mgr.setText("수정");
        btnUpdate_mgr.setPreferredSize(new java.awt.Dimension(100, 30));
        btnUpdate_mgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdate_mgrActionPerformed(evt);
            }
        });

        Table_mgr.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "사원번호", "이름", "이메일", "전화번호", "부서", "직급", "입사날짜", "회원상태"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Table_mgr.setIntercellSpacing(new java.awt.Dimension(1, 1));
        jScrollPane7.setViewportView(Table_mgr);

        cbxDept_mgr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "부서", "이름" }));

        btnSearch_mgr.setBackground(new java.awt.Color(204, 204, 204));
        btnSearch_mgr.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        btnSearch_mgr.setText("검색");
        btnSearch_mgr.setToolTipText("");
        btnSearch_mgr.setPreferredSize(new java.awt.Dimension(100, 30));
        btnSearch_mgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch_mgrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(98, Short.MAX_VALUE)
                .addComponent(cbxDept_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(txtSearch_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(btnSearch_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addContainerGap(15, Short.MAX_VALUE)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxDept_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSearch_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(btnSearch_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        resetPass_mgr.setBackground(new java.awt.Color(204, 204, 204));
        resetPass_mgr.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        resetPass_mgr.setText("비밀번호 초기화");
        resetPass_mgr.setPreferredSize(new java.awt.Dimension(100, 30));
        resetPass_mgr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetPass_mgrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(resetPass_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDel_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 929, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 73, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDel_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetPass_mgr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1002, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 457, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel10Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("관리자", jPanel10);

        jLabel2.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        jLabel2.setText("현재시간:");

        lblNowTime.setFont(new java.awt.Font("굴림", 0, 12)); // NOI18N
        lblNowTime.setText("현재시간 표기란");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtTimer)
                        .addGap(26, 26, 26)
                        .addComponent(btnStart)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEnd)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lblNowTime, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnStart)
                    .addComponent(btnEnd)
                    .addComponent(lblNowTime, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                    .addComponent(txtTimer)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAl5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAl5ActionPerformed
        alFrame af = new alFrame(empNo);
        af.setVisible(true);
    }//GEN-LAST:event_btnAl5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(-1);
    }//GEN-LAST:event_jButton3ActionPerformed

    TimeThread timeThread = new TimeThread(); // 타이머 스레드 생성
    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        String todayStr = lblToday.getText();
        
        //Thread 로 시간 	
        if(!CheckTime.isStarted(empNo, todayStr)){
            CheckTime.insertStartTime(empNo,todayStr); // 출근시간 
            timeThread.start(); // 타이머 시작
        }
        btnStart.setEnabled(false); // 출근버튼 비활성화
        btnEnd.setEnabled(true);// 퇴근버튼 시간 활성화
        areaTimes.setText(CheckTime.getTimeInfoString(empNo, todayStr)); // 해당날짜의 근무 시간 정보 로드
        SetWorkCon();
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnEndActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndActionPerformed
        String todayStr = lblToday.getText();
        if(!CheckTime.isEned(empNo, todayStr)){
            CheckTime.insertEndTime(empNo,todayStr); // 퇴근시간 
            CheckTime.insertWorkTime(empNo, todayStr, txtTimer.getText());
        }
        timeThread.setStop(); // 타이머 중단
        btnEnd.setEnabled(false); // 퇴근버튼 비활성화
        areaTimes.setText(CheckTime.getTimeInfoString(empNo, todayStr)); // 해당날짜의 근무 시간 정보 로드
        SetWorkCon();
    }//GEN-LAST:event_btnEndActionPerformed

    private void btnAddWorkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddWorkActionPerformed
        String dayStr = lblSelectedDate1.getText();

        // 업무 일지 작성폼 로드
        dailyFrame daily = new dailyFrame(empNo, dayStr);
        daily.setVisible(true);
    }//GEN-LAST:event_btnAddWorkActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        //업무일지 리로드
        SetWorkCon();
        mgrInitList = mgr.getInitMemberList();
        mgrSearchRes(mgrInitList);
        
        jTable3.setModel(initSign.selectAppBoxTable(empNo));
    }//GEN-LAST:event_formWindowGainedFocus

    private void btnRemoverBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverBtnActionPerformed
        JOptionPane opPn = new JOptionPane();
        int ans = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(ans == 0){
            deleteCon();
        }
    }//GEN-LAST:event_btnRemoverBtnActionPerformed
 //관리자 기능
  mgrMember mgr = new mgrMember();
  ArrayList<mgrMemberDTO>mgrInitList = mgr.getInitMemberList();
    private void btnDel_mgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDel_mgrActionPerformed
        int iRow = Table_mgr.getSelectedRow();
        TableModel data = Table_mgr.getModel();
        String SelectedEmpNo = (String)data.getValueAt(iRow, 0);
        String SelectedMemberId = mgr.getMemberIdByEmpNo(SelectedEmpNo);
        int empResult = mgr.deleteEmp(SelectedEmpNo);
        if(empResult < 0){
             JOptionPane.showMessageDialog(null, "사원 관련 DB 오류입니다.");
        }else{
            int memberResult = mgr.deleteMember(SelectedMemberId);
            if(memberResult < 0){
                JOptionPane.showMessageDialog(null, "회원 관련 DB 오류입니다.");
            }else{
                JOptionPane.showMessageDialog(null, "삭제가 성공적으로 반영되었습니다.");
            }
        }
    }//GEN-LAST:event_btnDel_mgrActionPerformed

    private void btnUpdate_mgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdate_mgrActionPerformed
        int iRow = Table_mgr.getSelectedRow();
        TableModel data = Table_mgr.getModel();
        String SelectedEmpNo = (String)data.getValueAt(iRow, 0);
        String SelectedEmpName = (String)data.getValueAt(iRow, 1);
        String SelectedEmpMail = (String)data.getValueAt(iRow, 2);
        String SelectedEmpNum = (String)data.getValueAt(iRow, 3);
        String SelectedDeptId = mgr.getDeptIdByDeptName((String)data.getValueAt(iRow, 4));
        String SelectedEmpRankno = (String)data.getValueAt(iRow, 5);
        String SelectedEmpHiredate = (String)data.getValueAt(iRow, 6);
        String SelectedLevelId = (String)data.getValueAt(iRow, 7);
        mgrMemberDTO selectedMember = new mgrMemberDTO(SelectedEmpNo, SelectedEmpName, SelectedEmpMail, SelectedEmpNum, SelectedDeptId, SelectedEmpRankno, SelectedEmpHiredate, SelectedLevelId, null);
        
        manageUpdate mgrForm = new manageUpdate(selectedMember);
        mgrForm.setVisible(true);
    }//GEN-LAST:event_btnUpdate_mgrActionPerformed

    private void btnSearch_mgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch_mgrActionPerformed
        
        String selectedCbxItem =  cbxDept_mgr.getSelectedItem().toString();
        ArrayList<String> deptList = mgr.getDeptInfo();
        String mgrTxtSearchRes = txtSearch_mgr.getText().trim();
        ArrayList<mgrMemberDTO> tableRes;
        
        if(selectedCbxItem.equals("부서")){
            if(mgrTxtSearchRes.equals(deptList.get(0))){ //영업
                tableRes = mgr.getDeptByUserInfo("영업");
                if(tableRes.isEmpty()){
                    JOptionPane.showMessageDialog(null, " 검색 결과가 없습니다.");
                    return;
                }
                mgrSearchRes(tableRes);
            }else if(mgrTxtSearchRes.equals(deptList.get(1))){ // 마케팅
                tableRes = mgr.getDeptByUserInfo("마케팅");
                if(tableRes.isEmpty()){
                    JOptionPane.showMessageDialog(null, " 검색 결과가 없습니다.");
                    return;
                }
                mgrSearchRes(tableRes);
            }else if(mgrTxtSearchRes.equals(deptList.get(2))){ //회계
                tableRes = mgr.getDeptByUserInfo("회계");
                if(tableRes.isEmpty()){
                    JOptionPane.showMessageDialog(null, " 검색 결과가 없습니다.");
                    return;
                }
                mgrSearchRes(tableRes);
            }else{
                JOptionPane.showMessageDialog(null, " 검색 결과가 없습니다.");
            }
        }else if(selectedCbxItem.equals("이름")){
            tableRes = mgr.getNameByUserInfo(mgrTxtSearchRes);
            if(tableRes.isEmpty()){
                JOptionPane.showMessageDialog(null, " 검색 결과가 없습니다.");
                return;
            }
            mgrSearchRes(tableRes);
        }
    }//GEN-LAST:event_btnSearch_mgrActionPerformed

    private void resetPass_mgrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetPass_mgrActionPerformed
         int iRow = Table_mgr.getSelectedRow();
        TableModel data = Table_mgr.getModel();
        String SelectedEmpNo = (String)data.getValueAt(iRow, 0);
        String SelectedMemberId = mgr.getMemberIdByEmpNo(SelectedEmpNo);
        int result = mgr.restPass(SelectedMemberId);
        if(result < 0){
            JOptionPane.showMessageDialog(null, "DB 오류입니다.");
        }else{
            JOptionPane.showMessageDialog(null, "성공적으로 비밀번호를 초기화하였습니다.");
        }
    }//GEN-LAST:event_resetPass_mgrActionPerformed

    private void btnAl4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAl4ActionPerformed
        int cnt = jTable2.getSelectedRowCount(); //선택된 행
        if(cnt > 0){
            String startDate = (String)jTable2.getValueAt(jTable2.getSelectedRow(), 2);  // 시작일                            
            String endDate = (String)jTable2.getValueAt(jTable2.getSelectedRow(), 3); // 종료일                    
            String check = (String)jTable2.getValueAt(jTable2.getSelectedRow(), 1);  // 구분 
            String sign_id = initSign.selectSignId(startDate, endDate, check);
            try{
                alFrame af2 = new alFrame(empNo, sign_id, 0);
                af2.setVisible(true);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(this, "변경하려는 내용을 선택해주세요");
        }

    }//GEN-LAST:event_btnAl4ActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String check = (String)jComboBox1.getSelectedItem();
        String signOk = (String)jComboBox2.getSelectedItem();
        jTable2.setModel(initSign.searchSignTableByCheck(check, signOk)); // 셀렉트된 테이블 모델 get 
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearch5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch5ActionPerformed
        String signChk = (String)jComboBox4.getSelectedItem();
        String stDate = startDate();
        String endDate = endDate();
        jTable3.setModel(initApprove.selectCond(signChk, stDate, endDate));
    }//GEN-LAST:event_btnSearch5ActionPerformed

    private void btnAl6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAl6ActionPerformed
         int cnt = jTable2.getSelectedRowCount(); //선택된 행
        if(cnt > 0){
            String startDate = (String)jTable2.getValueAt(jTable2.getSelectedRow(), 2);  // 시작일                            
            String endDate = (String)jTable2.getValueAt(jTable2.getSelectedRow(), 3); // 종료일                    
            String check = (String)jTable2.getValueAt(jTable2.getSelectedRow(), 1);  // 구분 
            String sign_id = initSign.selectSignId(startDate, endDate, check);
            try{
                alFrame af2 = new alFrame(empNo, sign_id, 1);
                af2.setVisible(true);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }else{
            JOptionPane.showMessageDialog(this, "변경하려는 내용을 선택해주세요");
        }

    }//GEN-LAST:event_btnAl6ActionPerformed

    private void btnAl2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAl2ActionPerformed
        int SelectedRow = jTable3.getSelectedRow();
        String selectedSignId = jTable3.getValueAt(SelectedRow, 0).toString();
        jTable3.setModel(initSign.applySign(selectedSignId, empNo));
    }//GEN-LAST:event_btnAl2ActionPerformed

    private void btnAl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAl1ActionPerformed
        int SelectedRow = jTable3.getSelectedRow();
        String selectedSignId = jTable3.getValueAt(SelectedRow, 0).toString();
        jTable3.setModel(initSign.rejectSign(selectedSignId, empNo));
    }//GEN-LAST:event_btnAl1ActionPerformed
    
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
    
    public void deleteCon(){
        String dayStr = lblSelectedDate1.getText();
        dailyFrame.deleteDaily(empNo,dayStr);
    }
               

    // 타이머 스레드
    private class TimeThread extends Thread{
    
        private boolean stop = false;

        public void setStop(){
            this.stop = true;
            System.out.println("스레드 중단");
            System.out.println(stop);
        }
        
        public void run(){
            Calendar cal = Calendar.getInstance();
            String initTime = "00:00:00";
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date now = null;
            try {
                now = formatter.parse(initTime);
            } catch (ParseException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            cal.setTime(now);
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            while(!stop){
                try{
                    cal.add(Calendar.SECOND, 1);
                    Date setDate = cal.getTime();
                    String dateToStr = dateFormat.format(setDate);
                    txtTimer.setText(dateToStr);

                    sleep(1000);	
                }
                catch(InterruptedException e){
                    System.out.println("SQLException : "+e.getMessage());
                }
            }
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
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    void changeCellEditor(JTable table, TableColumn column) {
            TableColumn SelectedColumn = column;
            deptComboBox = new JComboBox();
            levelComboBox = new JComboBox();
            acceptComboBox = new JComboBox();

            if(SelectedColumn == Table_mgr.getColumnModel().getColumn(4)){
                deptComboBox.addItem("영업");
                deptComboBox.addItem("마케팅");
                deptComboBox.addItem("회계");
                SelectedColumn.setCellEditor(new DefaultCellEditor(deptComboBox));
            }else if(SelectedColumn == Table_mgr.getColumnModel().getColumn(5)){
                levelComboBox.addItem("사원");
                levelComboBox.addItem("대리");
                levelComboBox.addItem("과장");
                levelComboBox.addItem("팀장");
                levelComboBox.addItem("사장");
                SelectedColumn.setCellEditor(new DefaultCellEditor(levelComboBox));
            }else if(SelectedColumn == Table_mgr.getColumnModel().getColumn(7)){
                 acceptComboBox.addItem("미승인");
                 acceptComboBox.addItem("승인");
                 SelectedColumn.setCellEditor(new DefaultCellEditor(acceptComboBox));
            }
    }
    /*관리자 관련 메소드*/
    void mgrSearchRes(ArrayList<mgrMemberDTO> Res){
        mgrResetRes(Table_mgr);
        int iCntRow = Table_mgr.getRowCount();
        
        if(Res.size() < iCntRow){
                for(int i=0; i<Res.size(); i++){
                if(Table_mgr.getValueAt(i, 0) == null){
                    mgrMemberDTO searchRes = Res.get(i);
                    Table_mgr.setValueAt(searchRes.getEmp_no(), i, 0);
                    Table_mgr.setValueAt(searchRes.getEmp_name(), i, 1);
                    Table_mgr.setValueAt(searchRes.getEmp_mail(), i, 2);
                    Table_mgr.setValueAt(searchRes.getEmp_num(), i, 3);
                    Table_mgr.setValueAt(mgr.getDeptNameByDeptId(searchRes.getDept_id()), i, 4);
                    Table_mgr.setValueAt(searchRes.getEmp_rankno(), i, 5);
                    Table_mgr.setValueAt(searchRes.getEmp_hiredate(), i, 6);
                    Table_mgr.setValueAt(mgr.getLevelNumByLevelId(searchRes.getLevel_id()), i, 7);
                 }
            }
        }else{
            for(int i=0; i<iCntRow; i++){
               if(Table_mgr.getValueAt(i, 0) == null){
                    mgrMemberDTO searchRes = Res.get(i);
                    Table_mgr.setValueAt(searchRes.getEmp_no(), i, 0);
                    Table_mgr.setValueAt(searchRes.getEmp_name(), i, 1);
                    Table_mgr.setValueAt(searchRes.getEmp_mail(), i, 2);
                    Table_mgr.setValueAt(searchRes.getEmp_num(), i, 3);
                    Table_mgr.setValueAt(mgr.getDeptNameByDeptId(searchRes.getDept_id()), i, 4);
                    Table_mgr.setValueAt(searchRes.getEmp_rankno(), i, 5);
                    Table_mgr.setValueAt(searchRes.getEmp_hiredate(), i, 6);
                    Table_mgr.setValueAt(mgr.getLevelNumByLevelId(searchRes.getLevel_id()), i, 7);
                 }
            }
        }
    }
    
    void mgrResetRes(JTable table){
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setNumRows(0);
        model.setNumRows(mgrInitList.size());
    }


    //    결재함
    public void lookupAppbox(){
        String strQuery = String.format("SELECT sign_id, sign_check, sign_start, sign_end, sign_per, emp_no, sign_ok FROM sign WHERE sign_mgr = '%s'", empNo);
        int iCntRow = 0;
        String signOk = "";
        String signChk = "1";
        try{
            DBM.getCon();
            DBM.stmt = DBM.conn.createStatement();
            DBM.rs = DBM.stmt.executeQuery(strQuery);
            while(DBM.rs.next()){
                jTable3.setValueAt(DBM.rs.getString("sign_id"), iCntRow, 0);
                jTable3.setValueAt(DBM.rs.getString("sign_check"), iCntRow, 1);
                jTable3.setValueAt(DBM.rs.getString("sign_start"), iCntRow, 2);
                jTable3.setValueAt(DBM.rs.getString("sign_end"), iCntRow, 3);
                jTable3.setValueAt(DBM.rs.getString("sign_per"), iCntRow, 4);
                jTable3.setValueAt(DBM.rs.getString("emp_no"), iCntRow, 5);
                
                if(DBM.rs.getString("sign_ok").equals("0")){
                    signOk = "대기";
                }else if(signChk.equals(DBM.rs.getString("sign_ok"))){
                    signOk = "승인";
                }else{
                    signOk = "반려";
                }
                
                jTable3.setValueAt(signOk, iCntRow, 6);
                iCntRow++;
            }
            DBM.rs.close();
            DBM.conn.close();
        }catch(Exception e ){
           System.out.println("SQLException : "+e.getMessage());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_mgr;
    private javax.swing.JTextArea areaTimes;
    private javax.swing.JTextArea areaWorks;
    private javax.swing.JButton btnAddWork;
    private javax.swing.JButton btnAl1;
    private javax.swing.JButton btnAl2;
    private javax.swing.JButton btnAl3;
    private javax.swing.JButton btnAl4;
    private javax.swing.JButton btnAl5;
    private javax.swing.JButton btnAl6;
    private javax.swing.JButton btnDel_mgr;
    private javax.swing.JButton btnEnd;
    private javax.swing.JButton btnMonthNext;
    private javax.swing.JButton btnMonthPre;
    private javax.swing.JButton btnRemoverBtn;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch5;
    private javax.swing.JButton btnSearch_mgr;
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnToday;
    private javax.swing.JButton btnUpdate_mgr;
    private javax.swing.JButton btnYearNext;
    private javax.swing.JButton btnYearPre;
    private javax.swing.JPanel calPanel;
    private javax.swing.JComboBox<String> cbxDept_mgr;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox4;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lblFile;
    private static javax.swing.JLabel lblFilePath;
    private javax.swing.JLabel lblNowTime;
    private javax.swing.JLabel lblSelectedDate1;
    private javax.swing.JLabel lblToday;
    private javax.swing.JLabel lblWorkTitle;
    private javax.swing.JLabel lblcurYYYYMM;
    private javax.swing.JPanel pnCal;
    private javax.swing.JPanel pnWork;
    private javax.swing.JButton resetPass_mgr;
    private javax.swing.JTextField txtSearch_mgr;
    private javax.swing.JLabel txtTimer;
    // End of variables declaration//GEN-END:variables
}
