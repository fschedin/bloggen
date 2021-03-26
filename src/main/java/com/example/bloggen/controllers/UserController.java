package com.example.bloggen.controllers;

import com.example.bloggen.repository.BlogRepository;
import com.example.bloggen.repository.CommentRepository;
import com.example.bloggen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private CommentRepository commentRepository;

    //show all blogs in admin view
    @RequestMapping("/admin")
    public String showAdmin(Model model) {
        model.addAttribute("blogs", blogRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("comments", commentRepository.findAll());
        return "adminview";
    }


}
