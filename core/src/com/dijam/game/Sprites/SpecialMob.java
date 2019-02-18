package com.dijam.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.dijam.game.FlyingWitch;

import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

/*This class determines the properties of the firebirds in the game.
* This class works the same as the bat class.*/

public class SpecialMob {
    private final int BIRDHEIGHT = 40;
    private final int BIRDWIDTH = 65;
    private final float groundHeight = 20 + 50;
    private TextureAtlas firebird;
    private Animation animation;
    private Vector3 position;
    private Rectangle hitbox;
    private Random random;
    private float speed = -FlyingWitch.GAME_WORLD_WIDTH/80;
    private float xCoordinate;
    private float yCoordinate;

    public SpecialMob() {
        firebird = new TextureAtlas("birdy.atlas");
        animation = new Animation<TextureRegion>(1/12f, firebird.getRegions());
        random = new Random();
        xCoordinate = random.nextInt((int)FlyingWitch.GAME_WORLD_WIDTH) + FlyingWitch.GAME_WORLD_WIDTH  * 2;
        yCoordinate = Math.max(getGroundHeight(),random.nextInt((int)FlyingWitch.GAME_WORLD_HEIGHT - (int)getBirdHeight()));
        position = new Vector3(xCoordinate, yCoordinate,0);
        hitbox = new Rectangle((int)position.x, (int)position.y, getBirdWidth(), getBirdHeight());
    }

    public void update(float dt) {
        position.add(speed, 0, 0);
        hitbox.set(position.x, position.y , getBirdWidth(), getBirdHeight());

    }


    public Animation getAnimation() {
        return animation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getBirdWidth() {
        return (float)BIRDWIDTH * FlyingWitch.widthRatio;
    }

    public float getBirdHeight() {
        return (float)BIRDHEIGHT * FlyingWitch.heightRatio;
    }

    public float getGroundHeight(){
        return (float)groundHeight * FlyingWitch.heightRatio;
    }

    public void  dispose()
    {
        firebird.dispose();
    }
}


