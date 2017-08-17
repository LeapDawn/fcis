package g11.service;

import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.model.User;

import java.util.List;

public interface UserService {

    /**
     * 登录业务
     * @param user 待登录的用户帐号/密码
     * @return 登录成功的用户信息
     */
    User login(User user);

    /**
     * 新增用户信息
     * @param user 待新增的用户信息
     */
    void save(User user);

    /**
     * 更新用户信息
     * @param user 待更新用户
     */
    void update(User user);

    /**
     * 删除用户
     * @param ids 待删除用户帐号,多个以`,`隔开
     * @return 成功删除用户记录数
     */
    int delete(String ids);

    /**
     * 修改用户密码,只能修改自己的
     * @param user
     */
    void updatePassword(User user);

    /**
     * 修改用户权限
     * @param user
     */
    void updateAccess(User user);

    /**
     * 查询用户信息列表
     * @param rl
     * @return
     */
    ResultModel<User> list(RequestList rl);
}
