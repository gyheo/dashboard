package com.dashboard.domain;

import javax.servlet.http.HttpSession;

import com.dashboard.web.HttpSessionUtils;

import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users") // 중복을 제거하는 것 (중요)
public class UserController {
    //private List<User> users = new ArrayList<User>();
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String form(Model model) {
        return "/user/form";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userID, String password, HttpSession session) {
        User user = userRepository.findByUserID(userID);
        if (user == null || !password.equals(user.getPassword())) {
            System.out.println("Login Fail");
            return "redirect:/users/loginForm";

        }
        
        System.out.println("Login Success");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }

    @PostMapping("")
    public String create(User user) {
        //users.add(user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(!sessionedUser.matchID(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있어요");
        }

        model.addAttribute("user", userRepository.findById(id).get());
        return "/user/updateForm";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, User updatedUser, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if(!sessionedUser.matchID(id)) {
            throw new IllegalStateException("자신의 정보만 수정할 수 있어요");
        }

        User user = userRepository.findById(id).get();
        user.update(updatedUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
