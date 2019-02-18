package com.dijam.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.dijam.game.FlyingWitch;

import java.util.Random;
import com.badlogic.gdx.math.Rectangle;

/**
 * This class determines the properties of the bats in the game.
 * Bats have a location, hitbox, speed, size and a look(animation),
 */

public class Bat {
    private Random random;
    private final float groundHeight = 20 + 50;
    private final float BATHEIGHT = 25;
    private final float BATWIDTH = 40;
    private float xCoordinate;
    private float yCoordinate;
    private float speed=0;
    private TextureAtlas batAtlas;
    private Animation batAnimation;
    private Vector3 position;
    private Rectangle hitbox;

    public Bat(float batSpeed){
        random = new Random();
        speed = batSpeed * FlyingWitch.widthRatio;
        // 2 next lines assign a random starting position for the bat.
        xCoordinate = random.nextInt((int)FlyingWitch.GAME_WORLD_WIDTH/2) + FlyingWitch.GAME_WORLD_WIDTH;
        yCoordinate = Math.max(getGroundHeight(),random.nextInt((int)FlyingWitch.GAME_WORLD_HEIGHT - (int)getBatHeight()));
        position = new Vector3(xCoordinate, yCoordinate,0);
        hitbox = new Rectangle(position.x, position.y, getBatWidth(), getBatHeight() );
        batAtlas = new TextureAtlas("bat.atlas");
        batAnimation = new Animation<TextureRegion>(1/8f,batAtlas.getRegions());
    }
    // Bat's location's x coordinate will change by the amount of speed every frame.
    public void update(float dt) {
        position.add(speed,0,0);
        hitbox.set((int)getPosition().x, (int)getPosition().y, getBatWidth(), getBatHeight());
    }

    public Animation getBatAnimation(){
        return batAnimation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getBatWidth(){
        return (float)BATWIDTH * FlyingWitch.widthRatio;
    }

    public float getGroundHeight(){
        return (float)groundHeight * FlyingWitch.heightRatio;
    }

    public float getBatHeight(){
        return (float)BATHEIGHT * FlyingWitch.heightRatio;
    }

    public void dispose() {
        batAtlas.dispose();
    }

}
