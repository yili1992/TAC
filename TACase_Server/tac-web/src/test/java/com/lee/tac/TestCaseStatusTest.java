package com.lee.tac;

import com.lee.tac.responses.CommonResponse;
import com.lee.tac.dto.TestcaseStatusDto;
import com.lee.tac.facade.TestcaseStatusApiImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

// 在当前宿主工程里使用spring-test进行集成测试
// https://docs.spring.io/spring/docs/3.2.x/spring-framework-reference/html/testing.html
// https://docs.spring.io/spring/docs/current/spring-framework-reference/html/integration-testing.html
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
// 可按需重写application.properties的配置
// @SpringBootTest("tac.sample=hello test")
// 按需选择
@ActiveProfiles("development")
public class TestCaseStatusTest {
    @Resource
    TestcaseStatusApiImpl caseStatusApiImpl;

    @Test
    public void getAllStatus() {
        CommonResponse<List<TestcaseStatusDto>> result = caseStatusApiImpl.getAllStatus();
        for(TestcaseStatusDto testcaseStatusDto : result.getData()){
            System.out.println(testcaseStatusDto.getName());
        }
    }
}
