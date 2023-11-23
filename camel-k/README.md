# Camel K Example

[Camel K](https://camel.apache.org/camel-k/2.1.x/index.html) is _hawtio-ready_ by default.

All you need to make it _hawtio-enabled_ is to turn on [Jolokia trait](https://camel.apache.org/camel-k/2.1.x/traits/jolokia.html) for a Camel K integration.

```console
kamel run hello.java -t jolokia.enabled=true --name hawtio-online-example-camel-k
```
