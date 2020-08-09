

## This is a fullstack project for migrating music in YouTube playlist to Spotify.

# Tech stack:
<!-- * [NGINX](https://docs.nginx.com/nginx/admin-guide/web-server/reverse-proxy/) as a proxy server -->
* [Vue](https://github.com/vuejs/vue) for web gui
* [Spring Boot](https://spring.io/guides/gs/spring-boot/) for server API
* [Docker Stack](https://docs.docker.com/engine/reference/commandline/stack/) for deployment


# How to run this project
To run this project, you need to have Docker installed and use Docker in Swarm Mode.
You can find more information how to run a single node Docker Swarm [here](https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/).

After you have Docker Swarm setup, [download the compose file](https://github.com/jameszezhan/converter/blob/master/docker/converter-service.yml) and run: 

`docker stack deploy -c converter-service.yml cs`

This application will take up port 8081 (web gui) and 8080 (server).

If you want to use a different port for either webgui or api server, go into the docker compose file and change the forwarding port for **apiservice** and/or **webgui**


Then visit `localhost:8081` or the port you specified.

You can read more about the project [here]().


# Mis Notes:
## Build converter-api image and deploy to a local docker registry
`cd ./converter`

`docker build -t localhost:5000/converter-api .`

`docker push localhost:5000/converter-api`

## Build converter-gui image and deploy to a local docker registry
`cd ../converter-gui`

`docker build -t localhost:5000/converter-gui .`

`docker push localhost:5000/converter-gui`

## Deploy stack
`cd ../docker`
`docker stack deploy -c converter-service.yml converter`
