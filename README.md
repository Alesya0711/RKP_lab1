## Цель работы
Реализовать Spring Boot приложение для обработки данных формы студента с автоматическим формированием группы и логина образовательного портала на основе введенных данных.

## Архитектура проекта
- Стек: Spring Boot, Thymeleaf, Maven
- Основные слои: 
  - Controller (`MainController`) — обработка HTTP запросов (GET/POST)
  - Model (`Student`) — POJO класс для представления данных студента
  - View (Thymeleaf templates) — рендеринг HTML страниц
- **Ключевые аннотации:** 
  - `@Controller` — обозначение класса контроллера
  - `@GetMapping` — обработка GET запросов
  - `@PostMapping` — обработка POST запросов
  - `@RequestParam` — получение параметров из запроса
  - `@ModelAttribute` — привязка данных формы к объекту
  - `th:action`, `th:object`, `th:field` — теги Thymeleaf для работы с формами

## Алгоритм работы

1. **Создание модели данных:**
   - Класс `Student` с полями: id, имя, фамилия, отчество, email, год поступления
   - Реализация геттеров и сеттеров для всех полей

2. **Обработка GET запроса:**
   - Метод `mainForm()` создает пустой объект `Student` и передает его в представление
   - Отображается форма ввода данных

3. **Работа с формой:**
   - Пользователь вводит данные: id, ФИО, email, год поступления
   - Теги `th:field` связывают поля формы с полями объекта `Student`

4. **Обработка POST запроса:**
   - Метод `mainForm(@ModelAttribute Student student)` получает заполненный объект
   - Автоматическое формирование группы: "ПИНз-1" + последние две цифры года
   - Автоматическое формирование логина: "student-pinz" + год + id
   - Объект передается на страницу результата

5. **Отображение результата:**
   - Страница `result.html` отображает все данные студента
   - Ссылка для возврата к форме ввода новых данных

## Скриншот работы приложения
Форма ввода данных
<img width="742" height="594" alt="image" src="https://github.com/user-attachments/assets/b0ad505e-bc00-48ff-b4d6-01a2105855dd" />

Страница с обработанными данными
<img width="610" height="604" alt="image" src="https://github.com/user-attachments/assets/1d9acdf8-d332-41e1-b167-019d019e4722" />




flowchart TB
    subgraph Client ["Клиентский слой (Browser)"]
        UI["Веб-интерфейс Капибанка<br/>(React 19, TypeScript, Tailwind CSS, Decimal.js)"]
    end

    subgraph Container ["Docker-контейнер (Cloud-Native Monolith)"]
        direction TB
        API["API Сервер и Статика<br/>(Python 3.12, FastAPI, Uvicorn)"]
        
        subgraph CoreLogic ["Бизнес-логика кредитного конвейера"]
            Auth["Модуль авторизации<br/>(PyJWT, Passlib)"]
            Pipeline["Движок заявок и контрактов<br/>(State Machine)"]
            Scoring["Модуль скоринга и<br/>кредитного решения"]
            Assistant["Сервис ИИ-помощника<br/>«Капибанкир» (Groq / Stub)"]
        end

        subgraph Mocks ["Локальные симуляторы и Моки (Autonomous)"]
            FNS["Мок ФНС / ЕГРЮЛ / ЕГРИП<br/>(Проверка ИНН и статуса МСП)"]
            BKI["Мок БКИ<br/>(Кредитная история и нагрузка)"]
        end

        DB[("PostgreSQL<br/>(SQLModel / SQLAlchemy / Alembic)<br/>Volume: kapibank-data")]
    end

    subgraph External ["Внешние сервисы (Опционально)"]
        Groq["Groq Cloud API<br/>(Модель: openai/gpt-oss-120b)"]
    end

    UI <-->|"REST API (JSON) / HTTP :8080"| API
    API --> Auth
    API --> Pipeline
    API --> Assistant
    Pipeline --> Scoring
    Scoring --> FNS
    Scoring --> BKI
    Pipeline <--> DB
    Auth <--> DB
    Assistant -.->|"HTTPS (при LLM_MODE=groq)"| Groq
