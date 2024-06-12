DROP TABLE COMMUNITYCATE;
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

DROP SEQUENCE COMMUNITYCATE_SEQ;

CREATE SEQUENCE COMMUNITYCATE_SEQ
  START WITH 1         
  INCREMENT BY 1       
  MAXVALUE 9999999999  
  CACHE 2             
  NOCYCLE;            


-- CREATE
INSERT INTO COMMUNITYCATE(CTYPENO, CNT, TYPENAME, MASTERNO)
VALUES(COMMUNITYCATE_SEQ.nextval, 0,'일반게시판', 9);

SELECT * FROM communitycate;

COMMIT;

-- READ: 목록
SELECT ctypeno, cnt, typename, masterno
FROM communitycate
ORDER BY ctypeno ASC;

-- READ: 조회
SELECT ctypeno, cnt, typename, masterno
FROM communitycate
WHERE ctypeno=1;

-- UPDATE: 수정
UPDATE communitycate
SET typename='test' 
WHERE ctypeno=2;

SELECT * FROM communitycate;

COMMIT;

-- DELETE: 삭제
DELETE FROM communitycate WHERE ctypeno=2;

SELECT * FROM communitycate;

/**********************************/
/* Table Name: 일반 커뮤니티 */
/**********************************/
DROP TABLE COMMUNITY;
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

DROP SEQUENCE COMMUNITY_SEQ;
CREATE SEQUENCE COMMUNITY_SEQ
  START WITH 1         
  INCREMENT BY 1       
  MAXVALUE 9999999999  
  CACHE 2             
  NOCYCLE;            

-- CREATE
INSERT INTO COMMUNITY(communityno,title,content,vcnt,rcnt,writedate,tag,memberno,ctypeno)
VALUES(COMMUNITY_SEQ.nextval,'test','테스트입니다',1,1,sysdate,'tagtest',1,3);

SELECT * FROM community;

-- READ: 목록
    SELECT communityno,title,content,vcnt,rcnt,writedate,tag,memberno,ctypeno
    FROM community
    ORDER BY communityno ASC;

-- READ: 조회
SELECT communityno,title,content,vcnt,rcnt,writedate,tag,memberno,ctypeno
FROM community
WHERE communityno=2;

-- UPDATE: 수정
    UPDATE community
    SET  title ='test1',content='테스트 수정입니다',vcnt=2,rcnt=3,writedate=sysdate,tag='수정태그',memberno=2,ctypeno=3
    WHERE communityno=2;

SELECT * FROM community;

COMMIT;

-- DELETE: 삭제
DELETE FROM community WHERE communityno=1;

SELECT * FROM community;
 -- search_paging
SELECT communityno,title,content,vcnt,rcnt,writedate,tag,memberno,ctypeno, r
FROM(   
        SELECT communityno,title,content,vcnt,rcnt,writedate,tag,memberno,ctypeno, rownum as r
        FROM (
            SELECT communityno,title,content,vcnt,rcnt,writedate,tag,memberno,ctypeno
            FROM community             
            WHERE UPPER(title)LIKE '%'||UPPER('test')||'%'OR UPPER(tag)LIKE '%' ||UPPER('test')
            ORDER BY communityno ASC
            )
        );
-- cnt        
    SELECT COUNT(*) as cnt
        FROM community
          WHERE UPPER(title) LIKE '%' || UPPER('word') || '%' OR UPPER(tag) LIKE '%' || UPPER('word') || '%'  
        ORDER BY communityno ASC;
        
SELECT c.communityno,c.title,c.content,c.vcnt,c.rcnt,c.writedate,c.tag,c.memberno,c.ctypeno,ca.cano, ca.filename, ca.filesize, ca.thumbfile,ca.communityno 
FROM community c
LEFT OUTER JOIN communityattachment ca
ON c.communityno = ca.communityno;

SELECT c.communityno,c.title,c.content,c.vcnt,c.rcnt,c.writedate,c.tag,c.memberno,c.ctypeno,ca.cano, ca.filename, ca.filesize, ca.thumbfile,ca.communityno 
FROM community c
RIGHT OUTER JOIN communityattachment ca
ON c.communityno = ca.communityno;



SELECT c.communityno,c.title,c.content,c.vcnt,c.rcnt,c.writedate,c.tag,c.memberno,c.ctypeno,ca.cano, ca.filename, ca.filesize, ca.thumbfile,ca.communityno 
FROM community c
INNER JOIN communityattachment ca
ON c.communityno = ca.communityno;


SELECT c.communityno,c.title,c.content,c.vcnt,c.rcnt,c.writedate,c.tag,c.memberno,c.ctypeno,ca.cano, ca.filename, ca.filesize, ca.thumbfile,ca.communityno 
FROM community c 
LEFT OUTER JOIN communityattachment ca
ON c.communityno = ca.communityno
WHERE c.communityno =32;



SELECT communityno,title,content,vcnt,rcnt,writedate,tag,memberno,ctypeno
FROM community
WHERE communityno=32;

SELECT cano, filename, filesize, thumbfile,communityno 
FROM communityattachment
WHERE communityno=32;
commit;
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
DROP TABLE COMMUNITYATTACHMENT;

CREATE TABLE COMMUNITYATTACHMENT(
    CANO                              NUMBER(10)     NOT NULL    PRIMARY KEY,
    FILENAME                          VARCHAR2(100)    NOT NULL,
    FILESIZE                          number(13)    NOT NULL,
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

DROP SEQUENCE COMMUNITYATTACHMENT_SEQ;
CREATE SEQUENCE COMMUNITYATTACHMENT_SEQ
  START WITH 1         
  INCREMENT BY 1       
  MAXVALUE 9999999999  
  CACHE 2             
  NOCYCLE;            

INSERT INTO communityattachment(cano, filename, filesize, thumbfile,communityno)
VALUES(COMMUNITYATTACHMENT_SEQ.nextval,'puppy.png',1000,'puppy.png',27);

SELECT * FROM communityattachment;

commit;


 -- read
SELECT cano, filename, filesize, thumbfile,communityno 
FROM communityattachment
ORDER BY communityno desc;

-- update
UPDATE communityattachment
SET filename ='cat.png',thumbfile='cat.png'
WHERE communityno = 14;

SELECT * FROM communityattachment;

--delete
DELETE FROM communityattachment WHERE communityno=0;

