package org.ggj.imposteurs;

import com.apollo.Layer;

public enum MyLayer implements Layer {
	BACKGROUND, BUBBLES, CHARACTERS, RAYS, ITEM, OVER;

	@Override
	public int getLayerId() {
		return ordinal();
	}

}
