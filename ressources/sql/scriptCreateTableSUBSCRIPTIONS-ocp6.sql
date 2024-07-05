USE ocp6;

-- Vérifier si la table SUBSCRIPTIONS existe, si oui, effacer tout son contenu
DROP TABLE IF EXISTS SUBSCRIPTIONS;

-- Créer la table SUBSCRIPTIONS
CREATE TABLE SUBSCRIPTIONS (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    subscribed_at TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES USER(id),
    FOREIGN KEY (topic_id) REFERENCES TOPIC(id)
);