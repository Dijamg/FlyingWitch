package com.dijam.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dijam.game.States.GameStateManager;
import com.dijam.game.States.MenuState;

import java.awt.Event;

public class FlyingWitch extends ApplicationAdapter {
	private SpriteBatch batch;
	private BitmapFont font;
	private GameStateManager gsm;
	public static final int HEIGHT = 400;
	public static final int WIDTH = 800;
	public static float GAME_WORLD_WIDTH ; //Actual width of the Android device
	public static float GAME_WORLD_HEIGHT; //Height of the Android device
	public static float widthRatio;			//Ratio which is used to scale game textures to fit the screen of the device.
	public static float heightRatio;		//Textures are all made for 800x400 resolution, but they will be scaled to take the whole screen of the device.
	public static final String TITLE = "Flying Witch";
	public static boolean gamePaused = false;
	public static  boolean muted = false;

	//Initializes used objects. Called when the program starts.
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		gsm = new GameStateManager();
		GAME_WORLD_HEIGHT = Gdx.graphics.getHeight();
		GAME_WORLD_WIDTH  =  Gdx.graphics.getWidth();
		widthRatio = GAME_WORLD_WIDTH/WIDTH;
		heightRatio = GAME_WORLD_HEIGHT/HEIGHT;
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}


	//Paints the screen. Compare to paintComponent(g) in swing etc.
	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch,font);
	}

	//Gets rid of the objects which will  no longer be used.
	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
