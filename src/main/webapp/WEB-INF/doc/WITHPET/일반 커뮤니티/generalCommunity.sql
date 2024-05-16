/**********************************/
/* Table Name: 일반 게시판 카테고리 */
/**********************************/
CREATE TABLE COMMUNITYCATE(
    CTYPENO                           NUMBER(10)     NOT NULL    PRIMARY KEY,
    CNT                               NUMBER(10)     DEFAULT 0     NOT NULL,
    TYPENAME                          VARCHAR2(100)    NOT NULL,
    MASTERNO                          NUMBER(10)     NULL ,
  FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO)
);

COMMENT ON TABLE COMMUNITYCATE is '일반 게시판 카테고리';
COMMENT ON COLUMN COMMUNITYCATE.CTYPENO is '게시판 종류 번호';
COMMENT ON COLUMN COMMUNITYCATE.CNT is '게시글 수';
COMMENT ON COLUMN COMMUNITYCATE.TYPENAME is '게시판 이름';
COMMENT ON COLUMN COMMUNITYCATE.MASTERNO is '관리자번호';


/**********************************/
/* Table Name: 일반 커뮤니티 */
/**********************************/
CREATE TABLE COMMUNITY(
    COMMUNITYNO                       NUMBER(10)     NOT NULL    PRIMARY KEY,
    TITLE                             VARCHAR2(100)    NOT NULL,
    CONTENT                           VARCHAR2(1000)     NOT NULL,
    VCNT                              NUMBER(10)     NOT NULL,
    RCNT                              NUMBER(10)     NOT NULL,
    WRITEDATE                         DATE     NOT NULL,
    TAG                               VARCHAR2(50)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NULL ,
    CTYPENO                           NUMBER(10)     NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (CTYPENO) REFERENCES COMMUNITYCATE (CTYPENO)
);

COMMENT ON TABLE COMMUNITY is '일반 커뮤니티';
COMMENT ON COLUMN COMMUNITY.COMMUNITYNO is '글 번호';
COMMENT ON COLUMN COMMUNITY.TITLE is '제목';
COMMENT ON COLUMN COMMUNITY.CONTENT is '내용';
COMMENT ON COLUMN COMMUNITY.VCNT is '조회수';
COMMENT ON COLUMN COMMUNITY.RCNT is '좋아요수';
COMMENT ON COLUMN COMMUNITY.WRITEDATE is '작성일자';
COMMENT ON COLUMN COMMUNITY.TAG is '검색태그';
COMMENT ON COLUMN COMMUNITY.MEMBERNO is '회원번호';
COMMENT ON COLUMN COMMUNITY.CTYPENO is '게시판 종류 번호';


/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE REPLY(
    REPLYNO                           NUMBER(10)     NULL      PRIMARY KEY,
    CONTENT                           VARCHAR2(500)    NOT NULL,
    RDATE                             DATE     NOT NULL,
    COMMUNITYNO                       NUMBER(10)     NOT NULL,
    MEMBERNO                          NUMBER(10)     NOT NULL,
  FOREIGN KEY (COMMUNITYNO) REFERENCES COMMUNITY (COMMUNITYNO),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE REPLY is '댓글';
COMMENT ON COLUMN REPLY.REPLYNO is '댓글번호';
COMMENT ON COLUMN REPLY.CONTENT is '내용';
COMMENT ON COLUMN REPLY.RDATE is '등록일';
COMMENT ON COLUMN REPLY.COMMUNITYNO is '글 번호';
COMMENT ON COLUMN REPLY.MEMBERNO is '회원번호';


/**********************************/
/* Table Name: 일반 커뮤니티 첨부파일 */
/**********************************/
CREATE TABLE COMMUNITYATTACHMENT(
    CANO                              NUMBER(10)     NOT NULL    PRIMARY KEY,
    FILENAME                          VARCHAR2(100)    NOT NULL,
    FILESIZE                          VARCHAR2(100)    NOT NULL,
    THUMBFILE                         VARCHAR2(100)    NOT NULL,
    COMMUNITYNO                       NUMBER(10)     NULL ,
  FOREIGN KEY (COMMUNITYNO) REFERENCES COMMUNITY (COMMUNITYNO)
);

COMMENT ON TABLE COMMUNITYATTACHMENT is '일반 커뮤니티 첨부파일';
COMMENT ON COLUMN COMMUNITYATTACHMENT.CANO is '첨부파일 번호';
COMMENT ON COLUMN COMMUNITYATTACHMENT.FILENAME is '파일명';
COMMENT ON COLUMN COMMUNITYATTACHMENT.FILESIZE is '파일사이즈';
COMMENT ON COLUMN COMMUNITYATTACHMENT.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN COMMUNITYATTACHMENT.COMMUNITYNO is '글 번호';

