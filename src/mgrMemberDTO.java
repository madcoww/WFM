/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author s_in_
 */
public class mgrMemberDTO {
    
    private String emp_no;
    private String emp_name;
    private String emp_mail;
    private String emp_num;
    private String dept_id;
    private String emp_rankno;
    private String emp_hiredate;
    private String level_id;
    private String member_id;
    
    public mgrMemberDTO(String emp_no, String emp_name, String emp_mail, String emp_num, String dept_id, String emp_rankno, String emp_hiredate, String level_id, String member_id){
        this.emp_no = emp_no;
        this.emp_name = emp_name;
        this.emp_mail = emp_mail;
        this.emp_num = emp_num;
        this.dept_id = dept_id;
        this.emp_rankno = emp_rankno;
        this.emp_hiredate = emp_hiredate;
        this.level_id = level_id;
        this.member_id = member_id;
    }
    public String getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_mail() {
        return emp_mail;
    }

    public void setEmp_mail(String emp_mail) {
        this.emp_mail = emp_mail;
    }

    public String getEmp_num() {
        return emp_num;
    }

    public void setEmp_num(String emp_num) {
        this.emp_num = emp_num;
    }

    public String getEmp_rankno() {
        return emp_rankno;
    }
    
    public String getDept_id() {
        return dept_id;
    }

    public void setDept_id(String dept_id) {
        this.dept_id = dept_id;
    }
    
    public void setEmp_rankno(String emp_rankno) {
        this.emp_rankno = emp_rankno;
    }

    public String getEmp_hiredate() {
        return emp_hiredate;
    }

    public void setEmp_hiredate(String emp_hiredate) {
        this.emp_hiredate = emp_hiredate;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
