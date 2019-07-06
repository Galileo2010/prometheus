package com.test.prometheus.Controller;


import com.test.prometheus.RestMessage;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("swaggerTestController相关api")
public class MyController {

    /**
     * Restful Get请求测试
     */
    @ApiOperation(value = "根据id查询学生的信息",notes = "查询数据库中某个学生的信息")
    @ApiImplicitParam(name ="id",value = "学生id",paramType = "path",required = true,dataType = "String")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "用户id",dataType = "String",paramType = "query",example = "1112")
    })
    @ApiResponses({
            @ApiResponse(code=400,message = "请求参数没有填好"),
            @ApiResponse(code=404,message="请求路径没有找到")
    })
    @GetMapping(value = "testRest/{id}")
    public RestMessage testGetResetful(@PathVariable String id){
        RestMessage restMessage = new RestMessage();
        System.out.println(id);
        return restMessage;
    }
}