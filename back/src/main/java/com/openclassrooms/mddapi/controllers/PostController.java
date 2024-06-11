package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{userId}/posts")
    public List<PostDto> getPostsByUserSubscriptions(@PathVariable Long userId) {
        return postService.getPostsByUserSubscriptions(userId);
    }

    @PostMapping("/post/create/{userId}/{topicId}")
    public PostDto createPost(@PathVariable Long userId, @PathVariable Long topicId, @RequestBody PostDto postDto) {
        System.out.println("---------UserId: " + userId);
        System.out.println("----------TopicId: " + topicId);
        System.out.println("---------PostDto: " + postDto);

        postDto.setUserId(userId);
        postDto.setTopicId(topicId);

        System.out.println("----------PostDto after setting UserId and TopicId: " + postDto);
        return postService.createPost(postDto);
    }
}