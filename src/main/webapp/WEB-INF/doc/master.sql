CREATE TABLE MASTER(
		MASTERNO                      		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		MASTERID                      		VARCHAR2(30)		 NOT NULL,
		MASTERPASSWD                  		VARCHAR2(50)		 NOT NULL
);

COMMENT ON TABLE MASTER is '관리자';
COMMENT ON COLUMN MASTER.MASTERNO is '관리자번호';
COMMENT ON COLUMN MASTER.MASTERID is '관리자 아이디';
COMMENT ON COLUMN MASTER.MASTERPASSWD is '관리자 비밀번호';

DROP SEQUENCE master_seq;

CREATE SEQUENCE master_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;                     -- 다시 1부터 생성되는 것을 방지

--등록--
INSERT INTO master(masterno, masterid, masterpasswd)                            
VALUES (member_seq.nextval, 'admin', '1234')

--목록--
SELECT masterno, masterid, masterpasswd
FROM master
ORDER BY masterid ASC;
     
             