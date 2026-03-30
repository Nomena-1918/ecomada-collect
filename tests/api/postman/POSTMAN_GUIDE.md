# Guide de Test Postman - EcoMada Collect (Couverture Complète)

Ce guide décrit l'utilisation de la collection Postman automatisée avec couverture étendue des endpoints Admin et du workflow multi-rôles.

## Prérequis
1. **Choisir l'environnement cible** :
   - **Distant (Heroku)** : `https://ecomada-collect-c8395a62a36e.herokuapp.com`
   - **Local** : `http://localhost:8090`
2. **Importer la collection** : [ecomada-collect.postman_collection.json](ecomada-collect.postman_collection.json)
3. **Configurer `base_url`** dans les variables de collection :
   - Valeur par défaut : `https://ecomada-collect-c8395a62a36e.herokuapp.com`
   - Local : `http://localhost:8090`
4. **En local uniquement** : démarrer l'application sur le port `8090`.

## 1. Setup Authentification
1. Ouvrir le dossier **0. Setup & Authentification**.
2. Exécuter **Login All (Automatisation Totale)**.
3. Vérifier que les tokens sont remplis dans :
   - `{{admin_token}}`
   - `{{citoyen_token}}`
   - `{{collecteur_token}}`
   - `{{recycleur_token}}`

## 2. Couverture Admin (CRUD Exhaustif)
Le dossier **1. Rôle : Administrateur** contient une couverture complète **READ -> CREATE -> UPDATE -> DELETE -> READ** pour toutes les ressources administrables :
- `roles`
- `users`
- `villes`
- `communes`
- `quartiers`
- `adresses`
- `points-collecte`
- `types-dechets`
- `status`
- `collecteurs`
- `recycleurs`
- `recompenses`
- `depots` (avec update via `PATCH /status`)

Des endpoints métier/agrégation sont aussi testés :
- `GET /api/users/2/impact`
- `POST /api/users/2/recompenses/1`
- `GET /api/points-collecte/1/stats`
- `GET /api/recycleurs/1/performance`

## 3. Workflow Multi-Rôles
Exécuter dans cet ordre pour le scénario complet :
1. **Citoyen** (`2. Rôle : Citoyen`)
   - `Step 1 : Faire un dépôt (ENREGISTRE)` (stocke `{{workflow_depot_id}}`)
2. **Collecteur** (`3. Rôle : Collecteur`)
   - `Step 2 : Ramasser (COLLECTE)`
   - `Step 3 : Livrer (TRANSFERE)`
3. **Recycleur** (`4. Rôle : Recycleur`)
   - `Step 4 : Transformer (VALORISE)`
   - `Consulter performance recycleur`

## 4. Codes de Réponse Vérifiés
Chaque requête intègre des assertions Postman.
- `200/201` pour lectures/créations/mises à jour/actions métier
- `204` pour les suppressions
- `404` pour la lecture post-suppression dans les séquences CRUD

## 5. Changer d'Environnement
1. Ouvrir la collection dans Postman.
2. Aller dans l'onglet **Variables**.
3. Modifier `base_url` :
   - Distant : `https://ecomada-collect-c8395a62a36e.herokuapp.com`
   - Local : `http://localhost:8090`
4. Relancer **Login All (Automatisation Totale)** avant de reprendre les tests.

## 6. Exécution Automatisée avec Newman
Un script prêt à l'emploi est disponible : `tests/api/scripts/run_newman.sh`

Exemples :
- Distant (Heroku, défaut) : `bash tests/api/scripts/run_newman.sh`
- Local : `bash tests/api/scripts/run_newman.sh local`
- URL personnalisée : `bash tests/api/scripts/run_newman.sh https://mon-api.exemple.com`

Prérequis Newman :
- `newman` installé globalement (`npm install -g newman`)
- ou `npx` disponible (le script utilisera `npx --yes newman` automatiquement)
