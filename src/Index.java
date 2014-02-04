/**
 * This execution entry point class handles the parsing, indexing and lookup of words in a text.
 */
public class Index {

	/**
	 * Execution entry point.
	 *
	 * @param args two strings representing the name of the file that contains the text to index,
	 * and the name of the file containing words to lookup in the text.
	 */
	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: java -cp <classpath> Index <text file> <word file>");
			System.exit(1);
		}


		String word;
		RadixTree radix = new RadixTree();
		int poz = 0;

		// inserts words in radix tree
		FileParser textFile = new FileParser(args[0]);
		textFile.open();
		while ((word = textFile.getNextWord()) != null) {
			radix.insert(word, poz);
			poz++;
		}
		textFile.close();

		// searches words in the radix tree by prefix
		FileParser prefixFile = new FileParser(args[1]);
		prefixFile.open();
		while ((word = prefixFile.getNextWord()) != null) {
			radix.findByPrefix(word);
			
		}
		prefixFile.close();
	}
}
