package org.ggj.imposteurs.component.ray;

import org.ggj.imposteurs.component.Physic;
import org.ggj.imposteurs.utils.ColorMapper;

import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;

public class PrismHandler extends RayHandler {

	@InjectComponent
	protected Physic phys;

	@Override
	public void handleRay(Vector2 point, Vector2 v, Vector2 n, Color color, int iter) {

		phys.getBody().getTransform().getPosition();

		Transform tr = phys.getBody().getTransform();
		// P = new Matrix3().setToRotation( 0 ).translate(
		// phys.getBody().getPosition());

		// oh yeah
		PolygonShape polygon = (PolygonShape) phys.getBody().getFixtureList().first().getShape();

		Vector2[] vertex = new Vector2[] { new Vector2(), new Vector2(), new Vector2() };

		// retreive the vertex
		polygon.getVertex(0, vertex[0]);
		polygon.getVertex(1, vertex[1]);
		polygon.getVertex(2, vertex[2]);

		// proj them
		for (Vector2 ve : vertex)
			tr.mul(ve);

		Matrix3[] Ps = new Matrix3[3];
		Matrix3[] Ps_ = new Matrix3[3];

		for (int i = 0; i < 3; i++) {
			Ps[i] = passage(vertex[i], vertex[(i + 1) % 3]);
			Ps_[i] = new Matrix3(Ps[i]).inv();
		}

		float h = 0;
		int i = 0;
		Vector2 n_ = new Vector2();
		for (; i < 3; i++) {
			Vector2 H = point.cpy().mul(Ps_[i]);
			if (Math.abs(H.y) < 0.00001) {
				h = H.x;

				n_.x = -v.x * Ps_[i].getValues()[Matrix3.M00] - v.y * Ps_[i].getValues()[Matrix3.M10];
				n_.y = -v.x * Ps_[i].getValues()[Matrix3.M01] - v.y * Ps_[i].getValues()[Matrix3.M11];

				break;
			}
		}

		n_.nor();

		Color[] colors = ColorMapper.prisme(color);
		for (int j = 1; j <= 2; j++) {
			int i_ = (i + j) % 3;
			Vector2 H = new Vector2(h, 0).mul(Ps[i_]);

			n_.x = 0;
			n_.y = 1;

			Vector2 vprim = new Vector2(
					n_.x * Ps[i_].getValues()[Matrix3.M00] + n_.y * Ps[i_].getValues()[Matrix3.M10], n_.x
							* Ps[i_].getValues()[Matrix3.M01] + n_.y * Ps[i_].getValues()[Matrix3.M11]);
			Vector2 vprim2 = vprim.cpy().nor();

			castRay(H.cpy(), H.cpy().add(vprim2.cpy().scl(2000)), colors[j - 1], iter + 1);
		}

	}

	private Matrix3 passage(Vector2 A, Vector2 B) {

		Vector2 u = new Vector2(B).sub(A);
		Vector2 v = new Vector2(u.y, -u.x);

		Matrix3 P = new Matrix3();
		P.val = new float[] { u.x, u.y, 0, v.x, v.y, 0, A.x, A.y, 1 };

		return P;
	}

}
