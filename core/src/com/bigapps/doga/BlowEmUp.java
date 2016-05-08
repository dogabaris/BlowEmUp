package com.bigapps.doga;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class BlowEmUp extends Game {
	private Texture yesilBalon;
	private Texture siyahBalon;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle bucket;
	//private Map<String,Rectangle> balonlar;
	private HashMap<String, Rectangle> balonlar = new HashMap<String, Rectangle>();
	//private Array<Rectangle> balonlar;
	private long yesilDropTime;
	private long siyahDropTime;
	private int random;

	@Override
	public void create () {
		// load the images for the droplet and the bucket, 64x64 pixels each
		yesilBalon = new Texture(Gdx.files.internal("yesilbalon.png"));
		siyahBalon = new Texture(Gdx.files.internal("siyahbalon.png"));
		bucketImage = new Texture(Gdx.files.internal("kova.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		rainMusic.play();

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();

		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 415; // bottom left corner of the bucket is 20 pixels above the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;

		// create the raindrops array and spawn the first raindrop
		//balonlar = new HashMap<String, Rectangle>();
		balonlar = new HashMap<String, Rectangle>();


		yesilBalonOlustur();
		siyahBalonOlustur();
	}
	private void yesilBalonOlustur() {
		Rectangle yesilBalon = new Rectangle();
		yesilBalon.x = MathUtils.random(0, 800-64);
		yesilBalon.y = 0;
		yesilBalon.width = 64;
		yesilBalon.height = 64;
		balonlar.put("yesilbalon",yesilBalon);
		yesilDropTime = TimeUtils.nanoTime();
	}
	private void siyahBalonOlustur() {
		Rectangle siyahBalon = new Rectangle();
		siyahBalon.x = MathUtils.random(0, 800-64);
		siyahBalon.y = 0;
		siyahBalon.width = 64;
		siyahBalon.height = 64;
		balonlar.put("siyahbalon",siyahBalon);
		siyahDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render () {
		// clear the screen with a dark blue color. The
		// arguments to glClearColor are the red, green
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// tell the camera to update its matrices.
		camera.update();

		// tell the SpriteBatch to render in the
		// coordinate system specified by the camera.
		batch.setProjectionMatrix(camera.combined);

		// begin a new batch and draw the bucket and
		// all drops
		batch.begin();
		batch.draw(bucketImage, bucket.x, bucket.y);

		for(Map.Entry<String, Rectangle> entry : balonlar.entrySet()) {
			String tur = entry.getKey();
			Rectangle balon = entry.getValue();

			if(tur.equals("yesilbalon"))
				batch.draw(yesilBalon, balon.x, balon.y);
			if(tur.equals("siyahbalon"))
				batch.draw(siyahBalon, balon.x, balon.y);


		}
		batch.end();

		// process user input
		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// make sure the bucket stays within the screen bounds
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;

		// check if we need to create a new raindrop
		if(TimeUtils.nanoTime() - yesilDropTime > 1000000000) yesilBalonOlustur();
		if(TimeUtils.nanoTime() - siyahDropTime > 1050000000) siyahBalonOlustur();

		// move the raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we play back
		// a sound effect as well.

		for(Iterator<Map.Entry<String, Rectangle>> it = balonlar.entrySet().iterator(); it.hasNext(); ) {
			Map.Entry<String, Rectangle> entry = it.next();

			String tur = entry.getKey();
			Rectangle balon = entry.getValue();

			balon.y += 480 * Gdx.graphics.getDeltaTime();
			if(balon.y + 64 < 0)
				it.remove();
			if(balon.overlaps(bucket)) {
				dropSound.play();
				it.remove();
			}

		}

	}
	@Override
	public void dispose() {
		// dispose of all the native resources
		yesilBalon.dispose();
		siyahBalon.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}
}
