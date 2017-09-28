package github.eddy.bigdata.core.configuration;

import github.eddy.bigdata.bilibili.common.TaskStatusEnum;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

import java.time.Instant;

public class MongoCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
        if (clazz == Instant.class) {
            // construct DocumentCodec with a CodecRegistry
            return (Codec<T>) new InstantCodec();
        } else if (clazz == Enum.class) {
            return (Codec<T>) new TaskStatusEnumCodec();
        }
        return null;
    }

    public static class InstantCodec implements Codec<Instant> {

        @Override
        public Instant decode(BsonReader reader, DecoderContext decoderContext) {
            String date = reader.readString();
            return Instant.parse(date);
        }

        @Override
        public void encode(BsonWriter writer, Instant value, EncoderContext encoderContext) {
            writer.writeString(value.toString());
        }

        @Override
        public Class<Instant> getEncoderClass() {
            return Instant.class;
        }
    }

    public static class TaskStatusEnumCodec implements Codec<TaskStatusEnum> {

        @Override
        public TaskStatusEnum decode(BsonReader reader, DecoderContext decoderContext) {
            return TaskStatusEnum.valueOf(reader.readString());
        }

        @Override
        public void encode(BsonWriter writer, TaskStatusEnum value, EncoderContext encoderContext) {
            writer.writeString(value.name());
        }

        @Override
        public Class<TaskStatusEnum> getEncoderClass() {
            return TaskStatusEnum.class;
        }
    }
}
