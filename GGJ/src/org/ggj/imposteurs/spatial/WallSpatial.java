package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;

import com.apollo.Layer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class WallSpatial extends GdxSpatial{
	
	private ShapeRenderer renderer;
	private Vector2[] vectrices;
	public WallSpatial(Vector2[] vectrices){
		renderer = new ShapeRenderer();
		this.vectrices = vectrices;
	}
	@Override
	public Layer getLayer() {
		return MyLayer.ITEM;
	}

	@Override
	public void render(SpriteBatch batch) {
		renderer.begin(ShapeType.Line);
		renderer.setColor(Color.BLACK);
		float offx = 100;
		float offy = 100;
		renderer.polygon(getVectricesAsFloatArray(100, 100));
		renderer.end();
	}
	
	public Vector2[] getVectrices(){
		return vectrices;
	}
	
	public float[] getVectricesAsFloatArray(int offsetx, int offsety){
		float[] tab = new float[vectrices.length*2];
		for(int i =0; i < vectrices.length; i=i+1){
			tab[i*2] = vectrices[i].x+offsetx;
			tab[i*2+1] = vectrices[i].y+offsety;
		}
		return tab;
	}
	
	public float[] getVectricesAsFloatArray(){
		return getVectricesAsFloatArray(0,0);
	}

}
