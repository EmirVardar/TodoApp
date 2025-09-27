# ---- Runtime image (küçük ve hızlı) ----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Build sonrası oluşan jar'ı kopyala
COPY target/*.jar app.jar

# (Opsiyonel) JVM bellek ayarı
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"

EXPOSE 8080

# Uygulamayı başlat
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
