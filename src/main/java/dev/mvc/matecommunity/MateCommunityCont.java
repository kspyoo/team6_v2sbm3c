package dev.mvc.matecommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class MateCommunityCont {

    @Autowired
    @Qualifier("dev.mvc.MateCommunityProc")
    private MateCommunityProcInter mateCommunityProc;
}
