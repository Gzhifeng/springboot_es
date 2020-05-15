package org.example.query;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.example.ElasticsearchClientBase;
import org.junit.Test;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * 术语查询
 * 通常用于结构化数据，如数字、日期和枚举，而不是全文字段
 * 官方文档 @see <a href='https://www.elastic.co/guide/en/elasticsearch/client/java-api/current/java-term-level-queries.html'></a>
 * 中文文档 @see <a href='https://es.quanke.name/query-dsl/term-level-queries.html'></a>
 * Created by http://quanke.name on 2017/11/16.
 */
public class TermLevelQueries extends ElasticsearchClientBase {

    /**
     * 查询包含在指定字段中指定的确切值的文档
     *
     * @throws Exception
     */
    @Test
    public void testTermQuery() throws Exception {
        QueryBuilder qb = termQuery(
                "user",    //field
                "kimchy"   //text
        );

        println(twitterPrepareSearch(qb));
    }

    /**
     * 查询包含任意一个在指定字段中指定的多个确切值的文档
     *
     * @throws Exception
     */
    @Test
    public void testTermsQuery() throws Exception {
        QueryBuilder qb = termsQuery(
                "user",    //field
                "kimchy", "li");                    //values

        println(twitterPrepareSearch(qb));
    }

    /**
     * 查询指定字段包含指定范围内的值（日期，数字或字符串）的文档
     *
     * @throws Exception
     */
    @Test
    public void testRangeQuery() throws Exception {

//        gte() :范围查询将匹配字段值大于或等于此参数值的文档。
//        gt() :范围查询将匹配字段值大于此参数值的文档。
//        lte() :范围查询将匹配字段值小于或等于此参数值的文档。
//        lt() :范围查询将匹配字段值小于此参数值的文档。
//        from() 开始值 to() 结束值 这两个函数与includeLower()和includeUpper()函数配套使用。
//        includeLower(true) 表示 from() 查询将匹配字段值大于或等于此参数值的文档。
//        includeLower(false) 表示 from() 查询将匹配字段值大于此参数值的文档。
//        includeUpper(true) 表示 to() 查询将匹配字段值小于或等于此参数值的文档。
//        includeUpper(false) 表示 to() 查询将匹配字段值小于此参数值的文档。

//        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("age");
//        rangeQueryBuilder.from(19);
//        rangeQueryBuilder.to(21);
//        rangeQueryBuilder.includeLower(true);
//        rangeQueryBuilder.includeUpper(true);

        //上面代码等价于下面代码

        QueryBuilder qb = QueryBuilders.rangeQuery("age").gte(11).lte(21);


        twitterPrepareSearch(qb);
    }

    /**
     * 查询指定的字段包含任何非空值的文档,如果指定字段上至少存在一个no-null的值就会返回该文档
     *
     * @throws Exception
     */
    @Test
    public void testExistsQuery() throws Exception {
        QueryBuilder qb = existsQuery("user");

        println(twitterPrepareSearch(qb));
    }

    /**
     * 查找指定字段包含以指定的精确前缀开头的值的文档。
     *
     * @throws Exception
     */
    @Test
    public void testPrefixQuery() throws Exception {
        QueryBuilder qb = prefixQuery(
                "user",    //field
                "l"     //prefix
        );

        println(twitterPrepareSearch(qb));
    }

    /**
     * 通配符查询
     * 查询指定字段包含与指定模式匹配的值的文档，其中该模式支持单字符通配符（？）和多字符通配符（*）,和前缀查询一样，
     * 通配符查询指定字段是未分析的（not analyzed）
     * <p>
     * 可以使用星号代替0个或多个字符，使用问号代替一个字符。星号表示匹配的数量不受限制，而后者的匹配字符数则受到限制。
     * 这个技巧主要用于英文搜索中，如输入““computer*”，就可以找到“computer、computers、computerised、computerized”等单词，
     * 而输入“comp?ter”，则只能找到“computer、compater、competer”等单词。注意的是通配符查询不太注重性能，在可能时尽量避免，
     * 特别是要避免前缀通配符（以通配符开始的词条）
     *
     * @throws Exception
     */
    @Test
    public void testWildcardQuery() throws Exception {
        QueryBuilder qb = wildcardQuery(
                "user",
                "k?mc*"
        );

        println(twitterPrepareSearch(qb));
    }

    /**
     * 正则表达式查询
     * 查询指定的字段包含与指定的正则表达式匹配的值的文档。
     * <p>
     * 和前缀查询一样，正则表达式查询指定字段是未分析的（not analyzed）。正则表达式查询的性能取决于所选的正则表达式。
     * 如果我们的正则表达式匹配许多词条，查询将很慢。一般规则是，正则表达式匹配的词条数越高，查询越慢。
     *
     * @throws Exception
     */
    @Test
    public void testRegexpQuery() throws Exception {

        QueryBuilder qb = regexpQuery(
                "user",        //field
                "k.*");             //regexp
        println(twitterPrepareSearch(qb));
    }

    /**
     * 模糊查询
     * 查询指定字段包含与指定术语模糊相似的术语的文档。模糊性测量为1或2的 Levenshtein。
     * <p>
     * 如果指定的字段是string类型，模糊查询是基于编辑距离算法来匹配文档。编辑距离的计算基于我们提供的查询词条和被搜索文档。
     * 如果指定的字段是数值类型或者日期类型，模糊查询基于在字段值上进行加减操作来匹配文档。
     * 此查询很占用CPU资源，但当需要模糊匹配时它很有用，例如，当用户拼写错误时。另外我们可以在搜索词的尾部加上字符 “~” 来进行模糊查询
     *
     * @throws Exception
     */
    @Test
    public void testFuzzyQuery() throws Exception {

        QueryBuilder qb = fuzzyQuery(
                "user",     //field
                "kimzh"    //text
        ).fuzziness(Fuzziness.TWO);

        println(twitterPrepareSearch(qb));
    }


}
