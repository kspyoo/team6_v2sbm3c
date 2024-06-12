
DROP TABLE  CULTUREFILE
/**********************************/
/* Table Name: 문화시설 파일 수정 */
/**********************************/
CREATE TABLE CULTUREFILE(
		FANO                          		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		SIZE1                         		NUMBER(10)		 NULL ,
		FILE1                         		VARCHAR2(100)    NULL ,
		FILE1SAVED                    		VARCHAR2(100)    NULL,
		THUMBFILE                     		VARCHAR2(100)    NULL,
		CULTUREFNO                    		NUMBER(10)		 NULL ,
  FOREIGN KEY (CULTUREFNO) REFERENCES CULTUREFACILITY (CULTUREFNO)
);

COMMENT ON TABLE CULTUREFILE is '문화시설 파일 수정';
COMMENT ON COLUMN CULTUREFILE.FANO is '파일번호';
COMMENT ON COLUMN CULTUREFILE.SIZE1 is '메인이미지크기';       -- 파일 사이즈
COMMENT ON COLUMN CULTUREFILE.FILE1 is '메인이미지';           -- 원본 파일명 image
COMMENT ON COLUMN CULTUREFILE.FILE1SAVED is '저장된 메인이미지';-- 저장된 파일명, image
COMMENT ON COLUMN CULTUREFILE.THUMBFILE is '썸네일 파일';      -- preview image
COMMENT ON COLUMN CULTUREFILE.CULTUREFNO is '문화시설번호';

DROP SEQUENCE culturefile_seq;

CREATE SEQUENCE culturefile_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
--1) 등록  
INSERT INTO culturefile(fano, culturefno, file1, file1saved, thumbfile, size1)
VALUES(culturefile_seq.nextval, 5, 'samyang.jpg', 'samyang_1.jpg', 'samyang_t.jpg', 1);  
  


-- 2) 목록( culturefno 기준 내림 차순, fano 기준 오르차순)
SELECT fano, culturefno, file1, file1saved, thumbfile, size1 
FROM culturefile
ORDER BY culturefno DESC,  fano ASC;

-- 3) 글별 파일 목록(culturefno 기준 내림 차순, fano 기준 오르차순)
SELECT fano, culturefno, file1, file1saved, thumbfile, size1
FROM culturefile
WHERE culturefno = 5
ORDER BY file1 ASC;

-- 4) 하나의 파일 삭제
DELETE FROM culturefile
WHERE fano = 2;

-- 5) FK contentsno 부모키 별 조회
SELECT fano, culturefno, file1, file1saved, thumbfile, size1
FROM culturefile
WHERE culturefno=5;

-- 부모키별 갯수 산출
SELECT COUNT(*) as cnt
FROM culturefile
WHERE culturefno=5;
