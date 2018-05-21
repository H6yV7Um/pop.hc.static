package com.jd.mongodbclient.sample;

import com.jd.mongodbclient.MongoDBClient;
import com.jd.mongodbclient.util.DateUtil;
import com.jd.mongodbclient.util.JsonUtil;
import com.mongodb.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class SimpleMongoDBClient<E> implements MongoDBClient<E>{
  /**
   * ���������������:�������������
   */
  public final static  int            INSERT_LIST_MAX_SIZE = 1000;
  /**
   * �������ӵ�����ֵ
   */
  private static       long           globalIndex = 0;
  /**
   * ��ʵ��������ֵ
   */
  private              long           myIndex = 0;
  /**
   * ���ݿ��ַ
   */
  private String       hostname       = DEFAULT_MONGO_DB_HOSTNAME;
  /**
   * ���ݿ�˿�
   */
  private String       port           = DEFAULT_MONGO_DB_PORT;
  /**
   * ���ݿ�����
   */
  private String       databaseName   = DEFAULT_MONGO_DB_DATABASE_NAME;
  /**
   * ������
   */
  private String       collectionName = DEFAULT_MONGO_DB_COLLECTION_NAME;
  /**
   * �û���
   */
  private String       userName       = null;
  /**
   * ����
   */
  private String       password       = null;
  /**
   * ��־����
   */
  private String       logName        = null;
  /**
   * ��¼��־����
   */
  private Logger       logger         = null;
  /**
   * Mongo����
   */
  private Mongo        mongo          = null;
  /**
   * Address
   */
  private ServerAddress address       = null;
  /**
   * ���ݿ����
   */
  private DB           database       = null;
  /**
   * ���ݱ����
   */
  private DBCollection collection     = null;
  /**
   * �����ļ���
   */
  private String configFile           = null;
  /**
   * ���ö���
   */
  private Properties mongoConfig      = null;
  
  private String isReconstructMongoId  = "false";
  private String connectionsPerHost    = "400";
  private String threadsAllowedToBlockForConnectionMultiplier = "100";
  //lishuai ����
  private String connectionTimeoutPoolMS = "180000";
  private String intervalCleanOldPortThreadSecond = "120";
  private String minConnectSize = "100";
  public SimpleMongoDBClient(Properties configProperties)
  {
    globalIndex++;
    myIndex = globalIndex;
    this.mongoConfig = configProperties;
    load(configProperties);
    if(logName==null||"".equals(logName))
      this.logger = Logger.getLogger(SimpleMongoDBClient.class);
    else
      this.logger = Logger.getLogger(logName);
    logger.info("SimpleMongoDBClient2(String "+configProperties+") create index="+myIndex);
  }

  public SimpleMongoDBClient(Properties configProperties,String host,String port,String databaseName,String collectionName, String userName,String password)
  {
    this.hostname = host;
    this.port = port;
    this.databaseName = databaseName;
    this.collectionName = collectionName;
    this.userName = userName;
    this.password = password;
    globalIndex++;
    myIndex = globalIndex;
    this.mongoConfig = configProperties;
    load(configProperties);
    if(logName==null||"".equals(logName))
      this.logger = Logger.getLogger(SimpleMongoDBClient.class);
    else
      this.logger = Logger.getLogger(logName);
    logger.info("SimpleMongoDBClient2(String "+configProperties+") create index="+myIndex);
  }

  public SimpleMongoDBClient(String configFile){
    globalIndex++;
    myIndex = globalIndex;
    this.configFile = configFile;
    load(configFile);
    if(logName==null||"".equals(logName))
      this.logger = Logger.getLogger(SimpleMongoDBClient.class);
    else
      this.logger = Logger.getLogger(logName);
    logger.info("SimpleMongoDBClient2("+configFile+") create index="+myIndex);
  }

  public DBCollection getCollection() {
    return collection;
  }
  public void requestStart(){
    //mongo.getConnector().requestStart();
  }
  public void requestDone(){
//    mongo.getConnector().requestDone();
//    DBPortPool pool = mongo.getConnector().getDBPortPool(address);
//    DBPort port = pool.get();
//    pool.cleanup(port);
  }
  @Deprecated
  public void close()
  {
    logger.info("SimpleMongoDBClient2("+(configFile==null?mongoConfig:configFile)+") close index="+myIndex);
    mongo.close();
  }
  
  @SuppressWarnings("unchecked")
  public <T> List<T> distinct(String key,QueryBuilder query) {
    List<T> result = this.collection.distinct(key, query.get());
    return result;
  }

  public <G> List<G> group(DBObject key, DBObject condition, DBObject initial,
      String reduce, String finalize,Class<G> gClass) {
    DBObject result = collection.group(key, condition, initial, reduce, finalize);
    JsonUtil<G> util = new JsonUtil<G>();
    List<G> resultList = util.toObjectList(result.toString(), List.class, gClass);
    return resultList;
  }

  public boolean delete(QueryBuilder condition) {
    WriteResult result = collection.remove(condition.get());
    if(result.getError()!=null){
//      logger.error("MongoDB ɾ���쳣:"+result.getError());
      return false;
    }
    else{
      return true;
    }
  }


  public boolean delete(E bean) {
    JsonUtil<E> util = new JsonUtil<E>();
    DBObject dbObj = util.toDBObject(bean);
    
    WriteResult result = collection.remove(dbObj);
    if(result.getError()!=null){
//      logger.error("MongoDB ɾ���쳣:"+result.getError());
      return false;
    }
    else{
      return true;
    }
  }

  public boolean insert(E bean) {
    JsonUtil<E> util = new JsonUtil<E>();
    DBObject dbObj = util.toDBObject(bean);
    WriteResult result = collection.insert(dbObj);
    if(result.getError()!=null){
//      logger.error("MongoDB �����쳣:"+result.getError());
      return false;
    }
    else{
      return true;
    }
  }

  public boolean insert(List<E> beanCollection) {
    boolean isSuccess = false;
    //System.out.println(beanCollection);
    if(beanCollection==null||beanCollection.size()<INSERT_LIST_MAX_SIZE){
      JsonUtil<E> util = new JsonUtil<E>();
      WriteResult result = collection.insert(util.toDBObjectList(beanCollection));
      if(result.getError()!=null){
//        logger.error("MongoDB ���������쳣:"+result.getError());
        isSuccess = false;
      }
      else{
        isSuccess = true;
      }
    }
    else{
     isSuccess = false;
//     logger.error("MongoDB ���������쳣:�������������ֵ������Ϊ��");
//      logger.error("MongoDB ���������쳣:�������������ֵ������Ϊ��");
    }
    return isSuccess;
  }

  public boolean insertOrUpdate(QueryBuilder condition, E bean,
      Map<String, String> attributeOperationMap) {
    boolean isSuccess = false;
    JsonUtil<E> util = new JsonUtil<E>();
    if(collection.insert(util.toDBObject(bean)).getError()!=null)
    {
      if(update(condition, bean, attributeOperationMap))
        isSuccess = true;
    }
    else{
      isSuccess = true;
    }
    return isSuccess;
  }

  public boolean marge(QueryBuilder condition, E bean) {
    return save(condition,bean);
  }

  public boolean save(QueryBuilder condition, E bean) {
    JsonUtil<E> util = new JsonUtil<E>();
    DBObject oldDBObj = collection.findOne(condition.get());
    DBObject newDBObj = util.toDBObject(bean);
    if(oldDBObj!=null)
      newDBObj.put("_id", oldDBObj.get("_id"));
    WriteResult result = collection.save(newDBObj);
    if(result.getError()!=null){
//      logger.error("MongoDB save�쳣:"+result.getError());
      return false;
    }
    else{
      return true;
    }
  }

  public List<E> select(QueryBuilder condition, int index, int size,Class<E> beanClass) {
    
    List<DBObject> dbObjList = collection.find(condition.get(), reconstructMongoId()).skip(index).limit(size).toArray();
    JsonUtil<E> util = new JsonUtil<E>();
    List<E> result = util.toObjectList(dbObjList, List.class, beanClass);
    return result;
  }

  public long selectCount(QueryBuilder condition) {
    return collection.count(condition.get());
  }

  public E selectOne(QueryBuilder condition,Class<E> beanClass) {
    
    DBObject dbObj = collection.findOne(condition.get(),reconstructMongoId());
    JsonUtil<E> util = new JsonUtil<E>();
    return dbObj!=null?util.toObject(dbObj.toString(), beanClass):null;
  }
  
  @SuppressWarnings("unchecked")
  public boolean update(QueryBuilder condition, E bean,Map<String, String> attributeOperationMap) {
    boolean isSuccess = true;
    //oneself
    for(Field f:bean.getClass().getDeclaredFields())
    {
      BasicDBObjectBuilder newDBObj = BasicDBObjectBuilder.start();
      f.setAccessible(true);
      String fieldName = f.getName();
      String operation = attributeOperationMap.get(fieldName);
      if(operation!=null){
        DBObject append = new BasicDBObject();
        try {
          JsonUtil util = new JsonUtil();
          if(f.getType().equals(List.class))
            append.put(fieldName, util.toDBObject(f.get(bean)));
          else if(f.getType().equals(Date.class))
            append.put(fieldName, DateUtil.format((Date) f.get(bean), DateUtil.YYYYMMDD_HHMMSS));
          else
            append.put(fieldName, f.get(bean));
        } catch (Exception e) {
          logger.error("��������:"+fieldName+"ʧ��.", e);
          e.printStackTrace();
        }
        newDBObj.append(operation, append);
        WriteResult result = collection.update(condition.get(), newDBObj.get(), false, true);
        if(result.getError()!=null){
//          logger.error("MongoDB update�쳣:"+result.getError());
          isSuccess = false&isSuccess;
          break;
        }
        else{
          isSuccess = true&isSuccess;
        }
      }
    }
    //superClass
    for(Field f:bean.getClass().getSuperclass().getDeclaredFields())
    {
      BasicDBObjectBuilder newDBObj = BasicDBObjectBuilder.start();
      f.setAccessible(true);
      String fieldName = f.getName();
      String operation = attributeOperationMap.get(fieldName);
      if(operation!=null){
        DBObject append = new BasicDBObject();
        try {
          JsonUtil util = new JsonUtil();
          if(f.getType().equals(List.class))
            append.put(fieldName, util.toDBObject(f.get(bean)));
          else if(f.getType().equals(Date.class))
            append.put(fieldName, DateUtil.format((Date) f.get(bean), DateUtil.YYYYMMDD_HHMMSS));
          else
            append.put(fieldName, f.get(bean));
        } catch (Exception e) {
          logger.error("��������:"+fieldName+"ʧ��.", e);
          e.printStackTrace();
        }
        newDBObj.append(operation, append);
        WriteResult result = collection.update(condition.get(), newDBObj.get(), false, true);
        if(result.getError()!=null){
//          logger.error("MongoDB update�쳣:"+result.getError());
          isSuccess = false&isSuccess;
          break;
        }
        else{
          isSuccess = true&isSuccess;
        }
      }
    }
    return isSuccess;
    
  }
  private DBObject reconstructMongoId()
  {
    return Boolean.valueOf(isReconstructMongoId)?QueryBuilder.start().get():QueryBuilder.start("_id").is(0).get();
  }
  private void load(Properties mongoConfig)
  {
    //System.out.println(mongoConfig);
    try {
      initFields(mongoConfig);
      initMongo();
    } catch (Exception e) {
      logger.error("�޷����������ļ�:"+mongoConfig, e);
    }
  }
  private void load(String configFile)
  {
    Properties config = new Properties();
    try {
      config.load(SimpleMongoDBClient.class.getClassLoader().getResourceAsStream(configFile));
      initFields(config);
      initMongo();
    } catch (IOException e) {
      logger.error("�޷����������ļ�:"+configFile, e);
    }
  }
  
  /**
   * ΪSimpleMongoDBClient��ʼ������
   * @param config ��ʼ������ֵ
   */
  private void initFields(Properties config)
  {
    Class<?> c = SimpleMongoDBClient.class;
    for(Field f:c.getDeclaredFields())
    {
      //System.out.println(f.getName());
      String key = c.getName()+"."+f.getName();
      String value = config.getProperty(key);
      if(value!=null&&value.trim().length()>0)
      {
        f.setAccessible(true);
        try {
          f.set(this,value);
        } catch (Exception e) {
          logger.error("��ʼ�����ݿ����["+f.getName()+"="+value+"]ʧ��",e);
        }
      }
    }
  }
  /**
   * ��ʼ��mongo,database��collectionʵ��
   */
  private void initMongo()
  {
    try {
      MongoOptions options = new MongoOptions();
      options.connectionsPerHost = Integer.valueOf(this.connectionsPerHost);
      options.threadsAllowedToBlockForConnectionMultiplier = Integer.valueOf(threadsAllowedToBlockForConnectionMultiplier);
      //options.setConnectionTimeoutPoolMS(Long.valueOf(connectionTimeoutPoolMS));
      //options.setIntervalCleanOldPortThreadSecond(Integer.valueOf(intervalCleanOldPortThreadSecond));
      //options.setMinConnectSize(Integer.valueOf(minConnectSize));
      if(!hostname.contains(",")){
        address = new ServerAddress(hostname,Integer.valueOf(port));
        this.mongo = new Mongo(address,options);
      }
      else{
        String[] hostNames = hostname.split(",");
        List<ServerAddress> addresses = new ArrayList<ServerAddress>();
        for(String host:hostNames){
          addresses.add(new ServerAddress(host,Integer.valueOf(port)));
        }
        this.mongo = new Mongo(addresses,options);
      }
      this.database = mongo.getDB(databaseName);
      if(userName!=null && userName.trim().length()>0 &&
          password!=null && password.trim().length()>0)
      {
        if(!database.authenticate(userName, password.toCharArray()))
        {
//          logger.error("�ʺŻ����벻��ȷ,userName="+userName+",password="+password);
          throw new RuntimeException("Unable to authenticate with MongoDB server.");
        }
      }
      this.collection = database.getCollection(collectionName);
    } catch (Exception e) {
      logger.error("��ʼ��Mongoʧ��",e);
    }
  }

  public List<E> select(QueryBuilder condition, QueryBuilder sort, int index,
      int size, Class<E> beanClass) {
    List<DBObject> dbObjList = null;
    if(sort!=null)
      dbObjList = collection.find(condition.get(), reconstructMongoId()).sort(sort.get()).skip(index).limit(size).toArray();
    else
      dbObjList = collection.find(condition.get(), reconstructMongoId()).skip(index).limit(size).toArray();
    JsonUtil<E> util = new JsonUtil<E>();
    List<E> result = util.toObjectList(dbObjList, List.class, beanClass);
    return result;
  }
  
}
