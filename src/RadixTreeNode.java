import java.util.ArrayList;


/**
 * Node used by the Radix Tree
 * Contains a String which stores the inserted text, and 2
 * ArrayLists - one with the children nodes and another one with the positions
 * of the stored word in the input text.
 * 
 * @author Razvan Cojocaru
 */
public class RadixTreeNode {
	
	private String key;
	private ArrayList<RadixTreeNode> children;
	private ArrayList<Integer> poz;
	
	public RadixTreeNode() {
		children = new ArrayList<RadixTreeNode>();
	}
	
	
	/**
	 * Getter/Setter methods for the 3 private members
	 */
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public ArrayList<Integer> getPoz() {
		return poz;
	}
	public void setPoz(ArrayList<Integer> poz) {
		this.poz = poz;
	}
	
	public ArrayList<RadixTreeNode> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<RadixTreeNode> children) {
		this.children = children;
	}
	
	/**
	 * @param str
	 * @return the number of common characters between the key String of the
	 * current node and the str given as parameter
	 */
	public int commonLetters(String str) {
		int nr = 0;
		while ( ( nr < this.key.length() ) && ( nr < str.length() ) ) {
			if ( this.key.charAt(nr) == str.charAt(nr) )
				nr++;
			else
				break;
		}
		return nr;
	}

}
