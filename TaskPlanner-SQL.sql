-- Inıtılaze database
CREATE DATABASE task_planner;
USE task_planner;



-- Create Table
CREATE TABLE tasks (
id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(255) NOT NULL,
description TEXT,
category varchar(255),
deadline DATE
);

-- Show table
SELECT * FROM tasks;
-- Clear table
truncate table tasks;