package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findByTopicId(Long topicId);
    List<Post> findByUserIdAndTopicId(Long userId, Long topicId);
    List<Post> findByCreatedAt(Date createdAt);
    List<Post> findByTopicIdOrderByCreatedAtDesc(Long topicId);
}
