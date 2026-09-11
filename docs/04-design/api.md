# REST API

| Метод | Endpoint | Назначение | Доступ |
|---|---|---|---|
| POST | `/api/auth/register` | регистрация | public |
| GET | `/api/auth/me` | текущий пользователь | auth |
| GET | `/api/rooms` | список комнат | auth |
| GET | `/api/rooms/{id}` | карточка комнаты | auth |
| POST | `/api/bookings` | создать бронь | USER/ADMIN |
| GET | `/api/bookings/mine` | мои бронирования | USER/ADMIN |
| GET | `/api/bookings/availability` | пересечения слота | USER/ADMIN |
| DELETE | `/api/bookings/{id}` | отменить бронь | USER/ADMIN |
| POST | `/api/admin/rooms` | создать комнату | ADMIN |
| PUT | `/api/admin/rooms/{id}` | изменить комнату | ADMIN |
| DELETE | `/api/admin/rooms/{id}` | удалить комнату | ADMIN |
| GET | `/api/admin/bookings` | все бронирования | ADMIN |
