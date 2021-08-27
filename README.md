# dondusang

Il s'agit d'un ancien projet commencé en **2018** d'une application Android à propos du don du sang avec les fonctionnalités suivantes:
* consultation des prochains dons du sang autour d'une localisation (Google Map)
* géolocalisation de l'utilisateur
* informations sur le centre de don
* historique des dons du sang effectués par l'utilisateur
* date du prochain don possible (sur la base des données de l'EFS)
* ajouter un centre en favoris
* ajouter une date de don dans Google Agenda depuis l'application
* quiz permettant de vérifier si un don peut être fait (sur la base des données de l'EFS)
* notification de rappel de don possible quand c'est le cas

Ce projet utilise entre autres les technologies suivantes:
    - Java
    - Architecture MVVM
    - Crashlytics
    - Google Map
    - Databinding

Pour les données EFS, j'ai fait de la rétro ingéniérie afin de pouvoir utiliser leur API non documentée.

Ce projet est actuellement en suspend, j'espère pouvoir le reprendre un jour avec dans l'idée de le refactoriser.

Axe d'amélioration:
- Refactorisation en utilisant Kotlin
- Utilisation de nouvelles fonctionnalités proposées par Android (Compose, Navigation Component etc... )
- Revoir tout le design

Screenshot de l'application:

<img src="https://i.imgur.com/2m8UiMy.png" width="200">
<img src="https://i.imgur.com/T01gnts.png" width="200">
<img src="https://i.imgur.com/nx4nbZ4.png" width="200">
<img src="https://i.imgur.com/Q3MBWRs.png" width="200">
<img src="https://i.imgur.com/TU5ymBA.png" width="200">

