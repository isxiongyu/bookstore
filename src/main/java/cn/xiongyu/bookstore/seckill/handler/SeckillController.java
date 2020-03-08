package cn.xiongyu.bookstore.seckill.handler;

import cn.xiongyu.bookstore.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: SeckillController
 * Package: cn.xiongyu.bookstore.seckill
 * Description:
 * Date: 2020/2/18 下午2:37
 * Author: xiongyu
 */
@RequestMapping("/seckill")
@Controller
public class SeckillController {
    @Autowired
    ISeckillService seckillService;
    ExecutorService executor = Executors.newFixedThreadPool(5);

    @ResponseBody
    @RequestMapping("/threadPoll.do")
    public Map<String, Integer> threadPoll(int seckillId) {
        Map<String, Integer> map = null;
        for (int i = 0; i < 1000; i++) {
            Runnable task = () -> {
                seckillService.seckillLock(seckillId);
            };
            executor.execute(task);
            map = new HashMap<>();
            map.put("code", 200);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/unlock.do")
    public Map<String, Integer> unlock(int seckillId) {
        seckillService.seckillUnlock(seckillId);
        Map<String, Integer> map = new HashMap<>();
        map.put("code", 200);
        return map;
    }
    @ResponseBody
    @RequestMapping("/lock.do")
    public Map<String, Integer> lock(int seckillId) {
        seckillService.seckillLock(seckillId);
        Map<String, Integer> map = new HashMap<>();
        map.put("code", 200);
        return map;
    }
    @ResponseBody
    @RequestMapping("/lockDBO.do")
    public Map<String, Integer> lockDBO(int seckillId) {
        seckillService.seckillDBO(seckillId);
        Map<String, Integer> map = new HashMap<>();
        map.put("code", 200);
        return map;
    }
    @ResponseBody
    @RequestMapping("/lockDBP.do")
    public Map<String, Integer> lockDBP(int seckillId) {
        seckillService.seckillDBP(seckillId);
        Map<String, Integer> map = new HashMap<>();
        map.put("code", 200);
        return map;
    }
    @ResponseBody
    @RequestMapping("/redisQueue.do")
    public Map<String, Integer> redisQueue(int seckillId) {
        seckillService.seckillRedisQueue(seckillId);
        Map<String, Integer> map = new HashMap<>();
        map.put("code", 200);
        return map;
    }
}
