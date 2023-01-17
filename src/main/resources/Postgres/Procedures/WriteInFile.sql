SELECT [STRAIGHT_JOIN]
       [SQL_SMALL_RESULT] [SQL_BIG_RESULT] [SQL_BUFFER_RESULT]
       [SQL_CACHE | SQL_NO_CACHE] [SQL_CALC_FOUND_ROWS] [HIGH_PRIORITY]
       [DISTINCT | DISTINCTROW | ALL]
    select_expression,...
    [INTO {OUTFILE | DUMPFILE} 'nom_fichier' export_options]
    [FROM table_references
      [WHERE where_definition]
      [GROUP BY {unsigned_integer | nom_de_colonne | formula} [ASC | DESC], ...
      [HAVING where_definition]
      [ORDER BY {unsigned_integer | nom_de_colonne | formula} [ASC | DESC] ,...]
      [LIMIT [offset,] lignes]
      [PROCEDURE procedure_name(argument_list)]
      [FOR UPDATE | LOCK IN SHARE MODE]]
	  
	  
SELECT 1
INTO OUTFILE 'c:\log.txt'
COPY idproperty TO 'c:\log.txt'
COPY (Select 1) TO 'c:\log.txt'