package g11.web;

import g11.commons.config.FilePath;
import g11.commons.exception.ExcelException;
import g11.dto.AjaxResult;
import g11.dto.RequestList;
import g11.dto.ResultModel;
import g11.dto.pageModel.PAdvancedPerson;
import g11.dto.pageModel.Section;
import g11.model.AdvancedCollective;
import g11.service.AllModelWorkerService;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by cody on 2017/8/7.
 */
@CrossOrigin
@RestController
@RequestMapping("/AllModelWorkerController")
public class AllModelWorkerController {
    @Autowired
    @Setter
    AllModelWorkerService allModelWorkerService;
    /**
     * 新增先进个人历届劳模
     */
    @PostMapping("/saveAdvancedPerson")
    public AjaxResult saveAdvancedPerson(@RequestBody  PAdvancedPerson pAdvancedPerson){
        allModelWorkerService.saveAdvancedPerson(pAdvancedPerson);
        return AjaxResult.success("保存历届劳模（个人）成功");
    }

    /**
     * 新增先进集体历届劳模
     * @param ac 先进集体信息
     */
    @PostMapping("/saveAdvancedCollective")
    public AjaxResult saveAdvancedCollective(@RequestBody AdvancedCollective ac){
        allModelWorkerService.saveAdvancedCollective(ac);
        return AjaxResult.success("保存历届劳模（集体）成功");
    }

    /**
     * 删除先进个人历届劳模
     */
    @PostMapping("/deleteAdvancedPerson")
    public AjaxResult deleteAdvancedPerson(@RequestBody Section section){
        allModelWorkerService.deleteAdvancedPerson(section);
        return AjaxResult.success("删除历届劳模（个人）成功");
    }

    /**
     * 删除先进集体历届劳模
     */
    @PostMapping("/deleteAdvancedCollective")
    public AjaxResult deleteAdvancedCollective(@RequestBody Section section){
        allModelWorkerService.deleteAdvancedCollective(section.getIds());
        return AjaxResult.success("删除历届劳模（集体）成功");
    }

    /**
     * 根据条件分页查询先进个人历届劳模
     * @param rl
     * @return
     */
    @PostMapping("/listAdvancedPerson")
    public AjaxResult listAdvancedPerson(@RequestBody  RequestList<PAdvancedPerson> rl){
        return AjaxResult.success(allModelWorkerService.listAdvancedPerson(rl));
    }

    /**
     * 根据条件分页查询先进集体历届劳模
     * @param rl
     * @return
     */
    @PostMapping("/listAdvancedCollective")
    public AjaxResult listAdvancedCollective(@RequestBody RequestList<AdvancedCollective> rl){
        return AjaxResult.success(allModelWorkerService.listAdvancedCollective(rl));
    }

    /**
     * 修改先进个人历届劳模
     * @return
     */
    @PostMapping("/updateAdvancedPerson")
    public AjaxResult updateAdvancedPerson(@RequestBody PAdvancedPerson pAdvancedPerson){
        allModelWorkerService.updateAdvancedPerson(pAdvancedPerson);
        return AjaxResult.success("修改历届劳模（个人）成功");
    }

    /**
     * 修改先进集体历届劳模
     * @param ac 先进集体信息
     */
    @PostMapping("/updateAdvancedCollective")
    public AjaxResult updateAdvancedCollective(@RequestBody AdvancedCollective ac){
        allModelWorkerService.updateAdvancedCollective(ac);
        return AjaxResult.success("修改历届劳模（集体）成功");
    }
    @GetMapping("/downloadData")
    public Object downloadData(@RequestParam String ids, @RequestParam int type) throws ExcelException,IOException {
        Section section = new Section();
        section.setIds(ids);
        section.setType((byte)type);
        File file = new File(FilePath.FILE_DIR_PATH + System.currentTimeMillis() +".xls");
        file.createNewFile();
        File filledFile=allModelWorkerService.downloadData(section,file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment","amwc.xls");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(filledFile), headers, HttpStatus.CREATED);
        Object retObj = responseEntity;
        file.delete();
        return retObj;
    }
}
