package com.me.DNA.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.me.DNA.main.Game;

public class Lettera extends B2DSprite{
	
	public Lettera(Body body){
		super(body);
			Texture tex = Game.res.getTexture("a");
			TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];
			//25 15 900 30
			animation.setFrames(sprites, 1/7f);
			width = sprites[0].getRegionWidth();
			height = sprites[0].getRegionHeight(); 
	}
}
