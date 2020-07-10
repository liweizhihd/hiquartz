package weizhi.example.hiquartz.service;

import weizhi.example.hiquartz.po.PetPO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: liweizhi
 * @Date: 2019/5/24 17:04
 * @Description:
 */
public interface IPetService {

    List<PetPO> getAllPet();

    List<PetPO> getAllPetByCond(Map<String, Object> cond);

    void addPet(PetPO pet);

    int updatePet(PetPO pet);

    int deletePet(int id);
}
