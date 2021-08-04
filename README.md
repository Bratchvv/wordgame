------------------------------------------------------------------

СБОРКА
----
mvn clean install

------------------------------------------------------------------
ЗАПУСК
----

###### Штатный запуск.

"C:\Program Files\Java\jdk-16.0.2\bin\java" -jar wordgame-1.0.0-SNAPSHOT.jar --server.port=8051 --spring.datasource.url=jdbc:postgresql://localhost:5432/wordgamedb --spring.datasource.username=postgres --spring.datasource.password=postgres

###### Запуск с нагрузочным тестом.

"C:\Program Files\Java\jdk-16.0.2\bin\java" -jar wordgame-1.0.0-SNAPSHOT.jar --server.port=8051 --spring.datasource.url=jdbc:postgresql://localhost:5432/wordgamedb --spring.datasource.username=postgres --spring.datasource.password=postgres --rfc.buffered.count=5000

###### Запуск с генератором игроков.

"C:\Program Files\Java\jdk-16.0.2\bin\java" -jar wordgame-1.0.0-SNAPSHOT.jar --server.port=8051 --spring.datasource.url=jdbc:postgresql://localhost:5432/wordgamedb --spring.datasource.username=postgres --spring.datasource.password=postgres --rfc.player.count=5000


ССЫЛКИ
--

http://localhost:8051/                     - Админка
http://localhost:8051/swagger-ui.html      - Swagger
http://localhost:8051/api-docs             - Swagger JSON

###### Доступы (по умолчанию):

##### СУБД

jdbc:postgresql://localhost:5432/wordgamedb
username: postgres
password: postgres

##### Админка

username: admin
password: admin


