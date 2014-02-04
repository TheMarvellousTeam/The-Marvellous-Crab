package org.ggj.imposteurs.builder.data;

import com.badlogic.gdx.math.Vector2;

public class BlockDefinition {
	public float[] vectrices;
	public static Vector2[] asVector2Array(float[] v){
			Vector2[] tab = new Vector2[v.length/2];
			for(int i=0; i < tab.length; i++){
				tab[i] = new Vector2(v[i*2], v[i*2+1]);
			}
			return tab;
		}
	
	public String name;
	public String file;
	public int offx;
	public int offy;
}
