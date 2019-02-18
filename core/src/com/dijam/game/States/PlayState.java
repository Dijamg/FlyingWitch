package com.dijam.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dijam.game.Sprites.Buff;
import com.dijam.game.Sprites.SpecialMob;
import com.dijam.game.Sprites.Witch;
import com.dijam.game.Sprites.Bat;
import com.dijam.game.FlyingWitch;
import java.util.ArrayList;
/*Play state of the program. This state contains the game loop etc.
* After you have came to the game state, you cannot get back to menu state, however you can pause game,
* but it does not take you to an another state.*/
public class PlayState extends State {
    Preferences prefs = Gdx.app.getPreferences("My Preferences");   //Stores the high score.
    private float timePassed;
    private float buffTimer = 0;
    private Witch witch;
    private Bat bat;
    private Buff buff;
    //3 next declarations are for the arraylists where the bats,birds and buffs will be stored.
    private ArrayList<Bat> bats;
    private ArrayList<SpecialMob> firebirds;
    private ArrayList<Buff> buffIcon;
    private Texture background;
    private Texture redLayer;
    private Texture soil;
    private Texture gameOverScreen;
    private Texture pauseBtn;
    private Texture pauseScreen;
    private Texture resumeBtn;
    private Texture muteBtn;
    private Texture unmuteBtn;
    private Texture record;
    private Texture ground;
    private Texture ready;
    private Texture tap;
    private Texture playBtn;
    private Texture scoreBtn;
    //5 next rectangles will work as the "hitbox" of the buttons.
    private Rectangle resume;
    private Rectangle mute;
    private Rectangle pause;
    private Rectangle play;
    private Rectangle scoreButton;
    // Tells if the score is better than your current highscore.
    private boolean newBest = false;
    // 2 next floats are the coordinates of the moving ground.
    private float soil1 = 0;
    private float soil2 = FlyingWitch.GAME_WORLD_WIDTH;
    /* Score increments when the bat has flied out of the screen.
    * Iterator is almost as same as score, but it increments also when a buff or firebird has been brought into the game.*/
    private int score = 0;
    private int iterator = 0;
    private int level = 1;
    private float batSpeed = -6f;
    private boolean buffed = false;
    private boolean gameOver = false;
    private boolean started = false;
    private Sound jumpSound;
    private Sound birdSound;
    private Sound buffEffectSound;
    private Sound buffPickUpSound;


    /*Initializes needed textures, variables, sounds and rectangles
    * which work as buttons. A texture will be drawn on the screen and
    * a rectangle will be put beneath it to register click on the them.
    * All the objects in the game f.e the witch, bats and birds are stored in an arraylist.*/
    public PlayState(GameStateManager gsm) {
        super(gsm);
        soil = new Texture("soil.png");
        background = new Texture("BGG.png");
        redLayer = new Texture("red.png");
        pauseScreen = new Texture("pause.png");
        pauseBtn = new Texture("PauseButton.png");
        resumeBtn = new Texture("ResumeButton.png");
        muteBtn = new Texture("SoundButton.png");
        unmuteBtn = new Texture("MutedButton.png");
        gameOverScreen = new Texture("scoreboard.png");
        record = new Texture("record.png");
        ground  = new Texture("ground.png");
        ready = new Texture("ready.png");
        tap = new Texture("tap1.png");
        playBtn = new Texture("PlayButton.png");
        scoreBtn = new Texture("Highscore.png");
        scoreButton = new Rectangle(FlyingWitch.GAME_WORLD_WIDTH/2, FlyingWitch.GAME_WORLD_HEIGHT/2 - FlyingWitch.heightRatio * 115, FlyingWitch.widthRatio * 160, FlyingWitch.heightRatio * 115);
        play = new Rectangle(FlyingWitch.GAME_WORLD_WIDTH/2 - (FlyingWitch.widthRatio * 160 + FlyingWitch.widthRatio * 20), FlyingWitch.GAME_WORLD_HEIGHT/2 - FlyingWitch.heightRatio * 115, FlyingWitch.widthRatio * 160, FlyingWitch.heightRatio * 115);
        pause = new Rectangle(FlyingWitch.GAME_WORLD_WIDTH - FlyingWitch.widthRatio * 40, FlyingWitch.heightRatio * 25, FlyingWitch.widthRatio * 40, FlyingWitch.heightRatio * 25);
        resume = new Rectangle(FlyingWitch.GAME_WORLD_WIDTH/2 - FlyingWitch.widthRatio * 120, FlyingWitch.GAME_WORLD_HEIGHT/2 - FlyingWitch.heightRatio * 100, FlyingWitch.widthRatio * 100, FlyingWitch.heightRatio * 100);
        mute = new Rectangle(FlyingWitch.GAME_WORLD_WIDTH/2, FlyingWitch.GAME_WORLD_HEIGHT/2 - FlyingWitch.heightRatio * 100, FlyingWitch.widthRatio * 100, FlyingWitch.heightRatio * 100);
        witch = new Witch();
        buff = new Buff();
        bats = new ArrayList<Bat>();
        firebirds = new ArrayList<SpecialMob>();
        buffIcon = new ArrayList<Buff>();
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("JumpSound.mp3"));
        birdSound = Gdx.audio.newSound(Gdx.files.internal("BirdSound.mp3"));
        buffEffectSound = Gdx.audio.newSound(Gdx.files.internal("BuffEffect.mp3"));
        buffPickUpSound = Gdx.audio.newSound(Gdx.files.internal("PickBuff.mp3"));
        addBat();
        addBat();
        addBat();
        addBat();
        addBat();
    }
    // Following 3 methods are responsible for adding bats,fire birds and buffs into the game.
    private void addBat() {
        bats.add(new Bat(batSpeed));
    }
    // Number of added birds depends on the current level.
    private void addFirebird() {
        if(level == 1) {
            firebirds.add(new SpecialMob());
        } else if (level == 2) {
            firebirds.add(new SpecialMob());
            firebirds.add(new SpecialMob());
        } else {
            firebirds.add(new SpecialMob());
            firebirds.add(new SpecialMob());
            firebirds.add(new SpecialMob());
        }
    }

    private void addBuff(){
        buffIcon.add(new Buff());
    }

    /* If you tap/click while on gameover screen, new game will be started.
     * if you tap/click pause button, game will be paused
     * if you tap/click while on "get ready" screen, game will start.
     * if you tap/click resume button while on pause screen, game will resume
     * if you tap/click mute/unmute button on pause screen, sound will be muted/unmuted
     * Otherwise the witch will jump*/
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            if (gameOver) {
                if(play.contains(Gdx.input.getX(), FlyingWitch.GAME_WORLD_HEIGHT - Gdx.input.getY())){
                    gsm.set(new PlayState(gsm));
                }
            } else if(!started){
                started = true;
            } else if (FlyingWitch.gamePaused) {
                if (resume.contains(Gdx.input.getX(), FlyingWitch.GAME_WORLD_HEIGHT - Gdx.input.getY())) {
                    FlyingWitch.gamePaused = false;
                    witch.jump();
                } else if (mute.contains(Gdx.input.getX(), FlyingWitch.GAME_WORLD_HEIGHT - Gdx.input.getY())) {
                    if(FlyingWitch.muted){
                        FlyingWitch.muted = false;
                    } else {
                        FlyingWitch.muted = true;
                    }
                }
            } else if (pause.contains(Gdx.input.getX(), Gdx.input.getY() + pause.height)) {
                FlyingWitch.gamePaused = true;
            } else {
                witch.jump();
                if(!FlyingWitch.muted){
                    jumpSound.play();
                }
            }
        }
    }

    // PlayState's main loop
    @Override
    public void update(float dt) {
        handleInput();
        if(!gameOver && !FlyingWitch.gamePaused && started) {
            witch.update(dt);
            // If you crash with a bat while not buffed, you will lose. updates highscore if necessary.
            for (Bat bat : bats) {
                bat.update(dt);
                if (bat.getHitbox().overlaps(witch.getHitbox()) && !buffed) {
                    if (score > prefs.getInteger("best")) {
                        prefs.putInteger("best", score);
                        prefs.flush();
                        newBest = true;
                    }
                    gameOver = true;
                }
            }
            // If bat goes out of screen or you crash with a bat while buffed, you'll get a score and bat will be removed. New bat will be added.
            for (int i = 0; i < bats.size(); i++) {
                bat = bats.get(i);
                if ((bat.getPosition().x < -bat.getBatWidth()) || (bat.getHitbox().overlaps(witch.getHitbox()) && buffed)) {
                    bats.remove(bat);
                    score++;
                    iterator++;
                    if (bats.size() < 10) {
                        addBat();
                    }
                    if(score == 100 || score == 200) {
                        level++;
                    }
                }
            }
            // Adds firebird into the game and plays a sound.
            if(iterator % 25 ==0 && iterator > 0 ) {
                addFirebird();
                if(!FlyingWitch.muted) {
                    birdSound.play();
                }
                iterator++;
                if(batSpeed >= -9f){
                    batSpeed--;
                }
            }
            //Adds buff into the game.
            if(iterator % 50 == 1 && iterator > 50){
                addBuff();
                iterator++;
            }
            // If you crash with a buff icon, you will get a buff.
            for(int i=0; i<buffIcon.size(); i++){
                Buff buff = buffIcon.get(i);
                buff.update(dt);
                if(buff.getHitbox().overlaps(witch.getHitbox())){
                    buffed = true;
                    buffIcon.remove(buff);
                    if(!FlyingWitch.muted) {
                        buffPickUpSound.play();
                        buffEffectSound.play(2.0f);
                    }

                }
            }
            // Makes the buff last 10 seconds.
            if(buffTimer > 10){
                buffTimer = 0;
                buffed = false;
            }
            // If you crash with a firebird, you will lose. Highscore will be updated if necessary.
            for(SpecialMob firebird : firebirds) {
                firebird.update(dt);
                if(firebird.getHitbox().overlaps(witch.getHitbox())) {
                    if (score > prefs.getInteger("best")) {
                        prefs.putInteger("best", score);
                        prefs.flush();
                        newBest = true;
                    }
                    gameOver = true;
                }
            }
            //If firebird goes out of screen, it will be removed.
            for(int i=0; i<firebirds.size(); i++) {
                SpecialMob firebird = firebirds.get(i);
                if(firebird.getPosition().x < -firebird.getBirdWidth() ) {
                    firebirds.remove(firebird);
                }
            }
        }
        if(!FlyingWitch.gamePaused && !gameOver){
            // Makes the ground move.
            soil1 -= FlyingWitch.GAME_WORLD_WIDTH/200;
            soil2 -= FlyingWitch.GAME_WORLD_WIDTH/200;
            if (soil1 < (-FlyingWitch.GAME_WORLD_WIDTH)) {
                soil1 = soil2 + FlyingWitch.GAME_WORLD_WIDTH;
            } else if (soil2 < (-FlyingWitch.GAME_WORLD_WIDTH)) {
                soil2 = soil1 + FlyingWitch.GAME_WORLD_WIDTH;
            }
        }
    }

    // Draws all the things to the screen. SpriteBatch draws Textures(images) and BitmapFont draws fonts and writing(Score etc).
    @Override
    public void render(SpriteBatch sb, BitmapFont bf) {
        sb.begin();
        bf.getData().setScale(FlyingWitch.GAME_WORLD_WIDTH/FlyingWitch.WIDTH, FlyingWitch.GAME_WORLD_HEIGHT/FlyingWitch.HEIGHT);
        timePassed += Gdx.graphics.getDeltaTime();
        if(buffed && !FlyingWitch.gamePaused){
            buffTimer += Gdx.graphics.getDeltaTime();
        }
        sb.draw(background, 0, 50 * FlyingWitch.heightRatio, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
        sb.draw(background, 0, 50 * FlyingWitch.heightRatio, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
        sb.draw(soil, soil1, 50 * FlyingWitch.heightRatio, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
        sb.draw(soil, soil2, 50 * FlyingWitch.heightRatio, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
        sb.draw(ground,0,0, FlyingWitch.GAME_WORLD_WIDTH, 50 * FlyingWitch.heightRatio);
        sb.draw((TextureRegion) witch.getAnimation(buffed).getKeyFrame(timePassed, true), witch.getPosition().x, witch.getPosition().y, witch.getWitchWidth(), witch.getWitchHeight());
        if(!started){
            sb.draw(ready,0,0,FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
            bf.draw(sb,"Tap screen to start!", FlyingWitch.GAME_WORLD_WIDTH/2 - FlyingWitch.widthRatio * 75, FlyingWitch.GAME_WORLD_HEIGHT/2);
            sb.draw(tap, FlyingWitch.GAME_WORLD_WIDTH/2 - (FlyingWitch.widthRatio * tap.getWidth()), FlyingWitch.GAME_WORLD_HEIGHT/2 - ((tap.getHeight() + 20) * FlyingWitch.heightRatio) , tap.getWidth()* FlyingWitch.widthRatio, tap.getHeight() * FlyingWitch.heightRatio );
        }
        if(started) {
            //Draws bats.
            for (Bat bat : bats) {
                sb.draw((TextureRegion) bat.getBatAnimation().getKeyFrame(timePassed, true), bat.getPosition().x, bat.getPosition().y, bat.getBatWidth(), bat.getBatHeight());
            }
            //Draws firebirds
            for (SpecialMob firebird : firebirds) {
                sb.draw((TextureRegion) firebird.getAnimation().getKeyFrame(timePassed, true), firebird.getPosition().x, firebird.getPosition().y, firebird.getBirdWidth(), firebird.getBirdHeight());
            }
            // Draws a buff.
            for (Buff buff : buffIcon) {
                sb.draw(buff.getBuffTexture(), buff.getPosition().x, buff.getPosition().y, buff.getBuffSize(), buff.getBuffSize());
            }
            //Draws this while you are buffed.
            if (buffed) {
                bf.getData().setScale(FlyingWitch.GAME_WORLD_WIDTH / FlyingWitch.WIDTH * 0.75f, FlyingWitch.GAME_WORLD_HEIGHT / FlyingWitch.HEIGHT * 0.75f);
                bf.draw(sb, "Buff", FlyingWitch.GAME_WORLD_WIDTH / 2 - buff.getBuffText().width / 2, FlyingWitch.GAME_WORLD_HEIGHT * 3 / 4 + buff.getBuffSize() + buff.getBuffText().height * 3 / 2);
                sb.draw(buff.getBuffTexture(), FlyingWitch.GAME_WORLD_WIDTH / 2 - buff.getBuffSize() / 2, FlyingWitch.GAME_WORLD_HEIGHT * 3 / 4, buff.getBuffSize(), buff.getBuffSize());
                bf.draw(sb, "Immune to bats", FlyingWitch.GAME_WORLD_WIDTH / 2 - buff.getBuffInfo().width / 2, FlyingWitch.GAME_WORLD_HEIGHT * 3 / 4);
                bf.draw(sb, String.format(java.util.Locale.US, "%.1f", buffTimer) + "/10.0", FlyingWitch.GAME_WORLD_WIDTH / 2 - buff.getBuffRemainingText().width / 2, FlyingWitch.GAME_WORLD_HEIGHT * 3 / 4 - FlyingWitch.GAME_WORLD_HEIGHT / FlyingWitch.HEIGHT * 20);
                sb.draw(redLayer, 0, 0, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
                bf.getData().setScale(FlyingWitch.GAME_WORLD_WIDTH / FlyingWitch.WIDTH, FlyingWitch.GAME_WORLD_HEIGHT / FlyingWitch.HEIGHT);
            }
            //Draws the score and the pause button.
            bf.draw(sb, "Score: " + Integer.toString(score), FlyingWitch.GAME_WORLD_HEIGHT / FlyingWitch.HEIGHT * 10, FlyingWitch.GAME_WORLD_HEIGHT - FlyingWitch.GAME_WORLD_HEIGHT / FlyingWitch.HEIGHT * 10);
            if (!FlyingWitch.gamePaused && !gameOver) {
                sb.draw(pauseBtn, pause.x, FlyingWitch.GAME_WORLD_HEIGHT - pause.y, pause.width, pause.height);
            }
            //Draws the pause screen with resume and mute/unmute button.
            if (FlyingWitch.gamePaused) {
                sb.draw(pauseScreen, 0, 0, FlyingWitch.GAME_WORLD_WIDTH, FlyingWitch.GAME_WORLD_HEIGHT);
                sb.draw(resumeBtn, resume.x, resume.y, resume.width, resume.height);
                if (FlyingWitch.muted) {
                    sb.draw(unmuteBtn, mute.x, mute.y, mute.width, mute.height);
                } else {
                    sb.draw(muteBtn, mute.x, mute.y, mute.width, mute.height);
                }
            }
            //Draws the gameover screen. Shows the score and tells the player if a new bighscore has been achieved.
            if (gameOver) {
                if (newBest) {
                    sb.draw(record, FlyingWitch.widthRatio * 150, FlyingWitch.heightRatio * 200, record.getWidth() * FlyingWitch.widthRatio, record.getHeight() * FlyingWitch.heightRatio);
                } else {
                    sb.draw(gameOverScreen, FlyingWitch.widthRatio * 150, FlyingWitch.heightRatio * 200, gameOverScreen.getWidth() * FlyingWitch.widthRatio, gameOverScreen.getHeight() * FlyingWitch.heightRatio);
                }
                bf.draw(sb, Integer.toString(score), FlyingWitch.GAME_WORLD_WIDTH / 2 + FlyingWitch.widthRatio * 50, FlyingWitch.heightRatio * 350);
                bf.draw(sb, Integer.toString(prefs.getInteger("best")), FlyingWitch.GAME_WORLD_WIDTH / 2 + FlyingWitch.widthRatio * 50, FlyingWitch.heightRatio * 275);
                sb.draw(playBtn, play.x, play.y, play.width, play.height);
                sb.draw(scoreBtn, scoreButton.x, scoreButton.y, scoreButton.width, scoreButton.height);
            }
        }
        sb.end();
    }

    // Disposes of all the sounds and textures when they are no longer needed.
    @Override
    public void dispose() {
        witch.dispose();
        for(Bat bat : bats){
            bat.dispose();
        }
        for(SpecialMob firebird : firebirds){
            firebird.dispose();
        }
        for(Buff buff : buffIcon){
            buff.dispose();
        }
        soil.dispose();
        background.dispose();
        redLayer.dispose();
        pauseScreen.dispose();
        pauseBtn.dispose();
        resumeBtn.dispose();
        muteBtn.dispose();
        unmuteBtn.dispose();
        gameOverScreen.dispose();
        record.dispose();
        background.dispose();
        buffPickUpSound.dispose();
        buffEffectSound.dispose();
        jumpSound.dispose();
        birdSound.dispose();
        ready.dispose();
        ground.dispose();
        playBtn.dispose();
        scoreBtn.dispose();
        System.out.println("Playstate has been disposed");
    }
}

