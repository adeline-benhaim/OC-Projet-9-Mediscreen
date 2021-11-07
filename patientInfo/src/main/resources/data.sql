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

-- INSERT INTO patient
--    (first_name, last_name, sex, dob, address, phone)
--VALUES
--    ('adeline', 'benhaim', 'M', '1986-09-28', '3 rue de la liberté', '0601010101'),
--    ('adeline', 'benhaim', 'F', '1975-02-03', '24 avenue de la republique', '0602020202')
--;