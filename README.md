# EcoMada Collect
Web Service Project - MBDS 2025-2026

## Documentation Technique
- [Spécifications Complètes](specifications.md)

### URLs API
- Local : `http://localhost:8090`
- Distant (Heroku) : `https://ecomada-collect-c8395a62a36e.herokuapp.com`

### Documentation OpenAPI / Swagger
- Local Swagger UI : `http://localhost:8090/swagger-ui.html`
- Local OpenAPI : `http://localhost:8090/v3/api-docs`
- Distant Swagger UI : `https://ecomada-collect-c8395a62a36e.herokuapp.com/swagger-ui.html`
- Distant OpenAPI : `https://ecomada-collect-c8395a62a36e.herokuapp.com/v3/api-docs`

## Tests & Documentation

### Collection Postman
Une collection Postman complète est disponible pour tester tous les endpoints de l'API, y compris une section dédiée à l'administration :
- [Lien vers la collection Postman](tests/api/postman/ecomada-collect.postman_collection.json)
- [Guide de Test Postman (Workflow & CRUD)](tests/api/postman/POSTMAN_GUIDE.md)

Par défaut, la variable `base_url` de la collection pointe vers l'environnement distant (Heroku).

Couverture actuelle de la collection :
- `0. Setup & Authentification` : login automatique des 4 rôles.
- `1. Rôle : Administrateur` : CRUD exhaustif `READ -> CREATE -> UPDATE -> DELETE -> READ` pour toutes les ressources admin + endpoints d'agrégation/métier.
- `2. Rôle : Citoyen` : création de dépôt + consultation impact.
- `3. Rôle : Collecteur` : transitions `COLLECTE` puis `TRANSFERE`.
- `4. Rôle : Recycleur` : transition `VALORISE` + performance recycleur.

Pour changer d'environnement dans Postman :
1. Ouvrir la collection `ecomada-collect.postman_collection.json`.
2. Aller dans **Variables**.
3. Modifier `base_url` selon le besoin :
   - Local : `http://localhost:8090`
   - Distant : `https://ecomada-collect-c8395a62a36e.herokuapp.com`
4. Relancer la requête `0. Setup & Authentification / Login All (Automatisation Totale)`.

### Scripts de Test
Des scripts bash sont également disponibles pour tester les différents rôles et workflows :
- `tests/api/scripts/demo_roles.sh` : Teste les permissions des rôles Admin, Citoyen, Collecteur et Recycleur.
- `tests/api/scripts/run_newman.sh` : Exécute la collection Postman complète via Newman (`remote`, `local` ou URL personnalisée).

Pour exécuter les scripts :
```bash
chmod +x tests/api/scripts/demo_roles.sh
./tests/api/scripts/demo_roles.sh

# Exécuter la collection Postman sur Heroku (par défaut)
bash tests/api/scripts/run_newman.sh

# Exécuter sur l'environnement local
bash tests/api/scripts/run_newman.sh local

# Exécuter sur une URL personnalisée
bash tests/api/scripts/run_newman.sh https://mon-api.exemple.com
```
