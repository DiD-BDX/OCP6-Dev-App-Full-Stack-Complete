package com.openclassrooms.mddapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.repository.TopicRepository;

/**
 * Service pour gérer les topics.
 * <p>
 * Cette classe est annotée avec {@link Service @Service} pour indiquer à Spring que c'est un bean et
 * peut être injectée où nécessaire. Elle utilise {@link TopicRepository TopicRepository} pour effectuer des opérations
 * sur les topics dans la base de données.
 * 
 * @see TopicRepository
 */
@Service
public class TopicService {
    private final TopicRepository topicRepository;

    /**
     * Constructeur pour {@link TopicService TopicService}.
     *
     * @param topicRepository Le repository à utiliser pour les opérations sur les topics.
     * @see TopicRepository
     */
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Trouve un topic par son ID.
     *
     * @param id L'ID du topic à trouver.
     * @return {@link Topic Topic} Le topic si trouvé, sinon null.
     * @see Topic
     */
    public Topic findById(Long id) {
        return this.topicRepository.findById(id).orElse(null);
    }

    /**
     * Récupère tous les topics.
     *
     * @return La liste de tous les topics.
     * @see Topic
     */
    public List<Topic> getAllTopics() {
        return this.topicRepository.findAll();
    }

}