package com.example.blogproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    BlogRepository blogRepository;

    @RequestMapping("/")
    public String homepage(Model model){
        model.addAttribute("blogs", blogRepository.findAll());
        return "homepage";
    }

    @RequestMapping("/posts")
    public String blogpage(Model model){
        model.addAttribute("blogs", blogRepository.findAll());
        return "posts";
    }

    @RequestMapping("/overview")
    public String aboutPage(Model model){
        model.addAttribute("blogs", blogRepository.findAll());
        return "overview";
    }

    @RequestMapping("/profile")
    public String profilePage(Model model){
        model.addAttribute("blogs", blogRepository.findAll());
        return "profile";
    }

    @GetMapping("/addblog")
    public String blogForm(Model model){
        model.addAttribute("blog", new Blog());
        return "blogform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Blog blog,
                              BindingResult result) {
        if (result.hasErrors()){
            return "blogform";
        }
        blogRepository.save(blog);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showMessage(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("blog",blogRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateBlog(@PathVariable("id") long id,
                             Model model){
        model.addAttribute("blog", blogRepository.findById(id).get());
        return "blogform";
    }

    @RequestMapping("/delete/{id}")
    public String delBlog(@PathVariable("id") long id){
        blogRepository.deleteById(id);
        return "redirect:/";
    }
}
