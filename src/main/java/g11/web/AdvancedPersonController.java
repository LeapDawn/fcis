package g11.web;

import g11.service.AdvancedPersonService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cody on 2017/8/7.
 */
public class AdvancedPersonController {
    @Autowired
    @Setter
    AdvancedPersonService advancedPersonService;
}
