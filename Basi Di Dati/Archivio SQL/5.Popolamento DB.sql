--Popoliamo il nostro DB con dati predefiniti al fine di poter testare il funzionamento
--dei trigger,delle funzioni e delle procedure.


DO $$
DECLARE
	varIdGruppo Gruppo.IdGruppo%TYPE;
	varIdCreatore CreatoreGruppo.IdCreatore%TYPE;
	varIdPost Post.IdPost%TYPE;
	varIdCommento Commento.IdCommento%TYPE;
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
	('Maria', 'Bianchi', 'Mariab', 'maria@gmail.com', 'passmaria', 'Appassionata di cucina asiatica', NULL),
	('Luigi', 'Verdi', 'Gigi789', 'luigi@gmail.com', 'passluigi', 'Ingegnere informatico e giocatore di tennis', NULL),
	('Francesca', 'Rossi', 'Fran123', 'francesca@gmail.com', 'passfran', 'Studentessa di medicina e amante degli animali', NULL),
   	 ('Giovanni', 'Neri', 'Giovanino', 'giovanni@gmail.com', 'passgiovanni', 'Studioso di storia e cultura medievale', NULL),
    	('Alessia', 'Rosa', 'Aly1617', 'alessia@icloud.com', 'passalessia', 'Appassionata di pittura e disegno', NULL),
    	('Marco', 'Arancio', 'Marco1819', 'marco@gmail.com', 'passmarco', 'Studente di ingegneria e appassionato di robotica', NULL),
    	('Laura', 'Castellammare', 'Lau01', 'laura@gmail.com', 'passlaura', 'Amante del teatro e della recitazione', NULL),
    	('Daniele', 'Viola', 'Danny2021', 'daniele@gmail.com', 'passdaniele', 'Appassionato di sci e sport invernali', NULL),
    	('Simona', 'Pellegrino', 'SimPell', 'simona@gmail.com', 'passsimona', 'Ingegnere ambientale e attivista per la sostenibilità', NULL),
	('Raffaele','Cecere', 'lello00', 'lellocece@libero.it', 'pass9923','Ingegnere Gestionale,24 anni.', NULL);


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
    	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('lello00'),varIdCreatore,varIdGruppo) ;
	
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('Hugo1819'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('LukeScotti'),varIdGruppo);
    	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('lello00'),varIdGruppo);
	
	INSERT INTO Post (Testo,URLImmagine,DataPubblicazione,OraPubblicazione,IdUtente,IdGruppo) 
	VALUES ('Benvenuti nel gruppo dei tifosi della Juventus',NULL,CURRENT_DATE,CURRENT_TIME,RecuperaIdUtenteConNickname('giorginho01'),varIdGruppo)
	RETURNING IdPost INTO varIdPost;
	
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Alice123'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('HugoToKnow'), varIdPost);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('Forza Juve',RecuperaIdUtenteConNickname('Alice123'), varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('giorginho01'), varIdCommento);
	
	
	
	---------------------------
	
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('Charlie789'),'Studenti Informatica Unina','Istruzione,Università','Gruppo studio degli studenti di informatica.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('Charlie789',varIdGruppo) INTO varIdCreatore;

	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('No1dem'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Frank1415'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Giulia1617'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Bob456'),varIdCreatore,varIdGruppo) ;
    PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Alice123'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('andrcap03'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('lello00'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Eva1213'),varIdCreatore,varIdGruppo);

    	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('No1dem'),varIdGruppo);
    	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('andrcap03'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('DaviGatta'),varIdGruppo);
	
	INSERT INTO Post (Testo,URLImmagine,DataPubblicazione,OraPubblicazione,IdUtente,IdGruppo) 
	VALUES ('Benvenuti nel gruppo degli studenti di informatica. Siamo qui per esplorare il mondo dell’informatica
	insieme.',NULL,CURRENT_DATE,CURRENT_TIME,RecuperaIdUtenteConNickname('Charlie789'),varIdGruppo)
	RETURNING IdPost INTO varIdPost;
	
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('DaviGatta'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('HugoToKnow'), varIdPost);
	INSERT INTO Like_ (idUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('andrcap03'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('lello00'), varIdPost);
	INSERT INTO Like_ (idUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Frank1415'), varIdpost);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('Sono contento di far parte di questo gruppo e non vedo l‘ora di condividere conoscenze',RecuperaIdUtenteConNickname('lello00'), varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('Charlie789'), varIdCommento);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato,IdCommentoRisp) VALUES ('Grazie per esserti unito al gruppo!',RecuperaIdUtenteConNickname('Charlie789'),varIdPost,varIdCommento)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('lello00'), varIdCommento);
	
	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('Eva1213'),'Passione Cucina','Cucina,Cibo','Benvenuti nel nostro gruppo di appassionati di cucina.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('Eva1213',varIdGruppo) INTO varIdCreatore;

	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('LukeScotti'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Irene2021'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('David1011'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('andrcap03'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Giulia1617'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Giovanino'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Lau01'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('SimPell'),varIdCreatore,varIdGruppo) ;

	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('Giulia1617'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('David1011'),varIdGruppo);

   	INSERT INTO Post (Testo,URLImmagine,DataPubblicazione,OraPubblicazione,IdUtente,IdGruppo) 
	VALUES ('In questo gruppo condivideremo ricette, trucchi culinari e la passione per l’arte di cucinare.',NULL,CURRENT_DATE,CURRENT_TIME,RecuperaIdUtenteConNickname('Eva1213'),varIdGruppo)
	RETURNING IdPost INTO varIdPost;
	
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Lau01'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('SimPell'), varIdPost);
	INSERT INTO Like_ (idUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Giulia1617'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('David1011'), varIdPost);
	INSERT INTO Like_ (idUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('LukeScotti'), varIdpost);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('Sono entusiasta di unirmi a questa comunità culinaria. Vorrei sapere, qual è la vostra ricetta preferita da preparare?',RecuperaIdUtenteConNickname('Lau01'), varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('Eva1213'), varIdCommento);
   	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('Giulia1617'), varIdCommento);

    	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato,IdCommentoRisp) VALUES ('La mia ricetta preferita da preparare è il risotto alla nerano',RecuperaIdUtenteConNickname('Giulia1617'),varIdPost,varIdCommento)
	RETURNING IdCommento INTO varIdCommento;

    	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('Lau01'), varIdCommento);
	
	
	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('LukeScotti'),'Hall of Series','Intrattenimento,SerieTV','Community amanti serie TV.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('LukeScotti',varIdGruppo) INTO varIdCreatore;


	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('giorginho01'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('andrcap03'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Aly1617'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Danny2021'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Gigi789'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Irene2021'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('No1dem'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('SimPell'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Frank1415'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Charlie789'),varIdCreatore,varIdGruppo) ;
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Lau01'),varIdCreatore,varIdGruppo) ;
		
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('andrcap03'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('Irene2021'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('SimPell'),varIdGruppo);
    	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('Lau01'),varIdGruppo);
	
    	INSERT INTO Post (Testo,URLImmagine,DataPubblicazione,OraPubblicazione,IdUtente,IdGruppo) 
	VALUES ('Qual è la vostra serie TV preferita in assoluto?',NULL,CURRENT_DATE,CURRENT_TIME,RecuperaIdUtenteConNickname('Irene2021'),varIdGruppo)
	RETURNING IdPost INTO varIdPost;
	
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('DaviGatta'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('SimPell'), varIdPost);
	INSERT INTO Like_ (idUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('andrcap03'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Frank1415'), varIdPost);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('La mia serie tv preferita è Game of Thrones',RecuperaIdUtenteConNickname('DaviGatta'),varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('andrcap03'), varIdCommento);
    INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('DaviGatta'), varIdCommento);
	
    INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('La mia serie tv preferita è Peaky Blinders',RecuperaIdUtenteConNickname('LukeScotti'),varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('DaviGatta'), varIdCommento);
	
	
	
	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('No1dem'),'Tifosi del Napoli','Calcio,Sport','Gruppo italiano per i tifosi del Napoli.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('No1dem',varIdGruppo) INTO varIdCreatore;

    	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('giorginho01'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('lello00'),varIdCreatore,varIdGruppo); 
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Frank1415'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Giovanino'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Gigi789'),varIdCreatore,varIdGruppo);
	
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('DaviGatta'),varIdGruppo);
    	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('HugoToKnow'),varIdGruppo);

    	INSERT INTO Post (Testo,URLImmagine,DataPubblicazione,OraPubblicazione,IdUtente,IdGruppo) 
	VALUES ('Il Napoli gioca meglio con il 433 o con il 352?',NULL,CURRENT_DATE,CURRENT_TIME,RecuperaIdUtenteConNickname('DaviGatta'),varIdGruppo)
	RETURNING IdPost INTO varIdPost;
	
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('giorginho01'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Gigi789'), varIdPost);
	INSERT INTO Like_ (idUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('No1dem'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Giovanino'), varIdPost);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('Con il 352 abbiamo più solidità difensiva.',RecuperaIdUtenteConNickname('No1dem'), varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('DaviGatta'), varIdCommento);
    	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('Gigi789'), varIdCommento);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('Con il 433 siamo più pericolosi in fase offensiva',RecuperaIdUtenteConNickname('giorginho01'), varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('Giovanino'), varIdCommento);
    	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('lello00'), varIdCommento);
	
	
	---------------------------
	SELECT CreaGruppo(RecuperaIdUtenteConNickname('Charlie789'),'NEWS H24','Attualità,Politica,Sport','Gruppo per discutere sulle notizie del giorno.') INTO varIdGruppo;
	SELECT RecuperaIdCreatoreConNickname('Charlie789',varIdGruppo) INTO varIdCreatore;
	

	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('lello00'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('HugoToKnow'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('DaviGatta'),varIdCreatore ,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Mariab'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Aly1617'),varIdCreatore ,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Irene2021'),varIdCreatore ,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Hugo1819'),varIdCreatore,varIdGruppo);
	PERFORM IscriviUtente(RecuperaIdUtenteConNickname('Fran123'),varIdCreatore ,varIdGruppo);
	
    	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('lello00'),varIdGruppo);
	INSERT INTO Amministratore (IdCreatore,IdUtente,IdGruppo) VALUES (varIdCreatore,RecuperaIdUtenteConNickname('Mariab'),varIdGruppo);
	
	INSERT INTO Post (Testo,URLImmagine,DataPubblicazione,OraPubblicazione,IdUtente,IdGruppo) 
	VALUES ('Oggi nel mondo della tecnologia si parla molto del nuovo apple vision pro, cosa ne pensate?',NULL,CURRENT_DATE,CURRENT_TIME,RecuperaIdUtenteConNickname('Charlie789'),varIdGruppo)
	RETURNING IdPost INTO varIdPost;
	
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Mariab'), varIdPost);
	INSERT INTO Like_ (IdUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Hugo1819'), varIdPost);
	INSERT INTO Like_ (idUtente,IdPost) VALUES (RecuperaIdUtenteConNickname('Aly1617'), varIdPost);
	
	INSERT INTO Commento (TestoCommento,IdUtente,IdPostCommentato) VALUES ('È una nuova tecnologia molto interessante anche se ha alcuni difetti come la batteria esterna che può risultare seccante e il prezzo poco 	accessibile.',RecuperaIdUtenteConNickname('Mariab'), varIdPost)
	RETURNING IdCommento INTO varIdCommento;
	
	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('DaviGatta'), varIdCommento);
    	INSERT INTO Like_ (IdUtente,IdCommento) VALUES (RecuperaIdUtenteConNickname('Fran123'), varIdCommento);
	

END;
$$ LANGUAGE plpgsql;
