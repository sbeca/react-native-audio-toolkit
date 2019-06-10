# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [Unreleased]
### BREAKING CHANGE
- Android: Change namespace from `com.futurice.rctaudiotoolkit` to `com.reactnativecommunity.rctaudiotoolkit` which requires users to re-link library. This can be done manually or automatically during the update process via:
    ```
    react-native unlink react-native-audio-toolkit
    npm install --save react-native-audio-toolkit@2.0.0
    react-native link react-native-audio-toolkit
    ```
- Android: Remove permissions from library AndroidManifest and instead require users to add them. See [SETUP.md](https://github.com/react-native-community/react-native-audio-toolkit/blob/master/docs/SETUP.md) and PR [#148](https://github.com/react-native-community/react-native-audio-toolkit/pull/148) for more details

### Added
- Add Typescript typings
- Add ability to set playback speed for audio player
- Add ability to pause a Recorder
- Add ability to set header data when requesting to play audio from a network URL
- Android: Add ability to record to AAC
- iOS: Added possibility to record from Bluetooth microphone

### Changed
- Specify exactly which files to include in npm package
- Android: `build.gradle` will use SDK version settings of the root project, if available
- iOS: Buffer up to 10 seconds of audio before beginning playback

### Fixed
- Fixed some incorrect examples in the documentation
- Android: Guard against possible exceptions while parsing stack trace
- Android: Fix build error related to defining `android:minSdkVersion` in the library's AndroidManifest
- Android: Fix crash on devices running API level 22 or earlier
- iOS: Fix `Player.pause()` not setting `PAUSED` state

## [1.0.6] - 2017-11-01
### Changed
- Android: Changed prepare to prepareAsync

### Fixed
- Fix compatibility with React Native 0.47
- Fix compatibility with React Native 0.48
- Fix compatibility with React Native 0.49
- Android: Fix updating playerContinueInBackground
- Android: Fix a bug when finding audio file
- Android: Fix compatibility with API level 16 by removing usage of `java.util.Objects`

## [1.0.5] - 2016-09-22
### Changed
- Android: By default pause playback if app is sent to background

## [1.0.4] - 2016-08-26
### Fixed
- iOS: Fix audio events

## [1.0.3] - 2016-08-17
### Added
- Add Player state diagram to documentation
- Add recording example to README.md
- Record `prepare()` returns filesystem path of file being recorded to

### Fixed
- Missing `this` in setup example

## [1.0.2] - 2016-06-25
### Added
- Add description to package.json

## [1.0.1] - 2016-06-25
### Added
- Add repository to package.json
- Add error handling to Example App
- iOS: Send current position at pause

### Changed
- Move documentation from README.md to separate files within docs directory
- Improve documentation
- Restructure Player and Recorder into own source files

### Fixed
- Android: Fix seeking, hide debug prints
- Android: Fix stop bug
- iOS: Fixed parsing of quality strings

## 1.0.0 - 2016-06-25

Initial release.


[Unreleased]: https://github.com/react-native-community/react-native-audio-toolkit/compare/dc2f04a35f388016aa294bcc80e7f553d1988037...HEAD
[1.0.6]: https://github.com/react-native-community/react-native-audio-toolkit/compare/4746870166fe4beb9fbf075d45fab952de4558d6...dc2f04a35f388016aa294bcc80e7f553d1988037
[1.0.5]: https://github.com/react-native-community/react-native-audio-toolkit/compare/6a2641ebc6b6177fa29ac81b694ea2dd64d5a2cd...4746870166fe4beb9fbf075d45fab952de4558d6
[1.0.4]: https://github.com/react-native-community/react-native-audio-toolkit/compare/eba2326941e9b2f4405e832ce5af0a85bf6817ef...6a2641ebc6b6177fa29ac81b694ea2dd64d5a2cd
[1.0.3]: https://github.com/react-native-community/react-native-audio-toolkit/compare/24dc361c950c2f4a919d557c4b2c7abecb28c6e8...eba2326941e9b2f4405e832ce5af0a85bf6817ef
[1.0.2]: https://github.com/react-native-community/react-native-audio-toolkit/compare/3384ceff8bdf34904b09abb34602f8f4120bcb9e...24dc361c950c2f4a919d557c4b2c7abecb28c6e8
[1.0.1]: https://github.com/react-native-community/react-native-audio-toolkit/compare/05523e1181ee0a8d41d0e4db9f192d2d48be2bb4...3384ceff8bdf34904b09abb34602f8f4120bcb9e
