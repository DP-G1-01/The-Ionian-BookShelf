INSERT INTO threads 
            (id, 
             description, 
             title) 
VALUES     (1, 
            'The IonianBookshelf´s Basic Thread', 
            'Basic Thread'); 

INSERT INTO threads 
            (id, 
             description, 
             title) 
VALUES     (2, 
            'The IonianBookshelf´s Bronze Thread', 
            'Bronze thread'); 

INSERT INTO threads 
            (id, 
             description, 
             title) 
VALUES      (3, 
             'Thread de la League de Silver', 
             'SILVER'); 

INSERT INTO threads 
            (id, 
             description, 
             title) 
VALUES      (4, 
			'Creo que Maestro Yi está over powereado. Estoy en una liga baja y no sé jugar contra alguien que elija este champion', 
			'Yi OP'); 

INSERT INTO threads 
            (id, 
             description, 
             title) 
VALUES      (5, 
'Os dejo esta build que recomiendo para jugar Shaco con el rol de Support', 
'Build para jugar con Shaco support'); 

INSERT INTO threads 
            (id, 
             description, 
             title) 
VALUES      (6, 
             'Description de ejemplo', 
             'Thread de ejemplo'); 
             
INSERT INTO threads
			(id,
			 description,
			 title)
VALUES		(100,
			 'Test Thread for MessageServiceTest',
			 'Test Thread');

INSERT INTO leagues 
            (id, 
             NAME, 
             thread_id) 
VALUES      (1, 
             'Basic', 
             1); 

INSERT INTO leagues 
            (id, 
             NAME, 
             thread_id) 
VALUES      (2, 
             'Bronze', 
             2); 

INSERT INTO leagues 
            (id, 
             NAME, 
             thread_id) 
VALUES      (3, 
             'Silver', 
             3); 

INSERT INTO authorities 
VALUES      ('admin', 
             'administrator'); 

INSERT INTO authorities 
VALUES      ('summoner1', 
             'summoner'); 

INSERT INTO authorities 
VALUES      ('summoner2', 
             'summoner'); 

INSERT INTO authorities 
VALUES      ('summoner3', 
             'summoner'); 

INSERT INTO authorities 
VALUES      ('summoner4', 
             'summoner'); 

INSERT INTO authorities 
VALUES      ('RAIMUNDOKARATE98', 
             'summoner'); 

INSERT INTO authorities 
VALUES      ('reviewer1', 
             'reviewer'); 

INSERT INTO authorities 
VALUES      ('reviewer2', 
             'reviewer'); 

INSERT INTO authorities 
VALUES      ('reviewer3', 
             'reviewer'); 

INSERT INTO authorities 
VALUES      ('reviewer4', 
             'reviewer'); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('admin', 
             'admin', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('summoner1', 
             'summoner1', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('summoner2', 
             'summoner2', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('summoner3', 
             'summoner3', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('summoner4', 
             'summoner4', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('reviewer1', 
             'reviewer1', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('reviewer2', 
             'reviewer2', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('reviewer3', 
             'reviewer3', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('reviewer4', 
             'reviewer4', 
             true); 

INSERT INTO users 
            (username, 
             password, 
             enabled) 
VALUES      ('RAIMUNDOKARATE98', 
             'raikonen', 
             true); 

INSERT INTO administrators 
VALUES     (1, 
            'admin@gmail.com', 
            'admin'); 

INSERT INTO summoners 
VALUES     (1, 
            'summoner1@gmail.com', false,
            'summoner1', 
            1); 

INSERT INTO summoners 
VALUES     (2, 
            'summoner2@gmail.com', false,
            'summoner2', 
            1); 

INSERT INTO summoners 
VALUES     (3, 
            'summoner3@gmail.com', false,
            'summoner3', 
            1); 

INSERT INTO summoners 
VALUES     (4, 
            'summoner4@gmail.com', true,
            'summoner4', 
            1); 

INSERT INTO summoners 
VALUES      (5, 
             'raimundokarate@gmail.com', false,
             'RAIMUNDOKARATE98', 
             3); 

INSERT INTO messages 
            (id, 
             moment, 
             text, 
             summoner_id, 
             thread_id) 
VALUES      (1, 
             '2010-10-10', 
'No pienso que Yi esté tan OP simplemente necesitais stunnearlo 8 segundos', 
5, 
4); 

INSERT INTO messages 
            (id, 
             moment, 
             text, 
             summoner_id, 
             thread_id) 
VALUES      (100, 
             '2010-10-10', 
'Positive Tet Text', 
5, 
100); 


INSERT INTO reviewers
VALUES
    (1, 'reviewer1@gmail.com', 'reviewer1');
INSERT INTO reviewers
VALUES
    (2, 'reviewer2@gmail.com', 'reviewer2');
INSERT INTO reviewers
VALUES
    (3, 'reviewer3@gmail.com', 'reviewer3');
INSERT INTO reviewers
VALUES
    (4, 'reviewer4@gmail.com', 'reviewer4');
    
INSERT INTO roles(id,description,image,name) VALUES(1,'Especializado en combate cercano. Combinan objetos de daño con objetos de aguante. También se los conoce como offtank','http://www.googleimagenes.es','Luchador');
INSERT INTO roles(id,description,image,name) VALUES(2,'Especializado en ataque a distancia. Popularmente conocido como AD Carry del inglés attack damage carry o carreador de daño de ataque','http://www.googleimagenes.es','Tirador');
INSERT INTO roles(id,description,image,name) VALUES(3,'Especializado en habilidades y daño mágico. Popularmente conocido como AP Carry del inglés ability power carry o carreador de poder de habilidad.','http://www.googleimagenes.es','Mago');
INSERT INTO roles(id,description,image,name) VALUES(4,'Especializado en emboscar al enemigo, atacando por sorpresa y retirándose rápidamente. Su misión es eliminar al jugador más importante del equipo enemigo (generalmente el tirador) aunque ello suponga morir él también.','http://googleimagenes.es','Asesino');
INSERT INTO roles(id,description,image,name) VALUES(5,'Especializado en resistencia. Su misión es absorber la mayor cantidad de daño posible e iniciar las peleas.','http://googleimagenes.es','Tanque');
INSERT INTO roles(id,description,image,name) VALUES(6,'Especializado en apoyar a su equipo y aportar visión en el mapa mediante guardianes de visión (wards).','http://googleimagenes.es','Apoyo');

INSERT INTO champions VALUES (1,'1.10','Ashe, comandante hija del hielo de la tribu de Avarosa, lidera las hordas más numerosas del norte. Impasible, inteligente e idealista, aunque incómoda en su papel de líder',null,'900','500','Ashe','1.0','2');
INSERT INTO champions VALUES (2,'0.90','Blitzcrank es un autómata enorme, casi indestructible, creado originalmente para el tratamiento de residuos tóxicos.',null,'1500','500','Blitzcrank ','1.0','6');
INSERT INTO champions VALUES (3,'1.30','Convertida en un arma viviente diseñada para operar fuera de la ley, Camille es la jefa de espías del clan Ferros, una elegante agente de élite que se asegura de que nada amenace el funcionamiento de Piltover ni de Zaun.',null,'1000','600','Camile','1.2','1');
INSERT INTO champions VALUES (4,'0.90','Desde el momento en que ChoGath emergió por primera vez a la dura luz solar de Runaterra, a la bestia solo le impulsaba el hambre más pura e insaciable.',null,'1700','500','Cho,Gath ','1.0','5');
INSERT INTO champions VALUES (5,'1.25','Karthus, heraldo del olvido, es un espíritu inmortal cuyos pavorosos cantos preceden al horror de su dantesca aparición.',null,'900','500','Karthus','1.0','3');
INSERT INTO champions VALUES (6,'1.10','Imbuida del fuego del sol, Leona es una guerrera sagrada de los Solari que defiende el Monte Targon con su Hoja del cénit y su Escudo del amanecer.',null,'1300','500','Leona','1.1','6');
INSERT INTO champions VALUES (7,'1.25','Maestro Yi ha atemperado su cuerpo y agudizado su mente, de modo que el pensamiento y la acción se han convertido casi en uno.',null,'1200','500','Maestro Yi','1.125','4');
INSERT INTO champions VALUES (8,'1.40','Otrora delincuente en las perversas calles de Zaun, Vi es una mujer impulsiva e imponente con muy poco respeto por las figuras de la autoridad.',null,'1400','600','Vi','1.2','1');
INSERT INTO champions VALUES (9,'1.00','Un demonio con sed de sangre mortal, Vladimir ha influido en el destino de Noxus desde los primeros días del imperio.','400','1200',null,'Vladimir','1.1','3');
INSERT INTO champions VALUES (10,'1.30','Zed, despiadado y nada compasivo, es el líder de la Orden de la Sombra, una organización que él mismo creó con el propósito de militarizar las tradiciones marciales y mágicas de Jonia','400','1200',null,'Zed','1.2','3');

INSERT INTO champions VALUES(11, 5.0, 'Soy un campeón sin mana, pero con energía', 3.0, 1000.0, null, 'Kiko Matafachas', 40.0, 1);

INSERT INTO items VALUES(10, 'Soy un item', 'Item 1');
INSERT INTO item_roles VALUES(10, 1);
INSERT INTO item_attributes VALUES(10, '1');
INSERT INTO item_attributes VALUES(10, '6');
INSERT INTO item_attributes VALUES(10, '0');

INSERT INTO change_requests VALUES(1, 'Soy una prueba que debe de tener 20 caracteres porquesi', 'PENDING', 'Gran titulo', 1, null, null, 1);
INSERT INTO ChangeRequest_changeChamp VALUES(1,'1');
INSERT INTO ChangeRequest_changeChamp VALUES(1,'5');
INSERT INTO ChangeRequest_changeChamp VALUES(1,'4');
INSERT INTO ChangeRequest_changeChamp VALUES(1,'2');
INSERT INTO ChangeRequest_changeChamp VALUES(1,'1');

INSERT INTO change_requests VALUES(2, 'Soy una prueba que debe de tener 20 caracteres porquesi', 'PENDING', 'Gran titulo clonado', 1, null, null, 1);
INSERT INTO ChangeRequest_changeChamp VALUES(2,'1');
INSERT INTO ChangeRequest_changeChamp VALUES(2,'5');
INSERT INTO ChangeRequest_changeChamp VALUES(2,'4');
INSERT INTO ChangeRequest_changeChamp VALUES(2,'2');
INSERT INTO ChangeRequest_changeChamp VALUES(2,'1');

INSERT INTO branches VALUES(1,'Slay your enemies in long term combats','https://lolstatic-a.akamaihd.net/frontpage/apps/prod/preseason-2018/es_ES/a6708b7ae3dbc0b25463f9c8e259a513d2c4c7e6/assets/img/runeBuilder/share/8000-8008.jpg','Precision');
INSERT INTO branches VALUES(2,'Slay your enemies in short combats','https://lolstatic-a.akamaihd.net/frontpage/apps/prod/preseason-2018/es_ES/a6708b7ae3dbc0b25463f9c8e259a513d2c4c7e6/assets/img/runeBuilder/share/8100-8128.jpg','Domination');
INSERT INTO branches VALUES(3,'Stay alive in long term combats','https://blog.gamersensei.com/wp-content/uploads/2017/12/RR102-Header.jpg','Resolve');

INSERT INTO runes VALUES(1,'PASSIVE: You get AP and AD for every second you are in combat','Conqueror','Key',1);
INSERT INTO runes VALUES(2,'PASSIVE: When you hit 3 times a champion, he receives 20% more damage for 3 seconds','Press the Attack','Key',1);
INSERT INTO runes VALUES(3,'PASSIVE: When you enter in combat, your Attack Speed gets boosted for 2 seconds','Lethal Tempo','Key',1);
INSERT INTO runes VALUES(4,'PASSIVE: Once every 3 seconds, you have a charged attack that heals you and raises your Movement Speed','Fleet Footwork','Key',1);
INSERT INTO runes VALUES(5,'PASSIVE: When you try to heal yourself with 100% maximum health, you get a shield','Overheal','1',1);
INSERT INTO runes VALUES(6,'PASSIVE: Whenever you kill an enemy champion, you heal 5% of your maximum health','Triumph','1',1);
INSERT INTO runes VALUES(7,'PASSIVE: Whenever you kill an enemy champion, you get 500 more maximum mana, up to 2500','Presence of Mind','1',1);
INSERT INTO runes VALUES(8,'PASSIVE: You gain Attack Speed every time you kill a minion or an enemy champion','Legend: Alacrity','2',1);
INSERT INTO runes VALUES(9,'PASSIVE: You gain Tenacity every time you kill a minion or an enemy champion','Legend: Tenacity','2',1);
INSERT INTO runes VALUES(10,'PASSIVE: You gain Life Steal every time you kill a minion or an enemy champion','Legend: Bloodline','2',1);
INSERT INTO runes VALUES(11,'PASSIVE: When an enemy champion is below 40% of his maximum health, you deal 15% more damage to him','Coup De Grace','3',1);
INSERT INTO runes VALUES(12,'PASSIVE: You deal more damage to enemies with more maximum health than you','Cut Down','3',1);
INSERT INTO runes VALUES(13,'PASSIVE: When you are below 60% maximum health, you deal 11% more damage','Last Stand','3',1);

INSERT INTO runes VALUES(14,'PASSIVE: When you hit 3 times an enemy champion, a lightning strikes him, dealing damage','Electrocute','Key',2);
INSERT INTO runes VALUES(15,'ACTIVE: You can activate your boots to boost your Movement Speed when heading towards an enemy champion','Predator','Key',2);
INSERT INTO runes VALUES(16,'PASSIVE: When you hit an enemy below 50% maximum health, you harvest his soul and deal more damage to him','Dark Harvest','Key',2);
INSERT INTO runes VALUES(17,'PASSIVE: When you enter combat, your next 3 basic attacks get an Attack Speed boost','Hail of Blades','Key',2);
INSERT INTO runes VALUES(18,'PASSIVE: Once every 3 seconds, your next ability that damages a champion deals 30 aditional True Damage','Cheap Shot','1',2);
INSERT INTO runes VALUES(19,'PASSIVE: Once every 3 seconds, your next ability that damages a champion heals you 30 health points','Taste of Blood','1',2);
INSERT INTO runes VALUES(20,'PASSIVE: When you dash, you get bonus Armor and Magic Resistance penetration','Sudden Impact','1',2);
INSERT INTO runes VALUES(21,'PASSIVE: When you kill a ward, a zombie ward spawns in that position, giving you more damage','Zombie Ward','2',2);
INSERT INTO runes VALUES(22,'PASSIVE: When your wards expire, they leave a Ghost Poro, which give you vision and damage','Ghost Poro','2',2);
INSERT INTO runes VALUES(23,'PASSIVE: When you kill for the first time an enemy champion, you gain damage permanently','Eyeball Collection','2',2);
INSERT INTO runes VALUES(24,'PASSIVE: When you kill for the first time an enemy champion, you gain Life Steal permanently','Ravenous Hunter','3',2);
INSERT INTO runes VALUES(25,'PASSIVE: When you kill for the first time an enemy champion, you gain item''s cooldown reduction permanently','Ingenious Hunter','3',2);
INSERT INTO runes VALUES(26,'PASSIVE: When you kill for the first time an enemy champion, you gain Movement Speed permanently','Relentless Hunter','3',2);
INSERT INTO runes VALUES(27,'PASSIVE: When you kill for the first time an enemy champion, you gain ultimate''s cooldown reduction permanently','Ultimate Hunter','3',2);

INSERT INTO runes VALUES(28,'PASSIVE: Every 5 seconds, your next attack with heal you and you will gain 20 maximum health','Grasp of the Undying','Key',3);
INSERT INTO runes VALUES(29,'PASSIVE: Immobilizing an enemy champion grants you more Armor and Magic Resistance for 2.5 seconds','Aftershock','Key',3);
INSERT INTO runes VALUES(30,'PASSIVE: After 3 seconds being near a structure, your next attack will deal more damage to it','Demolish','1',3);
INSERT INTO runes VALUES(31,'PASSIVE: Immobilizing an enemy champion marks him for 4 seconds, allied champions who deal damage to him will heal themselves','Font of Life','1',3);
INSERT INTO runes VALUES(32,'PASSIVE: After 10 minutes, you gain 10 Armor and Magic Resistance and your total resistances are increased by 5%','Conditioning','2',3);
INSERT INTO runes VALUES(33,'PASSIVE: After taking damage from an enemy champion, gain health regeneration based on your missing health over 10 seconds','Second Wind','2',3);
INSERT INTO runes VALUES(34,'PASSIVE: Every 8 minions that die near you permanently grants 3 bonus health','Overgrowth','3',3);
INSERT INTO runes VALUES(35,'PASSIVE: Grants 5% heal and shield power, incoming healing and shielding is increased by 10%','Revitalize','3',3);

INSERT INTO rune_pages (id, name, summoner_id, main_branch_id, secondary_branch_id, keyrune_id, mainrune1_id, mainrune2_id, mainrune3_id, 
secrune1_id, secrune2_id) VALUES(1, 'Precision rune page', 1, 1, 2, 1, 5, 8, 12, 18, 21);

INSERT INTO items VALUES(1, 'descripcion1', 'titulo1');
INSERT INTO items VALUES(2, 'Mayor velocidad de ataque y reducción de enfriamiento', 'Aguijón');
INSERT INTO items VALUES(3, 'Aumenta levemente la regeneración de maná', 'Amuleto de las hadas');
INSERT INTO items VALUES(4, 'Hace que el daño recibido ahora se reciba más adelante', 'Baile de la muerte');
INSERT INTO items VALUES(5, 'Velocidad de movimiento aumentada al atacar enemigos y obtienes un escudo con la vida baja', 'Bailarín espectral');

INSERT INTO item_attributes VALUES(1,'5');
INSERT INTO item_attributes VALUES(1,'0');
INSERT INTO item_attributes VALUES(1,'3');
INSERT INTO item_attributes VALUES(2,'2');
INSERT INTO item_attributes VALUES(2,'1');
INSERT INTO item_attributes VALUES(2,'1');
INSERT INTO item_attributes VALUES(3,'0');
INSERT INTO item_attributes VALUES(3,'3');
INSERT INTO item_attributes VALUES(3,'4');
INSERT INTO item_attributes VALUES(4,'0');
INSERT INTO item_attributes VALUES(4,'5');
INSERT INTO item_attributes VALUES(4,'1');
INSERT INTO item_attributes VALUES(5,'2');
INSERT INTO item_attributes VALUES(5,'3');
INSERT INTO item_attributes VALUES(5,'3');

INSERT INTO item_roles(item_id,role_id) VALUES(1,1);
INSERT INTO item_roles(item_id,role_id) VALUES(1,2);
INSERT INTO item_roles(item_id,role_id) VALUES(2,2);
INSERT INTO item_roles(item_id,role_id) VALUES(3,1);
INSERT INTO item_roles(item_id,role_id) VALUES(4,2);
INSERT INTO item_roles(item_id,role_id) VALUES(5,1);
INSERT INTO item_roles(item_id,role_id) VALUES(5,2);
INSERT INTO item_roles(item_id,role_id) VALUES(5,3);

INSERT INTO builds (id, description, title, visibility, champion_id, rune_page_id, summoner_id, thread_id, punctuation ) VALUES(1,'Soy la primera build de tus sueños y soy publica, lo juro un rato.', 'Build publica', true, 1, 1, 1, 5, null);
INSERT INTO builds (id, description, title, visibility, champion_id, rune_page_id, summoner_id, thread_id, punctuation ) VALUES(2,'Soy la segunda build de tus sueños y soy publica, lo juro un rato.', 'Build publica2', true, 1, 1, 2, 6, null);
INSERT INTO builds (id, description, title, visibility, champion_id, rune_page_id, summoner_id, thread_id, punctuation ) VALUES(3,'Soy la tercera build de tus sueños y soy privada, respeta, porfavor.', 'Build privada', false, 2, 1, 1, null, null);
INSERT INTO builds (id, description, title, visibility, champion_id, rune_page_id, summoner_id, thread_id, punctuation ) VALUES(4,'Soy la cuarta build de tus sueños y soy privada, respeta, porfavor.', 'Build privada2', false, 2, 1, 2, null, null);
INSERT INTO builds (id, description, title, visibility, champion_id, rune_page_id, summoner_id, thread_id, punctuation ) VALUES(100,'Build for BuildServiceTests', 'Build for tests', true, 2, 1, 1, null, null);

INSERT INTO build_items VALUES(1, 1);
INSERT INTO build_items VALUES(1, 2);
INSERT INTO build_items VALUES(2, 5);
INSERT INTO build_items VALUES(2, 3);
INSERT INTO build_items VALUES(3, 3);
