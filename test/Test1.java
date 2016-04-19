import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.action.RegisterAction;
import com.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
@Transactional
public class Test1 {
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Test
	public void test() {
		User user = new User();
		hibernateTemplate.save(user);
		System.out.println(user.getId());
	}

}
