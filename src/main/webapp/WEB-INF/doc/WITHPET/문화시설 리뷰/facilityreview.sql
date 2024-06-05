DROP TABLE FACILITYREVIEW

CREATE TABLE FACILITYREVIEW(
		RNO                           		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		REVIEWCOMMENT                 		VARCHAR2(1000)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CULTUREFNO                    		NUMBER(10)		NOT NULL ,
  FOREIGN KEY (CULTUREFNO) REFERENCES CULTUREFACILITY (CULTUREFNO),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE FACILITYREVIEW is '문화시설 후기';
COMMENT ON COLUMN FACILITYREVIEW.RNO is '후기 번호';
COMMENT ON COLUMN FACILITYREVIEW.REVIEWCOMMENT is '후기 내용';
COMMENT ON COLUMN FACILITYREVIEW.REVIEWGRADE is '후기 평점';
COMMENT ON COLUMN FACILITYREVIEW.RDATE is '작성일';
COMMENT ON COLUMN FACILITYREVIEW.MEMBERNO is '회원번호';
COMMENT ON COLUMN FACILITYREVIEW.CULTUREFNO is '문화시설번호';

DROP SEQUENCE facilityreview_seq;
CREATE SEQUENCE facilityreview_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999999
  CACHE 2                     -- 2번은 메모리에서만 계산
  NOCYCLE;                   -- 다시 1부터 생성되는 것을 방지


--등록 --
INSERT INTO facilityreview(rno, culturefno, memberno, reviewcomment,  rdate)
VALUES(facilityreview_seq.nextval, 1, 1, '댓글1', sysdate);

-- 전체 목록--
SELECT rno, culturefno, memberno, reviewcomment, rdate
FROM facilityreview
ORDER BY rno DESC;

--facilityreview + member join 목록--
SELECT m.id,
          r.rno, r.culturefno, r.memberno, r.reviewcomment,r.rdate
FROM member m,  facilityreview r
WHERE m.memberno = r.memberno
ORDER BY r.rno DESC;

-- reply + member join + 특정 contentsno 별 목록
SELECT m.id,
           r.rno, r.culturefno, r.memberno, r.reviewcomment, r.rdate
FROM member m,  facilityreview r
WHERE (m.memberno = r.memberno) AND r.culturefno=1
ORDER BY r.rno DESC;



SELECT * FROM culturefacility 
SELECT * FROM member 





