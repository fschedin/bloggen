package com.example.bloggen.controllers;

import com.example.bloggen.entity.Blog;
import com.example.bloggen.entity.User;
import com.example.bloggen.repository.BlogRepository;
import com.example.bloggen.repository.CommentRepository;
import com.example.bloggen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class BlogController {
    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CommentRepository commentRepository;
//TEST TEST KRAKEN
    //show all blogs with paging
    @GetMapping("/blogs/page/{pageno}")
    public String showPage(@PathVariable() Integer pageno, Model model) {

        if (pageno < 0 || pageno == null) {
            pageno = 0;
        }
        final int PAGESIZE = 3; //number of blogs on each page

        PageRequest paging = PageRequest.of(pageno, PAGESIZE);
        Page<Blog> pagedResult = blogRepository.findAll(paging);
        List<Blog> listBlogs;
        //returns the page content our 3 products as List
        listBlogs = pagedResult.getContent();
        model.addAttribute("currentPageNumber", pagedResult.getNumber()); //zerobased
        model.addAttribute("displayableCurrentPageNumber", pagedResult.getNumber() + 1);
        model.addAttribute("nextPageNumber", pageno + 1); //going forward to next page
        model.addAttribute("previousPageNumber", pageno - 1); //going backwards to previous page
        model.addAttribute("totalPages", pagedResult.getTotalPages());
        model.addAttribute("totalItems", pagedResult.getTotalElements());
        model.addAttribute("hasNext", pagedResult.hasNext());
        model.addAttribute("hasPrevious", pagedResult.hasPrevious());

        model.addAttribute("blogs", listBlogs);//set the list with the 3 blogs
        return "blogallview";
    }

    //add a blog
    @RequestMapping("/blogs/add")
    public String addBlog(Model model) {
        return "blogaddview";
    }

    @Autowired
    SecurityController sec = new SecurityController();


    @PostMapping("/blogs/add")
    public String addBlogToDB(Model model, @RequestParam Map<String, String> allFormRequestParams) {
        Blog blog = new Blog();
        blog.setTopic(allFormRequestParams.get("topic"));
        blog.setBody(allFormRequestParams.get("body"));
        User user = userRepository.findByUsername(sec.loggedInUser());
        user.addBlog(blog);
        System.out.println(user);
        userRepository.save(user);


        return "redirect:/blogs/page/0";
    }

    //delete blog



}
