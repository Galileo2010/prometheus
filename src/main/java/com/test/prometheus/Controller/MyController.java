package com.test.prometheus.Controller;


import com.test.prometheus.GetRequestCounter;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("swaggerTestController相关api")
public class MyController {
    @Autowired
    private GetRequestCounter getRequestCounter;

    @ApiOperation(value = "计算好数之和",notes = "输入a,b计算好数之和")
    @GetMapping(value = "/")
    public int getResult(@RequestParam int a, @RequestParam int b ){
        int sum = 0;
        int left = b, right = b * b * a;
        for (int x = left; x <= right; x++){
            if(x%b == 0) continue;
            int tmp = (x/b) % (x%b);
            if(tmp == 0){
                int y = (x/b) / (x%b);
                if (y >= 1 && y <= a)
                    sum += x;
            }
        }
//        getRequestCounter.Increment();
        return sum % 1000000007;
    }
}