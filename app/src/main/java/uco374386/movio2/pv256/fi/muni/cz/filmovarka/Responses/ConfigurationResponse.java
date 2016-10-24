package uco374386.movio2.pv256.fi.muni.cz.filmovarka.Responses;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 10/23/16.
 */

public class ConfigurationResponse {

    public ImagesConfigurationResponse images;
    @SerializedName("change_keys")
    public String[] changeKeys;

    public String getMostSuitableBackdropSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        for(String choice: images.backdropSizes) {
            if(Integer.parseInt(choice.substring(1)) > width) {
                return choice;
            }
        }
        return  images.backdropSizes[images.backdropSizes.length-2];
    }
}
