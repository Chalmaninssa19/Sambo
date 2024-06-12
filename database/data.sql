create database sambo;
.\c sambo;

create sequence idTypeUserSeq START WITH 1 INCREMENT BY 1;
create sequence idUserSeq START WITH 1 INCREMENT BY 1;
create sequence idCategorieBateauSeq START WITH 1 INCREMENT BY 1;
create sequence idPavillonSeq START WITH 1 INCREMENT BY 1;
create sequence idBateauSeq START WITH 1 INCREMENT BY 1;
create sequence idQuaiSeq START WITH 1 INCREMENT BY 1;
create sequence idPrevisionSeq START WITH 1 INCREMENT BY 1;
create sequence idPropositionSeq START WITH 1 INCREMENT BY 1;
create sequence idConfigPrestationSeq START WITH 1 INCREMENT BY 1;
create sequence idPropositionSeq START WITH 1 INCREMENT BY 1;
create sequence idEscaleSeq START WITH 1 INCREMENT BY 1;
create sequence idPrestationSeq START WITH 1 INCREMENT BY 1;
create sequence idValidationPrestationSeq START WITH 1 INCREMENT BY 1;
create sequence idValidationFactureSeq START WITH 1 INCREMENT BY 1;
create sequence idEtatValidationSeq START WITH 1 INCREMENT BY 1;
create sequence idChronoChangeSeq START WITH 1 INCREMENT BY 1;
create sequence idCoursEchangeSeq START WITH 1 INCREMENT BY 1;

create table typeUser(
    id INTEGER PRIMARY KEY,
    designation varchar(30) 
);

create table users (
    id INTEGER DEFAULT nextval('idUserSeq'::regclass) NOT NULL PRIMARY KEY,
    nom varchar(30),
    idTypeUser INTEGER,
    FOREIGN KEY(idTypeUser) REFERENCES typeUser(id)
);


create table categorieBateau (
    id integer PRIMARY KEY,
    value varchar(50) NOT NULL
);

create table pavillon (
    id integer default nextval('idPavillonSeq'::regclass) NOT NULL PRIMARY KEY,
    nom varchar(50) NOT NULL
);

create table bateau (
    id integer default nextval('idBateauSeq'::regclass) NOT NULL PRIMARY KEY,
    idCategorieBateau integer,
    idPavillon integer,
    nom varchar(50),
    profondeur DOUBLE PRECISION,
    dureeRemorquage DOUBLE PRECISION,
    litreRavitailEauNeed DOUBLE PRECISION,
    foreign key (idCategorieBateau) references categorieBateau(id),
    foreign key (idPavillon) references pavillon(id)
);

create table Quai(
    id INTEGER DEFAULT nextval('idQuaiSeq'::regclass) NOT NULL PRIMARY KEY,
    nom VARCHAR(20),
    profondeur DOUBLE PRECISION,
    isDispo boolean
);

create table prevision(
    id INTEGER DEFAULT nextval('idPrevisionSeq'::regclass) NOT NULL PRIMARY KEY,
    dateDebut TIMESTAMP,
    dateFin TIMESTAMP,
    dureeEscale DOUBLE PRECISION,
    idBateau  INTEGER,
    FOREIGN KEY (idBateau) REFERENCES bateau(id)
);

create table typePrestation (
    id INTEGER PRIMARY KEY,
    designation varchar(20)
);

create table configPrestation (
    id INTEGER DEFAULT nextval('idConfigPrestationSeq'::regclass) NOT NULL PRIMARY KEY,
    heureDebut INTEGER,
    heureFin INTEGER,
    trancheInf INTEGER,
    trancheSup INTEGER,
    idTypePrestation INTEGER,
    idCategorieBateau INTEGER,
    idPavillon INTEGER,
    idQuai INTEGER,
    tarif DOUBLE PRECISION,
    FOREIGN KEY(idTypePrestation) REFERENCES typePrestation(id),
    FOREIGN KEY(idCategorieBateau) REFERENCES categorieBateau(id),
    FOREIGN KEY(idPavillon) REFERENCES pavillon(id),
    FOREIGN KEY(idQuai) REFERENCES quai(id)
);

create table escale (
    id INTEGER DEFAULT nextval('idEscaleSeq'::regclass) NOT NULL PRIMARY KEY,
    reference varchar(20),
    idBateau INTEGER,
    idQuai INTEGER,
    dateDebut TIMESTAMP,
    dateFin TIMESTAMP,
    FOREIGN KEY(idBateau) REFERENCES bateau(id), 
    FOREIGN KEY(idQuai) REFERENCES quai(id)
);

create table etatValidation (
    id INTEGER DEFAULT nextval('idEtatValidationSeq'::regclass) NOT NULL PRIMARY KEY,
    value INTEGER
);

create table validationPrestation (
    id INTEGER DEFAULT nextval('idValidationPrestationSeq'::regclass) NOT NULL PRIMARY KEY,
    idUser INTEGER,
    idPrestation INTEGER,
    dateValidation TIMESTAMP,
    FOREIGN KEY(idUser) REFERENCES users(id),
    FOREIGN KEY(idPrestation) REFERENCES prestation(id)
);

create table prestation (
    id INTEGER DEFAULT nextval('idPrestationSeq'::regclass) NOT NULL PRIMARY KEY,
    idEscale INTEGER,
    reference varchar(20),
    idTypePrestation INTEGER,
    dateDebut TIMESTAMP,
    dateFin TIMESTAMP,
    duree DOUBLE PRECISION,
    idQuai INTEGER,
    montant DOUBLE PRECISION,
    idEtatValidation INTEGER,
    FOREIGN KEY(idTypePrestation) REFERENCES typePrestation(id),
    FOREIGN KEY(idEscale) REFERENCES escale(id),
    FOREIGN KEY(idQuai) REFERENCES quai(id),
    FOREIGN KEY(idEtatValidation) REFERENCES etatValidation(id)
); 

create sequence idFactureSeq START WITH 1 INCREMENT BY 1;

create table facture (
    id INTEGER DEFAULT nextval('idFactureSeq'::regclass) NOT NULL PRIMARY KEY,
    idEscale INTEGER,
    idPrestation INTEGER,
    idEtatValidation INTEGER,
    FOREIGN KEY(idPrestation) REFERENCES prestation(id),
    FOREIGN KEY(idEscale) REFERENCES escale(id)
);


create table validationFacture (
    id INTEGER DEFAULT nextval('idValidationFactureSeq'::regclass) NOT NULL PRIMARY KEY,
    idUser INTEGER,
    idFacture INTEGER,
    dateValidation TIMESTAMP,
    FOREIGN KEY(idUser) REFERENCES users(id),
    FOREIGN KEY(idFacture) REFERENCES facture(id)
);

create table chronoChangeQuai (
    id INTEGER DEFAULT nextval('idChronoChangeSeq'::regclass) NOT NULL PRIMARY KEY,
    idEscale INTEGER,
    idQuai INTEGER,
    dateDebut TIMESTAMP,
    dateFin TIMESTAMP,
    FOREIGN KEY(idEscale) REFERENCES escale(id),
    FOREIGN KEY(idQuai) REFERENCES quai(id)
);

create table coursEchange (
    id INTEGER DEFAULT nextval('idCoursEchangeSeq'::regclass) NOT NULL PRIMARY KEY,
    ariary DOUBLE PRECISION,
    euro DOUBLE PRECISION,
    dateEchange date
);