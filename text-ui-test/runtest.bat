@ECHO OFF

REM Set path to IntelliJ's JDK - replace this with your actual path
set JAVA_HOME="C:\Users\Avinash\.jdks\jbr-17.0.12"
set PATH=%JAVA_HOME%\bin;%PATH%

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
if exist ACTUAL.TXT del ACTUAL.TXT

REM compile the code into the bin folder
"%JAVA_HOME%\bin\javac"  -cp ..\src\main\java -Xlint:none -d ..\bin ..\src\main\java\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
"%JAVA_HOME%\bin\java" -classpath ..\bin Babe < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT