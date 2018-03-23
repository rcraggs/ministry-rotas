package rcraggs.rota.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rcraggs.rota.forms.UserForm;
import rcraggs.rota.model.User;
import rcraggs.rota.repository.UserRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UsersController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository repository;

    @RequestMapping("")
    public ModelAndView users(){
        ModelAndView m = new ModelAndView("users");
        List<User> users = repository.findAll();
        m.addObject("users", users);
        return m;
    }

    @RequestMapping(value = "/add")
    public ModelAndView addUserForm(@ModelAttribute("user") UserForm user) {
        return new ModelAndView("adduser");
    }

    @RequestMapping("submit")
    public ModelAndView createUser(@Valid @ModelAttribute("user") UserForm user, BindingResult result) {

        ModelAndView m = new ModelAndView();
        if (result.hasErrors()){
            m.setViewName("adduser");
            m.addObject("user", user);
            return m;
        }

        User newAdmin = new User();
        newAdmin.setEmail(user.getEmail());
        newAdmin.setForename(user.getForename());
        newAdmin.setSurname(user.getSurname());
        newAdmin.setPassword(passwordEncoder.encode(user.getPassword()));
        newAdmin.setRole(User.UserRole.ADMIN);
        newAdmin.setUsername(user.getUsername());
        repository.save(newAdmin);
        m.setViewName("redirect:/users");
        return m;
    }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editUser(@ModelAttribute("user") UserForm user, @PathVariable("id") long id, RedirectAttributes attributes) {

        ModelAndView m = new ModelAndView();
        Optional<User> u = repository.findById(id);
        if (!u.isPresent()){
            attributes.addFlashAttribute("message", "No user with that ID was found");
            attributes.addFlashAttribute("messagetype", "warning");
            m.setViewName("redirect:/users");
            return m;
        }

        return m;
    }

}
