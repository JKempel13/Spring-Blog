package com.codeup.springblog;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int(11) UNSIGNED")
    private long id;
    @Column(length = 200, nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne
    private PostDetails postDetails;

    public Post() {
    }

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Post(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return description;
    }

    public void setContent(String description) {
        this.description = description;
    }

    public PostDetails getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(PostDetails postDetails) {
        this.postDetails = postDetails;
    }
}
