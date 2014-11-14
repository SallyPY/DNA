package com.me.DNA.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.me.DNA.handlers.BBInput;
import com.me.DNA.handlers.BBInputProcessor;
import com.me.DNA.handlers.BoundedCamera;
import com.me.DNA.handlers.Content;
import com.me.DNA.handlers.GameStateManager;


public class Game implements ApplicationListener {
	public static final String TITLE = "Block Bunny";
	public static final int V_WIDTH = 320;
	public static final int V_HEIGHT = 240;
	public static final int SCALE = 2;
	
	public static final float STEP = 1 / 60f;

	
	private SpriteBatch sb;
	private BoundedCamera cam;
	private OrthographicCamera hudCam;
	
	private GameStateManager gsm;
	
	public static Content res;
	
	
	
	public void create() {
		Texture.setEnforcePotImages(false);
		
		Texture.setEnforcePotImages(false);
		Gdx.input.setInputProcessor(new BBInputProcessor());
		
		res = new Content();
		res.loadTexture("res/images/menu.png");
		res.loadTexture("res/images/hud.png");
		res.loadTexture("res/images/poly.png");
		res.loadTexture("res/images/a.png");
		res.loadTexture("res/images/letterg.png");
		res.loadTexture("res/images/checkmark.png");
		res.loadTexture("res/images/x.png");
		
		res.loadTexture("res/images/g.png");
		res.loadTexture("res/images/c.png");
		res.loadTexture("res/images/u.png");
		res.loadTexture("res/images/strip.png");
		
		
		res.loadSound("res/sfx/jump.wav");
		res.loadSound("res/sfx/crystal.wav");
		res.loadSound("res/sfx/levelselect.wav");
		res.loadSound("res/sfx/hit.wav");
		res.loadSound("res/sfx/hithit.wav");
		
		
		res.loadMusic("res/music/DST-BetaTron.mp3");
		res.getMusic("DST-BetaTron").setLooping(true);
		res.getMusic("DST-BetaTron").setVolume(0.5f);
		res.getMusic("DST-BetaTron").play();
		
		cam = new BoundedCamera();
		cam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		hudCam = new OrthographicCamera();
		hudCam.setToOrtho(false, V_WIDTH, V_HEIGHT);
		
		sb = new SpriteBatch();
		
		gsm = new GameStateManager(this);
		
	}
	
public void render() {
		
		Gdx.graphics.setTitle("Decoding Life");
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render();
		BBInput.update();

		//sb.setProjectionMatrix(hudCam.combined);
		//sb.begin();
		//sb.draw(res.getTexture("bunny"), 0, 0);
		//sb.end();
	}
	
	public void dispose() {
		res.removeAll();
		
	}
	public SpriteBatch getSpriteBatch() {return sb;}
	public BoundedCamera getCamera() { return cam; }
	public OrthographicCamera getHUDCamera() { 
		return hudCam;
	}
	
	
	
	public void resize(int w, int h) {}
	public void pause() {}
	public void resume() {}
	

}
