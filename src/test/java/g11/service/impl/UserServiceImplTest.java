package g11.service.impl;

import g11.Application;
import g11.commons.util.BackupCommand;
import g11.model.User;
import g11.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by cody on 2017/8/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    public void login() throws Exception {
        User user = new User();
        user.setId("zhangsan");
        user.setPassword("zhangsan");
        User result = userService.login(user);
        System.out.println(result);
    }

    @Test
    public void save() throws Exception {
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void updatePassword() throws Exception {
    }

    @Test
    public void updateAccess() throws Exception {
    }

    @Test
    public void list() throws Exception {
    }

}