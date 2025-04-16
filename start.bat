@echo off
chcp 65001 > nul  REM 设置代码页为 UTF-8

SET BACKEND_DIR=backend\cbbshop\cbbshop
SET FRONTEND_DIR=frontend\cbbshop
SET JAR_FILE=cbbshop-0.0.1-SNAPSHOT.jar

REM 构建后端项目
cd /d "%~dp0%BACKEND_DIR%"
mvn clean install

REM 修改 application.yml 以使用绝对路径
SET DB_PATH=%~dp0%BACKEND_DIR%\database\cbbshop.db

REM 启动后端
start cmd /k "cd /d %~dp0%BACKEND_DIR% && java -jar target\%JAR_FILE% --spring.datasource.url=jdbc:sqlite:%DB_PATH%"

REM 等待后端启动（根据需要调整等候时间）
timeout /t 10

REM 启动前端
start cmd /k "cd /d %~dp0%FRONTEND_DIR% && npm install && npm run serve"

echo Both backend and frontend are starting...