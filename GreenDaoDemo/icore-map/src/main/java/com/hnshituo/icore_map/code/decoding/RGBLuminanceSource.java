package com.hnshituo.icore_map.code.decoding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Wzh
 * @date 2016/7/14  14:07
 */
public class RGBLuminanceSource extends LuminanceSource {
    private final byte[] luminances;

    public RGBLuminanceSource(String path) throws FileNotFoundException {
        this(loadBitmap(path));
    }

    public static Result scanningImage(String path) {
        if(TextUtils.isEmpty(path)) {
            return null;
        } else {
            Hashtable hints = new Hashtable();
            Vector decodeFormats = new Vector();
            decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
            decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            options.inJustDecodeBounds = false;
            int sampleSize = (int)((float)options.outHeight / 200.0F);
            if(sampleSize <= 0) {
                sampleSize = 1;
            }

            options.inSampleSize = sampleSize;
            Bitmap scanBitmap = BitmapFactory.decodeFile(path, options);
            RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader multiFormatReader = new MultiFormatReader();
            multiFormatReader.setHints(hints);
            Result result = null;

            try {
                result = multiFormatReader.decodeWithState(binaryBitmap);
            } catch (NotFoundException var29) {
                ;
            } finally {
                multiFormatReader.reset();
            }

            if(result == null) {
                QRCodeReader reader = new QRCodeReader();

                try {
                    result = reader.decode(binaryBitmap, hints);
                } catch (NotFoundException var25) {
                    ;
                } catch (ChecksumException var26) {
                    ;
                } catch (FormatException var27) {
                    ;
                } finally {
                    reader.reset();
                }
            }

            return result;
        }
    }

    public RGBLuminanceSource(Bitmap bitmap) {
        super(bitmap.getWidth(), bitmap.getHeight());
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        this.luminances = new byte[width * height];

        for(int y = 0; y < height; ++y) {
            int offset = y * width;

            for(int x = 0; x < width; ++x) {
                int pixel = pixels[offset + x];
                int r = pixel >> 16 & 255;
                int g = pixel >> 8 & 255;
                int b = pixel & 255;
                if(r == g && g == b) {
                    this.luminances[offset + x] = (byte)r;
                } else {
                    this.luminances[offset + x] = (byte)(r + g + g + b >> 2);
                }
            }
        }

    }

    public byte[] getRow(int y, byte[] row) {
        if(y >= 0 && y < this.getHeight()) {
            int width = this.getWidth();
            if(row == null || row.length < width) {
                row = new byte[width];
            }

            System.arraycopy(this.luminances, y * width, row, 0, width);
            return row;
        } else {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
    }

    public byte[] getMatrix() {
        return this.luminances;
    }

    private static Bitmap loadBitmap(String path) throws FileNotFoundException {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if(bitmap == null) {
            throw new FileNotFoundException("Couldn\'t open " + path);
        } else {
            return bitmap;
        }
    }
}
