DROP TABLE culturefacility;
DROP TABLE CULTUREFACILITY CASCADE CONSTRAINTS;
CREATE TABLE CULTUREFACILITY(
		CULTUREFNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CNAME                         		VARCHAR2(200)		 NOT NULL,
        RADDRESS                            VARCHAR2(100)		 NOT NULL,
		LATITUDE                      		VARCHAR2(30)		 NOT NULL,
		LONGITUDE                     		VARCHAR2(30)		 NOT NULL,
		ADDR_CODE                     		VARCHAR2(30)		 NOT NULL,
		PHONE                         		VARCHAR2(30)		 NOT NULL,
		CLOSEDDAYS                    		VARCHAR2(30)		 NOT NULL,
		OPERATINGTIME                 		VARCHAR2(100)		 NOT NULL,
		PA                            		VARCHAR2(30)		 NOT NULL,
		MASTERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO)
);

COMMENT ON TABLE CULTUREFACILITY is '문화시설';
COMMENT ON COLUMN CULTUREFACILITY.CULTUREFNO is '문화시설번호';
COMMENT ON COLUMN CULTUREFACILITY.CNAME is '문화시설이름';
COMMENT ON COLUMN CULTUREFACILITY.RADDRESS is '도로명주소';
COMMENT ON COLUMN CULTUREFACILITY.LATITUDE is '위도';
COMMENT ON COLUMN CULTUREFACILITY.LONGITUDE is '경도';
COMMENT ON COLUMN CULTUREFACILITY.ADDR_CODE is '우편번호';
COMMENT ON COLUMN CULTUREFACILITY.PHONE is '전화번호';
COMMENT ON COLUMN CULTUREFACILITY.CLOSEDDAYS is '휴무일';
COMMENT ON COLUMN CULTUREFACILITY.OPERATINGTIME is '운영시간';
COMMENT ON COLUMN CULTUREFACILITY.PA is '주차가능여부';
COMMENT ON COLUMN CULTUREFACILITY.MASTERNO is '관리자번호';

DROP SEQUENCE culturefacility_seq;

CREATE SEQUENCE culturefacility_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지



 -- 등록 --
INSERT INTO culturefacility(culturefno, cname, raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa)
VALUES (culturefacility_seq.nextval, '테스트', '도로명 주소',33.450701, 126.570667, '우편번호', '전화번호', '휴무일', '운영시간', '주차가능여부');

INSERT INTO culturefacility(culturefno, cname, raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa)
VALUES (culturefacility_seq.nextval, '문화시설이름2', '도로명 주소','위도', '경도', '우편번호', '전화번호', '휴무일', '운영시간', '주차가능여부');

INSERT INTO culturefacility(culturefno, cname, raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa)
VALUES (culturefacility_seq.nextval, '문화시설이름3', '도로명 주소','위도', '경도', '우편번호', '전화번호', '휴무일', '운영시간', '주차가능여부');

INSERT INTO culturefacility(culturefno, cname, raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa)
VALUES (culturefacility_seq.nextval, '문화시설이름3', '주소','위도', '경도', '우편번호', '전화번호', '휴무일', '운영시간', '주차가능여부');

INSERT INTO culturefacility(culturefno, cname, raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa)
VALUES (culturefacility_seq.nextval, '문화시설이름3', '부산','위도', '경도', '우편번호', '전화번호', '휴무일', '운영시간', '주차가능여부');

-- 전체 목록 --
SELECT culturefno, cname,raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa
FROM culturefacility
ORDER BY culturefno DESC;

-- 모든글
SELECT culturefno, cname,raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa
FROM culturefacility
ORDER BY culturefno ASC;

-- 도로명주소별로 출력
SELECT culturefno, cname, raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa
FROM culturefacility
WHERE raddress LIKE '%도로%'
ORDER BY culturefno DESC;


-- 삭제
DELETE FROM culturefacility
WHERE culturefno = 1;

-- ----------------------------------------------------------------------------------------------------
-- 검색, cateno별 검색 목록
-- ----------------------------------------------------------------------------------------------------
-- 모든글
SELECT culturefno, cname,raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa
FROM culturefacility
ORDER BY culturefno ASC;

-- 도로명별 목록
SELECT culturefno, cname, raddress, latitude, longitude, addr_code, phone, closeddays, operatingtime, pa
FROM culturefacility
WHERE raddress LIKE '%도로%'
ORDER BY culturefno ASC;


commit;





