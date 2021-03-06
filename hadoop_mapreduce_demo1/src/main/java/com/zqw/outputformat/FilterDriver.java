package com.zqw.outputformat;

import com.zqw.flowsum.FlowDriver;
import com.zqw.inputformat.SequenceFileMapper;
import com.zqw.inputformat.SequenceFileReducer;
import com.zqw.inputformat.WholeFileInputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

public class FilterDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        //1. 获取配置信息
        Configuration configuration = new Configuration();
        Job job = Job.getInstance(configuration);

        //2. 获取jar包的位置
        job.setJarByClass(FlowDriver.class);

        //3. 指定job要使用的mapper/Reducer的业务类
        job.setMapperClass(FilterMapper.class);
        job.setReducerClass(FilterReducer.class);

        //4. 指定mapper输出数据的kv类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        //5. 指定最终输出的数据kv类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //要将自定义的输出格式组件设置到job中
        job.setOutputFormatClass(FilterOutputFormat.class);

        //6. 指定job输入输出原始文件的目录
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));


        //7. 将job中配置的相关参数，以及相关的java类所在的jar包交给yarn去运行
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);

    }

}
