# Спецификация межслойных интерфейсов

## Control → Mediator
- `UserService.register(username, password)` — регистрация пользователя.
- `BookingService.create(username, roomId, startAt, endAt)` — создание бронирования.
- `BookingService.cancel(bookingId, username)` — отмена собственного бронирования.
- `RoomService.create/update/delete` — управление комнатами.

## Mediator → Foundation
- `UserRepository.findByUsername`.
- `BookingRepository.findWithRelationsById`.
- `BookingRepository.findByUserUsernameOrderByStartAtDesc`.
- `BookingRepository.existsByRoomIdAndStartAtLessThanAndEndAtGreaterThanAndStatus`.
- `MeetingRoomRepository.findById/save/deleteById`.

Зависимость слоёв направлена сверху вниз: Presentation → Control → Mediator → Entity → Foundation.
