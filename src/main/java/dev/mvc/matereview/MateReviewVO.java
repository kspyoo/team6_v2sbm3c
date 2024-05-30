package dev.mvc.matereview;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateReviewVO {
    //RNO, REVIEWCOMMENT, REVIEWGRADE, RDATE, MEMBERNO, MCOMMUNITYNO
    // 후기번호
    private int rNo;
    // 후기 내용
    private String reviewComment;
    // 후기 점수
    private int reviewGrade;
    // 후기 작성일자
    private String rDate;
    // 작성자
    private String memberNo;
    // 후기 작성한 산책 메이트 게시글
    private int mCommunityNo;
}