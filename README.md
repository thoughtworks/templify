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
* Java files and packages
* Any file type as plaintext


# Quickstart

## Requirements
The basic requirements to use the cookiecutter-templater-for-backstage plugin are as follows:

* JDK11+
* Apache Maven 3.8.1

## Configuration 
The cookiecutter-templater-for-backstage plugin should be included in the pom.xml or settings.xml.

```xml
<repository>
    <id>artifact-registry-release</id>
    <url>https://us-east1-maven.pkg.dev/cookiecutter-templater-879f/twlabs-release</url>
</repository>
```

## Getting Started
To start using the plugin, you are going to need to adjust the configuration file. 
Currently, the configuration file is template.yml (We have plans to be updated to a more friendly name, please make suggestions).

```
mvn com.twlabs:cookiecutter-templater-maven-plugin:cutter
```

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
To perform mapping for replacement with XML files, you will need to provide the following fields:
* `file:` Relative path to the XML file from the project root.
* `query:` Within the placeholders tag, provide the XPATH for the XML file attribute that needs to be changed.
* `name:` Value to be replaced for 

Usage example:
* Config
```yaml
mappings:
  - file: pom.xml
    placeholders:
      - query: /project/groupId
        name: Cookiecutter.param.groupId
      - query: /project/artifactId
        name: Cookiecutter.test.replace.map.artifactId
```

- Before:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>0.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>my-example-maven-plugin</artifactId>
    <packaging>maven-plugin</packaging>
    <version>0.0.0-SNAPSHOT</version>
    <name>my project example</name>
    <url>http://maven.apache.org</url>
 ...
```

 - Expected result after running Cookie-Cutter Templater:
 ```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>0.0.0</modelVersion>
    <groupId>{{Cookiecutter.param.groupId}}</groupId>
    <artifactId>{{Cookiecutter.test.replace.map.artifactId}}</artifactId>
    <packaging>maven-plugin</packaging>
    <version>0.0.0-SNAPSHOT</version>
    <name>my project example</name>
    <url>http://maven.apache.org</url>
 ...
```


#### JSON
To make replacements in JSON files, you should use a similar XML configuration, but instead of using XPath, you should use JSONPath.
* `file:` Relative path to the JSON file from the project root.
* `query:` Within the placeholders tag, provide the JASONpath for the JSON file attribute that needs to be changed.
* `name:` It is the value that will be placed for the specified query field.

Usage example:
* Config
```yaml
mappings:
  - file: jsons/my_json.xml
    placeholders:
      - query: $['name']
        name: Cookiecutter.name
      - query: $['age']
        name: Cookiecutter.age
```
- Before:
```json
{
    "name": "David",
    "age": 30,
    "address": {
        "street": "123 Main St",
        "city": "New York",
        "state": "NY",
        "zip": "10001",
        "name": "David House"
    },
    "hobbies": [
        "Football",
        "Cooking",
        "Swimming"
    ],
    "languages": {
        "French": "Beginner",
        "German": "Intermediate",
        "Spanish": "Advanced"
    }
}
```
 - Expected result after running Cookie-Cutter Templater:
 ```json
 {
    "name": {{Cookiecutter.name}},
    "age": {{Cookiecutter.age}},
    "address": {
        "street": "123 Main St",
        "city": "New York",
        "state": "NY",
        "zip": "10001",
        "name": "David House"
    },
    "hobbies": [
        "Football",
        "Cooking",
        "Swimming"
    ],
    "languages": {
        "French": "Beginner",
        "German": "Intermediate",
        "Spanish": "Advanced"
    }
}
```


#### YAML
The configuration structure for YAML files follows the pattern: file, placeholders, query, and name. Each of these attributes has the following function:

* `file:` Relative path of the YAML file from the project root.
* `placeholders:` A set of queries and names to be processed during the plugin execution.
* `query:` The YAML path to be located in the file. We use the [YAMLPath](https://github.com/yaml-path/YamlPath) project structure.
* `name:` The value by which it should be replaced.

Usage example:
* Config
```yaml
 mappings:
   - file: yamls/generic1.yml
      placeholders:
        - query: mappings[0].file
          name: newFile
        - query: mappings[0].placeholders[0].query
          name: Cookiecutter.query.project.groupId
        - query: mappings[0].placeholders[0].name
          name: Cookiecutter.replace.map.groupId
        - query: mappings[0].placeholders[1].query
          name: Cookiecutter.query.project.artifactId
        - query: mappings[0].placeholders[1].name
          name: Cookiecutter.replace.map.artifactId
```
- Before:
```yaml
mappings:
  - file: pom.xml
    placeholders:
      - query: /project/groupId
        name: Cookiecutter.param.groupId
      - query: /project/artifactId
        name: Cookiecutter.test.replace.map.artifactId
      - query: /project/dependencies/dependency/scope[text()='test']
        name: Cookiecutter.replace.map.scopes
  - file: xmls/generic_1.xml
    placeholders:
      - query: /note/heading
        name: New Reminder
  - file: xmls/complex/generic_2.xml
    placeholders:
      - query: /bookstore/book/author[text()='Kurt Cagle']
        name: Cookiecutter.kurtCagle
      - query: /bookstore/book/year[text()='2005']
        name: Cookiecutter.NewYear
      - query: /bookstore/book/author[text()='replace ---- test']
        name: Cookiecutter.kurtCagle

```
 - Expected result after running Cookie-Cutter Templater:
 ```yaml
 ---
mappings:
  - file: "{{newFile}}"
    placeholders:
      - query: "{{Cookiecutter.query.project.groupId}}"
        name: "{{Cookiecutter.replace.map.groupId}}"
      - query: "{{Cookiecutter.query.project.artifactId}}"
        name: "{{Cookiecutter.replace.map.artifactId}}"
      - query: "/project/dependencies/dependency/scope[text()='test']"
        name: Cookiecutter.replace.map.scopes
  - file: xmls/generic_1.xml
    placeholders:
      - query: /note/heading
        name: New Reminder
  - file: xmls/complex/generic_2.xml
    placeholders:
      - query: "/bookstore/book/author[text()='Kurt Cagle']"
        name: Cookiecutter.kurtCagle
      - query: "/bookstore/book/year[text()='2005']"
        name: Cookiecutter.NewYear
      - query: "/bookstore/book/author[text()='replace ---- test']"
        name: Cookiecutter.kurtCagle
```

#### Java file and packages
The plugin has the ability to perform value replacement within the Java structure. The configuration is slightly different from other file types (XML, JSON, and YAML) because instead of specifying the file path, you need to provide the classpath (for both code and tests) and indicate that the structure is Java. It follows the following structure:

* `type:` Attribute to specify the type of structure to be worked with, in this case, it is Java.
* `base_dir:` Location of the classpath to be worked with. In this version, separate configurations for the main classpath and test classpath are still required.
* `placeholder:` Follows the previous structure of containing the query and name.
* `query:` The part of the package that will be replaced by a new value.
* `name:` The value that will be used for replacement.

Let's use a simple project structure to illustrate the usage of the plugin. 
The project structure will be as follows:
```
root
└── src/ 
|     └── main/java/com/myPackage/
|                            └── MyClass.java
|                            └── interfaces/
|                                         └── InterfaceClass.java
└── xmls/
|      └── complex/
|                └── generic_2.xml
|      └── generic_1.xml
└── ymls/
|      └── generic1.yml
└── maven-cookiecutter.yml
└── pom.xml 
```
For this example, the plugin configuration file will be formatted as follows:
```yaml
mappings:
  - file: pom.xml
    placeholders:
      - query: /project/groupId
        name: Cookiecutter.param.groupId
      - query: /project/artifactId
        name: Cookiecutter.test.replace.map.artifactId
      - query: /project/dependencies/dependency/scope[text()='test']
        name: Cookiecutter.replace.map.scopes

  - file: xmls/generic_1.xml
    placeholders:
      - query: /note/heading
        name: New Reminder

  - file: xmls/complex/generic_2.xml
    placeholders:
      - query: /bookstore/book/author[text()='Kurt Cagle']
        name: Cookiecutter.kurtCagle
      - query: /bookstore/book/year[text()='2005']
        name: Cookiecutter.NewYear

  - file: yamls/generic1.yml
    placeholders:
      - query: mappings[0].file
        name: newFile
      - query: mappings[0].placeholders[0].query
        name: Cookiecutter.query.project.groupId
      - query: mappings[0].placeholders[0].name
        name: Cookiecutter.replace.map.groupId
      - query: mappings[0].placeholders[1].query
        name: Cookiecutter.query.project.artifactId
      - query: mappings[0].placeholders[1].name
        name: Cookiecutter.replace.map.artifactId

  - type: java
    base_dir: src/main/java
    placeholders:
      - query: com.myPackage
        name: cookiecutter.package
```

##### The expected behavior for each of the files will be described below for type `java`:
###### Before MyClass.java
```Java
package com.myPackage;

/**
 * ...
 */
public class MyClass {
...
```
###### After MyClass.java
```java
package {{cookiecutter.package}};

/**
 * ...
 */
public class MyClass {
...
```


###### Before InterfaceClass.java
```Java
package com.myPackage.interfaces;

/**
 * InterfaceClass
 */
public class InterfaceClass {
...
```
###### After InterfaceClass.java
```Java
package {{cookiecutter.package}}.interfaces;

/**
 * InterfaceClass
 */
public class InterfaceClass {
...
```

##### The expected behavior for each of the files will be described below for file `yamls/generic1.yml`:
###### Before yamls/generic1.yml
```yaml
mappings:
  - file: pom.xml
    placeholders:
      - query: /project/groupId
        name: Cookiecutter.param.groupId
      - query: /project/artifactId
        name: Cookiecutter.test.replace.map.artifactId
      - query: /project/dependencies/dependency/scope[text()='test']
        name: Cookiecutter.replace.map.scopes
  - file: xmls/generic_1.xml
    placeholders:
      - query: /note/heading
        name: New Reminder
  - file: xmls/complex/generic_2.xml
    placeholders:
      - query: /bookstore/book/author[text()='Kurt Cagle']
        name: Cookiecutter.kurtCagle
      - query: /bookstore/book/year[text()='2005']
        name: Cookiecutter.NewYear
      - query: /bookstore/book/author[text()='replace ---- test']
        name: Cookiecutter.kurtCagle

```
 ###### After yamls/generic1.yml
 ```yaml
 ---
mappings:
  - file: "{{newFile}}"
    placeholders:
      - query: "{{Cookiecutter.query.project.groupId}}"
        name: "{{Cookiecutter.replace.map.groupId}}"
      - query: "{{Cookiecutter.query.project.artifactId}}"
        name: "{{Cookiecutter.replace.map.artifactId}}"
      - query: "/project/dependencies/dependency/scope[text()='test']"
        name: Cookiecutter.replace.map.scopes
  - file: xmls/generic_1.xml
    placeholders:
      - query: /note/heading
        name: New Reminder
  - file: xmls/complex/generic_2.xml
    placeholders:
      - query: "/bookstore/book/author[text()='Kurt Cagle']"
        name: Cookiecutter.kurtCagle
      - query: "/bookstore/book/year[text()='2005']"
        name: Cookiecutter.NewYear
      - query: "/bookstore/book/author[text()='replace ---- test']"
        name: Cookiecutter.kurtCagle
```

At the end the folder structure should be like this:
```
root
└── src/ 
|     └── main/java/{{cookiecutter.package}}/
|                            └── MyClass.java
|                            └── interfaces/
|                                         └── InterfaceClass.java
└── xmls/
|      └── complex/
|                └── generic_2.xml
|      └── generic_1.xml
└── ymls/
|      └── generic1.yml
└── maven-cookiecutter.yml
└── pom.xml 
``` 

##### in case you want to use plain text handler to replace the placeholders by overriding settings, you can use the following configuration: 
###### Mapping file
```
mappings:
  - file: src/main/java/com/thoughtworks/Sample.java
    type: plainText
    settings:
      placeholder:
        prefix: "{%"
        suffix: "%}"
    placeholders:
      - query: //backstage-template-condition-algo-start
        name: " if values.algo is defined and 'AES' in values.algo "
      - query: //backstage-template-condition-algo-end
        name: " endif "
  - file: build.gradle
    placeholders:
      - query: JavaVersion.VERSION_20
        name: values.java_version
  - file: build.gradle
    settings:
      placeholder:
        prefix: "{%"
        suffix: "%}"
    placeholders:
      - query: //backstage-template-condition-keycloak-condition-start
        name: " if values.security is defined and 'Keycloak' in values.security "
      - query: //backstage-template-condition-keycloak-condition-end
        name: " endif "
  - file: Makefile
    settings:
      placeholder:
        prefix: "{%"
        suffix: "%}"
    placeholders:
      - query: "#backstage-template-condition-infra-elk-condition-start"
        name: " if values.infra is defined and 'elk' in values.infra "
      - query: "#backstage-template-condition-infra-elk-condition-end"
        name: " endif "
```

This mapping will execute files in order and replace the placeholders considering files as text files.