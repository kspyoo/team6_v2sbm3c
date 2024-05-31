package dev.mvc.matereview;

import java.util.ArrayList;
import java.util.Map;

public interface MateReviewDAOInter {

    /**
     * 후기 작성
     * @param mateReviewVO
     * @return
     */
    public int create(MateReviewVO mateReviewVO);


    /**
     * 특정 게시물의 후기 목록 조회
     * @param map (게시글 번호, 시작 rownum, 끝 rownum)
     * @return
     */
    public ArrayList<MateReviewListVO> mateReviewList(Map<String, Object> map);

    /**
     * 게시물의 후기 개수
     * @param mCommunityNo 게시물 번호
     * @return
     */
    public int mateReviewListCount(int mCommunityNo);

    /**
     * 내가 작성한 후기 리스트 조회
     * @param map (회원 번호, 시작 rownum, 끝 rownum)
     * @return
     */
    public ArrayList<MyMateReviewVO> myReviewList(Map<String, Object> map);

    /**
     * 내가 작성한 후기 개수
     * @param memberNo 회원 번호
     * @return
     */
    public int myReviewCount(int memberNo);

    /**
     * 후기 조회
     * @param rNo 후기 번호
     * @return
     */
    public MateReviewViewVO reviewRead(int rNo);

    /**
     * 후기 삭제
     * @param rNo 후기 번호
     * @return
     */
    public int delete(int rNo);

    /**
     * 후기 수정
     * @param map (별점, 후기내용, 후기번호)
     * @return
     */
    public int update(Map<String, Object> map);

    /**
     * 후기를 작성했는지 확인
     * @param map
     * @return
     */
    public int reviewIsWritten(Map<String,Object> map);
}