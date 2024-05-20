package dev.mvc.culturefacility;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.culturefacility.CulturefacilityProc")
public class CulturefacilityProc implements CulturefacilityProcInter  {
  
  @Autowired
  private CulturefacilityDAOInter culturefacilityDAO;
  
  public CulturefacilityProc() {
    System.out.println("-> CulturefacilityProc");
  }

  @Override
  public CulturefacilityVO read(int cultureno) {
    CulturefacilityVO culturefacilityVO =this.culturefacilityDAO.read(cultureno);
    
    return culturefacilityVO;
  }

  @Override
  public int create(CulturefacilityVO culturefacilityVO) {
    int cnt=this.culturefacilityDAO.create(culturefacilityVO);
    return cnt;
  }

  @Override
  public ArrayList<CulturefacilityVO> list_all() {
    ArrayList<CulturefacilityVO> list=this.culturefacilityDAO.list_all();
    return list;
  }

  @Override
  public int update(CulturefacilityVO culturefacilityVO) {
    int cnt =this.culturefacilityDAO.update(culturefacilityVO);
    return cnt;
  }

  @Override
  public int delete(int cultureno) {
   int cnt =this.culturefacilityDAO.delete(cultureno);
    return cnt;
  }

}
