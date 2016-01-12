package com.github.keiwop.speechanalyzer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by keiwop on 11/01/16.
 */

public class SpeechAnalyzer{

	String D_LOG = "SpeechAnalyzer";
	private Context context;
	private SpeechRecognizer speech_recognizer;
	private Intent speech_recognizer_intent;
	private MainText main_text;
	private String current_language = "";

	public void setCurrentLanguage(String current_language){
		this.current_language = current_language;
	}

	public SpeechAnalyzer(Context context, MainText main_text, String current_language){
		this.context = context;
		this.main_text = main_text;
		this.current_language = current_language;
//		initSpeechRecognizer();
	}

	public void initSpeechRecognizer(){
		speech_recognizer = SpeechRecognizer.createSpeechRecognizer(this.context);
		speech_recognizer_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		speech_recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		speech_recognizer_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());
		speech_recognizer_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, current_language);

		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

		SpeechRecognitionListener listener = new SpeechRecognitionListener();
		speech_recognizer.setRecognitionListener(listener);
	}

	public void startListening(){
		initSpeechRecognizer();
		speech_recognizer.startListening(speech_recognizer_intent);
	}

	public void restartListening(){
		stopListening();
		startListening();
	}

	public void stopListening(){
		if(speech_recognizer != null){
			speech_recognizer.cancel();
			speech_recognizer.destroy();
		}
	}

	class SpeechRecognitionListener implements RecognitionListener{

		@Override
		public void onBeginningOfSpeech(){
			Log.d(D_LOG, "onBeginningOfSpeech");
		}

		@Override
		public void onBufferReceived(byte[] buffer){
			Log.d(D_LOG, "onBufferReceived");
		}

		@Override
		public void onEndOfSpeech(){
			Log.d(D_LOG, "onEndOfSpeech");
		}

		@Override
		public void onError(int error){
			Log.d(D_LOG, "onError: " + error);
			String str_error = "";
			int error_count = main_text.getErrorCount();
			error_count++;
			switch(error){
				case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:

					str_error = "network timeout error";
					restartListening();
					break;
				case SpeechRecognizer.ERROR_NETWORK:
					str_error = "network error";
					restartListening();
					break;
				case SpeechRecognizer.ERROR_AUDIO:
					str_error = "audio error";
					break;
				case SpeechRecognizer.ERROR_SERVER:
					str_error = "server error";
					break;
				case SpeechRecognizer.ERROR_CLIENT:
					str_error = "client error";
					break;
				case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
					str_error = "speech timeout error";
					restartListening();
					break;
				case SpeechRecognizer.ERROR_NO_MATCH:
					str_error = "no match error";
					restartListening();
					break;
				case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
					str_error = "recognizer busy error";
					restartListening();
					break;
				case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
					str_error = "insufficient permissions error";
					break;
				default:
					str_error = "unknown error";
					break;
			}
			main_text.setLastError(str_error);
			main_text.setErrorCount(error_count);
		}

		@Override
		public void onEvent(int eventType, Bundle params){
			Log.d(D_LOG, "onEvent");
		}

		@Override
		public void onPartialResults(Bundle partialResults){
			Log.d(D_LOG, "onPartialResults");
		}

		@Override
		public void onReadyForSpeech(Bundle params){
			Log.d(D_LOG, "onReadyForSpeech");
		}

		@Override
		public void onResults(Bundle results){
			Log.d(D_LOG, "onResults");
			ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
			Log.d(D_LOG, "matches: " + matches);
			main_text.setOtherMatches("");
			if(matches != null){
				for(int i = 0; i < matches.size(); ++i){
					String str = matches.get(i);
					if(i == 0){
						main_text.setFirstMatch(str);
					}
					else{
						String other_matches = main_text.getOtherMatches();
						other_matches += str;
						if(i < matches.size() - 1){
							other_matches += "\n";
						}
						main_text.setOtherMatches(other_matches);
					}
				}
			}
			restartListening();
		}

		@Override
		public void onRmsChanged(float rmsdB){
//			Log.d(D_LOG, "onRmsChanged");
		}
	}
}