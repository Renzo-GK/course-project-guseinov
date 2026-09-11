# Аудит соответствия методическим требованиям

| Область | Статус | Доказательство / остаток |
|---|---|---|
| Java 17+ / Spring Boot | ✅ | Java 21, Spring Boot |
| PostgreSQL | ✅ | Docker Compose + JPA |
| React + TypeScript | ✅ | `frontend/` |
| Docker | ✅ | `docker-compose.yml`, Dockerfiles |
| PCMEF | ✅ | `docs/02-architecture/` |
| REST API ≥ 8 | ✅ | 17 mapping-операций в controllers |
| Swagger/OpenAPI | ✅ | springdoc + OpenAPI config |
| BCrypt | ✅ | PasswordEncoder |
| JWT | ✅ по коду | Требуется финальная runtime-проверка после последнего build |
| ROLE_USER/ROLE_ADMIN | ✅ | Spring Security |
| ≥5 web screens | ✅/частично | SPA-разделы; для отчёта оформить как экраны/маршруты |
| Client validation | ✅/частично | React + Jakarta validation на сервере; расширить JS validation в UI |
| JUnit >40% | ✅ | JaCoCo: 52% instructions, 56% lines по предоставленному отчёту |
| Data Mapper | ✅ | `RoomMapper` |
| Identity Map | ✅ по ORM | Hibernate Persistence Context |
| Lazy Load | ✅ | JPA LAZY + EntityGraph |
| IDEF0 | ✅ документ | `00-initiation/idef0-a0.md` |
| BUC | ✅ документ | `buc-business-use-cases.md` |
| 15+ glossary | ✅ | 24 термина |
| Stakeholder matrix | ✅ документ | `stakeholder-matrix.md` |
| SWOT | ✅ документ | `swot.md` |
| Use Case + 2 detailed | ✅ | `use-case-specifications.md` |
| Domain Model | ✅ | `domain-model.md` |
| Interface specification | ✅ | `interface-specification.md` |
| ER / DDL / ORM | ✅ | `03-database/` |
| 3+ sequence diagrams | ✅ | auth, booking, cancel, report |
| Design class diagram | ✅ | `design-class-diagram.mmd` |
| GoF patterns | ⚠️ | Нужно добавить обоснованные 1–2 паттерна и описание |
| Testing report | ⚠️ | Требуется приложить финальный JaCoCo screenshot/report |
| Deployment/Tomcat | ⚠️ | Docker готов; нужен формальный Tomcat/WAR раздел |
| WBS | ✅ | `wbs.md` |
| Gantt | ⚠️ | Нужно оформить календарную диаграмму |
| COCOMO | ⚠️ | Нужно рассчитать оценку |
| User/Admin manuals | ⚠️ | Нужно объединить в финальный комплект |
| Explanatory note | ⚠️ | Предстоит собрать единый документ |
