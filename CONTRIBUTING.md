# Contributing guide

**Want to contribute? Great!** 

First, read the [main contributing guide](https://github.com/quarkusio/quarkus/blob/main/CONTRIBUTING.md), it will inform you about the coding guidelines, the IDE configuration or the code style we use.

To format your code make sure you run the following command before submitting a PR:

```shell
mvn net.revelc.code.formatter:formatter-maven-plugin:2.23.0:format
mvn impsort:sort
```