package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.component.Colored;
import org.ggj.imposteurs.component.Physic;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class PalourdeSpatial extends GdxSpatial {

	@InjectComponent
	private Physic phys;

	@InjectComponent
	private Colored colored;

	private Texture oyster;
	private Sprite pearl;

	public PalourdeSpatial(Texture anOyster, Texture aPearl) {
		super();
		oyster = anOyster;
		pearl = new Sprite(aPearl);
	}

	@Override
	public Layer getLayer() {
		return MyLayer.ITEM;
	}

	@Override
	public void render(SpriteBatch batch) {
		Body body = phys.getBody();
		batch.draw(oyster, body.getPosition().x - oyster.getWidth() / 2 + 20, body.getPosition().y - oyster.getHeight()
				/ 2 - 25);
		pearl.setColor(colored.getColor());
		pearl.setPosition(phys.offsetX + body.getPosition().x - pearl.getWidth() / 2, phys.offsetY
				+ body.getPosition().y - pearl.getHeight() / 2);
		pearl.draw(batch);
	}
}
