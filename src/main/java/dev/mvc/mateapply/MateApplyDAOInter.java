package dev.mvc.mateapply;

import java.util.ArrayList;
import java.util.Map;

public interface MateApplyDAOInter {
    /**
     * 모집글 신청
     * @param mateApplyVO
     * @return
     */
    public int create(MateApplyVO mateApplyVO);

    /**
     * 모집글 신청 여부
     * @param map (memberNo 자신의 회원 번호, mCommunityNo 해당 글 번호)
     * @return
     */
    public int isRecruited(Map<String, Object> map);

    /**
     * 모집글 신청 취소
     * @param map (memberNo 자신의 회원 번호, mCommunityNo 해당 글 번호)
     * @return
     */
    public int delete(Map<String, Object> map);

    /**
     * 내 신청 내역
     * @param map 회원번호, 시작번호, 끝번호
     * @return
     */
    public ArrayList<MateApplyListVO> applyList(Map<String, Object> map);

    /**
     * 신청내역 수
     * @param memberNo
     * @return
     */
    public int applyListCnt(int memberNo);

    /**
     * 내글의 신청 목록
     * @param map 게시글번호, 시작번호, 끝번호
     * @return
     */
    public ArrayList<MateRecruitListVO> recruitList(Map<String, Object> map);

    /**
     * 내 모집글의 신청 수
     * @param mCommunityNo
     * @return
     */
    public int recruitListCnt(int mCommunityNo);

    /**
     * 응답 (승낙)
     * @param aNo 신청 번호
     * @return
     */
    public int recruitAccept(int aNo);

    /**
     * 응답 (거부)
     * @param aNo 신청 번호
     * @return
     */
    public int recruitDenied(int aNo);

    /**
     * 해당 게시글의 모인 인원수
     * @param mCommunityNo 확인할 모집글
     * @return
     */
    public int checkMember(int mCommunityNo);

    /**
     * 전체 거부
     * @param mCommunityNo
     * @return
     */
    public int deniedAll(int mCommunityNo);

    /**
     * 해당 게시글의 신청 모두 삭제
     * @param mCommunityNo
     * @return
     */
    public int delete_byCommunityNo(int mCommunityNo);
}
