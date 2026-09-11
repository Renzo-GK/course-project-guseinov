$ErrorActionPreference = 'Stop'

Write-Host '=== Backend unit tests + JaCoCo ===' -ForegroundColor Cyan
Push-Location "$PSScriptRoot/../backend"
try {
  mvn test
  Write-Host "JaCoCo report: backend/target/site/jacoco/index.html" -ForegroundColor Green
} finally {
  Pop-Location
}

Write-Host '=== Frontend build ===' -ForegroundColor Cyan
Push-Location "$PSScriptRoot/../frontend"
try {
  npm install
  npm run build
} finally {
  Pop-Location
}

Write-Host '=== Docker smoke build ===' -ForegroundColor Cyan
Pop-Location -ErrorAction SilentlyContinue
Set-Location "$PSScriptRoot/.."
docker compose build
Write-Host 'Verification finished.' -ForegroundColor Green
