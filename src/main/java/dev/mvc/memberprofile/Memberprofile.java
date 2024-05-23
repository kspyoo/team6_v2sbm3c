package dev.mvc.memberprofile;

import java.io.File;

public class Memberprofile {
    /** 페이지당 출력할 레코드 갯수 */
    public static int RECORD_PER_PAGE = 8;

    /** 블럭당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨 */
    public static int PAGE_PER_BLOCK = 10;

    public static synchronized String getUploadDir() {
        String path = "";
        if (File.separator.equals("\\")) { 
            path="C:\\kd\\deploy\\team6_v2sbm3c\\member\\storage\\";
            
            
        } else { 
            path = "/home/ubuntu/deploy/team6_v2sbm3c/member/storage/";
        }
        
        return path;
    }
    
}

