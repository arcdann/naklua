package com.daniloff.QrReader;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.daniloff.barcodescanner.ScannerActivity;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class QKActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback,
		Camera.AutoFocusCallback {
	private Camera camera;
	private SurfaceView preview;
	private Handler handler = new Handler(Looper.myLooper());
	private boolean isOk;
	private ViewfinderView vfv;
	private Result rawResult;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		FrameLayout fl = new FrameLayout(this);

		preview = new SurfaceView(this);
		SurfaceHolder surfaceHolder = preview.getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		fl.addView(preview, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		vfv = new ViewfinderView(this, null);
		fl.addView(vfv, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		setContentView(fl);
		isOk = false;
	}

	public Camera getCamera() {
		return this.camera;
	}

	@Override
	protected void onResume() {
		super.onResume();
		camera = Camera.open();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
			camera.release();
			camera = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			camera.setPreviewDisplay(holder);
			camera.setPreviewCallback(this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Size previewSize = camera.getParameters().getPreviewSize();
		float aspect = (float) previewSize.width / previewSize.height;
		int previewSurfaceWidth = preview.getWidth();
		LayoutParams lp = preview.getLayoutParams();
		Camera.Parameters parameters = camera.getParameters();
		parameters.set("orientation", "landscape");
		camera.setParameters(parameters);
		lp.width = previewSurfaceWidth;
		lp.height = (int) (previewSurfaceWidth / aspect);
		preview.setLayoutParams(lp);
		try {
			camera.autoFocus(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		camera.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	public void onPreviewFrame(final byte[] bytes, final Camera camera) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Size previewSize = camera.getParameters().getPreviewSize();
					Rect rect = vfv.getFramingRectInPreview();
					LuminanceSource source = new PlanarYUVLuminanceSource(bytes, previewSize.width, previewSize.height,
							rect.left, rect.top, rect.width(), rect.height(), false);

					Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
					Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>(8);
					decodeFormats.add(BarcodeFormat.EAN_13);
					decodeFormats.add(BarcodeFormat.EAN_8);
					decodeFormats.add(BarcodeFormat.UPC_EAN_EXTENSION);
					decodeFormats.add(BarcodeFormat.CODABAR);
					decodeFormats.add(BarcodeFormat.CODE_128);
					decodeFormats.add(BarcodeFormat.MAXICODE);
					decodeFormats.add(BarcodeFormat.UPC_E);
					decodeFormats.add(BarcodeFormat.QR_CODE);
					
					hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
					hints.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, new ResultPointCallback() {
						@Override
						public void foundPossibleResultPoint(ResultPoint resultPoint) {
							vfv.addPossibleResultPoint(resultPoint);
						}
					});

					MultiFormatReader mfr = new MultiFormatReader();
					mfr.setHints(hints);

					try {
						BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
						rawResult = mfr.decodeWithState(bitmap);
					} catch (NotFoundException nfe) {
						nfe.printStackTrace();
					}
					if (rawResult != null) {
						Log.e("QKActivity", rawResult.getText());// DOM,SAX
						synchronized (this) {
							if (!isOk) {
								isOk = true;
								handler.post(new Runnable() {
									@Override
									public void run() {
										Intent intent = new Intent(QKActivity.this, ScannerActivity.class);
//										intent.putExtra(ResultActivity.RESULT, rawResult.getText());
//										intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										intent.putExtra("scannedCode",rawResult.getText());
										
										startActivity(intent);
									}
								});
							}
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void onAutoFocus(boolean b, Camera cam) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (camera != null
						&& (Camera.Parameters.FOCUS_MODE_AUTO.equals(camera.getParameters().getFocusMode()) || Camera.Parameters.FOCUS_MODE_MACRO
								.equals(camera.getParameters().getFocusMode()))) {
					camera.autoFocus(QKActivity.this);
				}
			}
		}).start();
	}
}
