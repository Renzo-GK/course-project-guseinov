# Спецификация ключевых методов

| Класс | Метод | Назначение | Транзакция |
|---|---|---|---|
| BookingService | create | Создание бронирования и проверка пересечения | Да |
| BookingService | cancel | Отмена собственного бронирования | Да |
| BookingService | availability | Получение расписания комнаты | Read-only |
| RoomService | create | Создание комнаты | Да |
| RoomService | update | Изменение комнаты | Да |
| RoomService | delete | Удаление комнаты | Да |
| UserService | register | Регистрация пользователя | Да |
| RoomSearchService | search | Фильтрация комнат | Read-only |
