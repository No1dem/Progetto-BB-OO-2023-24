--Popoliamo il nostro DB con dati predefiniti al fine di poter testare il funzionamento
--dei trigger,delle funzioni e delle procedure.


DO $$
DECLARE
	varIdGruppo Gruppo.IdGruppo%TYPE;
	varIdCreatore CreatoreGruppo.IdCreatore%TYPE;
BEGIN
	--Inserimento utenti

	INSERT INTO Utente (NomeUtente, CognomeUtente, Nickname, Email, Password, Biografia, URLFotoProfilo)
	VALUES 
  	('Alice', 'Rossi', 'Alice123', 'alice@gmail.com', 'pass123', 'Studentessa appassionata di tecnologia', NULL),
  	('Bob', 'Verdi', 'Bob456', 'bob@gmail.com', 'pass456', 'Appassionato di viaggi e fotografia',NULL),
  	('Charlie', 'Bianchi', 'Charlie789', 'charlie@gmail.com', 'pass789', 'Programmatore e appassionato di musica',NULL),
  	('David', 'Gialli', 'David1011', 'david@hotmail.com', 'pass1011', 'Appassionato di cucina italiana', NULL),
  	('Eva', 'Azzurri', 'Eva1213', 'eva@gmail.com', 'pass1213', 'Amante degli animali e attivista per i diritti degli animali', NULL),
  	('Frank', 'Neri', 'Frank1415', 'frank@gmail.com', 'pass1415', 'Scrittore e lettore appassionato', NULL),
  	('Giulia', 'Rosa', 'Giulia1617', 'giulia@icloud.com', 'pass1617', 'Studiosa di arte e cultura', NULL),
  	('Hugo', 'Arancio', 'Hugo1819', 'hugo@gmail.com', 'pass1819', 'Ingegnere informatico e appassionato di robotica',NULL),
  	('Giorgio', 'Castellammare', 'giorginho01', 'gcmare01@gmail.com', 'pass4040', 'Sono Giorgio , ho 22 anni e amo i videogiochi',NULL),
	('Fabrizio', 'Pellegrino', 'HugoToKnow', 'fabrpelle01@gmail.com', 'pass2341', 'Esperto di motori',NULL),
	('Irene', 'Viola', 'Irene2021', 'irene@gmail.com', 'pass2021', 'Appassionata di sport e vita attiva',NULL),
	('Antonio', 'De Martino', 'No1dem','demantos3@gmail.com', 'pass1111', 'Studente di Informatica della Federico II',NULL),
	('Andrea', 'Capitelli', 'andrcap03', 'an.capitelli@hotmail.it', 'pass0001', 'Forza Milan ',NULL),
	('Davide', 'Gatta', 'DaviGatta', 'd.gatta02@gmail.com', 'pass5051','Studente di Informatica della Federico II',NULL),
	('Luca', 'Scotti', 'LukeScotti', 'lucalu@libero.it', 'pass9792','Amo vedere film e serie tv.',NULL),
	('Raffaele','Cecere', 'lello00', 'lellocece@libero.it', 'pass9923','Ingegnere Gestionale,24 anni.',NULL);


	--Creazione gruppi e iscrivi utenti nel gruppo
	--PROTOTIPO CreaGruppo: 
	--PROTOTIPO IscriviUtente:
	--------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('giorginho01'),'Tifosi della Juve ITA','Sport,Calcio','Gruppo italiano per i tifosi della Juventus') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('giorginho01',varIdGruppo) INTO varIdCreatore;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('No1dem'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Hugo1819'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Alice123'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('LukeScotti'),varIdCreatore,varIdGruppo);

	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('Hugo1819'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('LukeScotti'),varIdGruppo);

	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('giorginho01'),'Studenti Informatica Unina','Istruzione,Università','Gruppo studio degli studenti di informatica.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('giorginho01',varIdGruppo) INTO varIdCreatore;

	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('No1dem'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Frank1415'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Giulia1617'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Bob456'),varIdCreatore,varIdGruppo) ;

	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('No1dem'),varIdGruppo);

	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('Eva1213'),'Passione Cucina','Cucina,Cibo','Benvenuti nel nostro gruppo di appassionati di cucina.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('Eva1213',varIdGruppo) INTO varIdCreatore;

	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('LukeScotti'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Irene2021'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('David1011'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('andrcap03'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Giulia1617'),varIdCreatore,varIdGruppo) ;

	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('Giulia1617'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('David1011'),varIdGruppo);

	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('LukeScotti'),'Hall of Series','Intrattenimento,SerieTV','Community amanti serie TV.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('LukeScotti',varIdGruppo) INTO varIdCreatore;


	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('giorginho01'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('andrcap03'),varIdCreatore,varIdGruppo) ;
	
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('andrcap03'),varIdGruppo);
	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('No1dem'),'Tifosi del Napoli','Calcio,Sport','Gruppo italiano per i tifosi del Napoli.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('No1dem',varIdGruppo) INTO varIdCreatore;


	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('giorginho01'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('lello00'),varIdCreatore,varIdGruppo); 

	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('DaviGatta'),varIdGruppo);


	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('Charlie789'),'NEWS H24','Attualità,Politica,Sport','Gruppo per discutere sulle notizie del giorno.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('Charlie789',varIdGruppo) INTO varIdCreatore;
	

	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('lello00'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore ,varIdGruppo);

	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('lello00'),varIdGruppo);

END;
$$ LANGUAGE plpgsql;

--
