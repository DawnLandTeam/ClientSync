package cn.dawnland.utils;

import cn.dawnland.vo.VersionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Created by cap_sub@dawnland.cn
 */
public class VersionUtils {

    private static HttpUtils httpUtils = HttpUtils.INSTANCE;
    private final static String versionUrl = "https://dawnland.cn/13/ClientUpdate/1.16.5-MOD-Server/version.json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static List<VersionResponse> getVersions(){
        String result = httpUtils.get(versionUrl);
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, VersionResponse.class);
        try {
            return objectMapper.readValue(result, javaType);
        } catch (JsonProcessingException e) {
            return new ArrayList<VersionResponse>();
        }
    }

}
