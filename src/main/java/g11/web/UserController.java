package g11.web;

import g11.dto.AjaxResult;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.model.User;
import g11.service.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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

    @PostMapping("/save")
    public AjaxResult save(@RequestBody User user) {
        try {
            userService.save(user);
        } catch (DuplicateKeyException e) {
            return AjaxResult.fail(610, "用户名[" + user.getId() + "]已经存在");
        }
        return AjaxResult.success("");
    }

    @PostMapping("/update")
    public AjaxResult update(@RequestBody User user) {
        userService.update(user);
        return AjaxResult.success("");
    }

    @PostMapping("/delete")
    public AjaxResult delete(@RequestBody User user) {
        int num = userService.delete(user.getId());
        return num > 0 ?  AjaxResult.success("成功删除" + num + "条用户数据") : AjaxResult.fail(611, "没有数据被删除");
    }


    @PostMapping("/updatePassword")
    public AjaxResult updatePassword(@RequestBody User user) {
        userService.updatePassword(user);
        return AjaxResult.success("");
    }


    @PostMapping("/updateAccess")
    public AjaxResult updateAccess(@RequestBody User user) {
        userService.updateAccess(user);
        return AjaxResult.success("");
    }

    @PostMapping("/list")
    public AjaxResult list(@RequestBody RequestList rl) {
        ResultModel<User> resultModel = userService.list(rl);
        return AjaxResult.success(resultModel);
    }


    @GetMapping("/logout")
    public AjaxResult logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return AjaxResult.success("");
    }
}
