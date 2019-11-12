package com.codeup.springblog.Controller;

import com.codeup.springblog.Post;
import com.codeup.springblog.PostDetails;
import com.codeup.springblog.Tag;
import com.codeup.springblog.User;
import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.TagRepository;
import com.codeup.springblog.repositories.UserRepository;
import com.codeup.springblog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SpringBeanContainer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class PostController {

    ArrayList<Post> postList;

    private final PostRepository postDao;
    private final TagRepository tagDao;
    private final UserRepository userDao;

    @Autowired
    EmailService emailService;

    public PostController(PostRepository postDao, TagRepository tagDao, UserRepository userDao) {
        this.postDao = postDao;
        this.tagDao = tagDao;
        this.userDao = userDao;
    }

//    @GetMapping("/posts")
//    public String returnTestView(@PathVariable long id, Model viewModel) {
//        viewModel.addAttribute("posts", postDao.getOne(id));
//        return "post/index";
//    }

    @GetMapping("/posts/{id}/historyOfPost")
    public String historyOfPost (@PathVariable long id, Model vModel) {
        Post post = postDao.getOne(id);
        vModel.addAttribute("post", post);
        return "posts/historyOfPost";
    }

    @PostMapping("/posts/update/{id}")
    public String updateDescription(@PathVariable long id, @RequestParam String description) {

        // get the correct pet object to update
        Post post = postDao.getOne(id);

        // get the current post details
        PostDetails pd = post.getPostDetails();

        // update the post details with the current details
        pd.setTopicDescription(description);
        post.setPostDetails(pd);

        // update the database record
        postDao.save(post);

        return "redirect:/posts";
    }

    @GetMapping("/posts-tags/{id}")
    public String showPostTag(@PathVariable long id,Model vModel) {
        vModel.addAttribute("post", postDao.getOne(id));
        return"posts/postTag";
    }

    @PostMapping("/tags/post/{id}")
    public String assignNewTagToPost(@PathVariable long id, @RequestParam String name) {

        Post p = postDao.getOne(id);

        // create a new tag and associate the tag with a given post
        tagDao.save(new Tag(name, Arrays.asList(p)));

        return "redirect:/posts-tags/{id}";
    }

    //shows all the posts
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

    @GetMapping("/posts/search")
    @ResponseBody
    public Post search(@PathVariable String title) {
        return postDao.findByTitle(title);
    }

    // to get the right post you want to edit
    @GetMapping("/posts/{id}/edit")
    public String editPost(@PathVariable long id, Model vModel) {
        vModel.addAttribute("posts", postDao.getOne(id));
        return "posts/edit";
    }

    // to post the changes made to post
    @PostMapping("/posts/{id}/edit")
    public String update(@PathVariable long id, @RequestParam String title, @RequestParam String description) {
        Post oldPost = postDao.getOne(id);
        oldPost.setTitle(title);
        oldPost.setDescription(description);
        postDao.save(oldPost);
        return "redirect:/posts/" + id;
    }

    // to delete
    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);
        return "redirect:/posts";
    }

    @GetMapping("/posts/create")
    public String showCreateForm(Model vModel) {
        vModel.addAttribute("post", new Post());
        // the name 'post' has to match on the html th:object//
        return "posts/createPost";
    }

    @PostMapping("/posts/create")
    public String create(@ModelAttribute Post postToBeCreated){
        postToBeCreated.setUser(userDao.getOne(1L));// this is manually setting the post to user_id 1 //
        Post savedPost = postDao.save(postToBeCreated);
        emailService.prepareAndSend(savedPost,"Post Created", "The post has been created, the id is " + savedPost.getId());
        return "redirect:/posts/" + savedPost.getId();
    }


//    @GetMapping("/posts/length")
//    public List<String> returnPostByLength() {
//        return postDao.getPostOfCertainTitleLength();
//    }
//
//    @GetMapping("/posts/length/native")
//    public List<String> returnPostByLengthNative() {
//        return postDao.getPostsOfCertainTitleLengthNative();
//    }
}
