package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.component.Colored;
import org.ggj.imposteurs.component.Physic;
import org.ggj.imposteurs.component.ui.Rotable;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.apollo.components.spatial.Spatial;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class IrisSpatial extends Spatial<SpriteBatch> {
	
	
	
	@InjectComponent
	protected Colored colored;
	
	@InjectComponent
	protected Physic phys;
	
	private Sprite sprite;
	
	public IrisSpatial(){
		sprite = new Sprite( new Sprite(new Texture(Gdx.files.internal("data/white_ball.png"))) );
		sprite.setOrigin(8+8,8);
	}
	
	@Override
	public Layer getLayer() {
		return MyLayer.OVER;
	}

	@Override
	public void render(SpriteBatch spriteBatch) {
		
		sprite.setColor( colored.getColor() );
		sprite.setPosition( phys.getBody().getPosition().x -16 , phys.getBody().getPosition().y -8 );
		sprite.setRotation( phys.getBody().getTransform().getRotation() * MathUtils.radiansToDegrees + 180 );
		
		sprite.draw(spriteBatch);
	}

}
