------------------------------------------------------------------

СБОРКА
----
mvn clean install

------------------------------------------------------------------

СОЗДАНИЕ БД
----
#### Выполнить один раз, перед запуском

create database wordgamedb; -- Creates the new database

create user 'wordgamedbuser'@'%' identified by 'password'; -- Creates the user

grant all on wordgamedb.* to 'wordgamedbuser'@'%'; -- Gives all privileges to the new user on the newly created database

ALTER DATABASE wordgamedb character set utf8mb4 collate utf8mb4_bin

------------------------------------------------------------------
ЗАПУСК
----

###### Штатный запуск.

"C:\Program Files\Java\jdk-16.0.2\bin\java" -jar wordgame-1.0.0-SNAPSHOT.jar --server.port=8051 --spring.datasource.url=jdbc:mysql://localhost:3306/wordgamedb --spring.datasource.username=wordgamedbuser --spring.datasource.password=password

###### Запуск с нагрузочным тестом.

"C:\Program Files\Java\jdk-16.0.2\bin\java" -jar wordgame-1.0.0-SNAPSHOT.jar --server.port=8051 --spring.datasource.url=jdbc:mysql://localhost:3306/wordgamedb --spring.datasource.username=wordgamedbuser --spring.datasource.password=password --rfc.buffered.count=5000

###### Запуск с генератором игроков.

"C:\Program Files\Java\jdk-16.0.2\bin\java" -jar wordgame-1.0.0-SNAPSHOT.jar --server.port=8051 --spring.datasource.url=jdbc:mysql://localhost:3306/wordgamedb --spring.datasource.username=wordgamedbuser --spring.datasource.password=password --rfc.player.count=5000


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


