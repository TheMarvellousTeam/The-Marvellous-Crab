package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.component.Colored;
import org.ggj.imposteurs.component.Physic;
import org.ggj.imposteurs.component.ray.RayHandler;
import org.ggj.imposteurs.component.ray.ReceptorHandler;
import org.ggj.imposteurs.utils.ColorMapper;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class BernardSpatial extends GdxSpatial {

	@InjectComponent
	private Physic phys;

	@InjectComponent
	private RayHandler h;

	@InjectComponent
	private Colored colored;

	private ReceptorHandler handler;
	private TextureRegion[] showNHideRegions;
	private TextureRegion[] jumpingRegion;

	private TextureRegion currentFrame;
	private Animation showNHideAnimation;
	private Animation jumpingAnimation;
	private Sprite def;
	private Animation current;

	private float stateTime;

	private Timer success;
	private Timer waiting;

	public BernardSpatial() {

	}

	public void initialize() {
		handler = (ReceptorHandler) h;
		String c = ColorMapper.getStringColor(colored.getColor());
		if (c == null) {
			System.err.println(c + " couleur inconnue pour le mapper!");
		}
		Texture texture = new Texture(Gdx.files.internal("data/" + c + " beggarset.png"));
		TextureRegion[][] regions = TextureRegion.split(texture, 96, 96);

		def = new Sprite(regions[0][1]);
		def.setOrigin(def.getWidth() / 2, def.getHeight() / 2);
		showNHideRegions = new TextureRegion[20];
		showNHideRegions[0] = regions[0][1];
		showNHideRegions[1] = regions[0][2];
		showNHideRegions[2] = regions[0][3];
		for (int i = 3; i < 17; i++)
			showNHideRegions[i] = regions[0][0];
		showNHideRegions[17] = regions[0][3];
		showNHideRegions[18] = regions[0][2];
		showNHideRegions[19] = regions[0][1];
		showNHideAnimation = new Animation(100, showNHideRegions);

		jumpingRegion = new TextureRegion[9];
		jumpingRegion[0] = regions[1][0];
		jumpingRegion[1] = regions[1][1];
		jumpingRegion[2] = regions[1][2];
		jumpingRegion[3] = regions[1][3];
		jumpingRegion[4] = regions[2][0];
		jumpingRegion[5] = regions[2][1];
		jumpingRegion[6] = regions[2][2];
		jumpingRegion[7] = regions[2][3];
		jumpingRegion[8] = regions[3][0];
		jumpingAnimation = new Animation(100, jumpingRegion);

		stateTime = 0f;
		waiting = new Timer();
		waiting.scheduleTask(new Task() {

			@Override
			public void run() {
				current = showNHideAnimation;
				stateTime = 0;

			}
		}, MathUtils.random(5), MathUtils.random(5) + 10);
		waiting.stop();

		success = new Timer();
		success.scheduleTask(new Task() {

			@Override
			public void run() {
				current = jumpingAnimation;
				stateTime = 0;

			}
		}, 0.5f, 2);
		success.stop();

		waiting.start();
	}

	@Override
	public Layer getLayer() {
		return MyLayer.ITEM;
	}

	@Override
	public void render(SpriteBatch batch) {
		if (current == null) {
			System.out.println("static");
			batch.draw(def, phys.getBody().getPosition().x - def.getWidth() / 2,
					phys.getBody().getPosition().y - def.getHeight() / 2);
			return;
		} else {
			batch.draw(currentFrame, phys.getBody().getPosition().x - def.getWidth() / 2,
					phys.getBody().getPosition().y - def.getHeight() / 2);
		}

	}

	public void update(int delta) {
		super.update(delta);

		if (handler.isSuccessfull()) {
			waiting.stop();
			success.start();
			current = jumpingAnimation;
		} else {
			success.stop();
			waiting.start();
			current = showNHideAnimation;
		}
		stateTime += (delta);
		if (current != null)
			currentFrame = current.getKeyFrame(stateTime, false);
	}

}
