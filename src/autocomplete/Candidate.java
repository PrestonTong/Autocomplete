package autocomplete;

public class Candidate implements Comparable<Candidate>{
	
	private String word;
	private Integer confidence;
	
	public Candidate() {
		
	}
	
	public Candidate(String word, Integer confidence) {
		this.word = word;
		this.confidence = confidence;
	}
	
	/* Returns the autocomplete candidate */
	public String getWord() {
		return word;
	}
	
	/* Returns the confidence for the candidate */
    public Integer getConfidence() {
    	return confidence;
    }

    /* Compare candidates based on confidence */
	@Override
	public int compareTo(Candidate c) {
		
		return c.confidence - confidence;
	}
	
	/* Return string of word and confidence */
	@Override
	public String toString() {
		return "\"" + word + "\" (" + confidence + ")";
	}
}
