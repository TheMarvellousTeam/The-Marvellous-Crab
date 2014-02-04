package org.ggj.imposteurs.manager;

import org.ggj.imposteurs.component.Physic;
import org.ggj.imposteurs.component.Physic2;

import com.apollo.Entity;
import com.apollo.managers.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class PhysicManager extends Manager {
	private World physWorld;

	public PhysicManager() {
		super();
		physWorld = new World(new Vector2(0, 0), false);
		physWorld.setContinuousPhysics(true);
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		createEdge(BodyType.StaticBody, 0, 0, 0, h, 0).setUserData("BOUND");
		createEdge(BodyType.StaticBody, 0, h, w, h, 0).setUserData("BOUND");
		createEdge(BodyType.StaticBody, w, h, w, 0, 0).setUserData("BOUND");
		createEdge(BodyType.StaticBody, w, 0, 0, 0, 0).setUserData("BOUND");
	}

	public World getPhysicWorld() {
		return physWorld;
	}

	@Override
	public void added(Entity e) {
		super.added(e);
		Physic phys = e.getComponent(Physic.class);
		if (phys != null) {
			phys.getBody().setUserData(e);
			phys.getBody().setSleepingAllowed(false);
			phys.getBody().setAwake(true);
			phys.getBody().setActive(true);
		}
		Physic2 p2 = e.getComponent(Physic2.class);
		if(p2 != null){
			p2.getBodyA().setUserData(e);
			p2.getBodyB().setUserData(e);
		}
	}

	@Override
	public void removed(Entity e) {
		super.removed(e);
		Physic phys = e.getComponent(Physic.class);
		if (phys != null) {
			physWorld.destroyBody(phys.getBody());
		}
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		Array<Body> bodies = new Array<Body>();
		physWorld.getBodies(bodies);
		
		for( Body body : bodies){
			body.setActive(true);
			body.setAwake(true);
		}
		physWorld.step(delta, 10, 10);
		
		
	}

	public Body createBox(BodyType type, float x, float y, float width, float height, float angle, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		def.position.set(x, y);
		def.angle = angle;
		def.active = true;
		def.allowSleep = false;
		Body body = physWorld.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height);
		body.createFixture(shape, density);
		shape.dispose();

		return body;
	}

	public Body createCircle(BodyType type, float x, float y, float angle, float radius, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		def.angle = angle;
		def.position.set(x, y);
		def.active = true;
		def.allowSleep = false;
		Body body = physWorld.createBody(def);

		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		body.createFixture(shape, density);
		shape.dispose();
		MassData mass = new MassData();
		mass.mass = 1000f;
		body.setMassData(mass);

		return body;
	}

	public Body createEdge(BodyType type, float x0, float y0, float x1, float y1, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		def.active = true;
		def.allowSleep = false;
		Body body = physWorld.createBody(def);

		EdgeShape shape = new EdgeShape();
		shape.set(new Vector2(0, 0), new Vector2(x1 - x0, y1 - y0));
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 1;
		fdef.density = density;
		body.createFixture(fdef);
		body.setTransform(x0, y0, 0);
		shape.dispose();

		return body;
	}

	public Body createPolygon(BodyType type, float x, float angle, float y, Vector2[] vertices, float density) {
		BodyDef def = new BodyDef();
		def.type = type;
		def.angle = angle;
		def.active = true;
		def.allowSleep = false;
		Body body = physWorld.createBody(def);
		PolygonShape shape = new PolygonShape();
		shape.set(vertices);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 1;
		fdef.density = density;
		body.createFixture(fdef);
		body.setTransform(x, y, 0f);
		shape.dispose();
		return body;
	}

}
