@echo off
echo 🚨 EMERGENCY RESTORATION STARTING...
echo.
echo Stopping any running processes...
taskkill /f /im "Android Studio*" 2>nul
taskkill /f /im gradle 2>nul

echo.
echo Restoring from backup...
xcopy /s /e /y "..\BhashaSetu-BACKUP-SAFE-2025-06-16\*" "."

echo.
echo ✅ Emergency restoration complete!
echo You can now restart Android Studio and continue working.
pause