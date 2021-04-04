package com.kupchyk.javaee.controller;

import com.kupchyk.javaee.dto.UserDto;
import com.kupchyk.javaee.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.kupchyk.javaee.service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-in")
    public String getSignIn(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("error", null);
        return "login";
    }

    @PostMapping("/sign-in")
    public String postSignIn(@ModelAttribute User user, Model model) {
        List<String> error = userService.login(user.getUsername(),user.getPassword());
        if(error.isEmpty()){
            return "redirect:/";
        }
        model.addAttribute("error", error.get(0));
        return "login";
    }

    @GetMapping("/sign-up")
    public String getSignUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/sign-up")
    public String postSignUp(@ModelAttribute @Valid UserDto user, BindingResult bindingResult, Model model) {
        List<String> errorsN= new ArrayList<>();
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;

                    String message = messageSource.getMessage(fieldError, null);
                    errorsN.add(message);
                }
            }
            model.addAttribute("error", errorsN);
            return "signup";
        }

       errorsN = userService.signup(user);
        if (errorsN.isEmpty()) {
            return "redirect:/sign-in";
        }
        model.addAttribute("error", errorsN);
        return "signup";
    }
}
