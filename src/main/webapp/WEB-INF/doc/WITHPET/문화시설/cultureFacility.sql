/**********************************/
/* Table Name: 문화시설 */
/**********************************/
CREATE TABLE CURTULEFACILITY(
    CULTUREFNO                        NUMBER(10)     NOT NULL    PRIMARY KEY,
    CNAME                             VARCHAR2(200)    NOT NULL,
    LATITUDE                          VARCHAR2(10)     NOT NULL,
    LONGITUDE                         VARCHAR2(10)     NOT NULL,
    ADDR_CODE                         VARCHAR2(10)     NOT NULL,
    PHONE                             VARCHAR2(15)     NOT NULL,
    CLOSEDDAYS                        DATE     NOT NULL,
    OPERATINGTIME                     VARCHAR2(30)     NOT NULL,
    PA                                VARCHAR2(10)     NOT NULL,
    MASTERNO                          NUMBER(10)     NULL ,
  FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO)
);

COMMENT ON TABLE CURTULEFACILITY is '문화시설';
COMMENT ON COLUMN CURTULEFACILITY.CULTUREFNO is '문화시설번호';
COMMENT ON COLUMN CURTULEFACILITY.CNAME is '문화시설이름';
COMMENT ON COLUMN CURTULEFACILITY.LATITUDE is '위도';
COMMENT ON COLUMN CURTULEFACILITY.LONGITUDE is '경도';
COMMENT ON COLUMN CURTULEFACILITY.ADDR_CODE is '우편번호';
COMMENT ON COLUMN CURTULEFACILITY.PHONE is '전화번호';
COMMENT ON COLUMN CURTULEFACILITY.CLOSEDDAYS is '휴무일';
COMMENT ON COLUMN CURTULEFACILITY.OPERATINGTIME is '운영시간';
COMMENT ON COLUMN CURTULEFACILITY.PA is '주차가능여부';
COMMENT ON COLUMN CURTULEFACILITY.MASTERNO is '관리자번호';


/**********************************/
/* Table Name: 문화시설 정보 첨부파일 */
/**********************************/
CREATE TABLE FACILITYATTACHMENT(
    FANO                              NUMBER(10)     NOT NULL    PRIMARY KEY,
    FILENAME                          VARCHAR2(100)    NOT NULL,
    FILESIZE                          VARCHAR2(100)    NOT NULL,
    THUMBFILE                         VARCHAR2(100)    NOT NULL,
    CULTUREFNO                        NUMBER(10)     NULL ,
  FOREIGN KEY (CULTUREFNO) REFERENCES CURTULEFACILITY (CULTUREFNO)
);

COMMENT ON TABLE FACILITYATTACHMENT is '문화시설 정보 첨부파일';
COMMENT ON COLUMN FACILITYATTACHMENT.FANO is '첨부파일 번호';
COMMENT ON COLUMN FACILITYATTACHMENT.FILENAME is '파일명';
COMMENT ON COLUMN FACILITYATTACHMENT.FILESIZE is '파일사이즈';
COMMENT ON COLUMN FACILITYATTACHMENT.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN FACILITYATTACHMENT.CULTUREFNO is '문화시설번호';


/**********************************/
/* Table Name: 문화시설 후기 */
/**********************************/
CREATE TABLE FACILITYREVIEW(
    RNO                               NUMBER(10)     NOT NULL    PRIMARY KEY,
    REVIEWCOMMENT                     VARCHAR2(1000)     NOT NULL,
    REVIEWGRADE                       VARCHAR2(10)     NOT NULL,
    RDATE                             DATE     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
    CULTUREFNO                        NUMBER(10)     NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (CULTUREFNO) REFERENCES CURTULEFACILITY (CULTUREFNO)
);

COMMENT ON TABLE FACILITYREVIEW is '문화시설 후기';
COMMENT ON COLUMN FACILITYREVIEW.RNO is '후기 번호';
COMMENT ON COLUMN FACILITYREVIEW.REVIEWCOMMENT is '후기 내용';
COMMENT ON COLUMN FACILITYREVIEW.REVIEWGRADE is '후기 평점';
COMMENT ON COLUMN FACILITYREVIEW.RDATE is '작성일';
COMMENT ON COLUMN FACILITYREVIEW.MEMBERNO is '회원번호';
COMMENT ON COLUMN FACILITYREVIEW.CULTUREFNO is '문화시설번호';

