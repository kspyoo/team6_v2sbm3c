package dev.mvc.master;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.master.MasterProc")
public class MasterProc implements MasterProcInter{
  @Autowired
  private MasterDAOInter masterDAO;

  @Override
  public int checkID(String id) {
    int cnt=this.checkID(id);
    return cnt;
  }

  @Override
  public int create(MasterVO masterVO) {
    int cnt= this.create(masterVO);
    return cnt;
  }

  @Override
  public ArrayList<MasterVO> list() {
    ArrayList<MasterVO> list =this.masterDAO.list();
    return list;
  }

  @Override
  public MasterVO read(int masterno) {
    MasterVO masterVO= this.read(masterno);
    return masterVO;
  }

  @Override
  public int update(MasterVO masterVO) {
    int cnt = this.update(masterVO);
    return cnt;
  }

  @Override
  public int delete(int masterno) {
    int cnt= this.delete(masterno);
    return cnt;
  }

  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.passwd_update(map);
    return cnt;
  }

  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.login(map);
    return cnt;
  }

}
