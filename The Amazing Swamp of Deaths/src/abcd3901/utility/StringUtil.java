package abcd3901.utility;

/**
 * Class useful to manipulate <code>Strings</code>
 * 
 * @author Arnaud Paré-Vogt
 *
 */
public class StringUtil {

	public static int countOccurences(String haystack, char needle) {
		int count = 0;
		for (int i = 0; i < haystack.length(); i++) {
			if (needle == haystack.charAt(i))
				count++;
		}
		return count;
	}

}
