import { NativeModules } from 'react-native';

import _ from 'lodash';

const RCTAudioManager = NativeModules.AudioManager;

const AudioPriority = {
    MIX_WITH_OTHER_APPS: 0,
    SILENCE_OTHER_APPS: 1
};

class AudioManagerImplementation {
    setAppAudioPriority(priority, callback = _.noop) {
        RCTAudioManager.setAppAudioPriority(priority, callback);
    }
}

const AudioManager = new AudioManagerImplementation();
export default AudioManager;
export { AudioManager, AudioPriority };
