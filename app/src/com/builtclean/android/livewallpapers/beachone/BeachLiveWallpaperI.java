package com.builtclean.android.livewallpapers.beachone;

import com.builtclean.android.livewallpapers.beachone.R;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public class BeachLiveWallpaperI extends WallpaperService {

	private final Handler mHandler = new Handler();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		return new BeachEngine();
	}

	class BeachEngine extends Engine {

		public int offsetX = 0;
		public int offsetY = 0;
		public int height;
		public int width;
		public int visibleWidth;

		public int currentImage = 0;

		public String[] images = { "image_028", "image_029", "image_030",
				"image_031", "image_032", "image_033", "image_034",
				"image_035", "image_036", "image_037", "image_038",
				"image_039", "image_040", "image_041", "image_042",
				"image_043", "image_044", "image_045", "image_046",
				"image_047", "image_048", "image_049", "image_050",
				"image_051", "image_052", "image_053", "image_054",
				"image_055", "image_056", "image_057", "image_058",
				"image_059", "image_060", "image_061", "image_062",
				"image_063", "image_064", "image_065", "image_066",
				"image_067", "image_068", "image_069", "image_070",
				"image_071", "image_072", "image_073", "image_074",
				"image_075", "image_076", "image_077", "image_078",
				"image_079", "image_080", "image_081", "image_082",
				"image_083", "image_084", "image_085", "image_086",
				"image_087", "image_088", "image_089", "image_090",
				"image_091", "image_092", "image_093", "image_094",
				"image_095", "image_096", "image_097", "image_098",
				"image_099", "image_100", "image_101", "image_102",
				"image_103", "image_104", "image_105", "image_106",
				"image_107", "image_108", "image_109", "image_110",
				"image_111", "image_112", "image_113", "image_114",
				"image_115", "image_116", "image_117", "image_118",
				"image_119", "image_120", "image_121", "image_122",
				"image_123", "image_124", "image_125", "image_126",
				"image_127", "image_128", "image_129", "image_130",
				"image_131", "image_132", "image_133", "image_134",
				"image_135", "image_136", "image_137", "image_138",
				"image_139", "image_140", "image_141", "image_142",
				"image_143", "image_144", "image_145", "image_146",
				"image_147", "image_148", "image_149", "image_150",
				"image_151", "image_152", "image_153", "image_154",
				"image_155", "image_156", "image_157", "image_158",
				"image_159", "image_160", "image_161", "image_162",
				"image_163", "image_164", "image_165", "image_166",
				"image_167", "image_168", "image_169", "image_170",
				"image_171", "image_172", "image_173", "image_174",
				"image_175", "image_176", "image_177", "image_178",
				"image_179", "image_180", "image_181", "image_182",
				"image_183", "image_184", "image_185" };

		private final Runnable mDrawBeach = new Runnable() {
			public void run() {
				drawFrame();
			}
		};
		private boolean mVisible;

		private MediaPlayer beachPlayer;

		BeachEngine() {
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			setTouchEventsEnabled(true);

			beachPlayer = MediaPlayer.create(getApplicationContext(),
					R.raw.beach);
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mDrawBeach);

			if (beachPlayer != null) {
				beachPlayer.release();
			}
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(mDrawBeach);
			}
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mDrawBeach);
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {

			this.height = height;
			if (this.isPreview()) {
				this.width = width;
			} else {
				this.width = 4 * width;
			}
			this.visibleWidth = width;

			drawFrame();

			super.onSurfaceChanged(holder, format, width, height);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset,
				float xOffsetStep, float yOffsetStep, int xPixelOffset,
				int yPixelOffset) {

			this.offsetX = xPixelOffset;
			this.offsetY = yPixelOffset;

			drawFrame();

			super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep,
					xPixelOffset, yPixelOffset);
		}

		@Override
		public Bundle onCommand(String action, int x, int y, int z,
				Bundle extras, boolean resultRequested) {

			Bundle bundle = super.onCommand(action, x, y, z, extras,
					resultRequested);

			if (action.equals("android.wallpaper.tap")) {
				playBeachSound();
			}

			return bundle;
		}

		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					drawBeach(c);
				}
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			mHandler.removeCallbacks(mDrawBeach);
			if (mVisible) {
				mHandler.post(mDrawBeach);
			}
		}

		void drawBeach(Canvas c) {

			Resources res = getResources();

			if (++currentImage == images.length) {
				currentImage = 0;
			}

			c.drawBitmap(BitmapFactory.decodeResource(res, res.getIdentifier(
					images[currentImage], "drawable",
					"com.builtclean.android.livewallpapers.beachone")),
					this.offsetX, this.offsetY, null);
		}

		void playBeachSound() {
			beachPlayer.seekTo(0);
			beachPlayer.start();
		}
	}
}