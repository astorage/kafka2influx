package com.java.kafkainfluxdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();


    public static <R> R deserialize(String sourStr, Class<R> destClass) {
        if (StringUtils.isEmpty(sourStr)) {
            throw new BizException("反序列化异常，源字符串不能为空");
        }
        if(destClass == null) {
            throw new BizException("反序列化异常，目标对象类型不能为空");
        }
        R result = null;
        try {
            result = objectMapper.readValue(sourStr, destClass);
        } catch (IOException e) {
            throw new BizException("反序列化异常", e);
        }
        return result;
    }

    @Test
    public void test() {
        System.out.println(System.currentTimeMillis());
//        String jsonStr = "{\"tagNo\": \"new\",\"tagType\": \"RECOMMEND\",\"content\": \"新品1\",\"num\": 10,\"isboolean\": true,\"float\": 33.33, \"ssdf\":null}";
//        Map<String, Object> resultMap = deserialize(jsonStr, Map.class);
//        System.out.println(resultMap);
//        InfluxDbClient client = new InfluxDbClient();
//        client.insertData(resultMap);

    }
}
