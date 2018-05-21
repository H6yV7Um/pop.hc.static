package com.jd.help.action.test.dao;

import com.jd.help.action.test.BaseTest;
import com.jd.help.dao.KnowledgeContentDao;
import com.jd.help.dao.KnowledgeDao;
import com.jd.help.domain.knowledge.Knowledge;
import com.jd.help.domain.knowledge.KnowledgeContent;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yfxialiang on 2018/4/10.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath*:*spring-config.xml"})
public class KnowledgeDaoTest extends BaseTest{

    @Autowired
    private KnowledgeDao knowledgeDao;

    @Autowired
    private KnowledgeContentDao knowledgeContentDao;

    @Test
    public void testInsertK(){
        Knowledge k1 = new Knowledge();
        k1.setBizId(1l);
        k1.setBizTypeId(1);
        k1.setBizTypeName("��������");
        k1.setName("֪ʶ1");

        Knowledge k2 = new Knowledge();
        k2.setBizId(2l);
        k2.setBizTypeId(1);
        k2.setBizTypeName("��������");
        k2.setName("֪ʶ2");

        Knowledge k3 = new Knowledge();
        k3.setBizId(3l);
        k3.setBizTypeId(2);
        k3.setBizTypeName("ѧϰ����");
        k3.setName("֪ʶ3");

        Knowledge k4 = new Knowledge();
        k4.setBizId(4l);
        k4.setBizTypeId(2);
        k4.setBizTypeName("ѧϰ����");
        k4.setName("֪ʶ4");

        Knowledge k5 = new Knowledge();
        k5.setBizId(5l);
        k5.setBizTypeId(3);
        k5.setBizTypeName("��������");
        k5.setName("֪ʶ5");

        long r1 = knowledgeDao.insert(k1);
        long r2 = knowledgeDao.insert(k2);
        long r3 = knowledgeDao.insert(k3);
        long r4 = knowledgeDao.insert(k4);
        long r5 = knowledgeDao.insert(k5);
        System.out.println(r1+"===="+r2+"===="+r3+"===="+r4+"===="+r5);

    }

    @Test
    public void testUpdateK(){
        Knowledge k = new Knowledge();
        k.setId(1l);
        k.setName("����֮���֪ʶ����");
        int r = knowledgeDao.updateByPrimaryKeySelective(k);
        System.out.println(r);
    }

    @Test
    public void testQueryKList(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("bizId",5);
        map.put("bizTypeId",3);
        List<Knowledge> list = knowledgeDao.queryByMap(map);
        System.out.println(list);
    }

    @Test
    public void testDeleteK(){
        int r = knowledgeDao.deleteByPrimaryKey(3l);
        System.out.println(r);

    }

    @Test
    public void testInsertKC(){
        KnowledgeContent c1 = new KnowledgeContent();
        c1.setKnowledgeId(4l);
        c1.setContent("֪ʶ1������");

        KnowledgeContent c2 = new KnowledgeContent();
        c2.setKnowledgeId(5l);
        c2.setContent("֪ʶ2������");

        KnowledgeContent c3 = new KnowledgeContent();
        c3.setKnowledgeId(6l);
        c3.setContent("֪ʶ3������");

        KnowledgeContent c4 = new KnowledgeContent();
        c4.setKnowledgeId(7l);
        c4.setContent("֪ʶ4������");

        KnowledgeContent c5 = new KnowledgeContent();
        c5.setKnowledgeId(8l);
        c5.setContent("֪ʶ5������");
        long r1 = knowledgeContentDao.insert(c1);
        long r2 = knowledgeContentDao.insert(c1);
        long r3 = knowledgeContentDao.insert(c1);
        long r4 = knowledgeContentDao.insert(c1);
        long r5 = knowledgeContentDao.insert(c1);
        System.out.println(r1+"===="+r2+"===="+r3+"===="+r4+"===="+r5);
    }

    @Test
    public void testUpdateKC(){
        KnowledgeContent kc = new KnowledgeContent();
        kc.setKnowledgeId(6l);
        kc.setContent("�����º��֪ʶ����");
        int r = knowledgeContentDao.updateByKnowledgeId(kc);
        System.out.println(r);

    }

    @Test
    public void testDeleteKC(){
        int r = knowledgeContentDao.deleteByKnowledgeId(8l);
        System.out.println(r);
    }

//    public KnowledgeDao getKnowledgeDao() {
//        return knowledgeDao;
//    }
//
//    public void setKnowledgeDao(KnowledgeDao knowledgeDao) {
//        this.knowledgeDao = knowledgeDao;
//    }
//
//    public KnowledgeContentDao getKnowledgeContentDao() {
//        return knowledgeContentDao;
//    }
//
//    public void setKnowledgeContentDao(KnowledgeContentDao knowledgeContentDao) {
//        this.knowledgeContentDao = knowledgeContentDao;
//    }
}
