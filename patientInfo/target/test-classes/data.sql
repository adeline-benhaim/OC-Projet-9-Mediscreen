DROP TABLE IF EXISTS patient;
CREATE TABLE patient (
                patient_id INT AUTO_INCREMENT NOT NULL,
                first_name VARCHAR(100) NOT NULL,
                last_name VARCHAR(100) NOT NULL,
                sex ENUM('F', 'M') NOT NULL,
                dob VARCHAR(100) NOT NULL,
                address VARCHAR(100),
                phone VARCHAR(20),

                PRIMARY KEY (patient_id)
);
 INSERT INTO patient
    (first_name, last_name, sex, dob, address, phone)
VALUES
    ('Lucas', 'Ferguson', 'M', '1968-06-22', '2 Warren Street', '387-866-1399'),
    ('Pippa', 'Rees', 'F', '1952-09-27', '745 West Valley Farms Drive', '628-423-0993'),
	('Edward', 'Arnold', 'M', '1952-11-11', '599 East Garden Ave', '123-727-2779'),
    ('Anthony', 'Sharp', 'M', '1946-11-26', '894 Hall Street', '451-761-8383'),
	('Wendy', 'Ince', 'F', '1958-06-29', '4 Southampton Road', '802-911-9975'),
    ('Tracey', 'Ross', 'F', '1949-12-07', '40 Sulphur Springs Dr', '131-396-5049'),
	('Claire', 'Wilson', 'F', '1966-12-31', '12 Cobblestone St', '300-452-1091'),
    ('Max', 'Buckland', 'M', '1945-06-24', '193 Vale St', '833-534-0864'),
	('Natalie', 'Clark', 'F', '1964-06-18', '12 Beechwood Road', '241-467-9197'),
    ('Piers', 'Bailey', 'M', '1959-06-28', '1202 Bumble Dr', '747-815-0557')
;