package org.ggj.imposteurs.screen;

import java.util.List;

import org.ggj.imposteurs.builder.LevelStore;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class MenuScreen implements Screen {

	private Stage stage;
	private Skin skin;
	private Game game;

	private class Background extends Actor {
		private TextureRegion texture;

		public Background(Texture background) {
			super();
			texture = new TextureRegion(background, 0, 0, 480, 800);
		}

		@Override
		public void draw(SpriteBatch batch, float parentAlpha) {
			batch.draw(texture, 0, 0);
		}
	}

	public MenuScreen(Game theGame) {
		game = theGame;
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		stage.addActor(new Background(new Texture(Gdx.files.internal("background/menu.png"))));

		// A skin can be loaded via JSON or defined programmatically, either is
		// fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region,
		// etc as a drawable, tinted drawable, etc.
		skin = new Skin();

		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are
		// stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.CLEAR);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		List<String> levels = LevelStore.get().getAllLevelNames();
		float currentX = width / 4;
		float currentY = 8 * height / 10;
		for (int i = 0; i < levels.size(); i++) {
			final String name = levels.get(i);
			TextButton button = new TextButton(name, skin);
			button.setPosition(currentX - button.getWidth() / 2, currentY - button.getHeight() / 2);
			currentX += width / 4;
			if (currentX > 3 * width / 4) {
				currentX = width / 4;
				currentY -= height / 10;
			}
			// button.addListener(new ChangeListener() {
			// @Override
			// public void changed(ChangeEvent event, Actor actor) {
			// game.setScreen(new LevelScreen(game, name));
			// }
			// });
			button.addListener(new InputListener() {
				@Override
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					game.setScreen(new LevelScreen(game, name));
					return false;
				}
			});
			stage.addActor(button);
		}

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		stage.draw();
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
		stage.dispose();
		skin.dispose();
	}

}
