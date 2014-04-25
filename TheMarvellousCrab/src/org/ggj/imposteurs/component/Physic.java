package org.ggj.imposteurs.component;

import com.apollo.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class Physic extends Component {

	private Body body;
	public int offsetX=0;
	public int offsetY=0;
	public Physic(Body aBody) {
		super();
		body = aBody;
	}

	public Body getBody() {
		return body;
	}
}
