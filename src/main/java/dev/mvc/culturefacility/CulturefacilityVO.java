package dev.mvc.culturefacility;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CulturefacilityVO {
//  CULTUREFNO                        NUMBER(10)     NOT NULL    PRIMARY KEY, --문화시설 번호
//  CNAME                             VARCHAR2(200)    NOT NULL, -- 문화시설이름
//  RADDRESS                          VARCHAR2(100)    NOT NULL, -- 도로명 주소
//  LATITUDE                          VARCHAR2(10)     NOT NULL, -- 위도
//  LONGITUDE                         VARCHAR2(10)     NOT NULL, -- 경도
//  ADDR_CODE                         VARCHAR2(10)     NOT NULL, -- 우편번호
//  PHONE                             VARCHAR2(15)     NOT NULL, -- 전화번호
//  CLOSEDDAYS                        DATE     NOT NULL,         -- 휴무일
//  OPERATINGTIME                     VARCHAR2(30)     NOT NULL, -- 운영시간
//  PA                                VARCHAR2(10)     NOT NULL, -- 주차가능여부
//  MASTERNO                          NUMBER(10)     NULL , -- 관리자 번호

  /** 문화시설번호 */
  private int cutlureno;
  /** 문화시설이름 */
  private String cname = "";
  /** 도로명주소 */
  private String raddress = "";
  /** 위도 */
  private String lattitude = "";
  /** 경도 */
  private String longitude = "";
  /** 우편번호 */
  private String addr_code = "";
  /** 전화번호 */
  private String phone = "";
  /** 휴무일 */
  private String date = "";
  /** 운영시간 */
  private String operatingtime = "";
  /** 주차가능여부 */
  private String pa = "";
  /** 관리자 번호 */
  private int masterno;

}