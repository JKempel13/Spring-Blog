package com.codeup.springblog;

import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.*;

@Entity
@Table(name="post_details")
public class PostDetails {
//Seed a couple of entries into the PostDetails and Posts table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT(11) UNSIGNED")
    private long id;

    @Column
    private boolean isAwesome;

    @Column(columnDefinition = "TEXT")
    private String historyOfPost;

    @Column
    private String topicDescription;

    @OneToOne(mappedBy = "postDetails")
    private Post post;

    public PostDetails() {
    }

    public PostDetails(boolean isAwesome, String historyOfPost, String topicDescription) {
        this.isAwesome = isAwesome;
        this.historyOfPost = historyOfPost;
        this.topicDescription = topicDescription;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAwesome() {
        return isAwesome;
    }

    public void setAwesome(boolean awesome) {
        isAwesome = awesome;
    }

    public String getHistoryOfPost() {
        return historyOfPost;
    }

    public void setHistoryOfPost(String historyOfPost) {
        this.historyOfPost = historyOfPost;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }
}
