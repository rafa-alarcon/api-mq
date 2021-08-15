DROP TABLE IF EXISTS bookings;

CREATE TABLE bookings (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  booking_owner VARCHAR(250) NOT NULL,
  check_in_date DATE NOT NULL,
  check_on_date DATE NOT NULL,
  total_days INT NOT NULL,
  guest_number INT NOT NULL,
  rooms INT NOT NULL,
  child_number INT NOT NULL
);


INSERT INTO bookings (booking_owner,check_in_date,check_on_date,
total_days,guest_number,rooms,child_number) VALUES
  ('Guest Test 1', '2021-08-12', '2021-08-14',2,3,1,1),
  ('Guest Test 2', '2021-08-12', '2021-08-13',1,2,1,0);