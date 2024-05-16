
/**********************************/
/* Table Name: 관리자 */
/**********************************/
CREATE TABLE MASTER(
    MASTERNO                          NUMBER(10)     NOT NULL    PRIMARY KEY,
    MASTERTYPE                        VARCHAR(30)   NOT NULL ,
    MASTERID                          VARCHAR2(30)     NOT NULL,
    MASTERPASSWD                      VARCHAR2(50)     NOT NULL
);

COMMENT ON TABLE MASTER is '관리자';
COMMENT ON COLUMN MASTER.MASTERNO is '관리자번호';
COMMENT ON COLUMN MASTER.MASTERTYPE is '관리자분류';
COMMENT ON COLUMN MASTER.MASTERID is '관리자 아이디';
COMMENT ON COLUMN MASTER.MASTERPASSWD is '관리자 비밀번호';


