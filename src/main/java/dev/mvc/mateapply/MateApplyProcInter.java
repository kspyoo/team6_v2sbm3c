package dev.mvc.mateapply;

import java.util.ArrayList;
import java.util.Map;

public interface MateApplyProcInter {
    /**
     * 모집글 신청
     * @param mateApplyVO
     * @return
     */
    public int create(MateApplyVO mateApplyVO);

    /**
     * 모집글 신청 여부
     * @param memberNo 자신의 회원 번호
     * @param mCommunityNo 해당 글 번호
     * @return
     */
    public int isRecruited(int memberNo, int mCommunityNo);

    /**
     * 모집글 신청 취소
     * @param memberNo 자신의 회원 번호
     * @param mCommunityNo 해당 글 번호
     * @return
     */
    public int delete(int memberNo, int mCommunityNo);

    /**
     * 내 신청 내역
     * @param memberNo 회원번호
     * @param now_page 현재 페이지 위치
     * @param record_per_page 페이지당 리스트 개수
     * @return
     */
    public ArrayList<MateApplyListVO> applyList(int memberNo , int now_page, int record_per_page);

    /**
     * 신청내역 수
     * @param memberNo
     * @return
     */
    public int applyListCnt(int memberNo);

    /**
     * 내글의 신청 목록
     * @param mCommunityNo 게시글번호
     * @param now_page 현재 페이지 위치
     * @param record_per_page 페이지당 리스트 개수
     * @return
     */
    public ArrayList<MateRecruitListVO> recruitList(int mCommunityNo, int now_page, int record_per_page);

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

    /**
     * 리스트 하단 페이지번호 html
     * @param now_page 현재 페이지 번호
     * @param searchWord 현재 검색어
     * @param list_file 목록 파일명
     * @param search_count 검색레코드 수
     * @param record_per_page 페이지당 보여줄 레코드 수
     * @param page_per_block 블럭당 보여줄 페이지 수
     * @return
     */
    public String list_pagingBox(int now_page, String searchWord, String list_file,
                                     int search_count, int record_per_page, int page_per_block);
}
