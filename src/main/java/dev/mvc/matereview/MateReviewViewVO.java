package dev.mvc.matereview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateReviewViewVO {
//    m.NAME, mc.TITLE, mc.ASSEMBLETIME, mc.STARTINGP, mc.STARTINGDETAIL
    // 후기번호
    private int rNo;
    // 후기 내용
    private String reviewComment;
    // 후기 점수
    private int reviewGrade;
    // 후기 작성일자
    private String rDate;
    // 작성자
    private int memberNo;
    // 후기 작성한 산책 메이트 게시글
    private int mCommunityNo;
    // 작성자 이름
    private String name;
    // 게시글 제목
    private String title;
    // 집합 시간
    private String assembleTime;
    // 출발지점
    private String startingP;
    // 출발지점 상세
    private String startingDetail;
}