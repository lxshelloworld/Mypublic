param(
    [string]$RemoteUrl
)

# PowerShell script to initialize git repo, create .gitignore, commit and push to remote
# Usage: .\upload_to_git.ps1 -RemoteUrl "git@github.com:username/repo.git"  OR
#        .\upload_to_git.ps1 "https://github.com/username/repo.git"

# Check git installed
if (-not (Get-Command git -ErrorAction SilentlyContinue)) {
    Write-Error "git is not installed or not in PATH. Please install Git and try again."
    exit 1
}

# Set repository root
$repoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $repoRoot

# Initialize git if not exists
if (-not (Test-Path .git)) {
    git init
    Write-Host "Initialized empty Git repository." -ForegroundColor Green
} else {
    Write-Host ".git already exists. Skipping git init." -ForegroundColor Yellow
}

# Configure user if not set
try {
    $name = git config user.name
} catch {
    $name = $null
}
if (-not $name) {
    $defaultName = Read-Host "Enter Git user.name (e.g. Your Name)"
    if ($defaultName) { git config user.name "$defaultName" }
    $defaultEmail = Read-Host "Enter Git user.email (e.g. you@example.com)"
    if ($defaultEmail) { git config user.email "$defaultEmail" }
}

# Add files and commit
git add .
# Check if there is anything to commit
$changes = git status --porcelain
if ($changes) {
    git commit -m "chore: initial commit - add incident-ticket-api project"
    Write-Host "Committed changes." -ForegroundColor Green
} else {
    Write-Host "No changes to commit." -ForegroundColor Yellow
}

# Add remote if provided
if (-not $RemoteUrl) {
    $RemoteUrl = Read-Host "Enter remote Git URL (e.g. https://github.com/username/repo.git) or leave empty to skip pushing"
}

if ($RemoteUrl) {
    # Check if origin exists
    $origin = git remote
    if ($origin -contains "origin") {
        Write-Host "Remote 'origin' already exists. Updating URL to $RemoteUrl" -ForegroundColor Yellow
        git remote set-url origin $RemoteUrl
    } else {
        git remote add origin $RemoteUrl
        Write-Host "Added remote origin: $RemoteUrl" -ForegroundColor Green
    }

    # Determine default branch name (use main)
    $branch = "main"
    # Create branch if not exists
    git branch --show-current | Out-Null
    $current = git rev-parse --abbrev-ref HEAD
    if ($current -ne $branch) {
        git branch -M $branch
    }

    Write-Host "Pushing to remote origin $branch ..." -ForegroundColor Cyan
    # Push (will prompt for credentials if needed)
    git push -u origin $branch
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Pushed to remote successfully." -ForegroundColor Green
    } else {
        Write-Host "Failed to push to remote. Check credentials or remote URL." -ForegroundColor Red
    }
} else {
    Write-Host "Remote URL not provided. Skipping push." -ForegroundColor Yellow
}

