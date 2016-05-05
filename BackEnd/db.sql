CREATE DATABASE IF NOT EXISTS projects;

USE projects;

CREATE TABLE seniority (
	id INT PRIMARY KEY,
	name VARCHAR(15) NOT NULL,
	cost DOUBLE NOT NULL
) Engine=InnoDB;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(30) NOT NULL,
	surname VARCHAR(30) NOT NULL,
    username VARCHAR(60) NOT NULL,
    email VARCHAR(60) NOT NULL,
	password VARCHAR(10) NOT NULL,
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
		ON UPDATE CASCADE
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



INSERT INTO absence VALUES(1, 'Ferie');	
INSERT INTO absence VALUES(2, 'Malattia');	
INSERT INTO absence VALUES(3, 'Sciopero');
INSERT INTO absence VALUES(4, 'Permesso');

INSERT INTO profile VALUES(1, 'Amministratore');
INSERT INTO profile VALUES(2, 'Controller');
INSERT INTO profile VALUES(3, 'Dipendente');
INSERT INTO profile VALUES(4, 'Project Manager');

INSERT INTO seniority VALUES(1, 'Junior', 0.0);	
INSERT INTO seniority VALUES(2, 'Middle', 0.0);	
INSERT INTO seniority VALUES(3, 'Senior', 0.0);	
