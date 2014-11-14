package com.me.DNA.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.me.DNA.main.Game;

public class Letterc extends B2DSprite
{
public Letterc(Body body)
{
	super(body);
		
		Texture tex = Game.res.getTexture("c");
		TextureRegion[] sprites = TextureRegion.split(tex, 16, 16)[0];
		
		animation.setFrames(sprites, 1/8f);
		width = sprites[0].getRegionWidth();
		height = sprites[0].getRegionHeight(); 
		
	}
	
}
	
