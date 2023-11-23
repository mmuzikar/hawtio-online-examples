# Hawtio-Enabled Application Examples for Hawtio Online

[![Build](https://github.com/hawtio/hawtio-online-examples/actions/workflows/build.yml/badge.svg)](https://github.com/hawtio/hawtio-online-examples/actions/workflows/build.yml)

[Hawtio Online](https://github.com/hawtio/hawtio-online) requires applications to be "hawtio-enabled" in order to get detected on OpenShift/Kubernetes. To make them hawtio-enabled, the application containers need to have a configured port named `jolokia` and that exposes the [Jolokia](https://jolokia.org/) API.

This repository provides a collection of examples that demonstrate how you can make an application hawtio-enabled.

## Examples

| Example | Description |
| ------- | ----------- |
| [Camel Quarkus](./camel-quarkus/) | A simple Camel Quarkus application example. |
| [Camel Spring Boot](./camel-springboot/) | A simple Camel Spring Boot application example. |
| [Camel K](./camel-k/) | A simple Camel K integration example. |
