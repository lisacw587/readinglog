# Use an official JDK 21 image
FROM eclipse-temurin:21-jdk

# Set working directory inside container
WORKDIR /app

# Copy project files into container
COPY . .

# Build the app (skip tests for faster deploys)
RUN ./mvnw clean package -DskipTests

# Run the Spring Boot jar (Render will provide $PORT)
CMD ["sh", "-c", "java -jar target/*.jar --server.port=$PORT"]
