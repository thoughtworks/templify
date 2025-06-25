# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Added

- Add spring example (#54)
- (feat): add kind for directoryHandler (#51)
- 👷 chore(gitActions): added MAVEN_GPG_PASSPHRASE
- 👷 chore(gitActions): added gpg_tty
- :construction_worker: chore(gitActions): added gpg public key
- 👷 chore(gitActions): added gpg keys
- :wrench: build(pom.xml): added scm, description, license and developer for the maven-central

### Changed

- #59 fix(directoryHandlerTest): Adjust to cleanup _replace folder after the mutation run
- Fix pitest errors related to org.pitest:pitest-maven:mutationCoverage (#58)
- Update safe_pr_checks.yml
- Testing PR builds (#57)
- Enforce safe PR policies (#56)
- The checks in pull requests are flaky (#55)
- #52 Update build_deploy_main.yml
- Update pr_build.yml
- :construction_worker: #42 build(gitAction): adjust for nightly build … (#43)
- Update CONTRIBUTION.md details (#45)
- Deep README update (#44)
- :construction_worker: build(pom and gitAction): adjust for nightly build&deploy
- Update README.md
- 👷 chore: Delete .circleci directory
- 👷 chore(circleCI): Comment pipe
- 🔧 chore(pom): Remove properties gpg
- 🔧 chore(pom): Update pom to use gpg keyname
- :wrench: chore(pom): Update pom to use gpg as env
- :construction_worker: chore(gitActions): bump actions versions
- :construction_worker: chore(gitActions): Create gitAction.yml
- :recycle: refact(reources-its): adjust pom.xml from ITs to look for the new groupId
- (dist): update distribution settings
- :memo: chore(readme): update circleCI Badge

## [0.1.1] - 2025-01-30

### Added

- Add token to the badge url.
- Add new status badge README.md
- (chore): add template to !gitignore.
- Added test to check if IT is doing replace on packge
- Add find and replace into java files

### Changed

- #39 transitioning templify (#40)
- Update issue.yml
- Create issue.yml
- (chore): update pom.xml from twlabs to thoughtworks
- Update README.md
- Update README.md description
- (fix): Wiki link
- (chore):Update CircleCI status badge with Templify instead Cookiecutter
- (chore): commit maven deploy until we add in a publi repo
- Create old-text.txt in old template directory
- (refactor): removing cookiecutter mentions
- (refactor): rename Cookiecutter to Templify.
- (fix): kill some mutations.
- Update README.md - adjust CookieCutter for Templify
- Cleaned/deleted some files/folders that are not been used
- Added wiki session
- Create LICENSE
- Chore(readme) Update README.md
- Uptaded to use File.separator instead fixed slash
- Change names of execution id to improve possibles debugs
- [] fix itf integration tests.
- ModifyFileContent now use replaceJavaFilesContent
- Added a replace java for a list of files
- To return a list of Paths instead a Map
- Find content at all java files in the project
- (refactory): renaming its in progress, look the commit files for more details.
- (refactory): rename -> basic_default_options_example
- (refactory): rename -> using_metadata_example
- (refactory): rename -> basic_json_example, generic_java_project_example
- (refactory): configuracao_basica renamed to basic_usage_example
- (fix): unknown mutation error
- (chore): shade reduced pom moved to target folder
- (fix): shade plugin mainClass changed to Hello.java
- (chore): pom.xml removed multiple blank lines, add docstring of mvnrepository on dependencies
- (chore): dependency maven-project removed
- (summary)Add support to picocli, pom.xml refactoried
- (summary): several mutations killed
- (tests): Killed mutations of several files
- (tests): JavaHandlerKind mutations

### Fixed

- Actually executing json handler
- Resolve config uppercase char missing
- Several fix to run IT test

### Removed

- (chore): remove picocli dependencies.
- (chore): removed unused dependencies, junit-engine
- (chore): removed unused dependencies, jackson-dataformat-yaml, sl4f-simple|api
- (chore): removed unused dependencies, junit-api, doxia, mojo-executor, pitest-parent, commons-lang3
- (chore): removed unused dependencies, maven-core, maven-artificat, junit-vintage

## [0.1.0] - 2024-01-16

### Added

- (refactory): add event bus component
- #37 - (feat,chore): add Makefile with some scripts, add some docstrings
- Add jacoco-it and report merged unit and integration coverage data
- Add builder (#37).
- Add runner chain to ease extend the template logic, and extend logger strategy (#37)
- Add settings map
- Add metadata and spec to the FileHandler codetemplate (#37)
- Add extended options in the kind fileHandler
- Add fixture for settings v2 and bump to 2.0.0-SNAPSHOT
- Add extensions attr to options model and tests
- Add model options and tests
- Add google guice.
- #35 - test(IT-sources): added IT file structure to custom prefix and suffix settings
- Add tree output style like jest
- #2 - chore(README.md): added yaml config
- #28 - chore(Mapping): added type when file is null
- #28 - test(JavaHandlerTest): added test for not find not found
- #28 chore(java/): added java test folder
- Added IT for json files
- #22 chore(test_replace_generics_xmls_files): added more complex xmls and subfolders
- #9 test(ProcessadorTest): added test for replace using a map of parameteres
- #9 - test(teste.xml): added a tag to we can modify more than one tag
- #9 test(ProcessadorTest): add test to verify if nodes are not found

### Changed

- (chore): cherry-pick 5079eb4
- (fix): test_running_with_existing_template_directory withou a target folder
- (fix): Makefile ci should run clean install
- (fix): Makefile add ci help
- (tests): KindHandlerBase initial mutations tests
- (refactory): Rename JsonKindHandler -> JsonHandlerKind
- (refactory): Rename YamlHandler -> YamlFileHandler
- (refactory): Rename XMLHandler -> XmlFileHandler
- (refactory): Rename JsonHandler -> JsonFileHandler
- (refactory): Rename JavaHandler -> JavaFileHandler
- (tests): Initial mutations of KindHandlerBase killed!
- (tests): Mutation tests JsonKindHandler
- (tests): Mutation tests YamlHandler, YamlHandlerKind
- (tests): Mutation tests of com.twlabs.services
- (tests): Mutation tests of com.twlabs.services.tasks...
- (tests): LoadConfigurationTaskTests, PluginConfig...
- (tests): PlaceholderSettings, KindHandlerBase, YamlKindHandler and more...
- (refactory): refactoring IT tests cases
- (refactory): refactoring KindHandlerBase
- (refactory): refactoring KindHandlerKind.
- (refactory): refactoring main/java packages.
- (refactory): 2.0.0 major changes
- (fix): script make tdd should run for all files
- (feat): script make tdd now run in watch mode
- (fix): make tdd, fixed no java files found
- (refactor): docstrings, interfaces, logs
- #37 - test(CreateTemplateRquest): Added test for RunnerLogger using MavenLogger to kill mutation
- #37 - test(CreateTemplateRquest): Added test for withLogger using RunnerLogger to kill mutation
- #37 - test(CreateTemplateRequest): Added test at BuildDir to kill mutation
- #37 - test(CreateTemplateRequest): Added test on getDestDir to kill mutation
- #37 - test(CreateTemplateRequest): Added test at getMavenCookiercutterYml to kill mutation
- #37 - test(PlaceholderSettings): Resolve mutation conditional at isEmpty method
- #37 - test(ExecuteStepsTaskTest): Added test to cover unsuported kind
- #37 - refact(ExecuteStepsTaskTest): Added as parameterized test
- #37 - refact(LoadConfigurationTask): Kill mutation test over the configuration condition
- #37 - test(LoadConfigurationTaskTest): Added test to kill mutation on exception
- #37 - refactor(LoadConfigurationTask): Change catch to lookup for a specific exceptions
- #31 - refact(CreateTemplateRequest): Added constructor passing CreateTemplateRequest as parameter
- #37 - refactor(LoadConfigurationTaskTest): Resolve mutation at warn log
- #37 - refactory(CopyProjectTaskTest): reafct to kill info logger mutation
- #37 - refactory(CopyProjectTask): adjust to use FileSystem
- #37 - refactor(FileSystem): Wrapper copyDirectoryStructure from FileUtils
- #37 - refactor(DeleteTemplateIfExistsTaks): Organize AAA
- Adjust to kill mutation test over the log (#37)
- Implement a Wrapper for FileUtils (#37)
- Com/twlabs/mojos/* ignored because issue: https://github.com/khmarbaise/maven-it-extension/issues/310, itf doesn't generated coverage data
- Should use the new KindModelFactory approach
- Refactor(KindExecutor) add KindModelFactory to process StepKindTemplate
- Refactor(mojos) move mojos classes and files to package mojos
- Killed mutation regarding info logs (#37)
- Killed negated conditional
- Refactor(test) add @TempDir DeleteTemplateIfExistsTaskTest
- WIP mermaid architecture. (#37)
- #37 - refact(MojoIT): adjust on config yaml file at replace generics yml file test
- #37 - refact(MojoIT): change thow unsupported file test to send error msg
- #37 - refact(MojoIT): change config yaml file at replace generics xmls test to suport new attributes
- #37 - refact(MojoIT): change config yaml file to suport new attributes
- Test unsupported type of file handler must thrown exception (#37)
- Change Mojo to support multiples files and placeholders (#37)
- Change Mojo/Fixtures to support multiple specs (#37)
- Refactor(fixture) change Mojo/Fixtures to support multiples files and placeholders (#37)
- Config file is read in template folder
- Refactor Mojo to support new v2 api
- Refactory all fixtures to use the config v2
- Refactor(FileHandler) renames
- Feat(2.0.0) add type param to file handler metadata
- StepsKindTemplate refactor to be simple
- Initial classes to support configuration v2
- WIP renames to support v2
- New maven-template-settings add metadata and specs
- Refactor(rename,docs) add docstrings and important renames
- Initial changes to support options and extended options in file handlers
- #33 - feat(Placeholder): Added feat to make surei/change that the first parameter before .(dot) is with lower case
- #33 - chore(CookieCutterMojoIT): Adjust the expected output over IT to work with the first parameter as lowercase
- #33 - test(ConfigReaderTest): Added test to validate lowercase for the first attribute at the placeholder
- #33 - chore(test_lower): Added config file to test lower case cenario
- - chore(pom): adjust to see stacktrace case of error
- #33 - test(ConfigReaderTest): Added test to verify if the placeholder name is ensured to be lowercase
- #35 - feat(CookieCutterMojo): Added the generic placeholder using settings from yaml config file
- #35 - feat(JavaHandler): Added a configurable prefix and suffix using the placeholder object
- #34 - test(CookieCutterMojoIT): Adjust IT to be with configurable prefix and suffix
- #35 - refact(JavaHandler): Refact to decople the method find
- #35 - feat(CookieCutterMojo): Implemente custom prefix and suffix settings for placeholder
- #35 - test(CookieCutterMojoIT):Added IT for custom prefix and suffix settings
- #35 - feat(CookieCutterMojo): Implemented defautl placeholder from settings at config file
- #35 - fix(IT-source): Added at pom properties for Java 8 be the minimnal requeriment to build
- #35 - test(CookieCutterMojoIT): Addded IT test for default suffix and prefix
- #35 - test(CookieCutterMojoIT): Added test to check if it is using default placeholder settings
- #35 - test(IT): Added project for IT test using default placeholder
- #35 - fix(IT pom): adjust some poms to execute with java greather then 7
- #35 - feat(PluginConfig): Added settings to be parse at yaml config file
- #35 - test(ConfigReaderTest): Added test read settings at yaml file
- #35 - test(various): Implemented various test to Settings at yaml
- #35 chore(Settings): Added to model prefix and suffix at Placeholder for Settings
- #35 test(ConfigReaderTest): Added test to validate generics prefix and suffix
- #35 - test(ConfigREaderTest): Added first to added prefix and suffix settings
- Update README.md
- Docs(CONTRIBUTION.md) add initial text
- #2 - chore(README): Start describing Java and package functionality
- Updated config.yml
- Release(0.1.2) fix JavaHandler issues
- #32 - fix(JavaHandler) File separator must be quoted when used as regex
- #32 - test(CookieCutterMojoIT): Added new test to reproduce de the error
- #32 - test(IT-structure): Added a new IT test to reproduce the error
- #31 - chore(CookieCutterMojo): Removed unnecessary comments
- #31 - fix(CookieCutterMojoIT): Resolve problem that it was not reading the warn message
- #31 - fix(IT): missing files and folder structure to test
- #31 - feat(CookieCutterMojo): Delete previous template dir
- #31 - test(CookieCutterMojoIT): Added IT for running plugin with a existing template dir
- New release with new config file name -> maven-cookietcutter.yml
- #30 - fix(CookieCutterMojo): Replace config file from template.yml to maven-cookiecutter.yml
- #30 - test(CookieCutterMojoIT-Files): Replace config file name from template.yml to maven-cookiecutter.yml
- #29 - fix(YamlReader): Added Frienldy msg without internal stake trace
- #29 - chore(CookieCutterMojoIT-Folders): Added config file at integration testes scenarios
- #29 - fix(YamlReader): Added friendly exception when config file could not be read
- #29 - fix(YamlReader): Throw exception when the config file is missing
- #29 test(ConfigReaderTest): Added test for when config file is missing
- Chore(example)
- Update pom.xml to snapshot 0.0.5
- Update README.md for XML and JSON
- Java package handler
- #28 - fix(JavaHandler): Adjustment made to regex parse {{ and }}
- #28 - refact(JavaHandler): Code cleanup and responsability decoupling in the replace method
- #28 - feat(CookieCutterMojo): Added support for java structure
- #28 - chore(JavaHandler): find method check if it is a replace now (need refact)
- #28 - test(ConfigReaderTest): Resolve mutation problem
- #28 - test(CookieCutterMojoIT): Added test for the feature Java handler
- #28 - fix(JavaHandlerTest): Resolve problem with the garbage folder at the end of mutation test
- #28 - fix(JavaHandler): Resolve problem when the query has dot
- #28 test(JavaHandlerTest): Added test to verify the replace package
- #28 - refact(JavaHandlerTest): clean test for maps
- #28 - fix(JavaHandlerTest): Problem with match not found inside of Java files
- #28 - feat(JavaHandler): Implemented find method
- #28 - feat(JavaHandler): Implemented replace with map
- #28 test(JavaHandlerTest): Add test for replace with map
- #28 fix(JavaHandler): Test fix when do not have java folder
- #28 -fix(.tmp): mistake file
- #2 - fix(README): Init translation to english
- Chore(ci) auth gcloud-cli
- Chore(ci) install gcloud-cli
- Chore(ci/cd) - #merge, add deploy job, circleci not supporting third-party orbs.
- Chore(ci/cd) - add deploy job, circleci not supporting third-party orbs.
- Chore(ci/cd) - added mvn deploy circleci
- Chore(mvn) release 0.0.3, version bumped to 0.0.4-SNAPSHOT
- Chore(deploy) add release repository to pom.xml
- Chore(deploy) add snapshot repository to pom.xml
- #28 -feat(JavaHandler): Add replace for java file
- Refactor(code) smalls changes for SRP and CCP
- Refactor(code) make setPlaceholders open-closed
- Refactor(smells) small changes
- Refactor(smells) try-with-resources don't need closes
- #24 - feat(CookieCutterMojo): file type validation
- #24 - test(CookieCutterMojoIT): teste for unsupported file type
- #24 - refact(CookieCutterMojo): Change setXmlPlaceHolder and setYamlPlaceHolder to setPlaceHolder
- #24 - feat(CookieCutterMojoIT): Added yaml handler
- #24 - feat(YamlHandler): Implemented replace method
- #24 - test(YamlHandlerTest): Implemented test for replace
- #24 - chore(FileHandlerException): Added a new constructor
- #24 - feat(YamlHandler): Implemented find
- #24 - test(YamlHandlerTest): Added find test
- #24 -chore(YamlHandler): Init Yaml handler
- Organize packages and classes
- #25 - chore(CookieCutterMojo): Adjust to make the Mojo responsible to inserte mustache
- #25 - fix(XMLHandler): Adjust replace to inserte mustache
- #25 - chore(template.yml): adjust at some names
- #25 - chore(CookiecutterMojoIT): Adjust test to check mustache
- #25 - chore(XMLHandlerTest): Adjust test to check mustache
- #22 -test(CookieCutterMojoIT): Added genirics xmls test with subfolders directories
- #23 - fix(CookieCutterMojo): start to use copyDirectoryStructure instead of copyDirectory
- #22 - feat(CookieCutterMojo): Now handle the xml mapped at template.yml
- #22 - chore(test_replace_default_pom_file): Added folder structure to IT
- #22 - test(CookieCutterMojoIT): XML reader test from a default pom.xml
- #20 - chore(ConfigReaderTest): Implemented and adjust the config reader for yaml
- 19 - refactor(YamlMappings -> PluginConfig): Renamed the class name
- #19 - refactor(packages) add services, interfaces and model packages
- Chore(merge) fix test merged by mistake
- #19 - refactor(miscellaneous): Refactor to cover some tests and classes reponsabilities
- #19 - feat(pom): Added snakeYaml
- #19 - test(ConfigMappingsTest): Added validation of a List of Translation at a Mapping
- #19 - test(ConfigMappingsTest): Validating Translation object construction
- #19 - test(ConfigMappingsTest): Validating Mapping object construction
- #19 - test(ConfigMappingsTest): Validating set Mappings
- #19 - feat(YamlMappings): Basic implementation of ConfigMappings
- #19 - feat(ConfigMappings): Added the interface responsible for the config mapping
- #19 - feat(ConfigMappingsTest): Added a test to implement config mapping
- Change class name
- Change the class name
- #17 chore(ProcessadorXML): Adjust to implement the interface without replacedValuePath
- #17 - chore(JsonHandler): Adjust to implement the interface without replacedValuePath
- #17 - chore(JsonHandler): Adjust test cenarios to remove replacedValuePath parameter
- #17 - chore(ProcessadorTest): Removed replacedVaulePath parameter from test cenarios
- #17 - chore(FileHandler): Removed replacedValuePath parameter from the interface
- #14 - test(JsonHandler) some tests with Mojo
- Chore (multiple files): Rename HandlerFiles to FileHandler
- Organize imports
- Made some adjustment to the test code be cleanner
- refactor(ProcessadorXML and ProcessadorTest): Adjust to cover mutation test
- Refactored to enable mutant testing, removed the flaky test
- chore(JsonHandler): Adjust at a Exception to pass at UT
- Chore(ProcessadorXML) add mutations
- #14 - chore(pom.xml) fix typo
- Refactoring(codereview) add mutation for all unit tests, removed unused imports, removed build warnings
- #14 - chore(config) add coverage circleci
- #14 - tests(JsonHandler) replace should work with strings and maps
- Chore(config) add mutation tests, God help us
- #14 - test(JsonHandler) replace must work with strings
- #15 - refactor(ProcessadorXML and JsonHandler): Replace exception for HandleFilesException
- #15 - refactor(ProcessadorTest): Adjust to treat HandlerFilesException
- #15 - refactor(HandlerFiles): Add throws HandlerFilesException
- #14 - test(JsonHandler) must throw unsupported excep for objects and lists
- #14 - refactor(Jsonhandler) removed NodeList from interface
- #14 - refactor(ProcessadorXML): Implemented the new type of find return (Map<String><String>)
- #14 - chore(teste.xml): Add new tags to support more complex test scenarios
- 4 - chore(HandlerFiles): Remove return type from NodeList and changed to a Map
- #14 - chore(ProcessadorTest): Update test methods to check if the HandlerFiles is returning a Map
- #14 - feat(JsonHandler) add some testing scenarios
- #14 - feat(JsonHandler) add class and initial tests
- #11 - chore(config) add labels and assignees
- #11 - chore(config issue) missing discussing tab, add temporally the wiki url
- #11 - chore(issue template) add bug and feature issue templates
- #9 - chore(ProcessarodXML): Implemented replace for multiples parameters using Map
- #9 - fix(ProcessadorXML): Resolve issue of change the Node value
- #9 - reafactor(ProcessadorTest): Validating 1 or more tags during the replace
- #9 chore(ProcessadorXML): Add RuntimeExpection when the tag was not found during a replace
- #9 - test(ProcessadorTest): Included test to validate not find tags during replace
- #9 - feat(ProcessadorXML): Implmentado a interface e faltando o replace por map
- #9 - test(ProcessadorTest): Classe inicial de teste, realizando find e parse. Falta parse de um map
- #9 - refactor(ProcessadorXMLTest): Foi removido pois será teremos testes de interface
- #9 - refactor(Processador): Virou FileHandler
- #9 - refactor(HandlerFiles): feito ajustes de nomeclatura e assinaturas de métodos
- #9 - chore(processadorXml) criacao interface e testes iniciais
- #9 - refactor(ProcessadorXMLTest): refatarado para testar nós não encontrados
- #9 - refactor(ProcessadorXML): refatorado para lançar exception para nós não encontrados
- #9 - feat(ProcessadorXML): implementado busca por parametro e parametros
- #9 - test(ProcessadorXMLTest): valida se o arquivo existe, nó e nós
- Criado método especializado em realizar cópia do projeto
- #6 test(CookieCutterMojoIT): Atualizado para o teste integrado verificar se o arquivo pom foi copiado
- #6 chore(CookieCutterMojo): Atualizado para o execute copiar os arquivos para o target template
- #6 chore(pom): adicionado dependencia e plugin para copiar arquivos do projeto
- #6 test(CookieCutterMojoIT): Alterada o metodo que testa o build para ver se o pom foi copiado
- #6 - test(CopiarProjetosTest): Feita estrutura inicial para validar a copia do pom no target
- Update README.md
- Update README.md
- #5 fix(CookieCutterMojo): Alterado o goal para cutter

### Fixed

- (tests): fix wrong spied test, add clean makefile
- (test): fix test_generic_java_project/maven-cookiecutter.yml
- Mock xmlHandler execute
- Created classes for fixture dependencies
- Test(FileHandlerKindModelFactory) missing values should throw IllegalArgumentsExecption
- Test(RunnerDefaultTest) should assurance the right task sequence execution
- Test(ExecuteStepsTaskTest) refactoring test methods
- Test(ExecuteStepsTaskTest) should execute fileHandler kind executor
- Added test class
- Copy project to a tmpDir properly. (#37)
- Configurations must be loaded properly. (#37)
- Java and MavenLog facade must call the internal methods properly. (#37)
- Test if MavenLogger and Java Util Logger is created. (#37)
- Change Logger strategy (#37)
- Added snapshot to resolve build problems
- Update to use File.separator
- Remove '.' from the return getFileExtensions
- Adjust unsupported file test, to use plain() instead error()
- Fix(dep) colission of jackson in faker/snakeyaml/yamlpath
- Added test to cover placeholder object
- Adjust at exception path import
- Adjust package path
- Fix(CookieCutterMojo) - refactoring copy project method to use the Java File Api
- Fix(circleci) - #7 orbs/maven nao suporta multiplos result paths, movido failsafe para surefire-reports/
- Fix(circleci) - #7 lista de results path deve ser string
- Fix(circleci) - #7 add plugin de report do failsafe e adicionado caminho de test_result no orbs/maven

### Removed

- Removed legacy tests
- #28 - reafact(JavaHandler): removed system.out
- Removed from project
- Chore (multiple): removed method set from model classes
- #15 - refactor(ProcessadorTest): remove RuntimeException and add HandlerFilesException

## [0.0.0] - 2023-04-06

### Added

- Add .circleci/config.yml

### Changed

- #4 - chore(circleci) fix surefire-plugin version
- #4 - chore(circleci) require maven/test
- #4 - chore(circleci) definicao de job para salvar relatorios de tests
- #4 - chore(circleci) add step para salvar resultados dos testes
- #4 - chore(circleci) upgrade maven-site-plugin -> 3.12.1
- #4 - chore(circleci) add dependencias do surefire reports
- #4 - chore(circleci) add surefire reports
- #4 - chore(circleci) fix pom.xml, maven-plugin-plugin
- #4 - chore(circleci) upgrade maven-compiler-plugin para 3.11.0
- #4 - chore(circleci) add -X ao comando tests
- #4 - chore(circleci) alterado para usar job maven/test
- #4 - chore(circleci) alterado nome do job maven/test -> maven
- #4 - chore(circleci) removido job checkout
- #4 - chore(circleci) add maven/with_cache
- #4 - chore(circleci) configuracoes do executor com jdk11.
- #4 - chore(circleci) add badge ao readme.
- #4 - fix(circleci) comando de testes nao estava buildando
- #4 - chore(circleci) maven-plugin-plugin fix index of bound em maven < 3.9 + jdk11
- #4 - chore(circleci) add job de verify com orb maven
- #1 test(CookieCutterMojoIT): Criada asserções basicas e que validam a uma build do plugin
- #1 chore(): Configuração do framework Integration Testing Framework (ITF)
- Limpeza no pom.xml para utilizar só as dependencias realmente necessárias
- Adicionado o arquivo .tool-verions
- #1 - chore(tests) o metodo lookupMojo precisa de uma tag configuration no projeto de testes, ao menos vazia. O lookupEmptyMojo funciona igual lookupMojo mas nao necessita da declaracao do plugin no build do projeto de testes
- #1 - chore(tests) configuracao do harness com junit5
- #1 - chore(project) habilitando framework de tests de Mojo
- "#1 - chore(): estrutura inicial do maven plugin e leitor de arquivo"
- Initial commit

[unreleased]: https://github.com/thoughtworks/templify/compare/v0.1.1..HEAD
[0.1.1]: https://github.com/thoughtworks/templify/compare/v0.1.0..v0.1.1
[0.1.0]: https://github.com/thoughtworks/templify/compare/v0.0.0..v0.1.0

<!-- generated by git-cliff -->
