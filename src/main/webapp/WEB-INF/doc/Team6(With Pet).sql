/**********************************/
/* Table Name: 회원 */
/**********************************/
DROP TABLE MEMBER;

CREATE TABLE member(
		MEMBERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(50)		 NOT NULL,
		NAME                          		VARCHAR2(15)		 NOT NULL,
		GENDER                        		VARCHAR2(5)		 NOT NULL,
		BIRTHDAY                      		DATE		 NOT NULL,
		PHONE                         		VARCHAR2(14)		 NOT NULL,
		ADDR_CODE                     		VARCHAR2(8)		 NOT NULL,
		ADDR_DETAIL                   		VARCHAR2(30)		 NOT NULL,
		JOINDATE                      		DATE		 NOT NULL,
		STATUS                        		VARCHAR2(10)		 DEFAULT 0		 NOT NULL
);

CREATE SEQUENCE member_MEMBERNO_SEQ NOMAXVALUE NOCACHE NOORDER NOCYCLE;

CREATE TRIGGER member_MEMBERNO_TRG
BEFORE INSERT ON member
FOR EACH ROW
BEGIN
IF :NEW.MEMBERNO IS NOT NULL THEN
  SELECT member_MEMBERNO_SEQ.NEXTVAL INTO :NEW.MEMBERNO FROM DUAL;
END IF;
END;

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
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE MASTER(
		MASTERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MASTERID                      		VARCHAR2(30)		 NOT NULL,
		MASTERPASSWD                  		VARCHAR2(50)		 NOT NULL
);

COMMENT ON TABLE MASTER is '관리자';
COMMENT ON COLUMN MASTER.MASTERNO is '관리자번호';
COMMENT ON COLUMN MASTER.MASTERID is '관리자 아이디';
COMMENT ON COLUMN MASTER.MASTERPASSWD is '관리자 비밀번호';


/**********************************/
/* Table Name: 문화시설 */
/**********************************/
CREATE TABLE CURTULEFACILITY(
		CULTUREFNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CNAME                         		VARCHAR2(200)		 NOT NULL,
		LATITUDE                      		VARCHAR2(10)		 NOT NULL,
		LONGITUDE                     		VARCHAR2(10)		 NOT NULL,
		ADDR_CODE                     		VARCHAR2(10)		 NOT NULL,
		PHONE                         		VARCHAR2(15)		 NOT NULL,
		CLOSEDDAYS                    		DATE		 NOT NULL,
		OPERATINGTIME                 		VARCHAR2(30)		 NOT NULL,
		PA                            		VARCHAR2(10)		 NOT NULL,
		MASTERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO)
);

COMMENT ON TABLE CURTULEFACILITY is '문화시설';
COMMENT ON COLUMN CURTULEFACILITY.CULTUREFNO is '문화시설번호';
COMMENT ON COLUMN CURTULEFACILITY.CNAME is '문화시설이름';
COMMENT ON COLUMN CURTULEFACILITY.LATITUDE is '위도';
COMMENT ON COLUMN CURTULEFACILITY.LONGITUDE is '경도';
COMMENT ON COLUMN CURTULEFACILITY.ADDR_CODE is '우편번호';
COMMENT ON COLUMN CURTULEFACILITY.PHONE is '전화번호';
COMMENT ON COLUMN CURTULEFACILITY.CLOSEDDAYS is '휴무일';
COMMENT ON COLUMN CURTULEFACILITY.OPERATINGTIME is '운영시간';
COMMENT ON COLUMN CURTULEFACILITY.PA is '주차가능여부';
COMMENT ON COLUMN CURTULEFACILITY.MASTERNO is '관리자번호';


/**********************************/
/* Table Name: 일반 게시판 카테고리 */
/**********************************/
CREATE TABLE COMMUNITYCATE(
		CTYPENO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CNT                           		NUMBER(10)		 DEFAULT 0		 NOT NULL,
		TYPENAME                      		VARCHAR2(100)		 NOT NULL,
		MASTERNO                      		NUMBER(10)		 NULL ,
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
		COMMUNITYNO                   		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(50)		 NOT NULL,
		CONTENT                       		VARCHAR2(10)		 NOT NULL,
		VCNT                          		NUMBER(10)		 NOT NULL,
		RCNT                          		NUMBER(10)		 NOT NULL,
		WRITEDATE                     		DATE		 NOT NULL,
		TAG                           		VARCHAR2(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		CTYPENO                       		NUMBER(10)		 NULL ,
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
		REPLYNO                       		NUMBER(10)		 NULL 		 PRIMARY KEY,
		CONTENT                       		VARCHAR2(10)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		COMMUNITYNO                   		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
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
/* Table Name: 회원 로그인 내역 */
/**********************************/
CREATE TABLE login(
		LOGINNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		IP                            		VARCHAR2(10)		 NOT NULL,
		CONNDATE                      		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE login is '회원 로그인 내역';
COMMENT ON COLUMN login.LOGINNO is '로그인번호';
COMMENT ON COLUMN login.IP is '접속 아이피';
COMMENT ON COLUMN login.CONNDATE is '접속 일자';
COMMENT ON COLUMN login.MEMBERNO is '회원번호';


/**********************************/
/* Table Name: 반려동물 분류 */
/**********************************/
CREATE TABLE PETTYPE(
		PETTYPENO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		PETTYPE                       		VARCHAR2(30)		 NOT NULL,
		PETKIND                       		VARCHAR2(50)		 NULL 
);

COMMENT ON TABLE PETTYPE is '반려동물 분류';
COMMENT ON COLUMN PETTYPE.PETTYPENO is '분류 번호';
COMMENT ON COLUMN PETTYPE.PETTYPE is '반려동물 종류';
COMMENT ON COLUMN PETTYPE.PETKIND is '반려동물 품종';


/**********************************/
/* Table Name: 반려동물 */
/**********************************/
CREATE TABLE Pet(
		PETNO                         		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		PETNAME                       		VARCHAR2(30)		 NOT NULL,
		PETAGE                        		NUMBER(2)		 NOT NULL,
		PETTYPENO                     		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
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
/* Table Name: 문화시설 정보 첨부파일 */
/**********************************/
CREATE TABLE FACILITYATTACHMENT(
		FANO                          		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		FILENAME                      		VARCHAR2(100)		 NOT NULL,
		FILESIZE                      		VARCHAR2(100)		 NOT NULL,
		THUMBFILE                     		VARCHAR2(100)		 NOT NULL,
		CULTUREFNO                    		NUMBER(10)		 NULL ,
  FOREIGN KEY (CULTUREFNO) REFERENCES CURTULEFACILITY (CULTUREFNO)
);

COMMENT ON TABLE FACILITYATTACHMENT is '문화시설 정보 첨부파일';
COMMENT ON COLUMN FACILITYATTACHMENT.FANO is '첨부파일 번호';
COMMENT ON COLUMN FACILITYATTACHMENT.FILENAME is '파일명';
COMMENT ON COLUMN FACILITYATTACHMENT.FILESIZE is '파일사이즈';
COMMENT ON COLUMN FACILITYATTACHMENT.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN FACILITYATTACHMENT.CULTUREFNO is '문화시설번호';


/**********************************/
/* Table Name: 일반 커뮤니티 첨부파일 */
/**********************************/
CREATE TABLE COMMUNITYATTACHMENT(
		CANO                          		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		FILENAME                      		VARCHAR2(100)		 NOT NULL,
		FILESIZE                      		VARCHAR2(100)		 NOT NULL,
		THUMBFILE                     		VARCHAR2(100)		 NOT NULL,
		COMMUNITYNO                   		NUMBER(10)		 NULL ,
  FOREIGN KEY (COMMUNITYNO) REFERENCES COMMUNITY (COMMUNITYNO)
);

COMMENT ON TABLE COMMUNITYATTACHMENT is '일반 커뮤니티 첨부파일';
COMMENT ON COLUMN COMMUNITYATTACHMENT.CANO is '첨부파일 번호';
COMMENT ON COLUMN COMMUNITYATTACHMENT.FILENAME is '파일명';
COMMENT ON COLUMN COMMUNITYATTACHMENT.FILESIZE is '파일사이즈';
COMMENT ON COLUMN COMMUNITYATTACHMENT.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN COMMUNITYATTACHMENT.COMMUNITYNO is '글 번호';


/**********************************/
/* Table Name: 산책 메이트 커뮤니티 */
/**********************************/
CREATE TABLE MATECOMMUNITY(
		MCOMMUNITYNO                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(300)		 NOT NULL,
		CONTENT                       		VARCHAR2(1000)		 NOT NULL,
		CNT                           		NUMBER(10)		 NOT NULL,
		STARTINGP                     		VARCHAR2(100)		 NOT NULL,
		WALKINGM                      		NUMBER(3)		 NOT NULL,
		WDATE                         		DATE		 NOT NULL,
		PETTYPENO                     		number(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
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
		ANO                           		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ASTATUS                       		VARCHAR2(15)		 NOT NULL,
		ADATE                         		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		MCOMMUNITYNO                  		NUMBER(10)		 NOT NULL,
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
		RNO                           		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		REVIEWCOMMENT                 		VARCHAR2(1000)		 NOT NULL,
		REVIEWGRADE                   		number(2)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		MCOMMUNITYNO                  		NUMBER(10)		 NULL ,
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


/**********************************/
/* Table Name: 문화시설 후기 */
/**********************************/
CREATE TABLE FACILITYREVIEW(
		RNO                           		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		REVIEWCOMMENT                 		VARCHAR2(1000)		 NOT NULL,
		REVIEWGRADE                   		VARCHAR2(10)		 NOT NULL,
		RDATE                         		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		CULTUREFNO                    		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (CULTUREFNO) REFERENCES CURTULEFACILITY (CULTUREFNO)
);

COMMENT ON TABLE FACILITYREVIEW is '문화시설 후기';
COMMENT ON COLUMN FACILITYREVIEW.RNO is '후기 번호';
COMMENT ON COLUMN FACILITYREVIEW.REVIEWCOMMENT is '후기 내용';
COMMENT ON COLUMN FACILITYREVIEW.REVIEWGRADE is '후기 평점';
COMMENT ON COLUMN FACILITYREVIEW.RDATE is '작성일';
COMMENT ON COLUMN FACILITYREVIEW.MEMBERNO is '회원번호';
COMMENT ON COLUMN FACILITYREVIEW.CULTUREFNO is '문화시설번호';


/**********************************/
/* Table Name: 반려동물 */
/**********************************/
CREATE TABLE PETPROFILE(
		PETPROFILENO                         		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		FILENAME                      		VARCHAR2(100)		 NOT NULL,
		FILESIZE                      		VARCHAR2(100)		 NOT NULL,
		THUMBFILE                     		VARCHAR2(100)		 NOT NULL,
		PETNO                         		NUMBER(10)		 NULL ,
  FOREIGN KEY (PETNO) REFERENCES Pet (PETNO)
);

COMMENT ON TABLE PETPROFILE is '반려동물';
COMMENT ON COLUMN PETPROFILE.PETPROFILENO is '프로필 사진 번호';
COMMENT ON COLUMN PETPROFILE.FILENAME is '파일이름';
COMMENT ON COLUMN PETPROFILE.FILESIZE is '파일 크기';
COMMENT ON COLUMN PETPROFILE.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN PETPROFILE.PETNO is '반려동물번호';


/**********************************/
/* Table Name: 김성민 */
/**********************************/
CREATE TABLE KSM(

);

COMMENT ON TABLE KSM is '김성민';


/**********************************/
/* Table Name: 김준석 */
/**********************************/
CREATE TABLE KJS(

);

COMMENT ON TABLE KJS is '김준석';


/**********************************/
/* Table Name: 김창인 */
/**********************************/
CREATE TABLE KCI(

);

COMMENT ON TABLE KCI is '김창인';


/**********************************/
/* Table Name: 강승표 */
/**********************************/
CREATE TABLE KSP(

);

COMMENT ON TABLE KSP is '강승표';


/**********************************/
/* Table Name: 회원 프로필 사진 수정 */
/**********************************/
CREATE TABLE Memberprofile(
		MPROFILENO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		FILENAME                      		VARCHAR2(100)		 NOT NULL,
		FILESIZE                      		VARCHAR2(100)		 NOT NULL,
		THUMBFILE                     		VARCHAR2(100)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE Memberprofile is '회원 프로필 사진 수정';
COMMENT ON COLUMN Memberprofile.MPROFILENO is '회원사진번호';
COMMENT ON COLUMN Memberprofile.FILENAME is '파일명';
COMMENT ON COLUMN Memberprofile.FILESIZE is '파일사이즈';
COMMENT ON COLUMN Memberprofile.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN Memberprofile.MEMBERNO is '회원번호';


