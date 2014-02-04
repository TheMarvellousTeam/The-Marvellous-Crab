package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;

import com.apollo.Layer;
import com.apollo.components.spatial.Node;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TeleporterNode extends Node<SpriteBatch>{

	
	public TeleporterNode(){
		super();
		
		
	}
	@Override
	public Layer getLayer() {
		return MyLayer.ITEM;
	}

	@Override
	public void render(SpriteBatch batch) {
		// TODO Auto-generated method stub
		
	}
	
}
