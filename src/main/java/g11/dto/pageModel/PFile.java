package g11.dto.pageModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by cody on 2017/8/14.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PFile implements Comparable<PFile>{

    private String fileName;

    private String lastTime;

    @Override
    public int compareTo(PFile o) {
        return o.getLastTime().compareTo(lastTime);
    }
}
