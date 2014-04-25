package org.ggj.imposteurs.manager;

import java.util.List;

import org.ggj.imposteurs.component.ray.RayPath;
import org.ggj.imposteurs.component.ray.RayPath.RaySegment;

import com.apollo.annotate.InjectManager;
import com.apollo.managers.RenderManager;
import com.apollo.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class GdxRenderManager extends RenderManager<SpriteBatch> {

	@InjectManager
	private TagManager tagManager;

	private ShapeRenderer renderer;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	// private Box2DDebugRenderer debugRenderer;

	private ParticleEffect laserEffect;

	public GdxRenderManager(SpriteBatch batch) {
		super(batch);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.zoom = 1f;
		// debugRenderer = new Box2DDebugRenderer();
		renderer = new ShapeRenderer();

		laserEffect = new ParticleEffect();
		laserEffect.load(Gdx.files.internal("particle/laser.p"), Gdx.files.internal("data"));
		laserEffect.start();
	}

	@Override
	public void render(SpriteBatch batch) {
		List<RaySegment> rays = null;
		if (tagManager.getEntity("RAYS") != null)
			rays = tagManager.getEntity("RAYS").getComponent(RayPath.class).getList();

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		super.render(batch);
		if (rays != null) {
			for (RaySegment seg : rays) {
				laserEffect.findEmitter("Laser").getTint()
						.setColors(new float[] { seg.color.r, seg.color.g, seg.color.b });
				laserEffect.setPosition(seg.p2.x, seg.p2.y);
				laserEffect.draw(batch, Gdx.graphics.getDeltaTime());
			}
		}
		batch.end();

		if (rays != null) {
			renderer.begin(ShapeType.Line);
			for (RaySegment seg : rays) {
				renderer.setColor(seg.color);

				Vector2 h = seg.p1.cpy().sub(seg.p2).nor();

				h.set(h.y, -h.x);

				for (int i = -4; i <= 8; i++) {
					Vector2 h_ = h.cpy().scl((float) (i / 4 * 0.3));
					renderer.line(seg.p1.add(h_), seg.p2.add(h_));
				}
			}
			rays.clear();
			renderer.end();
		}

		// debugRenderer.render(world.getManager(PhysicManager.class).getPhysicWorld(),
		// camera.combined);
	}
}
