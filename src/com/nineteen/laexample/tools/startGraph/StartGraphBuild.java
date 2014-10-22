package com.nineteen.laexample.tools.startGraph;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.parboiled.scala.Input;

import sqlStatments.StringAnalysis;

import com.nineteen.laexample.tools.graphdb.GraphDatabase;
import com.nineteen.laexample.tools.graphdb.RelTypes;
import com.nineteen.laexample.tools.logging.ToolLogger;
import com.nineteen.laexample.tools.nodes.Flight;
import com.nineteen.laexample.tools.nodes.Person;
import com.nineteen.laexample.tools.utility.Configuration;

public class StartGraphBuild {

	/**
	 * The Migration Logger.
	 */
	private final Logger theLogger;

	// private static GraphDatabase theGraphDatabase;

	private final static String DB_PATH;

	private final static String CSV_PATH;

	private final static String CSV_SPLITTER;

	private final static String JSON_PATH;

	private static StringAnalysis stringAnalysis;

	private static Scanner input;

	private static GraphDatabaseService graphDb;

	// private static FileDirectory theFileDirectory;

	/**
	 * Static Initialisation for Configuration Variables
	 */
	static {
		DB_PATH = Configuration.getString("neo4j.graph.db.path");
		CSV_PATH = Configuration.getString("csv.data.input");
		CSV_SPLITTER = Configuration.getString("csv.data.splitter");
		JSON_PATH = Configuration.getString("json.data.input");
	}

	/**
	 * Instantiate a Start Graph Build
	 */
	public StartGraphBuild() {

		// Instantiate the Logger
		theLogger = ToolLogger.getLogger();
		stringAnalysis = new StringAnalysis();

		// theGraphDatabase = new GraphDatabase();
		// theGraphManager = new GraphManager();
		// theFileDirectory = new FileDirectory();

	}

	public static void main(String[] args) {
		System.out.println("---- SQL4Neo - Created by LAExample ---- \n");

		graphDb = logIntoGraph();

		// Input scanner
		Scanner input = new Scanner(System.in);

		Boolean wouldUserLikeToQuery = true;

		while (wouldUserLikeToQuery) {
			wouldUserLikeToQuery = queryGraph(input, graphDb);
		}
		
		if (!wouldUserLikeToQuery){
			// Shutdown database
			GraphDatabase.shutdownGraphDatabase(graphDb);
		}

		System.out.println("\n---- SQL4Neo - Created by LAExample ----");

	}

	private static GraphDatabaseService logIntoGraph() {
		// Boolean whether the database name is correct
		Boolean validInput = false;

		// New graph database service
		GraphDatabaseService graphDb = null;

		// Scanner
		input = new Scanner(System.in);

		while (!validInput) {

			System.out.println("Select your database");
			String databaseName = input.nextLine();

			if (!databaseName.isEmpty()) {

				try {
					graphDb = GraphDatabase.logIntoDb("D:\\Users\\lukganno\\Documents\\Neo4j\\" + databaseName + ".graphdb");
					
					if (graphDb != null) {
						validInput = true;
						System.out.println("Logged into " + databaseName);
					}

				} catch (Exception e) {
					e.printStackTrace();
					logIntoGraph();
				}

			} else {
				validInput = false;
			}

		}

		return graphDb;

	}

	private static Boolean queryGraph(Scanner input, GraphDatabaseService graphDb) {
		System.out.println("Would you like to query the Graph (Y/N) : ");

		String answer = input.nextLine();
		
		Boolean keepQuerying = true;

		switch (answer) {
		case "Y":
			System.out.print(">");
			// System.out.println("1. SELECT id, attribute FROM nodeType WHERE attribute = value\n");
			// System.out.println("2. SELECT * FROM nodeType WHERE attribute = value\n");
			String queryType = input.nextLine();
			StringAnalysis.checkString(queryType, graphDb);
			break;
		case "N":
			keepQuerying = false;
			break;
		default:
			queryGraph(input, graphDb);
			break;
		}

		return keepQuerying;
	}

	private static Boolean queryGraphDONTEDIT(Scanner input, GraphDatabaseService graphDb) {
		System.out.println("Would you like to query the Graph (Y/N) : ");

		String answer = input.nextLine();
		String nodeType;
		String nodeAttrType;
		String nodeAttr;
		switch (answer) {
		case "Y":
			System.out.println("What type of query?: \n");
			System.out.println("1. SELECT id, attribute FROM nodeType WHERE attribute = value\n");
			System.out.println("2. SELECT * FROM nodeType WHERE attribute = value\n");
			String queryType = input.nextLine();
			switch (queryType) {
			case "1":
				System.out.println("Please enter the Node type you're looking for : ");
				System.out.println("FROM CLAUSE ");
				nodeType = input.nextLine().trim();
				System.out.println("Please enter the attribute this node must have : ");
				System.out.println("WHERE CLAUSE");
				nodeAttrType = input.nextLine().trim();
				System.out.println("Please enter the value for this attribute : ");
				System.out.println("WHERE EQUAL");
				nodeAttr = input.nextLine().trim();
				GraphDatabase.findNodes(graphDb, nodeType, nodeAttrType, nodeAttr);
				queryGraph(input, graphDb);
				break;

			case "2":
				System.out.println("Please enter the Node type you're looking for : ");
				System.out.println("FROM CLAUSE ");
				nodeType = input.nextLine().trim();
				System.out.println("Please enter the attribute this node must have : ");
				System.out.println("WHERE CLAUSE");
				nodeAttrType = input.nextLine().trim();
				System.out.println("Please enter the value for this attribute : ");
				System.out.println("WHERE EQUAL");
				nodeAttr = input.nextLine().trim();
				GraphDatabase.selectStarFromNodesWhere(graphDb, nodeType, nodeAttrType, nodeAttr);
				queryGraph(input, graphDb);
				break;
			default:
				queryGraph(input, graphDb);
				break;
			}

		case "N":
			break;
		default:
			queryGraph(input, graphDb);
			break;
		}

		return false;
	}

	private static Map<String, String> getDetails(Flight flight) {
		Map<String, String> flightAttr = new HashMap<String, String>();
		flightAttr.put("flightNumber", Integer.toString(flight.getFlightNumber()));
		flightAttr.put("carrierName", flight.getCarrierName());
		flightAttr.put("departureAirport", flight.getDeparture());
		flightAttr.put("arrivalAirport", flight.getArrival());
		flightAttr.put("departureDateTime", Integer.toString(flight.getDepartureDateTime()));
		flightAttr.put("arrivalDateTime", Integer.toString(flight.getArrivalDateTime()));

		return flightAttr;

	}

	private static Map<String, String> getDetails(Person person) {
		Map<String, String> personAttr = new HashMap<String, String>();
		personAttr.put("passportNumber", Integer.toString(person.getIdentifier()));
		personAttr.put("firstName", person.getFirstName());
		personAttr.put("lastName", person.getLastName());
		personAttr.put("dateOfBirth", person.getDateOfBirth().toString());
		personAttr.put("criminalRecord", person.getCriminalRecord());

		return personAttr;

	}

	private static Map<String, String> createAMapForNoAttributes(String key, String value) {
		Map<String, String> aMap = new HashMap<String, String>();
		aMap.put(key, value);
		return aMap;
	}

}
