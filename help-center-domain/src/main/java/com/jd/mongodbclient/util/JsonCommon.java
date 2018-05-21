package com.jd.mongodbclient.util;


import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.StdSerializerProvider;
import org.codehaus.jackson.node.ObjectNode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 11-6-7
 * Time: 下午4:37
 * To change this template use File | Settings | File Templates.
 */
public class JsonCommon {
  private  static ObjectMapper readMapper = new ObjectMapper();

    static {

        readMapper.getDeserializationConfig().set(
                DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        readMapper.getDeserializationConfig().set(
                DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        readMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        readMapper.setVisibility(JsonMethod.FIELD, JsonAutoDetect.Visibility.ANY);
    }


    private static class Common{
        JsonGenerator jsonGenerator =null;
        ByteArrayOutputStream stream;
        ObjectMapper mapper;

    }
    private static class EmptyNull extends JsonSerializer<Object> {
        public void serialize(Object value, JsonGenerator jgen,
                              SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString("");
        }
    }
    // 对于多线程，无需创建多个jackson的实例，每一个线程创建一个就可以了。
     private static ThreadLocal<Common>  generatorThreadLocal = new ThreadLocal<Common>(){
             protected Common initialValue() {
                 ObjectMapper mapper = new ObjectMapper();
                 StdSerializerProvider sp = new StdSerializerProvider();
                 sp.setNullValueSerializer(new EmptyNull());
                 mapper.setSerializerProvider(sp);
                 JsonGenerator jsonGenerator =null;
                  ByteArrayOutputStream stream= new ByteArrayOutputStream();
                 try {
                     jsonGenerator= mapper.getJsonFactory().createJsonGenerator(stream, JsonEncoding.UTF8);

                 } catch (IOException e) {
                 }
                 Common common = new Common();
                 common.jsonGenerator=jsonGenerator;
                 common.stream =stream;
                 common.mapper=mapper;
                 return common;
             }

    } ;

    /**
     *     将对象转成json串
     * @param obj
     * @return
     * @throws java.io.IOException
     */
    public static String toJson(Object obj) throws IOException {
          generatorThreadLocal.get().jsonGenerator.writeObject(obj);
        String v = generatorThreadLocal.get().stream.toString("utf-8");
        generatorThreadLocal.get().stream.reset();
        return  v;
    }


    /**
     * 将对象转成byte流。
     * @param obj
     * @return
     * @throws java.io.IOException
     */
    public static byte[] toJsonBytes(Object obj) throws IOException {
        generatorThreadLocal.get().jsonGenerator.writeObject(obj);
        return   generatorThreadLocal.get().stream.toByteArray();
    }

    public static <T> T toObject(String json,Class<T> clazz) throws IOException {
        return (T) readMapper.readValue(json,clazz);
    }

    /**
     * 将json外面的包裹层去掉。
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws java.io.IOException
     */
    public static <T> T toObjectExceptBunble(String json, Class<T> clazz) throws IOException {
        ObjectNode js = (ObjectNode) readMapper.readTree(json);

        return (T) readMapper.readValue(js.getFields().next().getValue().toString(), clazz);
    }

       /**
     *     将对象转成json串
     * @param obj
     * @return
     * @throws java.io.IOException
     */
    public static String toJson(Object obj,String bundle) throws IOException {
        generatorThreadLocal.get().jsonGenerator.writeObject(obj);
        String v = generatorThreadLocal.get().stream.toString("utf-8");
        StringBuffer sb = new StringBuffer();
        sb.append("{\"");
        sb.append(bundle);
        sb.append("\":");
        sb.append(v);
        sb.append("}");
        generatorThreadLocal.get().stream.reset();
        return  sb.toString();
    }


}
