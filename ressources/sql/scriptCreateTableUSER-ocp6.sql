USE ocp6;

-- Vérifier si la table USER existe, si oui, effacer tout son contenu
DROP TABLE IF EXISTS USER;

-- Créer la table USER
CREATE TABLE USER (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) NOT NULL UNIQUE,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL
);