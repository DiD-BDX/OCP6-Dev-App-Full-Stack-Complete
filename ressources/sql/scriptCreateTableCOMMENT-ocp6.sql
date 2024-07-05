USE ocp6;

-- Vérifier si la table COMMENT existe, si oui, effacer tout son contenu
DROP TABLE IF EXISTS COMMENT;

-- Créer la table COMMENT
CREATE TABLE COMMENT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER(id),
    FOREIGN KEY (post_id) REFERENCES POST(id)
);