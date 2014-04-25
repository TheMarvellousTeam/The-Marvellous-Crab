package org.ggj.imposteurs.component.ray;

import org.ggj.imposteurs.component.Physic;

import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MirrorHandler extends RayHandler {
	
	@InjectComponent
	private Physic phys;
	
	@Override
	public void handleRay(Vector2 point, Vector2 v, Vector2 n, Color color, int iter) {
		
		// detect if the point is the large or the small side of the rectangle
		// compute the inv of the transform matrix
		Matrix3 tr_ = new Matrix3().setToTranslation( phys.getBody().getTransform().getPosition() ).rotate( phys.getBody().getTransform().getRotation() * MathUtils.radiansToDegrees ).inv();
		
		Vector2 v_ = point.cpy().mul(tr_);
		
		//v_ = point.cpy().sub( phys.getBody().getTransform().getPosition() ).mul( new Matrix3().rotate( phys.getBody().getTransform().getRotation() ).inv() );
		
		if( (Math.abs( Math.abs(v_.x)-32 ) < 0.001) )
			// is the small side
			return;
		
		if (v.len() > 0.0000001) {
			float scalProduct = v.dot(n);
			Vector2 vprim = new Vector2(v).add(new Vector2(n).scl(scalProduct * -2));
			Vector2 next = new Vector2(point).add(vprim.scl(1000));
			castRay(point, next, color, iter + 1);
		} else {
			System.out.println("bug");
		}
	}

}
