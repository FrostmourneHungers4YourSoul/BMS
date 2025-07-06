# Book Management Service
REST-приложение на Spring Boot для управления книгами и авторами.

---
### Сборка и запуск
- Клонируйте проект: git clone https://github.com/FrostmourneHungers4YourSoul/BMS
- Перейдите в директорию проекта: cd [ваша папка с проектом]
- Запустите приложение командой:
```bash
      cd book-management-service
      mvn clean package
      mvn spring-boot:run
```

---

### Стэк
   - Java 17
   - Spring Boot 3 
   - in-memory DB (H2)
   - Maven
   - Lombok

---

### Функциональность
#### Книги
- `POST   /books` — добавить книгу
- `GET    /books` — получить список всех книг
- `GET    /books/{id}` — получить книгу по ID
- `PUT    /books/{id}` — обновить информацию о книге
- `DELETE /books/{id}` — удалить книгу

#### Авторы
- `POST   /authors` — добавить автора
- `GET    /authors?page=0&size=10` — получить список авторов с пагинацией
- `GET    /authors/{id}` — получить автора по ID

