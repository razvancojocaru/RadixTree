import java.util.ArrayList;


/**
 * Radix Tree implementation
 * Contains methods for insert and search by prefix
 * The insertRec and findRec methods are used by the other 2 methods.
 * 
 * @author Razvan Cojocaru
 */
public class RadixTree {
	
	public RadixTreeNode root;
	
	public RadixTree() {
        root = new RadixTreeNode();
        root.setKey("");
    }
	
	/**
	 * Method used to insert words in the Radix Tree
	 * @param key word to be inserted in the Radix Tree
	 * @param poz position of the key word in the text
	 */
	public void insert(String key, int poz) {
		insertRec(root, key, poz);
	}
	
	/**
	 * Recursive method used by <code>insert<code>
	 * @param node the RadixTreeNode in which the insertion is made
	 * @param key word to be inserted
	 * @param poz position of the key word in the text
	 */
	private void insertRec(RadixTreeNode node, String key, int poz) {
		
		int nrCommonChars = node.commonLetters(key);
		
		// Proper node has not been yet found
		// or we can insert without splitting an existing key
		if ( ( (nrCommonChars < key.length()) && 
				(nrCommonChars == node.getKey().length()) ) ) {
			
			boolean found = false;
            String newKey = key.substring(nrCommonChars, key.length());
            
            // Search through the current node's children
            for ( int i = 0; i < node.getChildren().size(); i++  ) {
                if (node.getChildren().get(i).getKey().startsWith(
                									newKey.charAt(0) + "")) {
                	
                    found = true;
                    insertRec(node.getChildren().get(i), newKey, poz);
                    break;
                }
            }

            // If no matching children are found, insert key as child of
            // the current node
            if (found == false) {
                RadixTreeNode n = new RadixTreeNode();
                n.setKey(newKey);
                ArrayList<Integer> array = new ArrayList<Integer>();
                array.add(poz);
                n.setPoz(array);
                node.getChildren().add(n);
            }
		}
		
			
		// We have found the proper node for insertion
		// a key split is required
		else if ( nrCommonChars < node.getKey().length() ) {
			
			// After the split, the old node is added as the new node's child 			
			// an auxiliary node is also created
			if ( nrCommonChars < key.length() ) {
				// where the split is done
				// the auxiliary node is created
				RadixTreeNode aux = new RadixTreeNode();
				aux.setKey(node.getKey().substring(
									nrCommonChars, node.getKey().length()));
				aux.setChildren(node.getChildren());
				aux.setPoz(node.getPoz());
				
				node.setChildren(new ArrayList<RadixTreeNode>());
				node.getChildren().add(aux);
				node.setKey(node.getKey().substring(0, nrCommonChars));
				node.setPoz(null);
				
				// the word is added in the Radix Tree
				RadixTreeNode newNode = new RadixTreeNode();
				newNode.setKey(key.substring(nrCommonChars, key.length()));
				ArrayList<Integer> array = new ArrayList<Integer>();
                array.add(poz);
                newNode.setPoz(array);
				node.getChildren().add(newNode);
			}
			
			// no auxiliary node needed, only one new node is created
			else {
				RadixTreeNode n = new RadixTreeNode();
				n.setKey(node.getKey().substring(nrCommonChars, 
						node.getKey().length()));
				n.setChildren(node.getChildren());
				n.setPoz(node.getPoz());
				
				node.setChildren(new ArrayList<RadixTreeNode>());
				node.getChildren().add(n);
				node.setKey(node.getKey().substring(0, nrCommonChars));
				ArrayList<Integer> array = new ArrayList<Integer>();
                array.add(poz);
                node.setPoz(array);
			}
		}	
		
		// if the key already exists in the Radix Tree
		// simply add the new position to the proper node
		else if ( key.equals(node.getKey()) ) {
			if (node.getPoz() == null)
				node.setPoz(new ArrayList<Integer>());
            node.getPoz().add(poz);
        }
		
	}
	
	/**
	 * Recursive method used by <code>findByPrefix<code> to get all the
	 * positions of the words in the subtree of a given node
	 * @param node which subtree positions are needed
	 * @param array in which the positions are stored
	 */
	private void findRec(RadixTreeNode node, ArrayList<Integer> array) {
		if ( node.getPoz() != null ) {
			array.addAll(node.getPoz());
		}
		
		for ( int i = 0; i < node.getChildren().size(); i++ ) {
			findRec(node.getChildren().get(i), array);
		}
	}
	
	/**
	 * Method used to print all the positions of the words in the subtree of
	 * the node containing the <code>key<code> prefix
	 * @param key String representing the given prefix
	 */
	public void findByPrefix(String key) {
		
		String searching = new String();
		RadixTreeNode node = root;
		ArrayList<Integer> positions = new ArrayList<Integer>();
		Boolean find = false;
		
		// Find the node which contains the given prefix
		while ( !searching.equals(key) ) {
			find = false;
			for ( int i=0; i<node.getChildren().size(); i++ ) {			
				if (node.getChildren().get(i).getKey().startsWith(
										key.charAt(searching.length()) + "")) {
					node = node.getChildren().get(i);
					searching = searching.concat(node.getKey());				
					
					find = true;
					break;
				}
			}
			
			// no words with the given prefix exist
			if ( find == false ) break;
			
			// we have to cut the <code>searching<code> String
			if ( searching.length() > key.length() ) 
				searching = searching.substring(0, key.length());
			
			// no words with the given prefix exist
			if (( searching.length() == key.length() ) 
					&& ( !searching.equals(key) )) {
				find = false;
				break;
			}
			
		}

		
		if ( find==true ) {
			// get the positions of the words in the subtree of the node
			// containing the given prefix
			// and print them
			findRec(node, positions);
			System.out.print(positions.size());
			for ( int i=0; i<positions.size(); i++ ) {
				System.out.print(" " + positions.get(i));
			}
			System.out.println("");
		}
		else {
			// if no words exist, print 0 
			System.out.println("0");
		}
	}
}
