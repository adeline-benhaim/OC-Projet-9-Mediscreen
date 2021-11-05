DROP database IF EXISTS mediscreen;
CREATE database mediscreen;
use mediscreen;

DROP TABLE IF EXISTS patient;
CREATE TABLE patient (
                patient_id INT AUTO_INCREMENT NOT NULL,
                first_name VARCHAR(100) NOT NULL,
                last_name VARCHAR(100) NOT NULL,
                gender ENUM('F', 'M') NOT NULL,
                birthdate VARCHAR(100) NOT NULL,
                address VARCHAR(100),
                phone VARCHAR(20),

                PRIMARY KEY (patient_id)
);

 INSERT INTO patient
    (first_name, last_name, gender, birthdate)
VALUES
    ("adeline", 'benhaim', 'M', '4FDGFD')
;