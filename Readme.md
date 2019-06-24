```
maven { url 'http://maven.aliyun.com/nexus/content/groups/public/' }
// https://mvnrepository.com/artifact/io.micrometer/micrometer-registry-prometheus
    compile group: 'io.micrometer', name: 'micrometer-registry-prometheus', version: '1.1.3'
```
### 配置阿里云镜像加速
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
### 拉取prometheus镜像
    ```
    docker pull prom/prometheus
    ```
### prometheus配置文件
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
- 需要共享挂载点所在的驱动器
### 启动docker
```
docker run -d --name=prometheus -p 9090:9090 -v C:/Users/Galiluck/Desktop/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus --config.file=/etc/prometheus/prometheus.yml
```
### 拉取Grafana镜像
```
docker run -d --name=grafana -p 3000:3000 grafana/grafana
```