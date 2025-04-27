## Инструкция для запуска процедуры автотестирования сервиса покупки туров

1. Запустить контейнеры СУБД MySQl, PostgerSQL и Node.js командой в терминале:

> docker-compose up

2. Запустить SUT в терминале при помощи команды:

- для MySQL:

> java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

- для PostgreSQL:

> java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

3. Убедиться в готовности системы. Приложение должно быть доступно по адресу:

> http://localhost:8080/

4. Запуск тестов:

- для MySQL:

> ./gradlew test

- для PostgreSQL:

> ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

5. Для генерации отчёта Allure Report по результатам тестирования и автоматическое открытие отчета в браузере по умолчанию.

> ./gradlew allureServe