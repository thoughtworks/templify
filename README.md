[![CircleCI](https://dl.circleci.com/status-badge/img/gh/twlabs/Cookiecutter-Templater-for-Backstage/tree/main.svg?style=svg&circle-token=32f338c23bd26fde500c4e1aaf59bfd5b7b04c6f)](https://dl.circleci.com/status-badge/redirect/gh/twlabs/Cookiecutter-Templater-for-Backstage/tree/main)

---

# Cookiecutter Templater for Backstage

<!--toc:start-->
- [Cookiecutter Templater for Backstage](#cookiecutter-templater-for-backstage)
<!--toc:end-->

---

# Sitação atual do plugin - Status
O plugin está em fase de desenvolvimento, ainda não está pronto para uso para `early stage` (estágio inicial de adoção e valição).

# Instalação

## Requisitos
Os requisitos básicos para utilizar o plugin `cookiecutter-templater-for-backstage` são os seguintes:

* JDK8+
* Apache Maven 3..1

## Configuração do Maven
Deverá ser incluido o `cookiecutter-templater-for-backstage` no `pom.xml` do projeto

```xml
<project>
  ...
    <build>
        <plugins>
            <plugin>
                <groupId>com.twlabs</groupId>
                <artifactId>cookiecutter-templater-maven-plugin</artifactId>
                <version>@project.version@</version>
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
</project>
```
## Validação da instalação

# Colaboração

