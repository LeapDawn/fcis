package g11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultModel {

	private int total; // 导入总记录数
	private int error; // 失败记录数
	private int success; // 成功记录数
	private List<?> data; // 导入失败数据
}
