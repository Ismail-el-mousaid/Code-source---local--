#Première étape de la construction d'une image angulaire
FROM node:latest as build
RUN mkdir -p /app

WORKDIR /app

COPY . .
#COPY package.json /app/

RUN npm install --force

#COPY . /app/

RUN npm run build --prod

#deploy in web server 'nginx'
FROM nginx:alpine
COPY --from=build /app/dist/ms-front-end /usr/share/nginx/html


