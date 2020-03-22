INSERT INTO user_accounts(id,username,password,enabled) VALUES (1,'RAIMUNDOKARATE98','passwordderaimundokarate98',TRUE);
INSERT INTO threads VALUES (4,'Thread de la League de Silver', 'SILVER');
INSERT INTO leagues(id,name,thread_id) VALUES (1,'SILVER',4);
INSERT INTO summoners(id,email,user_account_id,ligue_id) VALUES (1,'raimundokarate@gmail.com',1,1);


INSERT INTO threads VALUES (1,'Creo que Maestro Yi está over powereado. Estoy en una liga baja y no sé jugar contra alguien que elija este champion','Yi OP');
INSERT INTO threads VALUES (2,'Os dejo esta build que recomiendo para jugar Shaco con el rol de Support','Build para jugar con Shaco support');
INSERT INTO threads VALUES (3,'Description de ejemplo','Thread de ejemplo');

INSERT INTO messages(id,moment,text,summoner_id,thread_id) VALUES(1,'2020-03-07','No pienso que Yi esté tan OP simplemente necesitais stunnearlo 8 segundos',1,1);

