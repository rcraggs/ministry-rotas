package rcraggs.rota.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import rcraggs.rota.forms.UserForm;
import rcraggs.rota.repository.UserRepository;


import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserForm.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors err) {

        ValidationUtils.rejectIfEmpty(err, "forename", "user.forename.empty");
        ValidationUtils.rejectIfEmpty(err, "surname", "user.surname.empty");
        ValidationUtils.rejectIfEmpty(err, "username", "user.username.empty");
        ValidationUtils.rejectIfEmpty(err, "password", "user.password.empty");

        UserForm user = (UserForm) obj;

        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);
        if (!(pattern.matcher(user.getEmail()).matches())) {
            err.rejectValue("email", "user.email.invalid");
        }


        if (!user.getPassword().equals(user.getConfirmPassword())){
            err.rejectValue("confirmPassword", "user.password.different");
        }

        if (!user.getUsername().isEmpty()){
            if (repository.findByUsername(user.getUsername()) != null){
                err.rejectValue("username", "user.username.unique");
            }
        }
    }
}

