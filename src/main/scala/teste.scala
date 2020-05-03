import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming.Trigger

object streaming_app_demo {
  def main(args: Array[String]): Unit = {

    println("Spark Structured Streaming with Kafka Demo Application Started ...")

    val KAFKA_TOPIC_NAME_CONS = "test"
    val KAFKA_OUTPUT_TOPIC_NAME_CONS = "test"
    val KAFKA_BOOTSTRAP_SERVERS_CONS = "localhost:9092"


    val spark = SparkSession.builder
      .master("local[*]")
      .appName("Spark Structured Streaming with Kafka Demo")
      .config("hadoop.home.dir", "C:\\hadoop\\bin")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    // Stream from Kafka
    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", KAFKA_BOOTSTRAP_SERVERS_CONS)
      .option("subscribe", KAFKA_TOPIC_NAME_CONS)
      .option("startingOffsets", "earliest")
      .load()

    df.isStreaming

    df.printSchema

    val df2 = df.selectExpr("CAST(value AS STRING)", "CAST(timestamp AS TIMESTAMP)")

    df2.show()




  }
}