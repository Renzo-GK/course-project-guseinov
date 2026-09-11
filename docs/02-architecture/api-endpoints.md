# REST API — контрольный перечень

## Auth
- `POST /api/auth/register`
- `GET /api/auth/me`

## Rooms
- `GET /api/rooms`
- `GET /api/rooms/{id}`

## Bookings
- `POST /api/bookings`
- `GET /api/bookings/mine`
- `GET /api/bookings/availability?roomId={id}&date={yyyy-MM-dd}`
- `DELETE /api/bookings/{id}`

## Admin
- `POST /api/admin/rooms`
- `PUT /api/admin/rooms/{id}`
- `DELETE /api/admin/rooms/{id}`
- `GET /api/admin/bookings`
- `GET /api/admin/stats`

Всего: 13 основных endpoints.
