package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;

import com.apollo.Layer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class CongratulationSpatial extends GdxSpatial {

	private Sprite sprite;

	public CongratulationSpatial(Texture aTexture) {
		super();
		sprite = new Sprite(aTexture);
		sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2, 5 * Gdx.graphics.getHeight() / 6
				- sprite.getHeight() / 2);
	}

	@Override
	public Layer getLayer() {
		return MyLayer.OVER;
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
