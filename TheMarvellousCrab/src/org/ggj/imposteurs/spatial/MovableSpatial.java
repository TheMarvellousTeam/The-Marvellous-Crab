package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.component.Physic;
import org.ggj.imposteurs.component.ui.Movable;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovableSpatial extends GdxSpatial {
	@InjectComponent
	protected Movable rotable;

	@InjectComponent
	protected Physic phys;

	private Sprite sprite;

	public MovableSpatial() {
		sprite = new Sprite(new Texture(Gdx.files.internal("data/move_helper.png")));
	}

	@Override
	public Layer getLayer() {
		return MyLayer.OVER;
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		float width = rotable.getBounds().width;
		float height = rotable.getBounds().height;
		if (width == 0)
			width = 10;
		if (height == 0)
			height = 10;
		sprite.setBounds(rotable.getBounds().x, rotable.getBounds().y, width, height);

		if (rotable.displayHelper()) {
			sprite.draw(spriteBatch);
		}
	}
}
