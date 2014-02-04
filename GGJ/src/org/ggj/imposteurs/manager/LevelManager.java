package org.ggj.imposteurs.manager;

import org.ggj.imposteurs.MyGroup;
import org.ggj.imposteurs.builder.LevelStore;
import org.ggj.imposteurs.component.Expiration;
import org.ggj.imposteurs.component.Expiration.Callback;
import org.ggj.imposteurs.component.ray.RayHandler;
import org.ggj.imposteurs.component.ray.ReceptorHandler;
import org.ggj.imposteurs.screen.LevelScreen;
import org.ggj.imposteurs.screen.MenuScreen;
import org.ggj.imposteurs.spatial.CongratulationSpatial;

import com.apollo.Entity;
import com.apollo.annotate.InjectManager;
import com.apollo.managers.GroupManager;
import com.apollo.managers.Manager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;

public class LevelManager extends Manager {

	@InjectManager
	private GroupManager groupManager;

	@InjectManager
	private PhysicManager physicManager;

	private Game game;

	private Music loading;
	private int goodFor;

	public LevelManager(Game aGame, Music loadingSound) {
		super();
		game = aGame;
		goodFor = 0;
		loading = loadingSound;
		loading.setVolume(0.1f);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		boolean done = true;
		int i = 0;
		for (Entity e : groupManager.getEntityGroup(MyGroup.RECEPTOR)) {
			ReceptorHandler handler = (ReceptorHandler) e.getComponent(RayHandler.class);
			done = done && handler.isSuccessfull();
			handler.resetCurrent();
		}
		if (done) {
			goodFor += delta;
			if (!loading.isPlaying())
				loading.play();
		} else {
			goodFor = 0;
			loading.stop();
		}
		if (goodFor > 3000) {
			loading.stop();
			loadLevel();
		}
	}

	public void loadLevel() {
		Entity delay = new Entity(world);
		delay.setComponent(new Expiration(1000, new Callback() {
			@Override
			public void onExpire() {
				String nextLevel = LevelStore.get().getNextLevelName();
				System.out.println("NEXT" +nextLevel);
				if(nextLevel == null){
					System.out.println("JEU TERMINE");
					game.setScreen(new MenuScreen(game));
				}else
					game.setScreen(new LevelScreen(game, nextLevel));
			}
		}));
		delay.setComponent(new CongratulationSpatial(new Texture(Gdx.files.internal("data/grats.png"))));
		delay.addToWorld();
	}
}
