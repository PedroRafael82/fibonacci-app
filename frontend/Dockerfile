### Frontend multi-stage Dockerfile
# Build stage: build static assets with Node
FROM node:20-alpine AS build
WORKDIR /app

# copy package files and install
COPY package.json package-lock.json* ./
RUN if [ -f package-lock.json ]; then npm ci; else npm install; fi

# copy source and build
COPY . .
RUN npm run build

# Runtime stage: serve with nginx
FROM nginx:1.26-alpine
COPY --from=build /app/dist /usr/share/nginx/html

# minimal nginx config to serve SPA
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
