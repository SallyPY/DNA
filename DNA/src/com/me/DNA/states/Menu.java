package com.me.DNA.states;


import static com.me.DNA.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.me.DNA.handlers.Background;
import com.me.DNA.handlers.GameButton;
import com.me.DNA.handlers.GameStateManager;
import com.me.DNA.main.Game;

public class Menu extends GameState {
	
private boolean debug = false;
	
	private Background bg;

	private GameButton playButton;
	
	private GameButton exitButton;
	
	private World world;
	private Box2DDebugRenderer b2dRenderer;
	
	
	
	public Menu(GameStateManager gsm) {
		
		super(gsm);
		
		Texture tex = new Texture("res/images/splashscreen2.png");
		bg = new Background(new TextureRegion(tex), cam, 1f);
		bg.setVector(-20, 0);
		
		tex = new Texture("res/images/button3.png");
				//Game.res.getTexture("hud");
		playButton = new GameButton(new TextureRegion(tex), 574, 73, cam);
		
		
		tex = new Texture("res/images/button4.png");
		exitButton = new GameButton(new TextureRegion(tex), 580, 30, cam);
		
	
		//, 0, 34, 58, 27
		cam.setToOrtho(false, 640, 480);
		
		world = new World(new Vector2(0, -9.8f * 5), true);

		b2dRenderer = new Box2DDebugRenderer();		
	}
	
	public void handleInput() {
		
		// mouse/touch input
		if(playButton.isClicked()) {
			Game.res.getSound("crystal").play();
			gsm.setState(GameStateManager.INSTRUCTIONS);
		}
		if(exitButton.isClicked()) {		
			Gdx.app.exit();
		}
		
		
	}
	
	public void update(float dt) {
		
		handleInput();
		
		world.step(dt / 5, 8, 3);
		
		bg.update(dt);
		//animation.update(dt);
		
		playButton.update(dt);
		exitButton.update(dt);
	}
	
	public void render() {
		 Gdx.gl.glClearColor(0, 0, 0, 1);
	     Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	  
	     sb.setProjectionMatrix(cam.combined);
		
		// draw background
		bg.render2(sb);
		
		// draw button
		playButton.render(sb);
		exitButton.render(sb);
		
		
		// debug draw box2d
		if(debug) {
			cam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
			b2dRenderer.render(world, cam.combined);
			cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		}
			
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}