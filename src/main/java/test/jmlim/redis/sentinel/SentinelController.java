package test.jmlim.redis.sentinel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import test.jmlim.redis.sentinel.SentinelService;

@Controller
public class SentinelController {
    @Autowired
    private SentinelService sentinelService;

    /* Key-Value in the code
    String KEY = "username";
    String VALUE = "jmlim";
     */
    @Value("${property.RedisKey}")
    private String KEY;
    @Value("${property.RedisValue}")
    private String VALUE;

    @RequestMapping(value = "/sentinel", method= RequestMethod.GET)
    public String standalone(Model model) {

        sentinelService.setRedisValue(KEY, VALUE);
        String RESULT = sentinelService.getRedisValue(KEY);
        System.out.println(RESULT);
        model.addAttribute("RESULT", RESULT);
        return "redis.html";
    }
}
