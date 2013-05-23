:: set version=$1
:: %1 (argument 1) = version number
del /Q .\bin
del /Q lib\swt*
mkdir bin
mkdir lib

::  Create JAR for Linux 64bit

copy .\swt\swt-4.2.2-gtk-linux-x86_64.jar .\lib\swt.jar /Y
call sbt clean compile msgfmt assembly
copy .\target\scala-2.9.1\IRCBalloonJ-assembly-%1.jar .\bin\IRCBalloonJ-linux64-%1.jar /Y

::  Create JAR for Linux 32bit

copy .\swt\swt-4.2.2-gtk-linux-x86.jar .\lib\swt.jar /Y
call sbt clean compile msgfmt assembly
copy .\target\scala-2.9.1\IRCBalloonJ-assembly-%1.jar .\bin\IRCBalloonJ-linux32-%1.jar /Y

::  Create JAR for Windows 64bit
copy .\swt\swt-4.2.2-win32-win32-x86_64.jar .\lib\swt.jar /Y
call sbt clean compile msgfmt assembly
copy .\target\scala-2.9.1\IRCBalloonJ-assembly-%1.jar .\bin\IRCBalloonJ-win64-%1.jar /Y

::  Create JAR for Windows 32bit
copy .\swt\swt-4.2.2-win32-win32-x86.jar .\lib\swt.jar /Y
call sbt clean compile msgfmt assembly
copy .\target\scala-2.9.1\IRCBalloonJ-assembly-%1.jar .\bin\IRCBalloonJ-win32-%1.jar /Y

::  Swtich to default SWT jar
del /F lib\swt*
copy .\swt\swt-4.2.2-win32-win32-x86_64.jar .\lib\swt.jar /Y