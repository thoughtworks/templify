# Contribution

## How to contribute

### 1. Clone the repository

Now clone the repository from GitHub to your computer.

### 2. Create a branch

This repository not supports forking. So, create a branch named accord what you want to contribute to.

```
git checkout -b <add-your-new-branch-name>
```

For example:

```
git checkout -b add-alonzo-church
```

### 3. Commit your changes

Now open `Contributors.md` file in a text editor, add your name to it. Don't add it at the beginning or end of the file. Put it anywhere in between. Now, save the file.


If you go to the project directory and execute the command `git status`, you'll see there are changes. Add those changes to the branch you just created using the `git add` command:

```
git add Contributors.md
```

Now commit those changes using the `git commit` command:

```
git commit -m "Add <your-name> to Contributors list"
```

Replacing `<your-name>` with your name.


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

You just completed the standard `clone -> branch -> commits -> PR_` workflow that you'll often encounter as a contributor!

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
    note for LoadConfigurationTask "task accountable for reading the maven-cookiecutter.yml."

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
    class CookieCutterMojo{
        +execute()
    }

    RunnerDefault ..> RunnerTask
    RunnerDefault ..> RunnerTask
    RunnerDefault ..> RunnerTask
    RunnerDefault ..> RunnerTask
    CookieCutterMojo ..> CreateTemplateRunner
```
