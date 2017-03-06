DROP DATABASE IF EXISTS InteraktiveFolien;
CREATE DATABASE InteraktiveFolien collate utf8_unicode_ci;
USE InteraktiveFolien;

CREATE TABLE Lehrer (
        LehrerID            int PRIMARY KEY AUTO_INCREMENT,
        Benutzername		varchar(30) NOT NULL UNIQUE,
        Vorname             varchar(30) NOT NULL,
        Nachname         	varchar(30) NOT NULL,
        Passwort			varchar(30) NOT NULL
);

CREATE TABLE Kurs (
        KursID            	int PRIMARY KEY AUTO_INCREMENT,
        LehrerID			int NOT NULL,
        Name              	varchar(30) UNIQUE NOT NULL,
        Passwort            varchar(30) NOT NULL,
        -- Namenkürzel
        FOREIGN KEY (LehrerID) REFERENCES Lehrer(LehrerID) ON DELETE CASCADE
);

CREATE TABLE Foliensatz (
        FoliensatzID        int PRIMARY KEY AUTO_INCREMENT,
        KursID              int NOT NULL,
        Name         		varchar(30) NOT NULL,
        FOREIGN KEY (KursID) REFERENCES Kurs(KursID) ON DELETE CASCADE
);

-- Folientyp
-- Heatplot 		- H
-- Choice  			- C
-- Multiple Choice 	- M
-- reine Anzeige 	- A
CREATE TABLE Folie (
        FolienID            int PRIMARY KEY AUTO_INCREMENT,
        FoliensatzID		int NOT NULL,
        fPath			    varchar(120) NOT NULL,
        FolienTyp			char NOT NULL,
        FOREIGN KEY (FoliensatzID) REFERENCES Foliensatz(FoliensatzID) ON DELETE CASCADE
);

CREATE TABLE Auswahlbereich (
        BereichID           int PRIMARY KEY AUTO_INCREMENT,
        FolienID            int NOT NULL,
        EckeOLX         	int NOT NULL,
		EckeOLY         	int NOT NULL,
        EckeURX			 	int NOT NULL,
		EckeURY         	int NOT NULL,
        FOREIGN KEY (FolienID) REFERENCES Folie(FolienID) ON DELETE CASCADE
);

CREATE TABLE Student (
        StudentenID         int PRIMARY KEY AUTO_INCREMENT,
        Benutzername		varchar(30) NOT NULL UNIQUE,
        Vorname             varchar(30) NOT NULL,
        Nachname         	varchar(30) NOT NULL,
        Passwort			varchar(30) NOT NULL
);

CREATE TABLE Kursteilnahme (
		KursteilnahmeID		int PRIMARY KEY AUTO_INCREMENT,
        KursID            	int NOT NULL,
        StudentenID       	int NOT NULL,
        FOREIGN KEY (KursID) REFERENCES Kurs(KursID),
        FOREIGN KEY (StudentenID) REFERENCES Student(StudentenID) ON DELETE CASCADE
);

CREATE TABLE Uservoting (
        VotingID            int PRIMARY KEY AUTO_INCREMENT,
        BefID           int NOT NULL,
        StudentenID         int NOT NULL,
        FolienID			int NOT NULL,
        KoordX				int NOT NULL,
        KoordY				int NOT NULL,
        Auswahloption		varchar(30),
        FOREIGN KEY (StudentenID) REFERENCES Student(StudentenID),
        FOREIGN KEY (FolienID) REFERENCES Folie(FolienID) ON DELETE CASCADE
);

CREATE TABLE BefragungsEinheit (
        BefID           int PRIMARY KEY AUTO_INCREMENT,
        FolienID		int NOT NULL,
        Beginn			timestamp NOT NULL,
        Ende			timestamp NULL,
        FOREIGN KEY (FolienID) REFERENCES Folie(FolienID) ON DELETE CASCADE
);

INSERT INTO Student VALUES(null, 'Student1', 'Talip', 'Vural', 'stud');
INSERT INTO Lehrer VALUES(null, 'Lehrer1', 'Orcun', 'Döger', 'lehr');

INSERT INTO Kurs VALUES(null, 1, 'Mathe', 'PW');
INSERT INTO Kurs VALUES(null, 1, 'Englisch', 'PW');
INSERT INTO Kurs VALUES(null, 1, 'Deutsch', 'PW');

-- 								ID, Kurs, Student
INSERT INTO Kursteilnahme VALUES(null, 1, 1);
INSERT INTO Kursteilnahme VALUES(null, 2, 1);
