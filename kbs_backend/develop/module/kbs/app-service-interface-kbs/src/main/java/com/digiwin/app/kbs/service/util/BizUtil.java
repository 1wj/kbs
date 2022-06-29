package com.digiwin.app.kbs.service.util;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.digiwin.app.container.exceptions.DWArgumentException;
import com.digiwin.app.kbs.service.mws.domain.model.KnoBaseModel;
import com.digiwin.app.service.DWServiceContext;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Date 2022/1/20 11:39
 * @Created yanggld
 * @Description
 */
public class BizUtil {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Long getCurrentTenantSid() throws Exception {
        DWServiceContext context = DWServiceContext.getContext();
        Object userNameObj = context.getProfile().get("tenantSid");
        if (userNameObj == null) {
            throw new DWArgumentException("tenantSid", "tenantSid不可为空");
        }
        return (Long)userNameObj;
    }
    /**
     * 知识库相关请求参数中获取业务的参数信息
     *
     * @param param 原始参数
     * @param key   业务参数key
     * @param clazz 类型
     * @return
     * @throws Exception eg: 新增知识库，获取业务参数list= [{"base_no": "123","base_name": "baseName123"}]
     *                   入参即为
     *                   param：
     *                   {
     *                   "knowledge_base_info": [
     *                   {
     *                   "base_no": "123",
     *                   "base_name": "baseName123"
     *                   }
     *                   ]
     *                   }
     *                   key： knowledge_base_info
     */
    public static <T> List<T> getKnowledgeReqBizParam(String param, String key, Class<T> clazz) {
        Map<String, Object> paramMap = str2Map(param);
        return getMapTs(paramMap, key, clazz);
    }

    /**
     * string 转 model
     *
     * @param param
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T str2TByKey(String param, String key,Class<T> clazz) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        try {
            return objectMapper.readValue(JSON.parseObject(param).getString(key),clazz);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * string 转 model
     * @param param
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T str2T(String param,Class<T> clazz) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        try {
            return objectMapper.readValue(param,clazz);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * String 转 map
     *
     * @param param
     * @return
     * @throws Exception
     */
    public static Map<String, Object> str2Map(String param) {
        if (StringUtils.isEmpty(param)) {
            return new HashMap<>();
        }
        try {
            return objectMapper.readValue(param, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * map 取 model list
     *
     * @param map
     * @return
     * @throws Exception
     */
    public static <T> List<T> getMapTs(Map map, String key, Class<T> clazz) {
        if (!MapUtils.isEmpty(map)) {
            Object obj = map.get(key);
            if (obj != null) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String objStr = mapper.writeValueAsString(obj);
                    JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
                    return mapper.readValue(objStr, type);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return new ArrayList<>();
                }
            }
        }
        return new ArrayList<>();
    }

    /**
     * jackson注解的model转成map
     * map字段是JsonProperty对应的value
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static List<HashMap> model2HashMap(List list) {
        if (CollectionUtils.isNotEmpty(list)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, HashMap.class);
                return mapper.convertValue(list, type);
            } catch (Exception ex) {
                ex.printStackTrace();
                return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    /**
     * A类型集合 转成 B类型集合
     *
     * @param sourceList
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> convertList(List sourceList, Class<T> clazz) {
        if (CollectionUtils.isNotEmpty(sourceList)) {
            return (List<T>) sourceList.stream().map(source -> {
                try {
                    T target = clazz.newInstance();
                    BeanUtils.copyProperties(source, target);
                    return target;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * A类型集合 转成 B类型集合
     *
     * @param sourceList
     * @param clazz
     * @param func 加入自定义的转换逻辑
     * @param <T>
     * @return
     */
    public static <T> List<T> convertListWithFunc(List sourceList, Class<T> clazz, BiConsumer<Object,T> func) {
        if (CollectionUtils.isNotEmpty(sourceList)) {
            return (List<T>) sourceList.stream().map(source -> {
                try {
                    T target = clazz.newInstance();
                    BeanUtils.copyProperties(source, target);
                    func.accept(source,target);
                    return target;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    /**
     * 设置
     * @param t
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> Criteria buildLikeCriteria4Mongo(T t) throws IllegalAccessException {
        Class<?> aClass = t.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        List<Criteria> criteriaList = new ArrayList<>(declaredFields.length);
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object val = field.get(t);
            if (val != null) {
                String fieldName = field.getName();
                if (val instanceof String) {
                    String valStr = (String)val;
                    if (StringUtils.isNotEmpty(valStr)) {
                        Pattern pattern =Pattern.compile("^.*" + valStr + ".*$",Pattern.CASE_INSENSITIVE);
                        criteriaList.add(Criteria.where(fieldName).is(pattern));
                    }
                }
            }
        }
        Criteria criteria = new Criteria();
        if (CollectionUtils.isNotEmpty(criteriaList)) {
            Criteria[] array = criteriaList.toArray(new Criteria[criteriaList.size()]);
            criteria.orOperator(array);
        }
        return criteria;
    }
}
