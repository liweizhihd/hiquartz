package weizhi.example.hiquartz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import weizhi.example.hiquartz.dao.pocdb.PetMapper;
import weizhi.example.hiquartz.po.PetPO;
import weizhi.example.hiquartz.service.IPetService;

import java.util.List;
import java.util.Map;

/**
 * @Auther: liweizhi
 * @Date: 2019/5/24 17:04
 * @Description:
 */
@Service
public class PetServiceImpl implements IPetService {
    @Autowired
    private PetMapper petMapper;

    @Override
    public List<PetPO> getAllPet() {
        return petMapper.getAll();
    }

    @Override
    public List<PetPO> getAllPetByCond(Map<String, Object> cond) {
        return petMapper.getAllByCond(cond);
    }

    @Override
    public void addPet(PetPO pet) {
        petMapper.addPet(pet);
    }

    @Override
    public int updatePet(PetPO pet) {
        return petMapper.updatePet(pet);
    }

    @Override
    public int deletePet(int id) {
        return petMapper.deletePet(id);
    }


}
