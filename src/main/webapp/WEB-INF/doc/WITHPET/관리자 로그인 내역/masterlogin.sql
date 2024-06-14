/**********************************/
/* Table Name: 관리자 로그인 내역 */
/**********************************/
DROP TABLE MASTERLOGIN

CREATE TABLE MASTERLOGIN(
		MASTERNO                      		NUMBER(10)		 NOT NULL,
		IP                            		VARCHAR2(20)		 NOT NULL,
		CONNDATE                      		DATE		 NOT NULL,
		MASTERLOGINNO                 		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
  FOREIGN KEY (MASTERNO) REFERENCES MASTER (MASTERNO)
);

COMMENT ON TABLE MASTERLOGIN is '관리자 로그인 내역';
COMMENT ON COLUMN MASTERLOGIN.MASTERNO is '관리자번호';
COMMENT ON COLUMN MASTERLOGIN.IP is '접속 아이피';
COMMENT ON COLUMN MASTERLOGIN.CONNDATE is '접속일자';
COMMENT ON COLUMN MASTERLOGIN.MASTERLOGINNO is '로그인번호';

DROP SEQUENCE masterlogin_seq;

CREATE SEQUENCE masterlogin_seq
  START WITH 1              -- 시작 번호
  INCREMENT BY 1          -- 증가값
  MAXVALUE 9999999999 -- 최대값: 9999999 --> NUMBER(7) 대응
  CACHE 2                       -- 2번은 메모리에서만 계산
  NOCYCLE;       

INSERT INTO masterlogin(masterloginno,ip,conndate,masterno)
VALUES (masterlogin_seq.nextval,'1',sysdate,  7);

  <!-- 로그인 내역 조회 -->
SELECT lg.masterloginno, lg.ip, 
    lg.conndate, lg.masterno, mb.mastername, mb.masterid
FROM masterlogin lg
LEFT JOIN master mb ON lg.masterno = mb.masterno
WHERE lg.masterno != 0
  AND (
        UPPER(lg.ip) LIKE '%' || UPPER() || '%' 
        OR UPPER(lg.conndate) LIKE '%' || UPPER() || '%' 
        OR UPPER(mb.mastername) LIKE '%' || UPPER() || '%' 
        OR UPPER(mb.masterid) LIKE '%' || UPPER() || '%'
    )
ORDER BY lg.masterloginno DESC;








SELECT masterloginno,ip,conndate,masterno
FROM masterlogin
WHERE masterloginno
