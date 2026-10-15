CREATE DATABASE IF NOT EXISTS petcarestore
  CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE petcarestore;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS vaccinations;
DROP TABLE IF EXISTS medical_records;
DROP TABLE IF EXISTS appointments;
DROP TABLE IF EXISTS pets;
DROP TABLE IF EXISTS services;
DROP TABLE IF EXISTS users;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  full_name VARCHAR(100) NOT NULL,
  email VARCHAR(150) NOT NULL UNIQUE,
  phone VARCHAR(25),
  password_hash VARCHAR(256) NOT NULL,
  password_salt VARCHAR(128) NOT NULL,
  role ENUM('OWNER','STAFF','ADMIN') NOT NULL DEFAULT 'OWNER',
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE pets (
  pet_id INT AUTO_INCREMENT PRIMARY KEY,
  owner_id INT NOT NULL,
  pet_name VARCHAR(80) NOT NULL,
  species VARCHAR(40) NOT NULL,
  breed VARCHAR(80),
  age_years DECIMAL(4,1) NOT NULL DEFAULT 0,
  gender ENUM('MALE','FEMALE','UNKNOWN') NOT NULL DEFAULT 'UNKNOWN',
  notes VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_pets_owner FOREIGN KEY (owner_id) REFERENCES users(user_id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  INDEX idx_pets_owner (owner_id)
) ENGINE=InnoDB;

CREATE TABLE services (
  service_id INT AUTO_INCREMENT PRIMARY KEY,
  service_name VARCHAR(100) NOT NULL UNIQUE,
  description VARCHAR(500),
  price DECIMAL(10,2) NOT NULL DEFAULT 0.00,
  active TINYINT(1) NOT NULL DEFAULT 1,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE appointments (
  appointment_id INT AUTO_INCREMENT PRIMARY KEY,
  pet_id INT NOT NULL,
  service_id INT NOT NULL,
  appointment_date DATE NOT NULL,
  appointment_time TIME NOT NULL,
  status ENUM('BOOKED','CANCELLED','COMPLETED') NOT NULL DEFAULT 'BOOKED',
  notes VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_appointments_pet FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_appointments_service FOREIGN KEY (service_id) REFERENCES services(service_id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  INDEX idx_appointment_slot (appointment_date, appointment_time, status),
  INDEX idx_appointments_pet (pet_id)
) ENGINE=InnoDB;

CREATE TABLE medical_records (
  record_id INT AUTO_INCREMENT PRIMARY KEY,
  pet_id INT NOT NULL,
  staff_user_id INT NULL,
  record_date DATE NOT NULL,
  diagnosis VARCHAR(500) NOT NULL,
  treatment VARCHAR(1000),
  notes VARCHAR(1000),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_medical_pet FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_medical_staff FOREIGN KEY (staff_user_id) REFERENCES users(user_id)
    ON UPDATE CASCADE ON DELETE SET NULL,
  INDEX idx_medical_pet_date (pet_id, record_date)
) ENGINE=InnoDB;

CREATE TABLE vaccinations (
  vaccination_id INT AUTO_INCREMENT PRIMARY KEY,
  pet_id INT NOT NULL,
  staff_user_id INT NULL,
  vaccine_name VARCHAR(120) NOT NULL,
  vaccination_date DATE NOT NULL,
  next_due_date DATE NULL,
  notes VARCHAR(500),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_vaccinations_pet FOREIGN KEY (pet_id) REFERENCES pets(pet_id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
  CONSTRAINT fk_vaccinations_staff FOREIGN KEY (staff_user_id) REFERENCES users(user_id)
    ON UPDATE CASCADE ON DELETE SET NULL,
  INDEX idx_vaccinations_pet_date (pet_id, vaccination_date)
) ENGINE=InnoDB;

INSERT INTO services (service_name, description, price) VALUES
('General Check-up', 'Routine wellness examination for your pet.', 500.00),
('Vaccination', 'Vaccination consultation and administration.', 350.00),
('Grooming', 'Basic grooming and hygiene service.', 450.00),
('Dental Check', 'Oral health and dental examination.', 600.00);
