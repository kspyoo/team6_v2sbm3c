package dev.mvc.pet;

import java.util.ArrayList;
import java.util.Map;

public interface PetDAOInter {

    /**
     * 반려동물 정보 입력
     * @param petVO
     * @return
     */
    public int create(PetVO petVO);

    /**
     * 반려동물 정보 단건 조회
     * @param petNo
     * @return
     */
    public PetJoinVO petInfo(int petNo);

    /**
     * 반려동물 정보 리스트 조회
     * @param memberNo
     * @return
     */
    public ArrayList<PetJoinVO> petInfoList(int memberNo);

    /**
     * 회원의 데이터 개수 조회
     * @param memberNo
     * @return
     */
    public int petInfoCnt(int memberNo);

    /**
     * 반려동물 정보 수정
     * @param map
     * @return
     */
    public int update(Map<String, Object> map);

    /**
     * 반려동물 정보 삭제
     * @param petNo
     * @return
     */
    public int delete(int petNo);

    /**
     * 특정 유저의 반려동물 정보 전체 삭제
     * @param memberNo
     * @return
     */
    public int delete_all(int memberNo);

    /**
     * 카테고리안의 정보 개수
     * @param petTypeNo
     * @return
     */
    public int petInfoCnt_by_petTypeNo(int petTypeNo);

    /**
     * 특정 카테고리의 반려동물 정보 전체 삭제
     * @param petTypeNo
     * @return
     */
    public int delete_all_by_petTypeNo(int petTypeNo);
}
