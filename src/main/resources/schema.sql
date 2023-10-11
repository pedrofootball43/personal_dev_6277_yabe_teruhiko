DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS tasks;

CREATE TABLE users
(
    id SERIAL PRIMARY KEY;
    login_id TEXT;
    name TEXT;
    password TEXT;
);

CREATE TABLE categories
(
    id SERIAL PRIMARY KEY;
    name TEXT;
);

CREATE TABLE tasks
(
    id SERIAL PRIMARY KEY;
    user_id INTEGER;
    category_id INTEGER;
    task TEXT;
    task_detail TEXT;
    deadline DATE;
);