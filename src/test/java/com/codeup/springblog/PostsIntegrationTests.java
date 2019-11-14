package com.codeup.springblog;

import com.codeup.springblog.repositories.PostRepository;
import com.codeup.springblog.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.servlet.http.HttpSession;

import static org.hamcrest.core.StringContains.containsString;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBlogApplication.class)
@AutoConfigureMockMvc
public class PostsIntegrationTests {

    private User testUser;
    private HttpSession httpSession;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private PostRepository postDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setup() throws Exception {

        testUser = userDao.findByUsername("testUser");

        //this creates the testUser if it doesn't already exist//
        if(testUser == null) {
            User newUser = new User();
            newUser.setUsername("testUser");
            newUser.setPassword(passwordEncoder.encode("pass"));
            newUser.setEmail("testUser@codeup.com");
            testUser = userDao.save(newUser);
        }

        //this tests the login page and tests the redirection after login//
        httpSession = this.mvc.perform(post("/login").with(csrf())
        .param("username","testUser")
        .param("password","pass"))
        .andExpect(status().is(HttpStatus.FOUND.value()))
        .andExpect(redirectedUrl("/posts"))
        .andReturn()
        .getRequest()
        .getSession();
    }

    @Test
    public void contextLoads() {
        //Sanity test- making sure the MVC bean is working
        assertNotNull(mvc);
    }

    @Test
    public void testIfUserSessionIsActive() throws Exception {
        //making sure the session is not null
        assertNotNull(httpSession);
    }

    // Testing the createPost page
    @Test
    public void testCreatePost() throws Exception {
        //tests post request to /posts/create and expects redirection to the show page
        this.mvc.perform(
                post("/posts/create").with(csrf())
                    .session((MockHttpSession) httpSession)
                    //needs to have all the parameters that is required to create a post//
                    .param("title","test")
                    .param("description","test description"))
                .andExpect(status().is3xxRedirection());
    }

    //Testing the show page and the index page
    @Test
    public void testShowPost() throws Exception {
        //focusing on the testUser(5) post//
        Post existingPost = postDao.findAll().get(5);

        //Makes get request to showPage and expects redirection to same page//
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                //these are the fields that should appear on the show page
                .andExpect(content().string(containsString(existingPost.getDescription())));
    }

    @Test
    public void testPostsIndex() throws Exception {
        //same thing from before//
        Post existingPost = postDao.findAll().get(5);

        //Makes a get request to the index.html and checks if there is a specific string on that page//
        this.mvc.perform(get("/posts"))
                .andExpect(status().isOk())
                //looking for specific content on page//
                .andExpect(content().string(containsString("Featured Post")))
                //checks to see if this info is on the page
                .andExpect(content().string(containsString(existingPost.getTitle())));
    }

    //Tests the edit post page
    @Test
    public void testEditPost() throws Exception {
        //still grabbing info from first post to test
        Post existingPost = postDao.findAll().get(5);

        //makes post request to edit page and should redirect to show page
        this.mvc.perform(
                post("/posts/" + existingPost.getId() + "/edit").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title", "edited title")
                    .param("description","edited description"))
                .andExpect(status().is3xxRedirection());

        //Makes get request to edit page with correct content and should redirect to show page
        this.mvc.perform(get("/posts/" + existingPost.getId()))
                .andExpect(status().isOk())
                //looking for specific string from the post request
                .andExpect(content().string(containsString("edited title")))
                .andExpect(content().string(containsString("edited description")));
    }

    //testing the delete function
    @Test
    public void testDeletePost() throws Exception {
        //need to create a post to delete it
        this.mvc.perform(
                post("/posts/create").with(csrf())
                    .session((MockHttpSession) httpSession)
                    .param("title","post to be deleted")
                    .param("description", "Not going to show on database"))
                .andExpect(status().is3xxRedirection());

        //getting the post that we just created by title
        Post existingPost = postDao.findByTitle("post to be deleted");

        //makes post request to delete function and expects to redirect to index
        this.mvc.perform(
                post("/posts/" + existingPost.getId() + "/delete").with(csrf())
                    .session((MockHttpSession) httpSession)
                    //delete function only calls on the id
                    .param("id", String.valueOf(existingPost.getId())))
                .andExpect(status().is3xxRedirection());


    }


}
