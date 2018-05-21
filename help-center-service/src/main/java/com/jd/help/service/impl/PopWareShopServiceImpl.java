package com.jd.help.service.impl;

import com.jd.help.service.PopWareShopService;
import com.jd.ware.shop.api.mjbbs.bean.ForumThreadNoticeResult;
import com.jd.ware.shop.api.mjbbs.service.ForumThreadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangmanliang on 2016/12/20.
 */
@Service
public class PopWareShopServiceImpl implements PopWareShopService {

    private static Logger logger= LoggerFactory.getLogger(PopWareShopServiceImpl.class);

    @Resource
    private ForumThreadService forumThreadService;

    @Override
    public String queryForumThreadList(Integer num) {
        String content1 = "<div class='module cl xl xl1'>\n<ul>";
        String  weiStr = "</ul>\n</div>";
        String content="";
        try {
            List<ForumThreadNoticeResult> forumThreadNoticeResultList= forumThreadService.queryForumThreadList(7);
            String str1="<li><a href='";
            String str2="' title='";
            String str3="' target='_blank'>";
            String str4="</a></li>";
            if(forumThreadNoticeResultList!=null){
                for(ForumThreadNoticeResult forumThreadNoticeResult:forumThreadNoticeResultList){
                    Long noticeId=forumThreadNoticeResult.getNoticeId();
                    String noticeName=forumThreadNoticeResult.getNoticeName();
                    String NoticeUrl=forumThreadNoticeResult.getNoticeUrl();
                    String clstag = "' clatag='pageclick|keycount|helpcenter_home|" + noticeId;
                    content=content+str1+NoticeUrl+clstag+str2+noticeName+str3+noticeName+str4;
                    logger.info("ForumThreadNoticeResult获取接口内容："+content);
                }
                logger.info("ForumThreadNoticeResult获取接口内容最后："+content);
                content=content1+content+weiStr;
                return content;
            }
            content=content1+content+"ForumThreadNoticeResult获取接口内容为空"+weiStr;
            return content;
        } catch (Exception e) {
            logger.error("ForumThreadNoticeResult获取接口连接失败"+e);
            content=content1+content+"未获取到数据"+weiStr;
            return content;
        }
    }

}
