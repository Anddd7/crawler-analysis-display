package github.eddy.bigdata.core.hadoop.mapreduce;

import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author edliao
 * 中文分词
 */
@Slf4j
public class ChineseTokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {

    private static final IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(Object key, Text value, Context context)
            throws IOException, InterruptedException {
        Reader read = new InputStreamReader(new ByteArrayInputStream(value.getBytes()));
        IKSegmenter iks = new IKSegmenter(read, true);
        Lexeme t;
        while ((t = iks.next()) != null) {
            word.set(t.getLexemeText());
            context.write(word, one);
        }
    }
}