# MTA analysis using Sonata Serverless Workflow

## Description

This is a port of Parodos workflow written entirely in serverless workflow with sonataflow.

It performs consecutive REST invocations defined as functions.  

## Installing and Running

### Prerequisites
 
You will need:
  - Java 11+ installed
  - Environment variable JAVA_HOME set accordingly
  - Maven 3.8.1+ installed

When using native image compilation, you will also need: 
  - [GraalVm](https://www.graalvm.org/downloads/) 20.2.0+ installed
  - Environment variable GRAALVM_HOME set accordingly
  - Note that GraalVM native image compilation typically requires other packages (glibc-devel, zlib-devel and gcc) to be installed too.  You also need 'native-image' installed in GraalVM (using 'gu install native-image'). Please refer to [GraalVM installation documentation](https://www.graalvm.org/docs/reference-manual/aot-compilation/#prerequisites) for more details.

### Compile and Run in Local Dev Mode

```sh
mvn clean package quarkus:dev
```

### Compile and Run in JVM mode

```sh
mvn clean package 
java -jar target/quarkus-app/quarkus-run.jar
```

or on Windows

```sh
mvn clean package
java -jar target\quarkus-app\quarkus-run.jar
```

### Compile and Run using Local Native Image
Note that this requires GRAALVM_HOME to point to a valid GraalVM installation

```sh
mvn clean package -Pnative
```
  
To run the generated native executable, generated in `target/`, execute

```sh
./target/sw-quarkus-greeting-{version}-runner
```

### Submit a request

To invoke an MTA analysis using this workflow make sure you have an MTA service running.
Specify the endpoint in workflow spec under the function section `src/main/resources/mta.sw.yaml`

```yaml
// showing only the functions section
functions:
- name: getApplication
  type: custom
  operation: rest:get:https://mta.example.org:443/hub/applications
```

Execute the flow using curl with this payload:

```sh
curl -XPOST -H "Content-Type: application/json" http://localhost:8080/MTAAnalysis -d '{"repositoryURL": "https://github.com/your/repo"}'
```


## Deploying with Kogito Operator

In the [`operator`](operator) directory you'll find the custom resources needed to deploy this example on OpenShift with the [Kogito Operator](https://docs.jboss.org/kogito/release/latest/html_single/#chap_kogito-deploying-on-openshift).
