package g11.commons.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import g11.commons.exception.ExcelException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Excel文件操作工具类
 */
public class ExcelUtil {

    // 表头(列)数组
	private String[] headArray;
	// excel表数据
	private List<Map<String, Object>> bodyList;
	// 供导入时对表头进行验证的模版
	private String[] modelArray;
	// 指定哪些列为数字
	private String[] numberArray;

    /**
     * 读取excel文件
     * @param input  读取的文件流
     * @param modelArray 表头模版
     * @return excel表数据(只读取表头对应数据)
     * @throws ExcelException
     */
	public List<Map<String, Object>> readExcel(InputStream input, String[] modelArray) throws ExcelException {
	    this.setModelArray(modelArray);
	    this.readExcel(input);
	    return this.getBodyList();
    }


	/**
	 * 读取excel文件
	 * 1.执行该方法前需先设置modelArray,以供验证excel
	 * 2.读取excel表头
	 * 3.验证excel是否符合模版(不符合则抛出ExcelException)
	 * 4.读取excel具体数据
	 * 5.通过getter获取excel表头和数据
	 * @throws ExcelException
	 */
	public void readExcel(InputStream input) throws ExcelException {
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(input);
		} catch (Exception e) {
			throw new ExcelException("读取excel文件失败");
		}
		Sheet sheet = book.getSheet(0);
		// 获取表头
		headArray = new String[sheet.getColumns()];
	    for (int i = 0, size = sheet.getColumns(); i < size; i++) {
	    	headArray[i] = sheet.getCell(i, 0).getContents().trim();
		}
	    // 验证表头,验证不通过则抛出ExcelException给调用者处理
        checkExcelHead();

        // 遍历表格内容
	    bodyList = new ArrayList<Map<String, Object>>();
		for (int i = 1, rows = sheet.getRows(); i < rows; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			if (!sheet.getCell(0, i).getContents().trim().equals("")) {
				for (int j = 0, columns = sheet.getColumns(); j < columns; j++) {
						String value = sheet.getCell(j, i).getContents().trim();
						map.put(headArray[j], value);
				}
				bodyList.add(map);
			} else {
				break;
			}
		}
	}

    /**
     * 写入excel文件
     * @param file 文件名称
     * @param sheetName sheet名称
     * @param headArray excel表头数组
     * @param bodyList  excel数据集合
     * @throws ExcelException
     */
	public void writeExcel(File file, String sheetName, String[] headArray, List<Map<String, Object>> bodyList) throws ExcelException {
	    this.setHeadArray(headArray);
        this.setBodyList(bodyList);
        this.writeExcel(file, sheetName);
	}

	/**
	 * 将数据写入excel文件中
	 * 1.执行该方法前,需先设置bodyList和HeadList
	 * 2.写入表头
	 * 3.写入具体数据
	 * 4.写入传入的file文件中
	 * @param file
	 * @param sheetName
	 * @throws ExcelException
	 */
	public void writeExcel(File file, String sheetName) throws ExcelException {
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            WritableCellFormat decimalFormat = new WritableCellFormat(new NumberFormat("0.00"));
            WritableCellFormat intFormat = new WritableCellFormat(new NumberFormat("0"));
            for (int i = 0; i < headArray.length; i++) {
                sheet.addCell(new Label(i, 0, headArray[i]));
            }
            for (int i = 1; i <= bodyList.size(); i++) {
                Map<String, Object> map = bodyList.get(i-1);
                for (int j = 0; j < headArray.length; j++) {
                    if (checkIsSpecial(headArray[j])) {
                        double num = (Double) map.get(headArray[j]);
                        if (num != (int) num) {
                            sheet.addCell(new Number(j, i, num, decimalFormat));
                        } else {
                            sheet.addCell(new Number(j, i, num, intFormat));
                        }

                    } else {
                        sheet.addCell(new Label(j, i, (String) map.get(headArray[j])));
                    }
                }
            }
            workbook.write();
        } catch (IOException e) {
            throw new ExcelException("创建excel文件失败");
        } catch (WriteException e) {
            throw new ExcelException("写入excel文件失败");
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 * 验证excel是否符合模版
	 * @throws ExcelException 
	 */
	private void checkExcelHead() throws ExcelException{
		StringBuilder errorMsg = new StringBuilder("上传文件格式错误， 缺少列:\n");
		boolean flag=true;
        for (String modelStr : modelArray) {
            boolean flag_1 = false;
            for (int j = 0; j < headArray.length && flag_1 == false; j++) {
                if (modelStr.equals(headArray[j])) {
                    flag_1 = true;
                }
            }

            if (!flag_1) {
                flag = false;
                errorMsg.append("\"").append(modelStr).append("\", ");
            }
        }
		if (!flag) {
			errorMsg = new StringBuilder(errorMsg.substring(0, errorMsg.length() - 2));
			throw new ExcelException(errorMsg.toString());
		}
	}
	
	/**
	 * 验证该字段是否属于该数组
	 */
	private boolean checkIsSpecial(String str){
		if (numberArray == null) {
			return false;
		}
        for (String aNumberArray : numberArray) {
            if (aNumberArray.trim().equals(str)) {
                return true;
            }
        }
		return false;
	}
	
	public String[] getHeadArray() {
		return headArray;
	}

	public void setHeadArray(String[] headArray) {
		this.headArray = headArray;
	}

	public List<Map<String, Object>> getBodyList() {
		return bodyList;
	}

	public void setBodyList(List<Map<String, Object>> bodyList) {
		this.bodyList = bodyList;
	}

	public String[] getModelArray() {
		return modelArray;
	}

	public void setModelArray(String[] modelArray) {
		this.modelArray = modelArray;
	}

	public String[] getNumberArray() {
		return numberArray;
	}

	public void setNumberArray(String[] numberArray) {
		this.numberArray = numberArray;
	}
}
