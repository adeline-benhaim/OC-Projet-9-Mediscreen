CREATE SCHEMA IF NOT EXISTS mediscreen;

USE mediscreen;

CREATE TABLE IF NOT EXISTS patient (
                patient_id INT AUTO_INCREMENT NOT NULL,
                first_name VARCHAR(100) NOT NULL,
                last_name VARCHAR(100) NOT NULL,
                sex ENUM('F', 'M') NOT NULL,
                dob VARCHAR(100) NOT NULL,
                address VARCHAR(100),
                phone VARCHAR(20),

                PRIMARY KEY (patient_id)
);