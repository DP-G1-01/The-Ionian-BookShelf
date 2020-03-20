INSERT INTO BRANCHES VALUES(1,'Slay your enemies in long term combats','https://lolstatic-a.akamaihd.net/frontpage/apps/prod/preseason-2018/es_ES/a6708b7ae3dbc0b25463f9c8e259a513d2c4c7e6/assets/img/runeBuilder/share/8000-8008.jpg','Precision');
INSERT INTO BRANCHES VALUES(2,'Slay your enemies in short combats','https://lolstatic-a.akamaihd.net/frontpage/apps/prod/preseason-2018/es_ES/a6708b7ae3dbc0b25463f9c8e259a513d2c4c7e6/assets/img/runeBuilder/share/8100-8128.jpg','Domination');
INSERT INTO BRANCHES VALUES(3,'Stay alive in long term combats','https://blog.gamersensei.com/wp-content/uploads/2017/12/RR102-Header.jpg','Resolve');

INSERT INTO RUNES VALUES(1,'PASSIVE: You get AP and AD for every second you are in combat','Conqueror','Key',1);
INSERT INTO RUNES VALUES(2,'PASSIVE: When you hit 3 times a champion, he receives 20% more damage for 3 seconds','Press the Attack','Key',1);
INSERT INTO RUNES VALUES(3,'PASSIVE: When you enter in combat, your Attack Speed gets boosted for 2 seconds','Lethal Tempo','Key',1);
INSERT INTO RUNES VALUES(4,'PASSIVE: Once every 3 seconds, you have a charged attack that heals you and raises your Movement Speed','Fleet Footwork','Key',1);
INSERT INTO RUNES VALUES(5,'PASSIVE: When you try to heal yourself with 100% maximum health, you get a shield','Overheal','1',1);
INSERT INTO RUNES VALUES(6,'PASSIVE: Whenever you kill an enemy champion, you heal 5% of your maximum health','Triumph','1',1);
INSERT INTO RUNES VALUES(7,'PASSIVE: Whenever you kill an enemy champion, you get 500 more maximum mana, up to 2500','Presence of Mind','1',1);
INSERT INTO RUNES VALUES(8,'PASSIVE: You gain Attack Speed every time you kill a minion or an enemy champion','Legend: Alacrity','2',1);
INSERT INTO RUNES VALUES(9,'PASSIVE: You gain Tenacity every time you kill a minion or an enemy champion','Legend: Tenacity','2',1);
INSERT INTO RUNES VALUES(10,'PASSIVE: You gain Life Steal every time you kill a minion or an enemy champion','Legend: Bloodline','2',1);
INSERT INTO RUNES VALUES(11,'PASSIVE: When an enemy champion is below 40% of his maximum health, you deal 15% more damage to him','Coup De Grace','3',1);
INSERT INTO RUNES VALUES(12,'PASSIVE: You deal more damage to enemies with more maximum health than you','Cut Down','3',1);
INSERT INTO RUNES VALUES(13,'PASSIVE: When you are below 60% maximum health, you deal 11% more damage','Last Stand','3',1);

INSERT INTO RUNES VALUES(14,'PASSIVE: When you hit 3 times an enemy champion, a lightning strikes him, dealing damage','Electrocute','Key',2);
INSERT INTO RUNES VALUES(15,'ACTIVE: You can activate your boots to boost your Movement Speed when heading towards an enemy champion','Predator','Key',2);
INSERT INTO RUNES VALUES(16,'PASSIVE: When you hit an enemy below 50% maximum health, you harvest his soul and deal more damage to him','Dark Harvest','Key',2);
INSERT INTO RUNES VALUES(17,'PASSIVE: When you enter combat, your next 3 basic attacks get an Attack Speed boost','Hail of Blades','Key',2);
INSERT INTO RUNES VALUES(18,'PASSIVE: Once every 3 seconds, your next ability that damages a champion deals 30 aditional True Damage','Cheap Shot','1',2);
INSERT INTO RUNES VALUES(19,'PASSIVE: Once every 3 seconds, your next ability that damages a champion heals you 30 health points','Taste of Blood','1',2);
INSERT INTO RUNES VALUES(20,'PASSIVE: When you dash, you get bonus Armor and Magic Resistance penetration','Sudden Impact','1',2);
INSERT INTO RUNES VALUES(21,'PASSIVE: When you kill a ward, a zombie ward spawns in that position, giving you more damage','Zombie Ward','2',2);
INSERT INTO RUNES VALUES(22,'PASSIVE: When your wards expire, they leave a Ghost Poro, which give you vision and damage','Ghost Poro','2',2);
INSERT INTO RUNES VALUES(23,'PASSIVE: When you kill for the first time an enemy champion, you gain damage permanently','Eyeball Collection','2',2);
INSERT INTO RUNES VALUES(24,'PASSIVE: When you kill for the first time an enemy champion, you gain Life Steal permanently','Ravenous Hunter','3',2);
INSERT INTO RUNES VALUES(25,'PASSIVE: When you kill for the first time an enemy champion, you gain item''s cooldown reduction permanently','Ingenious Hunter','3',2);
INSERT INTO RUNES VALUES(26,'PASSIVE: When you kill for the first time an enemy champion, you gain Movement Speed permanently','Relentless Hunter','3',2);
INSERT INTO RUNES VALUES(27,'PASSIVE: When you kill for the first time an enemy champion, you gain ultimate''s cooldown reduction permanently','Ultimate Hunter','3',2);

INSERT INTO RUNES VALUES(28,'PASSIVE: Every 5 seconds, your next attack with heal you and you will gain 20 maximum health','Grasp of the Undying','Key',3);
INSERT INTO RUNES VALUES(29,'PASSIVE: Immobilizing an enemy champion grants you more Armor and Magic Resistance for 2.5 seconds','Aftershock','Key',3);
INSERT INTO RUNES VALUES(30,'PASSIVE: After 3 seconds being near a structure, your next attack will deal more damage to it','Demolish','1',3);
INSERT INTO RUNES VALUES(31,'PASSIVE: Immobilizing an enemy champion marks him for 4 seconds, allied champions who deal damage to him will heal themselves','Font of Life','1',3);
INSERT INTO RUNES VALUES(32,'PASSIVE: After 10 minutes, you gain 10 Armor and Magic Resistance and your total resistances are increased by 5%','Conditioning','2',3);
INSERT INTO RUNES VALUES(33,'PASSIVE: After taking damage from an enemy champion, gain health regeneration based on your missing health over 10 seconds','Second Wind','2',3);
INSERT INTO RUNES VALUES(34,'PASSIVE: Every 8 minions that die near you permanently grants 3 bonus health','Overgrowth','3',3);
INSERT INTO RUNES VALUES(35,'PASSIVE: Grants 5% heal and shield power, incoming healing and shielding is increased by 10%','Revitalize','3',3);


INSERT INTO THREADS VALUES(1, 'Bronze League thread', 'Bronze League');
INSERT INTO LEAGUES VALUES(1, 'Bronze', 1);
INSERT INTO USER_ACCOUNTS VALUES(1, 1, 'summoner1', 'summoner1');
INSERT INTO SUMMONERS VALUES(1, 'pepe@gmail.com', 1, 1);
INSERT INTO RUNE_PAGES VALUES(1, 'Precision rune page', 1, 5, 8, 12, 18, 21, 1);


INSERT INTO ROLES VALUES(1,'Descripcion','https://www.google.es','Rol1');
INSERT INTO ROLES VALUES(2,'Descripcion','https://www.google.es','Rol2');

INSERT INTO ITEMS VALUES(1, 'descripcion1', 'titulo1');

INSERT INTO ITEM_ATTRIBUTES VALUES(1,'Atributo');
INSERT INTO ITEM_ATTRIBUTES VALUES(1,'Atributo');

INSERT INTO ITEM_ROLES(item_id,role_id) VALUES(1,1);