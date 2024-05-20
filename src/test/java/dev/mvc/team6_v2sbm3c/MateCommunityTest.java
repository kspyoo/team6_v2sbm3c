package dev.mvc.team6_v2sbm3c;

import dev.mvc.matecommunity.MateCommunityProcInter;
import dev.mvc.matecommunity.MateCommunityVO;
import dev.mvc.member.MemberProcInter;
import dev.mvc.member.MemberVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MateCommunityTest {
    @Autowired
    @Qualifier("dev.mvc.MateCommunityProc")
    private MateCommunityProcInter mateCommunityProc;

    @Autowired
    @Qualifier("dev.mvc.member.MemberProc")
    private MemberProcInter memberProc;

    @Test
    public void create_community(){
        //#{addr_code}, #{addr_main},#{addr_detail}, sysdate, #{status}
        MemberVO memberVO = memberProc.read(1);
        MateCommunityVO mateCommunityVO = new MateCommunityVO();

        mateCommunityVO.setContent("테스트");
        mateCommunityVO.setTitle("작성테스트");
        mateCommunityVO.setSearchTag("종로, 산책, 강아지");
        mateCommunityVO.setStartingP("종로");
        mateCommunityVO.setWalkingM(3);
        mateCommunityVO.setPetTypeNo(1);
        mateCommunityVO.setMemberNo(1);

        int result = mateCommunityProc.create(mateCommunityVO);

        System.out.println(result);
    }

    @Test
    public void read_community(){
        //#{addr_code}, #{addr_main},#{addr_detail}, sysdate, #{status}
        MemberVO memberVO = memberProc.read(1);

        MateCommunityVO result = mateCommunityProc.read_content(2);

        System.out.println(result.getTitle());
    }
}
