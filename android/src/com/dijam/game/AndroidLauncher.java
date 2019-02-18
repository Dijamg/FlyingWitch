package com.dijam.game;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;


/*Main launcher of the Android app.*/
public class AndroidLauncher extends AndroidApplication{
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new FlyingWitch(), config);
	}

	@Override
	// The game will be put on pause when the game disappeares from screen (minimized).
	protected void onStop(){
		super.onStop();
		FlyingWitch.gamePaused = true;
	}
}
