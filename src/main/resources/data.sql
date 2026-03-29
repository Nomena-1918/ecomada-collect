-- Roles
INSERT INTO roles (nom)
VALUES ('ADMIN');
INSERT INTO roles (nom)
VALUES ('CITOYEN');
INSERT INTO roles (nom)
VALUES ('COLLECTEUR');
INSERT INTO roles (nom)
VALUES ('RECYCLEUR');

-- Statuts (rang croissant)
INSERT INTO statuts (nom, rang)
VALUES ('ENREGISTRE', 1);
INSERT INTO statuts (nom, rang)
VALUES ('COLLECTE', 2);
INSERT INTO statuts (nom, rang)
VALUES ('TRANSFERE', 3);
INSERT INTO statuts (nom, rang)
VALUES ('VALORISE', 4);

-- Types de dechets
INSERT INTO types_dechet (nom)
VALUES ('Plastique');
INSERT INTO types_dechet (nom)
VALUES ('Verre');
INSERT INTO types_dechet (nom)
VALUES ('Metal');
INSERT INTO types_dechet (nom)
VALUES ('Organique');

-- Villes
INSERT INTO villes (nom)
VALUES ('Antananarivo');
INSERT INTO villes (nom)
VALUES ('Toamasina');
INSERT INTO villes (nom)
VALUES ('Mahajanga');
INSERT INTO villes (nom)
VALUES ('Toliara');

-- Communes
INSERT INTO communes (nom, ville_id)
VALUES ('Antananarivo Renivohitra', 1);
INSERT INTO communes (nom, ville_id)
VALUES ('Ambohidratrimo', 1);
INSERT INTO communes (nom, ville_id)
VALUES ('Toamasina I', 2);
INSERT INTO communes (nom, ville_id)
VALUES ('Mahajanga I', 3);
INSERT INTO communes (nom, ville_id)
VALUES ('Toliara I', 4);

-- Quartiers
INSERT INTO quartiers (nom, commune_id)
VALUES ('Analakely', 1);
INSERT INTO quartiers (nom, commune_id)
VALUES ('Isotry', 1);
INSERT INTO quartiers (nom, commune_id)
VALUES ('Ambodivona', 2);
INSERT INTO quartiers (nom, commune_id)
VALUES ('Anjoma', 3);
INSERT INTO quartiers (nom, commune_id)
VALUES ('Tsaramandroso', 4);
INSERT INTO quartiers (nom, commune_id)
VALUES ('Tanambao', 5);

-- Adresses
INSERT INTO adresses (rue, quartier_id)
VALUES ('Rue de lIndependance', 1);
INSERT INTO adresses (rue, quartier_id)
VALUES ('Avenue Andrianampoinimerina', 2);
INSERT INTO adresses (rue, quartier_id)
VALUES ('Boulevard Ratsimilaho', 4);
INSERT INTO adresses (rue, quartier_id)
VALUES ('Rue du Commerce', 5);

-- Points de collecte
INSERT INTO points_collecte (nom, latitude, longitude, actif, adresse_id)
VALUES ('Point Analakely Centre', -18.9137, 47.5261, TRUE, 1);
INSERT INTO points_collecte (nom, latitude, longitude, actif, adresse_id)
VALUES ('Point Isotry Marche', -18.9180, 47.5190, TRUE, 2);
INSERT INTO points_collecte (nom, latitude, longitude, actif, adresse_id)
VALUES ('Point Toamasina Port', -18.1492, 49.3958, TRUE, 3);
INSERT INTO points_collecte (nom, latitude, longitude, actif, adresse_id)
VALUES ('Point Toliara Plage', -23.3516, 43.6854, TRUE, 4);

-- Users (mot de passe: password123 => BCrypt)
INSERT INTO users (nom, email, mot_de_passe)
VALUES ('Admin System', 'admin@ecomada.mg', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');
INSERT INTO users (nom, email, mot_de_passe)
VALUES ('Rakoto Jean', 'rakoto@ecomada.mg', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');
INSERT INTO users (nom, email, mot_de_passe)
VALUES ('Rasoa Marie', 'rasoa@ecomada.mg', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG');

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- User-Roles
INSERT INTO user_roles (user_id, role_id)
VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id)
VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id)
VALUES (3, 2);

-- Collecteurs
INSERT INTO collecteurs (nom, telephone, actif)
VALUES ('Collecte Express', '034 00 000 01', TRUE);
INSERT INTO collecteurs (nom, telephone, actif)
VALUES ('EcoRamasse', '034 00 000 02', TRUE);

-- Recycleurs
INSERT INTO recycleurs (nom, adresse, actif)
VALUES ('MadaRecycle', 'Zone industrielle Antsirabe', TRUE);
INSERT INTO recycleurs (nom, adresse, actif)
VALUES ('GreenPlast', 'Tanjombato, Antananarivo', TRUE);

CREATE TABLE IF NOT EXISTS recycleur_type_dechet
(
    recycleur_id   BIGINT NOT NULL,
    type_dechet_id BIGINT NOT NULL,
    PRIMARY KEY (recycleur_id, type_dechet_id),
    FOREIGN KEY (recycleur_id) REFERENCES recycleurs (id),
    FOREIGN KEY (type_dechet_id) REFERENCES types_dechet (id)
);

-- Recycleur-TypeDechet
INSERT INTO recycleur_type_dechet (recycleur_id, type_dechet_id)
VALUES (1, 1);
INSERT INTO recycleur_type_dechet (recycleur_id, type_dechet_id)
VALUES (1, 2);
INSERT INTO recycleur_type_dechet (recycleur_id, type_dechet_id)
VALUES (2, 1);
INSERT INTO recycleur_type_dechet (recycleur_id, type_dechet_id)
VALUES (2, 3);

-- Recompenses
INSERT INTO recompenses (nom, description, points_requis)
VALUES ('Eco-Citoyen Bronze', 'Badge pour 100 points cumules', 100);
INSERT INTO recompenses (nom, description, points_requis)
VALUES ('Eco-Citoyen Argent', 'Badge pour 500 points cumules', 500);
INSERT INTO recompenses (nom, description, points_requis)
VALUES ('Eco-Citoyen Or', 'Badge pour 1000 points cumules', 1000);

-- Depots de demo
INSERT INTO depots (poids_kg, date_depot, user_id, point_collecte_id, type_dechet_id, status_id, collecteur_id,
                    recycleur_id)
VALUES (8.0, '2026-03-01 10:00:00', 2, 1, 1, 4, 1, 1);
INSERT INTO depots (poids_kg, date_depot, user_id, point_collecte_id, type_dechet_id, status_id, collecteur_id,
                    recycleur_id)
VALUES (3.5, '2026-03-05 14:30:00', 2, 1, 2, 3, 1, NULL);
INSERT INTO depots (poids_kg, date_depot, user_id, point_collecte_id, type_dechet_id, status_id, collecteur_id,
                    recycleur_id)
VALUES (5.0, '2026-03-10 09:00:00', 3, 2, 4, 2, 2, NULL);
INSERT INTO depots (poids_kg, date_depot, user_id, point_collecte_id, type_dechet_id, status_id, collecteur_id,
                    recycleur_id)
VALUES (2.0, '2026-03-15 16:00:00', 3, 3, 3, 1, NULL, NULL);

CREATE TABLE IF NOT EXISTS user_recompenses
(
    recompense_id BIGINT NOT NULL,
    user_id       BIGINT NOT NULL,
    PRIMARY KEY (recompense_id, user_id),
    FOREIGN KEY (recompense_id) REFERENCES recompenses (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Attribution recompense demo
INSERT INTO user_recompenses (recompense_id, user_id)
VALUES (1, 2);
