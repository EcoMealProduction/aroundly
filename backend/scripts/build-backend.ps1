Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

# Safe defaults (can be overridden via env vars)
$IMAGE_NAME = if ($env:IMAGE_NAME) { $env:IMAGE_NAME } else { 'backend' }
$LOG_DIR    = if ($env:LOG_DIR)    { $env:LOG_DIR }    else { '.build-logs' }
$DEBUG      = if ($env:DEBUG)      { $env:DEBUG }      else { '0' }   # set to '1' for live logs
$RUN_TESTS  = if ($env:RUN_TESTS)  { $env:RUN_TESTS }  else { '1' }   # set to '0' to skip mvn test locally
$COMPOSE_UP = if ($env:COMPOSE_UP) { $env:COMPOSE_UP } else { '1' }   # set to '0' to skip compose up

New-Item -ItemType Directory -Force -Path $LOG_DIR | Out-Null

# Utilities
function Show-StepProgress {
    param([int]$currentStep, [int]$totalSteps, [string]$message)
    Write-Progress -Activity "Building Project" -Status $message -PercentComplete (($currentStep / [double]$totalSteps) * 100)
}

function Get-ComposeCmd {
    if (Get-Command 'docker' -ErrorAction SilentlyContinue) {
        # Prefer v2 'docker compose' if available
        try {
            $v2 = & docker compose version 2>$null
            if ($LASTEXITCODE -eq 0) { return @('docker','compose') }
        } catch {}
    }
    if (Get-Command 'docker-compose' -ErrorAction SilentlyContinue) {
        return @('docker-compose')
    }
    throw "Neither 'docker compose' nor 'docker-compose' is available."
}

# Run a step and capture logs; on failure, show last 120 lines and exit
function Run-Step {
    param(
        [string]$Label,
        [string]$LogFile,
        [scriptblock]$Action,
        [ref]$StepIndex,
        [int]$TotalSteps
    )
    $current = $StepIndex.Value + 1
    Write-Host ""
    Show-StepProgress $StepIndex.Value $TotalSteps $Label
    Write-Host "[$current/$TotalSteps] $Label"

    try {
        if ($DEBUG -eq '1') {
            & $Action 2>&1 | Tee-Object -FilePath $LogFile
        } else {
            & $Action *> $LogFile
        }
        Write-Host "✅  OK: $Label"
    }
    catch {
        Write-Host "❌  FAILED: $Label" -ForegroundColor Red
        if (Test-Path $LogFile) {
            Write-Host "`n--- Last 120 lines of $LogFile ---"
            Get-Content $LogFile -Tail 120
            Write-Host "-----------------------------------`n"
        }
        exit 1
    }
    finally {
        $StepIndex.Value++
        Show-StepProgress $StepIndex.Value $TotalSteps $Label
    }
}

# Steps list (dynamic based on options)
$steps = New-Object System.Collections.Generic.List[string]
if ($RUN_TESTS -eq '1') { $steps.Add("Running tests (local Maven)") }
$steps.Add("Docker image build")
if ($COMPOSE_UP -eq '1') { $steps.Add("docker compose build"); $steps.Add("docker compose up -d") }
$steps.Add("Done")

$totalSteps = $steps.Count
$stepIndex  = 0

# 1. Optional local tests (Maven)
if ($RUN_TESTS -eq '1') {
    if (Get-Command 'mvn' -ErrorAction SilentlyContinue) {
        Run-Step -Label $steps[$stepIndex] `
                 -LogFile (Join-Path $LOG_DIR '01-tests.log') `
                 -Action { mvn -B -e -q test } `
                 -StepIndex ([ref]$stepIndex) -TotalSteps $totalSteps
    } else {
        # Advance the step, but note it’s skipped
        Show-StepProgress $stepIndex $totalSteps $steps[$stepIndex]
        Write-Host "[$($stepIndex+1)/$totalSteps] Skipping tests: 'mvn' not found. Set RUN_TESTS=0 to hide this."
        $stepIndex++
    }
}

# 2. Docker image build (multi-stage Dockerfile does the Maven build)
Run-Step -Label $steps[$stepIndex] `
         -LogFile (Join-Path $LOG_DIR '02-docker-build.log') `
         -Action { docker build -t $IMAGE_NAME . } `
         -StepIndex ([ref]$stepIndex) -TotalSteps $totalSteps

# 3. docker compose
if ($COMPOSE_UP -eq '1') {
    $composeFile =
        (Test-Path 'docker-compose.yml') -or
        (Test-Path 'compose.yml')

    if (-not $composeFile) {
        Show-StepProgress $stepIndex $totalSteps $steps[$stepIndex]
        Write-Host "[$($stepIndex+1)/$totalSteps] No docker-compose.yml found; skipping compose."
        $stepIndex++
        # Still need to consume "up -d" step if it exists
        if ($stepIndex -lt $totalSteps -and $steps[$stepIndex] -like 'docker compose*') {
            Show-StepProgress $stepIndex $totalSteps $steps[$stepIndex]
            Write-Host "[$($stepIndex+1)/$totalSteps] Skipping compose up."
            $stepIndex++
        }
    } else {
        $composeCmd = Get-ComposeCmd

        # compose build
        Run-Step -Label $steps[$stepIndex] `
                 -LogFile (Join-Path $LOG_DIR '03-compose-build.log') `
                 -Action { & $composeCmd 'build' } `
                 -StepIndex ([ref]$stepIndex) -TotalSteps $totalSteps

        # compose up -d
        Run-Step -Label $steps[$stepIndex] `
                 -LogFile (Join-Path $LOG_DIR '04-compose-up.log') `
                 -Action { & $composeCmd 'up' '-d' } `
                 -StepIndex ([ref]$stepIndex) -TotalSteps $totalSteps

        # Brief status
        Write-Host ""
        Write-Host "📊 Service status:"
        & $composeCmd 'ps'
        Write-Host "ℹ️  Follow logs: $(($composeCmd -join ' ')) logs -f --tail=200"
    }
}


Show-StepProgress $stepIndex $totalSteps $steps[$stepIndex]
Write-Host "[$($stepIndex+1)/$totalSteps] $($steps[$stepIndex])"
Start-Sleep -Seconds 1
Show-StepProgress $totalSteps $totalSteps "Complete."
Write-Host "`n✅ All done!"