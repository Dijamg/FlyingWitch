package com.dijam.game.States;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;
/*Class which allows us to handle the game states. With an instance of this class,
* we will be able to add and remove states, paint the state and update the state.
* Only one instance of this class exists in the program and it will be passed to
* other states when it is needed.*/

public class GameStateManager {
    private Stack<State> states;        //Game states are stored in a stack.

    public GameStateManager(){
        states = new Stack<State>();
    }

    public void push(State state){      //Adds a new state to the top of the stack.
        states.push(state);
    }

    public void pop(State state){       //Removes the state on the top.
        states.pop().dispose();
    }

    public void set(State state){       //Removes the state on the top and adds a new one in its place.
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt){       //Updates the stack on the top.
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb, BitmapFont bf){  //Renders the stack on the top.
        states.peek().render(sb,bf);
    }
}

