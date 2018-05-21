package com.jd.help.dao.util;

import junit.framework.Assert;
import org.junit.Test;

/**
 * @author laichendong
 * @since 14-12-1 ионГ10:51
 */
public class PageTest {

    @Test
    public void testPage() throws Exception {
        Page page = new Page();
        for (int i = 1; i < 6; i++) {
            page.setPage(i);
            System.out.println(page.getStartRow() + "-" + page.getEndRow());
            switch (i) {
                case 1:
                    Assert.assertEquals(0, page.getStartRow());
                    Assert.assertEquals(19, page.getEndRow());
                    break;
                case 2:
                    Assert.assertEquals(20, page.getStartRow());
                    Assert.assertEquals(39, page.getEndRow());
                    break;
                case 5:
                    Assert.assertEquals(80, page.getStartRow());
                    Assert.assertEquals(99, page.getEndRow());
                    break;
            }

        }

    }
}
