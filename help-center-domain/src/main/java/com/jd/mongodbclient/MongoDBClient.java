package com.jd.mongodbclient;

import java.util.List;
import java.util.Map;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
/**
 * 
 * 
 * 
 * 
 * 
 * @author lishuai
 *
 * @param <E> bean����
 */
public interface MongoDBClient<E> {
  public final static String DEFAULT_CONFIG_FILE              = "mongo.properties";
  public final static String DEFAULT_MONGO_DB_HOSTNAME        = "localhost";
  public final static String DEFAULT_MONGO_DB_PORT            = "27017";
  public final static String DEFAULT_MONGO_DB_DATABASE_NAME   = "test";
  public final static String DEFAULT_MONGO_DB_COLLECTION_NAME = "test";
  
  /**
   * ����һ������
   * @param bean �������� 
   * @return �Ƿ�ɹ�
   */
  public boolean insert(E bean);
  /**
   * ����һ�����
   * @param beanCollection ���ݼ���
   * @return �Ƿ�ɹ�
   */
  public boolean insert(List<E> beanCollection);
  /**
   * �������,�������ָ���ֶ�
   * @param condition ��ѯ����
   * @param bean ����ʵ��
   * @param attributeOperationMap �����ֶ�
   * @return �Ƿ�ɹ�
   */
  public boolean insertOrUpdate(QueryBuilder condition,E bean,Map<String,String> attributeOperationMap);
  /**
   * ��������һ������(ֻ����һ����¼)
   * @param condition ��ѯ����
   * @param bean Ҫ���µ�����
   * @return �Ƿ�ɹ�
   */
  public boolean save(QueryBuilder condition,E bean);
  /**
   * ��������ɾ������
   * @param condition ɾ������
   * @return �Ƿ�ɹ�
   */
  public boolean delete(QueryBuilder condition);
  /**
   * ��������ɾ������
   * @param bean ��������
   * @return �Ƿ�ɹ�
   */
  public boolean delete(E bean);
  
  /**
   * ������������ָ�����Ե�����
   * @param condition ����
   * @param bean ����
   * @param attributeOperationMap ָ�����Եĸ��²���,key=������,value=��������
   * @return �Ƿ�ɹ�
   */
  public boolean update(QueryBuilder condition,E bean,Map<String,String> attributeOperationMap);
  /**
   * ��������,��������
   * @param condition ����
   * @param bean ���µ�����
   * @return �Ƿ�ɹ�
   */
  public boolean marge(QueryBuilder condition,E bean);
  
  /**
   * �������������ݼ���
   * @param condition ��ѯ����
   * @param index  ��ʼλ��
   * @param size ÿ��ȡ������
   * @param beanClass ����������
   * @return ���������ļ���
   */
  public List<E> select(QueryBuilder condition,int index,int size,Class<E> beanClass);
  /**
   * �������������ݼ���
   * @param condition ��ѯ����
   * @param sort �������
   * @param index ��ʼλ��
   * @param size ÿ��ȡ������
   * @param beanClass ����������
   * @return ���������ļ���
   */
  public List<E> select(QueryBuilder condition,QueryBuilder sort,int index,int size,Class<E> beanClass);
  /**
   * ������������һ����������������
   * @param condition ����
   * @param beanClass ���ض���������
   * @return �Ƿ�ɹ�
   */
  public E selectOne(QueryBuilder condition,Class<E> beanClass);
  
  /**
   * ��������,��ȡ���������ļ�¼��
   * @param condition ͳ������
   * @return ��¼��
   */
  public long selectCount(QueryBuilder condition);
  
  /**
   * group������� ���� SQL�� group by
   * @param <G> ������ķ���
   * @param key DBObject keyDBObj = new BasicDBObject();keyDBObj.put("sku", true);
   * @param condition DBObject cond = QueryBuilder.start("sku").in(longArr).get();
   * @param initial DBObject initial = new BasicDBObject();initial.put("totalTime", 0);initial.put("averagePerson", 0);
   * @param reduce String reduce = "function(doc,out){out.averagePerson++;out.totalTime+=doc.averageDay;}"
   * @param finalize String finalize = "function(out){out.averageDay=out.totalTime/out.averagePerson;}";
   * @param gClass �������͵������
   * @return ���������Ľ����
   */
  public <G> List<G> group(DBObject key,DBObject condition,DBObject initial,String reduce,String finalize,Class<G> gClass); 
  /**
   * ȥ���ظ���¼ ���磺���sku��ȥ�ز��� List<Long> result = client.distinct(SKU_KEY,query(�����ѯ����))
   * @param <T> ������ķ���
   * @param key �ƶ�ȥ���ֶ���
   * @param query ����
   * @return �������������Ľ����
   */
  public <T> List<T> distinct(String key,QueryBuilder query);
  
  
  public DBCollection getCollection();
  
  public void requestStart();
  public void requestDone();
  /**
   * �ر�ALL����
   */
  @Deprecated
  public void close();
}
