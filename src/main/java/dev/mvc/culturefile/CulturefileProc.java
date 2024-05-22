package dev.mvc.culturefile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.culturefile.CulturefileProc")
public abstract class CulturefileProc implements CulturefileProcInter {
  
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
  public CulturefileVO read(int culturefno) {
    CulturefileVO culturefileVO = this.culturefileDAO.read(culturefno);
    return culturefileVO ;
  }
  
  
  


}
