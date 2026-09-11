# Стратегия ORM

- JPA/Hibernate используется для отображения Entity в таблицы PostgreSQL.
- `@Entity`, `@Id`, `@ManyToOne` задают объектно-реляционные связи.
- Репозитории Spring Data обеспечивают CRUD и запросы.
- Persistence Context Hibernate выступает как Identity Map в рамках транзакции.
- Lazy-связи Booking → User/MeetingRoom используются для контроля объёма загрузки; для REST-ответов применена явная загрузка необходимых связей.
- Индекс `room_id/start_at/end_at` используется для запросов расписания.
