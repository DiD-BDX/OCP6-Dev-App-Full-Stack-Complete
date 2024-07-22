package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.services.TopicService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur pour les opérations liées aux topics.
 * <p>
 * Ce contrôleur gère les requêtes pour obtenir tous les topics.
 * Toutes les méthodes de ce contrôleur sont mappées sur l'URL "/api/topics".
 * @see com.openclassrooms.mddapi.controllers
 */
@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {
    private final TopicMapper topicMapper;
    private final TopicService topicService;

    /**
     * Récupère la liste de tous les topics.
     *
     * @return Une liste de tous les topics.
     * @see Topic
     */
    @GetMapping
    public List<TopicDto> getAllTopics() {
        List<Topic> topics = topicService.getAllTopics();
        return topics.stream()
                        .map(topicMapper::toDto)
                        .collect(Collectors.toList());
    }
}