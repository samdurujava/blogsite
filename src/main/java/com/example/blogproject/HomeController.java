package com.example.blogproject;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    BlogRepository posts;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        if (result.hasErrors()){
            return "registration";
        } else {
            user.setEnabled(true);
            user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
            userRepository.save(user);
            model.addAttribute("created",  true);
        }
        return "login";
    }

//    @RequestMapping("/")
//    public String homepage(Model model){
//        model.addAttribute("blogs", blogRepository.findAll());
//        return "homepage";
//    }

    @RequestMapping("/")
    public String homePage(Principal principal, Model model) {
        model.addAttribute("posts", posts.findAll());
        User user = ((CustomUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
        model.addAttribute("user", user);
        Role role1 = roleRepository.findByRole("ADMIN");
        for (User check : role1.getUsers()) {
            if (check.getId() == user.getId()) {
                return "redirect:/posts";
            }
        }
        return "homepage";
    }

//    @RequestMapping("/posts")
//    public String blogpage(Model model){
//        model.addAttribute("blogs", blogRepository.findAll());
//        return "posts";
//    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile (){
        return "profile";
    }

    @RequestMapping("/show")
    public String showPage(Principal principal, Model model) {
        model.addAttribute("posts", posts.findAll());
        return "show";
    }

    @RequestMapping("/overview")
    public String aboutPage(Model model){
        model.addAttribute("blogs", blogRepository.findAll());
        return "overview";
    }

    @RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        model.addAttribute("posts", posts.findAll());
        User user = ((CustomUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
        model.addAttribute("user", user);
        return "posts";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/add")
    public String addBlog(Principal principal, Model model){
        model.addAttribute("blog", new Blog());
        User user = ((CustomUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
        model.addAttribute("user", user);
        return "blogform";
    }

    @PostMapping("/send")
    public String sendBlog(Principal principal, @Valid Blog blog, BindingResult result, @RequestParam("file")MultipartFile file) {
        if (result.hasErrors()) {
            return "redirect:/add";
        }
        if (!file.isEmpty()) {
            try {
                Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
//                if (msg.isSepia()) {
//                    msg.setImage(cloudc.sepia(uploadResult.get("public_id").toString()));
//                    String info = cloudc.sepiaThumb(uploadResult.get("public_id").toString() + ".jpg", 50, 50, "fill");
//                    msg.setThumb(info);
//                } else {
                blog.setImage(uploadResult.get("url").toString());
                String info = cloudc.createUrl(uploadResult.get("public_id").toString() + ".jpg", 50, 50, "fill");
                String thumb = info.substring(info.indexOf("'") + 1, info.indexOf("'", info.indexOf("'") + 1));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        User user = ((CustomUserDetails)((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getUser();
        blog.setPostedDate(new Date());
        posts.save(blog);

        return "redirect:/posts";
    }

    @RequestMapping("/view/{id}")
    public String viewTask(@PathVariable("id") long id, Model model) {
        model.addAttribute("blog", posts.findById(id).get());
        return "show";
    }


    @RequestMapping("/update/{id}")
    public String updateBlog(@PathVariable("id") long id,
                             Model model){
        model.addAttribute("blog", blogRepository.findById(id).get());
        return "blogform";
    }

    @RequestMapping("/delete/{id}")
    public String delBlog(@PathVariable("id") long id, Model model){
        posts.deleteById(id);
        return "redirect:/admin";
    }
}
