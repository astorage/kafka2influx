package com.java.kafkainfluxdb;

import org.influxdb.BatchOptions;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class InfluxDbClient {

    private static final Logger logger = LoggerFactory.getLogger(InfluxDbClient.class);

    private static final String TIME = "time";

    @Value("${influxdb.url}")
    private String influxdbUrl;
    @Value("${influxdb.dbname}")
    private String dbName;
    @Value("${influxdb.tbname}")
    private String tbNmae;

    private InfluxDB influxDB;

    @PostConstruct
    public void init() {
        influxDB = InfluxDBFactory.connect(influxdbUrl, " ", " ");
        influxDB.setDatabase(dbName);

        influxDB.enableBatch(BatchOptions.DEFAULTS.exceptionHandler(
                (failedPoints, throwable) -> {
                    failedPoints.forEach(point -> {
                        point.toString();
                    });
                    throwable.printStackTrace();
                })
        );
    }

    public void insertData(Map<String, Object> dataMap) {
        Point point = Point.measurement(tbNmae).time((long)dataMap.get(TIME), TimeUnit.MILLISECONDS).fields(dataMap).build();
        influxDB.write(point);
        influxDB.flush();
        logger.info("insert into influxdb success! {}", dataMap);
    }

}
