USE ocp6;
-- Vérifier si la table TOPIC existe, si oui, effacer tout son contenu
DROP TABLE IF EXISTS TOPIC;

CREATE TABLE TOPIC (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

-- Repeupler la table TOPIC
INSERT INTO TOPIC (name, description)
VALUES 
('Java', 'Discussion sur le langage de programmation Java et ses frameworks'),
('Full-Stack JavaScript', 'Discussion sur le développement Full-Stack JavaScript, y compris Node.js, Express, React, et Vue.js'),
('Python', 'Sujet dédié au langage de programmation Python et à son écosystème'),
('Web Development', 'Tout sur le développement web, HTML, CSS, JavaScript et plus'),
('DevOps', 'Pratiques de DevOps, CI/CD, Docker, Kubernetes et autres technologies'),
('Machine Learning', 'Discussion sur l''apprentissage automatique, les bibliothèques Python, les algorithmes et plus'),
('Open Source Databases', 'Discussion sur les bases de données open source, y compris MySQL, PostgreSQL, MongoDB et plus');