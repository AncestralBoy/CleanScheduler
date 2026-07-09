# Esegue seed_rooms.sql contro il container postgres di CleanScheduler avviato via docker compose.
$ErrorActionPreference = "Stop"

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$Container = if ($env:POSTGRES_CONTAINER) { $env:POSTGRES_CONTAINER } else { "cleanscheduler-postgres-1" }
$DbUser = if ($env:POSTGRES_USER) { $env:POSTGRES_USER } else { "myuser" }
$DbName = if ($env:POSTGRES_DB) { $env:POSTGRES_DB } else { "mydatabase" }

Get-Content "$ScriptDir\seed_rooms.sql" | docker exec -i $Container psql -U $DbUser -d $DbName
