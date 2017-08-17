package g11.service.impl;

import g11.commons.exception.DataViolationException;
import g11.commons.util.StringUtil;
import g11.dao.DataDirDAO;
import g11.dao.DataDirDetailDAO;
import g11.model.DataDir;
import g11.model.DataDirDetail;
import g11.service.DataDirService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.Parser;

import java.util.List;

/**
 * Created by Xiaoxia Xie on 2017/8/10.
 */
@Service
public class DataDirServiceImpl implements DataDirService {

    @Autowired
    @Setter
    private DataDirDAO dataDirDAO;
    @Autowired
    @Setter
    private DataDirDetailDAO dataDirDetailDAO;

    @Override
    public List<DataDir> listDataDir(){
        return dataDirDAO.selectAll();
    }

    @Override
    public void save(DataDirDetail detail) {
        dataDirDetailDAO.insert(detail);
    }

    @Override
    public void update(DataDirDetail detail) {
        dataDirDetailDAO.updateByPrimaryKey(detail);
    }

    @Override
    public int delete(String ids) {
        String[] split = ids.split(",");
        System.out.println("=============="+split.length);
        Integer[] idArray = new Integer[split.length];
        for (int i = 0 ; i<split.length ; i++){
            if (StringUtil.isInt(split[i])){
                idArray[i] =  Integer.parseInt(split[i]);
            }

        }
        return dataDirDetailDAO.delete(idArray);
    }

    @Override
    public List<DataDirDetail> listDataDirDetail(Integer dataDir) {
        return  dataDirDetailDAO.selectByDataDir(dataDir);
    }
}
