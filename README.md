BookingVacation
BookingVacation est une application qui permet de réserver une location, d'accéder aux informations de l'utilisateur connecté, et de gérer les locations.

# Environnement
L'application a été développée en Java avec le Framework SpringBoot, Spring Data JPA ainsi que Spring Security.
C'est une API REST organisée en couches, avec une interface utilisateur développée en Angular pour interagir avec cette API.

# Utilisation de l'API
Configuration de la base de données :
Commencez par créer une base de données et connectez-la à l'API via le fichier application.properties.

Création des entités :
Créez les entités nécessaires pour générer les différentes tables dans la base de données.

Dépendances Maven :
Dans le fichier pom.xml, ajoutez les dépendances nécessaires telles que Spring Security, Spring Data JPA, OpenAPI, ModelMapper, et MySQL.

Structure des packages :

* Controller
* Service
* Repository
* Entities
* DTOs
Configuration :
Le fichier application.properties doit contenir les informations de connexion à la base de données.

# Sécurité
Gestion des rôles :
Attribuez des rôles aux utilisateurs pour limiter l'accès à certaines pages ou requêtes spécifiques.

Utilisation des DTOs :
Les DTOs sont utilisés pour communiquer avec les controllers, ajoutant ainsi une couche de sécurité.

Authentification :
Implémentez la génération de jetons (tokens) et configurez un filtre de sécurité pour permettre aux utilisateurs de se connecter en toute sécurité.

# Frontend
Démarrage de l'application Angular :
Lancez l'application Angular avec la commande ng serve dans le terminal.

Enregistrement d'un utilisateur :
Cliquez sur le lien "Register" pour créer un nouvel utilisateur.

Gestion des locations :
Une fois enregistré, l'utilisateur sera dirigé vers la page de gestion des locations, où il pourra créer une location et envoyer des messages.

