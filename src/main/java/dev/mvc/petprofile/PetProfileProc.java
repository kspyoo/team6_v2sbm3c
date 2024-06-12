package dev.mvc.petprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

@Service("dev.mvc.petprofile.PetProfileProc")
public class PetProfileProc implements PetProfileProcInter{

    @Autowired
    private PetProfileDAOInter petProfileDAOInter;

    @Override
    public int create(PetProfileVO petProfileVO) {
        return petProfileDAOInter.create(petProfileVO);
    }

    @Override
    public PetProfileVO read_one() {
        return petProfileDAOInter.read_one();
    }

    @Override
    public ArrayList<PetProfileVO> list(int petNo) {
        return petProfileDAOInter.list(petNo);
    }

    @Override
    public int delete(int petProfileNo) {
        return this.petProfileDAOInter.delete(petProfileNo);
    }

    @Override
    public int delete_all(int petNo) {
        return this.petProfileDAOInter.delete_all(petNo);
    }

    public int profile_cnt(int petNo){
        return this.petProfileDAOInter.profile_cnt(petNo);
    }

    @Override
    public String randomFileName() {
        String randowNum = "0123456789";
        LocalDate now_date = LocalDate.now();
        String year = String.valueOf(now_date).split("-")[0];
        String month = String.valueOf(now_date).split("-")[1];
        String day = String.valueOf(now_date).split("-")[2];

        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        int minute = now.getMinute();
        int second = now.getSecond();

        StringBuffer str = new StringBuffer();
        int num = 0;
        for (int i = 0; i<5 ; i++){
            num = new Random().nextInt(randowNum.length());
            str.append(num);
        }

        String randomName = year + month + day +hour + minute + second + "_" + str;

        return randomName;
    }
}
