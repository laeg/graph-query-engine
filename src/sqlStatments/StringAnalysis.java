package sqlStatments;

import org.neo4j.graphdb.GraphDatabaseService;

import com.nineteen.laexample.tools.graphdb.GraphDatabase;

public class StringAnalysis {

	public StringAnalysis() {

	}

	public static void checkString(String inputString, GraphDatabaseService graphDb) {
		String[] tokens = inputString.split(" ");

		// System.out.println(tokens[0]);
		// Debugging purposes
		int counter = 0;
		for (String token : tokens) {
			System.out.println(counter + " - " + token);
			counter++;
		}

		// SELECT * 1 WHERE clause
		if (tokens.length == 8) {
			// Check if its a select query
			if (tokens[0].toUpperCase().equalsIgnoreCase("SELECT")) {
				selectStarWhere(tokens, graphDb);
			}
		} // end if == 8

	}

	public static void selectStarWhere(String[] tokens, GraphDatabaseService graphDb) {
		// Check if its a select query
		if (tokens[0].toUpperCase().equalsIgnoreCase("SELECT")) {
			// Check its a select all
			if (tokens[1].equalsIgnoreCase("*")) {
				// has a from condition
				if (tokens[2].equalsIgnoreCase("FROM")) {
					// the node type is not empty
					if (!tokens[3].isEmpty()) {
						// there is a where condition
						if (tokens[4].equalsIgnoreCase("WHERE")) {
							// the attribute type is not empty
							if (!tokens[5].isEmpty()) {
								// there is a equal condition
								if (tokens[6].equalsIgnoreCase("=")) {
									// the attribute value is not empty
									if (!tokens[7].isEmpty()) {
										GraphDatabase.selectStarFromNodesWhere(graphDb, tokens[3].toString(), tokens[5].toString(),
												tokens[7].toString());
									} // end if attribute value
								} // end if =
							} // end if attribute type
						} // end if where
					} // end if node label
				} // end if from
			} // end if *

		} // end if select
	}

	public static void checkString1(String inputString) {

		String[] tokens = inputString.split(" ");

		System.out.println(tokens[0]);

		// Check if its a select query
		if (tokens[0].equals("SELECT")) {

			int counter = 0;
			for (String token : tokens) {
				System.out.println(counter + " - " + token);
				counter++;
			}

			System.out.println("---------------");
			System.out.println("SELECT statment");
			System.out.println("---------------");

			// Check token two is a * and does not contain any attributes
			if (tokens[1].endsWith("*")) {
				System.out.println("SELECT ALL");
				System.out.println("---------------");

			} else if (tokens[1].contains("*,")) {
				System.out.println("SELECT ALL WITH ATTRIBUTES");
				System.out.println("---------------");
			}
			/**
			 * else if (tokens[1].equals("*") && !tokens[2].endsWith(",")) { int
			 * counter = 0; for (String token : tokens) {
			 * System.out.println(counter + " - " + token); counter++; } }
			 */

			// for(String token : tokens){
			// System.out.println(token);
			// }
		}

	}
}
