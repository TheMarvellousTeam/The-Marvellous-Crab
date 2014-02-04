package org.ggj.imposteurs.component.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Rotable extends UIComponent {

	// some flags
	private boolean lookAtMeIAmDragged = false;
	private boolean pointerDown = false;

	private boolean displayWheel = false;

	private Vector2 a = new Vector2();
	private float initialAngle;

	private float pas = 4;

	public Rotable(float pas) {
		this.pas = pas;
	}

	@Override
	public void update(int arg0) {
		super.update(arg0);

		if (!pointerDown && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			// pointer has just been pressed
			pointerDown = true;

			// am i pressed?
			if ((lookAtMeIAmDragged = isPointerOnMe())) {
				// if i am, hold the actual mouse position, relatively to my
				// center
				a = pointerOnWorld().sub(phys.getBody().getPosition()).nor();

				// and my current rotation
				initialAngle = phys.getBody().getTransform().getRotation();
			}
		}

		// button released
		if (!Gdx.input.isButtonPressed(Buttons.LEFT)) {
			pointerDown = false;

			if (lookAtMeIAmDragged) {
				lookAtMeIAmDragged = false;

				// round the angle
				Float angle = computeAngle();
				if (angle != null) {
					if (pas != 0)
						angle = (float) (Math.round(angle / (Math.PI / pas)) * (Math.PI / pas));
					phys.getBody().setTransform(phys.getBody().getPosition(), angle + initialAngle);
				}
			}
		}

		// dragged
		if (lookAtMeIAmDragged) {
			// apply the rotation
			Float angle = computeAngle();
			if (angle != null)
				phys.getBody().setTransform(phys.getBody().getPosition(), computeAngle() + initialAngle);
		}

	}

	public boolean displayWheel() {
		return lookAtMeIAmDragged;
	}

	private Float computeAngle() {

		Vector2 b = new Vector2();

		b.x = Gdx.input.getX() - phys.getBody().getPosition().x;
		b.y = Gdx.graphics.getHeight() - Gdx.input.getY() - phys.getBody().getPosition().y;

		if (b.len() < 30)
			return null;

		b.nor();

		float scalar = MathUtils.clamp(b.dot(a), -1, 1);

		return (float) (Math.acos(scalar) * (a.crs(b) < 0 ? -1.0 : 1.0));
	}

}
