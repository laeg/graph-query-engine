graph-query-engine
=========================

A little project I've started to be able to take a SQL statement and execute it on a Neo4J graph data set

So far, the following type of queries have been implemented: 

--------------------------------------------------------------------

SELECT 	* 
FROM 	nodeLabel 
WHERE 	specifiedAttribute = specifiedValue

Input: Node Label, Attribute to find, Attribute Value to find

--------------------------------------------------------------------
SELECT 	id, 
		specifiedAttribute 
FROM 	nodeLabel 
WHERE 	specifiedAttribute = specifiedValue

Input: Node Label, Attribute to find, Attribute Value to find

--------------------------------------------------------------------