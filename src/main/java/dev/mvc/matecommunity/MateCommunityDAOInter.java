package dev.mvc.matecommunity;

import java.util.ArrayList;
import java.util.Map;

public interface MateCommunityDAOInter {
    /**
     * 게시글 작성
     * @param mateCommunityVO
     * @return
     */
    public int create(MateCommunityVO mateCommunityVO);

    /**
     * 게시글 전체 조회
     * @param map (검색어, 시작번호, 끝번호)
     * @return
     */
    public ArrayList<MateCommunityVO> list_all(Map<String, Object> map);

    /**
     * 특정 카테고리의 게시글 조회
     * @param map (카테고리번호, 검색어, 시작번호, 끝번호)
     * @return
     */
    public ArrayList<MateCommunityVO> list_all_by_petTypeNo(Map<String, Object> map);

    /**
     * 게시글 조회
     * @param mCommunityNo
     * @return
     */
    public MateCommunityJoinVO read_content(int mCommunityNo);

    /**
     * 게시글 수정
     * @param mateCommunityVO (제목, 내용, 검색태그, 시작지점, 모집 인원, 글번호)
     * @return
     */
    public int update_content(MateCommunityVO mateCommunityVO);

    /**
     * 조회수 증가
     * @param mCommunityNo
     * @return
     */
    public int viewCnt_up(int mCommunityNo);

    /**
     * 모집상태 변경(마감)
     * @param mCommunityNo
     * @return
     */
    public int recruit_finish(int mCommunityNo);

    /**
     * 모집상태 변경(모집중)
     * @param mCommunityNo
     * @return
     */
    public int recruit_start(int mCommunityNo);

    /**
     * 게시글 삭제
     * @param mCommunityNo
     * @return
     */
    public int delete_content(int mCommunityNo);

    /**
     * 모든 게시글 갯수
     * @param map
     * @return
     */
    public int list_all_count(String SearchWord);

    /**
     * 특정 카테고리의 게시글 갯수
     * @param map
     * @return
     */
    public int list_all_by_petTypeNo_count(Map<String,Object> map);

    /**
     * 자신의 모든 게시글
     * @param map
     * @return
     */
    public ArrayList<MateCommunityJoinVO> my_list_all(Map<String, Object> map);

    /**
     * 자신의 게시글 개수
     * @param memberNo
     * @return
     */
    public int my_list_all_count(int memberNo);
}
