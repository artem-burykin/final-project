CREATE DATABASE IF NOT EXISTS publisherhouse;
USE publisherhouse;

DROP TABLE IF EXISTS order_has_product;
DROP TABLE IF EXISTS publisherhouse.order;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS publication;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;


CREATE TABLE role (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(1024)
);

CREATE TABLE account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    login VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL,
    email VARCHAR(32) NOT NULL,
    first_name VARCHAR(32) NOT NULL,
    last_name VARCHAR(32) NOT NULL,
    role_id INT,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    isBlocked boolean DEFAULT false,
    CONSTRAINT fk_account_role_id FOREIGN KEY (role_id)
        REFERENCES role (id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE,
    parent_id INT,
    description VARCHAR(1024),
    CONSTRAINT fk_category_parent_id FOREIGN KEY (parent_id)
        REFERENCES category (id)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE product (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL UNIQUE,
    description VARCHAR(2048),
    price DOUBLE NOT NULL CONSTRAINT ck_product_price CHECK (price >= 0),
    category_id INT,
    logo VARCHAR(64) UNIQUE,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_product_category_id FOREIGN KEY (category_id)
        REFERENCES category (id)
        ON UPDATE CASCADE ON DELETE SET NULL
);

create table publication (
	id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    name varchar(255) NOT NULL,
    content longtext NOT NULL,
    create_date TIMESTAMP DEFAULT current_timestamp,
    CONSTRAINT fk_product_product_id FOREIGN KEY (product_id)
		REFERENCES product (id)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE status (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE,
    description VARCHAR(1024)
);

CREATE TABLE publisherhouse.order (
    id INT PRIMARY KEY AUTO_INCREMENT,
    total DOUBLE CONSTRAINT ch_total CHECK (total >= 0),
    account_id INT,
    status_id INT NOT NULL,
    description VARCHAR(1024),
    date_start TIMESTAMP NOT NULL,
    date_end TIMESTAMP NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_account_id FOREIGN KEY (account_id)
        REFERENCES account (id) 
        ON UPDATE CASCADE ON DELETE SET NULL,
    CONSTRAINT fk_order_status_id FOREIGN KEY (status_id)
        REFERENCES status (id) 
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE order_has_product (
    order_id INT,
    product_id INT,
    price DOUBLE NOT NULL DEFAULT 0 CONSTRAINT ck_order_has_product_price CHECK (price >= 0),
    PRIMARY KEY (order_id , product_id),
    CONSTRAINT fk_order_has_product_order_id FOREIGN KEY (order_id)
        REFERENCES publisherhouse.order (id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_oder_has_product_product_id FOREIGN KEY (product_id)
        REFERENCES product (id)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

INSERT INTO role (id, name, description) VALUES (DEFAULT, 'Administrator', 'Has administrator rules');
INSERT INTO role (id, name, description) VALUES (DEFAULT, 'User', 'Can buy publication and has substricption');

INSERT INTO category(id, name, description) VALUES (DEFAULT, 'Family', 'If you find some information about family and chill time, come to us');
INSERT INTO category(id, name, description) VALUES (DEFAULT, 'Sport', 'This category is for everyone, who like sport and is interested in it');
INSERT INTO category(id, name, description) VALUES (DEFAULT, 'Travalling', 'You can find a place to come and take a lot of pleasure');
INSERT INTO category(id, name, parent_id, description) VALUES (DEFAULT, 'Family movies', (SELECT id FROM (SELECT  id FROM category WHERE name = 'Family' LIMIT 1) as `tmp`), 'Here you can find a movie, which you will watch with your family this evening');
INSERT INTO category(id, name, parent_id, description) VALUES (DEFAULT, 'Rugby', (SELECT id FROM (SELECT  id FROM category WHERE name = 'Sport' LIMIT 1) as `tmp`), 'All news about rugby');
INSERT INTO category(id, name, parent_id, description) VALUES (DEFAULT, 'Basketball', (SELECT id FROM (SELECT  id FROM category WHERE name = 'Sport' LIMIT 1) as `tmp`), 'All news about basketball here');

INSERT INTO product(id, name, description, price, category_id, logo, create_date, last_update) VALUES (DEFAULT, 'BBC Sport - Rugby Union', 'Breaking news & live sports coverage including results, video, audio and analysis on Football, F1, Cricket, Rugby Union, Rugby League, Golf, Tennis and all the main world sports, plus major events such as the Olympic Games.', 449.00, (SELECT id FROM category WHERE name = 'Rugby'), "rugbyUnion.png", DEFAULT, DEFAULT);
INSERT INTO product(id, name, description, price, category_id, logo, create_date, last_update) VALUES (DEFAULT, 'TalkBasket.net', '', 799.00, (SELECT id FROM category WHERE name = 'Basketball'), "talkBasket.png", DEFAULT, DEFAULT);
INSERT INTO product(id, name, description, price, category_id, logo, create_date, last_update) VALUES (DEFAULT, 'GlodalHelpSwap', 'GlobalHelpSwap is a global travel blog focused on independent, responsible and meaningful travel. It is read by an engaged community of international global travellers.', 249.00, (SELECT id FROM category WHERE name = 'Travalling'), "glodalHelpSwap.png", DEFAULT, DEFAULT);
INSERT INTO product(id, name, description, price, category_id, logo, create_date, last_update) VALUES (DEFAULT, 'Best Family Movies', 'Need a great pick for your next family movie night? Having trouble finding a movie you can all agree on? Our editors have hand-picked some of the best kids and family movies available on DVD, including beloved classics, action-packed adventures, laugh-out-loud comedies, and powerful dramas. Need even more inspiration? Be sure to check out our Essential Movie Guide for more than 190 of our favorite time-tested titles we know your family will love. Or use our age-based filters to find the perfect pick, no matter your kids age. So grab some popcorn, sit back, and enjoy!', 99.00, (SELECT id FROM category WHERE name = 'Family movies'), "bestFamilyMovies.png", DEFAULT, DEFAULT);

INSERT INTO publication(id, product_id, name, content, create_date) VALUES (DEFAULT, (SELECT id FROM product WHERE name = 'Best Family Movies'),'The Adventures of Milo and Otis', 'Lovable pet tale about friendship despite differences', DEFAULT);
INSERT INTO publication(id, product_id, name, content, create_date) VALUES (DEFAULT, (SELECT id FROM product WHERE name = 'Best Family Movies'),'Frozen', 'Wintry Disney musical is fabulous celebration of sisterhood.', DEFAULT);
INSERT INTO publication(id, product_id, name, content, create_date) VALUES (DEFAULT, (SELECT id FROM product WHERE name = 'Best Family Movies'),'Finding Nemo', 'Sweet father-son tale has some very scary moments.', DEFAULT);