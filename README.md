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



-----


------

## Prérequis

**1. Créer une base de données MySQL**

Dans ce projet, la base de données 'minorease' doit être créée avant de lancer l'application.

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


### Tests unitaires

Dans ce test unitaire, nous évaluons la classe RatingService. 
Nous utilisons les frameworks Mockito et JUnit pour réaliser le test. 
Voici un résumé des différents cas de test couverts :

- Test de la méthode laisserCommentaire() avec un commentaire vide : 
Nous créons un objet Parent, un objet Hotel et un commentaire vide. 
Nous nous attendons à ce qu'une exception de type IllegalArgumentException soit lancée lors de l'appel à la méthode ratingService.laisserCommentaire(). 
Nous vérifions également qu'aucune interaction n'a eu lieu avec le ratingRepository.
