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
<<<<<<< HEAD
    ArrayList<CulturefileVO> list =this.culturefileDAO.read(culturefno);
    return list;
=======
    ArrayList<CulturefileVO> list = this.culturefileDAO.read(culturefno);
    return list ;
>>>>>>> 192858b06898921b3df4ebb5f693b65986afd04a
  }
  

  

  @Override
  public int delete(int fano) {
    int cnt =this.culturefileDAO.delete(fano);
    return cnt;
  }


  
  


}