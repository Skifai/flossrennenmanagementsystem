# Stage 1: Build the application using Maven and GraalVM
FROM ghcr.io/graalvm/native-image-community:21 AS build
WORKDIR /app

# Install Node.js, npm, and other build dependencies (required for Vaadin production build)
# The community image is based on Oracle Linux
RUN microdnf install -y nodejs npm findutils ca-certificates

# Copy the maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY package.json .
COPY package-lock.json .
COPY tsconfig.json .
COPY types.d.ts .
COPY vite.config.ts .

# Ensure mvnw is executable
RUN chmod +x mvnw

# Download dependencies (cache layer)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Build the native image
# We use the 'production' and 'native' profiles defined in pom.xml
RUN ./mvnw package native:compile -Pnative,production -DskipTests

# Stage 2: Create the final image
# Using a slim base image for the runtime
FROM debian:bookworm-slim
WORKDIR /app

# Install PostgreSQL and Supervisor
RUN apt-get update && \
    apt-get install -y postgresql-15 supervisor libstdc++6 zlib1g bash && \
    rm -rf /var/lib/apt/lists/*

# Setup PostgreSQL data directory and permissions
RUN mkdir -p /var/lib/postgresql/data /var/run/postgresql && \
    chown -R postgres:postgres /var/lib/postgresql /var/run/postgresql

# Copy the native binary from the build stage
COPY --from=build /app/target/flossrennen-management-system /app/flossrennen-management-system

# Copy scripts and configuration
COPY start-postgres.sh /app/start-postgres.sh
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
RUN chmod +x /app/start-postgres.sh

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=production

# Expose the application port
EXPOSE 8080

# Run supervisor to manage both PostgreSQL and the App
ENTRYPOINT ["/usr/bin/supervisord", "-c", "/etc/supervisor/conf.d/supervisord.conf"]
