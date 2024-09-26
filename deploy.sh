#!/bin/bash

ENV_FILE="./.env"

# Обновление кода и деплой backend приложения
pushd ~/TelegramBotLaurentTransport || exit

# Переходим на ветку main
#git checkout dev
git fetch

# Обновляем ветку main
#git pull origin dev
git pull origin main

# Останавливаем старые контейнеры микросервисов и запускаем новые, с обновлённым кодом
docker compose -f docker-compose.yml --env-file $ENV_FILE down --timeout=60 --remove-orphans
docker compose -f docker-compose.yml --env-file $ENV_FILE up --build --detach

popd || exit