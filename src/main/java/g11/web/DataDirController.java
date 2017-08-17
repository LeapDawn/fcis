package g11.web;

import g11.commons.exception.DataViolationException;
import g11.dao.DataDirDetailDAO;
import g11.dto.AjaxResult;
import g11.model.DataDir;
import g11.model.DataDirDetail;
import g11.service.DataDirService;
import lombok.Setter;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典controller
 */
@CrossOrigin
@RestController
@RequestMapping("/DataDir")
public class DataDirController {

    @Autowired
    @Setter
    DataDirService  dataDirService;

    @Autowired
    @Setter
    DataDirDetailDAO dataDirDetailDAO;

    @PostMapping("/getDataDir")
    public AjaxResult listDataDir() {
        List<DataDir> dataDirs = dataDirService.listDataDir();
        return AjaxResult.success(dataDirs);
    }

    @PostMapping("/insert")
    public  AjaxResult save(@RequestBody DataDirDetail detail){
        dataDirService.save(detail);
        return AjaxResult.success("新增数据字典明细信息成功！");
    }

    @PostMapping("/modify")
    public AjaxResult update(@RequestBody DataDirDetail detail){
        dataDirService.update(detail);
        return AjaxResult.success("修改数据字典明细信息成功！");
    }

    @PostMapping("/delete")
    public AjaxResult delete(@RequestParam String ids){
        Integer result = dataDirService.delete(ids);
        if (result== 0){
            throw new DataViolationException(501,"本次操作无数据字典明细信息被删除!");
        }
        else
            return AjaxResult.success("操作成功！成功删除"+ result +"条数据");
    }

    @PostMapping("/getDetail")
    public AjaxResult listDataDirDetail(@RequestBody DataDirDetail detail){
        Integer dataDir = detail.getDataDir();
        List<DataDirDetail> details = dataDirService.listDataDirDetail(dataDir);
        if (details.isEmpty()){
            throw new DataViolationException(502,"该id数据字典下无任何数据字典明细信息！");
        }
        else
            return AjaxResult.success(details);
    }

}
