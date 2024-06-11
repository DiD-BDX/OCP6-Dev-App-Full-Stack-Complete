USE ocp6;

-- Vérifier si la table POST existe, si oui, effacer tout son contenu
DROP TABLE IF EXISTS POST;

-- Créer la table POST
CREATE TABLE POST (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT,
    created_at TIMESTAMP
);
