package dev.mvc.culturefile;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.culturefile.CulturefileProc")
public class CulturefileProc implements CulturefileProcInter {
  
  @Autowired
  private CulturefileDAOInter culturefileDAO;

  @Override
  public int create(CulturefileVO culturefileVO) {
    int cnt =this.culturefileDAO.create(culturefileVO);
    return cnt;
  }

  @Override
  public int update_file(CulturefileVO culturefileVO) {
    int cnt = this.culturefileDAO.update_file(culturefileVO);
    return cnt;
  }
  
  @Override
  public ArrayList<CulturefileVO> read(int culturefno) {

    ArrayList<CulturefileVO> list =this.culturefileDAO.read(culturefno);
    return list;
  }
  
  @Override
  public int delete(int fano) {
    int cnt =this.culturefileDAO.delete(fano);
    return cnt;
  }

  @Override
  public CulturefileVO readByFano(int fano) {
    CulturefileVO culturefileVO = this.culturefileDAO.readByFano(fano);
    return culturefileVO ;
  }



  


  
  


}