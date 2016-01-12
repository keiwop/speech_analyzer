package com.github.keiwop.speechanalyzer;

import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Random;


/**
 * Created by keiwop on 11/01/16.
 */
public class MainText extends Observable{
	private String first_match = "";
	private String other_matches = "";
	private String last_error = "";
	private int error_count = 0;
	private String command = "";
	private String answer = "";
	String D_LOG = "SpeechAnalyzer";
	Analyzer analyzer;


	public MainText(String current_language){
		this.analyzer = new Analyzer(current_language);
	}

	public String getFirstMatch(){
		return first_match;
	}

	public void setFirstMatch(String first_match){
//		synchronized(this){
		setAnswer("");
		setCommand("");
		this.first_match = first_match;
//		}
		setChanged();
		notifyObservers();
		analyzeMatch(first_match);
	}

	public String getOtherMatches(){
		return other_matches;
	}

	public void setOtherMatches(String other_matches){
		this.other_matches = other_matches;
		setChanged();
		notifyObservers();
	}

	public String getLastError(){
		return last_error;
	}

	public void setLastError(String last_error){
		this.last_error = last_error;
		setChanged();
		notifyObservers();
	}

	public int getErrorCount(){
		return error_count;
	}

	public void setErrorCount(int error_count){
		this.error_count = error_count;
		setChanged();
		notifyObservers();
	}

	public String getCommand(){
		return command;
	}

	public void setCommand(String command){
		this.command = command;
		setChanged();
		notifyObservers();
	}

	public String getAnswer(){
		return answer;
	}

	public void setAnswer(String answer){
		this.answer = answer;
		setChanged();
		notifyObservers();
	}

	public void analyzeMatch(String match){
		match += ".";
		for(Map.Entry<String, List<String>> entry : analyzer.getKeywordsMap().entrySet()){
			System.out.println("Answer: " + entry.getKey() + "\nKeywords: " + entry.getValue());
			if(matchContains(match, entry.getValue())){
				List<String> answersList = analyzer.getAnswersMap().get(entry.getKey());
				setAnswer(answersList.get(new Random().nextInt(answersList.size())));
				setCommand(analyzer.getCommandsMap().get(entry.getKey()));
			}
		}
	}

	public boolean matchContains(String match, List<String> matchesList){
		Log.d(D_LOG, "matchesList: " + matchesList.toString());
		for(String str : matchesList){
			System.out.println("Match: " + match + "\t\tKeyword: " + str);
			if(match.toLowerCase().contains(str)){
				return true;
			}
		}
		return false;
	}

	public void clearAll(){
		setFirstMatch("");
		setOtherMatches("");
		setLastError("");
		setAnswer("");
		setCommand("");
		setErrorCount(0);
	}
}
