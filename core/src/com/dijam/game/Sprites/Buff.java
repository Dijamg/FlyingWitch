package com.dijam.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.dijam.game.FlyingWitch;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;

/*The class of the ingame buffs.
* Class is almost identical to the bat class, except this class also
* stores data of the texts relating to the buff, f.e buff info, time
* of the buff remaining etc.
* Buff texts are stored here, so we can get a its size which makes it
* possible to scale them to the size of the device screen.*/
public class Buff {
    private final int groundHeight = 20 + 50;
    private final float SPEED = -FlyingWitch.GAME_WORLD_WIDTH/160;
    private final int buffSize = 45;
    private float xCoordinate;
    private float yCoordinate;
    private Random random;
    private Texture buffIcon;
    private Vector3 position;
    private Rectangle hitbox;
    private BitmapFont font;
    private GlyphLayout buffText;
    private GlyphLayout buffInfo;
    private GlyphLayout buffRemainingText;

    public Buff(){
        random = new Random();
        buffIcon = new Texture("BuffIcon.png");
        xCoordinate = FlyingWitch.GAME_WORLD_WIDTH * 2;
        yCoordinate = Math.max(getGroundHeight(),random.nextInt((int)FlyingWitch.GAME_WORLD_HEIGHT - (int)getBuffSize()));
        position = new Vector3(xCoordinate,yCoordinate,0);
        hitbox = new Rectangle((int)position.x, (int)position.y, getBuffSize(), getBuffSize());
        font = new BitmapFont();
        font.getData().setScale(FlyingWitch.widthRatio * 0.75f, FlyingWitch.heightRatio * 0.75f);
        buffText = new GlyphLayout(font, "Buff");
        buffInfo = new GlyphLayout(font, "Immune to bats.");
        buffRemainingText = new GlyphLayout(font, "0.00/10.0");

    }

    public void update(float dt) {
        position.add(SPEED,0,0);
        hitbox.set((int)getPosition().x, (int)getPosition().y, getBuffSize(), getBuffSize());
    }

    public Texture getBuffTexture() {
        return buffIcon;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public float getBuffSize(){
        return (float)buffSize/FlyingWitch.WIDTH * FlyingWitch.GAME_WORLD_WIDTH;
    }

    public float getGroundHeight(){
        return (float)groundHeight * FlyingWitch.heightRatio;
    }

    public GlyphLayout getBuffText(){
        return buffText;
    }

    public GlyphLayout getBuffInfo(){
        return buffInfo;
    }

    public GlyphLayout getBuffRemainingText(){
        return buffRemainingText;
    }

    public void dispose() {
        buffIcon.dispose();
    }

}
