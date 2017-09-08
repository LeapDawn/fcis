package g11.web;

import g11.commons.config.FilePath;
import g11.commons.exception.ExcelException;
import g11.dto.AjaxResult;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.UploadResultModel;
import g11.dto.pageModel.PAdvancedPerson;
import g11.dto.pageModel.Section;
import g11.model.AdvancedPerson;
import g11.service.AdvancedPersonService;
import lombok.Setter;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by cody on 2017/8/7.
 */
@CrossOrigin
//写了@RestController，就不需要写@ResponseBody
@RestController
@RequestMapping("/ap")
public class AdvancedPersonController {
    @Autowired
    @Setter
    AdvancedPersonService advancedPersonService;
    @RequestMapping("/addition")
    public AjaxResult addition(@RequestBody PAdvancedPerson pAdvancedPerson){
        pAdvancedPerson.setIsCurrent((byte)1);
        advancedPersonService.save(pAdvancedPerson);
        return AjaxResult.success("申报成功");
    }

    @RequestMapping("/delete")
    public AjaxResult delete(@RequestBody Section section) throws Exception {
        advancedPersonService.delete(section);
        return AjaxResult.success("删除成功");
    }
    @RequestMapping("/list")
    public AjaxResult list(@RequestBody RequestList<PAdvancedPerson> requestList) throws Exception {
        PAdvancedPerson pAdvancedPerson=requestList.getKey();
        pAdvancedPerson.setIsCurrent((byte)1);
        requestList.setKey(pAdvancedPerson);
        ResultModel<PAdvancedPerson> resultModel = advancedPersonService.list(requestList);
        return AjaxResult.success(resultModel);
    }
    @RequestMapping("/update")
    public AjaxResult update(@RequestBody PAdvancedPerson pAdvancedPerson) throws Exception {
        pAdvancedPerson.setIsCurrent((byte)1);
        advancedPersonService.update(pAdvancedPerson);
        return AjaxResult.success("修改成功");
    }
    @RequestMapping(value="/upload", method= RequestMethod.POST)
    @ResponseBody
    public AjaxResult upload(@RequestParam("file")MultipartFile file){
        if (file == null) {
            return AjaxResult.fail(201,"上传的文件为空");
        }
        UploadResultModel resultModel = null;
        AjaxResult result = null;
        // 导入功能的日志记录下放至service层
        try {
            resultModel = advancedPersonService.importData(file.getInputStream(),"某人");
        } catch (IOException e) {
            return  AjaxResult.fail(202,"获取上传的文件失败");
        } catch (ExcelException e) {
            return AjaxResult.fail(203,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.fail(204,"未知异常");
        }

        // 是否全部导入成功
        if (resultModel.getError() > 0) {
            result = AjaxResult.fail(205, resultModel);
        } else {
            result = AjaxResult.success(resultModel);
        }
        return result;
    }

    @RequestMapping("/download")
    public Object download(@RequestParam String ids) throws Exception {
        Section section = new Section();
        section.setIds(ids);
        File file = new File(FilePath.FILE_DIR_PATH + System.currentTimeMillis() +".xls");
        file.createNewFile();
        section.setIsCurrent((byte)1);
        File filledFile = advancedPersonService.downloadData(section,file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment","ap.xls");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(filledFile), headers, HttpStatus.CREATED);
        Object retObj = responseEntity;
        file.delete();
        return retObj;

    }
    @RequestMapping("/export")
    public Object export(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date beginDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date endDate) throws Exception {
        Section section = new Section();
        section.setBeginDate(beginDate);
        section.setEndDate(endDate);
        File file = new File(FilePath.FILE_DIR_PATH + System.currentTimeMillis() +".xls");
        file.createNewFile();
        section.setIsCurrent((byte)1);
        File filledFile = advancedPersonService.exportData(section,file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment","ap.xls");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(org.apache.commons.io.FileUtils.readFileToByteArray(filledFile), headers, HttpStatus.CREATED);
        Object retObj = responseEntity;
        file.delete();
        return retObj;
    }
    @RequestMapping("/statics")
    public AjaxResult statics(@RequestBody Section section) throws Exception {
        section.setIsCurrent((byte)1);
        return AjaxResult.success(advancedPersonService.statics(section));
    }

    @RequestMapping("/recognise")
    public AjaxResult recognise(@RequestBody List<AdvancedPerson> aclist){

        advancedPersonService.recognise(aclist);
        return AjaxResult.success("操作成功");
    }
    @RequestMapping("/overdue")
    public AjaxResult overdue(@RequestBody Section section){
        advancedPersonService.overdue(section);
        return AjaxResult.success("已全部更为历届");

    }

    @RequestMapping("/lastStaticsResult")
    public AjaxResult lastStaticsResult() throws Exception {
        return AjaxResult.success(advancedPersonService.getLastStaticsResult());
    }
}
