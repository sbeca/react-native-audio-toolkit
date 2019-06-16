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

public class AudioManagerModule extends ReactContextBaseJavaModule implements AudioManager.OnAudioFocusChangeListener {
    private static final String LOG_TAG = "AudioManagerModule";

    private ReactApplicationContext context;
    private AudioPriority currentAudioPriority = AudioPriority.MIX_WITH_OTHER_APPS;
    private AudioManager audioManager = null;
    @TargetApi(21)
    private AudioFocusRequest previousFocusRequest = null;

    // NOTE: Values must be kept in sync with AudioManager.js
    public enum AudioPriority {
        MIX_WITH_OTHER_APPS(0), SILENCE_OTHER_APPS(1)
    }

    public AudioManagerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.context = reactContext;
        this.audioManager = (AudioManager) Context.getSystemService(Context.AUDIO_SERVICE);
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

        if (this.audioManager == null) {
            this.audioManager = (AudioManager) Context.getSystemService(Context.AUDIO_SERVICE);
        }

        if (priority == AudioPriority.SILENCE_OTHER_APPS) {
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                requestAudioFocus21(callback);
            } else {
                requestAudioFocus(callback);
            }
        } else {
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                abandonAudioFocus26(callback);
            } else {
                abandonAudioFocus(callback);
            }
        }
    }

    private void requestAudioFocus(Callback callback) {
        int res = this.audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);

        if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            callback.invoke(false);
        } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            currentAudioPriority = SILENCE_OTHER_APPS;
            callback.invoke(true);
        } else {
            // TODO: Throw unhandled error
            callback.invoke(false);
        }
    }

    @TargetApi(21)
    private void requestAudioFocus21(Callback callback) {
        AudioAttributes playbackAttributes = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN).build();

        this.previousFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(playbackAttributes).setWillPauseWhenDucked(false)
                .setOnAudioFocusChangeListener(this, mMyHandler).build();

        int res = this.audioManager.requestAudioFocus(this.previousFocusRequest);

        if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            callback.invoke(false);
        } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            currentAudioPriority = SILENCE_OTHER_APPS;
            callback.invoke(true);
        } else {
            // TODO: Throw unhandled error
            callback.invoke(false);
        }
    }

    private void abandonAudioFocus(Callback callback) {
        int res = this.audioManager.abandonAudioFocusRequest(this);

        if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            callback.invoke(false);
        } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            currentAudioPriority = MIX_WITH_OTHER_APPS;
            callback.invoke(true);
        } else {
            // TODO: Throw unhandled error
            callback.invoke(false);
        }

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            this.previousFocusRequest = null;
        }
    }

    @TargetApi(26)
    private void abandonAudioFocus26(Callback callback) {
        if (this.previousFocusRequest == null) {
            // TODO: Throw error
            callback.invoke(false);
            return;
        }

        int res = this.audioManager.abandonAudioFocusRequest(this.previousFocusRequest);

        if (res == AudioManager.AUDIOFOCUS_REQUEST_FAILED) {
            callback.invoke(false);
        } else if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            currentAudioPriority = MIX_WITH_OTHER_APPS;
            callback.invoke(true);
        } else {
            // TODO: Throw unhandled error
            callback.invoke(false);
        }

        this.previousFocusRequest = null;
    }

    public void onAudioFocusChange(int focusChange) {
        // Don't need to do anything here yet

        switch (focusChange) {
        case AudioManager.AUDIOFOCUS_GAIN:
        case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
        case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE:
        case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
        case AudioManager.AUDIOFOCUS_LOSS:
        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
            break;
        }
    }
}
