<p align="center">
  <a href="https://dataflow.spring.io/">
    <img alt="Spring Data Flow Dashboard" title="Spring Data Flow" src="https://i.imgur.com/hpeKaRk.png" width="450" />
  </a>
</p>

[![Build Status - CI](https://github.com/spring-cloud/spring-cloud-dataflow/actions/workflows/ci.yml/badge.svg)](https://github.com/spring-cloud/spring-cloud-dataflow/actions/workflows/ci.yml)


*Spring Cloud Data Flow* is a microservices-based toolkit for building streaming and batch data processing pipelines in
Cloud Foundry and Kubernetes.

Data processing pipelines consist of Spring Boot apps, built using the [Spring Cloud Stream](https://github.com/spring-cloud/spring-cloud-stream)
or [Spring Cloud Task](https://github.com/spring-cloud/spring-cloud-task) microservice frameworks. 

This makes Spring Cloud Data Flow ideal for a range of data processing use cases, from import/export to event streaming
and predictive analytics.

----

## Building

### Step 1: Build spring-cloud-dataflow-server to jar file

    ./mvnw install -s .settings.xml -DskipTests -T 1C -am -pl :spring-cloud-dataflow-server,:spring-cloud-dataflow-composed-task-runner

### Step 2:
- In folder `/spring-cloud-dataflow-server/target`, rename file build to `spring-cloud-dataflow.jar`
- In folder `/spring-cloud-dataflow-composed-task-runner/target`, rename file build to `scdf-composed-task-runner.jar`

### Step 3:
- Move file build `spring-cloud-dataflow.jar` to folder `/scdf-bitnami-container/build/spring-cloud-dataflow/`
- Move file build `scdf-composed-task-runner.jar` to folder `/csdf-ctr-bitnami-container/build/spring-cloud-dataflow-composed-task-runner/`

(Nếu không có folder `.../build/spring-cloud-dataflow/` hoặc `.../build/spring-cloud-dataflow-composed-task-runner/` thì có thể tự tạo)

### Step 4: 
- Build docker image in folder `scdf-bitnami-container` by command:
    
      docker build --no-cache -t <repository>/spring-cloud-dataflow-server:<tag> ./

- Build docker image in folder `scdf-ctr-bitnami-container` by command:

      docker build --no-cache -t <repository>/spring-cloud-dataflow-composed-task-runner:<tag> ./

## Cấu trúc thư mục scdf-bitnami-container

```
scdf-bitnami-container/
│
├── build/
│   └── spring-cloud-dataflow/
│       └── spring-cloud-dataflow.jar                       # File build you just moved to
│       
├── prebuildfs/                                             # Prebuilt filesystem components
├── rootfs/                                                 # Root filesystem configurations
│
├── docker-compose.yml                                      # Docker Compose configuration file
├── Dockerfile                                              # Dockerfile to build the container image
└── tags-info.yaml                                          # YAML file with tag-related information
```

## Cấu trúc thư mục scdf-ctr-bitnami-container:

```
scdf-ctr-bitnami-container/
│
├── build/
│   └── spring-cloud-dataflow-composed-task-runner/
│       └── scdf-composed-task-runner.jar                   # File build you just moved to
│       
├── prebuildfs/                                             # Prebuilt filesystem components
├── rootfs/                                                 # Root filesystem configurations
│
├── docker-compose.yml                                      # Docker Compose configuration file
├── Dockerfile                                              # Dockerfile to build the container image
└── tags-info.yaml                                          # YAML file with tag-related information
```

## Notes (chỉ dành cho dev, không dành cho devops)

1. Source scdf gốc của Spring cloud: https://github.com/spring-cloud/spring-cloud-dataflow
2. Source container scdf của bitnami: https://github.com/bitnami/containers/tree/main/bitnami/spring-cloud-dataflow/2/debian-12
3. Source container scdf composed task runner của bitnami: https://github.com/bitnami/containers/tree/main/bitnami/spring-cloud-dataflow-composed-task-runner/2/debian-12
4. Nếu muốn update version mới của scdf, clone source 1 trước, sau đó clone source 2 và move tất cả content source 2 vào folder `scdf-bitnami-container`, cuối cùng build như các step trên (nếu ko có folder `scdf-bitnami-container` thì tự tạo theo cấu trúc thư mục như trên).
5. Nếu muốn update version mới của scdf composed task runner, clone source 1 trước, sau đó clone source 3 và move tất cả content source 3 vào folder `scdf-ctr-bitnami-container`, cuối cùng build như các step trên (nếu ko có folder `scdf-ctr-bitnami-container` thì tự tạo theo cấu trúc thư mục như trên).
6. Hạn chế update source container của bitnami vì cần phải fix một cố code để tương thích với điều kiện hiện có.
