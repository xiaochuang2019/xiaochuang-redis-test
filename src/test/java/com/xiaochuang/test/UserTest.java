package com.xiaochuang.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.javassist.expr.NewArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chuang.common.utils.DateUtil;
import com.chuang.common.utils.FileUtil;
import com.chuang.common.utils.RandomUtil;
import com.chuang.common.utils.StringUtil;
import com.xiaochuang.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-redis.xml")
public class UserTest {
	@Resource
	private RedisTemplate redisTemplate;
	@Test
	public void JDKTest() {
		List<User> users=new ArrayList<User>();
		//定义邮箱结尾数组
		String []email= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
		//定义性别数组
		String []gender= {"男","女"};
		for (int i = 1; i <= 50000; i++) {
			User user=new User();
			//ID使用1-5万的顺序号
			user.setId(i);
			//姓名使用3个随机汉字模拟，可以使用以前自己编写的工具方法
			user.setName(StringUtil.randomChineseString(3));
			//性别在女和男两个值中随机
			user.setGender(gender[RandomUtil.random(0,1)]);
			//手机以13开头+9位随机数模拟
			user.setPhone("13"+RandomUtil.randomNumber(9));
			//邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟
			user.setEmail(RandomUtil.randomString(RandomUtil.random(3,20))+email[RandomUtil.random(0,6)]);
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			Calendar calendar=Calendar.getInstance();
			//设置初始时间
			calendar.set(1949,0,1);
			//设置关闭时间
			Calendar calendar2=Calendar.getInstance();
			calendar2.set(2001,11,31);
			//格式化时间
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			user.setBirthday(""+format.format(DateUtil.randomDate(calendar.getTime(),calendar2.getTime())));
			users.add(user);
			}
			ListOperations opsForList = redisTemplate.opsForList();
			//设置存储启动时间
			Long long1=System.currentTimeMillis();
			//开始存储
			opsForList.leftPushAll("user_jdk",users);
			//设置存数结束时间
			Long long2=System.currentTimeMillis();
			//输出结果
			System.out.println("序列化方式:jdk,保存数量:"+users.size()+",所耗时间:"+(long2-long1));
	}
	@Test
	public void JSONTest() {
		List<User> users=new ArrayList<User>();
		String []email= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
		String []gender= {"男","女"};
		for (int i = 1; i <= 50000; i++) {
			User user=new User();
			//ID使用1-5万的顺序号
			user.setId(i);
			//姓名使用3个随机汉字模拟，可以使用以前自己编写的工具方法
			user.setName(StringUtil.randomChineseString(3));
			//性别在女和男两个值中随机
			user.setGender(gender[RandomUtil.random(0,1)]);
			//手机以13开头+9位随机数模拟
			user.setPhone("13"+RandomUtil.randomNumber(9));
			//邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟
			user.setEmail(RandomUtil.randomString(RandomUtil.random(3,20))+email[RandomUtil.random(0,6)]);
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			Calendar calendar=Calendar.getInstance();
			calendar.set(1949,0,1);
			Calendar calendar2=Calendar.getInstance();
			calendar2.set(2001,11,31);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			user.setBirthday(""+format.format(DateUtil.randomDate(calendar.getTime(),calendar2.getTime())));
			users.add(user);
			}
			ListOperations opsForList = redisTemplate.opsForList();
			Long long1=System.currentTimeMillis();
			opsForList.leftPushAll("user_json",users);
			Long long2=System.currentTimeMillis();
			System.out.println("序列化方式:json,保存数量:"+users.size()+",所耗时间:"+(long2-long1));
	}
	@Test
	public void HashTest() {
		Map<String,User> map=new HashMap<String, User>();
		String []email= {"@qq.com","@163.com","@sian.com","@gmail.com","@sohu.com","@hotmail.com","@foxmail.com"};
		String []gender= {"男","女"};
		for (int i = 1; i <= 50000; i++) {
			User user=new User();
			//ID使用1-5万的顺序号
			user.setId(i);
			//姓名使用3个随机汉字模拟，可以使用以前自己编写的工具方法
			user.setName(StringUtil.randomChineseString(3));
			//性别在女和男两个值中随机
			user.setGender(gender[RandomUtil.random(0,1)]);
			//手机以13开头+9位随机数模拟
			user.setPhone("13"+RandomUtil.randomNumber(9));
			//邮箱以3-20个随机字母 + @qq.com  | @163.com | @sian.com | @gmail.com | @sohu.com | @hotmail.com | @foxmail.com模拟
			user.setEmail(RandomUtil.randomString(RandomUtil.random(3,20))+email[RandomUtil.random(0,6)]);
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			Calendar calendar=Calendar.getInstance();
			calendar.set(1949,0,1);
			Calendar calendar2=Calendar.getInstance();
			calendar2.set(2001,11,31);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			user.setBirthday(""+format.format(DateUtil.randomDate(calendar.getTime(),calendar2.getTime())));
			map.put("user"+i,user);
		}
		HashOperations opsForHash = redisTemplate.opsForHash();
		Long long1=System.currentTimeMillis();
		opsForHash.putAll("user_hash",map);
		Long long2=System.currentTimeMillis();
		System.out.println("序列化方式:hash_jdk,保存数量:"+map.size()+",所耗时间:"+(long2-long1));
	}
}
