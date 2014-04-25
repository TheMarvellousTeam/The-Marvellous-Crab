package org.ggj.imposteurs.component;

import com.apollo.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class Physic2 extends Component{
	private Body a;
	private Body b;
	
	public Physic2(Body aA, Body aB){
		super();
		a = aA;
		b = aB;
	}
	public Body getBodyA(){
		return a;
	}
	public Body getBodyB(){
		return b;
	}
}
