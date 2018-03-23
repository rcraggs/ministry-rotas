package rcraggs.rota.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rcraggs.rota.forms.UserForm;
import rcraggs.rota.model.User;
import rcraggs.rota.repository.UserRepository;
import rcraggs.rota.validation.UserValidator;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public class UsersController {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserValidator userValidator;

    @Autowired
    public UsersController(PasswordEncoder passwordEncoder, UserRepository repository, UserValidator userValidator) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.userValidator = userValidator;
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }

    @ModelAttribute("allRoles")
    public List<User.UserRole> populateTypes() {
        return Arrays.asList(User.UserRole.ALL);
    }

    @RequestMapping("")
    public ModelAndView users(){
        ModelAndView m = new ModelAndView("users");
        List<User> users = repository.findAll();
        m.addObject("users", users);
        return m;
    }

    @RequestMapping(value = "/add")
    public ModelAndView addUserForm(@ModelAttribute("user") UserForm user) {
        user.setNewUser(true);
        return new ModelAndView("adduser");
    }

    @PostMapping("submit")
    public ModelAndView createOrUpdateUser(@Valid @ModelAttribute("user") UserForm user, BindingResult result, RedirectAttributes attributes) {

        ModelAndView m = new ModelAndView();
        if (result.hasErrors()){
            m.setViewName("adduser");
            m.addObject("user", user);
            return m;
        }

        // If the user already exists, update it, otherwise create a new one
        Optional<User> u = repository.findById(user.getId());
        if (!u.isPresent()){
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setForename(user.getForename());
            newUser.setSurname(user.getSurname());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            newUser.setRole(User.UserRole.ADMIN);
            newUser.setUsername(user.getUsername());
            newUser.setRole(user.getRole());

            repository.save(newUser);
            attributes.addFlashAttribute("message", "New user has been added");
        }else{

            User existingUser = u.get();
            existingUser.setEmail(user.getEmail());
            existingUser.setForename(user.getForename());
            existingUser.setSurname(user.getSurname());
            existingUser.setRole(user.getRole());

            repository.save(existingUser);
            attributes.addFlashAttribute("message", "User has been updated");
        }

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

        m.addObject("user", new UserForm(u.get()));
        m.setViewName("edituser");
        return m;
    }

}
