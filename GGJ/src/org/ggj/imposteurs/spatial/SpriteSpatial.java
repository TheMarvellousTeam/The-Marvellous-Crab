package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.component.Physic;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

public class SpriteSpatial extends GdxSpatial {

	@InjectComponent
	private Physic phys;

	protected Sprite sprite;

	public SpriteSpatial(Texture aTexture) {
		super();
		sprite = new Sprite(aTexture);
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
	}

	@Override
	public Layer getLayer() {
		return MyLayer.ITEM;
	}

	@Override
	public void render(SpriteBatch batch) {
		Body body = phys.getBody();
		sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
		sprite.setPosition(phys.offsetX+body.getPosition().x - sprite.getWidth() / 2, phys.offsetY+body.getPosition().y - sprite.getHeight() / 2);
		sprite.draw(batch);
	}
}
