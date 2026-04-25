package com.ruoyi.jt1078.protocol.commons.transform;

import com.ruoyi.jt1078.protocol.commons.transform.passthrough.PeripheralStatus;
import com.ruoyi.jt1078.protocol.commons.transform.passthrough.PeripheralSystem;
import io.github.yezhihao.protostar.PrepareLoadStrategy;
import io.github.yezhihao.protostar.schema.ArraySchema;
import io.github.yezhihao.protostar.schema.MapSchema;
import io.github.yezhihao.protostar.schema.NumberSchema;

/**
 * 透传消息转换器
 * @author yezhihao
 * https://gitee.com/yezhihao/jt808-server
 */
public class PassthroughConverter extends MapSchema<Number, Object> {

    public PassthroughConverter() {
        super(NumberSchema.BYTE_INT, 0);
    }

    @Override
    protected void addSchemas(PrepareLoadStrategy<Number> schemaRegistry) {
        schemaRegistry
                .addSchema(0, ArraySchema.BYTES)
                .addSchema(PeripheralStatus.key, PeripheralStatus.Schema.INSTANCE)
                .addSchema(PeripheralSystem.key, PeripheralSystem.Schema.INSTANCE);
    }
}