/**********************************/
/* Table Name: 회원 */
/**********************************/
CREATE TABLE member(
		MEMBERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(50)		 NOT NULL,
		NAME                          		VARCHAR2(15)		 NOT NULL,
		SEX                           		VARCHAR2(10)		 NOT NULL,
		PHONENUMBER                   		VARCHAR2(14)		 NOT NULL,
		MAIL                          		VARCHAR2(8)		 NOT NULL,
		ADDRESS                       		VARCHAR2(30)		 NOT NULL,
		JOINDATE                      		DATE		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL
);

COMMENT ON TABLE member is '회원';
COMMENT ON COLUMN member.MEMBERNO is '회원번호';
COMMENT ON COLUMN member.ID is '아이디';
COMMENT ON COLUMN member.PASSWD is '패스워드';
COMMENT ON COLUMN member.NAME is '이름';
COMMENT ON COLUMN member.SEX is '성별';
COMMENT ON COLUMN member.PHONENUMBER is '전화번호';
COMMENT ON COLUMN member.MAIL is '우편번호';
COMMENT ON COLUMN member.ADDRESS is '주소';
COMMENT ON COLUMN member.JOINDATE is '가입일';
COMMENT ON COLUMN member.GRADE is '등급';


/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE Master(
		MASTERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MASTERID                      		VARCHAR2(10)		 NOT NULL,
		MASTERPASSWD                  		VARCHAR2(10)		 NOT NULL
);

COMMENT ON TABLE Master is '관리자';
COMMENT ON COLUMN Master.MASTERNO is '관리자번호';
COMMENT ON COLUMN Master.MASTERID is '관리자 아이디';
COMMENT ON COLUMN Master.MASTERPASSWD is '관리자 비밀번호';


/**********************************/
/* Table Name: 문화시설 */
/**********************************/
CREATE TABLE Culture F(
		CULTUREFNO                    		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CNAME                         		VARCHAR2(15)		 NOT NULL,
		LATITUDE                      		VARCHAR2(10)		 NOT NULL,
		LONGITUDE                     		VARCHAR2(10)		 NOT NULL,
		ZIPCODE                       		VARCHAR2(8)		 NOT NULL,
		PHONENUMBER                   		VARCHAR2(14)		 NOT NULL,
		CLOSEDDAYS                    		DATE		 NOT NULL,
		OPERATINGTIME                 		VARCHAR2(15)		 NOT NULL,
		PA                            		VARCHAR2(10)		 NOT NULL,
		MASTERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MASTERNO) REFERENCES Master (MASTERNO)
);

COMMENT ON TABLE Culture F is '문화시설';
COMMENT ON COLUMN Culture F.CULTUREFNO is '문화시설번호';
COMMENT ON COLUMN Culture F.CNAME is '문화시설이름';
COMMENT ON COLUMN Culture F.LATITUDE is '위도';
COMMENT ON COLUMN Culture F.LONGITUDE is '경도';
COMMENT ON COLUMN Culture F.ZIPCODE is '우편번호';
COMMENT ON COLUMN Culture F.PHONENUMBER is '전화번호';
COMMENT ON COLUMN Culture F.CLOSEDDAYS is '휴무일';
COMMENT ON COLUMN Culture F.OPERATINGTIME is '운영시간';
COMMENT ON COLUMN Culture F.PA is '주차가능여부';
COMMENT ON COLUMN Culture F.MASTERNO is '관리자번호';


/**********************************/
/* Table Name: 일반 게시판 카테고리 */
/**********************************/
CREATE TABLE Notice(
		TYPENO                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		CNT                           		NUMBER(10)		 DEFAULT 0		 NOT NULL,
		COMTYPENAME                   		VARCHAR2(10)		 NOT NULL,
		MASTERNO                      		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (MASTERNO) REFERENCES Master (MASTERNO)
);

COMMENT ON TABLE Notice is '일반 게시판 카테고리';
COMMENT ON COLUMN Notice.TYPENO is '게시판 종류 번호';
COMMENT ON COLUMN Notice.CNT is '게시글 수';
COMMENT ON COLUMN Notice.COMTYPENAME is '게시판 종류';
COMMENT ON COLUMN Notice.MASTERNO is '관리자번호';


/**********************************/
/* Table Name: 일반 커뮤니티 */
/**********************************/
CREATE TABLE Normal Com(
		WRITENO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(50)		 NOT NULL,
		COMMENT                       		VARCHAR2(10)		 NOT NULL,
		VCNT                          		NUMBER(10)		 NOT NULL,
		RCNT                          		NUMBER(10)		 NOT NULL,
		WRITEDATE                     		DATE		 NOT NULL,
		TAG                           		VARCHAR2(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		TYPENO                        		NUMBER(10)		 NOT NULL,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (TYPENO) REFERENCES Notice (TYPENO)
);

COMMENT ON TABLE Normal Com is '일반 커뮤니티';
COMMENT ON COLUMN Normal Com.WRITENO is '글 번호';
COMMENT ON COLUMN Normal Com.TITLE is '제목';
COMMENT ON COLUMN Normal Com.COMMENT is '내용';
COMMENT ON COLUMN Normal Com.VCNT is '조회수';
COMMENT ON COLUMN Normal Com.RCNT is '좋아요수';
COMMENT ON COLUMN Normal Com.WRITEDATE is '작성일자';
COMMENT ON COLUMN Normal Com.TAG is '검색태그';
COMMENT ON COLUMN Normal Com.MEMBERNO is '회원번호';
COMMENT ON COLUMN Normal Com.TYPENO is '게시판 종류 번호';


/**********************************/
/* Table Name: 댓글 */
/**********************************/
CREATE TABLE comment(
		COMMENTNO                     		NUMBER(10)		 NULL 		 PRIMARY KEY,
		CONTENTS                      		VARCHAR2(10)		 NOT NULL,
		PASSWD                        		VARCHAR2(10)		 NOT NULL,
		COLUMN_4                      		INTEGER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		WRITENO                       		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (WRITENO) REFERENCES Normal Com (WRITENO)
);

COMMENT ON TABLE comment is '댓글';
COMMENT ON COLUMN comment.COMMENTNO is '댓글번호';
COMMENT ON COLUMN comment.CONTENTS is '내용';
COMMENT ON COLUMN comment.PASSWD is '비밀번호';
COMMENT ON COLUMN comment.COLUMN_4 is '등록일';
COMMENT ON COLUMN comment.MEMBERNO is '회원번호';
COMMENT ON COLUMN comment.WRITENO is '글 번호';


/**********************************/
/* Table Name: 회원 로그인 내역 */
/**********************************/
CREATE TABLE login(
		loginno                       		NUMBER(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE login is '회원 로그인 내역';
COMMENT ON COLUMN login.loginno is '로그인번호';
COMMENT ON COLUMN login.MEMBERNO is '회원번호';


/**********************************/
/* Table Name: 반려동물 분류 */
/**********************************/
CREATE TABLE TABLE_19(
		PETTYPENO                     		INTEGER(10)		 NOT NULL		 PRIMARY KEY,
		PETTYPE                       		INTEGER(10)		 NOT NULL,
		PETKIND                       		INTEGER(10)		 NULL ,
		MASTERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (MASTERNO) REFERENCES Master (MASTERNO)
);

COMMENT ON TABLE TABLE_19 is '반려동물 분류';
COMMENT ON COLUMN TABLE_19.PETTYPENO is '분류 번호';
COMMENT ON COLUMN TABLE_19.PETTYPE is '반려동물 종류';
COMMENT ON COLUMN TABLE_19.PETKIND is '반려동물 품종';
COMMENT ON COLUMN TABLE_19.MASTERNO is '관리자번호';


/**********************************/
/* Table Name: 반려동물 */
/**********************************/
CREATE TABLE Pet(
		PETNUM                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		PETNAME                       		VARCHAR2(20)		 NOT NULL,
		PETAGE                        		VARCHAR2(10)		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		PETTYPENO                     		INTEGER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (PETTYPENO) REFERENCES TABLE_19 (PETTYPENO)
);

COMMENT ON TABLE Pet is '반려동물';
COMMENT ON COLUMN Pet.PETNUM is '반려동물번호';
COMMENT ON COLUMN Pet.PETNAME is '반려동물이름';
COMMENT ON COLUMN Pet.PETAGE is '반려동물나이';
COMMENT ON COLUMN Pet.MEMBERNO is '회원번호';
COMMENT ON COLUMN Pet.PETTYPENO is '분류 번호';


/**********************************/
/* Table Name: 문화시설 정보 첨부파일 */
/**********************************/
CREATE TABLE Attachment(
		ANAME                         		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		FILENAME                      		VARCHAR2(50)		 NOT NULL,
		FILESIZE                      		VARCHAR2(50)		 NOT NULL,
		THUMBFILE                     		VARCHAR2(50)		 NOT NULL,
		CULTUREFNO                    		NUMBER(10)		 NULL ,
  FOREIGN KEY (CULTUREFNO) REFERENCES Culture F (CULTUREFNO)
);

COMMENT ON TABLE Attachment is '문화시설 정보 첨부파일';
COMMENT ON COLUMN Attachment.ANAME is '첨부파일 번호';
COMMENT ON COLUMN Attachment.FILENAME is '파일명';
COMMENT ON COLUMN Attachment.FILESIZE is '파일사이즈';
COMMENT ON COLUMN Attachment.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN Attachment.CULTUREFNO is '문화시설번호';


/**********************************/
/* Table Name: 일반 커뮤니티 첨부파일 */
/**********************************/
CREATE TABLE Attachment(
		ANAME                         		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		FILENAME                      		VARCHAR2(50)		 NOT NULL,
		FILESIZE                      		VARCHAR2(50)		 NOT NULL,
		THUMBFILE                     		VARCHAR2(50)		 NOT NULL,
		WRITENO                       		NUMBER(10)		 NULL ,
  FOREIGN KEY (WRITENO) REFERENCES Normal Com (WRITENO)
);

COMMENT ON TABLE Attachment is '일반 커뮤니티 첨부파일';
COMMENT ON COLUMN Attachment.ANAME is '첨부파일 번호';
COMMENT ON COLUMN Attachment.FILENAME is '파일명';
COMMENT ON COLUMN Attachment.FILESIZE is '파일사이즈';
COMMENT ON COLUMN Attachment.THUMBFILE is '썸네일 파일';
COMMENT ON COLUMN Attachment.WRITENO is '글 번호';


/**********************************/
/* Table Name: 산책 메이트 커뮤니티 */
/**********************************/
CREATE TABLE Walk mate C(
		WNUM                          		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		TITLE                         		VARCHAR2(30)		 NOT NULL,
		CONTENT                       		VARCHAR2(30)		 NOT NULL,
		CNT                           		NUMBER(10)		 NOT NULL,
		STARTINGP                     		VARCHAR2(30)		 NULL ,
		WALKINGM                      		NUMBER(10)		 NOT NULL,
		DATE                          		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NULL ,
		PETNUM                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (PETNUM) REFERENCES Pet (PETNUM)
);

COMMENT ON TABLE Walk mate C is '산책 메이트 커뮤니티';
COMMENT ON COLUMN Walk mate C.WNUM is '글번호';
COMMENT ON COLUMN Walk mate C.TITLE is '제목';
COMMENT ON COLUMN Walk mate C.CONTENT is '내용';
COMMENT ON COLUMN Walk mate C.CNT is '조회수';
COMMENT ON COLUMN Walk mate C.STARTINGP is '출발지점';
COMMENT ON COLUMN Walk mate C.WALKINGM is '구하는 산책 인원';
COMMENT ON COLUMN Walk mate C.DATE is '작성일자';
COMMENT ON COLUMN Walk mate C.MEMBERNO is '회원번호';
COMMENT ON COLUMN Walk mate C.PETNUM is '반려동물번호';


/**********************************/
/* Table Name: 산책 메이트 신청 */
/**********************************/
CREATE TABLE Walk mate A (
		COLUMN_5                      		INTEGER(10)		 NULL 		 PRIMARY KEY,
		ASTATUS                       		VARCHAR2(15)		 NOT NULL,
		ADATE                         		DATE		 NOT NULL,
		MEMBERNO                      		NUMBER(10)		 NOT NULL,
		WNUM                          		NUMBER(10)		 NULL ,
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO),
  FOREIGN KEY (WNUM) REFERENCES Walk mate C (WNUM)
);

COMMENT ON TABLE Walk mate A  is '산책 메이트 신청';
COMMENT ON COLUMN Walk mate A .COLUMN_5 is '신청 번호';
COMMENT ON COLUMN Walk mate A .ASTATUS is '신청상태';
COMMENT ON COLUMN Walk mate A .ADATE is '신청일자';
COMMENT ON COLUMN Walk mate A .MEMBERNO is '회원번호';
COMMENT ON COLUMN Walk mate A .WNUM is '글번호';


/**********************************/
/* Table Name: 산책 메이트 후기 */
/**********************************/
CREATE TABLE Walk mate review (
		REVIEWNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		REVIEWCOMMENT                 		VARCHAR2(10)		 NOT NULL,
		WNUM                          		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (WNUM) REFERENCES Walk mate C (WNUM),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE Walk mate review  is '산책 메이트 후기';
COMMENT ON COLUMN Walk mate review .REVIEWNO is '후기 번호';
COMMENT ON COLUMN Walk mate review .REVIEWCOMMENT is '후기 내용';
COMMENT ON COLUMN Walk mate review .WNUM is '글번호';
COMMENT ON COLUMN Walk mate review .MEMBERNO is '회원번호';


/**********************************/
/* Table Name: 문화시설 후기 */
/**********************************/
CREATE TABLE Culture F review(
		REVIEWNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		REVIEWCOMMENT                 		VARCHAR2(50)		 NOT NULL,
		CULTUREFNO                    		NUMBER(10)		 NULL ,
		MEMBERNO                      		NUMBER(10)		 NULL ,
  FOREIGN KEY (CULTUREFNO) REFERENCES Culture F (CULTUREFNO),
  FOREIGN KEY (MEMBERNO) REFERENCES member (MEMBERNO)
);

COMMENT ON TABLE Culture F review is '문화시설 후기';
COMMENT ON COLUMN Culture F review.REVIEWNO is '후기 번호';
COMMENT ON COLUMN Culture F review.REVIEWCOMMENT is '후기 내용';
COMMENT ON COLUMN Culture F review.CULTUREFNO is '문화시설번호';
COMMENT ON COLUMN Culture F review.MEMBERNO is '회원번호';


