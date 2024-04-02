-- 	ControlloNumeroLikeCommentiInPost
--La seguente funzione controlla che all'inserimento di un nuovo post 
--i valori NumeroLike e NumeroCommenti sia settato a quello di default 0

CREATE OR REPLACE FUNCTION ControlloNumeroLikeCommentiInPost()
RETURNS TRIGGER AS $$
BEGIN
	IF (NEW.NumeroCommenti <> 0 OR NEW.NumeroLike <> 0) THEN 
		RAISE EXCEPTION 'Impossibile inserire un post che abbia già dei like e commenti';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER ControlloNumeroLikeCommentiInPost
BEFORE INSERT ON Post
FOR EACH ROW 
EXECUTE FUNCTION ControlloNumeroLikeCommentiInPost();

-- 	ControlloNumeroLikeInCommento
--La seguente funzione controlla che all'inserimento di un nuovo commento
--il numero di like sia settato al valore di default 0 

CREATE OR REPLACE FUNCTION ControlloNumeroLikeInCommento()
RETURNS TRIGGER AS $$	
BEGIN
	IF (NEW.NumeroLike <> 0) THEN 
		RAISE EXCEPTION 'Impossibile inserire un commento che abbia un numero di like diverso da zero';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER ControlloNumeroLikeInCommento
BEFORE INSERT ON Commento
FOR EACH ROW 
EXECUTE FUNCTION ControlloNumeroLikeInCommento();

--	CreatoreGruppoèAmministratore
--All'inserimento di una tupla in creatoreGruppo la funzione inseirsce una tupla anche nella tabella
--amministratore relativa allo stesso utente 

CREATE OR REPLACE FUNCTION CreatoreAdmin() 
RETURNS TRIGGER AS $$
BEGIN 
	INSERT INTO Iscrizione  (idUtente, idGruppo)
	VALUES (New.idUtente , New.idGruppo);
	INSERT INTO Amministratore (idCreatore , idUtente , idGruppo)
	VALUES (New.idCreatore , New.idUtente , New.idGruppo);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER CreatoreGruppoèAmministratore
AFTER INSERT ON CreatoreGruppo 
FOR EACH ROW 
EXECUTE FUNCTION CreatoreAdmin();

--	NuovoAdmin
-- la seguente funzione si occupa di controllare che l'utente nominato amministratore
--sia effetivamente iscritto al gruppo

CREATE OR REPLACE FUNCTION NuovoAdmin()
RETURNS TRIGGER AS $$
BEGIN 
	IF NOT EXISTS (SELECT 1
			   	   FROM Iscrizione
			   	   WHERE New.idUtente=idUtente AND New.idGruppo=idGruppo)
	THEN
			RAISE EXCEPTION  'L''utente non è iscritto';
	END IF;
    RETURN NEW;
END;

$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER NominaAmministratore
BEFORE INSERT ON Amministratore 
FOR EACH ROW
EXECUTE FUNCTION NuovoAdmin();

--	CreatorePostInGruppo
--La seguente funzione si occupa di controllare che un utente che scrive un post, lo stia scrivendo  
--in un gruppo in cui è iscritto

CREATE OR REPLACE FUNCTION CreatorePostInGruppo()
RETURNS TRIGGER AS $$
BEGIN
	IF NOT EXISTS (SELECT 1
			       FROM Iscrizione I 
			       WHERE I.IdUtente=NEW.IdUtente AND I.IdGruppo=NEW.IdGruppo)
	THEN
		RAISE EXCEPTION 'L''utente che ha scritto il post non è iscritto al gruppo';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER CreatorePostInGruppo
BEFORE INSERT ON Post
FOR EACH ROW
EXECUTE FUNCTION CreatorePostInGruppo();

--	NotificaAccessoCreatore
--La seguente funzione invia una notifica di tipo <<Accesso>> al CreatoreGruppo del gruppo a cui un --utente vuole accedere
CREATE OR REPLACE FUNCTION InviaNotificaAccesso()
RETURNS TRIGGER AS $$
DECLARE 
	varCreatoreGruppo CreatoreGruppo.IdCreatore%TYPE;
	varNewIdNotifica Notifica.IdNotifica%TYPE;
	varNomeUtenteRichiesta Utente.NomeUtente%TYPE;
	varCognomeUtenteRichiesta Utente.CognomeUtente%TYPE;
	varNomeGruppoRichiesta Gruppo.NomeGruppo%TYPE;
	varIdUtenteCreatore Utente.IdUtente%TYPE;
	Messaggio TEXT;
BEGIN
	--Salvo il Creatore del gruppo dove viene effettuata una nuova richiesta
	SELECT IdCreatore 
	INTO varCreatoreGruppo
	FROM RichiestaDiAccesso
	WHERE IdRichiesta = NEW.IdRichiesta;

	--Salvo il nome e cognome dell'utente che effettua la richiesta di accesso
	SELECT NomeUtente  
	INTO varNomeUtenteRichiesta
	FROM Utente
	WHERE IdUtente = NEW.IdUtenteRichiesta;

	SELECT CognomeUtente 
	INTO varCognomeUtenteRichiesta
	FROM Utente
	WHERE IdUtente = NEW.IdUtenteRichiesta;
	--Salvo il nome del gruppo in cui si effettua la richiesta di accesso
	SELECT NomeGruppo
	INTO varNomeGruppoRichiesta
	FROM Gruppo
	WHERE IdGruppo = New.IdGruppoRichiesta;
	--Testo della notifica
	Messaggio := 'L''utente ' || varNomeUtenteRichiesta || ' ' || varCognomeUtenteRIchiesta || ' ha effettuato una nuova richiesta di accesso al gruppo: ' || varNomeGruppoRichiesta || '.';
	--Creo e salvo l'id della nuova notifica 
	INSERT INTO Notifica(DataInvio, OraInvio, TestoNotifica, TipoNotifica)
	VALUES (CURRENT_DATE, CURRENT_TIME,Messaggio,'Accesso')
	RETURNING IdNotifica INTO varNewIdNotifica;
	UPDATE RichiestaDiAccesso SET IdNotificaGenerata=varNewIdNotifica
	WHERE IdRichiesta=NEW.IdRichiesta;
	--Recupero l’id dell’utente che è il creatore
	SELECT IdUtente
	INTO varIdUtenteCreatore
	FROM CreatoreGruppo
	WHERE IdCreatore=NEW.IdCreatore;
	--Invio la notifica al’utente che è il  creatore
	INSERT INTO Ricevere VALUES (varIdUtenteCreatore, varNewIdNotifica);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER NotificaAccessoCreatore
AFTER INSERT ON RichiestaDiAccesso
FOR EACH ROW
EXECUTE FUNCTION InviaNotificaAccesso();

--	VerificaStatoRichiesta
--La seguente funzione si occupa di controllare se esistono richieste di accesso per un utente 
--e se esiste,controlla se è esitata come <<Accettato>> altrimenti blocca l’iscrizione al gruppo
CREATE OR REPLACE FUNCTION VerificaStatoRichiesta()
RETURNS TRIGGER AS $$
BEGIN
 	IF NEW.IdUtente NOT IN (SELECT IdUtente 
	      				 	FROM CreatoreGruppo
	      				 	WHERE IdGruppo=NEW.IdGruppo)
	THEN  
		IF NOT EXISTS(SELECT 1
				      FROM RichiestaDiAccesso
	    			  WHERE IdUtenteRichiesta=NEW.IdUtente AND IdGruppoRichiesta=NEW.IdGruppo)
		THEN
			RAISE EXCEPTION 'L''utente non ha fatto richiesta di accedere al gruppo.';
		ELSIF NOT EXISTS(SELECT 1
			    	     FROM RichiestaDiAccesso
			     	   	 WHERE StatoRichiesta='Accettato' AND IdUtenteRichiesta=NEW.IdUtente AND IdGruppoRichiesta=NEW.IdGruppo)
		THEN
			RAISE EXCEPTION 'La richiesta di accesso dell''utente non è stata accettata.';
		END IF;
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER VerificaStatoRichiesta
BEFORE INSERT ON Iscrizione
FOR EACH ROW 
EXECUTE FUNCTION VerificaStatoRichiesta();

--	ControllaRichiestaUtenteIscritto
--La seguente funzione ha il compito di evitare che un utente possa mandare una richiesta di accesso ad un gruppo in cui è gia iscritto 
--possa mandare una richiesta di accesso 

CREATE OR REPLACE FUNCTION ControllaRichiestaUtenteIscritto() 
RETURNS TRIGGER AS $$
BEGIN 
	IF EXISTS(SELECT 1
		  FROM Iscrizione
		  WHERE NEW.idUtenteRichiesta=idUtente AND 
		  NEW.idGruppoRichiesta=idGruppo)
	THEN
		RAISE EXCEPTION  'Utente già iscritto al gruppo';
	END IF;
 	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE  TRIGGER ValiditàRichiestaDiAccesso 
BEFORE INSERT ON RichiestaDiAccesso
FOR EACH ROW 
EXECUTE FUNCTION ControllaRichiestaUtenteIscritto();


--	IscriviUtenteInGruppo
--La seguente funzione non appena la richiesta di accesso di un utente viene accettata, --quest’ultimo viene iscritto nel gruppo a cui aveva fatto richiesta.

CREATE OR REPLACE FUNCTION IscriviUtenteInGruppo()
RETURNS TRIGGER AS $$
BEGIN
	INSERT INTO Iscrizione VALUES (NEW.IdUtenteRichiesta,NEW.IdGruppoRichiesta);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER IscriviUtenteInGruppo
AFTER UPDATE ON RichiestaDiAccesso
FOR EACH ROW
WHEN (NEW.StatoRichiesta='Accettato')
EXECUTE FUNCTION IscriviUtenteInGruppo();

--	ValiditàCreatoreGruppoInRichiesta
--La seguente funzione controlla se l’id del creatore gruppo nella richiesta è corretto.

CREATE OR REPLACE FUNCTION ValiditàCreatoreInRichiesta()
RETURNS TRIGGER AS $$
BEGIN
	IF NOT EXISTS(SELECT 1
				  FROM CreatoreGruppo CG
				  WHERE CG.IdCreatore=NEW.IdCreatore AND CG.IdGruppo=NEW.IdGruppoRichiesta)
	THEN
		RAISE EXCEPTION 'L''idCreatore non corrisponde con quello del creatore del gruppo.';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER ValiditàCreatoreInRichiesta
BEFORE INSERT ON RichiestaDiAccesso
FOR EACH ROW
EXECUTE FUNCTION ValiditàCreatoreInRichiesta();

--	EliminaRichiestaAccesso
--La seguente funzione ha il compito di eliminare una richiesta di accesso dalla relativa tabella appena viene esitata come <<Rifiutato>>

CREATE OR REPLACE FUNCTION EliminaRichiestaAccesso()
RETURNS TRIGGER AS $$
BEGIN
	DELETE FROM RichiestaDiAccesso WHERE NEW.IdRichiesta=IdRichiesta;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER ControlloRichiestaRifiutata
AFTER UPDATE ON RichiestaDiAccesso
FOR EACH ROW
WHEN(NEW.StatoRichiesta='Rifiutato')
EXECUTE FUNCTION EliminaRichiestaAccesso(); 

--	EliminaNotificaRichiestaAccesso
--Dopo aver eliminato una richiesta di accesso,la seguente funzione elimina anche la notifica che ha –generato la corrispondente richiesta di accesso.

CREATE OR REPLACE FUNCTION EliminaNotificaRichiestaAccesso()
RETURNS TRIGGER AS $$
BEGIN 
	DELETE FROM  Notifica WHERE IdNotifica=OLD.IdNotificaGenerata;
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER EliminaNotificaRichiestaAccesso
AFTER DELETE ON RichiestaDiAccesso
FOR EACH ROW
EXECUTE FUNCTION EliminaNotificaRichiestaAccesso();


--	GestioneUscitaCreatoreGruppo
--Se il creatore di un gruppo,abbandona un gruppo,allora il gruppo deve essere eliminato
 
CREATE OR REPLACE FUNCTION GestioneUscitaCreatoreGruppo()
RETURNS TRIGGER AS $$
BEGIN
		IF EXISTS (SELECT 1
			       FROM CreatoreGruppo C
			       WHERE C.IdGruppo=OLD.IdGruppo AND C.IdUtente=OLD.IdUtente)
		THEN
			DELETE FROM Gruppo WHERE IdGruppo=OLD.IdGruppo;
		END IF;
		RETURN OLD; 
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER GestioneUscitaCreatoreGruppo
AFTER DELETE ON Iscrizione 
FOR EACH ROW
EXECUTE FUNCTION GestioneUscitaCreatoreGruppo();

--	GestioneEliminazioneUtenteCreatoreGruppo
--Se viene eliminato un utente, vengono eliminati tutti i gruppi in cui era un creatore

CREATE OR REPLACE FUNCTION GestioneEliminazioneUtenteCreatoreGruppo()
RETURNS TRIGGER AS $$
BEGIN
		DELETE FROM Gruppo WHERE IdGruppo IN (SELECT C.IdGruppo
								     	      FROM CreatoreGruppo C
								      	      WHERE C.IdUtente=OLD.IdUtente);
		RETURN OLD; 
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER GestioneEliminazioneUtenteCreatoreGruppo
BEFORE DELETE ON Utente 
FOR EACH ROW
EXECUTE FUNCTION GestioneEliminazioneUtenteCreatoreGruppo();

--	InviaNotificaNuovoPost
--La seguente funzione permette di inserire una nuova Notifica, che deve essere inviata a tutti e soli 
--gli iscritti al gruppo in cui è stato scritto il post.
--La funzione inserisce nella tabella Ricevere, l’utente che riceve la notifica con il rispettivo 
--IdNotifica e aggiorna l’attributo IdNotifica del Post che è stato scritto.

CREATE OR REPLACE FUNCTION InserisciNotificaPost(CreatorePost Utente.IdUtente%TYPE, UtenteNotifica Utente.IdUtente%TYPE,IdGruppoIN Gruppo.IdGruppo%TYPE,IdPostIN INT) 
RETURNS VOID AS $$
DECLARE
    Messaggio TEXT;
	NewIdNotifica INT;
	NomeGruppoIN Gruppo.NomeGruppo%TYPE;
	varNome Utente.NomeUtente%TYPE;
	varCognome Utente.CognomeUtente%TYPE;
BEGIN
	--Estraiamo il nome del gruppo 
	SELECT  G.NomeGruppo
	INTO NomeGruppoIN
	FROM Gruppo G
	WHERE G.IdGruppo=IdGruppoIN;

	--Estraiamo nome e cognome dell’utente creatore del post
	SELECT U.NomeUtente
	INTO varNome
	FROM Utente U
	WHERE U.IdUtente=CreatorePost;

	SELECT U.CognomeUtente
	INTO varCognome
	FROM Utente U
	WHERE U.IdUtente=CreatorePost;

	Messaggio := 'L''utente ' ||varNome||' '||varCognome||' ha scritto un nuovo post in: '||NomeGruppoIN||'.';
    INSERT INTO Notifica (DataInvio, OraInvio, TestoNotifica, TipoNotifica, IdNuovoPost)
    VALUES (CURRENT_DATE, CURRENT_TIME, Messaggio, 'Post', IdPostIN)
   	RETURNING IdNotifica INTO NewIdNotifica;    --Recupera l’ultimo ID generato
	UPDATE Post SET IdNotifica=NewIdNotifica WHERE IdPost=IdPostIN;
    INSERT INTO Ricevere VALUES(UtenteNotifica,NewIdNotifica);
END;
$$ LANGUAGE plpgsql;

--La seguente funzione di trigger permette di estrarre tutti gli utenti iscritti al gruppo in cui è stato 
--scritto il post e richiama la funzione InserisciNotificaPost

CREATE OR REPLACE FUNCTION InviaNotificaNuovoPost()
RETURNS TRIGGER AS $$
DECLARE 
       	 EstraiUtentiGruppo CURSOR FOR
		 SELECT I.IdUtente
    	 FROM Iscrizione I
         WHERE I.IdGruppo = NEW.IdGruppo AND I.IdUtente<>NEW.IdUtente;
BEGIN
  	FOR RigaUtenti IN EstraiUtentiGruppo      
    LOOP
        -- Chiamata alla funzione per ogni utente nel gruppo
    	PERFORM InserisciNotificaPost(NEW.IdUtente, RigaUtenti.IdUtente, NEW.IdGruppo,NEW.IdPost);
		RETURN NEW;
    END LOOP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER InviaNotificaNuovoPost
AFTER INSERT ON Post
FOR EACH ROW
EXECUTE FUNCTION InviaNotificaNuovoPost ();


--	ValiditàNotificaInterazionePost
--La seguente funzione di trigger si occupa di controllare la validità di una notifica, una notifica per essere valida deve avere una sola chiave esterna diversa da null e le altre due uguali a null quando è di tipo <<Interazione>> o <<Post>>.

CREATE OR REPLACE FUNCTION ValiditàNotificaInterazionePost()
RETURNS TRIGGER AS $$ 
BEGIN 
IF (
	(NEW.idNuovoLike IS NULL AND NEW.idNuovoCommento IS NULL AND NEW.idNuovoPost IS NULL) 
	OR
	(NEW.idNuovoLike IS NULL AND NEW.idNuovoCommento IS NOT NULL AND NEW.idNuovoPost IS NOT NULL) 
	OR
	(NEW.idNuovoLike IS NOT NULL AND NEW.idNuovoCommento IS NOT NULL AND NEW.idNuovoPost IS NULL)
	OR
	(NEW.idNuovoLike IS NOT NULL AND NEW.idNuovoCommento IS NULL AND NEW.idNuovoPost IS NOT NULL)
   )THEN
    		RAISE EXCEPTION 'La notifica non è valida';
  	END IF;
  	RETURN NEW;
END;
$$ LANGUAGE plpgsql; 


CREATE OR REPLACE TRIGGER NotificaInterazionePost
BEFORE INSERT ON Notifica 
FOR EACH ROW
WHEN (NEW.TipoNotifica='Interazione' OR NEW.TipoNotifica ='Post')
EXECUTE FUNCTION ValiditàNotificaInterazionePost();


--	ValiditàNotificaAccesso
--La seguente funzione controlla che gli attributi IdNuovoPost,IdNuovoLike,IdNuovoCommento sono 
--NULL per le notifiche di tipo <<Accesso>>
CREATE OR REPLACE FUNCTION ValiditàNotificaAccesso()
RETURNS TRIGGER AS $$ 
BEGIN 
	IF(NEW.IdNuovoCommento IS NOT NULL OR NEW.IdNuovoLike IS NOT NULL OR NEW.IdNuovoPost  IS NOT NULL)
	THEN
		RAISE EXCEPTION 'Attributi della notifica non validi.';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER ValiditàNotificaAccesso
BEFORE INSERT ON Notifica
FOR EACH ROW 
WHEN (NEW.TipoNotifica='Accesso')
EXECUTE FUNCTION ValiditàNotificaAccesso();


--	AggiungiIscritto 
--La seguente funzione permette di aggiornare il numero di iscritti quando un utente entra in un
--gruppo.

CREATE OR REPLACE FUNCTION AumentaNumeroIscritti(GruppoIN IN INT)
RETURNS VOID AS $$
BEGIN 
	--Aggiorno il numero di iscritti
	UPDATE Gruppo SET NumeroIscritti=NumeroIscritti+1
	WHERE IdGruppo=GruppoIN;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION AggiungiIscritto()
RETURNS TRIGGER AS $$
BEGIN
	PERFORM AumentaNumeroIscritti(NEW.IdGruppo);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE TRIGGER AggiungiIscritto
AFTER INSERT ON Iscrizione
FOR EACH ROW
EXECUTE FUNCTION AggiungiIscritto();

--	RimuoviIscritto 
--La seguente funzione permette di aggiornare il numero di iscritti quando un utente esce da un
--gruppo.

CREATE OR REPLACE FUNCTION DiminuisciNumeroIscritti(GruppoIN IN INT)
RETURNS VOID AS $$
BEGIN 
	--Aggiorno il numero di iscritti
	UPDATE Gruppo SET NumeroIscritti=NumeroIscritti-1
	WHERE IdGruppo=GruppoIN;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION RimuoviIscritto()
RETURNS TRIGGER AS $$
BEGIN
	PERFORM DiminuisciNumeroIscritti(NEW.IdGruppo);
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;



CREATE OR REPLACE TRIGGER RimuoviIscritto
BEFORE DELETE ON Iscrizione
FOR EACH ROW
EXECUTE FUNCTION RimuoviIscritto();

--	AggiungiLike
--La seguente funzione permette di aumentare il numero di Like per un commento o un post

CREATE OR REPLACE FUNCTION IncrementaLike(CommentoIN IN Commento.IdCommento%TYPE,PostIN IN Post.IdPost%TYPE)
RETURNS VOID AS $$
BEGIN 
	--Solo uno tra IdCommento e IdPost è NOT NULL in una riga di Like_ poiché uno stesso like
	--non può fare riferimento ad un Post e ad un Commento
	IF(CommentoIN IS NOT NULL) THEN 
		UPDATE Commento SET NumeroLike=NumeroLike+1 
		WHERE IdCommento=CommentoIN;
	ELSE 
		UPDATE Post SET NumeroLike=NumeroLike+1
		WHERE IdPost=PostIN;
	END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION AggiungiLike()
RETURNS TRIGGER AS $$
BEGIN
		PERFORM IncrementaLike(NEW.IdCommento,NEW.IdPost);
		RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER AggiungiLike
AFTER INSERT ON Like_
FOR EACH ROW
EXECUTE FUNCTION AggiungiLike();


--	RimuoviLike
--La seguente funzione permette di aumentare il numero di Like per un commento o un post

CREATE OR REPLACE FUNCTION DecrementaLike(CommentoIN IN Commento.IdCommento%TYPE,PostIN IN Post.IdPost%TYPE)
RETURNS VOID AS $$
BEGIN 
	--Solo uno tra IdCommento e IdPost è NOT NULL in una riga di Like_ poiché uno stesso like
	--non può fare riferimento ad un Post e ad un Commento
	IF(CommentoIN IS NOT NULL)
	THEN 
		UPDATE Commento SET NumeroLike=NumeroLike-1 
		WHERE IdCommento=CommentoIN;
	ELSE 
		UPDATE Post SET NumeroLike=NumeroLike-1
		WHERE IdPost=PostIN;
	END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION SottraiLike()
RETURNS TRIGGER AS $$
BEGIN
		PERFORM DecrementaLike(OLD.IdCommento,OLD.IdPost);
		RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER SottraiLike
AFTER DELETE ON Like_
FOR EACH ROW
EXECUTE FUNCTION SottraiLike();

--	ControlloUtenteLikeIscrittoAlGruppo
--La seguente funzione controlla che l’IdUtente di una riga da inserire in Like_ corrisponda a quello 
--di un utente iscritto al gruppo in cui è stato scritto il post o il commento,altrimenti blocca l’inserimento del like.

CREATE OR REPLACE FUNCTION ControlloUtenteLikeIscrittoAlGruppo()
RETURNS TRIGGER AS $$
DECLARE 
	varIdGruppo Gruppo.IdGruppo%TYPE;
BEGIN 
	--Recupero l'id del gruppo in cui il like è stato inserito 
	IF NEW.IdCommento IS NULL THEN   --Il like è per un post
		SELECT IdGruppo
		INTO varIdGruppo
		FROM Post
		WHERE IdPost=NEW.IdPost;
	ELSE  				--Il like è per un commento
		SELECT IdGruppo
		INTO varIdGruppo
		FROM Commento C JOIN Post P
		ON C.IdPostCommentato=P.IdPost
		WHERE IdCommento=NEW.IdCommento;
	END IF;
		
	IF NEW.IdUtente NOT IN(SELECT IdUtente
			       FROM Iscrizione 
			       WHERE IdGruppo=varIdGruppo)
	THEN
		RAISE EXCEPTION 'L''utente non è iscritto al gruppo.';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;
		
CREATE OR REPLACE TRIGGER ControlloUtenteLikeIscrittoAlGruppo
BEFORE INSERT ON Like_
FOR EACH ROW 
EXECUTE FUNCTION ControlloUtenteLikeIscrittoAlGruppo();

-- 	AggiungiCommento 
--La seguente funzione permette di incrementare il numero di commenti sotto a un post
--non appena viene inserita una nuova riga in Commento

CREATE OR REPLACE FUNCTION AggiungiCommento()
RETURNS TRIGGER AS $$
BEGIN 
	UPDATE Post SET NumeroCommenti=NumeroCommenti+1
	WHERE IdPost=NEW.IdPostCommentato;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER AggiungiCommento
AFTER INSERT ON Commento
FOR EACH ROW
EXECUTE FUNCTION AggiungiCommento();

-- 	RimuoviCommento
--La seguente funzione permette di decrementare il numero di commenti sotto a un post
--non appena viene eliminata una  riga in Commento 

CREATE OR REPLACE FUNCTION RimuoviCommento()
RETURNS TRIGGER AS $$
BEGIN 
	UPDATE Post SET NumeroCommenti=NumeroCommenti-1
	WHERE IdPost=OLD.IdPostCommentato;
	RETURN OLD;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER RimuoviCommento
AFTER DELETE ON Commento
FOR EACH ROW
EXECUTE FUNCTION RimuoviCommento();

-- 	ControllaDataOraPost
--la seguente funzione si occupa di controllare che l'inserimento di un post avvenga rispettando la 
--data e l'orario attuali, di conseguenza vieta inserimenti di post in date passate o future

CREATE OR REPLACE FUNCTION ControllaDataOraPost()
RETURNS TRIGGER AS $$
BEGIN
	IF (NEW.DataPubblicazione <> CURRENT_DATE) THEN 
		RAISE EXCEPTION  'Impossibile inserire un post che non abbia la data odierna';
	ELSIF (NEW.OraPubblicazione <> CURRENT_TIME) THEN
		RAISE EXCEPTION 'Impossibile inserire un post che non abbia l''orario corrente';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER ControllaDataOraPost
BEFORE INSERT ON Post
FOR EACH ROW 
EXECUTE FUNCTION ControllaDataOraPost();

--	DataOraPostNonModificabile
--La data e l'orario di un post non possono essere modificati una volta
--che sono stati inseriti in fase di creazione del post

CREATE OR REPLACE FUNCTION DataOraPostNonModificabile()
RETURNS TRIGGER AS $$
BEGIN
    IF OLD.OraPubblicazione IS DISTINCT FROM NEW.OraPubblicazione  OR OLD.DataPubblicazione IS DISTINCT FROM NEW.DataPubblicazione  THEN
        RAISE EXCEPTION 'Non è consentito modificare data o orario.';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER DataOraPostNonModificabile
BEFORE UPDATE ON Post
FOR EACH ROW
EXECUTE FUNCTION DataOraPostNonModificabile();


--	ControlloUtenteCommentoIscrittoAlGruppo
--La seguente funzione permette di controllare che un Utente che vuole inserire un commento sia
--iscritto al gruppo in cui commenta,se così non è allora blocca l'inserimento 

CREATE OR REPLACE FUNCTION ControlloUtenteCommentoIscrittoAlGruppo ()
RETURNS TRIGGER AS $$
DECLARE 
	varIdGruppo Gruppo.IdGruppo%TYPE;
BEGIN 
	--Recupero l’IdGruppo in cui si trova il post (o il commento sotto il post) commentato
	SELECT IdGruppo
	INTO varIdGruppo
	FROM Post
	WHERE IdPost=NEW.IdPostCommentato;

	IF NEW.IdUtente NOT IN (SELECT IdUtente
				FROM Iscrizione 
				WHERE IdGruppo=varIdGruppo)
	THEN
		RAISE EXCEPTION 'L''utente non è iscritto al gruppo.';
	END IF;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER ControlloUtenteCommentoIscrittoAlGruppo
BEFORE INSERT ON Commento
FOR EACH ROW
EXECUTE FUNCTION ControlloUtenteCommentoIscrittoAlGruppo();


--	CreaGruppo
--La seguente funzione, che prende in input l’id di un utente che vuole creare un gruppo e le 
--informazioni necessarie per la creazione del gruppo,permette di creare un gruppo,nominare
--creatore gruppo un utente


CREATE OR REPLACE FUNCTION CreaGruppo(UtenteCreatoreIN IN Utente.IdUtente%TYPE,
NomeGruppoIN IN Gruppo.NomeGruppo%TYPE,TagIN IN Gruppo.TagGruppo%TYPE,
DescrIN IN Gruppo.DescrizioneGruppo%TYPE)
RETURNS INT AS $$
DECLARE
	varIdGruppo Gruppo.IdGruppo%TYPE;
BEGIN
	--Inserisci dati del nuovo gruppo
	INSERT INTO Gruppo(NomeGruppo,TagGruppo,DescrizioneGruppo)
	VALUES(NomeGruppoIN,TagIN,DescrIN)
	RETURNING IdGruppo INTO varIdGruppo;
	--Inserisci quale utente ha creato il gruppo
	INSERT INTO CreatoreGruppo(IdUtente,IdGruppo) VALUES (UtenteCreatoreIN,varIdGruppo);
	RETURN varIdGruppo;
END;
$$ LANGUAGE plpgsql;


--	InviaNotificaInterazioneLike
-- la seguente funzione, all'inserimento di una nuova riga nella tabbella Like_ per un Post, consente di 
-- inviare la relativa notifica al creatore del Post, questo si traduce con l'inserimento di una nuova riga nella tabella Notifica

CREATE OR REPLACE FUNCTION NotificaInterazioneLike()
RETURNS TRIGGER AS $$
DECLARE 
  	NomeUtenteInterazione Utente.NomeUtente%TYPE;
  	CognomeUtenteInterazione Utente.NomeUtente%TYPE;
  	NewIdNotifica Notifica.IdNotifica%TYPE;
  	CreatorePost Post.IdUtente%TYPE;
  	Messaggio TEXT;
BEGIN
  	--Salvo il nome dell'utente che ha interagito
  	SELECT NomeUtente 
  	INTO NomeUtenteInterazione
  	FROM Utente U
  	WHERE U.IdUtente = NEW.IdUtente;
  	--Salvo il cognome dell'utente che ha interagito
  	SELECT CognomeUtente 
  	INTO CognomeUtenteInterazione
  	FROM Utente U
 	 WHERE U.IdUtente = NEW.IdUtente;
	--Salvo l'id del Creatore del Post
  	SELECT IdUtente 
  	INTO CreatorePost
  	FROM Post 
  	WHERE IdPost = NEW.IdPost;

	--Se il creatore post ha messo like al suo post non deve ricevere la notifica
	IF CreatorePost = NEW.IdUtente 
	THEN
		RETURN NULL;
	END IF;

	Messaggio:= 'L''utente '|| NomeUtenteInterazione ||' '|| CognomeUtenteInterazione||'ha messo "Mi piace" al tuo post.';

  	--Creo e salvo l'id della notifica appena generata
  	INSERT INTO Notifica(DataInvio,OraInvio,TestoNotifica,TipoNotifica,IdNuovoLike)
  	VALUES (CURRENT_DATE,CURRENT_TIME,Messaggio,'Interazione',NEW.IdLike)
  	RETURNING IdNotifica INTO NewIdNotifica;

 	 --Invio della notifica al creatore del post
  	INSERT INTO Ricevere
  	VALUES(CreatorePost, NewIdNotifica);
  	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER InviaNotificaInterazioneLike
AFTER INSERT ON Like_
FOR EACH ROW
WHEN (NEW.IdPost IS NOT NULL)
EXECUTE FUNCTION NotificaInterazioneLike();


--	InviaNotificaInterazioneCommento
-- la seguente funzione, all'inserimento di una nuova riga nella tabbella Commento per un Post, consente di 
-- inviare la relativa notifica al creatore del Post, questo si traduce con l'inserimento di una nuova riga nella tabella Notifica

CREATE OR REPLACE FUNCTION NotificaInterazioneCommento()
RETURNS TRIGGER AS $$
DECLARE 
  	NomeUtenteInterazione Utente.NomeUtente%TYPE;
  	CognomeUtenteInterazione Utente.NomeUtente%TYPE;
  	NewIdNotifica Notifica.IdNotifica%TYPE;
  	CreatorePost Post.IdUtente%TYPE;
  	Messaggio TEXT;
BEGIN
  	--Salvo il nome dell'utente che ha interagito
  	SELECT NomeUtente 
  	INTO NomeUtenteInterazione
	FROM Utente U
	WHERE U.IdUtente = NEW.IdUtente;

	--Salvo il cognome dell'utente che ha interagito
	SELECT CognomeUtente 
	INTO CognomeUtenteInterazione
	FROM Utente U
	WHERE U.IdUtente = NEW.IdUtente;

  	--Salvo l'id del Creatore del Post
  	SELECT IdUtente 
  	INTO CreatorePost
  	FROM Post 
  	WHERE IdPost = NEW.IdPostCommentato;
	
	--Se il creatore post ha commentato sotto il suo post non deve ricevere la notifica
	IF CreatorePost = NEW.IdUtente THEN
		RETURN NULL;
	END IF;

	Messaggio:= 'L''utente '|| NomeUtenteInterazione ||' '|| CognomeUtenteInterazione||'ha commentato il tuo post.';

  	--Creo e salvo l'id della notifica appena generata
  	INSERT INTO Notifica(DataInvio,OraInvio,TestoNotifica,TipoNotifica,IdNuovoCommento)
  	VALUES (CURRENT_DATE,CURRENT_TIME,Messaggio,'Interazione',NEW.IdCommento)
  	RETURNING IdNotifica INTO NewIdNotifica;

  	--Invio della notifica al creatore del post
  	INSERT INTO Ricevere
  	VALUES(CreatorePost, NewIdNotifica);
  	RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER InviaNotificaInterazioneCommento
AFTER INSERT ON Commento
FOR EACH ROW
EXECUTE FUNCTION NotificaInterazioneCommento();

--	OttieniStatisticheGruppo
-- La seguente procedure si occupa di fornire delle statistiche mensili relative ad un gruppo, i parametri di ingresso sono 
-- idGruppo ed un mese e anno di riferimento, la funzione restituirà numero totale di post, commenti , like ai post relativi al periodo di riferimento.

CREATE OR REPLACE PROCEDURE OttieniStatisticheGruppo(IN idGruppoin Gruppo.idGruppo%TYPE,IN mese INT,IN anno INT,OUT numero_post INT,OUT numero_commenti INT,OUT numero_like INT)
AS $$
BEGIN 
    SELECT COUNT(*) INTO numero_post
    FROM Post
    WHERE IdGruppo = idGruppoin AND EXTRACT(MONTH FROM DataPubblicazione) = mese
    	AND EXTRACT(YEAR FROM DataPubblicazione) = anno;
   
    SELECT COUNT(*) INTO numero_commenti
    FROM Commento AS C , Post AS P
    WHERE P.IdGruppo = idGruppoin AND C.idPostCommentato=P.idPost
        AND EXTRACT(MONTH FROM P.DataPubblicazione) = mese
        AND EXTRACT(YEAR FROM P.DataPubblicazione) = anno;

   
    SELECT COUNT(*) INTO numero_like
    FROM Like_
    WHERE IdPost IN (SELECT IdPost FROM Post WHERE IdGruppo = idGruppo
                        AND EXTRACT(MONTH FROM DataPubblicazione) = mese
                        AND EXTRACT(YEAR FROM DataPubblicazione) = anno);
	RAISE NOTICE 'Numero commenti : %  Numero post : %  Numero like per i post: %',numero_commenti,numero_post,numero_like;
END;
$$ LANGUAGE plpgsql;

-- RecuperaIdUtenteConNickname
--La seguente funzione la utilizziamo per popolare il database

CREATE OR REPLACE FUNCTION RecuperaIdUtenteConNickname(NicknameIN IN Utente.Nickname%TYPE)
RETURNS INT AS $$
DECLARE
	varIdUtente Utente.IdUtente%TYPE;
BEGIN
	SELECT IdUtente
	INTO varIdUtente
	FROM Utente
	WHERE Nickname=NicknameIN;

	IF varIdUtente IS NULL
	THEN
		RAISE EXCEPTION 'L''utente % non esiste nel database',NicknameIN;
	END IF;
	RETURN varIdUtente;
END;
$$ LANGUAGE plpgsql;

-- RecuperaIdCreatoreConIdUtente
--La seguente funzione è utilizzata nel popolamento del DB

CREATE OR REPLACE FUNCTION RecuperaIdCreatoreConNickname(NicknameIN IN Utente.Nickname%TYPE,IdGruppoIN IN Gruppo.IdGruppo%TYPE)
RETURNS INT AS $$
DECLARE
	varIdUtente Utente.IdUtente%TYPE;
	varIdCreatore CreatoreGruppo.IdCreatore%TYPE;
BEGIN
	SELECT RecuperaIdUtenteConNickname(NicknameIN)
	INTO varIdUtente;

	SELECT IdCreatore
	INTO varIdCreatore
	FROM CreatoreGruppo
	WHERE IdUtente=varIdUtente AND IdGruppo=IdGruppoIN;

	IF varIdCreatore IS NULL 
	THEN
		RAISE EXCEPTION 'L''utente inserito non è un creatore di un gruppo';
	END IF;
	RETURN varIdCreatore;
END;
$$ LANGUAGE plpgsql;

-- IscriviUtente
--La seguente funzione è utilizzata nel popolamento del DB, per iscrivere un utente ad un gruppo

CREATE OR REPLACE FUNCTION IscriviUtente(IdUtenteIN IN Utente.IdUtente%TYPE,IdCreatoreIN IN CreatoreGruppo.IdCreatore%TYPE,IdGruppoIN IN Gruppo.IdGruppo%TYPE)
RETURNS VOID AS $$
DECLARE 
BEGIN 
	INSERT INTO RichiestaDiAccesso(StatoRichiesta,IdUtenteRichiesta,IdCreatore,IdGruppoRichiesta)
	VALUES ('Accettato',IdUtenteIN,IdCreatoreIN,IdGruppoIN);
END;
$$ LANGUAGE plpgsql;
