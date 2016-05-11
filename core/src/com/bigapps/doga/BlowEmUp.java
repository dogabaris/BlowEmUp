package com.bigapps.doga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BlowEmUp extends Game implements InputProcessor {
	private Texture yesilBalon;
	private Texture siyahBalon;
	private Texture kirmiziBalon;
	private Texture sariBalon;
	private Texture background;
	private Sound dropSound;
	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private HashMap<String, Rectangle> balonlar = new HashMap<String, Rectangle>();
	private long yesilDropTime;
	private long siyahDropTime;
	private long kirmiziDropTime;
	private long sariDropTime;
	private int random;
	private TouchInfo touch;
	private int touchX;
	private int touchY;

	class TouchInfo {
		public float touchX = -10;
		public float touchY = -10;
		public boolean touched = false;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			touch.touchX = screenX;
			touch.touchY = screenY;
			touch.touched = true;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			touch.touchX = -10;
			touch.touchY = -10;
			touch.touched = false;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void create () {

		Gdx.input.setInputProcessor(this);

		yesilBalon = new Texture(Gdx.files.internal("yesilbalon.png"));
		kirmiziBalon = new Texture(Gdx.files.internal("kirmizibalon.png"));
		siyahBalon = new Texture(Gdx.files.internal("siyahbalon.png"));
		sariBalon = new Texture(Gdx.files.internal("saribalon.png"));
		background = new Texture(Gdx.files.internal("background1.jpg"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();


		balonlar = new HashMap<String, Rectangle>();
		touch = new TouchInfo();

	}
	private void yesilBalonOlustur() {
		Rectangle yesilBalon = new Rectangle();
		yesilBalon.x = MathUtils.random(0, 800-64);
		yesilBalon.y = -64;
		yesilBalon.width = 64;
		yesilBalon.height = 64;
		balonlar.put("yesilbalon",yesilBalon);
		yesilDropTime = TimeUtils.nanoTime();
	}
	private void siyahBalonOlustur() {
		Rectangle siyahBalon = new Rectangle();
		siyahBalon.x = MathUtils.random(0, 800-64);
		siyahBalon.y = -64;
		siyahBalon.width = 64;
		siyahBalon.height = 64;
		balonlar.put("siyahbalon",siyahBalon);
		siyahDropTime = TimeUtils.nanoTime();
	}
	private void kirmiziBalonOlustur() {
		Rectangle kirmiziBalon = new Rectangle();
		kirmiziBalon.x = MathUtils.random(0, 800-64);
		kirmiziBalon.y = -64;
		kirmiziBalon.width = 64;
		kirmiziBalon.height = 64;
		balonlar.put("kirmizibalon",kirmiziBalon);
		kirmiziDropTime = TimeUtils.nanoTime();
	}
	private void sariBalonOlustur() {
		Rectangle sariBalon = new Rectangle();
		sariBalon.x = MathUtils.random(0, 800-64);
		sariBalon.y = MathUtils.random(0, 480-64);
		sariBalon.width = 64;
		sariBalon.height = 64;
		balonlar.put("saribalon",sariBalon);
		sariDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();


		batch.setProjectionMatrix(camera.combined);


		batch.begin();
		//backgroundSprite.draw(batch);
		//batch.draw(background,0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(background,0,0,800, 480);

		for(Map.Entry<String, Rectangle> entry : balonlar.entrySet()) {
			String tur = entry.getKey();
			Rectangle balon = entry.getValue();

			if(tur.equals("yesilbalon"))
				batch.draw(yesilBalon, balon.x, balon.y);
			if(tur.equals("siyahbalon"))
				batch.draw(siyahBalon, balon.x, balon.y);
			if(tur.equals("kirmizibalon"))
				batch.draw(kirmiziBalon, balon.x, balon.y);
			if(tur.equals("saribalon"))
				batch.draw(sariBalon, balon.x, balon.y);

		}
		batch.end();

		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			touchX=(int) touchPos.x;
			touchY=(int) touchPos.y;
			touchDown(touchX,touchY,0, Input.Buttons.LEFT);
		}


		if(TimeUtils.nanoTime() - yesilDropTime > MathUtils.random(1200000000, 1400000000)) yesilBalonOlustur();
		if(TimeUtils.nanoTime() - siyahDropTime > MathUtils.random(1200000000, 1300000000)) siyahBalonOlustur();
		if(TimeUtils.nanoTime() - kirmiziDropTime > MathUtils.random(1200000000, 1500000000)) kirmiziBalonOlustur();
		if(TimeUtils.nanoTime() - sariDropTime > 1000000000) sariBalonOlustur();

		for(Iterator<Map.Entry<String, Rectangle>> it = balonlar.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, Rectangle> entry = it.next();
			Rectangle balon = entry.getValue();

			if(entry.getKey().equals("saribalon")){
				balon.x +=  Gdx.graphics.getDeltaTime();
			}
			if(entry.getKey().equals("kirmizibalon")){
				balon.y += 480 * Gdx.graphics.getDeltaTime();
				random =MathUtils.random(-6, 6);
				if(random>0)
					balon.x += random;
				if(random<=0)
					balon.x += random;
			}
			else if(entry.getKey().equals("yesilbalon")||entry.getKey().equals("siyahbalon")){
				balon.y += 480 * Gdx.graphics.getDeltaTime();
			}
			if(balon.y + 64 < 0)
				it.remove();
			if(balon.contains(touch.touchX,touch.touchY) && touch.touched) {//Balona dokunulduğunda
				dropSound.play();
				it.remove();
				touchUp(touchX,touchY,0, Input.Buttons.LEFT);
				System.out.println(touch.touched +" "+touch.touchX +" "+ touch.touchY);
			}

		}

	}
	@Override
	public void dispose() {
		// dispose of all the native resources
		yesilBalon.dispose();
		siyahBalon.dispose();
		kirmiziBalon.dispose();
		sariBalon.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
