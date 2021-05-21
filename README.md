# IDATT2105-Prosjekt
 
## Krav for å kjøre applikasjonen
### Backend
- [Maven](https://maven.apache.org/download.cgi)
- [Docker](https://docs.docker.com/get-docker/)

### Frontend
- [Yarn](https://classic.yarnpkg.com/en/docs/install/#debian-stable):
```bash
npm install -g yarn
```

## Installasjon

```bach
git clone https://github.com/stianmogen/IDATT2105-Prosjekt.git
```

### Backend

```bash

cd IDATT2105-Prosjekt\backend

# With Maven  
#The application runs on port 8080
docker-compose up & mvn dependency:resolve && mvn spring-boot:run

# With Docker-compose
docker-compose -f docker-compose.yml build
docker-compose -f docker-compose.yml up

```

### Frontend

```bash

cd IDATT2105-Prosjekt\frontend

# Set url to api in env-file
echo REACT_APP_API_URL=http://localhost:8080/api/  > .env

# Install dependencies
yarn 

# Run the app
yarn start
```
