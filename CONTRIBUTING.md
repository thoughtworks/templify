# Contribution

## Requirements

1. Maven 3.9.6+
2. Java JDK 11

## Install and Testing Locally

`mvn clean install`

The project uses JUnit with Mutation tests and IT tests.

1. [Jupiter aka Junit5](https://junit.org/junit5/)
2. [PIT](https://pitest.org/)
3. [Maven IT extenstion](https://github.com/khmarbaise/maven-it-extension)

Before adding new code, make sure to add test cases for it so that the maintainers can provide guidance and simulate the same scenarios.

### Unit Tests

1. They're just conventional JUnit tests.
2. Avoid excessive mocks.
3. Try to use the AAA structure, stands for: Arrange, Act, Assert
4. Ensure all the mutations were killed and covered.

### Integration Tests

1. Make sure to add your project scenario to the `resources-it` folder.
2. Include your IT scenario in the class `TemplifyIT`. (This class is responsible for executing the Maven plugin build for the project scenarios)
3. Ensure the tests are marked as `@MavenTest` and have meaningful asserts.

If you face any issue about the 

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
