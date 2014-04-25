package org.ggj.imposteurs.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Color;

public class ColorMapper {
	private static Map<String, Color> map;
	static {
		map = new HashMap<String, Color>();
		map.put("red", Color.RED);
		map.put("green", Color.GREEN);
		map.put("blue", Color.BLUE);
		map.put("yellow", Color.YELLOW);
		map.put("magenta", Color.MAGENTA);
		map.put("orange", Color.ORANGE);
	}

	public static Color getColor(String color) {
		Color c = map.get(color.toLowerCase());
		if (c != null) {
			return c;
		}
		return null;
	}
	
	public static String getStringColor(Color c){
		for(String s : map.keySet()){
			if(map.get(s).equals(c)){
				return s;
			}
		}
		return null;
	}

	public static Color filter(Color filtredColor, Color filterColor) {
		if (filtredColor == filterColor)
			return filtredColor;

		if (filtredColor == Color.RED) {
			if (filterColor == Color.BLUE)
				return Color.MAGENTA;
			else if (filterColor == Color.YELLOW)
				return Color.ORANGE;

		} else if (filtredColor == Color.BLUE) {
			if (filterColor == Color.YELLOW)
				return Color.GREEN;
			else if (filterColor == Color.RED)
				return Color.MAGENTA;
		} else if (filtredColor == Color.YELLOW) {
			if (filterColor == Color.BLUE)
				return Color.GREEN;
			else if (filterColor == Color.RED)
				return Color.ORANGE;
		}
		return filtredColor;
	}

	public static Color[] prisme(Color color) {
		Color[] colors;
		if (color == Color.MAGENTA) {
			colors = new Color[] { Color.RED, Color.BLUE };
		} else if (color == Color.ORANGE) {
			colors = new Color[] { Color.YELLOW, Color.RED };
		} else if (color == Color.GREEN) {
			colors = new Color[] { Color.BLUE, Color.YELLOW };
		} else {
			colors = new Color[] { color, color };
		}
		return colors;
	}
}
