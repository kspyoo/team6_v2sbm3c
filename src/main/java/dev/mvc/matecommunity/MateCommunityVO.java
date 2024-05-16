package dev.mvc.matecommunity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MateCommunityVO {
    // 게시글 번호
    private int mCommunityNo;
    // 게시글 제목
    private String title;
    // 게시글 내용
    private String content;
    // 조회수
    private int viewCnt;
    // 시작지점
    private String startingP;
    // 메이트 인원
    private int walkingM;
    // 게시글 작성일자
    private String wDate;
    // 모집 상태
    private int status;
    // 게시글 검색태그
    private String searchTag;
    // 반려동물 분류번호
    private int petTypeNo;
    // 작성 회원번호
    private int memberNo;
}
