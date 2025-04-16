# Templify 

**Standardize and accelerate the creation of templates-based projects using definining placeholders across any kind of project**

Templify is a Maven plugin that helps you generate templates-based from a predefined project using a YAML-based configuration. It supports advanced transformations such as XPath-based replacement in XML files – ideal for managing complex project scaffolding and enforcing consistency.

[![Build and Deploy](https://github.com/thoughtworks/templify/actions/workflows/gitAction.yml/badge.svg)](https://github.com/thoughtworks/templify/actions/workflows/gitAction.yml) 

---

## What does it do?

Templify automates the generation of templates by:

- Reading a YAML config file (`maven-templify.yml`)
- Loading project files from a directory
- Applying structured transformations (like XPath replacements in XML)
- Outputting a complete, placeholders templates-based ready to integrate with your Backstage, Jinja and so on 

---

## Who is it for?

Templify is built for:

- **Base Platform teams** creating reusable service templates
- **Developers** automating repetitive setup tasks
- **Organizations** seeking consistent project standards and faster delivery

---

## How it works

You define:

- A **template folder** containing your base project
- A **YAML config file** that describes:
  - Which files to transform
  - How to match content (XPath for XML)
  - What to replace

Templify will process the files and generate a project in a specified destination.

---

## 🧪 Example usage

### 1. Prepare your config file

`maven-templify.yml`:

```yaml
steps:
  - kind: XmlHandler
    apiVersion: v1
    spec:
      - files:
          - pom.xml
        placeholders:
          - match: /project/groupId
            replace: templify.param.groupId
          - match: /project/artifactId
            replace: templify.test.replace.map.artifactId
          - match: /project/dependencies/dependency/scope[text()='test']
            replace: templify.replace.map.scopes
      - files:
          - xmls/generic_1.xml
        placeholders:
          - match: /note/heading
            replace: New Reminder
      - files:
          - xmls/complex/generic_2.xml
        placeholders:
          - match: /bookstore/book/author[text()='Kurt Cagle']
            replace: templify.kurtCagle
          - match: /bookstore/book/year[text()='2005']
            replace: templify.NewYear
```

---

### 2. Run the plugin

```bash
mvn com.thoughtworks.templify:templify:create
```

-

## Template folder structure

```

📁 target/template/
├── pom.xml
├── xmls/
│   ├── generic_1.xml
│   └── complex/generic_2.xml
└── other project files...
```

Templify will copy this structure to the `target/template` folder and apply the transformations defined in `maven-templify.yml`.

---

## What makes it powerful?

- 🎯 **XPath JSONPath, YAMLPath, JAva Procjects and Plain Text support** for precise targeting your project nodes
- 🔄 **Batch transformations** across multiple files
- 💼 **Custom parameter references** (e.g., `templify.param.groupId`)
- 🧩 **Composable templates** for different types of services or modules

---

## Learn More

- [Templify Wiki](https://github.com/thoughtworks/templify/wiki)

--

## Contributing

Spotted a bug or have a new use case in mind? We welcome contributions, ideas, and questions. Open an [issue](https://github.com/thoughtworks/templify/issues) or submit a PR!
