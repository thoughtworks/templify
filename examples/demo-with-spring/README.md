##  Demo: Using Templify + Cookiecutter to Create Reusable Templates with Spring Boot

This demo showcases how to convert a hello world Spring Boot project into a reusable template using Templify and then inflate that template with Cookiecutter.

### Overview
First, we use Templify to mark files and insert placeholders.
Then, we use Cookiecutter to inflate the template with specific values, generating a ready-to-use project.


### Structure
```
├── project/                       # Original project (Spring Boot example)
├── cookiecutter_inflate/          # Cookiecutter template
│   ├── cookiecutter.json          # Defines dynamic fields
│   └── {{ cookiecutter.project_slug }}/
└── README.md
```

### Cookiecutter Parameters
This is what we have in our cookcutter config file, and for what we are going replace the place holders during the inflate

#### cookiecutter.json
```
{
  "project_name": "templify_example_with_cookiecutter",
  "project_slug": "{{ cookiecutter.project_name.lower().replace(' ', '_') }}",
  "group_id": "project.new.id",
  "project_version": "0.0.1",
  "project_description": "This is very new fancy description",
  "template_demo_package": "com.project",
  "get_mapping": "/project-get",
  "return_get": "My new Hello World!"
}
```

### How Templify Processes the Project
The `maven-templify.yml` file defines what to replace and how:

#### in `pom.xml` (XMLHandler):
* /project/artifactId → {{ cookiecutter.project_name }}
* /project/groupId → {{ cookiecutter.group_id }}
* /project/version → {{ cookiecutter.project_version }}
* /project/description → {{ cookiecutter.project_description }}

#### in Java files (JavaHandler):
* com.example.demo_with_spring → {{ cookiecutter.template_demo_package }}

#### in plan text strings (PlainTextHandler):
* "/hello" → {{ cookiecutter.get_mapping }}
* "Hello World" → {{ cookiecutter.return_get }}

### Step-by-Step Instructions
#### 1. Generate the Template with Templify
```bash
cd project
mvn com.thoughtworks.templify:templify:create
```
This generates a target/template folder with placeholder values applied.

#### 2. Copy Files into the Cookiecutter Template
```bash
cp -R target/template/* ../cookiecutter_inflate/\{\{\ cookiecutter.project_slug\ \}\}/
```
Use exactly `\{\{ cookiecutter.project_slug \}\}` — Cookiecutter inflates this folder name later.

#### 3. Remove Unnecessary Files
```bash
rm ../cookiecutter_inflate/\{\{\ cookiecutter.project_slug\ \}\}/maven-templify.yml
cd ..
```
Optionally remove .mvn/, target/, etc., if included.

#### 4. Inflate the Template with Cookiecutter
```bash
cookiecutter cookiecutter_inflate

```
You'll be prompted to fill in values like:

```less
project_name [templify_example_with_cookiecutter]: my-spring-service
group_id [project.new.id]: com.mycompany
template_demo_package [com.project]: com.mycompany.my_spring_service
get_mapping [/project-get]: /hello
return_get [My new Hello World!]: Hello, World!
```

## Final Results
You get a ready-to-run Spring Boot project with:

* Custom Java package
* Custom endpoint (/hello)
* Customized response (Hello, World!)
* Clean, dynamic project metadata in pom.xml
