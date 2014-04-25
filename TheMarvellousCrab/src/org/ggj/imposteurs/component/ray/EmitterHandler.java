package org.ggj.imposteurs.component.ray;

import org.ggj.imposteurs.component.Colored;
import org.ggj.imposteurs.component.Physic;

import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class EmitterHandler extends RayHandler {
	@InjectComponent
	private Colored color;

	@InjectComponent
	private Physic phys;

	@Override
	public void handleRay(Vector2 point, Vector2 v, Vector2 n, Color color, int iter) {

	}

	@Override
	public void update(int delta) {
		super.update(delta);
		Vector2 v = new Vector2(phys.getBody().getPosition());
		v.x += MathUtils.cos(phys.getBody().getAngle());
		v.y += MathUtils.sin(phys.getBody().getAngle());
		castRay(phys.getBody().getPosition().cpy() , v, color.getColor(),0);
	}
}
