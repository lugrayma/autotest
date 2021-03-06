package utils;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisFirstStepUtil {
    public static SqlSession getSqlsession() throws IOException {

        //获取配置资源文件
        Reader reader = Resources.getResourceAsReader("MyBatisConnectConfig.xml");

        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
        //sqlSession就是能够执行配置文件中的sql语句
        SqlSession sqlSession=factory.openSession();
        return sqlSession;
    }
}

