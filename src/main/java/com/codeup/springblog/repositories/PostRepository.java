package com.codeup.springblog.repositories;

import com.codeup.springblog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByTitle(String title);

    @Query("from Post p where p.id like ?1")
    Post getPostById(long id);

//    @Query("SELECT title FROM posts WHERE LENGTH(title) < 10")
//    List<String> getPostOfCertainTitleLength();
//
//    @Query(nativeQuery = true, value = "SELECT title FROM posts WHERE LENGTH(title) < 10")
//    List<String> getPostsOfCertainTitleLengthNative();
}