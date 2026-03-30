# Guide de Test Postman - EcoMada Collect (Version Automatisée)

Ce guide explique comment utiliser la collection Postman **automatisée** pour tester les fonctionnalités de l'API EcoMada Collect sans avoir à gérer manuellement les jetons d'authentification.

## Prérequis
1.  **Lancer l'application** : Assurez-vous que le serveur Spring Boot tourne sur le port `8090`.
2.  **Importer la collection** : Importez le fichier [ecomada-collect.postman_collection.json](ecomada-collect.postman_collection.json).
3.  **Variables** : Tout est pré-configuré via les variables de collection.

---

## 1. Automatisation de l'Authentification (Setup)
Il n'est plus nécessaire de se connecter manuellement pour chaque rôle.

1.  Ouvrez le dossier **0. Setup & Authentification**.
2.  Lancez la requête **Login All (Automatisation Totale)**.
3.  **Résultat** : Un script en arrière-plan connecte simultanément l'Admin, le Citoyen, le Collecteur et le Recycleur. Les tokens sont stockés dans les variables `{{admin_token}}`, `{{citoyen_token}}`, etc.

*Désormais, chaque dossier de rôle utilisera automatiquement le bon jeton.*

---

## 2. Tester le Workflow Complet (Sans Reconnexion)
Vous pouvez maintenant enchaîner les requêtes dans l'ordre du cycle de vie des déchets :

1.  **Citoyen** : Dossier `2. Rôle : Citoyen` -> `Step 1 : Faire un dépôt`.
2.  **Collecteur** : Dossier `3. Rôle : Collecteur` -> `Step 2 : Ramasser` et `Step 3 : Livrer`.
3.  **Recycleur** : Dossier `4. Rôle : Recycleur` -> `Step 4 : Valoriser`.

*L'authentification est gérée de manière transparente au niveau des dossiers.*

---

## 3. Vérification des Status Codes
Chaque requête de la collection inclut désormais un test automatique de validité (assertion).

*   **Test automatique** : Postman vérifie que la réponse est soit `200 OK` soit `201 Created`.
*   **Visualisation** : Après chaque envoi, consultez l'onglet **Test Results** de Postman. S'il est vert (PASS), la requête s'est déroulée comme prévu.

---

## 4. Tester la Sécurité (RBAC)
Le système d'automatisation renforce la démonstration de la sécurité :
*   Si vous déplacez une requête d'administration dans le dossier **Rôle : Citoyen**, elle échouera avec une erreur `403 Forbidden` car elle tentera d'utiliser le `citoyen_token` automatiquement.
