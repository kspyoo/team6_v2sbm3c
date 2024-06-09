package dev.mvc.petprofile;

public interface PetProfileProcInter {
    /**
     * 사진 업로드
     * @param petProfileVO (사진 파일 데이터)
     * @return
     */
    public int create(PetProfileVO petProfileVO);
}
