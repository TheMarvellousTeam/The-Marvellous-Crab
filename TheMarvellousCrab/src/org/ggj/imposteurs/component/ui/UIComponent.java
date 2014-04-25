package org.ggj.imposteurs.component.ui;

import org.ggj.imposteurs.component.Physic;

import com.apollo.Component;
import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

public class UIComponent extends Component {
	
	@InjectComponent
	protected Physic phys;
	
	public Vector2 pointerOnWorld(){
		return new Vector2( Gdx.input.getX() , Gdx.graphics.getHeight() - Gdx.input.getY() );
	}
	
	public Boolean isPointerOnMe(){
		
		Vector2 pointer = pointerOnWorld();
		
		// a body is composed of a list of fixture ( ~= shape )
		// iterate thought this list and test collision with the shapes
		Array<Fixture> fixture = phys.getBody().getFixtureList();
		
		for( Fixture f : fixture ){
			if( f.testPoint( pointer ) ){
				return true;
			}
		}
		
		return false;
	}
}
