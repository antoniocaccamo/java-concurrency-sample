package me.antoniocaccamo.sample.concurrency.myibatis;

import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import me.antoniocaccamo.sample.concurrency.CommandExecutor;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

@Factory
public class MybatisFactory {

    private final DataSource dataSource;

    public MybatisFactory(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(){

        TransactionFactory transactionFactory = new JdbcTransactionFactory();

        Environment environment = new Environment("dev", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMappers(CommandExecutor.class.getPackage().getName());

        return new SqlSessionFactoryBuilder().build(configuration);

    }

}
