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
    ADDR_CODE                         VARCHAR2(8)    NOT NULL,
    ADDR_MAIN                         VARCHAR2(30)   NOT NULL,
    ADDR_DETAIL                       VARCHAR2(30)     NOT NULL,
    JOINDATE                          DATE     NOT NULL,
    STATUS                            VARCHAR2(10)     DEFAULT 0     NOT NULL
);

SELECT * FROM member;

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
    MEMBERNO                          NUMBER(10)     NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO) ON DELETE CASCADE
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
    PETKIND                           VARCHAR2(50)     NULL 
);

COMMENT ON TABLE PETTYPE is '반려동물 분류';
COMMENT ON COLUMN PETTYPE.PETTYPENO is '분류 번호';
COMMENT ON COLUMN PETTYPE.PETTYPE is '반려동물 종류';
COMMENT ON COLUMN PETTYPE.PETKIND is '반려동물 품종';


/**********************************/
/* Table Name: 반려동물 */
/**********************************/
CREATE TABLE Pet(
    PETNO                             NUMBER(10)     NOT NULL    PRIMARY KEY,
    PETNAME                           VARCHAR2(30)     NOT NULL,
    PETAGE                            NUMBER(2)    NOT NULL,
    PETTYPENO                         NUMBER(10)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
  FOREIGN KEY (PETTYPENO) REFERENCES PETTYPE (PETTYPENO),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE Pet is '반려동물';
COMMENT ON COLUMN Pet.PETNO is '반려동물번호';
COMMENT ON COLUMN Pet.PETNAME is '반려동물이름';
COMMENT ON COLUMN Pet.PETAGE is '반려동물나이';
COMMENT ON COLUMN Pet.PETTYPENO is '분류 번호';
COMMENT ON COLUMN Pet.MEMBERNO is '회원번호';


/**********************************/
/* Table Name: 반려동물 사진 */
/**********************************/
CREATE TABLE PETPROFILE(
    PETPROFILENO                            NUMBER(10)     NOT NULL    PRIMARY KEY,
    FILENAME                          VARCHAR2(100)    NOT NULL,
    FILESIZE                          VARCHAR2(100)    NOT NULL,
    THUMBFILE                         VARCHAR2(100)    NOT NULL,
    PETNO                             NUMBER(10)     NULL ,
  FOREIGN KEY (PETNO) REFERENCES Pet (PETNO)
);

COMMENT ON TABLE PETPROFILE is '반려동물';
COMMENT ON COLUMN PETPROFILE.PETPROFILENO is '프로필 사진 번호';
COMMENT ON COLUMN PETPROFILE.FILENAME is '파일이름';
COMMENT ON COLUMN PETPROFILE.FILESIZE is '파일 크기';
COMMENT ON COLUMN PETPROFILE.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN PETPROFILE.PETNO is '반려동물번호';


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

ALTER TABLE member MODIFY (gender VARCHAR2(10));
ALTER TABLE login MODIFY (conndate VARCHAR2(30));
ALTER TABLE login MODIFY (ip VARCHAR2(40));

SELECT l.loginno, l.ip, l.conndate, l.memberno, m.id, m.name
FROM login l LEFT JOIN member m ON l.memberno = m.memberno
ORDER BY l.loginno DESC;

    SELECT l.loginno, l.ip, l.conndate, l.memberno, m.id, m.name
    FROM login l , member m
    WHERE l.memberno = m.memberno
    ORDER BY l.loginno DESC;

INSERT INTO memberprofile(mprofileno, memberno)
VALUES (memberprofile_seq.nextval,61);

commit;

DELETE FROM login WHERE memberno=63;

<<<<<<< HEAD
SELECT A.SID , A.SERIAL# , object_name , A.SID || ', ' || A.SERIAL# AS KILL_TASK 
  FROM V$SESSION A 
 INNER JOIN V$LOCK B ON A.SID = B.SID 
 INNER JOIN DBA_OBJECTS C ON B.ID1 = C.OBJECT_ID 
 WHERE B.TYPE = 'TM' ;
=======


ALTER
>>>>>>> df485a745291a6bd9a9a25236824d93d5fe63928
