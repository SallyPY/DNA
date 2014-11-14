package com.me.DNA.entities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.me.DNA.main.Game;

public class Strip extends B2DSprite {
	private TextureRegion[] blocks;

	
public Strip(Body body)
	{
		super(body);
		Texture tex = Game.res.getTexture("strip");
		blocks = new TextureRegion[7];
		for(int i = 0; i< blocks.length;i++)
		{
			blocks[i] = new TextureRegion(tex, i * 16, 0, 16, 16);
			
		}
		
		
		animation.setFrames(blocks, 10/3f);
		
		width = blocks[0].getRegionWidth();
		height = blocks[0].getRegionHeight();
		
	}
	
}