--	UnicoLikePerPostCommento
ALTER TABLE Like_ ADD CONSTRAINT UnicoLikePerPostCommento
UNIQUE(IdUtente, IdCommento, IdPost);

--	UnicitàNickname
ALTER TABLE Utente ADD CONSTRAINT UnicitàNickname
UNIQUE(Nickname);

--	UnicitàUtenteGruppoInAmministratore
ALTER TABLE Amministratore ADD CONSTRAINT UnicitàUtenteGruppoInAmministratore
UNIQUE(IdGruppo,IdUtente);

--	UnicitàRichiestaAccessoPerGruppo
ALTER TABLE RichiestaDiAccesso ADD CONSTRAINT UnicitàRichiestaAccessoPerGruppo
UNIQUE (IdUtenteRichiesta,IdGruppoRichiesta)

--	ValiditàDataOraPost
ALTER TABLE Post
ADD CONSTRAINT ValiditàDataOraPost
CHECK (DataPubblicazione = CURRENT_DATE AND OraPubblicazione=CURRENT_TIME);

--	ValiditàDataOraNotifica
ALTER TABLE Notifica
ADD CONSTRAINT ValiditàDataOraNotifica
CHECK (DataInvio=CURRENT_DATE AND OraInvio=CURRENT_TIME);

--	ControlloPostNoTestoNoFoto
ALTER TABLE Post ADD CONSTRAINT ControlloPostNoTestoNoFoto
CHECK( 
( URLImmagine IS NULL AND Testo IS NOT NULL ) 
	OR
	( Testo IS NULL AND URLImmagine IS NOT NULL )
	OR
	( Testo IS NOT NULL AND URLImmagine IS NOT NULL )	
);


--  ControlloRiferimentoLike

--Controllo che un like è riferito ad un Post o a un Commento
ALTER TABLE Like_
ADD CONSTRAINT ControlloRiferimentoLike
CHECK(
	(IdCommento IS NULL AND IdPost IS NOT NULL)
	OR
	(IdCommento IS NOT NULL AND IdPost IS NULL)
);

-- UnicitàGruppoInCreatoreGruppo

ALTER TABLE CreatoreGruppo
ADD CONSTRAINT UnicitaGruppoInCreatoreGruppo UNIQUE (IdGruppo);



