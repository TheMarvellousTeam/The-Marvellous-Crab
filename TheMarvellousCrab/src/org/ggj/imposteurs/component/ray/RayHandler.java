package org.ggj.imposteurs.component.ray;

import org.ggj.imposteurs.component.ray.RayPath.RaySegment;
import org.ggj.imposteurs.component.ray.math.Collider;
import org.ggj.imposteurs.component.ray.math.ContactInfo;
import org.ggj.imposteurs.manager.PhysicManager;

import com.apollo.Component;
import com.apollo.Entity;
import com.apollo.annotate.InjectManager;
import com.apollo.annotate.InjectTaggedEntity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

public abstract class RayHandler extends Component {

	@InjectTaggedEntity("RAYS")
	protected Entity rays;

	@InjectManager
	protected PhysicManager physManager;

	@Override
	public Class<? extends Component> getType() {
		return RayHandler.class;
	}

	public abstract void handleRay(Vector2 point, Vector2 v, Vector2 n, Color color, int iter);
	
	public void castRay(final Vector2 start, Vector2 end, final Color color, int iter )  {
		
		if( iter > 50 )
			return;
		
		Array<Body> bodies = new Array<Body>();
		physManager.getPhysicWorld().getBodies(bodies);
		
		Vector2 v = end.sub( start ).nor();
		
		Collider collider = new Collider();
		
		ContactInfo ci = null;
		
		try {
			ci = collider.collideScene( bodies , v , start );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if( ci == null )
			return;
		
		rays.getComponent(RayPath.class).getList().add(new RaySegment(start, ci.point, color));
		
		RayHandler receptor = ((Entity) ci.body.getUserData()).getComponent(RayHandler.class);
		
		receptor.handleRay(new Vector2(ci.point), new Vector2(ci.point).sub(start) , new Vector2(ci.normal),
				color , iter+1 );
	}
	
	/*
	public void castRay(final Vector2 start, Vector2 end, final Color color,final int iter) {
		if(iter > 50){
			System.out.println("LOLOL");
			return;
		}
		physManager.getPhysicWorld().rayCast(new RayCastCallback() {

			@Override
			public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
				rays.getComponent(RayPath.class).getList().add(new RaySegment(start, point, color));
				if (!"BOUND".equals(fixture.getBody().getUserData())) {
					RayHandler receptor = ((Entity) fixture.getBody().getUserData()).getComponent(RayHandler.class);
					receptor.handleRay(new Vector2(point), new Vector2(point).sub(start) , new Vector2(normal),
							color, iter+1);
				}
				return 0;
			}

		}, start, end);
	}
	*/

}
