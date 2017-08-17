package g11.service.impl;

import g11.commons.exception.DataViolationException;
import g11.dao.UserDAO;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.model.User;
import g11.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * 用户操作逻辑
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User login(User user) {
        String id = user.getId();
        if (id == null || id.trim().equals("")) {
            throw new DataViolationException(601, "用户名为空");
        }
        User result = userDAO.select(id);
        if (result == null) {
            throw new DataViolationException(602, "用户不存在");
        } else if (!result.getPassword().equals(user.getPassword())) {
            throw new DataViolationException(603, "密码错误");
        } else {
            return result;
        }
    }

    @Override
    public void save(User user) {

        userDAO.insertSelective(user);

    }

    @Override
    public void update(User user) {
        userDAO.updateByPrimaryKeySelective(user);

    }

    @Override
    public int delete(String ids) {
        if (ids == null || ids.trim().equals("")) {
            throw new DataViolationException(604, "没有选择待删除的用户");
        }
        String[] idArray = ids.split(",");
        return userDAO.delete(idArray);
    }

    @Override
    public void updatePassword(User user) {
        String newPassword = user.getPassword();
        if (newPassword == null || newPassword.trim().equals("")) {
            throw new DataViolationException(605, "密码不能为空");
        } else {
            userDAO.updatePassword(user);
        }


    }

    @Override
    public void updateAccess(User user) {
        userDAO.updateAccess(user);

    }

    @Override
    public ResultModel<User> list(RequestList rl) {
        int total = userDAO.count();
        ResultModel<User> resultModel = new ResultModel<>(total, rl.getRows(), rl.getPage());
        List<User> list = userDAO.list((resultModel.getCurrentPage() - 1) * resultModel.getRows(), resultModel.getRows());
        resultModel.setData(list);
        return resultModel;
    }
}
