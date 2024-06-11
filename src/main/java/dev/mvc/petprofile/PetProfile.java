package dev.mvc.petprofile;

import java.io.File;

public class PetProfile {

    public static synchronized String getUploadDir() {
        String path = "";

        String osName = System.getProperty("os.name").toLowerCase();

//        if (File.separator.equals("\\")) {
//            path="C:\\kd\\deploy\\team6_v2sbm3c\\member\\storage\\";
//        } else {
//            path = "/home/ubuntu/deploy/team6_v2sbm3c/member/storage/";
//        }

        if(osName.contains("win")){
            path="C:\\kd\\deploy\\team6_v2sbm3c\\petprofile\\storage\\";
        }else if(osName.contains("mac")){
            path="/users/seongmin/desktop/kd/deploy/team6_v2sbm3c/petprofile/storage/";
        }else{
            path="/home/ubuntu/deploy/team6_v2sbm3c/pet/storage/";
        }

        return path;
    }
}
