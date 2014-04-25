package org.ggj.imposteurs.component.ray.math;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Segment;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;


public class Collider {
	
	public ContactInfo collideSegment( Vector2 A , Vector2 B , Vector2 v , Vector2 o ) throws Exception{
		
		Boolean debug = false;
		
		// project one point of the line on AB ( orthogonally )
		
		// director vector of AB
		Vector2 AB = B.cpy().sub(A);
		Vector2 sv = AB.cpy().nor();
		
		Vector2 Ao = o.cpy().sub(A);
		
		float scal = sv.dot( Ao );
		
		// projection of o on AB
		Vector2 H = sv.cpy().scl( scal ).add( A );
		
		if( debug && Math.abs(AB.crs(H.cpy().sub(A)) ) > 1 )
			throw new Exception("the point H is not on the segment line ! "+(AB.crs(H.cpy().sub(A)) ));
		
		Vector2 Ho = H.cpy().sub(o);
		
		if( debug && Math.abs(AB.dot( Ho ) ) > 1 )
			throw new Exception("Ho not orthogonal to AB ! "+(AB.dot( Ho ) ));
			
		float l_Ho = Ho.len();
		
		if( l_Ho < 0.000001 ){
			ContactInfo c = new ContactInfo();
			c.t = 0;
			c.point = H;
			c.normal = sv.set( sv.y , -sv.x );
			return c;
		}
			
		
		Ho.scl( 1/l_Ho );
		
		float comp_ortho_v = Ho.dot( v );
		
		if( Math.abs(comp_ortho_v) < 0.0000001 )
			return null;
		
		float t = l_Ho / comp_ortho_v;
		
		Vector2 point = v.cpy().scl(t).add(o);
		
		if( debug && Math.abs(AB.crs(point.cpy().sub(A)) ) > 1 )
			throw new Exception("the point computed is not on the segment line ! " + (AB.crs(point.cpy().sub(A)))  );
		
		scal = AB.dot(  point.cpy().sub(A) );
		if(  scal < 0 || scal > AB.dot(AB)  )
			return null;
			
		
		ContactInfo c = new ContactInfo();
		c.t = t;
		c.point = point;
		c.normal = sv.set( sv.y , -sv.x );
		return c;
	}
	
	public ContactInfo collidePolygon( Vector2[] poly, Vector2 v , Vector2 o ) throws Exception{
		
		ContactInfo best = null;
		
		for(int i=0;i<poly.length;i++){
			ContactInfo tmp = collideSegment( poly[i], poly[ (i+1)%poly.length ] , v , o );
			if( tmp == null || tmp.t < 0.000001 )
				continue;
			if( best == null || tmp.t < best.t ){
				if( best != null && Math.abs( best.t - tmp.t ) < 0.00001 )
					// damned the ray is right on an edge
					// we can't say which edge it collide exactly, so let's say it doesn't collide
					best = null;
				else
					best = tmp;
			}
		}
		
		return best;
	}
	
	public ContactInfo collideScene( Array<Body> bodies , Vector2 v , Vector2 o ) throws Exception{
		
		ContactInfo best = null;
		
		for( Body body : bodies ){
			
			Shape shape_ = body.getFixtureList().first().getShape();
			
			if( !(shape_ instanceof PolygonShape) )
				continue;
			
			if( body.getFixtureList().first().testPoint(o) )
				continue;
			
			PolygonShape shape = (PolygonShape)shape_;
			
			Vector2[] poly = new Vector2[ shape.getVertexCount() ];
			
			for( int i=0;i<poly.length;i++){
				poly[i] = new Vector2();
				shape.getVertex(i, poly[i]);
				poly[i] = body.getTransform().mul( poly[i] );
			}
			
			
			ContactInfo tmp = collidePolygon( poly , v , o );
			if( tmp == null || tmp.t < 0.00001  )
				continue;
			if( best == null || tmp.t < best.t ){
				best = tmp;
				best.body = body;
			}
		}
		
		return best;
	}
	
}
