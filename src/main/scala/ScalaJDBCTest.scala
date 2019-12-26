import org.apache.spark.sql.SparkSession
import java.util.Properties


object ScalaJDBCTest {
  println("Hi There")

   def main(args: Array[String]) {  
   val spark = SparkSession
      .builder()
      .appName("Spark SQL data sources example")
      .config("spark.master", "local")
      .getOrCreate()

      spark.sparkContext.setLogLevel("INFO")

   println("In main calling JDBC function")  
   #runJdbcDatasetExample(spark)
   println("Finished making JDBC call") 
 
   spark.stop() 
  }
  private def runJdbcDatasetExample(spark: SparkSession): Unit = {
    // $example on:jdbc_dataset$
    // Note: JDBC loading and saving can be achieved via either the load/save or jdbc methods
    // Loading data from a JDBC source
    println("In JDBC function")  
    val jdbcDF = spark.read
      .format("jdbc")
      .option("driver", "com.ibm.db2.jcc.DB2Driver")
      .option("url", "jdbc:db2://ppydalprddb02.sl.bluecloud.ibm.com:50000/CSPROD")
      .option("dbtable", "extract.rep_all")
      .option("user", "csusr")
      .option("password", "temp123")
      .load()
      
      println("Getting User Profile Data")  
      jdbcDF.printSchema() 
      val rowcount = jdbcDF.count() 
      println("JDBC load complated " + rowcount + " rows loaded" )
  } 
  
//  private def runJsonDatasetExample(spark: SparkSession): Unit = {
//    // $example on:json_dataset$
//    // Primitive types (Int, String, etc) and Product types (case classes) encoders are
//    // supported by importing this when creating a Dataset.
//    import spark.implicits._
//
//    // A JSON dataset is pointed to by path.
//    // The path can be either a single text file or a directory storing text files
//    val path = "people.json"
//    val peopleDF = spark.read.json(path)
//
//    // The inferred schema can be visualized using the printSchema() method
//    peopleDF.printSchema()
//    // root
//    //  |-- age: long (nullable = true)
//    //  |-- name: string (nullable = true)
//
//    // Creates a temporary view using the DataFrame
//    peopleDF.createOrReplaceTempView("people")
//
//    // SQL statements can be run by using the sql methods provided by spark
//    val teenagerNamesDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19")
//    teenagerNamesDF.show()
//    // +------+
//    // |  name|
//    // +------+
//    // |Justin|
//    // +------+
//
//    // Alternatively, a DataFrame can be created for a JSON dataset represented by
//    // a Dataset[String] storing one JSON object per string
//    val otherPeopleDataset = spark.createDataset(
//      """{"name":"Yin","address":{"city":"Columbus","state":"Ohio"}}""" :: Nil)
//    val otherPeople = spark.read.json(otherPeopleDataset)
//    otherPeople.show()
//    // +---------------+----+
//    // |        address|name|
//    // +---------------+----+
//    // |[Columbus,Ohio]| Yin|
//    // +---------------+----+
//    // $example off:json_dataset$
//  }
}