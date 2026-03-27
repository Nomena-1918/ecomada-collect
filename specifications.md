# EcoMada Collect – Sujet complet de mini-projet REST

**API REST de gestion intelligente des déchets et de valorisation circulaire à Madagascar**

---

## Sommaire

 1. [Titre du projet](#1-titre-du-projet)
 2. [Contexte](#2-contexte)
 3. [Problématique](#3-problématique)
 4. [Objectif général](#4-objectif-général)
 5. [Objectifs spécifiques](#5-objectifs-spécifiques)
 6. [Acteurs du système](#6-acteurs-du-système)
 7. [Modèle conceptuel des données (MCD)](#7-modèle-conceptuel-des-données-mcd)
 8. [Relations entre les entités](#8-relations-entre-les-entités)
 9. [Description fonctionnelle du cycle métier](#9-description-fonctionnelle-du-cycle-métier)
 10. [Règles de gestion](#10-règles-de-gestion)
 11. [Périmètre technique du projet](#11-périmètre-technique-du-projet)
 12. [Structure détaillée des ressources REST](#12-structure-détaillée-des-ressources-rest)
 13. [Filtres recommandés](#13-filtres-recommandés)
 14. [Endpoints d’agrégation](#14-endpoints-dagrégation)
 15. [HATEOAS](#15-hateoas)
 16. [Choix techniques recommandés](#16-choix-techniques-recommandés)
 17. [Structure technique recommandée](#17-structure-technique-recommandée)
 18. [Données de démonstration](#18-données-de-démonstration)
 19. [Scénario d’utilisation](#19-scénario-dutilisation)
 20. [Schéma logique simplifié](#20-schéma-logique-simplifié)
 21. [Valeur ajoutée du projet](#21-valeur-ajoutée-du-projet)
 22. [Formulation finale du sujet](#22-formulation-finale-du-sujet)
 23. [Résumé court pour présentation orale](#23-résumé-court-pour-présentation-orale)
 24. [Sources et Références](#24-sources-et-références)

---

## 1. Titre du projet
**EcoMada Collect** – API REST de gestion intelligente des déchets et de valorisation circulaire à Madagascar

## 2. Contexte
À Madagascar, en particulier dans les zones urbaines et périurbaines, la gestion des déchets constitue un défi majeur. L’accumulation de déchets plastiques, ménagers et recyclables dans les quartiers, marchés, rues et canaux de drainage a des conséquences directes sur la santé publique, l'assainissement et l'environnement.

Le projet **EcoMada Collect** consiste à développer une **API REST sécurisée** permettant de gérer les dépôts de déchets, leur collecte, leur transfert vers des recycleurs, ainsi que la participation des citoyens à une logique d’économie circulaire locale.

## 3. Problématique
Comment concevoir une API REST sécurisée permettant de digitaliser la gestion des dépôts de déchets, leur collecte et leur valorisation dans un contexte urbain malgache, tout en produisant des statistiques utiles et en encourageant la participation citoyenne ?

## 4. Objectif général
Développer une **API REST complète, sécurisée et documentée** pour la gestion intelligente des déchets à Madagascar, en intégrant les différents acteurs du cycle de valorisation.

## 5. Objectifs spécifiques
L’API devra permettre de :
* Gérer les utilisateurs, les statuts hiérarchisés et les localisations géographiques.
* Enregistrer les dépôts de déchets et gérer leur cycle de vie (collecte -> transfert -> valorisation).
* Attribuer des récompenses aux citoyens engagés.
* Produire des statistiques et agrégations décisionnelles.

## 6. Acteurs du système
* **Administrateur** : Gestion globale (utilisateurs, géo, types de déchets, récompenses).
* **Citoyen** : Enregistrement des dépôts, consultation de l'impact et des récompenses.
* **Collecteur** : Prise en charge des dépôts et mise à jour des statuts de collecte.
* **Recycleur** : Réception des flux et déclaration de la valorisation finale.

## 7. Modèle conceptuel des données (MCD)
Les entités principales incluent : **User, Role, Ville, Commune, Quartier, Adresse, PointCollecte, TypeDechet, Status, Depot, Collecteur, Recycleur, Recompense**.

## 8. Relations entre les entités
* **Géo** : Ville (1-n) Commune (1-n) Quartier (1-n) Adresse (1-n) PointCollecte.
* **Métier** : User (1-n) Depot, Status (1-n) Depot, Collecteur/Recycleur (1-n) Depot.
* **n-n** : User ↔ Role, User ↔ Recompense, Recycleur ↔ TypeDechet.

## 9. Description fonctionnelle du cycle métier
Un citoyen dépose un déchet (`ENREGISTRE`). Un collecteur le ramasse (`COLLECTE`) puis le livre (`TRANSFERE`) à un recycleur qui le transforme (`VALORISE`). Ce cycle génère des points pour le citoyen.

## 10. Règles de gestion
* **Statuts** : Un dépôt ne peut passer qu'à un statut de rang supérieur (1 -> 2 -> 3 -> 4).
* **Localisation** : Chaque point de collecte doit être rattaché à une adresse physique précise.
* **Accès** : Seul un Recycleur ou Admin peut passer un dépôt au statut "VALORISE".

## 11. Périmètre technique du projet
* Architecture REST, Base H2, Sécurité JWT, Pagination, HATEOAS et Swagger.

## 12. Structure détaillée des ressources REST
*(La mention **CRUD** indique : GET (all), GET (one), POST, PUT, DELETE)*

* **Auth** : `POST /api/auth/register`, `POST /api/auth/login`
* **Utilisateurs (CRUD)** : `/api/users`, `/api/users/{id}/impact` (Stats)
* **Rôles (CRUD)** : `/api/roles`
* **Géographie (CRUD)** : `/api/villes`, `/api/communes`, `/api/quartiers`, `/api/adresses`
* **Collecte (CRUD)** : `/api/points-collecte`, `/api/points-collecte/{id}/stats` (Agrégation)
* **Déchets (CRUD)** : `/api/types-dechets`, `/api/status`
* **Dépôts (CRUD)** : `/api/depots` (Filtres multi-critères obligatoires)
* **Partenaires (CRUD)** : `/api/collecteurs`, `/api/recycleurs`, `/api/recycleurs/{id}/performance`
* **Récompenses (CRUD)** : `/api/recompenses`, `/api/users/{id}/recompenses/{recompenseId}` (Attribution)

## 13. Filtres recommandés
Recherches par `villeId`, `statusId`, `typeDechetId`, `dateMin/Max`, et `actif` (pour les points et recycleurs).

## 14. Endpoints d’agrégation
* **Stats Point** : Vue synthétique des volumes par type pour un lieu donné.
* **Impact Citoyen** : Total Kg déposés, points cumulés et récompenses obtenues.

## 15. HATEOAS
Les réponses JSON doivent inclure les liens `_links` vers les ressources liées (ex: un dépôt doit pointer vers son citoyen et son point de collecte).

## 16. Choix techniques recommandés
* **Java 25**, **Gradle**, **Spring Boot 4.0.5**, **Spring Security (JWT)**, **Spring Data JPA**, **H2**, **Lombok**, **MapStruct**.

## 17. Structure technique recommandée
Organisation par packages : `controller`, `service`, `repository`, `entity`, `dto`, `mapper`, `security`, `exception`.

## 18. Données de démonstration
Villes : Antananarivo, Toamasina, Mahajanga, Toliara. Déchets : Plastique, Verre, Métal, Organique.

## 19. Scénario d’utilisation
Inscription -> Localisation du point -> Dépôt (8kg plastique) -> Collecte -> Valorisation -> Gain de récompense.

## 20. Schéma logique simplifié
Hiérarchie géo stricte et suivi linéaire du cycle de vie du déchet via le champ `rang` de la table `Status`.

## 21. Valeur ajoutée du projet
Réponse aux enjeux d'assainissement malgaches, structuration des flux informels et incitation au tri par la "gamification" (récompenses).

## 22. Formulation finale du sujet
Conception d'une API REST sécurisée pour le pilotage de l'économie circulaire à Madagascar, incluant la traçabilité géo-localisée des déchets et l'agrégation de données d'impact environnemental.

## 23. Résumé court pour présentation orale
**EcoMada Collect** : Une plateforme API pour digitaliser la chaîne de valeur des déchets à Madagascar, transformant chaque dépôt citoyen en impact mesurable et valorisé.

## 24. Sources et Références
* **Société Municipale d'Assainissement (SMA)** : Autorité de référence pour la propreté urbaine à Antananarivo.
* **Ministère de l'Environnement (MEDD)** : Politiques nationales de gestion des déchets à Madagascar.
* **Banque Mondiale & AFD** : Rapports sur le développement urbain et le projet PRODUIR à Madagascar.
* **ODD 11 & 12** : Objectifs mondiaux pour les villes durables et la consommation responsable.