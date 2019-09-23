package autocomplete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AutocompleteProvider {
	
	// Main data structure to hold word/confidence
	private TreeMap<String, Integer> wordMap;
	
	public AutocompleteProvider() {
		wordMap = new TreeMap<String, Integer>();
	}
	
	/* Returns list of candidates ordered by confidence */
	public List<Candidate> getWords(String fragment) {
		
		List<Candidate> candidates = new ArrayList<>();
		
		// Return empty list if fragment is not only letters
		if (!Pattern.matches("[a-zA-Z]+",fragment))
			return candidates;
		
		// Find submap based between fragment. { is the next ascii value after z to constrain submap to only contain fragment.
		SortedMap<String, Integer> candidateMap = wordMap.subMap(fragment, fragment + "{");
		
		for (String key: candidateMap.keySet())
			candidates.add(new Candidate(key, candidateMap.get(key)));
		
		// Sort by confidence level
		Collections.sort(candidates);
		
		return candidates;
		
	}
    
	/* Trains the algorithm with the provided passage */
	public void train(String passage) {
		
		String cleanedPassage = cleanPassage(passage);
		String [] words = cleanedPassage.split(" ");

		for (String word: words) {
			// Increment confidence for word, or initialize to 1 if does not exist
			if (wordMap.containsKey(word)) {
				wordMap.put(word, wordMap.get(word) + 1);
			}
			else {
				wordMap.put(word, 1);
			}
		}
	}
	
	/* Filter the passage for only letters */
	public String cleanPassage(String passage) {
		passage = passage.replace('\n', ' ');
		
	    char[] chars = passage.toCharArray();
	    String result = "";

	    for (char c : chars) {
	        if(Character.isLetter(c) || c == ' ') {
	        	result += c;
	        }
	    }

	    return result.toLowerCase();
	}
	
	/* Return map containing words */
	public TreeMap<String, Integer> getMap(){
		return wordMap;
	}
	
	/* Return string of candidates */
	public String candidatesToString(List<Candidate> candidates) {
		if (candidates.isEmpty()) {
			return "";
		}	
		
		String result = "";
		
		for (int i = 0; i < candidates.size() - 1; i++) 
			result += candidates.get(i) + ", ";
		
		result += candidates.get(candidates.size() - 1);
		
		return result;
	}
	
	/* Print candidates to system */
	public void printWords(List<Candidate> candidates) {
		if (candidates.isEmpty()) {
			return;
		}
		
		
		for (int i = 0; i < candidates.size() - 1; i++) 
			System.out.print(candidates.get(i) + ", ");
		
		System.out.println(candidates.get(candidates.size() - 1));
		
	}
}
