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

    public String getImageBasePath() {
        return images.secureBaseUrl;
    }
}
