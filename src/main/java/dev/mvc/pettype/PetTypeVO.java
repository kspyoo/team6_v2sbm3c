package dev.mvc.pettype;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetTypeVO {
    // 반려동물 분류 번호
    private int petTypeNo;
    // 반려동물 분류 이름
    private String petType;
    // 생성일
    private String regDate;
    // 작성한 관리자 번호
    private int masterNo;
}
