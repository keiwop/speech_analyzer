package com.github.keiwop.speechanalyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by keiwop on 12/01/16.
 */
public class Analyzer{
	private Map<String, List<String>> keywords_map = new HashMap<>();
	private Map<String, List<String>> answers_map = new HashMap<>();
	private Map<String, String> commands_map = new HashMap<>();

	public Map<String, List<String>> getKeywordsMap(){
		return keywords_map;
	}

	public Map<String, List<String>> getAnswersMap(){
		return answers_map;
	}

	public Map<String, String> getCommandsMap(){
		return commands_map;
	}

	public Analyzer(String current_language){
		if(current_language.contains("en")){
			setLanguageEN();
		}
		else if(current_language.contains("fr")){
			setLanguageFR();
		}
	}

	public void setLanguageEN(){
		keywords_map.put("hello", Arrays.asList("hello", "hi", "howdy"));
		answers_map.put("hello", Arrays.asList("Hello", "Hi", "Howdy", "Yo! What's up?"));

		keywords_map.put("sandwich", Arrays.asList("make me a sandwich"));
		answers_map.put("sandwich", Arrays.asList("Make it yourself"));

		keywords_map.put("sudo_sandwich", Arrays.asList("sudo make me a sandwich"));
		answers_map.put("sudo_sandwich", Arrays.asList("Okay"));

		keywords_map.put("light_on", Arrays.asList("light on", "lights on", "on the lights", "on the light.", "light it up"));
		commands_map.put("light_on", "light:all:on");
		answers_map.put("light_on", Arrays.asList("Turning on the lights", "Right away!", "It's too bright for me now"));

		keywords_map.put("light_1_on", Arrays.asList("on the light 1", "on the light one", "on the first light", "the first light on"));
		commands_map.put("light_1_on", "light:1:on");
		answers_map.put("light_1_on", Arrays.asList("Turning on the light", "Right away!", "It's still a bit dark, no?"));

		keywords_map.put("light_off", Arrays.asList("light of", "lights of", "of the light.", "off the light.", "of the lights", "off the lights", "shut the light"));
		commands_map.put("light_off", "light:all:off");
		answers_map.put("light_off", Arrays.asList("Turning off the lights", "Right away!", "It's dark now"));

		keywords_map.put("light_1_off", Arrays.asList("of the light 1", "of the light one", "off the light one", "of the first light", "off the first light", "the first light of"));
		commands_map.put("light_1_off", "light:1:off");
		answers_map.put("light_1_off", Arrays.asList("Turning off the light", "Right away!", "Why not"));

		keywords_map.put("play_music", Arrays.asList("play music", "some music.", "on the music", "music on", "start the music", "launch music"));
		commands_map.put("play_music", "music:on");
		answers_map.put("play_music", Arrays.asList("Playing some music", "Launching the music player", "Okay"));

		keywords_map.put("pause_music", Arrays.asList("pause music", "pause the music", "freeze", "pause"));
		commands_map.put("pause_music", "music:pause");
		answers_map.put("pause_music", Arrays.asList("Pausing the music", "Okay"));

		keywords_map.put("stop_music", Arrays.asList("stop music", "stop the music", "music stop", "off the music", "of the music", "music of"));
		commands_map.put("stop_music", "music:off");
		answers_map.put("stop_music", Arrays.asList("Stopping the music", "Quitting the music player", "Okay"));

		keywords_map.put("next_music", Arrays.asList("next music", "next song", "song after", "next"));
		commands_map.put("next_music", "music:next");
		answers_map.put("next_music", Arrays.asList("Playing the next music", "Okay"));

		keywords_map.put("prev_music", Arrays.asList("previous music", "previous song", "song before", "prev", "produce"));
		commands_map.put("prev_music", "music:prev");
		answers_map.put("prev_music", Arrays.asList("Playing the previous music", "Okay"));

		keywords_map.put("volume_up", Arrays.asList("volume up", "more volume", "loud"));
		commands_map.put("volume_up", "vol:up");
		answers_map.put("volume_up", Arrays.asList("Augmenting volume", "Okay"));

		keywords_map.put("volume_down", Arrays.asList("volume down", "less volume", "quiet"));
		commands_map.put("volume_down", "vol:down");
		answers_map.put("volume_down", Arrays.asList("Reducing volume", "Okay"));

		keywords_map.put("volume_mute", Arrays.asList("mute", "shut up", "shut the fuck up"));
		commands_map.put("volume_mute", "vol:mute");
		answers_map.put("volume_mute", Arrays.asList("Reducing volume to 0", "Volume is muted", "Okay"));

		keywords_map.put("brightness_up", Arrays.asList("brightness up", "more brightness", "bright"));
		commands_map.put("brightness_up", "bl:up");
		answers_map.put("brightness_up", Arrays.asList("Augmenting brightness", "Okay"));

		keywords_map.put("brightness_down", Arrays.asList("brightness down", "less brightness", "dark", "fade"));
		commands_map.put("brightness_down", "bl:down");
		answers_map.put("brightness_down", Arrays.asList("Reducing brightness", "Okay"));
	}

	public void setLanguageFR(){
		keywords_map.put("light_on", Arrays.asList("allume:lumière"));
		commands_map.put("light_on", "light:all:on");
		answers_map.put("light_on", Arrays.asList("Les lumières sont allumées", "C'est fait !", "C'est trop lumineux pour moi"));
	}
}
