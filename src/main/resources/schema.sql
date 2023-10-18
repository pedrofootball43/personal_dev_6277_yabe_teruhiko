DROP VIEW IF EXISTS v_tasks;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS tasks;


CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    login_id TEXT,
    name TEXT,
    password TEXT
);

CREATE TABLE categories
(
    id SERIAL PRIMARY KEY,
    name TEXT
);

CREATE TABLE tasks
(
    id SERIAL PRIMARY KEY,
    user_id INTEGER,
    category_id INTEGER,
    task TEXT,
    task_detail TEXT,
    /*deadline TEXT*/
    deadline DATE,
    situation TEXT
    
);

CREATE VIEW v_tasks AS 
(
	SELECT
		t.id,
		t.user_id,
		t.category_id,
		c.name AS category_name,
		t.task,
		t.task_detail,
		t.deadline,
		t.situation
	FROM tasks t
	JOIN categories c
	ON t.category_id = c.id
);

