#!/bin/sh
# Launcher: compiles and runs the app. Real script lives in scripts/run.sh
exec sh "$(dirname "$0")/scripts/run.sh" "$@"
