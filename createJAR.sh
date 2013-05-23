version=$1

rm -rvf bin/ &&
rm -rvf lib/swt* &&
mkdir bin &&
mkdir lib &&

# Create JAR for Linux 64bit
cp swt/swt-4.2.2-gtk-linux-x86_64.jar lib/swt.jar &&
sbt clean compile msgfmt assembly &&
cp target/IRCBalloonJ-assembly-$version.jar bin/IRCBalloonJ-linux64-$version.jar &&

# Create JAR for Linux 32bit
cp swt/swt-4.2.2-gtk-linux-x86.jar lib/swt.jar &&
sbt clean compile msgfmt assembly &&
cp target/IRCBalloonJ-assembly-$version.jar bin/IRCBalloonJ-linux32-$version.jar &&

# Create JAR for Windows 64bit
cp swt/swt-4.2.2-win32-win32-x86_64.jar lib/swt.jar &&
sbt clean compile msgfmt assembly &&
cp target/IRCBalloonJ-assembly-$version.jar bin/IRCBalloonJ-win64-$version.jar &&

# Create JAR for Windows 32bit
cp swt/swt-4.2.2-win32-win32-x86.jar lib/swt.jar &&
sbt clean compile msgfmt assembly &&
cp target/IRCBalloonJ-assembly-$version.jar bin/IRCBalloonJ-win32-$version.jar &&

# Swtich to default SWT jar
rm -rvf lib/swt* &&
cp swt/swt-4.2.2-gtk-linux-x86_64.jar lib/swt.jar
