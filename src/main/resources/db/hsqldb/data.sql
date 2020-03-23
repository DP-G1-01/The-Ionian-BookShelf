INSERT INTO threads VALUES(1, 'The IonianBookshelf´s Basic Thread', 'Basic Thread');
INSERT INTO threads VALUES(2, 'The IonianBookshelf´s Bronze Thread', 'Bronze thread');

INSERT INTO leagues VALUES(1, 'Basic', 1);
INSERT INTO leagues VALUES(2, 'Bronze', 2);

INSERT INTO authorities VALUES('admin', 'administrator');

INSERT INTO authorities VALUES('summoner1', 'summoner');
INSERT INTO authorities VALUES('summoner2', 'summoner');
INSERT INTO authorities VALUES('summoner3', 'summoner');
INSERT INTO authorities VALUES('summoner4', 'summoner');

INSERT INTO authorities VALUES('reviewer1', 'reviewer');
INSERT INTO authorities VALUES('reviewer2', 'reviewer');
INSERT INTO authorities VALUES('reviewer3', 'reviewer');
INSERT INTO authorities VALUES('reviewer4', 'reviewer');

INSERT INTO users(username, password, enabled) VALUES('admin', 'admin', true);

INSERT INTO users(username, password, enabled) VALUES('summoner1', 'summoner1', true);
INSERT INTO users(username, password, enabled) VALUES('summoner2', 'summoner2', true);
INSERT INTO users(username, password, enabled) VALUES('summoner3', 'summoner3', true);
INSERT INTO users(username, password, enabled) VALUES('summoner4', 'summoner4', true);

INSERT INTO users(username, password, enabled) VALUES('reviewer1', 'reviewer1', true);
INSERT INTO users(username, password, enabled) VALUES('reviewer2', 'reviewer2', true);
INSERT INTO users(username, password, enabled) VALUES('reviewer3', 'reviewer3', true);
INSERT INTO users(username, password, enabled) VALUES('reviewer4', 'reviewer4', true);

INSERT INTO administrators VALUES(1, 'admin@gmail.com', 'admin');

INSERT INTO summoners VALUES(1, 'summoner1@gmail.com', 'summoner1', 1);
INSERT INTO summoners VALUES(2, 'summoner2@gmail.com', 'summoner2', 1);
INSERT INTO summoners VALUES(3, 'summoner3@gmail.com', 'summoner3', 1);
INSERT INTO summoners VALUES(4, 'summoner4@gmail.com', 'summoner4', 1);

INSERT INTO reviewers VALUES (1, 'reviewer1@gmail.com', 'reviewer1');
INSERT INTO reviewers VALUES (2, 'reviewer2@gmail.com', 'reviewer2');
INSERT INTO reviewers VALUES (3, 'reviewer3@gmail.com', 'reviewer3');
INSERT INTO reviewers VALUES (4, 'reviewer4@gmail.com', 'reviewer4');

