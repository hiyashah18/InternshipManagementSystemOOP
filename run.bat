@echo off
REM Compiles and runs the Internship Management System (Windows).
REM Usage:  run.bat          (starts on port 8080)
REM         run.bat 9090     (starts on a different port)
if not exist out mkdir out
javac -d out src\model\*.java src\exception\*.java src\service\*.java src\util\*.java src\web\*.java src\main\*.java
if errorlevel 1 (
  echo.
  echo Compilation failed. Check that the JDK is installed ^(run: javac -version^).
  pause
  exit /b 1
)
java -cp out main.Main %*
pause
