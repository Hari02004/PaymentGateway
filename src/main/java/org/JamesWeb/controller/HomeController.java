package org.JamesWeb.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.JamesWeb.DTO.LoginRequestDto;
import org.JamesWeb.model.users;
import org.JamesWeb.service.UserService;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @GetMapping("/register")
    public String register(){
            log.info("GET /register");
            return "register";
        }

    @GetMapping("/home")
    public String homePage(){
            log.info("GET /home");
            return "home";
        }

    @PostMapping("/register")
    public String saveUser(@ModelAttribute users user){
                 log.info("Saving new user registration");
                 userService.register(user);
                 return "redirect:/";
        }
    @PostMapping("/")
    public String userLogin(@ModelAttribute LoginRequestDto loginRequestDto, HttpServletRequest request){
            log.info("POST / - login attempt");
            userService.login(loginRequestDto,request);
            return "/home";
        }
    @GetMapping("/")
    public String login(Model model){
            log.info("GET /");
            model.addAttribute("username","");
            return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
            log.info("GET /logout - invalidating session");
            HttpSession session = request.getSession(false);
            if(session !=null){
                session.invalidate();
            }

            SecurityContextHolder.clearContext();

            return "redirect:/";
        }
}

