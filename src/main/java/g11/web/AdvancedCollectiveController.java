package g11.web;

import g11.commons.exception.ExcelException;
import g11.dto.AjaxResult;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.UploadResultModel;
import g11.dto.pageModel.Section;
import g11.model.AdvancedCollective;
import g11.service.AdvancedCollectiveService;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Created by cody on 2017/8/7.
 */

@CrossOrigin
//写了@RestController，就不需要写@ResponseBody
@RestController
@RequestMapping("/ac")
public class AdvancedCollectiveController {
    @Autowired
    @Setter
    AdvancedCollectiveService advancedCollectiveService;

    @RequestMapping("/addition")
    public AjaxResult addition(@RequestBody AdvancedCollective advancedCollective) throws Exception {
        advancedCollectiveService.save(advancedCollective);
        return AjaxResult.success("申报成功");
    }

    @RequestMapping("/delete")
    public AjaxResult delete(@RequestBody Section section) throws Exception {
        advancedCollectiveService.delete(section);
        return AjaxResult.success("删除成功");
    }

    @RequestMapping("/list")
    public AjaxResult list(@RequestBody RequestList<AdvancedCollective> requestList) throws Exception {
        ResultModel<AdvancedCollective> resultModel = advancedCollectiveService.list(requestList);
        return AjaxResult.success(resultModel);
    }

    @RequestMapping("/update")
    public AjaxResult update(@RequestBody AdvancedCollective advancedCollective) throws Exception {
        advancedCollectiveService.update(advancedCollective);
        return AjaxResult.success("修改成功");
    }

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    @ResponseBody
    public AjaxResult upload(@RequestParam("file") MultipartFile file){
        if (file == null) {
            return AjaxResult.fail(101,"上传的文件为空");
        }
        UploadResultModel resultModel = null;
        AjaxResult result = null;
        // 导入功能的日志记录下放至service层
        try {
            resultModel = advancedCollectiveService.importData(file.getInputStream(),"某人");
        } catch (IOException e) {
            return  AjaxResult.fail(102,"获取上传的文件失败");
        } catch (ExcelException e) {
            return AjaxResult.fail(103,e.getMessage());
        } catch (Exception e) {
            return AjaxResult.fail(104,"未知异常");
        }

        // 是否全部导入成功
        if (resultModel.getError() > 0) {
            result = AjaxResult.success("没有全部导入成功");
        } else {
            result = AjaxResult.success("成功导入" + resultModel.getSuccess() + "条采样地记录");
        }
        return result;
    }

    @RequestMapping("/download")
    public Object download(@RequestBody Section section) throws Exception {
        File file = new File("D:\\ac.xlsx");
        file.createNewFile();
        section.setIsCurrent((byte)1);
        File filledFile = advancedCollectiveService.downloadData(section,file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment","ac.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(filledFile), headers, HttpStatus.CREATED);
        Object retObj = responseEntity;
        file.delete();
        return retObj;
    }

//    //下载导入模板
//    @RequestMapping("/downloadImportModel")
//    public Object downloadImportModel() throws Exception {
//        File file = new File("classpath:acImportModel.xlsx");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment","acImportModel.xlsx");
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
//        Object retObj = responseEntity;
//        file.delete();
//        return retObj;
//    }

    @RequestMapping("/export")
    public Object export(@RequestBody Section section) throws Exception {
        File file = new File("D:\\ac.xlsx");
        file.createNewFile();
        File filledFile = advancedCollectiveService.exportData(section,file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment","ac.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(filledFile), headers, HttpStatus.CREATED);
        Object retObj = responseEntity;
        file.delete();
        return retObj;
    }

    @RequestMapping("/statics")
    public AjaxResult statics(@RequestBody Section section) throws Exception {
        return AjaxResult.success(advancedCollectiveService.statics(section));
    }

    @RequestMapping("/lastStaticsResult")
    public AjaxResult lastStaticsResult() throws Exception {
        return AjaxResult.success(advancedCollectiveService.getLastStaticsResult());
    }
}
