package g11.dao;

import g11.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDAO {
    int delete(String[] ids);

    int insert(User record);

    int insertSelective(User record);

    User select(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePassword(User user);

    int updateAccess(User user);

    int count();

    List<User> list(@Param("skip") int skip, @Param("rows") int rows);
}