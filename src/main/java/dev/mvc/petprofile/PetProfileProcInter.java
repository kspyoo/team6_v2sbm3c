package dev.mvc.petprofile;

public interface PetProfileProcInter {
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
    public PetProfileVO list(int petNo);
}
