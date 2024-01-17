# MovieTonight
Aplikacja dobierająca filmy
Żeby odpalić apke musicie:
1. Stworzyć katalog i dać do niego plik certyfikatu root.crt
mkdir -p $env:appdata\postgresql\; Invoke-WebRequest -Uri https://cockroachlabs.cloud/clusters/1e2bba84-d4f7-469e-b38a-6885bbd6145a/cert -OutFile $env:appdata\postgresql\root.crt
Aplication Properties 
spring.datasource.url=jdbc:postgresql://big-tamarin-13008.8nj.gcp-europe-west1.cockroachlabs.cloud:26257/defaultdb?sslmode=verify-full&sslrootcert=C:/Users/Bombolel/AppData/Roaming/postgresql/root.crt
spring.datasource.username=bajojajo
spring.datasource.password=34hyNKk8X6spn7zZStUVbg
spring.datasource.driverClassName=org.postgresql.Driver
spring.jpa.database-platform = org.hibernate.dialect.PostgreSQLDialect
spring.jpa.open-in-view=true
Ścieżki do root.crt odowiednio zmieńcie dla swojego kompa

