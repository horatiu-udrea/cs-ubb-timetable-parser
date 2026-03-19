#it only works on JDK 21 apparently
JAVA_HOME="$HOME/.gradle/jdks/eclipse_adoptium-21-aarch64-os_x.2/jdk-21.0.10+7/Contents/Home" \
  PATH="$JAVA_HOME/bin:$PATH"

./gradlew run --no-daemon --console=plain
