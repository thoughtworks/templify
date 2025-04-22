# Contribution

## Install

This project uses a Makefile to run scripts. You can run `make help` to get the list of commands.

## How to contribute

### 1. Fork and clone this repository.

https://docs.github.com/en/get-started/exploring-projects-on-github/contributing-to-a-project

### 2. Create a branch to add your changes

```
git switch -C <add-your-new-branch-name>
```

For example:

```
git switch -C update-readme
```

### 3. Commit your changes

This repository uses the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) standard. 

For example:
    -
```
git commit -m "feat: add new feature" 
git commit -m "docs: correct spelling of CHANGELOG" 
git commit -m "feat(api)!: send an email to the customer when a product is shipped" 

```

### 4. Push to the original branch

Push your changes using the command `git push`:

```
git push origin <add-your-branch-name>
```

Replacing `<add-your-branch-name>` with the name of the branch you created earlier.

### 5. Create the pull request

You'll see a `Compare & pull request` button. Click on that button.

Now submit the pull request.

Soon I'll be merging all your changes into the main branch of this project. You will get a notification email once the changes have been merged.


## Where to go from here?

Congrats!  

You just completed the standard `fork/clone -> branch -> commits -> PR_` workflow that you'll often encounter as a contributor!

## Architecture


```mermaid
---
title: Plugin Architecture
---
classDiagram
    RunnerDefault <|-- CreateTemplateRunner
    note for RunnerDefault "can create templates by ordering tasks."

    CopyProjectTask <|-- RunnerTask
    note for CopyProjectTask "task accountable for copying project to final directory."

    DeleteTemplateIfExistsTask <|-- RunnerTask
    note for DeleteTemplateIfExistsTask "task accountable for checking if template exists and deleting it."

    ExecuteStepsTask <|-- RunnerTask
    note for ExecuteStepsTask "task accountable for process each StepKind."

    LoadConfigurationTask <|-- RunnerTask
    note for LoadConfigurationTask "task accountable for reading the maven-templify.yml."

    class RunnerDefault{
        +execute()
    }
    interface CreateTemplateRunner{
        +execute()
    }
    class RunnerTask{
        +execute()
    }
    class CopyProjectTask{
        +execute()
    }
    class LoadConfigurationTask{
        +execute()
    }
    class ExecuteStepsTask{
        +execute()
    }
    class DeleteTemplateIfExistsTask{
        +execute()
    }
    class templifyMojo{
        +execute()
    }

    RunnerDefault ..> RunnerTask
    RunnerDefault ..> RunnerTask
    RunnerDefault ..> RunnerTask
    RunnerDefault ..> RunnerTask
    templifyMojo ..> CreateTemplateRunner
```
