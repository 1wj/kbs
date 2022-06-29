package com.digiwin.app.kbs.service.mongo.support;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.digiwin.app.kbs.service.util.MongoUtil;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ObjectIdSerializer
 * @Description fastJson 序列化 _id序列化
 * @Version: v1-知识库迭代一
 **/
public class ObjectIdSerializer implements ObjectSerializer, ObjectDeserializer {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if ("java.util.List<org.bson.types.ObjectId>".equals(type.getTypeName())){
            return (T) MongoUtil.toObjectIds(parser.parseArray(String.class));
        }else {
            return (T) new ObjectId(parser.parseObject(String.class));
        }
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object instanceof List) {
            List<ObjectId> ids = (List<ObjectId>) object;
            serializer.write(ids.stream().map(objectId -> objectId.toString()).collect(Collectors.toList()));
        } else {
            ObjectId id = (ObjectId) object;
            serializer.write(id.toString());
        }
    }
}
