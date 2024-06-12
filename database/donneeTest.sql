INSERT INTO typeUser VALUES ( 1, 'Capitainnerie');
INSERT INTO typeUser VALUES ( 2, 'Chef capitainnerie');
INSERT INTO typeUser VALUES ( 3, 'Facturation');
INSERT INTO typeUser VALUES ( 4, 'Chef facturation');

INSERT INTO users VALUES (DEFAULT, 'Rakoto', 1);
INSERT INTO users VALUES (DEFAULT, 'Randria', 2);
INSERT INTO users VALUES (DEFAULT, 'Ravao', 3);
INSERT INTO users VALUES (DEFAULT, 'Rabeza', 4);


INSERT INTO categorieBateau VALUES (1, 'Petrolier');
INSERT INTO categorieBateau VALUES (2, 'Paquebot');

INSERT INTO pavillon VALUES (DEFAULT, 'International');
INSERT INTO pavillon VALUES (DEFAULT, 'National');

INSERT INTO bateau VALUES (DEFAULT, 1, 1, 'PETROLIER', 30.0, 20.0, 200.0);
INSERT INTO bateau VALUES (DEFAULT, 2, 2, 'PAQUEBOT', 25.5, 120.0, 150.0);

INSERT INTO quai VALUES (DEFAULT, 'Quai 1', 30.0, true);

INSERT INTO typePrestation VALUES (1, 'Stationnement');
INSERT INTO typePrestation VALUES (2, 'Remorquage');
INSERT INTO typePrestation VALUES (3, 'cession eau');
INSERT INTO typePrestation VALUES (4, 'Reparation');

INSERT INTO etatValidation VALUES(DEFAULT, 1);
INSERT INTO etatValidation VALUES(DEFAULT, 11);
INSERT INTO etatValidation VALUES(DEFAULT, 20);

INSERT INTO coursEchange VALUES (DEFAULT, 4500.0, 1.0, '2023-01-01');
INSERT INTO coursEchange VALUES (DEFAULT, 5000.0, 1.0, '2023-06-01');

/*Stationnement*/
/*heureDebut, heureFin, trancheInf, trancheSup,typePrestation, categorieBateau, pavillon, quai, tarif*/
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 15, 1, 1, 2, 1, 100000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 15, 30, 1, 1, 2, 1, 150000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, 45, 1, 1, 2, 1, 200000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 45, 60, 1, 1, 2, 1, 250000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 60, -1, 1, 1, 2, 1, 50000.0);

INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 15, 1, 1, 1, 1, 500.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 15, 30, 1, 1, 1, 1, 800.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, 45, 1, 1, 1, 1, 1000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 45, 60, 1, 1, 1, 1, 1500.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 60, -1, 1, 1, 1, 1, 200.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 0, 15, 1, 1, 2, 1, 150000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 15, 30, 1, 1, 2, 1, 200000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 30, 45, 1, 1, 2, 1, 250000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 45, 60, 1, 1, 2, 1, 300000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 60, -1, 1, 1, 2, 1, 60000.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 0, 15, 1, 1, 1, 1, 800.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 15, 30, 1, 1, 1, 1, 1000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 30, 45, 1, 1, 1, 1, 1500.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 45, 60, 1, 1, 1, 1, 2000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 60, -1, 1, 1, 1, 1, 250.0);


/*Remorquage*/
/*Petrolier*/
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 10, 2, 1, 2, 1, 100000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 10, 20, 2, 1, 2, 1, 150000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 20, 30, 2, 1, 2, 1, 200000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, -1, 2, 1, 2, 1, 20000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 10, 2, 1, 1, 1, 500.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 10, 20, 2, 1, 1, 1, 800.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 20, 30, 2, 1, 1, 1, 1000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, -1, 2, 1, 1, 1, 100.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 0, 10, 2, 1, 2, 1, 150000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 10, 20, 2, 1, 2, 1, 200000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 20, 30, 2, 1, 2, 1, 250000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 30, -1, 2, 1, 2, 1, 25000.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 0, 10, 2, 1, 1, 1, 600.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 10, 20, 2, 1, 1, 1, 900.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 20, 30, 2, 1, 1, 1, 1100.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 30, -1, 2, 1, 1, 1, 200.0);

/*Paquebot*/
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 10, 2, 1, 2, 1, 150000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 10, 20, 2, 1, 2, 1, 200000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 20, 30, 2, 1, 2, 1, 25000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, -1, 2, 1, 2, 1, 20000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 10, 2, 1, 1, 1, 800.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 10, 20, 2, 1, 1, 1, 1000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 20, 30, 2, 1, 1, 1, 1200.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, -1, 2, 1, 1, 1, 100.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 0, 10, 2, 1, 2, 1, 200000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 10, 20, 2, 1, 2, 1, 250000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 20, 30, 2, 1, 2, 1, 300000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 30, -1, 2, 1, 2, 1, 25000.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 0, 10, 2, 1, 1, 1, 900.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 10, 20, 2, 1, 1, 1, 1100.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 20, 30, 2, 1, 1, 1, 1300.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 0, 30, -1, 2, 1, 1, 1, 150.0);


/*Reparation*/
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 15, 4, 1, 2, 1, 10000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 15, 30, 4, 1, 2, 1, 15000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, 45, 4, 1, 2, 1, 20000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 45, 60, 4, 1, 2, 1, 25000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 60, -1, 4, 1, 2, 1, 15000.0);

INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 0, 15, 4, 1, 1, 1, 50.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 15, 30, 4, 1, 1, 1, 80.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 30, 45, 4, 1, 1, 1, 100.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 45, 60, 4, 1, 1, 1, 120.0);
INSERT INTO configPrestation VALUES (DEFAULT, 0, 18, 60, -1, 4, 1, 1, 1, 70.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 0, 15, 4, 1, 2, 1, 15000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 15, 30, 4, 1, 2, 1, 20000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 30, 45, 4, 1, 2, 1, 25000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 45, 60, 4, 1, 2, 1, 30000.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 60, -1, 4, 1, 2, 1, 20000.0);

INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 0, 15, 4, 1, 1, 1, 100.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 15, 30, 4, 1, 1, 1, 110.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 30, 45, 4, 1, 1, 1, 120.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 45, 60, 4, 1, 1, 1, 130.0);
INSERT INTO configPrestation VALUES (DEFAULT, 18, 24, 60, -1, 4, 1, 1, 1, 90.0);


/*Ravitail en eau*/
/*Tranche  fixe*/
INSERT INTO configPrestation VALUES (DEFAULT, 6, 5, 0, 0, 3, 1, 1, 1, 1.5);
INSERT INTO configPrestation VALUES (DEFAULT, 6, 5, 0, 0, 3, 1, 2, 1, 500.0);
INSERT INTO configPrestation VALUES (DEFAULT, 6, 5, 0, 0, 3, 2, 1, 1, 1.5);
INSERT INTO configPrestation VALUES (DEFAULT, 6, 5, 0, 0, 3, 2, 2, 1, 500.0);
