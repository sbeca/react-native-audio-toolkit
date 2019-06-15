package com.reactnativecommunity.rctaudiotoolkit;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.net.Uri;
import android.webkit.URLUtil;
import android.content.ContextWrapper;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.io.IOException;
import java.io.File;
import java.lang.Thread;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class AudioManagerModule extends ReactContextBaseJavaModule {
    private static final String LOG_TAG = "AudioManagerModule";

    private ReactApplicationContext context;
    private AudioPriority currentAudioPriority = AudioPriority.MIX_WITH_OTHER_APPS;

    // NOTE: Values must be kept in sync with AudioManager.js
    public enum AudioPriority {
        MIX_WITH_OTHER_APPS(0), SILENCE_OTHER_APPS(1)
    }

    public AudioManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
    }

    @Override
    public String getName() {
        return "RCTAudioManager";
    }

    @ReactMethod
    public void setAppAudioPriority(AudioPriority priority, Callback callback) {
        if (priority == currentAudioPriority) {
            callback.invoke(true);
            return;
        }

        if (priority == AudioPriority.SILENCE_OTHER_APPS) {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                requestAudioFocus21();
            } else {
                requestAudioFocus();
            }
        } else {

        }

        currentAudioPriority = priority;
        callback.invoke(true);
    }

    private void requestAudioFocus() {
    }

    @TargetApi(21)
    private void requestAudioFocus21() {
        AudioManager mAudioManager = (AudioManager) Context.getSystemService(Context.AUDIO_SERVICE);

        AudioAttributes mPlaybackAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).build();

        AudioFocusRequest mFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(mPlaybackAttributes).setAcceptsDelayedFocusGain(true).setWillPauseWhenDucked(false)
                .setOnAudioFocusChangeListener(this, mMyHandler).build();

        int res = mAudioManager.requestAudioFocus(mFocusRequest);
    }
}
