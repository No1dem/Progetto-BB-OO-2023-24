DROP TABLE IF EXISTS Utente CASCADE;
DROP TABLE IF EXISTS Gruppo CASCADE ;
DROP TABLE IF EXISTS CreatoreGruppo CASCADE; 
DROP TABLE IF EXISTS Like_ CASCADE;
DROP TABLE IF EXISTS Commento CASCADE;
DROP TABLE IF EXISTS Post CASCADE;
DROP TABLE IF EXISTS Notifica CASCADE;
DROP TABLE IF EXISTS Ricevere CASCADE;
DROP TABLE IF EXISTS RichiestaDiAccesso CASCADE;
DROP TABLE IF EXISTS Iscrizione CASCADE; 
DROP TABLE IF EXISTS Amministratore CASCADE;

CREATE TABLE Utente (
 	IdUtente SERIAL PRIMARY KEY,
  	NomeUtente VARCHAR(30) NOT NULL CHECK (NomeUtente ~ '^[A-Za-zÀ-ÖØ-öø-ÿ ]+$'),
  	CognomeUtente VARCHAR(30) NOT NULL CHECK (CognomeUtente ~ '^[A-Za-zÀ-ÖØ-öø-ÿ ]+$'),
  	Nickname VARCHAR(20) NOT NULL CHECK(LENGTH(Nickname)>=5),
 	Email VARCHAR(100) UNIQUE NOT NULL,  
  	Password VARCHAR(50) NOT NULL CHECK(LENGTH(Password)>=6),
  	Biografia VARCHAR(350),
  	URLFotoProfilo VARCHAR(2000)
);


CREATE TABLE Gruppo (
	IdGruppo SERIAL PRIMARY KEY,
 	NomeGruppo VARCHAR(50) NOT NULL,
 	TagGruppo VARCHAR(200) NOT NULL CHECK (TagGruppo ~ '^([a-zA-ZàèìòùÀÈÌÒÙ]+,)*[a-zA-ZàèìòùÀÈÌÒÙ]+$'),
 	DescrizioneGruppo TEXT,
 	NumeroIscritti INT DEFAULT 0
);

CREATE TABLE CreatoreGruppo (
	IdCreatore SERIAL PRIMARY KEY,
	IdUtente INT NOT NULL,
	IdGruppo INT NOT NULL,
	FOREIGN KEY (IdUtente) REFERENCES Utente(IdUtente) ON DELETE CASCADE,
	FOREIGN KEY (IdGruppo) REFERENCES Gruppo(IdGruppo) ON DELETE CASCADE
);

CREATE TABLE Amministratore (
	IdAmministratore SERIAL PRIMARY KEY,
	IdCreatore INT NOT NULL,
	IdUtente INT NOT NULL,
	IdGruppo INT NOT NULL,
	FOREIGN KEY (IdCreatore) REFERENCES CreatoreGruppo(IdCreatore) ON DELETE CASCADE,
	FOREIGN KEY (IdUtente) REFERENCES Utente(IdUtente) ON DELETE CASCADE,
	FOREIGN KEY (IdGruppo) REFERENCES Gruppo(IdGruppo) ON DELETE CASCADE
);



CREATE TABLE Post (
	IdPost SERIAL PRIMARY KEY,
	Testo TEXT,
	URLImmagine VARCHAR(2000),
	DataPubblicazione DATE NOT NULL DEFAULT CURRENT_DATE,
	OraPubblicazione TIME NOT NULL DEFAULT CURRENT_TIME,
	NumeroLike INT DEFAULT 0,
	NumeroCommenti INT DEFAULT 0,
	IdUtente INT NOT NULL,
	IdGruppo INT NOT NULL,
	IdNotifica INT,
	FOREIGN KEY (IdUtente) REFERENCES Utente(IdUtente) ON DELETE CASCADE,
	FOREIGN KEY (IdGruppo) REFERENCES Gruppo(IdGruppo) ON DELETE CASCADE
	--Aggiungere dopo Notifica la FK su IdNotifica
);


CREATE TABLE Commento (
	IdCommento SERIAL PRIMARY KEY,
  	TestoCommento TEXT NOT NULL,
  	NumeroLike INT DEFAULT 0,
  	IdUtente INT NOT NULL,
  	IdCommentoRisp INT,
  	IdPostCommentato INT NOT NULL,
  	FOREIGN KEY (IdUtente) REFERENCES Utente(IdUtente) ON DELETE CASCADE,                  
  	FOREIGN KEY (IdPostCommentato) REFERENCES Post(IdPost) ON DELETE CASCADE
);
ALTER TABLE Commento
ADD CONSTRAINT commento_IdCommentoRisp_fkey
FOREIGN KEY (IdCommentoRisp) REFERENCES Commento(IdCommento) ON DELETE CASCADE;

CREATE TABLE Like_ (
	IdLike SERIAL PRIMARY KEY,
  	IdUtente INT NOT NULL,
  	IdCommento INT,
  	IdPost INT,
  	FOREIGN KEY (IdUtente) REFERENCES Utente(IdUtente) ON DELETE CASCADE,
  	FOREIGN KEY (IdCommento) REFERENCES Commento(IdCommento) ON DELETE CASCADE,
  	FOREIGN KEY (IdPost) REFERENCES Post(IdPost) ON DELETE CASCADE
);

CREATE TABLE Notifica (
	IdNotifica SERIAL PRIMARY KEY,
  	DataInvio DATE NOT NULL,
  	OraInvio TIME NOT NULL,
  	TestoNotifica TEXT NOT NULL,
  	TipoNotifica EnumTipoNotifica NOT NULL,
  	IdNuovoPost INT,
  	IdNuovoCommento INT,
  	IdNuovoLike INT,
  	FOREIGN KEY (IdNuovoPost) REFERENCES Post(IdPost) ON DELETE CASCADE,
  	FOREIGN KEY (IdNuovoCommento) REFERENCES Commento(IdCommento) ON DELETE CASCADE,
  	FOREIGN KEY (IdNuovoLike) REFERENCES Like_(IdLike) ON DELETE CASCADE 
);

--	PostIdNotificaFKey
ALTER TABLE Post
ADD CONSTRAINT post_IdNotifica_fkey
FOREIGN KEY (IdNotifica) REFERENCES Notifica(IdNotifica) ON DELETE SET NULL;

CREATE TABLE Iscrizione (
	IdUtente INT,
	IdGruppo INT,
  	PRIMARY KEY (IdUtente, IdGruppo),
	FOREIGN KEY (IdUtente) REFERENCES Utente(IdUtente) ON DELETE CASCADE,
  	FOREIGN KEY (IdGruppo) REFERENCES Gruppo(IdGruppo) ON DELETE CASCADE
);
 

CREATE TABLE Ricevere (
	IdUtente INT,
  	IdNotifica INT,
  	PRIMARY KEY (IdUtente, IdNotifica),
  	FOREIGN KEY (IdUtente) REFERENCES Utente(IdUtente) ON DELETE CASCADE ,
  	FOREIGN KEY (IdNotifica) REFERENCES Notifica(IdNotifica) ON DELETE CASCADE
);

CREATE TABLE RichiestaDiAccesso (
	IdRichiesta SERIAL PRIMARY KEY,
	StatoRichiesta EnumStati DEFAULT 'In attesa',
	IdUtenteRichiesta INT NOT NULL,
	IdCreatore INT NOT NULL,
	IdGruppoRichiesta INT NOT NULL,
	IdNotificaGenerata INT,
	FOREIGN KEY (IdUtenteRichiesta) REFERENCES Utente(IdUtente) ON DELETE CASCADE,
	FOREIGN KEY (IdCreatore) REFERENCES CreatoreGruppo(IdCreatore) ON DELETE CASCADE,
	FOREIGN KEY (IdNotificaGenerata) REFERENCES Notifica(IdNotifica) ON DELETE CASCADE,
	FOREIGN KEY (IdGruppoRichiesta) REFERENCES Gruppo(IdGruppo) ON DELETE CASCADE
);
