

##### 1.广播变量
    Broadcast可以理解为一个公共的共享变量。可以把一个DataSet（数据集）广播出去，不同的Task在节点上都能够获取到它，
    这个数据集在每个节点上只会存在一份。如果不使用Broadcast，则在各节点的每个Task中都需要复制一份DataSet数据集，
    比较浪费内存（也就是一个节点中可能会存在多份DataSet数据）。

    广播变量只能在Flink批处理程序中才可以使用。
    
    DataStream Broadcast（分区规则）
    分区规则是把元素广播给所有的分区，数据会被重复处理，类似于Storm中的allGrouping。
    


##### 2.广播变量和累加器区别
    Flink Broadcast和Accumulator的区别
    Broadcast允许程序员将一个只读的变量缓存在每台机器上，而不用在任务之间传递变量。广播变量可以进行共享，但是不可以进行修改。
    Accumulator可以在不同任务中对同一个变量进行累加操作，但是只有在任务执行结束的时候才能获得累加器的最终结果。
    
    