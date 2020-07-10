package weizhi.example.hiquartz.dao.pocdb;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import weizhi.example.hiquartz.po.PetPO;

import java.util.List;
import java.util.Map;

/**
 * @Auther: liweizhi
 * @Date: 2019/5/24 17:03
 * @Description:
 */
@Mapper
@Repository
public interface PetMapper {
    @Select("SELECT id,color,name,age FROM pet")
    List<PetPO> getAll();

    @Select("SELECT id,name,age,color FROM pet WHERE name like CONCAT('%',#{name},'%') and age = #{age}")
    List<PetPO> getAllByCond(Map<String, Object> cond);

    @Insert("insert into pet (name,age,color) values (#{name},#{age},#{color})")
    void addPet(PetPO pet);

    @Update("update pet set name = #{name}, age = #{age} where id = #{id}")
    int updatePet(PetPO pet);

    @Delete("delete from pet where id = #{id}")
    int deletePet(int id);
}
