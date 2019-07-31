功能：消费kafka中的数据，然后写入influxdb

环境：
 springboot，log4j2，jackson
 influxdb1.7.7, influx-java 当前最新的发布版本是2.15， 我是自己用最新的源码自己package的 版本2.16-SNAPSHOT，这个版本里面增加了注解@TimeColumn来标记time字段
 
 influxdb的文档也比较全，influxdb-java 上github的说明也很详细，
 https://github.com/influxdata/influxdb-java/blob/master/src/test/java/org/influxdb/InfluxDBTest.java说明了各种用法
 
 kafka最近的版本是kafka_2.12-2.3.0 ，kafka的官方文档比较全，学习起来比较方便。每个版本都有对应的文档，文档里面都有对应的java api
