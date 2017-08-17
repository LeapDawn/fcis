package g11.dto;

import lombok.Data;

/**
 * Created by cody on 2017/7/20.
 */
@Data
public class RequestList<T> {

    private int rows;    // 单页显示记录数
    private int page;   // 页码
    private T key;  // 搜索条件
}
