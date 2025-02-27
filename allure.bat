@echo off
echo Generating Allure report...

:: Set JAVA_HOME if not already set (adjust if necessary)
set "JAVA_HOME=C:\Program Files\Java\corretto-21.0.3"
set "PATH=%JAVA_HOME%\bin;%PATH%"
set "PATH=C:\Users\Mohamed Tharwat\.m2\repository\allure\allure-2.26.0\bin;%PATH%"

:: Generate the report from the allure-results folder (assumed to be in project root)
allure generate allure-results --clean -o allure-report
if %errorlevel% neq 0 (
    echo Failed to generate Allure report.
    pause
    exit /B %errorlevel%
)

echo Opening Allure report...
start allure-report\index.html
exit
