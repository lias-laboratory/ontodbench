*******************************************************************************QUERY 1****************************************************************************************************
--Query1
--(type GraduateStudent ?X)
--(takesCourse ?X http://www.Department0.University0.edu/GraduateCourse0)
-- This query bears large input and high selectivity. It queries about just one class and one property and does not assume any hierarchy information or inference.

-- VERTICAL
(SELECT T1.subject 
FROM triplets AS T1
WHERE (T1.predicat ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' 
		AND T1.object='http://www.Department0.University0.edu/GraduateCourse0'))
INTERSECT
(SELECT T2.subject
FROM triplets AS T2
WHERE (T2.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T2.object ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent')) 
   
-- BINARY
(SELECT P1.subject 
FROM property18 AS P1 
WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent')
INTERSECT 
(SELECT P2.subject 
FROM property11 AS P2 
WHERE (P2.pr_value = 'http://www.Department0.University0.edu/GraduateCourse0'))

-- idprop1=SELECT id FROM idproperty WHERE property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type'
-- idprop2=SELECT id FROM idproperty WHERE property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'

-- HORIZONTAL
(SELECT C1.subject 
FROM class7 AS C1 
WHERE (C1.predicat37 = 'http://www.Department0.University0.edu/GraduateCourse0')) 
UNION 
(SELECT L.subject 
FROM left_over AS L, class7 AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' 
		AND L.object = 'http://www.Department0.University0.edu/GraduateCourse0' 
		AND L.subject=C2.subject))

-- idclass = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'
-- idpred  = SELECT id FROM idpredicat WHERE (id_class='idclass' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse')

*******************************************************************************QUERY 2 ****************************************************************************************************		
--Query2 (Query1 using joint)
--(type GraduateStudent ?X)
--(takesCourse ?X http://www.Department0.University0.edu/GraduateCourse0)
-- This query bears large input and high selectivity. It queries about just one class and one property and does not assume any hierarchy information or inference.
	

-- VERTICAL
SELECT T1.subject 
FROM triplets AS T1, triplets AS T2
WHERE (T1.predicat ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' 
		AND T1.object='http://www.Department0.University0.edu/GraduateCourse0' 
		AND T1.subject=T2.subject
		AND T2.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T2.object ='http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent') 
   
-- BINARY
SELECT P1.subject 
FROM property18 AS P1,property11 AS P2 
WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' 
		AND P1.subject=P2.subject
		AND P2.pr_value = 'http://www.Department0.University0.edu/GraduateCourse0')

-- idprop1=SELECT id FROM idproperty WHERE property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type'
-- idprop2=SELECT id FROM idproperty WHERE property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'

-- HORIZONTAL
(SELECT C1.subject 
FROM class7 AS C1 
WHERE (C1.predicat37 = 'http://www.Department0.University0.edu/GraduateCourse0')) 
UNION 
(SELECT L.subject 
FROM left_over AS L, class7 AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse' 
		AND L.object = 'http://www.Department0.University0.edu/GraduateCourse0' 
		AND L.subject=C2.subject))

-- idclass = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'
-- idpred  = SELECT id FROM idpredicat WHERE (id_class='idclass' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse')

*******************************************************************************QUERY 3****************************************************************************************************
--Query3
--(type Publication ?X)
--(publicationAuthor ?X http://www.Department0.University0.edu/AssistantProfessor0)
--This query is similar to Query 1 but class Publication has a wide hierarchy.	

-- VERTICAL
(SELECT T1.subject
FROM triplets AS T1
WHERE (T1.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'))
INTERSECT 
(SELECT T2.subject 
FROM triplets AS T2
WHERE (T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' 
		AND T2.object = 'http://www.Department0.University0.edu/AssistantProfessor0'))

-- BINARY
(SELECT P1.subject 
FROM property18 AS P1 
WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication')
INTERSECT 
(SELECT P2.subject 
FROM   property8 AS P2 
WHERE  P2.pr_value = 'http://www.Department0.University0.edu/AssistantProfessor0') 

-- idprop1 = SELECT id FROM idproperty WHERE (property ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor')
			
-- HORIZONTAL
(SELECT C1.subject 
FROM class9 AS C1 
WHERE (C1.predicat50 = 'http://www.Department0.University0.edu/AssistantProfessor0'))
UNION (SELECT L.subject 
FROM left_over AS L, class9 AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' 
		AND L.object = 'http://www.Department0.University0.edu/AssistantProfessor0' 
		AND L.subject=C2.subject)) 

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class= 'idclass1' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor'

*******************************************************************************QUERY 4****************************************************************************************************
--Query4 (Query3 using joint)
--(type Publication ?X)
--(publicationAuthor ?X http://www.Department0.University0.edu/AssistantProfessor0)
--This query is similar to Query 1 but class Publication has a wide hierarchy.	

-- VERTICAL
SELECT T1.subject
FROM triplets AS T1,triplets AS T2
WHERE (T1.predicat ='http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication' 
		AND T1.subject=T2.subject
		AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' 
		AND T2.object = 'http://www.Department0.University0.edu/AssistantProfessor0')

-- BINARY
SELECT P1.subject 
FROM property18 AS P1,property8 AS P2  
WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication' 
		AND P1.subject=P2.subject
		AND P2.pr_value = 'http://www.Department0.University0.edu/AssistantProfessor0') 

-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE  (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor')
			
-- HORIZONTAL
(SELECT C1.subject 
FROM class9 AS C1 
WHERE (C1.predicat50 = 'http://www.Department0.University0.edu/AssistantProfessor0'))
UNION 
(SELECT L.subject 
FROM left_over AS L, class9 AS C2 
WHERE (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor' 
		AND L.object = 'http://www.Department0.University0.edu/AssistantProfessor0' 
		AND L.subject=C2.subject)) 

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#publicationAuthor'

*******************************************************************************QUERY 5****************************************************************************************************
--Query5
--(type FullProfessor ?X)
--(worksFor ?X http://www.Department0.University0.edu)

--VERTICAL
(SELECT T1.subject
FROM triplets T1
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'))
INTERSECT
(SELECT T2.subject
FROM triplets AS T2
WHERE (T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' 
		AND T2.object = 'http://www.Department0.University0.edu'))

	
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

*******************************************************************************QUERY 6****************************************************************************************************
--Query6 (Query5 using joint)
--(type FullProfessor ?X)
--(worksFor ?X http://www.Department0.University0.edu)

--VERTICAL
SELECT T1.subject
FROM triplets T1,triplets AS T2
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' 
		AND T1.subject=T2.subject
		AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor' 
		AND T2.object = 'http://www.Department0.University0.edu')

	
--BINARY
SELECT P1.subject 
FROM  idprop1 AS P1,idprop2 AS P2  
WHERE P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' 
		AND P1.subject=P2.subject
		AND P2.pr_value = 'http://www.Department0.University0.edu')

-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')
			
--HORIZONTAL
SELECT C1.subject
FROM class5 AS C1 
WHERE (C1.predicat31 = 'http://www.Department0.University0.edu') 

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'

*******************************************************************************QUERY 7****************************************************************************************************       
--Query7
--(type GraduateStudent ?X)
--(type University ?Y)
--(type Department ?Z)
--(memberOf ?X ?Z)
--(subOrganizationOf ?Z ?Y)
--(undergraduateDegreeFrom ?X ?Y)
--This query increases in complexity: 3 classes and 3 properties are involved. Additionally, there is a transitive pattern of relationships between the objects involved.

-- VERTICAL
SELECT T1.subject, T5.subject, T3.subject
FROM triplets AS T1, triplets AS T2, triplets AS T3, triplets AS T4, triplets AS T5, triplets AS T6
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' 
		AND T1.subject=T2.subject 
		AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf' 
		AND T2.object=T3.subject
		AND T3.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T3.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Department'   
		AND T3.subject=T4.subject 
		AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf' 
		AND T4.object=T5.subject
		AND T5.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T5.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University' 
		AND T6.subject = T1.subject
		AND T6.object = T5.subject)
	
-- BINARY
SELECT P1.subject,P3.subject,P5.subject 
FROM property18 AS P1,property6 AS P2, property10 AS P3, property15 AS P4, property18 AS P5
WHERE (P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' 
		AND P1.subject=P2.subject 
		AND P2.pr_value=P3.subject 
		AND P3.pr_value=P5.subject 
		AND P5.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University'
		AND P4.subject = P1.subject
		AND P4.pr_value = P5.subject) ;

-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf')
-- idprop3 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf')
-- idprop4 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#undergraduateDegreeFrom')
	
-- HORIZONTAL
SELECT C1.subject,C2.subject,C3.subject 
FROM   class7 AS C1,class4 AS C2, class14 AS C3 
WHERE (C1.predicat35 = C2.subject 
		AND C2.predicat21 = C3.subject 
		AND C1.predicat40 = C3.subject)

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'
-- idclass2 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Department'
-- idclass3 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#University'
-- idpred1  = SELECT id FROM idpredicat WHERE id_class= 'idclass1' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#memberOf'
-- idpred12 = SELECT id FROM idpredicat WHERE id_class= 'idclass1' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#undergraduateDegreeFrom'
-- idpred2  = SELECT id FROM idpredicat WHERE id_class= 'idclass2' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#subOrganizationOf'

*******************************************************************************QUERY 8****************************************************************************************************	
--Query8
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
	AND T2.subject=T3.subject 
	AND T3.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
	AND T3.subject=T4.subject 
	AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress' 
	AND T4.subject=T5.subject 
	AND T5.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone')
	ORDER BY (T1.subject) 
	
--BINARY
SELECT P1.subject,P3.pr_value,P4.pr_value,P5.pr_value 
FROM property18 AS P1, property16 AS P2, property7 AS P3, property3 AS P4, property14 AS P5
WHERE ((P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor' 
		OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor' 
		OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor' ) 
	AND P1.subject=P2.subject 
	AND P2.pr_value = 'http://www.Department0.University0.edu'
	AND P1.subject=P3.subject 
	AND P1.subject=P4.subject 
	AND P1.subject=P5.subject)
ORDER BY (P1.subject) 
	
-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor')
-- idprop3 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name')
-- idprop4 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress')
-- idprop5 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone')
		
--HORIZONTAL
(SELECT SUB,NAME,MAIL,TEL
FROM ((SELECT C1.subject AS SUB, C1.predicat26 AS NAME, C1.predicat23 AS MAIL, C1.predicat29 AS TEL 
		FROM  class5 AS C1 
		WHERE (C1.predicat31 = 'http://www.Department0.University0.edu')) 
		UNION 
		(SELECT C2.subject, C2.predicat13, C2.predicat11 ,C2.predicat16
		FROM  class2 AS C2 
		WHERE (C2.predicat18 = 'http://www.Department0.University0.edu'))) AS T1) 
UNION 
(SELECT C3.subject,C3.predicat4, C3.predicat2, C3.predicat7
FROM  class1 AS C3 
WHERE (C3.predicat9 = 'http://www.Department0.University0.edu'))

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'
-- idclass2 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor'
-- idclass3 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor'
-- idpred11 = SELECT id FROM idpredicat WHERE id_class = 'idclass1' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
-- idpred12 = SELECT id FROM idpredicat WHERE id_class = 'idclass1' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'
-- idpred13 = SELECT id FROM idpredicat WHERE id_class = 'idclass1' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'
-- idpred14 = SELECT id FROM idpredicat WHERE id_class = 'idclass1' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'
-- idpred21 = SELECT id FROM idpredicat WHERE id_class = 'idclass2' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
-- idpred22 = SELECT id FROM idpredicat WHERE id_class = 'idclass2' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'
-- idpred23 = SELECT id FROM idpredicat WHERE id_class = 'idclass2' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'
-- idpred24 = SELECT id FROM idpredicat WHERE id_class = 'idclass2' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'
-- idpred31 = SELECT id FROM idpredicat WHERE id_class = 'idclass3' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#name'
-- idpred32 = SELECT id FROM idpredicat WHERE id_class = 'idclass3' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#emailAddress'
-- idpred33 = SELECT id FROM idpredicat WHERE id_class = 'idclass3' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#telephone'
-- idpred34 = SELECT id FROM idpredicat WHERE id_class = 'idclass3' AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#worksFor'


*******************************************************************************QUERY 9****************************************************************************************************
--Query9
-- Count the number of instance of type Publication

--VERTICAL
SELECT T1.object,COUNT(T1.subject) 
FROM triplets AS T1
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
		AND T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication') 
GROUP BY T1.object

--BINARY
SELECT P.pr_value, COUNT(P.subject) 
FROM property18 AS P 
WHERE (P.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication')
GROUP BY(P.pr_value)

--idprop1 = SELECT id FROM idproperty WHERE (property LIKE 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
	
--HORIZONTAL
SELECT COUNT(*) FROM class9

-- idclass = SELECT id FROM idclass WHERE type Like 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Publication'

*******************************************************************************QUERY 10****************************************************************************************************
--Query10
--(type Student ?X)
--(type Professor ?Y)
--(type Course ?Z)
--(advisor ?X ?Y)
--(takesCourse ?X ?Z)
--(teacherOf ?Y ?Z)
--Besides the aforementioned features of class Student and the wide hierarchy of class Professor, like Query 7, 
--this query is characterized by the most classes and properties in the query set and there is a triangular pattern of relationships.

--VERTICAL
SELECT T1.subject,T2.object,T3.object
FROM triplets AS T1,triplets AS T2,triplets AS T3,triplets AS T4
WHERE (T1.predicat = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type' 
	AND (T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' 
		OR T1.object = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#UndergraduateStudent') 
	AND T1.subject=T2.subject
	AND T2.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#advisor'
	AND T2.object =T3.subject
	AND T3.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf'
	AND T3.object=T4.object
	AND T4.predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'
	AND T4.subject  = T1.subject) 
ORDER BY (T1.SUBJECT) 

	
--BINARY
SELECT P1.subject,P3.subject,P4.pr_value
FROM property18 AS P1, property1 AS P2, property12 AS P3, property11 AS P4
WHERE ((P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent' 
		OR P1.pr_value = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#UndergraduateStudent') 
	AND P1.subject=P2.subject 
	AND P2.pr_value = P3.subject
	AND P3.pr_value=P4.pr_value
	AND P4.subject=P1.subject)
ORDER BY (P1.SUBJECT) 
	
-- idprop1 = SELECT id FROM idproperty WHERE (property = 'http://www.w3.org/1999/02/22-rdf-syntax-ns#type')
-- idprop2 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#advisor')
-- idprop3 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf')
-- idprop4 = SELECT id FROM idproperty WHERE (property = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse')

--HORIZONTAL
CREATE OR REPLACE VIEW v_student AS ((SELECT student, prof, cour
									  FROM ((SELECT C1.subject AS student, C1.predicat33 AS prof, C1.predicat37 AS cour
											 FROM  class7 AS C1)
											UNION
											(SELECT C2.subject AS student, C2.predicat67 AS prof, C2.predicat71 AS cour
											 FROM  class13 AS C2)) AS T)
									 UNION 
									(SELECT L.subject AS student, L.object AS prof, L1.object AS cour
									FROM class7 AS C1, class13 AS C2, left_over AS L, left_over AS L1
									WHERE ((L.subject=C1.subject OR L.subject=C2.subject) AND (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#advisor')
									AND (L.subject=L1.subject) AND (L1.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'))))
									
CREATE OR REPLACE VIEW v_prof  AS ((SELECT prof, cour
									FROM ((SELECT C3.subject AS prof, C3.predicat28 AS cour
										   FROM  class5 AS C3)
										   UNION
										  (SELECT  C4.subject AS prof, C4.predicat15 AS cour
										   FROM  class2 AS C4)) AS T2)
								    UNION 
								   (SELECT prof, cour
									FROM ((SELECT C5.subject AS prof, C5.predicat6 AS cour
										   FROM  class1 AS C5)
										   UNION
										  (SELECT L.subject AS prof, L.object AS cour
										  FROM class5 AS C3, class2 AS C4, class1 AS C5, left_over AS L
										  WHERE ((L.subject=C3.subject OR L.subject=C4.subject OR L.subject=C5.subject) 
										  AND (L.prop = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf')))) AS T3))

									  
SELECT  T1.student, T1.prof, T1.cour
FROM v_student AS T1, v_prof AS T2
WHERE (T1.prof=T2.prof AND T1.cour=T2.cour)	

-- idclass1 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateStudent'
-- idclass2 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#UndergraduateStudent'

-- idclass3 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#FullProfessor'
-- idclass4 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssociateProfessor'
-- idclass5 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#AssistantProfessor'

-- idclass6 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#Course'
-- idclass7 = SELECT id FROM idclass WHERE type = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#GraduateCourse'

-- idpred11 = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#advisor'
-- idpred12 = SELECT id FROM idpredicat WHERE id_class=idclass1 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'

-- idpred21 = SELECT id FROM idpredicat WHERE id_class=idclass2 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#advisor'
-- idpred22 = SELECT id FROM idpredicat WHERE id_class=idclass2 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#takesCourse'

-- idpred31 = SELECT id FROM idpredicat WHERE id_class=idclass3 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf'

-- idpred41 = SELECT id FROM idpredicat WHERE id_class=idclass3 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf'

-- idpred51 = SELECT id FROM idpredicat WHERE id_class=idclass3 AND predicat = 'http://s-lisi-locforge.w.ensma.fr/svn/OntologyMetric#teacherOf'
