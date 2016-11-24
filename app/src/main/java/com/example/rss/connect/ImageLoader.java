package com.example.rss.connect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageLoader {

    public static void fetchImage(final String iUrl, final ImageView imageView){
        if ( iUrl == null || imageView == null )
            return;

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message){
                final Bitmap image = (Bitmap) message.obj;
                imageView.setImageBitmap(image);
            }
        };

        final Thread thread = new Thread(){
            @Override
            public void run(){
                final Bitmap image = downloadImage(iUrl);
                if(image != null){
                    Message message = handler.obtainMessage(1, image);
                    handler.sendMessage(message);
                }
            }
        };

        thread.setPriority(3);
        thread.start();
    }

     private static Bitmap downloadImage(String iUrl){
         Bitmap bitmap = null;
         HttpURLConnection connection= null;
         try {
             URL url = new URL(iUrl);
             connection = (HttpURLConnection) url.openConnection();
             connection.setDoInput(true);
             connection.connect();
             bitmap = BitmapFactory.decodeStream(connection.getInputStream());
         } catch (Exception e){
             Log.d("Image Loader", "Error");
             e.printStackTrace();
         }
         finally {
             connection.disconnect();
         }
         return bitmap;
     }

    /*
    private static Bitmap decodeBitmapFromStream(InputStream is) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, options);
        options.inSampleSize = calculateInSampleSize(options, 64, 64);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
        if(bitmap == null){
            Log.d("BITMAP", "NULL");
        }
        return bitmap;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    */
}
