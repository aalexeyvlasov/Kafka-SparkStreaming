package applications

object SparkStreamingApp {


    //Пакеты прописаны в built.sbt// StreamingContext is the main entry point for all streaming functionality
  import org.apache.spark.SparkConf
  import org.apache.spark.streaming.{Seconds, StreamingContext}// StreamingContext is the main entry point for all streaming functionality




  def main(args: Array[String]): Unit = {



      //2 execution threads - local[2]
      val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")

      //Задается интервал отрезка (одной партии) - Seconds(1)
      //60*5 секунд - 5 минут
      val ssc = new StreamingContext(conf, Seconds(60*5))


      //DStream представляющий дату от TCP source(hostname (т.е. localhost) и порт(т.е. 9999))
      val lines = ssc.socketTextStream("127.0.0.1", 9999)


      val words = lines.flatMap(_.split(" "))
      val pairs = words.map(word => (word, 1))

      val wordCounts = pairs.reduceByKey(_ + _)

      wordCounts.print()

      //Сохранение файлов в SparkStreaming
      wordCounts.saveAsTextFiles("C:\\Users\\Алексей\\IdeaProjects\\Kafka-SparkStreaming\\SparkStreaming\\spark_output_"
                + java.util.UUID.randomUUID.toString)


      // Start the computation
      ssc.start()

      //Останавливает после первого захода
      //ssc.stop()

      // Работает до принудительной остановки
      ssc.awaitTermination()






    }

}
