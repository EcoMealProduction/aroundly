$steps = @(
    "Running tests...",
    "Building JAR...",
    "Checking for Dockerfile...",
    "Building Docker image...",
    "Done!"
)

$totalSteps = $steps.Count

function Show-StepProgress {
    param([int]$currentStep, [string]$message)
    Write-Progress -Activity "Building Project" -Status $message -PercentComplete (($currentStep / $totalSteps) * 100)
}

# Step 1: Tests
Show-StepProgress 0 $steps[0]
Write-Host "🧪  $($steps[0])"
mvn test
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

# Step 2: Build (skip tests here, since already run)
Show-StepProgress 1 $steps[1]
Write-Host "🛠️  $($steps[1])"
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

# Step 3: Dockerfile check
Show-StepProgress 2 $steps[2]
Write-Host "📦  $($steps[2])"
if (-Not (Test-Path "Dockerfile")) {
    Write-Host "⚠️  Dockerfile not found. Creating default Dockerfile..."
    @"
FROM openjdk:21-jdk-slim
LABEL authors=""ecomeal-production""
COPY infra/target/infra-0.0.1.jar app/app.jar

ENTRYPOINT [""java"", ""-jar"", ""app/app.jar""]
"@ | Out-File -Encoding UTF8 Dockerfile
}

# Step 4: Docker build
Show-StepProgress 3 $steps[3]
Write-Host "🐳  $($steps[3])"
docker build -t backend .
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

# Step 5: Done
Show-StepProgress 4 $steps[4]
Write-Host "✔️  $($steps[4])"
Start-Sleep -Seconds 1
Show-StepProgress 5 "Complete."
