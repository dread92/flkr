package io.reade.flkr.controller;

import io.reade.flkr.entity.User;
import io.reade.flkr.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserRepository users;

    public RegistrationController(UserRepository flkrService) {
        this.users = flkrService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/register")
    public String initCreationForm(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String processCreationForm(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        } else {
            this.users.save(user);
            return "redirect:/thanks";
        }
    }
}
