package com.danilov.barcode;

import java.io.FileInputStream;
import java.io.InputStream;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

public class Scanner {
	public static void main(String[] args) {
		InputStream barCodeInputStream = new FileInputStream("file.jpg");
		BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
		LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Reader reader = new MultiFormatReader();
		Result result = reader.decode(bitmap);
		System.out.println("Barcode text is " + result.getText());
	}
}
