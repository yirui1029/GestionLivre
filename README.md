#  Gestion des livres – Tests Unitaires, Intégration & Composants

##  Objectif du projet

Ce projet a pour but d’apprendre à mettre en place différents types de tests dans une application Spring Boot :

* Tests unitaires
* Tests d’intégration
* Tests de composants (Cucumber)

L’objectif est de comprendre le rôle de chaque type de test et comment ils s’articulent dans une architecture propre.

---

##  Stack technique

* Kotlin
* Spring Boot
* JUnit 5
* Kotest
* Cucumber
* RestAssured
* H2 Database (in-memory)

---

## Types de tests

###  Tests unitaires

* Testent une classe isolée (ex : `BookService`)
* Utilisent des mocks
* Rapides et simples

---

###  Tests d’intégration

* Testent plusieurs couches ensemble (service + repository)
* Utilisent une vraie base de données (H2 ici)
* Vérifient le bon fonctionnement global

---

### Tests de composants (Cucumber)

* Tests fonctionnels basés sur des scénarios (BDD)
* Simulent des appels HTTP via RestAssured
* Vérifient le comportement de l’application comme un utilisateur

---

###  Lancer tous les tests

```bash
./gradlew test
```

---

### Lancer les tests d’intégration

```bash
./gradlew testIntegration
```

---

### Lancer les tests Cucumber (component)

```bash
./gradlew testComponent
```

---

## Base de données

Ce projet utilise **H2 (base en mémoire)** :

 Pourquoi H2 et pas PostgreSQL ?

Au départ, l’objectif était d’utiliser Testcontainers avec PostgreSQL.

Cependant :

*  Problèmes de configuration Docker avec WSL
* Conteneurs non démarrés correctement
* Instabilité locale
 Pour garantir des tests fonctionnels et simples, H2 a été choisi.


##  Fonctionnalités

* Ajouter un livre
* Lister les livres
* Réserver un livre
* Rendre un livre

---

 Points importants

* Repository en mémoire pour simplifier les tests
* Séparation claire des types de tests
* Utilisation de Cucumber pour les scénarios métiers



##  Limitations

* Pas de base PostgreSQL en local (problème Docker/WSL)
* Repository en mémoire (pas persistant)

---

##  Objectif pédagogique

Ce projet permet de comprendre :

* la différence entre les types de tests
* comment structurer un projet testable
* comment tester une API REST de bout en bout


Projet réalisé dans un objectif d’apprentissage des tests en Java/Kotlin avec Spring Boot.


