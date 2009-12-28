@echo off

rem ### modify PATH (e.g. for "dot")
rem set PATH=C:\Programme\graphviz\bin;%PATH%

rem ### set another java path
set JAVA="java.exe"
rem set JAVA="C:\Programme\Java\jre1.6.0_05\bin\java.exe"

rem ### the *maximal* amount of memory to use (must not exceed system memory)
set MEMORY=1024m


rem ############# do not change anything below this line ########################


set JAR_DIR=%~dp0%

%JAVA% -Xmx%MEMORY% -jar "%JAR_DIR%\LTSA.jar"

if %ERRORLEVEL%==0 goto end

echo An Error occured!
pause

:end
