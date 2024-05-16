/**********************************/
/* Table Name: 산책 메이트 커뮤니티 */
/**********************************/
CREATE TABLE MATECOMMUNITY(
    MCOMMUNITYNO                      NUMBER(10)     NOT NULL    PRIMARY KEY,
    TITLE                             VARCHAR2(300)    NOT NULL,
    CONTENT                           VARCHAR2(1000)     NOT NULL,
    CNT                               NUMBER(10)     NOT NULL,
    STARTINGP                         VARCHAR2(100)    NOT NULL,
    WALKINGM                          NUMBER(3)    NOT NULL,
    WDATE                             DATE     NOT NULL,
    PETTYPENO                         number(10)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
  FOREIGN KEY (PETTYPENO) REFERENCES PETTYPE (PETTYPENO),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE MATECOMMUNITY is '산책 메이트 커뮤니티';
COMMENT ON COLUMN MATECOMMUNITY.MCOMMUNITYNO is '글번호';
COMMENT ON COLUMN MATECOMMUNITY.TITLE is '제목';
COMMENT ON COLUMN MATECOMMUNITY.CONTENT is '내용';
COMMENT ON COLUMN MATECOMMUNITY.CNT is '조회수';
COMMENT ON COLUMN MATECOMMUNITY.STARTINGP is '출발지점';
COMMENT ON COLUMN MATECOMMUNITY.WALKINGM is '구하는 산책 인원';
COMMENT ON COLUMN MATECOMMUNITY.WDATE is '작성일자';
COMMENT ON COLUMN MATECOMMUNITY.PETTYPENO is '분류 번호';
COMMENT ON COLUMN MATECOMMUNITY.MEMBERNO is '회원번호';


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


