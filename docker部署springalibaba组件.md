# docker部署

# nacos单机

```
# 启动docker镜像
docker run --env MODE=standalone --name nacos -d -p 8848:8848 nacos/nacos-server:1.1.4
# 进入nacos
docker exec -it nacos /bin/bash
# 修改conf里面的application.properties
cd conf
vim application.properties
#修改数据库信息做持久化配置
```

![1591622836477](C:\Users\11985\AppData\Roaming\Typora\typora-user-images\1591622836477.png)

# nacos集群

### 查看地址

```
docker inspect n【容器名】 | grep IPAddress
```

### 第一个

```
docker run \
  --name nacos-1 \
  --hostname=nacos-1 \
  -d \
  -e MYSQL_MASTER_SERVICE_HOST=192.168.216.132 \
  -e MYSQL_MASTER_SERVICE_PORT=3306 \
  -e MYSQL_MASTER_SERVICE_DB_NAME=nacos_config \
  -e MYSQL_MASTER_SERVICE_USER=root \
  -e MYSQL_MASTER_SERVICE_PASSWORD=123456\
  -e SPRING_DATASOURCE_PLATFORM=mysql \
  -e MYSQL_DATABASE_NUM=1 \
  -e NACOS_USER=nacos\
  -e NACOS_PASSWORD=nacos\
  #docker此处填容器地址，或者做地址映射，修改docker网卡
  -e NACOS_SERVERS=172.17.0.2:8848,172.17.0.3:8848,172.17.0.4:8848\
  -e JVM_XMS=512m \
  -e JVM_XMX=512m \
  -e JVM_XMN=256m \
  -e JVM_MS=32m \
  -e JVM_MMS=80m \
  -p 8801:8848 \
  nacos/nacos-server:1.1.4
```

### 第二个

```
docker run \
  --name nacos-2 \
  --hostname=nacos-2 \
  -d \
  -e MYSQL_MASTER_SERVICE_HOST=192.168.216.132 \
  -e MYSQL_MASTER_SERVICE_PORT=3306 \
  -e MYSQL_MASTER_SERVICE_DB_NAME=nacos_config \
  -e MYSQL_MASTER_SERVICE_USER=root \
  -e MYSQL_MASTER_SERVICE_PASSWORD=123456\
  -e SPRING_DATASOURCE_PLATFORM=mysql \
  -e MYSQL_DATABASE_NUM=1 \
  -e NACOS_USER=nacos\
  -e NACOS_PASSWORD=nacos\
  -e NACOS_SERVERS=172.17.0.2:8848,172.17.0.3:8848,172.17.0.4:8848\
  -e JVM_XMS=512m \
  -e JVM_XMX=512m \
  -e JVM_XMN=256m \
  -e JVM_MS=32m \
  -e JVM_MMS=80m \
  -p 8802:8848 \
  nacos/nacos-server:1.1.4
```

### 第三个

```
docker run \
--name nacos-3 \
  --hostname=nacos-3 \
  -d \
  -e MYSQL_MASTER_SERVICE_HOST=192.168.216.132 \
  -e MYSQL_MASTER_SERVICE_PORT=3306 \
  -e MYSQL_MASTER_SERVICE_DB_NAME=nacos_config \
  -e MYSQL_MASTER_SERVICE_USER=root \
  -e MYSQL_MASTER_SERVICE_PASSWORD=123456\
  -e SPRING_DATASOURCE_PLATFORM=mysql \
  -e MYSQL_DATABASE_NUM=1 \
  -e NACOS_USER=nacos\
  -e NACOS_PASSWORD=nacos\
  -e NACOS_SERVERS=172.17.0.2:8848,172.17.0.3:8848,172.17.0.4:8848\
  -e JVM_XMS=512m \
  -e JVM_XMX=512m \
  -e JVM_XMN=256m \
  -e JVM_MS=32m \
  -e JVM_MMS=80m \
  -p 8803:8848 \
  nacos/nacos-server:1.1.4
```

# sentinel

## 1 矫正虚拟机时间

 https://jingyan.baidu.com/article/2d5afd6913ad5b85a2e28e98.html 

## 2 拉取镜像

```
docker pull bladex/sentinel-dashboard：1.7.0
```

## 3 启动

```
docker run --name sentinel  -d -p 8858:8858 -d -v /etc/localtime:/etc/localtime -e "TZ=Asia/Shanghai"  bladex/sentinel-dashboard:1.7.0
```

-v /etc/localtime:/etc/localtime 设置docker容器与本机时间一致

-e "TZ=Asia/Shanghai" 设置时区与本机时间一致

只有当服务器时间与本机时间一致时，sentinel的实时监控才能正常使用

<img src="E:\Java学习\springcloud\sentinel持久化配置.png" alt="sentinel持久化配置" style="zoom:100%;" />

# seata

```
docker run --name seata-server -p 8091:8091  -e SEATA_IP=192.168.216.132  -e SEATA_PORT=8091  seataio/seata-server:1.0.0
```

-e SEATA_IP=192.168.216.132  设置注册ip，不设置则默认为docker容器ip， 外部机无法访问

```
#进入seate
docker exec -it seata-server /bin/sh 
#修改file.conf，将日志放入数据库，配置数据库
cd resources/
vi file.conf
i #进入修改模式 esc退出修改模式
：wq #保存并退出
```

![1591622551284](C:\Users\11985\AppData\Roaming\Typora\typora-user-images\1591622551284.png)

```
#修改registry.conf
vi registry.conf 
i #进入修改模式 esc退出修改模式
：wq #保存并退出
```

![1591622711068](C:\Users\11985\AppData\Roaming\Typora\typora-user-images\1591622711068.png)