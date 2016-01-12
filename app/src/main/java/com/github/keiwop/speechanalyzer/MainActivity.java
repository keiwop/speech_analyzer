package com.github.keiwop.speechanalyzer;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer{

	String D_LOG = "SpeechAnalyzer";
	String PERMISSION_AUDIO = android.Manifest.permission.RECORD_AUDIO;
	String[] PERMISSIONS_AUDIO = {PERMISSION_AUDIO};
	SpeechAnalyzer speech_analyzer;
	FloatingActionButton fab;
	TextView text_view_first_match;
	TextView text_view_other_matches;
	TextView text_view_last_error;
	TextView text_view_error_count;
	TextView text_view_command;
	TextView text_view_answer;
	private MainText main_text;
	boolean is_running = false;
	private String current_language = "en-US";


	@Override
	public void update(Observable o, Object arg){
		String first_match = ((MainText) o).getFirstMatch();
		String other_matches = ((MainText) o).getOtherMatches();
		String last_error = ((MainText) o).getLastError();
		int error_count = ((MainText) o).getErrorCount();
		String command = ((MainText) o).getCommand();
		String answer = ((MainText) o).getAnswer();

		Log.d(D_LOG, "first_match: " + first_match);
		Log.d(D_LOG, "other_matches: " + other_matches);
		Log.d(D_LOG, "last_error: " + last_error);
		Log.d(D_LOG, "error_count: " + error_count);
		Log.d(D_LOG, "command: " + command);
		Log.d(D_LOG, "answer: " + answer);

		text_view_first_match.setText(first_match);
		text_view_other_matches.setText(other_matches);
		text_view_last_error.setText(last_error);
		text_view_error_count.setText(String.valueOf(error_count));
		text_view_command.setText(command);
		text_view_answer.setText(answer);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				if(!is_running){
					startAnalyzer();
				}
				else{
					stopAnalyzer();
				}
			}
		});

		text_view_first_match = (TextView) findViewById(R.id.text_view_first_match);
		text_view_other_matches = (TextView) findViewById(R.id.text_view_other_matches);
		text_view_last_error = (TextView) findViewById(R.id.text_view_last_error);
		text_view_error_count = (TextView) findViewById(R.id.text_view_error_count);
		text_view_command = (TextView) findViewById(R.id.text_view_command);
		text_view_answer = (TextView) findViewById(R.id.text_view_answer);

		main_text = new MainText(current_language);
		main_text.addObserver(this);
		speech_analyzer = new SpeechAnalyzer(this, main_text, current_language);
	}


	public void startAnalyzer(){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
			if(checkSelfPermission(PERMISSION_AUDIO) == PackageManager.PERMISSION_DENIED){
				Log.d(D_LOG, "Asking permission for " + PERMISSION_AUDIO);
				requestPermissions(PERMISSIONS_AUDIO, 1);
			}
		}
		fab.setImageResource(R.drawable.ic_stop_white);
		speech_analyzer.startListening();
		is_running = true;
	}

	public void stopAnalyzer(){
		speech_analyzer.stopListening();
		main_text.clearAll();
		fab.setImageResource(R.drawable.ic_start_white);
		is_running = false;
	}

	public void restartAnalyzer(){
		stopAnalyzer();
		main_text = new MainText(current_language);
		main_text.addObserver(this);
		speech_analyzer.setCurrentLanguage(current_language);
		startAnalyzer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();

		if(id == R.id.action_settings){
			return true;
		}
		else if(id == R.id.lang_en){
			current_language = "en-US";
			restartAnalyzer();
		}
		else if(id == R.id.lang_fr){
			current_language = "fr-FR";
			restartAnalyzer();
		}


		return super.onOptionsItemSelected(item);
	}
}
