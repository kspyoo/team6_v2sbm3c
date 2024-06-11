package dev.mvc.pet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetJoinVO {
    // 반려동물 번호
    private int petNo;
    // 반려동물 이름
    private String petName;
    // 반려동물 나이
    private String petBirth;
    // 성별
    private String petGender;
    // 반려동물 상세 정보
    private String petDetail;
    // 반려동물 종류 번호
    private int petTypeNo;
    // 사용자 번호
    private int memberNo;
    // 반려동물 이미지
    private String svFileName;
}
