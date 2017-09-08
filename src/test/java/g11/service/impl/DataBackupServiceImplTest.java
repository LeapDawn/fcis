//package g11.service.impl;
//
//import g11.Application;
//import g11.service.DataBackupService;
//import lombok.Setter;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import static org.junit.Assert.*;
//
///**
// * Created by cody on 2017/8/11.
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
//public class DataBackupServiceImplTest {
//
//    @Setter
//    @Autowired
//    private DataSource dataSource;
//
//    @Setter
//    @Autowired
//    private DataBackupService dataBackupService;
//
//    @Test
//    public void list() throws Exception {
//
//    }
//
//    @Test
//    public void delete() throws Exception {
//    }
//
//    @Test
//    public void backup() throws Exception {
//        dataBackupService.backup("demo");
//    }
//
//    @Test
//    public void restore() throws Exception {
//        dataBackupService.restore("demo");
//    }
//
//    @Test
//    public void test() throws SQLException {
//        System.out.println(dataSource.getConnection());
//    }
//}