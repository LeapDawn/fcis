package g11.dto.pageModel;

import lombok.Data;
import sun.reflect.generics.tree.ByteSignature;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/8.
 */
@Data
public class Section {
    private String ids;
    private Date beginDate; //开始日期
    private Date endDate;  //结束日期
    private Byte isCurrent; //是否为当届，当届isCurrent = 1，不是当届isCurrent = 0
    private Byte type;//0表示先进个人，1表示先进集体
}
