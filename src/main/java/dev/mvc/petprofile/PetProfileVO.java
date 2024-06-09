package dev.mvc.petprofile;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PetProfileVO {
    // PETPROFILENO, OGFILENAME, SVFILENAME, SEQNO, UPLOADDATE, PETNO
    // 사진 파일 데이터
    private MultipartFile file1MF;
    // 사진 번호
    private int petProfileNo;
    // 원본 사진 이름
    private String ogFileName;
    // 저장된 사진 이름 (중복된 이름인 경우 00(1).jpg 이런식으로 이름을 변경한후 저장함)
    private String svFileName;
    // 사진 출력 순서
    private int seqNo;
    // 업로드 시간
    private String uploadDate;
    // 반려동물 번호
    private int petNo;

}
