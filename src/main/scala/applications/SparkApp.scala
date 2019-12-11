package applications

object SparkApp {

  //Пакеты прописаны в built.sbt
  import org.apache.spark.{SparkConf, SparkContext}


  def main(args: Array[String]): Unit = {

    //тект для анализа
    val x = "C:\\Users\\Алексей\\IdeaProjects\\Kafka-SparkStreaming\\text_for_spark.txt"

    //Имя для понимания, какую задачу выпоняет программа сейчас - Configures
    //Выбор узла (кластера), local - local computer
    val conf = new SparkConf().setAppName("ExampleSpark").setMaster("local")

    //Дает доступ для функций Spark, чтобы создать RDD файлы
    val sc =new SparkContext(conf)

    //Загрузка содержания файла в RDD
    //Каждый элемент(partition) в RDD - это одна линия текстового файла
    val lines = sc.textFile(x).cache()

    //Разделение строк на слова (split через функцию flatMap)
    val word_count = lines.flatMap(line => line.split(" "))
      .map(word => (word,1))
      //Агрегация (сложение) одинаковых ключей (слов) - подсчет слов
      .reduceByKey(_ + _)

    //вывод слов в консоль
    //word_count.collect().foreach(println)

    //сохранение результата
    word_count.saveAsTextFile("C:\\Users\\Алексей\\IdeaProjects\\Kafka-SparkStreaming\\SparkTextFile\\spark_output_"
      + java.util.UUID.randomUUID.toString)

    sc.stop()




  }

}
