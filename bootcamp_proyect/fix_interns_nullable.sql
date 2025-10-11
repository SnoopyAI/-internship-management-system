-- Script para permitir valores NULL en la tabla interns
-- Ejecutar este script en SQL Server Management Studio o Azure Data Studio
-- Base de datos: DB_Intership_mangement_JPA

USE DB_Intership_mangement_JPA;
GO

-- Primero, eliminar las restricciones de foreign key si existen
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_ACADEMY_TUTOR_INTERN')
    ALTER TABLE interns DROP CONSTRAINT FK_ACADEMY_TUTOR_INTERN;
GO

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_COMPANY_TUTOR_INTERN')
    ALTER TABLE interns DROP CONSTRAINT FK_COMPANY_TUTOR_INTERN;
GO

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_BOARD_INTERN')
    ALTER TABLE interns DROP CONSTRAINT FK_BOARD_INTERN;
GO

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE name = 'FK_UNIVERSITY_INTERN')
    ALTER TABLE interns DROP CONSTRAINT FK_UNIVERSITY_INTERN;
GO

-- Modificar las columnas para permitir NULL
ALTER TABLE interns ALTER COLUMN academy_tutor_id INT NULL;
GO

ALTER TABLE interns ALTER COLUMN company_tutor_id INT NULL;
GO

ALTER TABLE interns ALTER COLUMN board_id INT NULL;
GO

ALTER TABLE interns ALTER COLUMN university_id INT NULL;
GO

ALTER TABLE interns ALTER COLUMN career VARCHAR(100) NULL;
GO

ALTER TABLE interns ALTER COLUMN semester INT NULL;
GO

-- Recrear las foreign keys sin restricción NOT NULL
ALTER TABLE interns 
ADD CONSTRAINT FK_ACADEMY_TUTOR_INTERN 
FOREIGN KEY (academy_tutor_id) REFERENCES academy_tutors(id);
GO

ALTER TABLE interns 
ADD CONSTRAINT FK_COMPANY_TUTOR_INTERN 
FOREIGN KEY (company_tutor_id) REFERENCES company_tutors(id);
GO

ALTER TABLE interns 
ADD CONSTRAINT FK_BOARD_INTERN 
FOREIGN KEY (board_id) REFERENCES boards(board_id);
GO

ALTER TABLE interns 
ADD CONSTRAINT FK_UNIVERSITY_INTERN 
FOREIGN KEY (university_id) REFERENCES universities(university_id);
GO

PRINT 'Script ejecutado correctamente. Las columnas ahora permiten valores NULL.';
GO
