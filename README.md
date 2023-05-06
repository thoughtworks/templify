[![CircleCI](https://dl.circleci.com/status-badge/img/gh/twlabs/Cookiecutter-Templater-for-Backstage/tree/main.svg?style=svg&circle-token=32f338c23bd26fde500c4e1aaf59bfd5b7b04c6f)](https://dl.circleci.com/status-badge/redirect/gh/twlabs/Cookiecutter-Templater-for-Backstage/tree/main)

---

# Cookiecutter Templater for Backstage

<!--toc:start-->
- [Cookiecutter Templater for Backstage](#cookiecutter-templater-for-backstage)
<!--toc:end-->

---

# Plugin current status
The plugin is currently in development and is ready for early-stage adoption and validation.
Today, the plugin supports:
* XML 
* YAML 
* JSON 

Soon, the plugin will also be able to update Java packages. :)

# Quickstart

## Requirements
The basic requirements to use the cookiecutter-templater-for-backstage plugin are as follows:

* JDK11+
* Apache Maven 3.8.1

## Configuration 
The cookiecutter-templater-for-backstage plugin should be included in the pom.xml or settings.xml file of the project.

```xml
<project>
  ...
    <build>
        <plugins>
            <plugin>
                <groupId>com.twlabs</groupId>
                <artifactId>cookiecutter-templater-maven-plugin</artifactId>
                <version>{{latest-version}}</version>
                <executions>
                    <execution>
                        <id>configuracao_basica_build_test</id>
                        <phase>initialize</phase>
                        <goals>
                            <goal>cutter</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
  ...
  <repositories>
        <repository>
            <id>artifact-registry</id>
            <url>artifactregistry://southamerica-east1-maven.pkg.dev/cookiecutter-templater-879f/twlabs-snapshot</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
        <repository>
            <id>artifact-registry-release</id>
            <url>artifactregistry://us-east1-maven.pkg.dev/cookiecutter-templater-879f/twlabs-release</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
  ...
</project>
```
## Getting Started
To start using the plugin, you are going to need to adjust the configuration file. 
Currently, the configuration file is template.yml (We have plans to be updated to a more friendly name, please make suggestions).

### Usage of the configuration file.
As mentioned, the plugin supports some file types and we are going to explain the configuration for each supported types.

#### Configuration file structure
The configuration file is in a `Yaml` type and has the following structure:
```
mappings
└── - file 
|     └── placeholders
|         └── - query
|         └──   name
```

* `mappings:` The root attribute of the YAML, it contains the list of file-type parameters
* `file:` It is the attribute that contains the path to the file where the data replacement will take place and has a list of `placeholders` that will perform the replacement. 
The path should be informed from the root of the project. Example:
``` yaml
- file: resources/my_xmls/generic_1.xml
```
* `placeholders:` atribute that contains a list of `queries` and `names`
* `query:` For each file type, the query will have a specific format. The formats will be described in the corresponding section for each file type.
* `name:` This is the value that the query result will be transformed into. *Important:* the plugin will already format it as `mustache` type, `{{new value}}`
 
#### XML

#### JSON

#### YAML



