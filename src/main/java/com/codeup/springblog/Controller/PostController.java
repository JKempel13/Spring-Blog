package com.codeup.springblog.Controller;

import com.codeup.springblog.Post;
import com.codeup.springblog.repositories.PostRepository;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class PostController {

    ArrayList<Post> postList;

    private PostRepository postDao;

    public PostController(PostRepository postDao) {
        this.postDao = postDao;
    }

    @GetMapping("/posts")
    public String index(Model vModel) {
        vModel.addAttribute("posts", postDao.findAll());
        return "posts/index";
    }

    @GetMapping("/posts/{id}")
    public String show(@PathVariable long id, Model vModel) {
        vModel.addAttribute("post", postDao.getOne(id));
        return "posts/show";
    }

//    @GetMapping("/posts/search")
//    public Post search() {
//
//    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String showForm() {
        return "view the form for creating an ad";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String create(@RequestParam String title, @RequestParam String body) {
        System.out.println("title = " + title);
        System.out.println("body = " + body);
        return "create a new ad";
    }
}
