-- Insert users
INSERT INTO user_details (id, birthday, name)
VALUES (1001, CURRENT_DATE, 'Mbarek');

INSERT INTO user_details (id, birthday, name)
VALUES (1002, CURRENT_DATE, 'Laura');

-- Insert posts
INSERT INTO post (id, description, user_id)
VALUES (2002, 'I wanna learn MS', 1001);

INSERT INTO post (id, description, user_id)
VALUES (2003, 'I wanna learn cpp', 1001);

INSERT INTO post (id, description, user_id)
VALUES (2004, 'I wanna learn DevOps', 1001);
