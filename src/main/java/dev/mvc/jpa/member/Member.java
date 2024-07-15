package dev.mvc.jpa.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Member {
    /** 회원 번호 */
    @Id
    private int memberno;
    /** 아이디 */
    private String id = "";
    /** 비밀번호 */
    private String passwd = "";
    /** 이름 */
    private String name= "";
    /** 성별 */
    private String gender = "";
    /** 생년월일 */
    private String birthday = "";
    /** 전화번호 */
    private String phone = "";
    /** 우편번호 */
    private String addr_code = "";
    /** 주소 */
    private String addr_main = "";
    /** 상세주소 */
    private String addr_detail = "";
    /** 가입일 */
    private String joindate = "";
    /** 계정 상태
     * 1 : 정상 회원, 2 : 휴면 계정, 3 : 탈퇴 회원
     * */
    private String status = "";
    /** 가입일 */
    private String email = "";

}
