package org.ggj.imposteurs.component.ray;

import java.util.LinkedList;
import java.util.List;

import com.apollo.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class RayPath extends Component {

	private List<RaySegment> list;

	public RayPath() {
		list = new LinkedList<RayPath.RaySegment>();
	}

	public List<RaySegment> getList() {
		return list;
	}

	public static class RaySegment {
		public Color color;
		public Vector2 p1, p2;

		public RaySegment(Vector2 start, Vector2 point, Color aColor) {
			p1 = new Vector2(start);
			p2 = new Vector2(point);
			color = aColor;
		}

		@Override
		public String toString() {
			return p1 + " - " + p2;
		}
	}

}
