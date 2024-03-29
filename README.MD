## This is a fullstack project for migrating music in YouTube Music to Spotify.

# Tech stack:
<!-- * [NGINX](https://docs.nginx.com/nginx/admin-guide/web-server/reverse-proxy/) as a proxy server -->
* [Vue](https://github.com/vuejs/vue) for web gui
* [Spring Boot](https://spring.io/guides/gs/spring-boot/) for server API
* [Docker Stack](https://docs.docker.com/engine/reference/commandline/stack/) for deployment

# How to run locally
On production mode, all web content (vue js) will be served from spring.
But locally, we can run backend and frontend as two saperate services on port 8080(FE) and port 8089(BE).
## Backend
* git clone project
* Edit `converter/src/main/resources/application.properties` and update active profile to `dev`
* Run application.

## Frontend
* `cd converter-gui`
* `npm run serve`
* env vars will be served from `.env.development`, instead of `.env.production`.

# How to build
* git clone project
* copy application.yml to application-dev.yml
* Update Google and Spotify api keys and secrets
* `mvn clean install`
* `mvn --projects converter spring-boot:run`

# How to run this project (Deprecated)
Prerequsite for running this project:
* Docker Swarm 
  
  To run this project, you need to have Docker installed and use Docker in Swarm Mode. You can find more information how to run a single node Docker Swarm [here](https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/).
* YouTube Data API authorization credentials
  
  You will need a [YouTube Data API](https://developers.google.com/youtube/registering_an_application) OAuth crendentials for run this project as it uses YouTube Data API. Remember to whitelist `http://localhost:${API_PORT}/api/v1/youtube` on your Google API Console.
  https://i.imgur.com/TPK7XKz.png

* Spotify API authorization credentials
  
  Same as above, you will need a [Spotify Data API](https://developer.spotify.com/dashboard/) OAuth crendentials for run this project as it uses Spotify API. Remember to whitelist `http://localhost:${API_PORT}/api/v1/spotify` on your Google API Console.


To run the application, [download the compose file](https://github.com/jameszezhan/converter/blob/master/docker/converter-service.yml) and change the following entries to your own:
* GOOGLE_CLIENT_ID
* GOOGLE_CLIENT_SECRET
* SPOTIFY_CLIENT_ID
* SPOTIFY_CLIENT_SECRET

After chaning the credentials, run: 

`docker stack deploy -c converter-service.yml cs`

This application will take up port 8081 (web gui) and 8080 (server).

If you want to use a different port for either webgui or api server, go into the docker compose file and change the forwarding port for **apiservice** and/or **webgui**


Then visit `localhost:8081` or the port you specified.

You can read more about the project [here](https://medium.com/@zezhanchen/fullstack-with-vue-spring-boot-and-nginx-d22d13898d3e).

If you want to play with application without running it, you can visit https://zeehon.us/converter.



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


https://github.com/jonashackt/spring-boot-vuejs