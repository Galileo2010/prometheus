### 创建自己的微服务

1. 新建spring-boot项目
   - 基于Gradle创建
   -  选择Web相关配置

2. 配置阿里云maven仓库地址

   ```
   maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
   ```

3. 添加**prometheus**相关依赖

   ```
   compile group: 'io.micrometer', name: 'micrometer-registry-prometheus', version: '1.1.3'
   ```

4. 配置endpoints

   ```
   management.endpoints.web.exposure.include= prometheus,health
   ```

5. 自定义监控指标

   ```
   package com.test.prometheus;
   
   import io.micrometer.core.instrument.Counter;
   import io.micrometer.core.instrument.MeterRegistry;
   import org.springframework.stereotype.Component;
   
   @Component
   public class GetRequestCounter {
       private final Counter counter;
       public GetRequestCounter(MeterRegistry registry){
           counter = Counter.builder("kakaluote.total").description("this is from xiaojun").register(registry);
       }
   
       public void Increment(){
           counter.increment();
       }
   }
   ```

6. 触发指标变化

   ```
       @GetMapping("/home")
       public String home(){
           getRequestCounter.Increment();
           return "hello, xiaojun.";
       }
   ```

### Docker相关设置

- 注册阿里云账号，获取私有加速地址
    ```
    {
    "registry-mirrors": [
        "https://f6u6602f.mirror.aliyuncs.com"
    ],
    "insecure-registries": [],
    "debug": true,
    "experimental": false
    }
    ```
### 启动prometheus服务

1. 拉取prometheus镜像

   ```
   docker pull prom/prometheus
   ```

2. 编写配置文件

   - 编写配置文件prometheus.yml

     ```
     # my global config
     global:
     scrape_interval:     15s # Set the scrape interval to every 15 seconds. Default is every 1 minute.
     evaluation_interval: 15s # Evaluate rules every 15 seconds. The default is every 1 minute.
     # scrape_timeout is set to the global default (10s).
     
     # Load rules once and periodically evaluate them according to the global 'evaluation_interval'.
     rule_files:
     # - "first_rules.yml"
     # - "second_rules.yml"
     
     # A scrape configuration containing exactly one endpoint to scrape:
     # Here it's Prometheus itself.
     scrape_configs:
     # The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
     - job_name: 'prometheus'
         # metrics_path defaults to '/metrics'
         # scheme defaults to 'http'.
         static_configs:
         - targets: ['127.0.0.1:9090']
     
     - job_name: 'spring-actuator'
         metrics_path: '/actuator/prometheus'
         scrape_interval: 5s
         static_configs:
         - targets: ['10.0.75.1:8080']
     ```

   - 共享挂载点所在的驱动器

3. 启动服务

   ```
   docker run -d --name=prometheus -p 9090:9090 -v C:/Users/Galiluck/Desktop/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
   ```

### 启动Grafana服务

- 拉取Grafana镜像并启动服务

  ```
  docker run -d --name=grafana -p 3000:3000 grafana/grafana
  ```

- 添加prometheus数据源

- 添加dashboard

- 配置文件地址

  ```
  /usr/share/grafana/conf/defaults.ini
  ```

- 文件挂载

  ```
  docker run -d --name=grafana -p 3000:3000 -v C:/Users/Galiluck/Desktop/prometheus/defaults.ini:/usr/share/grafana/conf/defaults.ini grafana/grafana
  ```

- 匿名登录

  ```
  参考：https://grafana.com/docs/installation/configuration/
  GF_AUTH_PROXY_ENABLED: true
  
  GF_AUTH_ANONYMOUS_ENABLED: true
  GF_SECURITY_ALLOW_EMBEDDING: true
  
docker run -d -p 3000:3000 --name=grafana -e "GF_AUTH_PROXY_ENABLED=true" -e "GF_AUTH_ANONYMOUS_ENABLED=true" -e "GF_SECURITY_ALLOW_EMBEDDING=true" grafana/grafana
  ```
  
  

### Jenkins使用

- 下载Docker拉取Jenkins镜像

  ```
  docker pull jenkins/jenkins
  ```

- 启动Jenkins服务

  ```
  docker run -d -p 3001:3001 -v ./jenkins:/var/jenkins_home  --name jenkins docker.io/jenkins/jenkins
  ```

  

### Swagger工具

1. 配置springfox-swagger2、springfox-swagger-ui

2. 开启服务，放在application文件同目录下

   ```
   @Configuration
   @EnableSwagger2
   public class SwaggerConfig{
       @Bean
       public Docket createRestApi() {
           return new Docket(DocumentationType.SWAGGER_2)
                   .apiInfo(apiInfo())
                   .select()
                   .apis(RequestHandlerSelectors.basePackage("com.test.prometheus"))
                   .paths(PathSelectors.any())
                   .build();
       }
   
       private ApiInfo apiInfo() {
           return new ApiInfoBuilder()
                   .title("Spring Boot中使用Swagger2构建RESTful APIs")
                   .description("更多请致电TEL:15549070600")
                   .termsOfServiceUrl("")
                   .version("1.0")
                   .build();
       }
   }
   ```

   

3. 使用注解

4. 访问/swagger-ui.html