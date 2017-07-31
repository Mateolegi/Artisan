@echo off
set COMPOSER_HOME=Home
if not exist %COMPOSER_HOME% md "%COMPOSER_HOME%"
php -r "copy('https://getcomposer.org/installer', 'composer-setup.php');"
php -r "if (hash_file('SHA384', 'composer-setup.php') === '669656bab3166a7aff8a7506b8cb2d1c292f042046c5a994c43155c0be6190fa0355160742ab2e1c88d40d5be660b410') { echo 'Installer verified'; } else { echo 'Installer corrupt'; unlink('composer-setup.php'); } echo PHP_EOL;"
php composer-setup.php
php -r "unlink('composer-setup.php');"
call php composer.phar --version | findstr /i /r /c:"Composer......version"
call php composer.phar --quiet config --global process-timeout 3000
set COMPOSER_LOCAL=Local
if not exist %COMPOSER_LOCAL% md "%COMPOSER_LOCAL%"
call composer --quiet config --global cache-dir "%COMPOSER_LOCAL%"
set COMPOSER_BAT=composer.bat
if not exist "%COMPOSER_BAT" (
	echo @ECHO OFF> "%COMPOSER_BAT%"
	echo SET COMPOSER_HOME=%%Home>> "%COMPOSER_BAT%"
	echo if not exist %%COMPOSER_HOME%% md "%%COMPOSER_HOME%%">> "%COMPOSER_BAT%"
	echo php "%%~dp0composer.phar" %%*>> "%COMPOSER_BAT%"
	echo EXIT /B %%ERRORLEVEL%%>> "%COMPOSER_BAT%"
)
exit