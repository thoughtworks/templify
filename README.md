[![CircleCI](https://dl.circleci.com/status-badge/img/gh/twlabs/Cookiecutter-Templater-for-Backstage/tree/main.svg?style=svg&circle-token=32f338c23bd26fde500c4e1aaf59bfd5b7b04c6f)](https://dl.circleci.com/status-badge/redirect/gh/twlabs/Cookiecutter-Templater-for-Backstage/tree/main)

---

# Cookiecutter Templater

<!--toc:start-->
- [Cookiecutter Templater for Backstage](#cookiecutter-templater-for-backstage)
<!--toc:end-->

## Description

The Cookiecutter plugin is designed to make mapping keys at project configuration in a simple way. By mapping key files—such as XML, JSON, YAML, and even Java code — CookieCutter plugin allows you to define and automate template replacements in a flexible and centralized way. 

Instead of manually editing repetitive elements across files, you can now manage everything in a single configuration file. Placholders are automatically replaced with the correct values, making the setup process faster, more reliable, and less error-prone. This plugin supports structured data formats like XML and JSON and can handle custom elements in Java files or even plain text, allowing for a custom setup for unique requirements.

---

## Plugin current status
The plugin is currently in development and is ready for early-stage adoption and validation.
Today, the plugin supports:
* XML 
* YAML 
* JSON
* Plain text
* Java files and packages


## Quickstart

### Requirements
The basic requirements to use the cookiecutter-templater plugin are as follows:

* JDK11+
* Apache Maven 3.9.1

### Configuration 
The cookiecutter-templater-for plugin should be included in the pom.xml or settings.xml.

```xml
<repository>
    <id>artifact-registry-release</id>
    <url>https://us-east1-maven.pkg.dev/cookiecutter-templater-879f/twlabs-release</url>
</repository>
```

## Getting Started
To start using the plugin, you are going to need to adjust the configuration file. 
The configuration file is `maven-cookiecutter.yml`. After the adjusment of the configurationfile, you will need to run the command below:

```
mvn com.twlabs:cookiecutter-templater-maven-plugin:cutter
```

### Usage of the configuration file.
As mentioned, the plugin supports some file types and we are going to explain the configuration for each supported types.
But first let understand the configuration file structure.

#### Configuration file structure
The configuration file is in a `Yaml` type and has the following structure:
```
settings
└── placeholder
|   └── prefix
|   └── suffix 
steps
└── - kind
|     └── apiVersion
|     └── spec
|         └── - files
|         └── placeholders
|             └── - match
|             └── replace
```
#### 1. **settings**
   - **placeholder**: Configures how placeholders are identified within files.
      - **prefix**: Specifies the prefix marking the start of a placeholder.
      - **suffix**: Specifies the suffix marking the end of a placeholder.
   - This setup lets the plugin locate variables needing replacement in the template files and if it is not defined the default prefix and sufix at the placeholder will be `{{` and `}}`.
#### 2. **steps**
   - A list of actions, with each item defining a specific configuration step.
   - **kind**: Identifies the type of action to perform at this step. Ex: XmlHandler, YmlHandler, JavaHandler...
   - **apiVersion**: Defines the API version used for executing this action. today we have only the `v1`
   - **spec**: Contains instructions for the step, including files and placeholders to be processed.
      - **files**: Lists the files and paths that will be processed for replacements. The path should be informed from the root of the project
      - **placeholders**: Specifies the replacement operations in each file.
         - **match**: The text or pattern the plugin will locate within the file.
         - **replace**: The text that will replace the `match`.

### Summary of How It Works
This configuration allows the plugin to:
- Identify variables with placeholders using the specified `prefix` and `suffix`.
- Execute specific steps (`steps`) to replace values in designated files according to the `placeholders` rules in each step.


### More Information
For detailed documentation and advanced configuration options, please refer to our [Cookiecutter Wiki](wiki/)











