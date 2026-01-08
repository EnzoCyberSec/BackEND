# WokAndRoll API Backend

API REST développée en Java pour la gestion du restaurant WokAndRoll. Elle permet de gérer les commandes, consulter les plats et obtenir des statistiques de vente.

## 🛠 Technologies

* **Langage :** Java 11
* **Framework :** Javalin 5.6.3
* **Build :** Maven
* **Base de données :** MySQL 8.0
* **JSON :** Jackson

## 📋 Prérequis

* JDK 11 installé
* Maven installé
* Serveur MySQL en cours d'exécution

## ⚙️ Configuration de la Base de Données

Avant de lancer l'application, configurez l'accès à votre base de données dans le fichier :
`src/main/java/fr/isen/wokandroll/config/DatabaseConfig.java`

Configuration par défaut actuelle :
```java
public static final String JDBC_URL = "jdbc:mysql://10.211.55.3:3306/wokandroll";
public static final String JDBC_USER = "enzo";
public static final String JDBC_PASSWORD = "azerty";
