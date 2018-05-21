package com.jd.help.action.test.action;

import com.jd.common.web.result.Result;
import com.jd.help.action.test.BaseTest;
import com.jd.help.center.domain.category.query.HelpCategoryQuery;
import com.jd.help.center.service.category.HelpCategoryService;
import org.junit.Test;

/**
 * Created by gaofei on 14-2-25
 */
public class HelpCategoryServiceTest extends BaseTest {
    private HelpCategoryService helpCategoryService;

    @Test
    public void findCategoryByQuery(){
        HelpCategoryQuery helpCategoryQuery = new HelpCategoryQuery();

        try {
            Result result = helpCategoryService.findCategoryByQuery(helpCategoryQuery);
            System.out.println(result.isSuccess());

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void setHelpCategoryService(HelpCategoryService helpCategoryService) {
        this.helpCategoryService = helpCategoryService;
    }
}
