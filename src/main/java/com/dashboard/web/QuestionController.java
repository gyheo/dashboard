package com.dashboard.web;

import com.dashboard.domain.Question;
import com.dashboard.domain.QuestionRepository;
import com.dashboard.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }
        
        return "/qna/form";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "/users/loginForm";
        }

        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        Question newQuestion = new Question(sessionUser, title, contents);
        questionRepository.save(newQuestion);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());

        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionRepository.findById(id).get());

        return "qna/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, String title, String contents) {
        Question question = questionRepository.findById(id).get();
        question.update(title, contents);
        questionRepository.save(question);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        questionRepository.deleteById(id);
        return "redirect:/";
    }
}
