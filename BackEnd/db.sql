CREATE DATABASE IF NOT EXISTS gestioneprogetti;

USE gestioneprogetti;

CREATE TABLE seniority (
	id INT PRIMARY KEY,
	name VARCHAR(15) NOT NULL,
	cost DOUBLE NOT NULL
) Engine=InnoDB;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(60) NOT NULL,
    password VARCHAR(10) NOT NULL,
    email VARCHAR(60) NOT NULL,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
	skill VARCHAR(255) NOT NULL,
	isDeactivated BOOLEAN NOT NULL,
	seniority INT,
	FOREIGN KEY(seniority) REFERENCES seniority(id)
    ON DELETE CASCADE
	ON UPDATE CASCADE		
) Engine=InnoDB;


CREATE TABLE profile (
	id INT PRIMARY KEY,
	name VARCHAR(15) NOT NULL
) Engine=InnoDB;

CREATE TABLE profileUser (
	user INT,
	profile INT,
	PRIMARY KEY(user, profile),
	FOREIGN KEY(user) REFERENCES user(id)
    ON DELETE CASCADE
	ON UPDATE CASCADE,
	FOREIGN KEY(profile) REFERENCES profile(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
) Engine=InnoDB;

CREATE TABLE project (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(60) NOT NULL,
	description VARCHAR(255) NOT NULL,
	status VARCHAR(10) NOT NULL,
	budget DOUBLE NOT NULL,
	cost DOUBLE NOT NULL,
	project_manager INT,
	FOREIGN KEY(project_manager) REFERENCES user(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE	
) Engine=InnoDB;

CREATE TABLE projectUser (
    user INT,
	project INT,
	total_hours INT NOT NULL,
    FOREIGN KEY(user) REFERENCES user(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	FOREIGN KEY(project) REFERENCES project(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	PRIMARY KEY(user, project)
) Engine=InnoDB;

CREATE TABLE absence (
	id INT PRIMARY KEY,
	name VARCHAR(10) NOT NULL
) Engine=InnoDB;

CREATE TABLE timesheetCell (
	id INT AUTO_INCREMENT PRIMARY KEY,
	absence INT,
	project INT,
	user INT NOT NULL,
    date DATE NOT NULL,
    content CHAR(1),
	FOREIGN KEY(user) REFERENCES user(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	FOREIGN KEY(absence) REFERENCES absence(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
	FOREIGN KEY(project) REFERENCES project(id)
	ON DELETE CASCADE
	ON UPDATE CASCADE
) Engine=InnoDB;


/* absence */
INSERT INTO absence VALUES(1, 'Ferie');	
INSERT INTO absence VALUES(2, 'Malattia');	
INSERT INTO absence VALUES(3, 'Sciopero');
INSERT INTO absence VALUES(4, 'Permesso');
/* profile */
INSERT INTO profile VALUES(1, 'Amministratore');
INSERT INTO profile VALUES(2, 'Controller');
INSERT INTO profile VALUES(3, 'Dipendente');
INSERT INTO profile VALUES(4, 'Project Manager');
/* seniority */
INSERT INTO seniority VALUES(1, 'Junior', 0.0);	
INSERT INTO seniority VALUES(2, 'Middle', 0.0);	
INSERT INTO seniority VALUES(3, 'Senior', 0.0);	
/* user */
INSERT INTO user VALUES(NULL, 'Admin', 'admin', 'admin@gmail.com', 'Amministratore', 'Admin', 'Non richiesto', false, 3);
INSERT INTO user VALUES(NULL, 'Ctrl', 'controller', 'ctrl@gmail.com', 'Controller', 'Controller', 'Non richiesto', false, 3);
INSERT INTO user VALUES(NULL, 'IndomitoGallo', 'talocci', 'pippo@gmail.com', 'Luca', 'Talocci', 'Non eccellente', true, 2);
INSERT INTO user VALUES(NULL, 'LBernabei', 'bernabei', 'pluto@gmail.com', 'Lorenzo', 'Bernabei', 'Forte', false, 3);
INSERT INTO user VALUES(NULL, 'LukeCame', 'camerlengo', 'paperino@gmail.com', 'Luca', 'Camerlengo', 'Bravo', false, 1);
INSERT INTO user VALUES(NULL, 'Dav33', 'vitiello', 'minnie@gmail.com', 'Davide', 'Vitiello', 'Non eccellente', false, 2);
INSERT INTO user VALUES(NULL, 'Aizevs', 'svezia', 'quo@gmail.com', 'Lorenzo', 'Svezia', 'Bravissimo', false, 1);
INSERT INTO user VALUES(NULL, 'Gaudo', 'gaudenzi', 'qua@gmail.com', 'Francesco', 'Gaudenzi', 'Il migliore', false, 3);
/* profileUser */
INSERT INTO profileUser VALUES(1, 1);
INSERT INTO profileUser VALUES(2, 2);
INSERT INTO profileUser VALUES(3, 3);
INSERT INTO profileUser VALUES(4, 3);
INSERT INTO profileUser VALUES(4, 4);
INSERT INTO profileUser VALUES(5, 3);
INSERT INTO profileUser VALUES(6, 3);
INSERT INTO profileUser VALUES(7, 3);
INSERT INTO profileUser VALUES(8, 4);
/* project */
INSERT INTO project VALUES(NULL, 'Progetto1', 'Macchina del caffè con comandi vocali', 'in corso', 20000, 5000, 4);
INSERT INTO project VALUES(NULL, 'Progetto2', 'Tagliaerba con comandi vocali', 'in corso', 10000, 7000, 8);
INSERT INTO project VALUES(NULL, 'Progetto3', 'Lampadario con comandi vocali', 'concluso', 0, 15000, 4);
INSERT INTO project VALUES(NULL, 'Progetto4', 'Computer con comandi vocali', 'stand-by', 10000, 5000, 8);
/* projectUser */
INSERT INTO projectUser VALUES(3, 1, 120);
INSERT INTO projectUser VALUES(4, 1, 80);
INSERT INTO projectUser VALUES(4, 2, 30);
INSERT INTO projectUser VALUES(5, 3, 60);
INSERT INTO projectUser VALUES(5, 4, 50);
INSERT INTO projectUser VALUES(6, 2, 40);
INSERT INTO projectUser VALUES(7, 1, 30);
INSERT INTO projectUser VALUES(7, 2, 20);
