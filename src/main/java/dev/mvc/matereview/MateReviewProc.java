package dev.mvc.matereview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.matereview.MateReviewProc")
public class MateReviewProc implements MateReviewProcInter{

    @Autowired
    private MateReviewDAOInter mateReviewDAOInter;

    @Override
    public int create(MateReviewVO mateReviewVO) {
        return mateReviewDAOInter.create(mateReviewVO);
    }

}