package dev.mvc.pettype;

import java.util.ArrayList;
import java.util.Map;

public interface PetTypeDAOInter {

    /**
     * 반려동물 종류(카테고리) 등록
     * @param petTypeVO
     * @return
     */
    public int create(PetTypeVO petTypeVO);

    /**
     * 반려동물 종류 리스트 조회
     * @return
     */
    public ArrayList<PetTypeVO> list();

    /**
     * 반려동물 종류 이름 수정
     * @param map (petType 변경할 이름, petTypeNo 종류 번호)
     * @return
     */
    public int update(Map<String,Object> map);

    /**
     * 반려동물 종류 삭제
     * @param petTypeNo
     * @return
     */
    public int delete(int petTypeNo);
}
