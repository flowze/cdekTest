
# Task Time Tracker API

Бэкенд-сервис для учета рабочего времени сотрудников. Позволяет создавать задачи, отслеживать прогресс и фиксировать трудозатраты.

## 🛠 Технологический стек

- **Java 17** & **Spring Boot 3.x**
- **MyBatis** (ORM слой)
- **PostgreSQL** (Основная БД)
- **JWT** (JSON Web Token) — Bearer-авторизация
- **SpringDoc OpenAPI** (Swagger) — документация
- **Testcontainers** — интеграционные тесты

## ⚙️ Настройка окружения

Параметры запуска вынесены в файл `.env` в корне проекта. По умолчанию настроены следующие порты:

- **API:** `8080`
- **pgAdmin:** `5050` (Email: `admin@admin.com`, Pass: `admin`)
- **Postgres:** `5432`

## 🚀 Запуск приложения

### 1. Сборка артефакта

Приложение внутри Docker ожидает JAR-файл с конкретным именем. Выполните сборку:

```bash
mvn clean package
```

Убедитесь, что в `target/` появился файл `cdekTest-0.0.1-SNAPSHOT.jar`.

### 2. Запуск контейнеров

Убедитесь, что у вас установлен Docker и запущен Docker Desktop, затем выполните:

```bash
docker compose -f docker/docker-compose.yml --env-file docker/.env up -d --build
```

## 📝 Проверка API

### Swagger (OpenAPI)

После запуска документация доступна по адресу:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Тестовые данные (Seed)

В базу данных автоматически добавлены демонстрационные сущности для быстрой проверки:

- **Тестовый пользователь:** `testuser` / `password123`
- **Тестовая задача:** ID `1`
- **Токен:** Для доступа к защищенным эндпоинтам сначала выполните запрос `POST /api/v1/auth/login`.

## 🧪 Тестирование

Для запуска Unit и интеграционных тестов (требуется установленный Docker для Testcontainers):

```bash
./mvnw test
```

## 📬 Примеры запросов

### 🔐 Авторизация

#### POST `/api/v1/auth/login`

**Тело запроса:**
```json
{
    "username": "testuser",
    "password": "password123"
}
```

**Ответ:** `200 OK`
```json
{
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTc3NjY3MzI1MywiZXhwIjoxNzc2NzU5NjUzfQ.zDL5aW3F8gbUiyDWoXpjf3jYuGgH4CY1Qe5oFT6B3luZRXQUPxpZGkkakf0PE7Zn",
    "username": "testuser"
}
```

### 📋 Задачи

#### POST `/api/v1/tasks`
*Требуется авторизация: `Authorization: Bearer <your_jwt_token>`*

**Тело запроса:**
```json
{
  "title": "Подготовить README",
  "description": "Описать сборку, запуск и примеры запросов"
}
```

**Ответ:** `201 Created`
```json
{
    "id": 34,
    "title": "Подготовить README",
    "description": "Описать сборку, запуск и примеры запросов",
    "status": "NEW"
}
```

#### GET `/api/v1/tasks/1`
*Требуется авторизация*

**Ответ:** `200 OK`
```json
{
    "id": 1,
    "title": "Разработать API",
    "description": "Реализовать эндпоинты для учета времени",
    "status": "IN_PROGRESS"
}
```

#### PATCH `/api/v1/tasks/1/status?status=DONE`
*Требуется авторизация*

**Ответ:** `200 OK`

### ⏱️ Учёт рабочего времени

#### POST `/api/v1/time-records`
*Требуется авторизация*

**Тело запроса:**
```json
{
  "employeeId": 1,
  "taskId": 1,
  "startTime": "2026-04-20T10:00:00",
  "endTime": "2026-04-20T12:00:00",
  "workDescription": "Реализация контроллеров и мапперов"
}
```

**Ответ:** `201 Created`

#### GET `/api/v1/time-records/employee/1?start=2026-04-20T00:00:00&end=2026-04-20T23:59:59`
*Требуется авторизация*

**Ответ:** `200 OK`
```json
[
    {
        "id": 34,
        "taskId": 1,
        "startTime": "2026-04-20T10:00:00",
        "endTime": "2026-04-20T12:00:00",
        "workDescription": "Реализация контроллеров и мапперов"
    }
]
```
