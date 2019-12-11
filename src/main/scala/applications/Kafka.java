package applications;

////Структура:
//1. Stream from applications.Kafka (Key, Value)  = <null, "applications.Kafka applications.Kafka Stream">
//2. MapValues lowercase             = <null, "kafka kafka stream">
//3. FlatMapValues split by space    = <null, "kafka">, <null, "kafka">, <null, stream"> //Разбивка собщения на 3 сообщения
//4. SelectKey to apply a key        = <kafka, "kafka">, <kafka, "kafka">, <stream, stream"> //здесь: Key = Value - для последующей группировки
//5. GroupByKey (before aggregation) = (<kafka, "kafka">, <kafka, "kafka">), (<stream, stream">)
//6. Count (aggregation)             = (<kafka, "2">), (<stream, 1">) //считает совпадения в кажой группе
//7. To                                 //чтобы записать результат обратно в applications.Kafka

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.Arrays;
import java.util.Properties;

// Properties instance


public class Kafka {
    public static void main (String[] args){

        // Properties instance
        Properties my_config = new Properties();

        my_config.put(StreamsConfig.APPLICATION_ID_CONFIG, "Word_Count-stream");
        my_config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093"); //localhost:9092,
       // my_config.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);
        my_config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        my_config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        my_config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        StreamsBuilder my_builder = new StreamsBuilder();

        //1. Stream from applications.Kafka (Key, Value)  = <null, "applications.Kafka applications.Kafka Stream">


     /////Числой фильтр
     // long limit_MIN = 1024;
     // long limit_MAX = 1037455;
     // KStream<String, Long> my_WordCountInput = my_builder.stream("my_word_count_input");

     // KStream<String, Long> my_WordCountInput_2 = my_WordCountInput.filter(
     //         (limit_name, limit_value)->limit_value >= limit_MIN && limit_value <= limit_MAX );

        KStream<String, String> my_WordCountInput = my_builder.stream("my_word_count_input");




        //2. MapValues lowercase             = <null, "kafka kafka stream">

        /*//На ValueMapper<String, Object> //=>Правая кнопка мыши, Generate/Implement Methods/OK
        my_WordCountInput.mapValues(new ValueMapper<String, Object>() {
        //=> Gives bad format:
            @Override
            public Object apply(String value) {
                return null;
            }
        *///Вместо этого лучше использовать лямбду:
        KTable<String, Long> my_wordcount = my_WordCountInput

                .mapValues(my_value_a -> my_value_a.toLowerCase())
                //альтернативный вариант:
                //.mapValues(String::toLowerCase)

                 //3. FlatMapValues split by space    = <null, "kafka">, <null, "kafka">, <null, stream"> //Разбивка собщения на 3 сообщения
                 //value становится списком, созданным с помощью разделением пробелом изначального value
                .flatMapValues(my_value_b -> Arrays.asList(my_value_b.split(" ")))

                 //4. SelectKey to apply a key        = <kafka, "kafka">, <kafka, "kafka">, <stream, stream"> //здесь: Key = Value - для последующей группировки
                 //замена  my_IgnoredKey на my_word  (my_word, my_word)
                .selectKey((my_IgnoredKey, my_word) -> my_word)

                 //5. GroupByKey (before aggregation) = (<kafka, "kafka">, <kafka, "kafka">), (<stream, stream">)
                 //Группировка по ключу
                .groupByKey()

                 //6. Count (aggregation)             = (<kafka, "2">), (<stream, 1">) //считает совпадения в кажой группе
                .count();

        //7. To                                 //чтобы записать результат обратно в applications.Kafka
        //Нужно обновить Serdes в соответствии с KTable<String, Long>
        my_wordcount.toStream().to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));



        System.out.println("Запуск Apache applications.Kafka: ");



        //Запуск программы
        KafkaStreams my_streams = new KafkaStreams(my_builder.build(), my_config);

        my_streams.start();


    }





}

