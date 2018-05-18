package com.lee.tac.inner;

import com.lee.tac.dto.TestcaseLogDto;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-25 16:14
 **/
public interface TestcaseLogService {

    void updateTestcaseLog(TestcaseLogDto testcaseLogDto);

    TestcaseLogDto selectTestcaseLogById(Integer id);
}
