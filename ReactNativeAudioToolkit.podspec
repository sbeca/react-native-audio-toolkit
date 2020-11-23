require "json"

# NPM package specification
package = JSON.parse(File.read(File.join(File.dirname(__FILE__), "package.json")))

Pod::Spec.new do |s|
  s.name         = "ReactNativeAudioToolkit"
  s.version      = package["version"]
  s.summary      = package["description"]
  s.homepage     = package["homepage"]
  s.license      = "MIT"
  s.author       = { "Rasmus Eskola" => "fruitiex@gmail.com" }
  s.platforms    = { :ios => "9.0", :tvos => "9.0" }
  s.source       = { :git => "https://github.com/sbeca/react-native-audio-toolkit.git#for-app", :commit => "31c8edf02e0b8be002eba5dd955dbc0ef3e3684a" }
  s.source_files = "ios/**/*.{h,m}"

  s.dependency "React"
end
