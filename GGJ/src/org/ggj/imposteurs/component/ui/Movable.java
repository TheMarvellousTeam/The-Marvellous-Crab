package org.ggj.imposteurs.component.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Movable extends UIComponent {

	// some flags
	private Boolean lookAtMeIAmDragged = false;
	private Boolean pointerDown = false;

	private Rectangle boundary;

	public Movable(Rectangle boundary) {
		this.boundary = boundary;
	}

	@Override
	public void update(int arg0) {
		super.update(arg0);

		if (!pointerDown && Gdx.input.isButtonPressed(Buttons.LEFT)) {
			// pointer has just been pressed
			pointerDown = true;

			// am i pressed?
			if ((lookAtMeIAmDragged = isPointerOnMe())) {

			}
		}

		// button released
		if (!Gdx.input.isButtonPressed(Buttons.LEFT)) {
			pointerDown = false;

			if (lookAtMeIAmDragged) {
				lookAtMeIAmDragged = false;

				// round the position
				Vector2 pos = computePosition();
				pos.x = (float) (Math.round(pos.x / 10)) * 10;

				phys.getBody().setTransform(pos, phys.getBody().getTransform().getRotation());
			}
		}

		// dragged
		if (lookAtMeIAmDragged) {
			// apply the translation
			phys.getBody().setTransform(computePosition(), phys.getBody().getTransform().getRotation());
		}

	}

	private Vector2 computePosition() {

		Vector2 pos = pointerOnWorld();

		pos.x = MathUtils.clamp(pos.x, boundary.getX(), boundary.getX() + boundary.getWidth());
		pos.y = MathUtils.clamp(pos.y, boundary.getY(), boundary.getY() + boundary.getHeight());

		return pos;
	}

	public boolean displayHelper() {
		return lookAtMeIAmDragged;
	}

	public Rectangle getBounds() {
		return boundary;
	}

}
