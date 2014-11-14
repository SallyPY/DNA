package com.me.DNA.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

public class BBContactListener implements ContactListener{
	
	private int numFootContacts;
	private Array<Body> abodiesToRemove;
	private Array<Body> gbodiesToRemove;
	private Array<Body> ubodiesToRemove;
	private Array<Body> cbodiesToRemove;
	private Array<Body> badgBodiesToRemove;
	private Array<Body> badaBodiesToRemove;
	private Array<Body> badcBodiesToRemove;
	private Array<Body> baduBodiesToRemove;
	private boolean playerDead;
	public int totalContacts = 0;
	
	
	public BBContactListener() {
		super();
		abodiesToRemove = new Array<Body>();
		cbodiesToRemove = new Array<Body>();
		gbodiesToRemove = new Array<Body>();
		ubodiesToRemove = new Array<Body>();
		badgBodiesToRemove = new Array<Body>();
		badaBodiesToRemove = new Array<Body>();
		badcBodiesToRemove = new Array<Body>();
		baduBodiesToRemove = new Array<Body>();
		
	}
	
	public void beginContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa == null || fb == null) return;
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts++;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts++;
		}
		
		//if(fa.getUserData() != null && fa.getUserData().equals("letter") && totalContacts == 0) {
			//bodiesToRemove.add(fa.getBody());
			//totalContacts++;
		
		//}
	//	if(fa.getUserData() != null && fa.getUserData().equals("letter") && totalContacts == 1) {
			//badBodiesToRemove.add(fa.getBody());}
			//totalContacts++;	
				
		
		if(fb.getUserData() != null && fb.getUserData().equals("letter") && totalContacts == 0) {
			abodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
			else if( fb.getUserData() != null && fb.getUserData().equals("letterg") && totalContacts == 0)
		{
			badgBodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		
		else if(fb.getUserData() != null && fb.getUserData().equals("letter") && totalContacts == 1) {
			abodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		else if( fb.getUserData() != null && fb.getUserData().equals("letteru") && totalContacts == 1)
		{
			baduBodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		
		else if(fb.getUserData() != null && fb.getUserData().equals("letteru") && totalContacts == 2) {
			ubodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		else if( fb.getUserData() != null && fb.getUserData().equals("letterc") && totalContacts == 2)
		{
			badcBodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		
		else if(fb.getUserData() != null && fb.getUserData().equals("letteru") && totalContacts == 3) {
			ubodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		else if( fb.getUserData() != null && fb.getUserData().equals("letterg") && totalContacts == 3)
		{
			badgBodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		else if(fb.getUserData() != null && fb.getUserData().equals("letterc") && totalContacts == 4) {
			cbodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		else if( fb.getUserData() != null && fb.getUserData().equals("letter") && totalContacts == 4)
		{
			badaBodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		
		else if(fb.getUserData() != null && fb.getUserData().equals("letter") && totalContacts == 5) {
			abodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		else if( fb.getUserData() != null && fb.getUserData().equals("letteru") && totalContacts == 5)
		{
			baduBodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		
		else if(fb.getUserData() != null && fb.getUserData().equals("letterg") && totalContacts == 6) {
			gbodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		else if( fb.getUserData() != null && fb.getUserData().equals("letteru") && totalContacts == 6)
		{
			baduBodiesToRemove.add(fb.getBody());
			totalContacts++;
		}
		
			
	
		
		
	}
	
	public void endContact(Contact contact) {
		
		Fixture fa = contact.getFixtureA();
		Fixture fb = contact.getFixtureB();
		
		if(fa == null || fb == null) return;
		
		if(fa.getUserData() != null && fa.getUserData().equals("foot")) {
			numFootContacts--;
		}
		if(fb.getUserData() != null && fb.getUserData().equals("foot")) {
			numFootContacts--;
		}
		
	}
	
	public boolean playerCanJump() { return numFootContacts > 0; }
	public Array<Body> getaBodies() { return abodiesToRemove; }
	public Array<Body> getuBodies() { return ubodiesToRemove; }
	public Array<Body> getgBodies() { return gbodiesToRemove; }
	public Array<Body> getcBodies() { return cbodiesToRemove; }
	public Array<Body> getBadgBodies() { return badgBodiesToRemove; }
	public Array<Body> getBadaBodies() { return badaBodiesToRemove; }
	public Array<Body> getBaduBodies() { return baduBodiesToRemove; }
	public Array<Body> getBadcBodies() { return badcBodiesToRemove; }
	public boolean isPlayerDead() { return playerDead; }
	
	public void preSolve(Contact c, Manifold m) {}
	public void postSolve(Contact c, ContactImpulse ci) {}
	
}
 