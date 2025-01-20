[![CircleCI](https://dl.circleci.com/status-badge/img/gh/thoughtworks/templify/tree/main.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/thoughtworks/templify/tree/main)
---

# Templifly

Templifly is a Maven plugin designed to simplify the process of managing and automating template-based configurations in your projects. By mapping key files—such as XML, JSON, YAML, Java code, or even plain text—Templifly allows you to define placeholders and automate their replacement in a centralized and flexible way.

Instead of manually editing repetitive elements across files, you can manage everything through a single configuration file. Placeholders are automatically replaced with the appropriate values during the build process, making your setup faster, more reliable, and less error-prone. This plugin supports structured data formats like XML and JSON and can handle custom elements in Java files, offering flexibility for unique project requirements.

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
The basic requirements to use the Templifly-templater plugin are as follows:

* JDK11+
* Apache Maven 3.9.1

### Configuration 
The Templifly-templater-for plugin should be included in the pom.xml or settings.xml.

```xml
<repository>
    <id>artifact-registry-release</id>
    <url>https://us-east1-maven.pkg.dev/templifly-templater-879f/twlabs-release</url>
</repository>
```

## Getting Started
To start using the plugin, you are going to need to adjust the configuration file. 
The configuration file is `maven-templifly.yml`. After the adjusment of the configurationfile, you will need to run the command below:

```
mvn com.twlabs:templifly-templater-maven-plugin:cutter
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
For detailed documentation and advanced configuration options, please refer to our [Templifly Wiki](https://github.com/twlabs/Templify/wiki)











