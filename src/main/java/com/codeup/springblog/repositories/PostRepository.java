package com.codeup.springblog.repositories;

import com.codeup.springblog.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);

    @Query("from Post a where a.id like ?1")
    Post getPostById(long id);
}
