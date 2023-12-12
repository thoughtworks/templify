.DEFAULT_GOAL := help

.PHONY: help

help: # Show help for each of the Makefile recipes.
	@echo 'Available commands:'
	@grep -E '^[a-zA-Z0-9 -]+:.*#'  Makefile | sort | while read -r l; do printf "\033[1;32m$$(echo $$l | cut -f 1 -d':')\033[00m:$$(echo $$l | cut -f 2- -d'#')\n"; done

.SILENT: tdd
	
.PHONY: scripts
dev: # Build and install mvn packages locally.
	- mvn install

build: # Build the jar file.
	- mvn package

test: # Run unit tests.
	- mvn test -DunitOnly=true

cov: # Run mutation coverage.
	- mvn -DwithHistory test-compile org.pitest:pitest-maven:mutationCoverage

tdd: # Run tests of git modified files. You may need to give permission to the script to run.
	./scripts/tdd-modified-files.sh

clean: # Remove all build files
	-rm -rf ./target
	-rm -rf ./reports
	-rm -rf ./coverage
	-mvn clean
