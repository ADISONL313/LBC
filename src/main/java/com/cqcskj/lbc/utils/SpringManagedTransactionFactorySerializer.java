package com.cqcskj.lbc.utils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import java.io.IOException;

public class SpringManagedTransactionFactorySerializer extends JsonSerializer<SpringManagedTransactionFactory> {
    @Override
    public void serialize(SpringManagedTransactionFactory value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // 在这里自定义序列化逻辑
        gen.writeStartObject();
        // 可以添加一些你认为有意义的属性或信息到 JSON 中
        gen.writeStringField("transactionFactoryType", "SpringManagedTransactionFactory");
        gen.writeEndObject();
    }
}