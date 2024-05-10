package dev.mvc.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
 * 공공데이터 포멧
 */
@Getter
@Setter
@ToString
public class OpenAPIDTO {
            
    @JsonProperty("건물 번호")
    private String CFNO;

    @JsonProperty("경도")
    private String longitude;

    @JsonProperty("기본 정보_장소설명")
    private String basicInfo;

    @JsonProperty("도로명 이름")
    private String roadName;

    @JsonProperty("도로명주소")
    private String roadAddr;

    @JsonProperty("리 명칭")
    private String liName;

    @JsonProperty("반려동물 동반 가능정보")
    private String petAvailable;

    @JsonProperty("반려동물 전용 정보")
    private String petInfo;

    @JsonProperty("반려동물 제한사항")
    private String petLimit;

    @JsonProperty("번지")
    private String houseNumber;

    @JsonProperty("법정읍면동명칭")
    private String eupmyundong;

    @JsonProperty("시군구 명칭")
    private String sigungu;

    @JsonProperty("시도 명칭")
    private String cityName;

    @JsonProperty("시설명")
    private String facilityName;

    @JsonProperty("애견 동반 추가 요금")
    private String fee;

    @JsonProperty("우편번호")
    private String addrCode;

    @JsonProperty("운영시간")
    private String operatingTime;

    @JsonProperty("위도")
    private String latitude;

    @JsonProperty("입장 가능 동물 크기")
    private String petSize;

    @JsonProperty("입장(이용료)가격 정보")
    private String charge;

    @JsonProperty("장소(실내) 여부")
    private String innerPlace;

    @JsonProperty("장소(실외)여부")
    private String outerPlace;

    @JsonProperty("전화번호")
    private String number;

    @JsonProperty("주차 가능여부")
    private String pa;

    @JsonProperty("지번주소")
    private String landAddr;

    @JsonProperty("최종작성일")
    private String lastWrite;

    @JsonProperty("카테고리1")
    private String cate1;

    @JsonProperty("카테고리2")
    private String cate2;

    @JsonProperty("카테고리3")
    private String cate3;

    @JsonProperty("홈페이지")
    private String homepage;

    @JsonProperty("휴무일")
    private String closeDay;
}
