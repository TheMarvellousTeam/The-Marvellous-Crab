package org.ggj.imposteurs.builder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.ggj.imposteurs.MyGroup;
import org.ggj.imposteurs.MyLayer;
import org.ggj.imposteurs.builder.data.BlockData;
import org.ggj.imposteurs.builder.data.BlockDefinition;
import org.ggj.imposteurs.builder.data.EmitterData;
import org.ggj.imposteurs.builder.data.FilterData;
import org.ggj.imposteurs.builder.data.LevelData;
import org.ggj.imposteurs.builder.data.MirrorData;
import org.ggj.imposteurs.builder.data.PrismData;
import org.ggj.imposteurs.builder.data.ReceptorData;
import org.ggj.imposteurs.builder.data.TeleporterData;
import org.ggj.imposteurs.component.Colored;
import org.ggj.imposteurs.component.Physic;
import org.ggj.imposteurs.component.Physic2;
import org.ggj.imposteurs.component.ray.EmitterHandler;
import org.ggj.imposteurs.component.ray.FilterHandler;
import org.ggj.imposteurs.component.ray.MirrorHandler;
import org.ggj.imposteurs.component.ray.PrismHandler;
import org.ggj.imposteurs.component.ray.RayPath;
import org.ggj.imposteurs.component.ray.ReceptorHandler;
import org.ggj.imposteurs.component.ray.TeleportHandler;
import org.ggj.imposteurs.component.ray.WallHandler;
import org.ggj.imposteurs.component.ui.Movable;
import org.ggj.imposteurs.component.ui.Rotable;
import org.ggj.imposteurs.manager.PhysicManager;
import org.ggj.imposteurs.spatial.BackgroundSpatial;
import org.ggj.imposteurs.spatial.BernardSpatial;
import org.ggj.imposteurs.spatial.IrisSpatial;
import org.ggj.imposteurs.spatial.MovableSpatial;
import org.ggj.imposteurs.spatial.PalourdeSpatial;
import org.ggj.imposteurs.spatial.RotableSpatial;
import org.ggj.imposteurs.spatial.SpriteSpatial;
import org.ggj.imposteurs.spatial.TeleporterSpatial;
import org.ggj.imposteurs.utils.ColorMapper;
import org.ggj.imposteurs.utils.Utils;

import com.apollo.Entity;
import com.apollo.Layer;
import com.apollo.World;
import com.apollo.components.spatial.Node;
import com.apollo.managers.GroupManager;
import com.apollo.managers.TagManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

public class LevelStore {
	private static LevelStore instance;
	private List<LevelData> levels;
	private ArrayList<BlockDefinition> definitions;
	private String current;

	private LevelStore() {
		levels = new ArrayList<LevelData>();
		definitions = new ArrayList<BlockDefinition>();
	}

	public static LevelStore get() {
		if (instance == null)
			return instance = new LevelStore();
		else
			return instance;
	}

	public void loadLevels(String filepath) {
		String text = Gdx.files.internal(filepath).readString();
		Type type = Utils.getArrayListTypeFor(String.class);
		ArrayList<String> levelFiles = new Gson().fromJson(text, type);
		for (String file : levelFiles)
			loadLevel(file);
	}

	private void loadLevel(String filepath) {
		System.out.print("loading level file " + filepath + "...");
		String text = Gdx.files.internal(filepath).readString();
		Gson g = new Gson();
		LevelData levelData = g.fromJson(text, LevelData.class);
		levels.add(levelData);
		System.out.println("OK");
	}

	public void loadBlockDefinitions(String filepath) {
		Gson g = new Gson();
		String t;
		String text = Gdx.files.internal(filepath).readString();
		Type type = Utils.getArrayListTypeFor(BlockDefinition.class);
		List<LinkedTreeMap<?, ?>> treeMapList = g.fromJson(text, type);
		for (LinkedTreeMap<?, ?> map : treeMapList) {
			System.out.println(t = g.toJson(map));
			definitions.add(g.fromJson(t, BlockDefinition.class));
		}
		System.out.println(definitions);
	}

	public List<LevelData> getAllLevels() {
		return levels;
	}

	public List<String> getAllLevelNames() {
		List<String> names = new ArrayList<String>();
		for (LevelData level : levels)
			names.add(level.name);
		return names;
	}

	public void startLevel(World world, String levelName) {
		System.out.println("start " + levelName);
		LevelData levelData = null;
		for (LevelData level : levels) {
			if (level.name.equals(levelName)) {
				levelData = level;
				break;
			}
		}

		if (levelData == null) {
			System.err.println("Impossible de charger " + levelName);
			return;
		}
		PhysicManager physManager = world.getManager(PhysicManager.class);
		GroupManager groupManager = world.getManager(GroupManager.class);
		TagManager tagManager = world.getManager(TagManager.class);

		Entity rays = new Entity(world);
		rays.setComponent(new RayPath());
		tagManager.register("RAYS", rays);
		rays.addToWorld();

		Entity background = new Entity(world);
		background.setComponent(new BackgroundSpatial(new Texture(Gdx.files.internal("background/souslocean.png")),
				new Texture(Gdx.files.internal("background/crabAnim.png"))));
		background.addToWorld();

		for (EmitterData em : levelData.emitters) {
			Entity e = new Entity(world);

			Node<SpriteBatch> node = new Node<SpriteBatch>() {
				@Override
				public Layer getLayer() {
					// TODO Auto-generated method stub
					return MyLayer.BACKGROUND;
				}

				@Override
				public void render(SpriteBatch arg0) {
					// TODO Auto-generated method stub

				}
			};
			node.addChild(new SpriteSpatial(new Texture(Gdx.files.internal("data/oueil.png"))));
			node.addChild(new IrisSpatial());
			node.addChild(new RotableSpatial());
			e.setComponent(node);
			e.setComponent(new Physic(physManager.createCircle(BodyType.StaticBody, em.transform.x, em.transform.y,
					em.transform.angle * MathUtils.degreesToRadians, 18, 0)));
			e.setComponent(new EmitterHandler());
			e.setComponent(new Colored(ColorMapper.getColor(em.color)));
			e.setComponent(new Rotable(0));
			e.addToWorld();
		}

		for (MirrorData mi : levelData.mirrors) {
			Entity e = new Entity(world);
			e.setComponent(new Physic(physManager.createBox(BodyType.StaticBody, mi.transform.x, mi.transform.y, 32, 8,
					MathUtils.degreesToRadians * mi.transform.angle, 0)));

			// declare a middle node
			Node<SpriteBatch> node = new Node<SpriteBatch>() {
				@Override
				public Layer getLayer() {
					// TODO Auto-generated method stub
					return MyLayer.BACKGROUND;
				}

				@Override
				public void render(SpriteBatch arg0) {
					// TODO Auto-generated method stub

				}
			};
			node.addChild(new SpriteSpatial(new Texture(Gdx.files.internal("data/mirror.png"))));

			e.setComponent(node);
			if (mi.rotable) {
				e.setComponent(new Rotable(0));
				node.addChild(new RotableSpatial());
			}
			if (mi.movable != null) {
				e.setComponent(new Movable(new Rectangle(mi.movable.x, mi.movable.y, mi.movable.w, mi.movable.h)));
				node.addChild(new MovableSpatial());
			}
			e.setComponent(new MirrorHandler());
			e.addToWorld();
		}

		for (ReceptorData re : levelData.receptors) {
			Entity e = new Entity(world);
			e.setComponent(new Physic(physManager.createBox(BodyType.StaticBody, re.transform.x, re.transform.y, 16,
					16, 0, 0)));
			e.setComponent(new Colored(ColorMapper.getColor(re.color)));
			e.setComponent(new ReceptorHandler());
			// e.setComponent(new ReceiverNode(new
			// Texture(Gdx.files.internal("data/receiver.png"))));
			e.setComponent(new BernardSpatial());
			groupManager.setGroup(e, MyGroup.RECEPTOR);
			e.addToWorld();
		}

		for (PrismData mi : levelData.prisms) {

			float size = 50;

			Vector2[] poly = new Vector2[] { new Vector2(0, 0).scl(size),
					new Vector2((float) 0.5, (float) (-0.866)).scl(size),
					new Vector2((float) -0.5, (float) (-0.866)).scl(size) };
			Vector2 center = new Vector2();
			for (Vector2 v : poly)
				center.add(v);
			center.scl((float) (1.0 / 3.0));

			for (Vector2 v : poly)
				v.sub(center);

			Entity e = new Entity(world);
			Body phy = physManager.createPolygon(BodyType.StaticBody, (float) mi.transform.x,
					(float) (MathUtils.degreesToRadians * mi.transform.angle), (float) mi.transform.y, poly, 0);
			e.setComponent(new Physic(phy));

			// declare a middle node
			Node<SpriteBatch> node = new Node<SpriteBatch>() {
				@Override
				public Layer getLayer() {
					// TODO Auto-generated method stub
					return MyLayer.BACKGROUND;
				}

				@Override
				public void render(SpriteBatch arg0) {
					// TODO Auto-generated method stub

				}
			};
			node.addChild(new SpriteSpatial(new Texture(Gdx.files.internal("data/prisme.png"))));

			e.setComponent(node);
			if (mi.rotable) {
				e.setComponent(new Rotable(0));
				node.addChild(new RotableSpatial());
			}
			if (mi.movable != null) {
				e.setComponent(new Movable(new Rectangle(mi.movable.x, mi.movable.y, mi.movable.w, mi.movable.h)));
				node.addChild(new MovableSpatial());
			}
			e.setComponent(new PrismHandler());
			e.addToWorld();
		}

		for (FilterData filter : levelData.filters) {
			Entity e = new Entity(world);
			Physic p = new Physic(physManager.createBox(BodyType.StaticBody, filter.transform.x, filter.transform.y,
					15, 15, MathUtils.degreesToRadians * filter.transform.angle, 0));
			// p.offsetX = 15;
			// p.offsetY = -15;
			e.setComponent(p);
			e.setComponent(new Colored(ColorMapper.getColor(filter.color)));
			// e.setComponent(new ColoredSpriteSpatial(new
			// Texture(Gdx.files.internal("data/receiver.png"))));
			e.setComponent(new PalourdeSpatial(new Texture(Gdx.files.internal("data/palourde.png")), new Texture(
					Gdx.files.internal("data/pearl.png"))));
			if (filter.rotable)
				e.setComponent(new Rotable(0));
			if (filter.movable != null)
				e.setComponent(new Movable(new Rectangle(filter.movable.x, filter.movable.y, filter.movable.w,
						filter.movable.h)));
			e.setComponent(new FilterHandler());
			e.addToWorld();
		}

		for (BlockData data : levelData.blocks) {
			BlockDefinition def = null;
			for (BlockDefinition defIt : definitions)
				if (defIt.name.equals(data.type)) {
					def = defIt;
					break;
				}
			if (def == null) {
				System.err.println(data.type + " isn't defined as a block.");
				break;
			}
			Vector2[] poly = BlockDefinition.asVector2Array(def.vectrices);
			Entity e = new Entity(world);
			Body phy = physManager.createPolygon(BodyType.StaticBody, data.transform.x, 0, data.transform.y, poly, 0);
			Physic p = new Physic(phy);
			p.offsetX = def.offx;
			p.offsetY = def.offy;
			e.setComponent(new SpriteSpatial(new Texture(Gdx.files.internal(def.file))));
			e.setComponent(p);
			e.setComponent(new WallHandler());

			if (data.movable != null)
				e.setComponent(new Movable(
						new Rectangle(data.movable.x, data.movable.y, data.movable.w, data.movable.h)));

			e.addToWorld();
		}

		for (TeleporterData data : levelData.teleporters) {
			Entity teleport = new Entity(world);
			teleport.setComponent(new Physic2(physManager.createBox(BodyType.StaticBody, data.p1.x - 5, data.p1.y - 5,
					10, 10, 0, 0), physManager.createBox(BodyType.StaticBody, data.p2.x - 5, data.p2.y - 5, 10, 10, 0,
					0)));
			teleport.setComponent(new TeleportHandler(new Vector2(data.p1.x, data.p1.y), new Vector2(data.p2.x,
					data.p2.y)));
			teleport.setComponent(new TeleporterSpatial(Gdx.files.internal("particle/teleporter.p"), Gdx.files
					.internal("data")));
			teleport.addToWorld();
		}

		float[][] bounds = { { 480, 0, 480, 800, 580, 480 }, { 0, 0, -100, 400, 0, 800 },
				{ 0, 800, 240, 900, 480, 800 }, { 0, 0, 240, -100, 480, 0 } };
		for (float[] bound : bounds) {
			Vector2[] poly = BlockDefinition.asVector2Array(bound);

			Vector2 center = new Vector2();

			for (Vector2 v : poly)
				center.add(v);
			center.scl((float) (1.0 / poly.length));

			for (Vector2 v : poly)
				v.sub(center);

			Entity e = new Entity(world);
			Body phy = physManager.createPolygon(BodyType.StaticBody, center.x, 0, center.y, poly, 0);
			// e.setComponent(new SpriteSpatial(new
			// Texture(Gdx.files.internal("data/stone.png"))));
			e.setComponent(new Physic(phy));
			e.setComponent(new WallHandler());
			e.addToWorld();
		}

		current = levelName;

	}

	public String getNextLevelName() {
		if (current == null) {
			return levels.get(0).name;
		}
		int index = -1;
		for (int i = 0; i < levels.size() - 1; i++) {
			if (levels.get(i).name.equals(current)) {
				index = i + 1;
			}
		}
		if (index != -1) {
			return levels.get(index).name;
		} else {
			System.out.println("dernier level atteint!");
			return null;
		}
	}
}
