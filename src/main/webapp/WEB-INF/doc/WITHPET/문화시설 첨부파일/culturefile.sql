
DROP TABLE  CULTUREFILE
/**********************************/
/* Table Name: 문화시설 파일 수정 */
/**********************************/
CREATE TABLE CULTUREFILE(
		FANO                          		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		SIZE1                         		VARCHAR2(100)		 NULL ,
		FILE1                         		INTEGER   NULL ,
		FILE1SAVED                    		VARCHAR2(100)		 NOT NULL,
		THUMBFILE                     		VARCHAR2(100)		 NOT NULL,
		CULTUREFNO                    		NUMBER(10)		 NULL ,
  FOREIGN KEY (CULTUREFNO) REFERENCES CULTUREFACILITY (CULTUREFNO)
);

COMMENT ON TABLE CULTUREFILE is '문화시설 파일 수정';
COMMENT ON COLUMN CULTUREFILE.FANO is '파일번호';
COMMENT ON COLUMN CULTUREFILE.SIZE1 is '메인이미지크기';
COMMENT ON COLUMN CULTUREFILE.FILE1 is '메인이미지';
COMMENT ON COLUMN CULTUREFILE.FILE1SAVED is '저장된 메인이미지';
COMMENT ON COLUMN CULTUREFILE.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN CULTUREFILE.CULTUREFNO is '문화시설번호';

DROP SEQUENCE culturefile_seq;

CREATE SEQUENCE culturefile_seq
  START WITH 1                -- 시작 번호
  INCREMENT BY 1            -- 증가값
  MAXVALUE 9999999999  -- 최대값: 9999999999 --> NUMBER(10) 대응
  CACHE 2                        -- 2번은 메모리에서만 계산
  NOCYCLE;                      -- 다시 1부터 생성되는 것을 방지
  
-- 파일 수정
UPDATE culturefile
SET file1='train.jpg', file1saved='train.jpg', thumbfile='train_t.jpg', size1=5000
WHERE fano = 1;
