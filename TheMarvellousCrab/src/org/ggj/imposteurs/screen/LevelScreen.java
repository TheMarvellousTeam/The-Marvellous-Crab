package org.ggj.imposteurs.screen;

import org.ggj.imposteurs.builder.LevelStore;
import org.ggj.imposteurs.manager.GdxRenderManager;
import org.ggj.imposteurs.manager.LevelManager;
import org.ggj.imposteurs.manager.PhysicManager;
import org.ggj.imposteurs.spatial.BubbleSpatial;

import com.apollo.Entity;
import com.apollo.World;
import com.apollo.managers.GroupManager;
import com.apollo.managers.TagManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class LevelScreen implements Screen {

	private String level;
	private Game game;

	private World world;
	private GdxRenderManager renderManager;
	private PhysicManager physManager;
	private TagManager tagManager;
	private GroupManager groupManager;

	private SpriteBatch batch;

	public LevelScreen(Game aGame, String levelName) {
		super();
		level = levelName;
		game = aGame;
	}

	@Override
	public void show() {
		batch = new SpriteBatch();

		world = new World();
		world.setManager(renderManager = new GdxRenderManager(batch));
		world.setManager(physManager = new PhysicManager());
		world.setManager(tagManager = new TagManager());
		world.setManager(groupManager = new GroupManager());
		world.setManager(new LevelManager(game, Gdx.audio.newMusic(Gdx.files.internal("music/loading.mp3"))));

		LevelStore.get().startLevel(world, level);
		
		Timer timer = new Timer();
		timer.scheduleTask(new Task() {
			
			@Override
			public void run() {
				Entity bubble = new Entity(world);
				int x = (int)(Math.random()*Gdx.graphics.getWidth());
				bubble.setComponent(new BubbleSpatial(x,100));
				bubble.addToWorld();
			}
		}, 2,3);
		timer.start();
	}

	@Override
	public void render(float delta) {
		world.update((int) (delta * 1000));
		renderManager.render(batch);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		batch.dispose();
	}

}
