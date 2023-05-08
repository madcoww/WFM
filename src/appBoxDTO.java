/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kim-wonjun
 */
public class appBoxDTO {
    private String sign_id;
    private String sign_check;
    private String sign_start;
    private String sign_end;
    private String sign_per;
    private String emp_no;

    public String getEmp_no() {
        return emp_no;
    }

    public void setEmp_no(String emp_no) {
        this.emp_no = emp_no;
    }
    private int sign_ok;

    public appBoxDTO(String sign_id, String sign_check, String sign_start, String sign_end, String sign_per, String emp_no, int sign_ok){
        this.sign_id = sign_id;
        this.sign_check = sign_check;
        this.sign_start = sign_start;
        this.sign_end = sign_end;
        this.sign_per = sign_per;
        this.emp_no = emp_no;
        this.sign_ok = sign_ok;
    }
    
    public String getSign_id() {
        return sign_id;
    }

    public void setSign_id(String sign_id) {
        this.sign_id = sign_id;
    }

    public String getSign_check() {
        return sign_check;
    }

    public void setSign_check(String sign_check) {
        this.sign_check = sign_check;
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

    public String getSign_per() {
        return sign_per;
    }

    public void setSign_per(String sign_per) {
        this.sign_per = sign_per;
    }

    public int getSign_ok() {
        return sign_ok;
    }

    public void setSign_ok(int sign_ok) {
        this.sign_ok = sign_ok;
    }
    
    
    
}
