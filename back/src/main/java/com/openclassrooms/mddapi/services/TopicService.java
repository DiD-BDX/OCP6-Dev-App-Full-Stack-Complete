package com.openclassrooms.mddapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.models.Topic;

import com.openclassrooms.mddapi.repository.TopicRepository;

/**
 * Service pour gérer les topics.
 * <p>
 * Cette classe est annotée avec {@link Service} pour indiquer à Spring que c'est un bean et
 * peut être injectée où nécessaire. Elle utilise {@link TopicRepository} pour effectuer des opérations
 * sur les topics dans la base de données.
 */
@Service
public class TopicService {
    private final TopicRepository topicRepository;


    /**
     * Constructeur pour {@link TopicService}.
     *
     * @param topicRepository Le repository à utiliser pour les opérations sur les topics.
     * @param userRepository Le repository à utiliser pour les opérations sur les utilisateurs.
     */
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    /**
     * Trouve un topic par son ID.
     *
     * @param id L'ID du topic à trouver.
     * @return {@link Topic} Le topic si trouvé, sinon null.
     */
    public Topic findById(Long id) {
        return this.topicRepository.findById(id).orElse(null);
    }

    /**
     * Met à jour un topic avec un nouveau titre.
     *
     * @param currentName Le titre actuel du topic.
     * @param newName Le nouveau titre.
     * @return {@link Topic} Le topic mis à jour si trouvé et mis à jour avec succès, sinon null.
     */
    public Topic updateTopic(String currentName, String newName) {
        Optional<Topic> optionalTopic = this.topicRepository.findByName(currentName);

        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setName(newName);
            this.topicRepository.save(topic);
            return topic;
        }

        return null;
    }

    /**
     * Met à jour la description d'un topic.
     *
     * @param id L'ID du topic à mettre à jour.
     * @param newDescription La nouvelle description.
     * @return {@link Topic} Le topic mis à jour si trouvé et mis à jour avec succès, sinon null.
     */
    public Topic updateDescription(Long id, String newDescription) {
        Optional<Topic> optionalTopic = this.topicRepository.findById(id);

        if (optionalTopic.isPresent()) {
            Topic topic = optionalTopic.get();
            topic.setDescription(newDescription);
            this.topicRepository.save(topic);
            return topic;
        }

        return null;
    }

    /**
     * Récupère tous les topics.
     *
     * @return La liste de tous les topics.
     */
    public List<Topic> getAllTopics() {
        return this.topicRepository.findAll();
    }
}
