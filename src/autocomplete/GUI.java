package autocomplete;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements DocumentListener {
	private AutocompleteProvider acp;
	
	private JFrame frame;
	private JPanel trainPanel;
	private JTextArea trainArea;
	private JScrollPane trainScroll;
	private JButton trainButton;
	private JPanel inputPanel;
	private JTextArea inputArea;
	private JScrollPane inputScroll;
	private JPanel wordSuggestionsPanel;
	private JTextArea wordSuggestionsArea;
	private JScrollPane wordSuggestionsScroll;
	
	public GUI() {
		
		// Initalize autocompleteprovider
		acp = new AutocompleteProvider();
		
		// Overall Frame
		frame = new JFrame("Autocomplete");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 960);
        
        // Training Panel and Contents
        trainPanel = new JPanel ();
        trainPanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Train" ) );
        trainPanel.setBounds(30, 30, 580, 400);
        
        trainArea = new JTextArea(20, 48);  
        trainArea.setLineWrap(true);
        trainArea.setWrapStyleWord(true);
        
        trainScroll = new JScrollPane (trainArea);
        trainScroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        trainButton = new JButton("Enter");
        trainButton.addActionListener(new ActionListener()
        {
          // Train input and clear train area
          public void actionPerformed(ActionEvent e) {
  	    	acp.train(trainArea.getText());
  	    	trainArea.setText("");
          }
        });
        
        trainPanel.add(trainScroll);
        trainPanel.add(trainButton);
        
        // Input Panel and Contents
        inputPanel = new JPanel ();
        inputPanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Input" ) );
        inputPanel.setBounds(30, 450, 580, 200);
        
        inputArea = new JTextArea(10, 48);
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        inputArea.getDocument().addDocumentListener(this);
        
        inputScroll = new JScrollPane (inputArea);
        inputScroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        inputPanel.add(inputScroll);
        
        // Word Suggestion Panel and Contents
    	wordSuggestionsPanel = new JPanel ();
    	wordSuggestionsPanel.setBorder ( new TitledBorder ( new EtchedBorder (), "Word Suggestions" ) );
    	wordSuggestionsPanel.setBounds(30, 660, 580, 200);
        
    	wordSuggestionsArea = new JTextArea(10, 48);
    	wordSuggestionsArea.setEditable(false);
    	wordSuggestionsArea.setLineWrap(true);
    	wordSuggestionsArea.setWrapStyleWord(true);
    	wordSuggestionsArea.getDocument().addDocumentListener(this);
    	
        wordSuggestionsScroll = new JScrollPane (wordSuggestionsArea);
        wordSuggestionsScroll.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        wordSuggestionsPanel.add(wordSuggestionsScroll);
        
        // Add All Panels to Frame
        frame.add(trainPanel);
        frame.add(inputPanel);
        frame.add(wordSuggestionsPanel);
        frame.setLayout(null);
        frame.setVisible(true);
        
	}
	
	@Override
	public void changedUpdate(DocumentEvent arg0) {
		
	}

	/* Update potential candidates in textarea */
	@Override
	public void insertUpdate(DocumentEvent de) {
        if (de.getLength() != 1) {
            return;
        }
        
        int pos = de.getOffset();
        String fragment = null;
        try {
        	fragment = inputArea.getText(0, pos + 1);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        
        // Find where the word starts
        int i;
        for (i = pos; i >= 0; i--) {
            if (! Character.isLetter(fragment.charAt(i))) {
                break;
            }
        }

        String prefix = fragment.substring(i + 1).toLowerCase();

        wordSuggestionsArea.setText(acp.candidatesToString(acp.getWords(prefix)));
        
	}

	@Override
	public void removeUpdate(DocumentEvent de) {

	} 
}
