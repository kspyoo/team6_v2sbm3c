package dev.mvc.matecommunity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service("dev.mvc.MateCommunityProc")
public class MateCommunityProc implements MateCommunityProcInter {

    @Autowired
    private MateCommunityDAOInter mateCommunityDAO;

    @Override
    public int create(MateCommunityVO mateCommunityVO) {
        return mateCommunityDAO.create(mateCommunityVO);
    }

    @Override
    public ArrayList<MateCommunityVO> list_all(Map<String, Object> map) {
        return mateCommunityDAO.list_all(map);
    }

    @Override
    public ArrayList<MateCommunityVO> list_all_by_petTypeNo(Map<String, Object> map) {
        return mateCommunityDAO.list_all_by_petTypeNo(map);
    }

    @Override
    public MateCommunityVO read_content(int mCommunityNo) {
        return mateCommunityDAO.read_content(mCommunityNo);
    }

    @Override
    public int update_content(Map<String, Object> map) {
        return mateCommunityDAO.update_content(map);
    }

    @Override
    public int viewCnt_up(int mCommunityNo) {
        return mateCommunityDAO.viewCnt_up(mCommunityNo);
    }

    @Override
    public int recruit_finish(int mCommunityNo) {
        return mateCommunityDAO.recruit_finish(mCommunityNo);
    }

    @Override
    public int recruit_start(int mCommunityNo) {
        return mateCommunityDAO.recruit_start(mCommunityNo);
    }

    @Override
    public int delete_content(int mCommunityNo) {
        return mateCommunityDAO.delete_content(mCommunityNo);
    }
}
