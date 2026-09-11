# Матрица трассируемости

| Требование | Реализация | Тест/проверка |
|---|---|---|
| FR-01 Авторизация | Spring Security + HTTP Basic | Вход user/admin |
| FR-02 Роли | ROLE_USER / ROLE_ADMIN | Проверка admin endpoint |
| FR-03 Список комнат | `/api/rooms` + React | UI «Комнаты» |
| FR-04 Карточка комнаты | Room entity/REST | UI |
| FR-05 Выбор интервала | BookingRequest | UI + validation |
| FR-06 Запрет пересечения | BookingService | `BookingServiceTest` |
| FR-07 Свои бронирования | `/api/bookings/mine` | UI |
| FR-08 Отмена | `/api/bookings/{id}` | `BookingServiceExtendedTest` |
| FR-09 Администрирование | `/api/admin/**` | admin сценарий |
| FR-10 REST API | Controllers | HTTP smoke tests |
| FR-11 Валидация | Jakarta Validation | unit/integration tests |
| FR-12 Статистика | `/api/admin/stats` | admin UI |

| Требование | Реализация | Доказательство |
|---|---|---|
| JWT-аутентификация | `JwtService` + `JwtAuthenticationFilter` + `/api/auth/login` | код security/auth |
| Расширенная доменная модель | User, MeetingRoom, Booking, Building, Workspace, Equipment, Notification, AuditLog | JPA entities |
| Поиск с фильтрацией | `GET /api/rooms/search` | RoomSearchController |
| Workspace API | `GET /api/workspaces` | WorkspaceController |
| Equipment API | `GET /api/equipment` | EquipmentController |
