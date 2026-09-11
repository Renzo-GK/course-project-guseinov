# Модель бизнес-классов

Основные сущности предметной области:

- User — сотрудник и его роль;
- MeetingRoom — переговорная;
- Booking — резервирование комнаты;
- Building — здание;
- Workspace — рабочее пространство;
- Equipment — оборудование;
- Notification — уведомление;
- AuditLog — журнал действий.

Связи:
- User 1:N Booking;
- MeetingRoom 1:N Booking;
- Building 1:N MeetingRoom;
- MeetingRoom N:M Equipment (через связующую модель при расширении);
- User 1:N Notification;
- User 1:N AuditLog.
