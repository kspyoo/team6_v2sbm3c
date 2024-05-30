package dev.mvc.matereview;

public interface MateReviewDAOInter {

    /**
     * 후기 작성
     * @param mateReviewVO
     * @return
     */
    public int create(MateReviewVO mateReviewVO);
}