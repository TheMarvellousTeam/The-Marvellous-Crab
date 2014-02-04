package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;

import com.apollo.Layer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BubbleSpatial extends GdxSpatial{

	private  int iter;
	private boolean positive;
	private float  deltax,x,y;
	private float rot;
	private Sprite sprite;
	public BubbleSpatial(int x, int y){
		this.x = x;
		this.y = y;
		rot = 0;
		sprite = new Sprite(new Texture(Gdx.files.internal("data/bubble.png")));
	}
	
	@Override
	public Layer getLayer() {
		return MyLayer.BUBBLES;
	}
	
	public void update(int delta){
		super.update(delta);
		this.y++;
		
		if(iter > 50){
			positive  = Math.random()>0.5?true:false;
			iter = 0;
		}
		if(positive)
			this.x += 0.1;
		else
			this.x -= 0.1;
		iter++;
		
		if(y > Gdx.graphics.getHeight()+32){
			owner.getWorld().deleteEntity(owner);
		}
		rot += 0.3;
		sprite.setRotation(rot);
	}

	@Override
	public void render(SpriteBatch batch) {
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

}
