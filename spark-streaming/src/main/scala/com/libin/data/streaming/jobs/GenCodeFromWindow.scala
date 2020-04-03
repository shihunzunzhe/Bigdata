package com.libin.data.streaming.jobs

import com.libin.data.streaming.utils.StreamingExamples
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Copyright (c) 2020/4/3 libin Inc. All Rights Reserved.
 * Authors: libin<2578858653@qq.com>
 *
 * Purpose : nc -lk 9999
 */
object GenCodeFromWindow {
	def main(args: Array[String]): Unit = {
		val conf = new SparkConf().setMaster("local[2]").setAppName("GenCodeFromWindow")
		val ssc = new StreamingContext(conf, Seconds(5))
		
		ssc.checkpoint("/home/baolibin/2020_github/checkout")
		StreamingExamples.setStreamingLogLevels()
		
		val lines = ssc.socketTextStream("localhost", 9999)
		
		val res = lines.flatMap(_.split(" "))
			.map((_, 1))
			.reduceByKeyAndWindow((a: Int, b: Int) => (a + b), Seconds(10), Seconds(5))
		
		res.print()
		
		ssc.start() // Start the computation
		ssc.awaitTermination() // Wait for the computation to terminate
	}
}

/**
Input:
a
a
a
a
a

b
b
b
b
b


a
b
c
d
e
...
Output:
Time: 1585905790000 ms
-------------------------------------------
(d,7)
(b,42)
(,21)
(V,1)
(e,7)
(a,42)
(c,7)
-------------------------------------------
Time: 1585905795000 ms
-------------------------------------------
(d,1)
(b,6)
(,3)
(a,6)
(c,1)
 */
