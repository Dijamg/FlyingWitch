package com.dijam.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.dijam.game.FlyingWitch;


/*This is the class of the witch character in the game.
* This class doesn't differ much from the bat and specialmob class, but witch's movement is
* different to the other 2.
* The witch has 2 looks, depending of it being buffed or not.
* The width and heigthRatio variables scale the objects according to the screen size of the device.
*/

public class Witch {
    private final float GRAVITY = -FlyingWitch.GAME_WORLD_HEIGHT/20;
    private final float witchHeight = 75;
    private final float witchWidth = 100;
    private final float groundHeight = 20+50;
    private final float hitboxHeight = getWitchHeight() - 10 * FlyingWitch.heightRatio;
    private final float hitboxWidth = getWitchWidth() - 65*FlyingWitch.widthRatio;
    private TextureAtlas witchAtlas;
    private TextureAtlas buffAtlas;
    private Animation witchAnimation;
    private Animation buffAnimation;
    private Rectangle hitbox;
    private Vector3 position;
    private Vector3 velocity;

    public Witch() {
        witchAtlas = new TextureAtlas("Animation.atlas");
        buffAtlas = new TextureAtlas("buff.atlas");
        witchAnimation = new Animation<TextureRegion>(1/12f, witchAtlas.getRegions());
        buffAnimation = new Animation<TextureRegion>(1/12f, buffAtlas.getRegions());
        position = new Vector3(FlyingWitch.GAME_WORLD_HEIGHT/8,FlyingWitch.GAME_WORLD_WIDTH/2 - FlyingWitch.GAME_WORLD_WIDTH/8,0);
        velocity = new Vector3(0,0,0);
        hitbox = new Rectangle((int)position.x + 20 * FlyingWitch.widthRatio,(int)position.y,hitboxWidth,hitboxHeight);

    }
    /* The witch accelerates towards the ground.*/
    public void update(float dt) {
        if(position.y > 0) {
            velocity.add(0,GRAVITY,0);
        }
        velocity.scl(dt);     // Scales velocity by change in time.
        position.add(0,velocity.y,0);
        // next 2 if statements stops the witch from going out of the screen.
        if (position.y > FlyingWitch.GAME_WORLD_HEIGHT - getWitchHeight()) {
            position.y = FlyingWitch.GAME_WORLD_HEIGHT - getWitchHeight();
        }
        if (position.y < getGroundHeight()) {
            position.y = getGroundHeight();
        }
        // Changes the location of the hitbox to the location of the witch.
        hitbox.set((int)getPosition().x + 20*FlyingWitch.widthRatio, (int)getPosition().y, hitboxWidth,hitboxHeight);
        // Reverses what we scaled previously.
        velocity.scl(1/dt);
    }

    public void jump() {
        velocity.y = 300 * FlyingWitch.heightRatio;
    }


    public Animation getAnimation(boolean buffed) {
        if(buffed){
            return buffAnimation;
        }else{
            return witchAnimation;
        }
    }

    public float getWitchWidth (){
        return witchWidth * FlyingWitch.widthRatio;
    }

    public float getWitchHeight(){
        return witchHeight * FlyingWitch.heightRatio;
    }

    public float getGroundHeight(){
        return groundHeight * FlyingWitch.heightRatio;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void dispose() {
        witchAtlas.dispose();
    }
}
