package com.dijam.game.States;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/* Abstract class which defines all the methods which all of the game states must have.
 * Every state must be able to handle users input, update the state (loop), paint the screen
 * and get rid of objects which are no longer being used.*/

public abstract class State {
    protected GameStateManager gsm;

    protected State(GameStateManager gsm){
        this.gsm = gsm;
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb, BitmapFont bf);
    public abstract void dispose();

}

