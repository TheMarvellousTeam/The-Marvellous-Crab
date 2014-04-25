package org.ggj.imposteurs.component.ray;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class TeleportHandler extends RayHandler{
	
	private Vector2 p1;
	private Vector2 p2;
	
	public TeleportHandler(Vector2 aP1, Vector2 aP2){
		p1 = aP1;
		p2 = aP2;
	}
	@Override
	public void handleRay(Vector2 point, Vector2 v, Vector2 n, Color color, int iter) {
		Vector2 source = null;
		Vector2 newSource;
		if(p1.dst(point) <= 20){//valeur arbitraire
			source = p1;
			newSource=p2;
		}else{
			source = p2;
			newSource = p1;
		}
		System.out.println("source "+source);
			Vector2 r = new Vector2(v).scl(1000);
			castRay(newSource, r, color, ++iter);
	}

}
