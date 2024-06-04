package dev.mvc.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.pet.PetProc")
public class PetProc implements PetProcInter{

    @Autowired
    private PetDAOInter petDAO;

    @Override
    public int create(PetVO petVO){
        return this.petDAO.create(petVO);
    }
}
