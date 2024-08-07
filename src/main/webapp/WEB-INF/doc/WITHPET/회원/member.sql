/**********************************/
/* Table Name: 회원 */
/**********************************/
DROP TABLE MEMBER;

CREATE TABLE member(
    MEMBERNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
    ID                                VARCHAR2(30)     NOT NULL,
    PASSWD                            VARCHAR2(50)     NOT NULL,
    NAME                              VARCHAR2(15)     NOT NULL,
    GENDER                            VARCHAR2(10)    NOT NULL,
    BIRTHDAY                          DATE     NOT NULL,
    PHONE                             VARCHAR2(14)     NOT NULL,
    ADDR_CODE                         VARCHAR2(8)    NULL,
    ADDR_MAIN                         VARCHAR2(30)   NULL,
    ADDR_DETAIL                       VARCHAR2(30)     NULL,
    JOINDATE                          DATE     NOT NULL,
    STATUS                            VARCHAR2(10)     DEFAULT 0     NOT NULL
);

SELECT COUNT(*) FROM login;



CREATE SEQUENCE member_MEMBERNO_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;


COMMENT ON TABLE member is '회원';
COMMENT ON COLUMN member.MEMBERNO is '회원번호';
COMMENT ON COLUMN member.ID is '아이디';
COMMENT ON COLUMN member.PASSWD is '패스워드';
COMMENT ON COLUMN member.NAME is '이름';
COMMENT ON COLUMN member.GENDER is '성별';
COMMENT ON COLUMN member.BIRTHDAY is '생년월일';
COMMENT ON COLUMN member.PHONE is '전화번호';
COMMENT ON COLUMN member.ADDR_CODE is '우편번호';
COMMENT ON COLUMN member.ADDR_DETAIL is '상세주소';
COMMENT ON COLUMN member.JOINDATE is '가입일';
COMMENT ON COLUMN member.STATUS is '계정 상태';


/**********************************/
/* Table Name: 회원 로그인 내역 */
/**********************************/
CREATE TABLE login(
    LOGINNO                           NUMBER(10)     NOT NULL    PRIMARY KEY,
    IP                                VARCHAR2(40)     NOT NULL,
    CONNDATE                          VARCHAR2(30)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO) ON DELETE SET NULL
);

commit;

COMMENT ON TABLE login is '회원 로그인 내역';
COMMENT ON COLUMN login.LOGINNO is '로그인번호';
COMMENT ON COLUMN login.IP is '접속 아이피';
COMMENT ON COLUMN login.CONNDATE is '접속 일자';
COMMENT ON COLUMN login.MEMBERNO is '회원번호';

DROP SEQUENCE login_seq;

CREATE SEQUENCE login_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지


/**********************************/
/* Table Name: 반려동물 분류 */
/**********************************/
CREATE TABLE PETTYPE(
    PETTYPENO                         NUMBER(10)     NOT NULL    PRIMARY KEY,
    PETTYPE                           VARCHAR2(30)     NOT NULL,
    regdate                             DATE        NOT NULL,
    MASTERNO                          NUMBER(10)     NOT NULL,
    FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO) ON DELETE SET NULL
);


ALTER TABLE PETTYPE ADD regdate DATE DEFAULT sysdate NOT NULL ;
ALTER TABLE MEMBER ADD email VARCHAR2(30);
update PETTYPE set MASTERNO=6;

COMMENT ON TABLE PETTYPE is '반려동물 분류';
COMMENT ON COLUMN PETTYPE.PETTYPENO is '분류 번호';
COMMENT ON COLUMN PETTYPE.PETTYPE is '반려동물 종류';

update MEMBER set email='vkjiu486@gmail.com';

DROP SEQUENCE PETTYPE_SEQ;

CREATE SEQUENCE PETTYPE_SEQ
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

insert into PETTYPE(PETTYPENO, PETTYPE) VALUES (PETTYPE_SEQ.nextval, '강아지');
insert into PETTYPE(PETTYPENO, PETTYPE) VALUES (PETTYPE_SEQ.nextval, '고양이');
insert into PETTYPE(PETTYPENO, PETTYPE) VALUES (PETTYPE_SEQ.nextval, '기타');

select *
from PETTYPE;

/**********************************/
/* Table Name: 반려동물 */
/**********************************/
DROP TABLE PET;

CREATE TABLE Pet(
    PETNO                             NUMBER(10)     NOT NULL    PRIMARY KEY,
    PETNAME                           VARCHAR2(30)     NOT NULL,
    PETBIRTH                            VARCHAR2(11)    NOT NULL,
    PETGENDER                         char(1)       NOT NULL,
    PETDETAIL                         VARCHAR2(300)    ,
    PETTYPENO                         NUMBER(10)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
  FOREIGN KEY (PETTYPENO) REFERENCES PETTYPE (PETTYPENO),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE Pet is '반려동물';
COMMENT ON COLUMN Pet.PETNO is '반려동물번호';
COMMENT ON COLUMN Pet.PETNAME is '반려동물이름';
COMMENT ON COLUMN Pet.PETBIRTH is '반려동물생년월일';
COMMENT ON COLUMN Pet.PETGENDER is '반려동물성별';
COMMENT ON COLUMN Pet.PETDETAIL is '반려동물상세정보';
COMMENT ON COLUMN Pet.PETTYPENO is '분류 번호';
COMMENT ON COLUMN Pet.MEMBERNO is '회원번호';

DROP SEQUENCE PET_SEQ;

CREATE SEQUENCE PET_SEQ
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지


/**********************************/
/* Table Name: 반려동물 사진 */
/**********************************/
DROP TABLE PETPROFILE;

CREATE TABLE PETPROFILE(
    PETPROFILENO                            NUMBER(10)     NOT NULL    PRIMARY KEY,
    OGFILENAME                          VARCHAR2(100)    NOT NULL,
    SVFILENAME                          VARCHAR2(100)    NOT NULL,
--     FILESIZE                          VARCHAR2(100)    NOT NULL,
--     THUMBFILE                         VARCHAR2(100)    NOT NULL,
    SEQNO                             NUMBER(5)     NOT NULL,
    UPLOADDATE                        DATE      NOT NULL,
    PETNO                             NUMBER(10)     NULL,
  FOREIGN KEY (PETNO) REFERENCES Pet (PETNO)
);

COMMENT ON TABLE PETPROFILE is '반려동물';
COMMENT ON COLUMN PETPROFILE.PETPROFILENO is '프로필 사진 번호';
COMMENT ON COLUMN PETPROFILE.OGFILENAME is '원본파일이름';
COMMENT ON COLUMN PETPROFILE.SVFILENAME is '저장된파일이름';
-- COMMENT ON COLUMN PETPROFILE.FILESIZE is '파일 크기';
-- COMMENT ON COLUMN PETPROFILE.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN PETPROFILE.SEQNO is '사진 순서';
COMMENT ON COLUMN PETPROFILE.UPLOADDATE is '사진 등록 시간';
COMMENT ON COLUMN PETPROFILE.PETNO is '반려동물번호';

DROP SEQUENCE PETPROFILE_SEQ;

CREATE SEQUENCE PETPROFILE_SEQ
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지


/**********************************/
/* Table Name: 회원 프로필 사진 수정 */
/**********************************/
DROP TABLE Memberprofile;

CREATE TABLE Memberprofile(
		MPROFILENO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		file1                         		VARCHAR2(10)		 ,
		file1saved                    		VARCHAR2(100)		 ,
		THUMBFILE                     		VARCHAR2(100)		 ,
		FILESIZE                      		LONG		 ,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
    FOREIGN KEY (MEMBERNO) REFERENCES MEMBER (MEMBERNO) ON DELETE CASCADE
    );


COMMENT ON TABLE Memberprofile is '회원 프로필 사진 수정';
COMMENT ON COLUMN Memberprofile.MPROFILENO is '회원사진번호';
COMMENT ON COLUMN Memberprofile.file1MF  is '이미지파일';
COMMENT ON COLUMN Memberprofile.size1_label is '메인이미지 크기';
COMMENT ON COLUMN Memberprofile.file1 is '메인이미지';
COMMENT ON COLUMN Memberprofile.file1saved  is '저장된 메인이미지';
COMMENT ON COLUMN Memberprofile.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN Memberprofile.FILESIZE is '파일사이즈';
COMMENT ON COLUMN Memberprofile.MEMBERNO is '회원번호';

DROP SEQUENCE memberprofile_seq;

CREATE SEQUENCE memberprofile_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지
  
  
/**********************************/
/* Table Name: 공지게시판 */
/**********************************/
DROP TABLE notice;

CREATE TABLE notice(
		noticeno                      		NUMBER(10)		 NULL 		 PRIMARY KEY,
		title                    		VARCHAR2(30)		 NOT NULL ,
    notice                       		VARCHAR2(100)		 NOT NULL ,
		file                      		VARCHAR2(100)		 NULL ,
		noticedate                    		DATE		 NULL ,
    PASSWD                            VARCHAR2(50)     NOT NULL,
		MASTERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO)
);

COMMENT ON TABLE notice is '공지게시판';
COMMENT ON COLUMN notice.noticeno is '공지글 번호';
COMMENT ON COLUMN notice.title is '공지글 이름';
COMMENT ON COLUMN notice.notice is '공지글 내용';
COMMENT ON COLUMN notice.file is '첨부파일';
COMMENT ON COLUMN notice.noticedate is '등록일';
COMMENT ON COLUMN notice.passwd is '비밀번호';
COMMENT ON COLUMN notice.MASTERNO is '관리자번호';

DROP SEQUENCE NOTICE_SEQ;

CREATE SEQUENCE NOTICE_SEQ
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;     
    
ALTER TABLE notice ADD Likecnt NUMBER(10) DEFAULT 0 NOT NULL;
    
    
    
    
    
    
    
    
    
DROP TABLE noticeimg;

CREATE TABLE noticeimg(
		imgno                      		NUMBER(10)		NOT NULL 		 PRIMARY KEY,
		nfile                    		VARCHAR2(100)		 NULL ,
    filesaved                       		VARCHAR2(100)		 NULL ,
		thumbfile                      		VARCHAR2(100)		 NULL ,
		filesize                    		LONG		 NULL ,
    noticeno                          NUMBER(10)		NOT NULL ,
  FOREIGN KEY (noticeno) REFERENCES notice (noticeno)ON DELETE CASCADE
);

DROP SEQUENCE NOTICEIMG_SEQ;

CREATE SEQUENCE NOTICEIMG_SEQ
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE;     
  
INSERT INTO Memberprofile (MPROFILENO,memberno)
VALUES (memberprofile_seq.nextval,1);
  
INSERT INTO Memberprofile (MPROFILENO,memberno)
VALUES (memberprofile_seq.nextval,42);

INSERT INTO Memberprofile (MPROFILENO,memberno)
VALUES (memberprofile_seq.nextval,76);
  
ALTER TABLE member MODIFY (email VARCHAR2(30));
ALTER TABLE login MODIFY (conndate VARCHAR2(30));
ALTER TABLE login MODIFY (ip VARCHAR2(40));
ALTER TABLE login MODIFY (memberno NUMBER(10) NULL);
ALTER TABLE member MODIFY (ADDR_DETAIL  VARCHAR2(30) NULL);



SELECT l.loginno, l.ip, l.conndate, l.memberno, m.id, m.name
FROM login l LEFT JOIN member m ON l.memberno = m.memberno
ORDER BY l.loginno DESC;

    SELECT l.loginno, l.ip, l.conndate, l.memberno, m.id, m.name
    FROM login l , member m
    WHERE l.memberno = m.memberno
    ORDER BY l.loginno DESC;

INSERT INTO memberprofile(mprofileno, memberno)
VALUES (memberprofile_seq.nextval,42);

commit;

DELETE FROM login WHERE memberno=63;

ALTER TABLE Memberprofile
DROP CONSTRAINT SYS_C008015;

ALTER TABLE Memberprofile
DROP CONSTRAINT SYS_C008015;

ALTER TABLE Memberprofile
ADD CONSTRAINT SYS_C008015
FOREIGN KEY (MEMBERNO)
REFERENCES MEMBER (MEMBERNO)
ON DELETE CASCADE;


ALTER TABLE login
DROP CONSTRAINT SYS_C008053;

ALTER TABLE login
ADD CONSTRAINT SYS_C008053
FOREIGN KEY (MEMBERNO)
REFERENCES MEMBER (MEMBERNO)
ON DELETE SET NULL;

ALTER TABLE login
MODIFY memberno DEFAULT 0;

UPDATE login 
SET ip = '1'
WHERE memberno IS NULL;

SELECT COUNT(*) as cnt
FROM login
    WHERE (UPPER(ip) LIKE '%' || UPPER('하정') || '%' 
          OR UPPER(conndate) LIKE '%' || UPPER('하정') || '%' 
          OR UPPER(memberno) LIKE '%' || UPPER('하정') || '%') AND memberno != 0;
          
          
ALTER TABLE notice MODIFY (title VARCHAR2(50));

SELECT mprofileno, file1, file1saved, thumbfile, filesize, memberno
FROM memberprofile
WHERE memberno =42;

DELETE FROM memberprofile WHERE mprofileno != 162 and memberno = 42;

SELECT * FROM memberprofile ORDER BY MEMBERNO DESC;


        SELECT imgno, nfile, filesaved, thumbfile, filesize, noticeno
        FROM noticeimg
        WHERE noticeno = 24
        ORDER BY imgno ASC;
        
        
        
        
        
        
--likeno
--noticeno
---memberno
--likedate

DROP TABLE sympathy;

CREATE TABLE sympathy(
		sympathyno                      		NUMBER(10)		NOT NULL 		 PRIMARY KEY,
		noticeno                    		NUMBER(10)		NOT NULL ,
    memberno                       		NUMBER(10)		NOT NULL ,
		sdate                      		date		NOT NULL ,
  FOREIGN KEY (noticeno) REFERENCES notice (noticeno)ON DELETE CASCADE ,
  FOREIGN KEY (memberno) REFERENCES member (memberno)ON DELETE CASCADE
);

DROP SEQUENCE SYMPATHY_SEQ;

CREATE SEQUENCE SYMPATHY_SEQ
    START WITH 1              -- 시작 번호
    INCREMENT BY 1          -- 증가값
    MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
    CACHE 2                       -- 2번은 메모리에서만 계산
    NOCYCLE; 
    
    
    
    SELECT sp.sympathyno, sp.memberno, sp.noticeno, sp.sdate, title, notice, nfile, noticedate
    FROM sympathy sp
    LEFT JOIN notice nt ON sp.noticeno = nt.noticeno
    WHERE memberno=3
    ORDER BY sympathyno DESC;
    
    DROP TABLE EMPLOYEE;