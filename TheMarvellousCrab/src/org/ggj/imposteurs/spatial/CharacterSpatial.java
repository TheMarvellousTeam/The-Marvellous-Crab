package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;

import com.apollo.Layer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CharacterSpatial extends GdxSpatial {
	
	public CharacterSpatial( Texture texture , int tileSize ){
		super();
		TextureRegion[][] tmp = TextureRegion.split(texture,tileSize,tileSize);
	}
	
	@Override
	public Layer getLayer() {
		return MyLayer.CHARACTERS;
	}

	@Override
	public void render(SpriteBatch arg0) {
		// TODO Auto-generated method stub

	}

}
