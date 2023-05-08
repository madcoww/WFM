/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author dawoo
 */
public class signConDTO {
    private String sign_id;
    private String emp_no;
    private String sign_start;
    private String sign_end;
    private String sign_check;
    private String sign_content;
    private String sign_mgr;
    private int sign_ok;
    private String sign_per;
    
    
    public signConDTO(String sign_id, String emp_no, String sign_start, String sign_end, String sign_check, String sign_content, String sign_mgr, int sign_ok, String sign_per){
        this.sign_id = sign_id;
        this.emp_no = emp_no;
        this.sign_start = sign_start;
        this.sign_end = sign_end;
        this.sign_check = sign_check;
        this.sign_content = sign_content;
        this.sign_mgr = sign_mgr;
        this.sign_ok = sign_ok;
        this.sign_per = sign_per;
    }
    public String getSign_id() {
        return sign_id;
    }
    public void setSign_id(String sign_id) {
        this.sign_id = sign_id;
    }
    public String getEmp_no() {
        return emp_no;
    }
    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }
    public String getSign_start() {
        return sign_start;
    }
    public void setSign_start(String sign_start) {
        this.sign_start = sign_start;
    }
    public String getSign_end() {
        return sign_end;
    }
    public void setSign_end(String sign_end) {
        this.sign_end = sign_end;
    }
    public String getSign_check() {
        return sign_check;
    }
    public void setSign_check(String sign_check) {
        this.sign_check = sign_check;
    }
    public String getSign_content() {
        return sign_content;
    }
    public void setSign_content(String sign_content) {
        this.sign_content = sign_content;
    }
    public String getSign_mgr() {
        return sign_mgr;
    }
    public void setSign_mgr(String sign_mgr) {
        this.sign_mgr = sign_mgr;
    }
    public int getSign_ok() {
        return sign_ok;
    }
    public void setSign_ok(int sign_ok) {
        this.sign_ok = sign_ok;
    }
    public String getSign_per() {
        return sign_per;
    }
    public void setSign_per(String sign_per) {
        this.sign_per = sign_per;
    }
}
