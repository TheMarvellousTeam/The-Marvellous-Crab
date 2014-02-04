package org.ggj.imposteurs.component.ray;

import org.ggj.imposteurs.component.Colored;

import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class ReceptorHandler extends RayHandler {

	private Color current;

	@InjectComponent
	private Colored expected;

	@Override
	public void handleRay(Vector2 point, Vector2 v, Vector2 n, Color color,int iter) {
		current = color;
	}

	public boolean isSuccessfull() {
		return current == expected.getColor();
	}

	public Color getCurrent() {
		return current;
	}

	public void resetCurrent() {
		current = null;
	}

}
