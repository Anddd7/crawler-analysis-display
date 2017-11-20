package github.eddy.bigdata.bilibili.service.analysis;

import github.eddy.bigdata.core.hadoop.AbstractMongoAnalysis;
import github.eddy.bigdata.core.hadoop.HadoopJobBuilder;
import github.eddy.bigdata.core.hadoop.mapreduce.TextSumReducer;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.bson.BSONObject;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author edliao
 * @since 2017/11/20
 *
 * 对视频描述的分析 ,使用中文分词包
 */
@Slf4j
public class DescriptionParticiple extends AbstractMongoAnalysis {

  @Override
  public void configMapperReducer(HadoopJobBuilder builder) throws IOException {
    builder.addMapper(IKAnalyzerMapper.class)
        .setReducer(TextSumReducer.class);
  }

  public static class IKAnalyzerMapper extends Mapper<Object, BSONObject, Text, IntWritable> {

    private AnalyzerWrapper analyzerWrapper = new AnalyzerWrapper(true);

    private static final IntWritable ONE = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(Object key, BSONObject value, Context context)
        throws IOException, InterruptedException {
      String description = (String) value.get("description");
      analyzerWrapper.analysisString(description, s -> {
        word.set(s.trim().toLowerCase());
        try {
          context.write(word, ONE);
        } catch (Exception e) {
          log.error("", e);
        }
      });
    }

    /**
     * 对中文分词器进行包装 ,增加一些便捷的函数
     */
    static final class AnalyzerWrapper {

      Analyzer analyzer;

      public AnalyzerWrapper(boolean useSmart) {
        this.analyzer = new IKAnalyzer(useSmart);
      }

      /**
       * 分析字符串并对结果字符串回调
       *
       * @param s,termConsumer {@link AnalyzerWrapper#analysisString(String, Consumer, BiConsumer,
       * Consumer)}
       */
      private void analysisString(String s, Consumer<String> termConsumer) {
        analysisString(s, termConsumer, null, null);
      }

      /**
       * 分析字符串并对结果进行回调
       *
       * @param s 目标字符串
       * @param termConsumer 对分析结果的字符串进行回调
       * @param offsetConsumer 对分析结果的位置进行回调
       * @param typeConsumer 对分析结果的类型进行回调
       */
      private void analysisString(String s, Consumer<String> termConsumer,
          BiConsumer<Integer, Integer> offsetConsumer, Consumer<String> typeConsumer) {
        final Reader r = new StringReader(s);
        try (TokenStream ts = analyzer.tokenStream("default", r)) {
        /*
        addAttribute start
         */
          CharTermAttribute term = termConsumer == null ?
              null : ts.addAttribute(CharTermAttribute.class);
          OffsetAttribute offset = offsetConsumer == null ?
              null : ts.addAttribute(OffsetAttribute.class);
          TypeAttribute type = typeConsumer == null ?
              null : ts.addAttribute(TypeAttribute.class);
         /*
        addAttribute end
         */
          ts.reset();
          while (ts.incrementToken()) {
          /*
        consumeAttribute start
         */
            if (termConsumer != null) {
              termConsumer.accept(term.toString());
            }
            if (offsetConsumer != null) {
              offsetConsumer.accept(offset.startOffset(), offset.endOffset());
            }
            if (typeConsumer != null) {
              typeConsumer.accept(type.type());
            }
          /*
        consumeAttribute end
         */
          }
          ts.end();
        } catch (IOException e) {
          log.error("", e);
        }
      }
    }
  }
}
