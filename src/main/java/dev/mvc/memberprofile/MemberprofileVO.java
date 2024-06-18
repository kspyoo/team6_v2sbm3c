package dev.mvc.memberprofile;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberprofileVO {
  
  /** 메인 이미지 크기 단위, 파일 크기 */
  private int mprofileno = 0;
  /**이미지 파일*/
  private MultipartFile file1MF = null;
  /** 메인 이미지 크기 단위, 파일 크기 */
  private String size1_label = "";
  /** 메인 이미지 */
  private String file1 = ""; ////////////
  /** 실제 저장된 메인 이미지 */
  private String file1saved = ""; ////////////
  /** 메인 이미지 preview */
  private String thumbfile = ""; ////////////
  /** 메인 이미지 크기 */
  private long filesize = 0; ////////////
  /** FK*/
  private int memberno = 0;
  /** 원본 파일명 */
  private String fname;
  /** 업로드된 파일명 */
  private String fupname;
  /**list 저장*/
  private List<MultipartFile> fnamesMF;
}