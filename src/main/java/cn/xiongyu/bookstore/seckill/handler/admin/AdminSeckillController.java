package cn.xiongyu.bookstore.seckill.handler.admin;

import cn.xiongyu.bookstore.seckill.domain.SeckillBook;
import cn.xiongyu.bookstore.seckill.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: AdminSeckillController
 * Package: cn.xiongyu.bookstore.seckill.handler.admin
 * Description:
 * Date: 2020/3/4 下午4:57
 * Author: xiongyu
 */
@Controller
@RequestMapping("/admin/seckill")
public class AdminSeckillController {
    @Autowired
    ISeckillService seckillService;
    @ResponseBody
    @RequestMapping("/addSeckillCache.do")
    public Map<String, Integer> addSeckillCache(int seckillId, int number) {
        seckillService.addSeckillCache(seckillId, number);
        Map<String, Integer> map = new HashMap<>();
        map.put("code", 200);
        return map;
    }
}
