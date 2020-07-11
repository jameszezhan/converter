
# Build converter-api image
cd converter
docker build -t localhost:5000/converter-api .

# Build converter-gui image
cd ../converter-gui
docker build -t localhost:5000/converter-gui .

# Deploy stack
cd ../docker
docker stack deploy -c converter-service.yml converter