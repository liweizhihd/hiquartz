package weizhi.example.hiquartz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import weizhi.example.hiquartz.po.PetPO;
import weizhi.example.hiquartz.service.IPetService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liweizhi
 * @Date: 2019/4/28 11:28
 * @Description:
 */
@RestController
@RequestMapping("pet")
@Slf4j
public class PetController {
    @Autowired
    private IPetService petService;

    @GetMapping("getAll")
    public List<PetPO> getAll001() {
        List<PetPO> ret = petService.getAllPet();
        log.info("getAll ret : {}", ret);
        return ret;
    }

    @GetMapping("get/{name}/{age}")
    public List<PetPO> getByNameAge(@PathVariable("name") String name, @PathVariable("age") int age) {
        Map<String, Object> cond = new HashMap<>(16);
        cond.put("name", name);
        cond.put("age", age);
        List<PetPO> ret = petService.getAllPetByCond(cond);
        log.info("getByNameAge cond:{} \n ret : {}", cond, ret);
        return ret;
    }

    @GetMapping("add/{name}/{age}/{color}")
    public int add(@PathVariable("name") String name, @PathVariable("age") int age, @PathVariable("color") String color) {
        try {
            PetPO pet = new PetPO(name, age, color);
            petService.addPet(pet);
            log.info("add pet:{}", pet);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @GetMapping("update/{id}/{name}/{age}")
    public int update(@PathVariable("id") int id, @PathVariable("name") String name, @PathVariable("age") int age) {
        PetPO pet = new PetPO(id, name, age);
        int i = petService.updatePet(pet);
        log.info("update ret :{},id:{}", i, id);
        return i;
    }

    @GetMapping("delete/{id}")
    public int delete(@PathVariable("id") int id) {
        int i = petService.deletePet(id);
        log.info("delete ret :{},id:{}", i, id);
        return i;
    }

}
