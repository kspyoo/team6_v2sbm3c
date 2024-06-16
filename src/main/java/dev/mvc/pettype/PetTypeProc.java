package dev.mvc.pettype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service("dev.mvc.pettype.PetTypeProc")
public class PetTypeProc implements PetTypeProcInter{

    @Autowired
    private PetTypeDAOInter petTypeDAO;

    @Override
    public int create(PetTypeVO petTypeVO) {
        return this.petTypeDAO.create(petTypeVO);
    }

    @Override
    public ArrayList<PetTypeVO> list() {
        return this.petTypeDAO.list();
    }

    @Override
    public int update(String petType, int petTypeNo) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("petType",petType);
        map.put("petTypeNo",petTypeNo);

        return this.petTypeDAO.update(map);
    }

    @Override
    public int delete(int petTypeNo) {
        return this.petTypeDAO.delete(petTypeNo);
    }
}
