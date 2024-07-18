> [!NOTE]
>Ce fichier est un guide pour installer et tester l'application MDD  (Monde de Dév). C'ette application est composé d'une API (back-end) et d'une UI Web (Front-end). Bonne installation !

<h1>TABLE DES MATIERES:</h1>

- [1 - INSTALLATION DE LA BASE DE DONNÉE](#1---installation-de-la-base-de-donnée)
  - [Installer MySQL (MacOS)](#installer-mysql-macos)
  - [Installer MySQL (Windows)](#installer-mysql-windows)
- [2 - Création de la base de donnée de production (MacOS et Windows)](#2---création-de-la-base-de-donnée-de-production-macos-et-windows)
- [3 - Demarrer le projet](#3---demarrer-le-projet)
  - [Cloner le projet en local](#cloner-le-projet-en-local)
  - [Demarrer l'API (Back-End)](#demarrer-lapi-back-end)
    - [Installez Maven (MacOs)](#installez-maven-macos)
    - [Installez Maven (Windows)](#installez-maven-windows)
    - [Configurez les informations de connexion à la base de données dans des variables d'environnement ainsi que le JWT\_KEY (DB\_USERNAME, DB\_PASSWORD, JWT\_KEY).](#configurez-les-informations-de-connexion-à-la-base-de-données-dans-des-variables-denvironnement-ainsi-que-le-jwt_key-db_username-db_password-jwt_key)
    - [Demarrer Spring-Boot](#demarrer-spring-boot)
  - [Demarrer le site web (Front-End)](#demarrer-le-site-web-front-end)
- [4 - Sécurité](#4---sécurité)
  - [Utilisation du framework Spring Security et de JSON Web Token (JWT)](#utilisation-du-framework-spring-security-et-de-json-web-token-jwt)
    - [Gestion des tokens et de la sécurité](#gestion-des-tokens-et-de-la-sécurité)
- [NOTE DE VERSIONS](#note-de-versions)
    - [Angular](#angular)
    - [VERSION DES PACKAGES](#version-des-packages)

# 1 - INSTALLATION DE LA BASE DE DONNÉE
La base de donnée est une base Open Source MySQL (SQL, « Structured Query Language » est un langage standardisé utilisé
pour gérer et manipuler les bases de données relationnelles).

## Installer MySQL (MacOS)
- Pour installer MySQL sur un Mac, vous pouvez utiliser Homebrew, un gestionnaire de paquets pour macOS. 
- Installer Homebrew:
  ```/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"```
  
- Installer MySQL: ```brew services start mysql```
- Démarrer le service MySQL: ```brew services start mysql```
- Se connecter à la base de donnée: ```mysql -u root -p```
  *(Cela vous demandera de saisir le mot de passe de l'utilisateur root. Par défaut, le mot de passe est vide, donc vous pouvez simplement appuyer sur Entrée.)*
- Definissez un mot de passe pour l'utilisateur root:```ALTER USER 'root'@'localhost' IDENTIFIED BY 'password';``` puis: ```FLUSH PRIVILEGES;```
  *(remplacez password par votre mot de passe)*

## Installer MySQL (Windows)
- Allez sur le site de téléchargement de MySQL : https://dev.mysql.com/downloads/installer/
- Télécharger la version "MySQL Installer for Windows".
- Une fois le téléchargement terminé, ouvrez le fichier d'installation.
- Suivez les instructions de l'installateur.
- Une fois l'installation terminée, vous pouvez démarrer le serveur MySQL en utilisant le service MySQL dans le Panneau de configuration de Windows.

# 2 - Création de la base de donnée de production (MacOS et Windows)
- Ovrez une invite de commande (Windows) ou une fenetre de terminal (MacOS).
- Se connecter à la base de donnée: ```mysql -u root -p```
  *(Cela vous demandera de saisir le mot de passe de l'utilisateur root.)*
- Créez la base de donnée "ocp6": ```CREATE DATABASE ocp6;```(Ceci est la base de PRODUCTION !)
- Selectionnez la base de donnée créé: ```USE ocp6;```
- Créez les TABLES en utilisant les scripts SQL fournis: ```source ressources/sql/<nom_du_script>.sql;``` *(remplacer par le chemin vers votre fichier de script "<nom_du_script>.sql")*
- Vous pouvez maintenant quitter MySQL en tapant `exit` et en appuyant sur Entrée.

# 3 - Demarrer le projet
## Cloner le projet en local
- Naviguez vers le répertoire où vous voulez cloner le dépôt GitHub.
- Une fois dans le répertoire souhaité, tapez la commande suivante et appuyez sur Entrée:
  
   `git clone https://github.com/DiD-BDX/OCP6-Dev-App-Full-Stack-Complete.git`

Maintenant, le dépôt GitHub devrait être cloné dans un nouveau dossier dans le répertoire actuel. Ce dossier aura le même nom que le dépôt.

Vous y trouverez 2 sous-dossiers, un pour le Front-End "front" et un pour le Back-End "back".

## Demarrer l'API (Back-End)

### Installez Maven (MacOs)
- Installez Maven si il n'est pas deja installé à l'aide de Homebrew: `brew install maven`
- Pour vérifier que Maven est bien installé, vous pouvez exécuter la commande suivante : `mvn -v`

### Installez Maven (Windows)
- Ouvrez une invite de commande en mode administrateur.
- Installez Maven si il n'est pas deja installé à l'aide de Chocolatey (par exemple): ```Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://chocolatey.org/install.ps1'))```
- Une fois Chocolatey installé, vous pouvez installer Maven en exécutant la commande suivante: `choco install maven`
- Pour vérifier que Maven est bien installé, vous pouvez exécuter la commande suivante : `mvn -v`

### Configurez les informations de connexion à la base de données dans des variables d'environnement ainsi que le JWT_KEY (DB_USERNAME, DB_PASSWORD, JWT_KEY).
- Pour MacOS, vous pouvez ajouter ces variables dans le fichier `~/.bash_profile` ou `~/.zshrc` en ajoutant ces lignes:
  
  ```export DB_USERNAME=root```
  
  ```export DB_PASSWORD=votre-mot-de-passe```
  
  ```export JWT_KEY=votre-cle-secrete```
  
  *(remplacer "votre-mot-de-passe" et "votre-cle-secrete" par vos informations)*

- Pour Windows, vous pouvez ajouter ces variables dans les variables d'environnement du système avec l'invite de commande PowerShell:
  
  ```[Environment]::SetEnvironmentVariable("DB_USERNAME", "root", "Machine")```
  
  ```[Environment]::SetEnvironmentVariable("DB_PASSWORD", "votre-mot-de-passe", "Machine")```
  
  ```[Environment]::SetEnvironmentVariable("JWT_KEY", "votre-cle-secrete", "Machine")```
  
  *(remplacer "votre-mot-de-passe" et "votre-cle-secrete" par vos informations)*

### Demarrer Spring-Boot
- Ouvrez un terminal (MacOS) ou une invite de commande (Windows).
- Naviguer vers le répertoire du projet que vous venez de cloner (le repertoire "back").
-  Installez les dependances: `mvn install`
-  Configurer la base de données dans le fichier `back/src/main/resources/application.properties` 
        
Verifiez ces lignes:

        `spring.datasource.url=jdbc:mysql://localhost:3306/ocp6?allowPublicKeyRetrieval=true`

        `spring.datasource.username=${DB_USERNAME}`

        `spring.datasource.password=${DB_PASSWORD}`

        `spring.jpa.hibernate.ddl-auto=update`

- Démarrer l'application: `mvn spring-boot:run`

## Demarrer le site web (Front-End)
- Naviguer vers le répertoire du projet (partie Front-End) :`cd front`
- Ouvrez une invite de commande (Windows) ou une fenetre de terminal (MacOS) dans ce repertoire.
- Installer les dépendances du projet qui sont répertoriées dans le fichier package.json: `npm install`
- Démarrer l'application en utilisant la commande `npm run start`. Cela démarrera le serveur de développement Angular.

> [!NOTE]
> *Le site MDD se lance par defaut sur http://localhost:4200/*
>
> Verifiez que tout fonctionne bien.

# 4 - Sécurité

## Utilisation du framework Spring Security et de JSON Web Token (JWT)
Le code de `WebSecurityConfig` utilise le design pattern Strategy en encapsulant la logique d'authentification et d'autorisation via JWT dans des composants distincts (AuthTokenFilter, JwtExtractor, JwtValidator). Ces composants sont configurés et intégrés dans le framework Spring Security pour gérer la sécurité de l'application de manière modulaire et interchangeable.
### Gestion des tokens et de la sécurité
Dans `application.properties`, vous pouvez configurer les paramètres de sécurité suivants:

`oc.app.jwtSecret=${JWT_KEY}` : la clé secrète utilisée pour signer les tokens JWT.

`oc.app.jwtExpirationMs=86400000` : la durée de validité des tokens JWT en millisecondes.

Pour cette version MVP, la durée de validité des tokens de 24 heures (en ms) est définie par defaut mais non utilisée pour une persistance des tokens entre les sessions.
Pour modifier cela, ajouter cette ligne dans la configuration de la sécurité dans WebSecurityConfig (methode generateJwtToken):

`.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))`


# NOTE DE VERSIONS
### Angular
`ng version`
- Angular CLI: 17.1.0
- Node: 20.12.1
- Package Manager: npm 10.8.0

### VERSION DES PACKAGES
- @angular-devkit/architect       0.1701.0
- @angular-devkit/core            17.1.0
- @angular-devkit/schematics      17.1.0
- @schematics/angular             17.1.0

