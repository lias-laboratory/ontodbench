*******************************************************************************QUERY 1****************************************************************************************************
--Query1
--(type GraduateStudent ?X)
--(takesCourse ?X http://www.Department0.University0.edu/GraduateCourse0)
-- This query bears large input and high selectivity. It queries about just one class and one property and does not assume any hierarchy information or inference.

-- VERTICAL
(SELECT T1.subject 
FROM triplets AS T1
WHERE (T1.predicat ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' AND T1.object='http://www.Department1.University0.edu/GraduateCourse0'))
INTERSECT
(SELECT T2.subject
FROM triplets AS T2
WHERE (T2.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T2.object ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent')) 
   
-- BINARY
(SELECT P1.subject 
FROM idprop1 AS P1 
WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent')
INTERSECT 
(SELECT P2.subject 
FROM idprop2 AS P2 
WHERE (P2.pr_value = 'http://www.Department1.University0.edu/GraduateCourse0'))

-- idprop1=SELECT id FROM idproperty WHERE property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type'
-- idprop2=SELECT id FROM idproperty WHERE property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'

-- HORIZONTAL
(SELECT C1.subject 
FROM idclass AS C1 
WHERE (C1.idpred = 'http://www.Department1.University0.edu/GraduateCourse0')) 
UNION 
(SELECT L.subject 
FROM left_over AS L, idclass AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' AND L.object = 'http://www.Department1.University0.edu/GraduateCourse0' AND L.subject=C2.subject))

-- idclass = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'
-- idpred  = SELECT id FROM idpredicat WHERE (id_class=idclass AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse')

*******************************************************************************QUERY 2****************************************************************************************************       
	 
--Query2
--(type GraduateStudent ?X)
--(type University ?Y)
--(type Department ?Z)
--(memberOf ?X ?Z)
--(subOrganizationOf ?Z ?Y)
--This query increases in complexity: 3 classes and 3 properties are involved. Additionally, there is a transitive pattern of relationships between the objects involved.

-- VERTICAL
SELECT T1.subject, T3.subject, T2.subject
FROM triplets AS T1, triplets AS T2, triplets AS T3, triplets AS T4, triplets AS T5 
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' AND T1.subject=T4.subject AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf' AND T4.object=T3.subject
	AND T3.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T3.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Department'   AND T3.subject=T5.subject AND T5.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf' AND T5.object=T2.subject
	AND T2.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T2.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University') 
	
-- BINARY
SELECT P1.subject,P3.subject,P4.subject 
FROM idprop1 AS P1, idprop2 AS P2, idprop3 AS P3, idprop1 AS P4 
WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' AND P1.subject=P2.subject AND P2.pr_value=P3.subject AND P3.pr_value=P4.subject AND P4.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University') ;

-- idprop1 =SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 =SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf')
-- idprop3 =SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf')
	
-- HORIZONTAL
SELECT C1.subject,C2.subject,C3.subject 
FROM idclass1 AS C1,idclass2 AS C2, idclass3 AS C3 
WHERE (C1.idpred1=C2.subject AND C2.idpred2=C3.subject )

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'
-- idclass2 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Department'
-- idclass3 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class= idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf'
-- idpred2  = SELECT id FROM idpredicat WHERE id_class=idclass2  AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf'
																		
*******************************************************************************QUERY 3****************************************************************************************************
--Query3
--(type Publication ?X)
--(publicationAuthor ?X http://www.Department0.University0.edu/AssistantProfessor0)
--This query is similar to Query 1 but class Publication has a wide hierarchy.	

-- VERTICAL
(SELECT T1.subject
FROM triplets AS T1
WHERE (T1.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'))
INTERSECT 
(SELECT T2.subject 
FROM triplets AS T2
WHERE (T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' AND T2.object = 'http://www.Department0.University0.edu/AssistantProfessor0'))

-- BINARY
(SELECT P1.subject 
FROM idprop1 AS P1 
WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication')
INTERSECT 
(SELECT P2.subject 
FROM   idprop2 AS P2 
WHERE  P2.pr_value = 'http://www.Department0.University0.edu/AssistantProfessor0') 

-- idprop1 = SELECT id FROM idproperty WHERE (property ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE  (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor')
			
-- HORIZONTAL
(SELECT C1.subject 
FROM idclass1 AS C1 
WHERE (C1.idpred1 = 'http://www.Department0.University0.edu/AssistantProfessor0'))
UNION (SELECT L.subject 
FROM left_over AS L, idclass1 AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' AND L.object = 'http://www.Department0.University0.edu/AssistantProfessor0' AND L.subject=C2.subject)) 

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor'


*******************************************************************************QUERY 4****************************************************************************************************
--Query4
--(type FullProfessor ?X)
--(worksFor ?X http://www.Department0.University0.edu)

--VERTICAL
(SELECT T1.subject
FROM triplets T1
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'))
INTERSECT
(SELECT T2.subject
FROM triplets AS T2
WHERE (T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' AND T2.object = 'http://www.Department0.University0.edu'))

	
--BINARY
(SELECT P1.subject 
FROM  idprop1 AS P1 
WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor') 
INTERSECT 
(SELECT P2.subject 
FROM idprop2 AS P2 
WHERE (P2.pr_value = 'http://www.Department0.University0.edu'))

-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')
			
--HORIZONTAL
SELECT C1.subject
FROM idclass1 AS C1 
WHERE (C1.idpred1 = 'http://www.Department0.University0.edu') 

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'

*******************************************************************************QUERY 5****************************************************************************************************	
--Query5
--(type Professor ?X)
--(worksFor ?X http://www.Department0.University0.edu)
--(name ?X ?Y1)
--(emailAddress ?X ?Y2)
--(telephone ?X ?Y3)
--This query has small input and high selectivity. Class Professor has a wide hierarchy. Another feature is that it queries about multiple properties of a single class.

--VERTICAL
SELECT T1.subject,T3.object,T4.object,T5.object
FROM triplets AS T1,triplets AS T2,triplets AS T3,triplets AS T4, triplets AS T5
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
	AND (T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor' 
		OR T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor' 
		OR T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor') 
	AND T1.subject=T2.subject
	AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' 
	AND T2.object ='http://www.Department0.University0.edu' 
	AND T1.subject=T3.subject 
	AND T3.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
	AND T1.subject=T4.subject 
	AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress' 
	AND T1.subject=T5.subject 
	AND T5.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone')
	ORDER BY (T1.SUBJECT) 
	
--BINARY
 SELECT P1.subject,P3.pr_value,P4.pr_value,P5.pr_value 
FROM idprop1 AS P1, idprop2 AS P2, idprop3 AS P3, idprop4 AS P4, idprop5 AS P5
WHERE ((P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor' 
		OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor' 
		OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' ) 
	AND P1.subject=P2.subject 
	AND P2.pr_value = 'http://www.Department0.University0.edu'
	AND P1.subject=P3.subject 
	AND P1.subject=P4.subject 
	AND P1.subject=P5.subject)
	
-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')
-- idprop3 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name')
-- idprop4 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress')
-- idprop5 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone')
		
--HORIZONTAL
(SELECT SUB,NAME,MAIL,TEL
FROM ((SELECT C1.subject AS SUB, C1.idpred11 AS NAME, C1.idpred12 AS MAIL, C1.idpred13 AS TEL 
		FROM  idclass1 AS C1 
		WHERE (C1.idpred14 = 'http://www.Department0.University0.edu')) 
		UNION 
		(SELECT C2.subject, C2.idpred21, C2.idpred22 ,C2.idpred23
		FROM  idclass2 AS C2 
		WHERE (C2.idpred24 = 'http://www.Department0.University0.edu'))) AS T1) 
UNION 
(SELECT C3.subject,C3.idpred31 ,C3.idpred32, C3.idpred33
FROM  idclass3 AS C3 
WHERE (C3.idpred34 = 'http://www.Department0.University0.edu'))

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'
-- idclass2 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor'
-- idclass3 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor'
-- idpred11 = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
-- idpred12 = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'
-- idpred13 = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'
-- idpred14 = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'
-- idpred21 = SELECT id FROM idpredicat WHERE id_class=idclass2 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
-- idpred22 = SELECT id FROM idpredicat WHERE id_class=idclass2 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'
-- idpred23 = SELECT id FROM idpredicat WHERE id_class=idclass2 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'
-- idpred24 = SELECT id FROM idpredicat WHERE id_class=idclass2 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'
-- idpred31 = SELECT id FROM idpredicat WHERE id_class=idclass3 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
-- idpred32 = SELECT id FROM idpredicat WHERE id_class=idclass3 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'
-- idpred33 = SELECT id FROM idpredicat WHERE id_class=idclass3 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'
-- idpred34 = SELECT id FROM idpredicat WHERE id_class=idclass3 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'

*******************************************************************************QUERY 6****************************************************************************************************
--Query6
-- Count the number of instance of type Publication

--VERTICAL
SELECT T1.object,COUNT(T1.subject) 
FROM triplets AS T1
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication') 
GROUP BY T1.object

--BINARY
SELECT P.pr_value, COUNT(P.subject) 
FROM idprop1 AS P 
WHERE (P.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication')
GROUP BY(P.pr_value)
	--idprop1=SELECT id FROM idproperty WHERE (property LIKE 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
	
--HORIZONTAL
SELECT COUNT(*) FROM idclass

-- idclass = SELECT id FROM idclass WHERE type Like 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'
	
*******************************************************************************QUERY 7 ****************************************************************************************************		
--Query7 (Query1 using joint)
--(type GraduateStudent ?X)
--(takesCourse ?X http://www.Department0.University0.edu/GraduateCourse0)
-- This query bears large input and high selectivity. It queries about just one class and one property and does not assume any hierarchy information or inference.
	

-- VERTICAL
SELECT T1.subject 
FROM triplets AS T1, triplets AS T2
WHERE (T1.predicat ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' AND T1.object='http://www.Department1.University0.edu/GraduateCourse0' AND T1.subject=T2.subject
		AND T2.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T2.object ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent') 
   
-- BINARY
SELECT P1.subject 
FROM idprop1 AS P1,idprop2 AS P2 
WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' AND P1.subject=P2.subject
		AND P2.pr_value = 'http://www.Department1.University0.edu/GraduateCourse0')

-- idprop1=SELECT id FROM idproperty WHERE property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type'
-- idprop2=SELECT id FROM idproperty WHERE property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'

-- HORIZONTAL
(SELECT C1.subject 
FROM idclass AS C1 
WHERE (C1.idpred = 'http://www.Department1.University0.edu/GraduateCourse0')) 
UNION 
(SELECT L.subject 
FROM left_over AS L, idclass AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' AND L.object = 'http://www.Department1.University0.edu/GraduateCourse0' AND L.subject=C2.subject))

-- idclass = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'
-- idpred  = SELECT id FROM idpredicat WHERE ((id_class=idclass) AND (predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'))

*******************************************************************************QUERY 8****************************************************************************************************
--Query8 (Query3 using joint)
--(type Publication ?X)
--(publicationAuthor ?X http://www.Department0.University0.edu/AssistantProfessor0)
--This query is similar to Query 1 but class Publication has a wide hierarchy.	

-- VERTICAL
SELECT T1.subject
FROM triplets AS T1,triplets AS T2
WHERE (T1.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication' AND T1.subject=T2.subject
		AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' AND T2.object = 'http://www.Department0.University0.edu/AssistantProfessor0')

-- BINARY
SELECT P1.subject 
FROM idprop1 AS P1,idprop2 AS P2  
WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication' AND P1.subject=P2.subject
		AND P2.pr_value = 'http://www.Department0.University0.edu/AssistantProfessor0') 

-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE  (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor')
			
-- HORIZONTAL
(SELECT C1.subject 
FROM idclass1 AS C1 
WHERE (C1.idpred1 = 'http://www.Department0.University0.edu/AssistantProfessor0'))
UNION (SELECT L.subject 
FROM left_over AS L, idclass1 AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' AND L.object = 'http://www.Department0.University0.edu/AssistantProfessor0' AND L.subject=C2.subject)) 

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor'

*******************************************************************************QUERY 9****************************************************************************************************
--Query9 (Query4 using joint)
--(type FullProfessor ?X)
--(worksFor ?X http://www.Department0.University0.edu)

--VERTICAL
SELECT T1.subject
FROM triplets T1,triplets AS T2
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' AND T1.subject=T2.subject
		AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' AND T2.object = 'http://www.Department0.University0.edu')

	
--BINARY
SELECT P1.subject 
FROM  idprop1 AS P1,idprop2 AS P2  
WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' AND P1.subject=P2.subject
		AND P2.pr_value = 'http://www.Department0.University0.edu')

-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')
			
--HORIZONTAL
SELECT C1.subject
FROM idclass1 AS C1 
WHERE (C1.idpred1 = 'http://www.Department0.University0.edu') 

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'
