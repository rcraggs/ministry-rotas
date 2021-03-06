package rcraggs.rota.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "pages/login";
    }

    @GetMapping("/logout")
    public String logout() {
        // TODO: do some log out stuff in the controller
        return "pages/login";
    }

    @GetMapping("/forbidden")
    public String forbidden() {
        return "pages/forbidden";
    }
}