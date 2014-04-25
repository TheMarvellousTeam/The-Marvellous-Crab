package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.component.Physic;
import org.ggj.imposteurs.component.ui.Rotable;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RotableSpatial extends GdxSpatial {

	@InjectComponent
	protected Rotable rotable;

	@InjectComponent
	protected Physic phys;

	private Sprite sprite;

	public RotableSpatial() {
		sprite = new Sprite(new Texture(Gdx.files.internal("data/rotate_helper.png")));
		sprite.setOrigin(55, 45);
	}

	@Override
	public Layer getLayer() {
		return MyLayer.OVER;
	}

	@Override
	public void render(SpriteBatch spriteBatch) {

		sprite.setPosition(phys.getBody().getPosition().x - 55, phys.getBody().getPosition().y - 128 + 45);

		if (rotable.displayWheel()) {
			sprite.draw(spriteBatch);
		}
	}

}
