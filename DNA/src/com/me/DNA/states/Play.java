package com.me.DNA.states;

import static com.me.DNA.handlers.B2DVars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.me.DNA.entities.HUD;
import com.me.DNA.entities.Lettera;
import com.me.DNA.entities.Letterc;
import com.me.DNA.entities.Letterg;
import com.me.DNA.entities.Letteru;
import com.me.DNA.entities.Player;
import com.me.DNA.entities.Strip;
import com.me.DNA.handlers.B2DVars;
import com.me.DNA.handlers.BBContactListener;
import com.me.DNA.handlers.BBInput;
import com.me.DNA.handlers.Background;
import com.me.DNA.handlers.BoundedCamera;
import com.me.DNA.handlers.GameStateManager;
import com.me.DNA.main.Game;

public class Play extends GameState{
	
	private boolean debug = false;

	private World world;
	private Box2DDebugRenderer b2dr;
	private BoundedCamera b2dCam;
	

	private Player player;
	private Strip strip;
	
	private BBContactListener cl;
	
	private TiledMap tilemap;
	private float tileSize;
	private int tileMapWidth;
	private int tileMapHeight;
	private OrthogonalTiledMapRenderer tmRenderer;
	
	private Array<Lettera> letters;
	private Array<Letterg> lettersg;
	private Array<Letterc> lettersc;
	private Array<Letteru> lettersu;
	
	private Background bg;

	private HUD hud;
	public Play(GameStateManager gsm) {
		
		super(gsm);
		//set up box2d stuff
		world = new World(new Vector2(0, -9.81f), true);
		
		cl = new BBContactListener();
		world.setContactListener(cl);
		
		b2dr = new Box2DDebugRenderer();
		// create player
		createPlayer();
		createStrip();
		
		// create walls
		createWalls();
		cam.setBounds(0, tileMapWidth * tileSize, 0, tileMapHeight * tileSize);
		
		//create letters
		createLettersa();
		//player.setTotalCrystals(letters.size);
		
		createLettersg();
		player.setTotalCrystals(letters.size + lettersg.size);
		
		//createLettersc
		createLettersc();
		player.setTotalCrystals(letters.size + lettersg.size + lettersc.size);
		
		createLettersu();
		
		
		// create backgrounds
		Texture tex = new Texture("res/images/firstbackgroundimage.png");
		bg = new Background(new TextureRegion(tex), cam, 1f);
		bg.setVector(-20, 0);
	
	
		// create hud
		hud = new HUD(player);
		// set up box2d cam
		b2dCam = new BoundedCamera();
		b2dCam.setToOrtho(false, Game.V_WIDTH / PPM, Game.V_HEIGHT / PPM);
		b2dCam.setBounds(0, (tileMapWidth * tileSize) / PPM, 0, (tileMapHeight * tileSize) / PPM);
		
	
	}
	
	
	private void createPlayer(){
		// create bodydef
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.DynamicBody;
		bdef.position.set(100 / PPM, 200 / PPM);
		bdef.fixedRotation = true;
		bdef.linearVelocity.set(1f,0);
		
		// create body from bodydef
		Body body = world.createBody(bdef);
		
		// create box shape for player collision box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(13 / PPM, 13 / PPM); // changer size
		
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1;
		fdef.friction = 0;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_REDRED | B2DVars.BIT_LETTER;
		
		body.createFixture(fdef);
		shape.dispose();
		
		// create foot sensor
		shape = new PolygonShape();
		shape.setAsBox(13 / PPM, 2 / PPM, new Vector2(0, -13 / PPM), 0);
		
		// create fixturedef for player foot
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_REDRED;
		
		
		body.createFixture(fdef).setUserData("foot");;
		shape.dispose();
		
		player = new Player(body);
		body.setUserData(player);
		
		// final tweaks, manually set the player body mass to 1 kg
				MassData md = body.getMassData();
				md.mass = 1;
				body.setMassData(md);
				
				// i need a ratio of 0.005
				// so at 1kg, i need 200 N jump force
		
	}
	private void createStrip(){
		// create bodydef
		BodyDef bdef = new BodyDef();
		bdef.type = BodyType.StaticBody;
		bdef.position.set(47 / PPM, 215 / PPM);
		bdef.fixedRotation = true;
	
		
		// create body from bodydef
		Body body = world.createBody(bdef);
		
		// create box shape for player collision box
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(13 / PPM, 13 / PPM); // changer size
		
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 1;
		fdef.friction = 0;
		fdef.filter.categoryBits = B2DVars.BIT_PLAYER;
		fdef.filter.maskBits = B2DVars.BIT_REDRED | B2DVars.BIT_LETTER;
		
		body.createFixture(fdef);
		shape.dispose();
		
	
		strip = new Strip(body);
		body.setUserData(strip);

	}	
	

private void createWalls(){
	// load tile map and map renderer
			try {
				tilemap = new TmxMapLoader().load("res/maps/dnaaa.tmx");
			}
			catch(Exception e) {
				System.out.println("Cannot find file: res/maps/dna.tmx");
				Gdx.app.exit();
			}
			tileMapWidth = (int) tilemap.getProperties().get("width", Integer.class);
			tileMapHeight = (int) tilemap.getProperties().get("height", Integer.class);
			tileSize = (int) tilemap.getProperties().get("tilewidth", Integer.class);
			tmRenderer = new OrthogonalTiledMapRenderer(tilemap);
	
	TiledMapTileLayer layer;
	
	layer = (TiledMapTileLayer) tilemap.getLayers().get("blocks");
	createBlocks(layer, B2DVars.BIT_REDRED);	

}

private void createBlocks(TiledMapTileLayer layer, short bits){

	// tile size
	float ts = layer.getTileWidth();
	
	// go through all cells in layer
	for(int row = 0; row < layer.getHeight(); row++) {
		for(int col = 0; col < layer.getWidth(); col++) {
			
			// get cell
			Cell cell = layer.getCell(col, row);
			
			// check that there is a cell
			if(cell == null) continue;
			if(cell.getTile() == null) continue;
			
			// create body from cell
			BodyDef bdef = new BodyDef();
			bdef.type = BodyType.StaticBody;
			bdef.position.set((col + 0.5f) * ts / PPM, (row + 0.5f) * ts / PPM);
			ChainShape cs = new ChainShape();
			Vector2[] v = new Vector2[3];
			v[0] = new Vector2(-ts / 2 / PPM, -ts / 2 / PPM);
			v[1] = new Vector2(-ts / 2 / PPM, ts / 2 / PPM);
			v[2] = new Vector2(ts / 2 / PPM, ts / 2 / PPM);
			cs.createChain(v);
			FixtureDef fd = new FixtureDef();
			fd.friction = 0;
			fd.shape = cs;
			fd.filter.categoryBits = bits;
			fd.filter.maskBits = B2DVars.BIT_PLAYER;
			world.createBody(bdef).createFixture(fd);
			cs.dispose();
			
		}
	}
	
}
private void createLettersa(){
	letters = new Array<Lettera>();
	
	MapLayer layer = tilemap.getLayers().get("lettera");
	if(layer == null) return;
	
	
	// get all crystals in "crystals" layer,
			// create bodies for each, and add them
			// to the crystals list
	for(MapObject mo: layer.getObjects()){
		BodyDef cdef = new BodyDef();
		Ellipse e = ((EllipseMapObject) mo).getEllipse();
		cdef.type = BodyType.StaticBody;
		float x = e.x /PPM;
		float y = e.y /PPM;
		
		cdef.position.set(x,y);
		Body body = world.createBody(cdef);
		FixtureDef cfdef = new FixtureDef();
		
		CircleShape cshape = new CircleShape();
		cshape.setRadius(8/PPM);//8 of crystal
		
		cfdef.shape = cshape;
		cfdef.isSensor = true;
		cfdef.filter.categoryBits = B2DVars.BIT_LETTER;
		cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
		
		
		body.createFixture(cfdef).setUserData("letter");
		
		Lettera c = new Lettera(body);

		body.setUserData(c);
		letters.add(c);
		
		
		cshape.dispose();
		
	}
	
}
private void createLettersg(){
	lettersg = new Array<Letterg>();
	
	MapLayer layer = tilemap.getLayers().get("letterg");
	if(layer == null) return;
	
	
	// get all crystals in "crystals" layer,
			// create bodies for each, and add them
			// to the crystals list
	for(MapObject mo: layer.getObjects()){
		BodyDef cdef = new BodyDef();
		Ellipse e = ((EllipseMapObject) mo).getEllipse();
		cdef.type = BodyType.StaticBody;
		float x = e.x /PPM;
		float y = e.y /PPM;
		
		cdef.position.set(x,y);
		Body body = world.createBody(cdef);
		FixtureDef cfdef = new FixtureDef();
		
		CircleShape cshape = new CircleShape();
		cshape.setRadius(8/PPM);//8 of crystal
		
		cfdef.shape = cshape;
		cfdef.isSensor = true;
		cfdef.filter.categoryBits = B2DVars.BIT_LETTER;
		cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
		
		
		body.createFixture(cfdef).setUserData("letterg");
		
		Letterg c = new Letterg(body);

		body.setUserData(c);
		lettersg.add(c);
		
		
		cshape.dispose();
		
	}
}

	private void createLettersc(){
		lettersc = new Array<Letterc>();
		
		MapLayer layer = tilemap.getLayers().get("letterc");
		if(layer == null) return;
		
		
		// get all crystals in "crystals" layer,
				// create bodies for each, and add them
				// to the crystals list
		for(MapObject mo: layer.getObjects()){
			BodyDef cdef = new BodyDef();
			Ellipse e = ((EllipseMapObject) mo).getEllipse();
			cdef.type = BodyType.StaticBody;
			float x = e.x /PPM;
			float y = e.y /PPM;
			
			cdef.position.set(x,y);
			Body body = world.createBody(cdef);
			FixtureDef cfdef = new FixtureDef();
			
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8/PPM);//8 of crystal
			
			cfdef.shape = cshape;
			cfdef.isSensor = true;
			cfdef.filter.categoryBits = B2DVars.BIT_LETTER;
			cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
			
			
			body.createFixture(cfdef).setUserData("letterc");
			
			Letterc c = new Letterc(body);

			body.setUserData(c);
			lettersc.add(c);
			
			
			cshape.dispose();
			
		}
	
}
	private void createLettersu(){
		lettersu = new Array<Letteru>();
		
		MapLayer layer = tilemap.getLayers().get("letteru");
		if(layer == null) return;
		
		
		// get all crystals in "crystals" layer,
				// create bodies for each, and add them
				// to the crystals list
		for(MapObject mo: layer.getObjects()){
			BodyDef cdef = new BodyDef();
			Ellipse e = ((EllipseMapObject) mo).getEllipse();
			cdef.type = BodyType.StaticBody;
			float x = e.x /PPM;
			float y = e.y /PPM;
			
			cdef.position.set(x,y);
			Body body = world.createBody(cdef);
			FixtureDef cfdef = new FixtureDef();
			
			CircleShape cshape = new CircleShape();
			cshape.setRadius(8/PPM);//8 of crystal
			
			cfdef.shape = cshape;
			cfdef.isSensor = true;
			cfdef.filter.categoryBits = B2DVars.BIT_LETTER;
			cfdef.filter.maskBits = B2DVars.BIT_PLAYER;
			
			
			body.createFixture(cfdef).setUserData("letteru");
			
			Letteru c = new Letteru(body);

			body.setUserData(c);
			lettersu.add(c);
			
			
			cshape.dispose();
			
		}
	}
private void playerJump() {
	if(cl.playerCanJump()) {
		player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 0);
		player.getBody().applyForceToCenter(0, 200, true);
		Game.res.getSound("jump").play();
	}
}
private void switchBlocks() {

	
}
	
	/*// get player foot mask bits
	Filter filter = player.getBody().getFixtureList().get(1).getFilterData();
	short bits = filter.maskBits;
	
	// switch to next block bit
	// red -> green -> blue
	if(bits == B2DVars.BIT_REDRED) {
		bits = B2DVars.BIT_REDREDRED;
	}
	else if(bits == B2DVars.BIT_BLUEBLUE) {
		bits = B2DVars.BIT_REDRED;
	}
	else if(bits == B2DVars.BIT_REDREDRED) {
		bits = B2DVars.BIT_BLUEBLUE;
	}
	
	// set player foot mask bits
	filter.maskBits = bits;
	player.getBody().getFixtureList().get(1).setFilterData(filter);
	
	// set player mask bits
	bits |= B2DVars.BIT_LETTER;
	filter.maskBits = bits;
	player.getBody().getFixtureList().get(0).setFilterData(filter);
	
	// play sound
	Game.res.getSound("changeblock").play();*/


public void handleInput() {
	// keyboard input
			if(BBInput.isPressed(BBInput.BUTTON1)) {
				playerJump();
				
					
			}
			if(BBInput.isPressed(BBInput.BUTTON2)) {
				switchBlocks();
			}
			
			// mouse/touch input for android
			// left side of screen to switch blocks
			// right side of screen to jump
			if(BBInput.isPressed()) {
				if(BBInput.x < Gdx.graphics.getWidth() / 2) {
					switchBlocks();
				}
				else {
					playerJump();
				}
			}
}

public void update(float dt) {
	
	handleInput();
	
	world.step(Game.STEP, 6, 2);
	
	//remove letters
	Array<Body> bodies = cl.getaBodies();
	for(int i = 0; i < bodies.size; i++){
		Body b = bodies.get(i);
		letters.removeValue((Lettera)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("crystal").play();
	}
	bodies.clear();
	
	
	
	Array<Body> bodiesu = cl.getuBodies();
	for(int i = 0; i < bodiesu.size; i++){
		Body b = bodiesu.get(i);
		lettersu.removeValue((Letteru)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("crystal").play();
	}
	bodiesu.clear();
	
	Array<Body> bodiesc = cl.getcBodies();
	for(int i = 0; i < bodiesc.size; i++){
		Body b = bodiesc.get(i);
		lettersc.removeValue((Letterc)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("crystal").play();
	}
	bodiesc.clear();
	
	
	
	Array<Body> bodiesg = cl.getgBodies();
	for(int i = 0; i < bodiesg.size; i++){
		Body b = bodiesg.get(i);
		lettersg.removeValue((Letterg)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("crystal").play();
	}
	bodiesg.clear();
	
	Array<Body> badBodies = cl.getBadgBodies();
	for(int i = 0; i < badBodies.size; i++){
		Body b = badBodies.get(i);
		lettersg.removeValue((Letterg)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectBadCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("hithit").play();
	}
	badBodies.clear();
	
	Array<Body> badBodiesu = cl.getBaduBodies();
	for(int i = 0; i < badBodiesu.size; i++){
		Body b = badBodiesu.get(i);
		lettersu.removeValue((Letteru)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectBadCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("hithit").play();
	}
	badBodiesu.clear();
	
	Array<Body> badBodiesc = cl.getBadcBodies();
	for(int i = 0; i < badBodiesc.size; i++){
		Body b = badBodiesc.get(i);
		lettersc.removeValue((Letterc)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectBadCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("hithit").play();
	}
	badBodiesc.clear();
	
	Array<Body> badBodiesa = cl.getBadaBodies();
	for(int i = 0; i < badBodiesa.size; i++){
		Body b = badBodiesa.get(i);
		letters.removeValue((Lettera)b.getUserData(), true );//double equal
		world.destroyBody(b);
		player.collectBadCrystal();//how to remove bodies put them in list and after
		Game.res.getSound("hithit").play();
	}
	badBodiesa.clear();
	
	

	
	
	
	player.update(dt);
	strip.update(dt);

	
	for(int i = 0; i < letters.size; i++){
		letters.get(i).update(dt);
		
	}
	for(int i = 0; i < lettersg.size; i++){
		lettersg.get(i).update(dt);
		
	}
	for(int i = 0; i < lettersc.size; i++){
		lettersc.get(i).update(dt);
		
	}
	for(int i = 0; i < lettersu.size; i++){
		lettersu.get(i).update(dt);
		
	}
	
	// check player win
		if(player.getBody().getPosition().x * PPM > tileMapWidth * tileSize) {
				Game.res.getSound("levelselect").play();
				gsm.setState(GameStateManager.MENU);
			}
			
			// check player failed
			if(player.getBody().getPosition().y < 0) {
				Game.res.getSound("hit").play();
				gsm.setState(GameStateManager.MENU);
			}
			if(player.getBody().getLinearVelocity().x < 0.001f) {
				Game.res.getSound("hit").play();
				gsm.setState(GameStateManager.MENU);
			}
			if(cl.isPlayerDead()) {
				Game.res.getSound("hit").play();
				gsm.setState(GameStateManager.MENU);
			}
			
		
	
}

public void render() {
	
	//set camera to follow player
	cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
	cam.position.set(
	player.getPosition().x * PPM + Game.V_WIDTH/4,
			Game.V_HEIGHT/2, 0
			);
	cam.update();
	
	

	// draw bgs
	sb.setProjectionMatrix(hudCam.combined);
	bg.render(sb);
/*	for(int i = 0; i < backgrounds.length; i++) {
		backgrounds[i].render(sb);
	}*/
	
	// draw tilemap
	tmRenderer.setView(cam);
	tmRenderer.render();
	
	//draw player
		sb.setProjectionMatrix(cam.combined);
		player.render(sb);
	
	
	//draw letters
	for(int i = 0; i < letters.size; i++){
		letters.get(i).render(sb);
	}
	
	for(int i = 0; i < lettersg.size; i++){
		lettersg.get(i).render(sb);
	}
	for(int i = 0; i < lettersc.size; i++){
		lettersc.get(i).render(sb);
	}
	for(int i = 0; i < lettersu.size; i++){
		lettersu.get(i).render(sb);
	}
	
	
	//draw hud
	sb.setProjectionMatrix(hudCam.combined);
	hud.render(sb);
	strip.render(sb);
	
	// draw box2d world
	if(debug) {
		b2dCam.setPosition(player.getPosition().x + Game.V_WIDTH / 4 / PPM, Game.V_HEIGHT / 2 / PPM);
		b2dCam.update();
		b2dr.render(world, b2dCam.combined);
	}
	
	

	
	
}

public void dispose() {}
}

