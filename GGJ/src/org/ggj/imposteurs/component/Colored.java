package org.ggj.imposteurs.component;

import com.apollo.Component;
import com.badlogic.gdx.graphics.Color;

public class Colored extends Component {
	private Color color;

	public Colored(Color aColor) {
		super();
		color = aColor;
	}

	public Color getColor() {
		return color;
	}

}
