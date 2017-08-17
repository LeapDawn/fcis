package g11.service.impl;

import g11.Application;
import g11.model.DataDir;
import g11.model.DataDirDetail;
import g11.service.DataDirService;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DataDirServiceImplTest {

    @Autowired
    @Setter
    private DataDirService dataDirService;

    @Test
    public void listDataDir() throws Exception {
        List<DataDir> dataDirs = dataDirService.listDataDir();
        for (DataDir dataDir : dataDirs) {
            System.out.println(dataDir);
        }

    }

    @Test
    public void save() throws Exception {
        DataDirDetail detail = new DataDirDetail();
        detail.setContent("Test1");
        detail.setDataDir(1);
        dataDirService.save(detail);
    }

    @Test
    public void update() throws Exception {
        DataDirDetail detail = new DataDirDetail();
        detail.setId(13);
        detail.setDataDir(2);
        detail.setContent("0814");
        dataDirService.update(detail);
    }

    @Test
    public void delete() throws Exception {
        String testStr ="10,5";
        int delete = dataDirService.delete(testStr);
        System.out.println(delete);
    }

    @Test
    public void listDataDirDetail() throws Exception {
        List<DataDirDetail> dataDirDetails = dataDirService.listDataDirDetail(2);
//        System.out.println(dataDirDetails);
        for (DataDirDetail detail : dataDirDetails){
            System.out.println(detail);
        }
    }

}