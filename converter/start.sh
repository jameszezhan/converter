#! /bin/sh

# start consumer
CONFIG_FILE=Application.properties

# GOOGLE CLIENT ID
if [ -z $GOOGLE_CLIENT_ID ]
then
    echo "GOOGLE_CLIENT_ID missing"
    exit 0
else 
    echo "GOOGLE_CLIENT_ID=$GOOGLE_CLIENT_ID">>$CONFIG_FILE 
fi

# GOOGLE CLIENT SECRET
if [ -z $GOOGLE_CLIENT_SECRET ]
then
    echo "GOOGLE_CLIENT_SECRET missing"
    exit 0
else 
    echo "GOOGLE_CLIENT_SECRET=$GOOGLE_CLIENT_SECRET">>$CONFIG_FILE 
fi

# GOOGLE API SCOPE
if [ -z $GOOGLE_SCOPE ]
then
    echo "GOOGLE_SCOPE missing"
    exit 0
else 
    echo "GOOGLE_SCOPE=$GOOGLE_SCOPE">>$CONFIG_FILE 
fi

# GOOGLE AUTH URL
if [ -z $GOOGLE_AUTH_URL ]
then
    echo "GOOGLE_AUTH_URL missing"
    exit 0
else 
    echo "GOOGLE_AUTH_URL=$GOOGLE_AUTH_URL">>$CONFIG_FILE 
fi

# GOOGLE TOKEN URL
if [ -z $GOOGLE_TOKEN_URL ]
then
    echo "GOOGLE_TOKEN_URL missing"
    exit 0
else 
    echo "GOOGLE_TOKEN_URL=$GOOGLE_TOKEN_URL">>$CONFIG_FILE 
fi

# GOOGLE REDIRECT URL
if [ -z $GOOGLE_REDIRECT_URL ]
then
    echo "GOOGLE_REDIRECT_URL missing"
    exit 0
else 
    echo "GOOGLE_REDIRECT_URL=$GOOGLE_REDIRECT_URL">>$CONFIG_FILE 
fi

# SPOTIFY CLIENT ID
if [ -z $SPOTIFY_CLIENT_ID ]
then
    echo "SPOTIFY_CLIENT_ID missing"
    exit 0
else 
    echo "SPOTIFY_CLIENT_ID=$SPOTIFY_CLIENT_ID">>$CONFIG_FILE 
fi

# SPOTIFY CLIENT SECRET
if [ -z $SPOTIFY_CLIENT_SECRET ]
then
    echo "SPOTIFY_CLIENT_SECRET missing"
    exit 0
else 
    echo "SPOTIFY_CLIENT_SECRET=$SPOTIFY_CLIENT_SECRET">>$CONFIG_FILE 
fi

# SPOTIFY API SCOPE
if [ -z $SPOTIFY_SCOPE ]
then
    echo "SPOTIFY_SCOPE missing"
    exit 0
else 
    echo "SPOTIFY_SCOPE=$SPOTIFY_SCOPE">>$CONFIG_FILE 
fi

# SPOTIFY AUTH URL
if [ -z $SPOTIFY_AUTH_URL ]
then
    echo "SPOTIFY_AUTH_URL missing"
    exit 0
else 
    echo "SPOTIFY_AUTH_URL=$SPOTIFY_AUTH_URL">>$CONFIG_FILE 
fi

# SPOTIFY TOKEN URL
if [ -z $SPOTIFY_TOKEN_URL ]
then
    echo "SPOTIFY_TOKEN_URL missing"
    exit 0
else 
    echo "SPOTIFY_TOKEN_URL=$SPOTIFY_TOKEN_URL">>$CONFIG_FILE 
fi

# SPOTIFY REDIRECT URL
if [ -z $SPOTIFY_REDIRECT_URL ]
then
    echo "SPOTIFY_REDIRECT_URL missing"
    exit 0
else 
    echo "SPOTIFY_REDIRECT_URL=$SPOTIFY_REDIRECT_URL">>$CONFIG_FILE 
fi

java -jar /opt/app/converter-0.0.1-SNAPSHOT.jar
