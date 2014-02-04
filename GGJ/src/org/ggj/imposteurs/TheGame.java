package org.ggj.imposteurs;

import org.ggj.imposteurs.builder.LevelStore;
import org.ggj.imposteurs.screen.LevelScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class TheGame extends Game {

	@Override
	public void create() {
		LevelStore.get().loadLevels("json/game.json");
		LevelStore.get().loadBlockDefinitions("json/blocks.json");

		// setScreen(new MenuScreen(this));
		setScreen(new LevelScreen(this, "Level 1"));

		Music soundtrack = Gdx.audio.newMusic(Gdx.files.internal("music/soundtrack.mp3"));
		Music ambiance = Gdx.audio.newMusic(Gdx.files.internal("music/ambiance.mp3"));
		ambiance.setLooping(true);
		soundtrack.setLooping(true);
		ambiance.setVolume(0.1f);
		soundtrack.setVolume(0.2f);
		ambiance.play();
		soundtrack.play();
	}

}
