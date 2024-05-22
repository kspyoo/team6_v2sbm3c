package dev.mvc.culturefile;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//
//
//FANO                              NUMBER(10)     NOT NULL    PRIMARY KEY,
//SIZE1                             VARCHAR2(100)    NULL ,
//FILE1                             INTEGER   NULL ,
//FILE1SAVED                        VARCHAR2(100)    NOT NULL,
//THUMBFILE                         VARCHAR2(100)    NOT NULL,
//CULTUREFNO                        NUMBER(10)     NULL ,
//FOREIGN KEY (CULTUREFNO) REFERENCES CULTUREFACILITY (CULTUREFNO)


@Getter @Setter @ToString
public class CulturefileVO {
  /** 문화시설 번호 */
  private int culturefno;
  
  /**
  이미지 파일
  <input type='file' class="form-control" name='file1MF' id='file1MF' 
             value='' placeholder="파일 선택">
  */
  private MultipartFile file1MF = null;
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";
  /** 메인 이미지 크기 */
  private long size1 = 0;
  /** 메인 이미지 */
  private String file1 = "";
  /** 실제 저장된 메인 이미지 */
  private String file1saved = "";
  /** 메인 이미지 preview */
  private String thumbfile = "";


}
