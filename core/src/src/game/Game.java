package src.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import src.game.characters.Being;
import src.game.characters.Core;
import src.game.graphics.Renderer;

public class Game extends ApplicationAdapter {
	
	AssetManager assetManager = new AssetManager();
	Renderer ren;
	Texture img;
	TextureRegion reg;
	
	ArrayList<Entity> chars = new ArrayList<>();
	
	@Override
	public void create () {
		assetManager.load("badlogic.jpg", Texture.class);
		assetManager.load("Tiny16-ExpandedFemaleSprites.png", Texture.class);
		assetManager.load("Tiny16-ExpandedMaleSprites.png", Texture.class);
		assetManager.load("Objects.png", Texture.class);
		
		ren = new Renderer(new SpriteBatch());
		while(!assetManager.update());
		img = assetManager.get("Tiny16-ExpandedFemaleSprites.png");
		reg = new TextureRegion(img, 0, 0, 16, 16);
		
		chars.add(new Being(new Vector3(1,4,0), reg));
		chars.add(new Core(new Vector3(16,16,0), new TextureRegion((Texture) assetManager.get("Objects.png"), 16,0,16,16)));
		
		initRender();
	}
	
	private void initRender() {
		//Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
	}

	@Override
	public void render () {
		//Render calls
		
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		ren.spriteBatch.begin();
		//lol hack
		//Gdx.gl.glDepthMask(true);
		for (Entity ent : chars) {
			ent.render(ren);
		}
		ren.spriteBatch.end();
		
		
		//Update calls
		for (Entity ent : chars) {
			ent.update();
		}
	}
}
