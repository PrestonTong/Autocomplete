## Asymmetrik Coding Submission

Challenge: Mobile Device Keyboard

Date: Fall 2019

Language: Java

IDE: eclipse

## Main.java

Initialize GUI and runs program.

## GUI.java

**Train** - enter sentences to be trained against the autocompleteprovider

**Input** - start typing like you would for any normal keyboard!

**Suggestions** - this textarea will be auto populated with suggestions based on what is typed in the input textarea



## AutocompleteProvider.java

Utilizes a treemap to store words and their corresponding confidence values.

Lookup takes submaps of fragmented inputs

## Candidate.java

Object class for trained words/confidence levels
