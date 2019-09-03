package me.antoniocaccamo.sample.concurrency.myibatis.mapper.impl;

import me.antoniocaccamo.sample.concurrency.model.User;
import me.antoniocaccamo.sample.concurrency.myibatis.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.inject.Singleton;
import java.util.List;

@Singleton
public class UserMapperImpl implements UserMapper {

    private final SqlSessionFactory sqlSessionFactory;

    public UserMapperImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;

    }

    @Override
    public User findById(Integer id) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            return getUserMapper(sqlSession).findById(id);
        }
    }


    @Override
    public void save(User user) {

        try (SqlSession sqlSession = sqlSessionFactory.openSession()){
            getUserMapper(sqlSession).save(user);
            sqlSession.commit();
        }
    }

    @Override
    public List<User> findAll() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            return getUserMapper(sqlSession).findAll();
        }
    }

    private static UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

}
