package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;

import com.apollo.Layer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BackgroundSpatial extends GdxSpatial {

	private TextureRegion background;
	private Animation crab;
	private float timestate = 0;

	public BackgroundSpatial(Texture aBackground, Texture aCrab) {
		super();
		background = new TextureRegion(aBackground, 0, 0, 480, 800);
		TextureRegion[][] regions = TextureRegion.split(aCrab, 358, 210);
		TextureRegion[] animFrames = new TextureRegion[41];
		int k = 0;
		for (int i = 0; i < regions.length && k < 41; i++) {
			for (int j = 0; j < regions[i].length && k < 41; j++) {
				animFrames[k++] = regions[i][j];
			}
		}
		crab = new Animation(35f, animFrames);
		crab.setPlayMode(Animation.LOOP);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		timestate += delta;
	}

	@Override
	public Layer getLayer() {
		return MyLayer.BACKGROUND;
	}

	@Override
	public void render(SpriteBatch batch) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batch.draw(background, 0, 0);
		batch.draw(crab.getKeyFrame(timestate), 50, 0);
	}

}
