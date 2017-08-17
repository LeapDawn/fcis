package g11.web;

import g11.dto.AjaxResult;
import g11.model.User;
import g11.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户Controller
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    @Setter
    UserService userService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody User user, HttpSession httpSession) {
        User login_User = null;
        login_User = userService.login(user);
        httpSession.setAttribute("user", login_User);
        return AjaxResult.success("");
    }
}
