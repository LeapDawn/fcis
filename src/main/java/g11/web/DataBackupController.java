package g11.web;

import g11.commons.exception.FileException;
import g11.dto.AjaxResult;
import g11.dto.pageModel.PFile;
import g11.service.DataBackupService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/backups")
public class DataBackupController {

    @Setter
    @Autowired
    private DataBackupService dataBackupService;

    @GetMapping("/list")
    public AjaxResult listBackups() throws FileException {
        return AjaxResult.success(dataBackupService.list());
    }

    @PostMapping("/deletion")
    public AjaxResult delete(@RequestBody PFile PFile) throws FileException {
        if (dataBackupService.delete(PFile.getFileName())){
            return AjaxResult.success("删除备份文件成功");
        } else {
            return AjaxResult.fail(403,"删除备份文件成功");
        }
    }

    @PostMapping("/backup")
    public AjaxResult backup(@RequestBody PFile PFile){
        dataBackupService.backup(PFile.getFileName());
        return AjaxResult.success("生成备份文件[" + PFile.getFileName() + "]成功");
    }

    @PostMapping("/restore")
    public AjaxResult restore(@RequestBody PFile PFile){
        dataBackupService.restore(PFile.getFileName());
        return AjaxResult.success("还原备份文件[" + PFile.getFileName() + "]成功");
    }
}
