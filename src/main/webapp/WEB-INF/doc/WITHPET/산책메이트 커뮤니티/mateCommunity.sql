/**********************************/
/* Table Name: 산책 메이트 커뮤니티 */
/**********************************/
DROP TABLE MATECOMMUNITY;

CREATE TABLE MATECOMMUNITY(
    MCOMMUNITYNO                      NUMBER(10)     NOT NULL    PRIMARY KEY,
    TITLE                             VARCHAR2(300)    NOT NULL,
    CONTENT                           VARCHAR2(1000)     NOT NULL,
    VIEWCNT                               NUMBER(10)     default 10  NOT NULL,
    STARTINGP                         VARCHAR2(100)    NOT NULL,
    STARTINGDETAIL                   VARCHAR2(100)    NOT NULL,
    WALKINGM                          NUMBER(3)    NOT NULL,
    WDATE                             DATE     NOT NULL,
    ASSEMBLETIME                      VARCHAR(30)     NOT NULL,
    STATUS                            number(1) default 0 not null ,
    SEARCHTAG                         varchar2(200)  not null,
    PETTYPENO                         number(10)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
  FOREIGN KEY (PETTYPENO) REFERENCES PETTYPE (PETTYPENO),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE MATECOMMUNITY is '산책 메이트 커뮤니티';
COMMENT ON COLUMN MATECOMMUNITY.MCOMMUNITYNO is '글번호';
COMMENT ON COLUMN MATECOMMUNITY.TITLE is '제목';
COMMENT ON COLUMN MATECOMMUNITY.CONTENT is '내용';
COMMENT ON COLUMN MATECOMMUNITY.VIEWCNT is '조회수';
COMMENT ON COLUMN MATECOMMUNITY.STARTINGP is '출발지점';
COMMENT ON COLUMN MATECOMMUNITY.STARTINGDETAIL is '상세지점';
COMMENT ON COLUMN MATECOMMUNITY.WALKINGM is '구하는 산책 인원';
COMMENT ON COLUMN MATECOMMUNITY.WDATE is '작성일자';
COMMENT ON COLUMN MATECOMMUNITY.ASSEMBLETIME is '집합날짜&시간';
COMMENT ON COLUMN MATECOMMUNITY.STATUS is '모집 상태'; -- 모집중 : 0, 모집 완료 : 1
COMMENT ON COLUMN MATECOMMUNITY.SEARCHTAG is '검색태그';
COMMENT ON COLUMN MATECOMMUNITY.PETTYPENO is '분류 번호';
COMMENT ON COLUMN MATECOMMUNITY.MEMBERNO is '회원번호';

DROP SEQUENCE MATECOMMUNITY_SEQ;
CREATE SEQUENCE MATECOMMUNITY_SEQ
    start with 1
    increment by 1
    maxvalue 9999999999
    cache 2
    nocycle;

insert into MATECOMMUNITY(MCOMMUNITYNO, TITLE, CONTENT, VIEWCNT, STARTINGP, WALKINGM, WDATE, STATUS, PETTYPENO, MEMBERNO)
values (MATECOMMUNITY_SEQ.nextval, '산책할사람 구해요!!!','강아지 산책 같이해요',0,'송파구 잠실',3,sysdate,0,1,1);

/**********************************/
/* Table Name: 산책 메이트 신청 */
/**********************************/
CREATE TABLE MATEAPPLY(
    ANO                               NUMBER(10)     NOT NULL    PRIMARY KEY,
    ASTATUS                           VARCHAR2(15)     NOT NULL,
    ADATE                             DATE     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
    MCOMMUNITYNO                      NUMBER(10)     NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (MCOMMUNITYNO) REFERENCES MATECOMMUNITY (MCOMMUNITYNO)
);

COMMENT ON TABLE MATEAPPLY is '산책 메이트 신청';
COMMENT ON COLUMN MATEAPPLY.ANO is '신청 번호';
COMMENT ON COLUMN MATEAPPLY.ASTATUS is '신청상태';
COMMENT ON COLUMN MATEAPPLY.ADATE is '신청일자';
COMMENT ON COLUMN MATEAPPLY.MEMBERNO is '회원번호';
COMMENT ON COLUMN MATEAPPLY.MCOMMUNITYNO is '글번호';


/**********************************/
/* Table Name: 산책 메이트 후기 */
/**********************************/
CREATE TABLE MATEREVIEW(
    RNO                               NUMBER(10)     NOT NULL    PRIMARY KEY,
    REVIEWCOMMENT                     VARCHAR2(1000)     NOT NULL,
    REVIEWGRADE                       number(2)    NOT NULL,
    RDATE                             DATE     NOT NULL,
    MEMBERNO                          NUMBER(10)     NULL ,
    MCOMMUNITYNO                      NUMBER(10)     NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (MCOMMUNITYNO) REFERENCES MATECOMMUNITY (MCOMMUNITYNO)
);

COMMENT ON TABLE MATEREVIEW is '산책 메이트 후기';
COMMENT ON COLUMN MATEREVIEW.RNO is '후기 번호';
COMMENT ON COLUMN MATEREVIEW.REVIEWCOMMENT is '후기 내용';
COMMENT ON COLUMN MATEREVIEW.REVIEWGRADE is '후기 점수';
COMMENT ON COLUMN MATEREVIEW.RDATE is '작성일';
COMMENT ON COLUMN MATEREVIEW.MEMBERNO is '회원번호';
COMMENT ON COLUMN MATEREVIEW.MCOMMUNITYNO is '글번호';


