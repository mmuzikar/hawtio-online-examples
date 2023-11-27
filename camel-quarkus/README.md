# Hawtio-Enabled Camel Quarkus Example

This sample application shows how to make it _hawtio-enabled_ with Camel Quarkus. Once deployed on OpenShift/Kubernetes, it will be discovered by Hawtio Online.

## Highlights

- [pom.xml](pom.xml)

This project uses Quarkus [Container Images](https://quarkus.io/guides/container-image) and [Kubernetes](https://quarkus.io/guides/deploying-to-kubernetes) extensions to build a container image and deploy it to a Kubernetes/OpenShift cluster.

The most important part in terms of the _hawtio-enabled_ configuration is defined in the `<properties>` section. To make it _hawtio-enabled_, the Jolokia agent must be attached to the application with HTTPS and SSL client authentication configured. The client principal should match those the Hawtio Online instance provides (the default is `hawtio-online.hawtio.svc`).

```xml
<properties>
    <jolokia.protocol>https</jolokia.protocol>
    <jolokia.host>*</jolokia.host>
    <jolokia.port>8778</jolokia.port>
    <jolokia.useSslClientAuthentication>true</jolokia.useSslClientAuthentication>
    <jolokia.caCert>/var/run/secrets/kubernetes.io/serviceaccount/service-ca.crt</jolokia.caCert>
    <jolokia.clientPrincipal.1>cn=hawtio-online.hawtio.svc</jolokia.clientPrincipal.1>
    <jolokia.extendedClientCheck>true</jolokia.extendedClientCheck>
    <jolokia.discoveryEnabled>false</jolokia.discoveryEnabled>
</properties>
```

## How to run locally

Run in development mode with:

```console
mvn compile quarkus:dev
```

Or build the project and execute the runnable JAR:

```console
mvn package && java -jar target/quarkus-app/quarkus-run.jar
```

### Running with Jolokia agent locally

You can run this example with Jolokia JVM agent locally as follows:

```console
java -javaagent:target/quarkus-app/lib/main/org.jolokia.jolokia-agent-jvm-2.0.0-M4-agent.jar -jar target/quarkus-app/quarkus-run.jar
```

## How to deploy it to Kubernetes/OpenShift

This example is intended to be used by deploying to a Kubernetes/OpenShift cluster.

To deploy it to a cluster, firstly change the container image parameters in [pom.xml](pom.xml) to fit your development environment. (The default image name is `quay.io/hawtio/hawtio-online-example-camel-quarkus:latest`, which should be pushed to the `hawtio` organisation on [Quay.io](https://quay.io/).)

```xml
    <!--
      Container registry and group should be changed to those which your
      application uses.
    -->
    <quarkus.container-image.registry>quay.io</quarkus.container-image.registry>
    <quarkus.container-image.group>hawtio</quarkus.container-image.group>
    <quarkus.container-image.tag>latest</quarkus.container-image.tag>
```

Then build the project with option `-Dquarkus.container-image.push=true` to push the build image to the container registry you use:

```console
mvn install -Dquarkus.container-image.push=true
```

The resources file for deployment is also generated at `target/kubernetes/kubernetes.yml`. You can use `kubectl` or `oc` command to deploy the application with the resources file:

```console
kubectl apply -f target/kubernetes/kubernetes.yml
```

After deployment is successful and the pod has started, the application log can be seen on the cluster like this:

```console
$ stern -oraw hawtio-online-example-camel-quarkus
+ hawtio-online-example-camel-quarkus-77f7bf4948-74hmb › hawtio-online-example-camel-quarkus
I> No access restrictor found, access to any MBean is allowed
Jolokia: Agent started with URL https://172.17.45.52:8778/jolokia/
 _____________________________________ 
< Hawtio Online Camel Quarkus Example >
 ------------------------------------- 
        \   ^__^
         \  (oo)\_______
            (__)\       )\/\
                ||----w |
                ||     ||

               Powered by Quarkus 3.5.2
2023-11-22 12:37:58,465 INFO  [org.apa.cam.imp.eng.DefaultCamelContextExtension] (main) Detected: camel-debug JAR (Enabling Camel Debugging)
2023-11-22 12:37:58,787 INFO  [org.apa.cam.qua.cor.CamelBootstrapRecorder] (main) Bootstrap runtime: org.apache.camel.quarkus.main.CamelMainRuntime
2023-11-22 12:37:58,788 INFO  [org.apa.cam.mai.MainSupport] (main) Apache Camel (Main) 4.1.0 is starting
2023-11-22 12:37:58,828 INFO  [org.apa.cam.mai.BaseMainSupport] (main) Auto-configuration summary
2023-11-22 12:37:58,828 INFO  [org.apa.cam.mai.BaseMainSupport] (main)     [MicroProfilePropertiesSource] camel.context.name=SampleCamelQuarkus
2023-11-22 12:37:59,003 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Apache Camel 4.1.0 (SampleCamelQuarkus) is starting
2023-11-22 12:37:59,388 INFO  [org.apa.cam.com.qua.QuartzComponent] (main) Setting org.quartz.scheduler.jmx.export=true to ensure QuartzScheduler(s) will be enlisted in JMX
2023-11-22 12:37:59,455 INFO  [org.apa.cam.com.qua.QuartzEndpoint] (main) Job Camel_SampleCamelQuarkus.cron (cron=0/10 * * * * ?, triggerType=CronTriggerImpl, jobClass=CamelJob) is scheduled. Next fire date is 2023-11-22T12:38:00.000+0000
2023-11-22 12:37:59,562 INFO  [org.apa.cam.com.qua.QuartzEndpoint] (main) Job Camel_SampleCamelQuarkus.simple (cron=null, triggerType=SimpleTriggerImpl, jobClass=CamelJob) is scheduled. Next fire date is 2023-11-22T12:37:59.552+0000
2023-11-22 12:37:59,668 INFO  [org.apa.cam.mai.BaseMainSupport] (main) Property-placeholders summary
2023-11-22 12:37:59,668 INFO  [org.apa.cam.mai.BaseMainSupport] (main)     [MicroProfilePropertiesSource] quartz.cron=0/10 * * * * ?
2023-11-22 12:37:59,669 INFO  [org.apa.cam.mai.BaseMainSupport] (main)     [MicroProfilePropertiesSource] quartz.repeatInterval=10000
2023-11-22 12:37:59,670 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Routes startup (started:2)
2023-11-22 12:37:59,671 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started cron (quartz://cron)
2023-11-22 12:37:59,671 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main)     Started simple (quartz://simple)
2023-11-22 12:37:59,671 INFO  [org.apa.cam.imp.eng.AbstractCamelContext] (main) Apache Camel 4.1.0 (SampleCamelQuarkus) started in 667ms (build:0ms init:0ms start:667ms)
2023-11-22 12:37:59,672 INFO  [org.apa.cam.imp.deb.BacklogDebugger] (main) Enabling Camel debugger
2023-11-22 12:37:59,673 INFO  [org.apa.cam.com.qua.QuartzComponent] (main) Starting Quartz scheduler: org.quartz.impl.StdScheduler@604d23fa
Hello Camel! - simple
2023-11-22 12:37:59,849 INFO  [org.apa.cam.com.deb.JmxConnectorService] (Camel (camel-1) thread #1 - CamelJMXConnector) JMX Connector thread started and listening at: service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi/camel
Hello Camel! - cron
Hello Camel! - simple
Hello Camel! - cron
Hello Camel! - simple
```
