INSERT INTO threads VALUES(1, 'Bronze thread', 'Bronze title');

INSERT INTO leagues VALUES(1, 'Bronze', 1);

INSERT INTO user_accounts(id, username, password, enabled) VALUES(1, 'admin', 'admin', true);

INSERT INTO user_accounts(id, username, password, enabled) VALUES(11, 'summoner1', 'summoner1', true);
INSERT INTO user_accounts(id, username, password, enabled) VALUES(12, 'summoner2', 'summoner2', true);
INSERT INTO user_accounts(id, username, password, enabled) VALUES(13, 'summoner3', 'summoner3', true);
INSERT INTO user_accounts(id, username, password, enabled) VALUES(14, 'summoner4', 'summoner4', true);

INSERT INTO user_accounts(id, username, password, enabled) VALUES(21, 'reviewer1', 'reviewer1', true);
INSERT INTO user_accounts(id, username, password, enabled) VALUES(22, 'reviewer2', 'reviewer2', true);
INSERT INTO user_accounts(id, username, password, enabled) VALUES(23, 'reviewer3', 'reviewer3', true);
INSERT INTO user_accounts(id, username, password, enabled) VALUES(24, 'reviewer4', 'reviewer4', true);

INSERT INTO user_account_authorities VALUES(1, 'ADMINISTRATOR');

INSERT INTO user_account_authorities VALUES(11, 'SUMMONER');
INSERT INTO user_account_authorities VALUES(12, 'SUMMONER');
INSERT INTO user_account_authorities VALUES(13, 'SUMMONER');
INSERT INTO user_account_authorities VALUES(14, 'SUMMONER');

INSERT INTO user_account_authorities VALUES(21, 'REVIEWER');
INSERT INTO user_account_authorities VALUES(22, 'REVIEWER');
INSERT INTO user_account_authorities VALUES(23, 'REVIEWER');
INSERT INTO user_account_authorities VALUES(24, 'REVIEWER');

INSERT INTO administrators VALUES(1, 'admin@gmail.com', 1);

INSERT INTO summoners VALUES(1, 'summoner1@gmail.com', 11, 1);
INSERT INTO summoners VALUES(2, 'summoner2@gmail.com', 12, 1);
INSERT INTO summoners VALUES(3, 'summoner3@gmail.com', 13, 1);
INSERT INTO summoners VALUES(4, 'summoner4@gmail.com', 14, 1);

INSERT INTO reviewers VALUES (1, 'reviewer1@gmail.com', 21);
INSERT INTO reviewers VALUES (2, 'reviewer2@gmail.com', 22);
INSERT INTO reviewers VALUES (3, 'reviewer3@gmail.com', 23);
INSERT INTO reviewers VALUES (4, 'reviewer4@gmail.com', 24);

