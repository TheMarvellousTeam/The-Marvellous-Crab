package org.ggj.imposteurs.spatial;

import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.component.Physic2;

import com.apollo.Layer;
import com.apollo.annotate.InjectComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TeleporterSpatial extends GdxSpatial {

	@InjectComponent
	private Physic2 phys;

	private ParticleEffect effect1, effect2;

	public TeleporterSpatial(FileHandle effectFile, FileHandle imagesDir) {
		super();
		effect1 = new ParticleEffect();
		effect1.load(effectFile, imagesDir);

		effect2 = new ParticleEffect();
		effect2.load(effectFile, imagesDir);

		effect1.start();
		effect2.start();
	}

	@Override
	public Layer getLayer() {
		return MyLayer.ITEM;
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		effect1.setPosition(phys.getBodyA().getPosition().x, phys.getBodyA().getPosition().y);
		effect2.setPosition(phys.getBodyB().getPosition().x, phys.getBodyB().getPosition().y);
	}

	@Override
	public void render(SpriteBatch batch) {
		effect1.draw(batch, Gdx.graphics.getDeltaTime());
		effect2.draw(batch, Gdx.graphics.getDeltaTime());
	}
}
