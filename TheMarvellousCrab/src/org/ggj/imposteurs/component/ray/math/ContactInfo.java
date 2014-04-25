package org.ggj.imposteurs.component.ray.math;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class ContactInfo {
	public float t;
	public Vector2 point;
	public Vector2 normal;
	public Body body;
	
	public ContactInfo(){
		
	}
}
