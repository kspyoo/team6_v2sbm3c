package dev.mvc.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("dev.mvc.pet.PetProc")
public class PetProc implements PetProcInter{

    @Autowired
    private PetDAOInter petDAO;

    /**
     * 반려동물 데이터 생성
     * @param petVO
     * @return
     */
    @Override
    public int create(PetVO petVO){
        return this.petDAO.create(petVO);
    }

    /**
     * 내 반려동물 정보 조회
     * @param petNo
     * @return
     */
    @Override
    public PetJoinVO petInfo(int petNo) {
        return this.petDAO.petInfo(petNo);
    }

    /**
     * 반려동물 데이터 리스트
     * @param memberNo
     * @return
     */
    @Override
    public ArrayList<PetJoinVO> petInfoList(int memberNo) {
        return this.petDAO.petInfoList(memberNo);
    }

    /**
     * 회원의 반려동물 데이터 개수
     * @param memberNo
     * @return
     */
    @Override
    public int petInfoCnt(int memberNo) {
        return this.petDAO.petInfoCnt(memberNo);
    }

    /**
     * 반려동물 데이터 수정
     * @param petNo 반려동물 번호
     * @param petName 반려동물 이름
     * @param petDetail 반려동물 상세정보
     * @return
     */
    @Override
    public int update(int petNo, String petName, String petDetail) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("petName", petName);
        map.put("petDetail", petDetail);
        map.put("petNo", petNo);

        return this.petDAO.update(map);
    }

    /**
     * 반려동물 데이터 삭제
     * @param petNo 반려동물 번호
     * @return
     */
    @Override
    public int delete(int petNo){
        return this.petDAO.delete(petNo);
    }

    public int delete_all(int memberNo){
        return this.petDAO.delete_all(memberNo);
    };
}
