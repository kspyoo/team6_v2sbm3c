package dev.mvc.petprofile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.petprofile.PetProfileProc")
public class PetProfileProc implements PetProfileProcInter{

    @Autowired
    private PetProfileDAOInter petProfileDAOInter;

    @Override
    public int create(PetProfileVO petProfileVO) {
        return petProfileDAOInter.create(petProfileVO);
    }
}
