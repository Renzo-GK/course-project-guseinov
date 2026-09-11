CREATE TABLE users (
 id BIGSERIAL PRIMARY KEY,
 username VARCHAR(100) NOT NULL UNIQUE,
 password VARCHAR(255) NOT NULL,
 role VARCHAR(30) NOT NULL
);

CREATE TABLE meeting_rooms (
 id BIGSERIAL PRIMARY KEY,
 name VARCHAR(150) NOT NULL,
 capacity INTEGER NOT NULL CHECK (capacity > 0),
 location VARCHAR(150),
 equipment TEXT
);

CREATE TABLE bookings (
 id BIGSERIAL PRIMARY KEY,
 user_id BIGINT NOT NULL REFERENCES users(id),
 room_id BIGINT NOT NULL REFERENCES meeting_rooms(id),
 start_at TIMESTAMP NOT NULL,
 end_at TIMESTAMP NOT NULL,
 status VARCHAR(30) NOT NULL DEFAULT 'ACTIVE',
 CHECK (start_at < end_at)
);

CREATE INDEX idx_bookings_room_time ON bookings(room_id, start_at, end_at);
