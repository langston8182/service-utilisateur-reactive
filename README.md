# Version

- **1.0.0-SNAPSHOT** : initialisation du projet

# Objectif

Serveur de resources permettant de gérer les utilisateurs de l'application bancaires.
\
Utilise les verbes CRUD pour la gestion des utilisateurs.

# Technique

Nécéssite une connection oauth2 et d'un jeton JWT pour fonctionner :
https://github.com/langston8182/bank-oauth2-authorization-server
Il faut avant tout démarrer le serveur d'autorisation.

Port d'écoute : 8100

**Postman** :
Se loguer pour récupérer le token JWT

![postman](images/postman.png)

# Docker

Utiliser docker-compose pour lancer le serveur.
`docker-compose up`

# Kubernetes
L'ensemble des configurations k8s se trouvent dans le projet Gihub\
https://github.com/langston8182/kubernetes-bank

Pour lancer le service
`kubectl create -f utilisateur-service.yaml`

Pour lancer le deploiement
`kubectl create -f utilisateur-deployment.yaml`

# Maven

Pour compiler le projet :
\
`mvn clean install`

Pour construire l'image Docker :
\
`mvn dockerfile:build`

Pour push l'image sur dockerHub, exécuter:
\
`mvn dockerfile:push`

# Concourse
Concourse est un outil pour faire du développement continue. A l'instar de Jenkins, 
il s'agit d'un pipeline as code. Fly est un interpreteur de commandes pour manipuler les pipelines concourse.

1) Installation :
https://github.com/concourse/concourse-docker

2) commandes fly:
    \
    - Créer un token de connexion\
    `fly login --concourse-url http://127.0.0.1:8080 -u test -p test`
    
    - Lister les targets
    `fly targets`\
    Pour chaque commande qui va suivre il faut suivre fly du target (fly -t tutorial).
    
    - Créer un alias fly\
    `alias fly="fly -t tutorial"`
    
    - Créer un pipeline a partir d'un fichier de configuration\
    `fly sp -p service-utilisateur -c ci/pipeline.yml -l credentials.yml`

3) Utilisation :

Pour le projet, un triger est lance dès que un commit est fait sur la branche master.
Le build du projet a lieu (mvn clean install), puis a l'issue de ce build, une image docker du jar est crée.
Cette image est ensuite déployé sur le registry docker.



# Configuration

Dans le fichier application.yml modifier les adresses Ip suivant si le serveur tourne en local ou dans un container.

Ip du serveur autorisation dans un container : 172.21.0.1

Ip du servuer autorisation dans k8s : authorization-service

# Contributeur

Cyril Marchive (cyril.marchive@gmail.com)
