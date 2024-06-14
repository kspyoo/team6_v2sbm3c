package dev.mvc.petprofile;

import java.util.ArrayList;

public interface PetProfileDAOInter {

    /**
     * 사진 업로드
     * @param petProfileVO (사진 파일 데이터)
     * @return
     */
    public int create(PetProfileVO petProfileVO);

    /**
     * 메인이미지 조회
     * @return
     */
    public PetProfileVO read_one();

    /**
     * 특정 반려동물 이미지 모두 조회
     * @param petNo 반려동물 번호
     * @return
     */
    public ArrayList<PetProfileVO> list(int petNo);

    /**
     * 이미지 삭제(단건)
     * @param petProfileNo
     * @return
     */
    public int delete(int petProfileNo);

    /**
     * 이미지 삭제(전체)
     * @param petNo
     * @return
     */
    public int delete_all(int petNo);

    /**
     * 이미지가 몇장 있는지 확인 조회
     * @param petNo
     * @return
     */
    public int profile_cnt(int petNo);

    /**
     * 출력 순서 변경 (앞으로)
     * @param petProfileNo
     * @return
     */
    public int seq_forward(int petProfileNo);

    /**
     * 출력 순서 변경 (뒤로)
     * @param petProfileNo
     * @return
     */
    public int seq_backward(int petProfileNo);
}
