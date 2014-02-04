package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.component.Colored;

import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ColoredSpriteSpatial extends SpriteSpatial {

	@InjectComponent
	private Colored color;

	public ColoredSpriteSpatial(Texture aTexture) {
		super(aTexture);
	}

	public ColoredSpriteSpatial(Texture aTexture, float scale) {
		super(aTexture);
		sprite.scale(scale);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.setColor(color.getColor());
		super.render(batch);
	}
}
