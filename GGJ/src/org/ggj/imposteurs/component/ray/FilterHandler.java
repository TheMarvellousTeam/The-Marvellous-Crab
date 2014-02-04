package org.ggj.imposteurs.component.ray;

import org.ggj.imposteurs.component.Colored;
import org.ggj.imposteurs.utils.ColorMapper;

import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class FilterHandler extends RayHandler {

	@InjectComponent
	private Colored colored;

	@Override
	public void handleRay(Vector2 point, Vector2 v, Vector2 n, Color color, int iter) {
		Vector2 next = v.cpy().scl(1000);
		castRay(point, next, ColorMapper.filter(color, colored.getColor()), iter + 1);
	}

}
