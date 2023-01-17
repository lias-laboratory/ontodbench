CREATE OR REPLACE FUNCTION VSCHEMA() RETURNS INTEGER AS $$

	BEGIN 
	    DROP TABLE IF EXISTS v_subject ; 
	    DROP TABLE IF EXISTS v_object ; 
		DROP TABLE IF EXISTS v_predicat ; 
		DROP TABLE IF EXISTS triplets CASCADE ; 
		DROP TABLE IF EXISTS types CASCADE ;
		DROP TABLE IF EXISTS propertytab CASCADE ; 
		CREATE TABLE triplets (subject TEXT,predicat TEXT, object TEXT, CONSTRAINT cle PRIMARY KEY(subject,predicat,object)); 
		CREATE TABLE types(type TEXT, nbrinstance INT, CONSTRAINT cle1 PRIMARY KEY(TYPE));
		CREATE TABLE propertytab(type TEXT,champ TEXT,occurrences INT, CONSTRAINT cle2 PRIMARY KEY(type,champ)) ;
		CREATE INDEX i_triplet ON triplets(predicat);
		CREATE INDEX i_propertytab ON propertytab(champ);
		
		RETURN 1; 
	END; 
$$LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION VMATERIALIZER() RETURNS INTEGER AS $$

	BEGIN 
		CREATE OR REPLACE VIEW v_subject AS SELECT * FROM triplets ORDER BY subject ; 
		CREATE OR REPLACE VIEW v_predicat AS SELECT * FROM triplets ORDER BY predicat ; 	
		CREATE OR REPLACE VIEW v_object AS SELECT * FROM triplets ORDER BY object ; 
		CREATE INDEX iv_subject  ON v_subject(subject);
		CREATE INDEX iv_predicat ON v_predicat(predicat);
		CREATE INDEX iv_object   ON v_object(object);
		
		RETURN 1; 
	END; 
$$LANGUAGE PLPGSQL; 

CREATE OR REPLACE FUNCTION VUPLOAD() RETURNS INTEGER AS $$ --TEXT DEFAULT 'c:\suivi.txt'
	DECLARE 
	FICHIER TEXT;
	SCALE FLOAT8 ;
	
	BEGIN 
		FICHIER='d:\vupload.txt';
		INSERT INTO types (SELECT T1.object,COUNT(T1.subject) FROM triplets AS T1 WHERE (T1.predicat LIKE '%#type') GROUP BY T1.object);
		SCALE=25;
		EXECUTE 'COPY (Select '||quote_literal(SCALE)||' ) TO '||quote_literal(FICHIER);
		INSERT INTO propertytab ( SELECT T1.object,T2.predicat,COUNT(T1.subject)
								  FROM triplets AS T1,(SELECT DISTINCT ON (subject,predicat) * FROM triplets) AS T2 
								  WHERE (T1.predicat LIKE '%#type') AND (T2.predicat NOT LIKE '%#type') AND (T1.subject=T2.subject)
								  GROUP BY T1.object, T2.predicat);
		SCALE=50;
		EXECUTE 'COPY (Select '||quote_literal(SCALE)||' ) TO '||quote_literal(FICHIER);
		
		RETURN 1;
	END;
$$LANGUAGE PLPGSQL; 