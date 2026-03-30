# EcoMada Collect
Web Service Project - MBDS 2025-2026

## Documentation Technique
- [Spécifications Complètes](specifications.md)
- [Swagger UI (Interactif)](http://localhost:8090/swagger-ui.html)
- [OpenAPI Docs (JSON/YAML)](http://localhost:8090/v3/api-docs)

## Tests & Documentation

### Collection Postman
Une collection Postman complète est disponible pour tester tous les endpoints de l'API, y compris une section dédiée à l'administration :
- [Lien vers la collection Postman](tests/api/postman/ecomada-collect.postman_collection.json)
- [Guide de Test Postman (Workflow & CRUD)](tests/api/postman/POSTMAN_GUIDE.md)

### Scripts de Test
Des scripts bash sont également disponibles pour tester les différents rôles et workflows :
- `tests/api/scripts/demo_roles.sh` : Teste les permissions des rôles Admin, Citoyen, Collecteur et Recycleur.

Pour exécuter les scripts :
```bash
chmod +x tests/api/scripts/demo_roles.sh
./tests/api/scripts/demo_roles.sh
```