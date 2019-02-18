package com.dijam.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dijam.game.FlyingWitch;

/*Main launcher, nothing special.
* Sets the title and the screen size.*/
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlyingWitch.WIDTH;
		config.height = FlyingWitch.HEIGHT;
		config.title = FlyingWitch.TITLE;
		new LwjglApplication(new FlyingWitch(), config);
	}
}

