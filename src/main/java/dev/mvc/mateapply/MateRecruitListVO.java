package dev.mvc.mateapply;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateRecruitListVO { // 내 게시글 신청 목록
    //ANO, ASTATUS, ADATE, MEMBERNO, MCOMMUNITYNO
    // 신청 번호
    private int aNo;
    // 신청 상태
    private String aStatus;
    // 신청 일자
    private String aDate;
    // 회원 번호
    private int memberNo;
    // 게시글 번호
    private int mCommunityNo;
    // 회원 이름
    private String name;
}
