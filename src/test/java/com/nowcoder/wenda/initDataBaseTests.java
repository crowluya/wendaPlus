package com.nowcoder.wenda;

import com.nowcoder.wenda.dao.UserDao;
import com.nowcoder.wenda.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import com.mysql.jdbc.Driver;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =WendaApplication.class )
@Sql("/init-schema.sql")

@Mapper
public class initDataBaseTests {
    @Autowired
	UserDao userDao;



	@Test


	public void initDatabaseTests() {
		Random random = new Random();

		for(int i = 0; i< 11; ++i){
			User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
		    user.setName(String.format("USER%d",i));
		    user.setPassword("");
		    user.setSalt("");
           userDao.addUser(user);

           user.setPassword("xx");
           userDao.updatePassword(user);
		}

		Assert.assertEquals("xx", userDao.selectedById(1).getPassword());
        userDao.deleteById(1);
        Assert.assertNull(userDao.selectedById(1));
	}

}
