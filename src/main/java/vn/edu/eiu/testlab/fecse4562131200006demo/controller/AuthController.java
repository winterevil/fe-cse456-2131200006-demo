package vn.edu.eiu.testlab.fecse4562131200006demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.eiu.testlab.fecse4562131200006demo.model.User;
import vn.edu.eiu.testlab.fecse4562131200006demo.service.UserService;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    //Hiển thị trang login
    @GetMapping({"/", "/login"})
    public String showLoginPage() {
        return "login"; //login.html
    }

    @PostMapping("/auth")
    public String doLogin(@ModelAttribute User user, HttpSession ses, RedirectAttributes RedAttr) {
        User loginUser = userService.findByUsername(user.getUsername());
        //So username với db nếu ko có thì quay lại login
        if (loginUser == null) {
            RedAttr.addFlashAttribute("errLogin", "Username or password incorrect");
            RedAttr.addFlashAttribute("username", user.getUsername());
            return "redirect:/login";
            //kèm lỗi sai username
        }
        if (!loginUser.getPassword().equals(user.getPassword())) {
            RedAttr.addFlashAttribute("errLogin", "Username or password incorrect");
            RedAttr.addFlashAttribute("username", user.getUsername());
            return "redirect:/login";
        }
        //Xuống được đây là đúng username
        //Lưu user vào session để xử lý phân quyền
        //session dùng chung cho toàn bộ app
        //Nếu login thành công thì hiển thị trang students
        ses.setAttribute("user", loginUser);
        return "redirect:/students";
    }
    //Xử lý khi click vào link/ nut logout
    @GetMapping("/logout")
    public String doLogout(HttpSession ses) {
        //ses.setAttribute("user", null);
        //ses.removeAttribute("user");
        ses.invalidate();
        return "redirect:/login";
    }
}
