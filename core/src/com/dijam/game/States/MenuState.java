package com.dijam.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dijam.game.Sprites.Witch;
import com.dijam.game.FlyingWitch;

/*This is the menu state of the program. This is the first state which will be shown upon
* start of the program.*/

public class MenuState extends State {
    private Texture background;
    private Witch witch;
    private Texture soil;
    private Texture playBtn;
    private Texture scoreBtn;
    private Texture name;
    private Texture ground;
    private Rectangle rate;
    private Rectangle play;
    private Rectangle score;
    private float soil1 = 0;
    private float soil2 = FlyingWitch.GAME_WORLD_WIDTH;
    private float timePassed = 0;

    /*Initializing all the needed textures, variables and rectangles.
    * Rectangles work as buttons.*/
    public MenuState(GameStateManager gsm) {
        super(gsm);
        background = new Texture("BGG.png");
        soil = new Texture("soil.png");
        playBtn = new Texture("PlayButton.png");
        scoreBtn = new Texture("Highscore.png");
        name = new Texture("name2.png");
        ground = new Texture("ground.png");
        score = new Rectangle(FlyingWitch.GAME_WORLD_WIDTH/2, FlyingWitch.GAME_WORLD_HEIGHT/2 - FlyingWitch.heightRatio * 100, FlyingWitch.widthRatio * 160, FlyingWitch.heightRatio * 100);
        play = new Rectangle(FlyingWitch.GAME_WORLD_WIDTH/2 - (FlyingWitch.widthRatio * 160 + FlyingWitch.widthRatio * 20), FlyingWitch.GAME_WORLD_HEIGHT/2 - FlyingWitch.heightRatio * 100, FlyingWitch.widthRatio * 160, FlyingWitch.heightRatio * 100);
        rate = new Rectangle(play.x + play.width/2, play.y + play.height + 20 * FlyingWitch.heightRatio, FlyingWitch.widthRatio * 160, FlyingWitch.heightRatio * 57);
        witch = new Witch();
    }


    @Override
    /*If the user presses the play button, the menu state will be removed and play state will be set on
    * its place.*/
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (play.contains(Gdx.input.getX(), FlyingWitch.GAME_WORLD_HEIGHT - Gdx.input.getY())) {
                gsm.set(new PlayState(gsm));
            }
        }
    }

    //Main loop of MenuState. All it does is register user input and move the ground.
    @Override
    public void update(float dt) {
        handleInput();
        soil1 -= FlyingWitch.GAME_WORLD_WIDTH/200;
        soil2 -= FlyingWitch.GAME_WORLD_WIDTH/200;
        if (soil1 < (-FlyingWitch.GAME_WORLD_WIDTH)) {
            soil1 = soil2 + FlyingWitch.GAME_WORLD_WIDTH;
        } else if (soil2 < (-FlyingWitch.GAME_WORLD_WIDTH)) {
            soil2 = soil1 + FlyingWitch.GAME_WORLD_WIDTH;
        }
    }
    // Draws needed things onto the screen.
    @Override
    public void render(SpriteBatch sb, BitmapFont bf) {
        timePassed += Gdx.graphics.getDeltaTime();
        sb.begin();
        sb.draw(background,0,50 * FlyingWitch.heightRatio, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
        sb.draw(ground, 0,0, FlyingWitch.GAME_WORLD_WIDTH, 50 * FlyingWitch.heightRatio);
        sb.draw(soil, soil1, 50 * FlyingWitch.heightRatio, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
        sb.draw(soil, soil2, 50 * FlyingWitch.heightRatio, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
        sb.draw((TextureRegion) witch.getAnimation(false).getKeyFrame(timePassed, true), witch.getPosition().x, witch.getPosition().y,witch.getWitchWidth(),witch.getWitchHeight());
        sb.draw(playBtn, play.x, play.y, play.width, play.height);
        sb.draw(scoreBtn, score.x, score.y, score.width, score.height);
        sb.draw(name, FlyingWitch.widthRatio * 150, FlyingWitch.heightRatio * 300, FlyingWitch.widthRatio * name.getWidth(), FlyingWitch.heightRatio * name.getHeight());
        sb.end();
    }

    // Disposes of all the textures when they are no longer used.
    @Override
    public void dispose() {
        background.dispose();
        soil.dispose();
        witch.dispose();
        playBtn.dispose();
        scoreBtn.dispose();
        name.dispose();
        ground.dispose();
        System.out.println("Menustate has been disposed.");
    }
}