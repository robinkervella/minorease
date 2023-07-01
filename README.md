# Minorease

![Accueil](https://zupimages.net/up/23/26/n52t.png)

## Description du projet

Cette application a été réalisée dans le cadre d'un projet de fin de formation.
Minorease permet au représentant légal d'un ou plusieurs mineurs d'effectuer une réservation dans un hôtel
tout en s'assurant que l'enfant soit bien accueilli dans l'établissement.

Côté Back-end, le site utilise les technologies suivantes : Spring Boot MVC,
Thymeleaf, Validation, Spring Web, Spring Boot Security et Spring Data JPA.

## Langages et Framework utilisés

![Spring Boot Badge](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=for-the-badge)

![Image](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

![Image](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white)


![MySQL Badge](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=fff&style=for-the-badge)

![Image](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white)

![Hibernate Badge](https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=fff&style=for-the-badge)

![Image](https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white)


![Thymeleaf Badge](https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf&logoColor=fff&style=for-the-badge)

![Image](https://img.shields.io/badge/JavaScript-323330?style=for-the-badge&logo=javascript&logoColor=F7DF1E)



--------

## Documentations officielles 

L'application est développée avec Spring Boot (voir le détail des packages
utilisés [ici](/HELP.md)).

* [Thymeleaf](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#web.servlet.spring-mvc.template-engines)
* [Validation](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#io.validation)
* [Spring REST Docs](https://docs.spring.io/spring-restdocs/docs/current/reference/html5/)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#web)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.5/reference/htmlsingle/#data.sql.jpa-and-spring-data)

## Parcours utilisateur représentant légal

![Utilisateur](https://zupimages.net/up/23/26/w90d.png)

On retrouve ici le parcours utilisateur (dans le cas de notre projet, celui du représentant légal).
Le parent doit pouvoir réserver un hôtel pour un mineur,


## Fonctionnalités

**1. Page d'accueil**

Notre site affiche sur la page d'accueil une barre de recherche permettant aux utilisateurs de saisir la ville, 
les dates d'arrivée et de départ, ainsi que le nombre de voyageurs pour leurs séjours à l'hôtel.
Un peu plus bas sur la même page, vous trouverez les objectifs de Minorease ainsi que les valeurs qu'ils défendent.

![Accueil](https://zupimages.net/up/23/26/n52t.png)

------

**2. Page des hôtels par ville et par date sélectionnées**

![PageHôtels](https://zupimages.net/up/23/26/3u6b.png)

Sur cette page, vous trouverez les hôtels disponibles dans la ville sélectionnée pendant la période de temps choisie.
En cliquant sur "Go", on se retrouve sur la page des choix de chambres.


-----

**3. Page de l'hôtel et choix du type de couchage**


![HôtelLits]()

Sur cette page, l'utilisateur aura la possibilité de choisir le nombre de lits souhaité. 
En cliquant sur le bouton "Réserver", il sera redirigé vers la page récapitulative de la réservation.
------

![PageRécap]()

Sur cette page l'utilisateur va venir finaliser sa réservation, pour cela 
il devra renseigner ces informations ainsi que celle du mineur 

------

![AddArticleOk](https://zupimages.net/up/23/24/q6r8.png)

-----

**3. Modifier / Supprimer un article**

Seul l'administrateur a les droits de modifier ou de supprimer un article.
Lorsqu'il clique sur 'Modifier', un formulaire reprenant le contenu de l'article en question s'affiche et il peut effectuer les modifications avant d'appuyer sur 'Enregistrer

![UpdateArticle](https://zupimages.net/up/23/24/x2fo.png)

S'il clique sur 'Supprimer', un message de confirmation apparaîtra avant la suppression définitive de l'article.

![DeleteArticle](https://zupimages.net/up/23/24/kbyu.png)

------

## Prérequis

**1. Créer une base de données MySQL**

Dans ce projet, la base de données 'blog_wow' doit être créée avant de lancer l'application.
Cependant, les tables 'article', 'authorities', 'commentaire' et 'users' n'ont pas besoin d'être créées au préalable, car elles seront générées automatiquement à partir du code.

**2. Cloner le repository**

Vous pouvez consulter la documentation correspondante en cliquant sur le lien suivant :
https://docs.github.com/fr/repositories/creating-and-managing-repositories/cloning-a-repository.
Elle contient toutes les informations nécessaires pour cloner le dépôt.

**3. Modification de application.properties**

Pour pouvoir vous connecter à votre base de données,
vous devrez adapter votre configuration dans le fichier 'application.properties' situé dans resources/application.properties :

Exemple pour MySQL :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/blog_wow
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Pour modifier le port du serveur (par défaut 8080), vous devez modifier le même fichier :
```properties
server.port=8081
```

### API

Par défaut l'API est documentée sur l'URL `http://localhost:8081/swagger-ui.html`.

![Swagger](https://zupimages.net/up/23/24/ksmo.png)

- Les services RESTFul (CRUD) pour les articles sont disponibles sur l'URL
  `/api/articles`.

### Tests unitaires

Un test unitaire a été réalisé sur la méthode articles() de notre classe ArticleController.
Nous utilisons Mockito et JUnit pour ce test.
Nous créons deux objets Article de test avec des valeurs fictives.
Ces objets sont ensuite ajoutés à une liste fictive articles.
Le comportement du mock mRepository est défini pour renvoyer la liste articles lorsque la méthode findAll() est appelée.
Nous appelons la méthode articles() de notre ArticleController pour obtenir le résultat, puis nous vérifions la taille de la liste, l'ID, le titre, la description, etc. de chaque article.

Cela nous permet de vérifier si la méthode articles() renvoie bien la liste d'articles attendue en utilisant le mock mRepository.
