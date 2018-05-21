package com.jd.help.center.admin.sysinfo;

import com.jd.common.struts.action.BaseAction;
import com.jd.common.web.result.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.management.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.sql.DataSource;
import java.io.File;
import java.lang.management.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * User: yangsiyong
 * Date: 12-6-11
 * Up: ????2:25
 * version 1.1
 */
@SuppressWarnings("unchecked")
public class SystemInfoAction extends BaseAction {
    private final static Log log = LogFactory.getLog(SystemInfoAction.class);
    private String method;

    private static DecimalFormat decimalFormat = new DecimalFormat("0.00");
    Up[] timeDeeps = new Up[]{
            new Up("ms", 1000), new Up("s", 60), new Up("m", 60), new Up("h", 24), new Up("day", 30), new Up("month", 12)
    };
    Up[] timeDeeps2 = new Up[]{
            new Up("ns", 1000), new Up("ms", 1000), new Up("s", 60), new Up("m", 60), new Up("h", 24), new Up("day", 30), new Up("month", 12)
    };

    Up[] byteDeeps = new Up[]{
            new Up("Byte", 1024), new Up("KB", 1024), new Up("MB", 1024), new Up("GB", 1024), new Up("TB", 1024)
    };

    @SuppressWarnings("unchecked")
    @Override
    public String execute() throws Exception {
        if ("threads".equals(method)) {
            return threads();
        } else if ("processors".equals(method)) {
            return processors();
        } else {
            Result result = new Result(true);
            step1(result);

            step2(result);

            step3(result);

            step4(result);
            step5(result);
            step6(result);
            toVm(result);

            return SUCCESS;
        }
    }

    public String threads() {
        Result result = new Result(true);
        step1(result);
        step2(result);
        step7(result);
        toVm(result);
        return SUCCESS;
    }

    public String processors() {
        Result result = new Result(true);
        step1(result);
        step2(result);
        step8(result);
        toVm(result);
        return SUCCESS;
    }

    private void step7(Result result) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(threadMXBean.isObjectMonitorUsageSupported(), threadMXBean.isSynchronizerUsageSupported());
        LinkedHashMap threads = new LinkedHashMap();
        result.addDefaultModel("threadDetails", threads);
        boolean cpu = threadMXBean.isThreadCpuTimeSupported() && threadMXBean.isThreadCpuTimeEnabled();
        for (ThreadInfo threadInfo : threadInfos) {
            long threadId = threadInfo.getThreadId();
            long threadUserTime = threadMXBean.getThreadUserTime(threadId);
            String key = "id=" + threadId
                    + ", blockedCount=" + threadInfo.getBlockedCount()
                    + ", blockedTime=" + (threadInfo.getBlockedTime() <= 0 ? 0 : formatUptime2(threadInfo.getBlockedTime()))
                    + ", waitedCount=" + threadInfo.getWaitedCount()
                    + ", waitedTime=" + (threadInfo.getWaitedTime() <= 0 ? 0 : formatUptime2(threadInfo.getWaitedTime()))
                    + ", threadUserTime=" + (threadUserTime <= 0 ? 0 : formatNsTime2(threadUserTime));
            if (cpu) {
                long threadCpuTime = threadMXBean.getThreadCpuTime(threadId);
                key += ", threadCpuTime=" + (threadCpuTime <= 0 ? 0 : formatNsTime2(threadCpuTime));
            }
            threads.put(key, threadToString(threadInfo));
        }
        result.addDefaultModel("threadMXBean", threadMXBean);
    }

    public String threadToString(ThreadInfo threadInfo) {

        StringBuilder sb = new StringBuilder("\"" + threadInfo.getThreadName() + "\"" +
                " Id=" + threadInfo.getThreadId() + " " +
                threadInfo.getThreadState());
        if (threadInfo.getLockName() != null) {
            sb.append(" on " + threadInfo.getLockName());
        }
        if (threadInfo.getLockOwnerName() != null) {
            sb.append(" owned by \"" + threadInfo.getLockOwnerName() +
                    "\" Id=" + threadInfo.getLockOwnerId());
        }
        if (threadInfo.isSuspended()) {
            sb.append(" (suspended)");
        }
        if (threadInfo.isInNative()) {
            sb.append(" (in native)");
        }
        sb.append('\n');
        int i = 0;
        StackTraceElement[] stackTrace = threadInfo.getStackTrace();
        for (; i < stackTrace.length; i++) {
            StackTraceElement ste = stackTrace[i];
            sb.append("\tat " + ste.toString());
            sb.append('\n');
            if (i == 0 && threadInfo.getLockInfo() != null) {
                Thread.State ts = threadInfo.getThreadState();
                switch (ts) {
                    case BLOCKED:
                        sb.append("\t-  blocked on " + threadInfo.getLockInfo());
                        sb.append('\n');
                        break;
                    case WAITING:
                        sb.append("\t-  waiting on " + threadInfo.getLockInfo());
                        sb.append('\n');
                        break;
                    case TIMED_WAITING:
                        sb.append("\t-  waiting on " + threadInfo.getLockInfo());
                        sb.append('\n');
                        break;
                    default:
                }
            }
            MonitorInfo[] lockedMonitors = threadInfo.getLockedMonitors();
            for (MonitorInfo mi : lockedMonitors) {
                if (mi.getLockedStackDepth() == i) {
                    sb.append("\t-  locked " + mi);
                    sb.append('\n');
                }
            }
        }


        LockInfo[] locks = threadInfo.getLockedSynchronizers();
        if (locks.length > 0) {
            sb.append("\n\tNumber of locked synchronizers = " + locks.length);
            sb.append('\n');
            for (LockInfo li : locks) {
                sb.append("\t- " + li);
                sb.append('\n');
            }
        }
        sb.append('\n');
        return sb.toString();
    }


    private void step8(Result result) {

        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        Map processors = new LinkedHashMap();
        try {
            Set<ObjectName> objectNames = platformMBeanServer.queryNames(new ObjectName("Catalina:type=RequestProcessor,*"), null);
            for (ObjectName objectPool : objectNames) {
                tomcatProcessorInfo(processors, platformMBeanServer, objectPool);
            }
        } catch (MalformedObjectNameException e) {
            log.error("get request processor error", e);
        }
        result.addDefaultModel("requestProcessors", processors);
    }


    /**
     * 收集tomcat相关的信息
     *
     * @param result
     */
    @SuppressWarnings("unchecked")
    private void step6(Result result) {
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            Set<ObjectName> objectNames;
            Map threadPools = new LinkedHashMap();
            objectNames = platformMBeanServer.queryNames(new ObjectName("Catalina:type=ThreadPool,*"), null);
            for (ObjectName objectPool : objectNames) {
                tomcatThreadPool(threadPools, platformMBeanServer, objectPool);
            }
            result.addDefaultModel("catalinaThreadPool", threadPools);

            objectNames = platformMBeanServer.queryNames(new ObjectName("Catalina:j2eeType=Servlet,*"), null);
            Map servlets = new LinkedHashMap();
            for (ObjectName objectPool : objectNames) {
                tomcatServletInfo(servlets, platformMBeanServer, objectPool);
            }
            result.addDefaultModel("servletInfo", servlets);


            objectNames = platformMBeanServer.queryNames(new ObjectName("Catalina:type=StringCache,*"), null);
            Map stringCaches = new LinkedHashMap();
            for (ObjectName objectPool : objectNames) {
                tomcatStringCacheInfo(stringCaches, platformMBeanServer, objectPool);
            }
            result.addDefaultModel("stringCaches", stringCaches);

            objectNames = platformMBeanServer.queryNames(new ObjectName("Catalina:type=Connector,*"), null);
            Map connectors = new LinkedHashMap();
            for (ObjectName objectPool : objectNames) {
                tomcatConnectorInfo(connectors, platformMBeanServer, objectPool);
            }
            result.addDefaultModel("connectors", connectors);

            objectNames = platformMBeanServer.queryNames(new ObjectName("Catalina:type=GlobalRequestProcessor,*"), null);
            Map globalRequestProcessors = new LinkedHashMap();
            for (ObjectName objectPool : objectNames) {
                tomcatGlobalProcessorInfo(globalRequestProcessors, platformMBeanServer, objectPool);
            }
            result.addDefaultModel("globalRequestProcessors", globalRequestProcessors);

            objectNames = platformMBeanServer.queryNames(new ObjectName("Catalina:type=DataSource,*"), null);
            Map dataSources = new LinkedHashMap();
            for (ObjectName objectPool : objectNames) {
                tomcatDataSourceInfo(dataSources, platformMBeanServer, objectPool);
            }
            result.addDefaultModel("globalDataSources", dataSources);


        } catch (Exception e) {
            log.error("get sysinfo step6 error", e);
        }
    }

    private void tomcatDataSourceInfo(Map dataSources, MBeanServer platformMBeanServer, ObjectName objectPool) {
        String[] poolKeys = new String[]{"numActive", "numIdle", "numTestsPerEvictionRun",
                "driverClassName", "url", "username", "defaultCatalog", "defaultAutoCommit", "defaultTransactionIsolation", "defaultReadOnly",
                "maxActive", "maxIdle", "minIdle", "initialSize", "maxWait",
                "testOnBorrow", "validationQuery", "testOnReturn", "testWhileIdle",
                "poolPreparedStatements", "maxOpenPreparedStatements", "removeAbandoned", "removeAbandonedTimeout", "logAbandoned",
                "minEvictableIdleTimeMillis", "timeBetweenEvictionRunsMillis", "accessToUnderlyingConnectionAllowed"};
        Map value = putValueFromMbean(dataSources, platformMBeanServer, objectPool, poolKeys);

    }

    private void tomcatGlobalProcessorInfo(Map globalRequestProcessors, MBeanServer platformMBeanServer, ObjectName objectPool) {
        String[] poolKeys = new String[]{"bytesSent", "bytesReceived", "processingTime", "errorCount", "maxTime", "requestCount"};
        Map value = putValueFromMbean(globalRequestProcessors, platformMBeanServer, objectPool, poolKeys);

        value.put("bytesSent", formatByte((Long) value.get("bytesSent")));
        value.put("bytesReceived", formatByte((Long) value.get("bytesReceived")));

        Long processingTime = (Long) value.get("processingTime");
        Integer requestCount = (Integer) value.get("requestCount");
        if (processingTime != null && requestCount != null && processingTime > 0 && requestCount > 0) {
            value.put("avgProcessingTime", (int) (processingTime / requestCount));
        }
        value.put("processingTime", formatUptime(processingTime));
    }

    private void tomcatConnectorInfo(Map connectors, MBeanServer platformMBeanServer, ObjectName objectPool) {
        String[] poolKeys = new String[]{"protocol", "scheme", "address", "port", "redirectPort", "compression", "bufferSize", "maxPostSize", "maxHttpHeaderSize", "connectionUploadTimeout", "disableUploadTimeout", "URIEncoding", "useBodyEncodingForURI", "enableLookups", "proxyName", "proxyPort", "maxThreads", "maxSpareThreads", "minSpareThreads", "acceptCount", "connectionLinger", "connectionTimeout", "tcpNoDelay", "maxKeepAliveRequests", "keepAliveTimeout", "strategy", "xpoweredBy", "allowTrace"};
        Map value = putValueFromMbean(connectors, platformMBeanServer, objectPool, poolKeys);
        connectors.put(value.get("protocol") + "-" + value.get("port"), value);
    }

    private void tomcatStringCacheInfo(Map stringCaches, MBeanServer platformMBeanServer, ObjectName objectPool) {
        String[] poolKeys = new String[]{"trainThreshold", "charEnabled", "byteEnabled", "hitCount", "accessCount", "cacheSize"};
        Map value = putValueFromMbean(stringCaches, platformMBeanServer, objectPool, poolKeys);
        stringCaches.put("stringCache", value);
    }

    private void tomcatProcessorInfo(Map processors, MBeanServer platformMBeanServer, ObjectName objectPool) {
        String[] poolKeys = new String[]{"method", "stage", "virtualHost", "serverPort", "maxTime", "requestCount", "errorCount", "method",
                "requestProcessingTime", "processingTime", "bytesSent", "bytesReceived", "currentUri",
                "currentQueryString", "requestBytesReceived", "requestBytesSent", "lastRequestProcessingTime", "remoteAddr"};
        Map value = putValueFromMbean(processors, platformMBeanServer, objectPool, poolKeys);

        value.put("bytesSent", formatByte((Long) value.get("bytesSent")));
        value.put("bytesReceived", formatByte((Long) value.get("bytesReceived")));
    }

    private void tomcatServletInfo(Map servlets, MBeanServer platformMBeanServer, ObjectName objectPool) {
        String[] poolKeys = new String[]{"minTime", "processingTime", "maxTime", "errorCount", "loadTime", "classLoadTime", "requestCount"};
        Map value = putValueFromMbean(servlets, platformMBeanServer, objectPool, poolKeys);
        Long processingTime = (Long) value.get("processingTime");
        Integer requestCount = (Integer) value.get("requestCount");
        if (processingTime != null && requestCount != null && processingTime > 0 && requestCount > 0) {
            value.put("avgProcessingTime", (int) (processingTime / requestCount));
        }
        value.put("processingTime", formatUptime(processingTime));
    }

    private Map putValueFromMbean(Map map, MBeanServer beanServer, ObjectName objectName, String[] poolKeys) {
        Map value = new LinkedHashMap();
        try {
            for (String poolKey : poolKeys) {
                Object attribute = beanServer.getAttribute(objectName, poolKey);
                if ("minTime".equals(poolKey) && attribute.equals(0x7FFFFFFFFFFFFFFFL)) {
                    attribute = 0;
                }
                value.put(poolKey, attribute);
            }
            String name = objectName.getKeyProperty("name");
            if (name != null) {
                map.put(name, value);
            }
        } catch (Exception e) {
            log.error("get mbean value error! namedObject=" + objectName, e);
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    private void tomcatThreadPool(Map pool, MBeanServer platformMBeanServer, ObjectName objectPool) {
        String name = objectPool.getKeyProperty("name");
        Map value = new LinkedHashMap();

        String[] poolKeys = null;
        if (name.contains("http")) {
            poolKeys = new String[]{"port", "maxThreads", "soTimeout", "acceptorThreadCount", "name", "currentThreadsBusy", "currentThreadCount"};
        } else if (name.contains("jk")) {
            poolKeys = new String[]{"maxThreads", "maxSpareThreads", "minSpareThreads", "currentThreadCount", "currentThreadsBusy"};
        }

        if (poolKeys != null) {
            try {
                for (String poolKey : poolKeys) {
                    value.put(poolKey, platformMBeanServer.getAttribute(objectPool, poolKey));
                }
                Integer maxThreads = (Integer) value.get("maxThreads");
                Integer currentThreadCount = (Integer) value.get("currentThreadCount");
                Integer currentThreadsBusy = (Integer) value.get("currentThreadsBusy");

                int restThreadCount = currentThreadCount - currentThreadsBusy;
                int spareThreadCount = maxThreads - currentThreadsBusy;


                int percent = getPercent(maxThreads, restThreadCount);
                if(restThreadCount>0&percent<1) percent=1;
                int percent1 = getPercent(maxThreads, currentThreadsBusy);
                if(currentThreadsBusy>0&percent1<1) percent1=1;
                value.put("restThreadPercent", percent);
                value.put("currentThreadPercent", percent1);
                int percent2 = 100-percent-percent1;
                value.put("spareThreadPercent", percent2);
                value.put("restThreadCount", restThreadCount);
                value.put("spareThreadCount", spareThreadCount);

                pool.put(name, value);
            } catch (Exception e) {
                log.error("get tomcat thread pool error!", e);
            }
        }
    }

    /**
     * 保证属性不被破坏
     *
     * @param object 被取值对象
     * @param field  被取值字段
     * @return 值内容
     */
    private Object getValue(Object object, Field field) {
        final boolean accessible = field.isAccessible();
        try {
            if (!accessible) {
                field.setAccessible(true);
            }
            return field.get(object);
        } catch (Exception e) {
            log.error("get field value error! object=" + object + " field=" + field, e);
            return null;
        } finally {
            if (!accessible) {
                field.setAccessible(accessible);
            }
        }

    }

    /**
     * 收集磁盘和网络相关的
     *
     * @param result
     * @throws java.net.SocketException
     */
    private void step4(Result result) throws SocketException {
        File[] partitions = File.listRoots();
        Percent[] percents = new Percent[partitions.length];
        for (int i = 0, partitionsLength = partitions.length; i < partitionsLength; i++) {
            File partition = partitions[i];
            Percent percent = new Percent(partition.getPath(), formatByte(partition.getTotalSpace()), formatByte(partition.getTotalSpace() - partition.getUsableSpace()), formatByte(partition.getFreeSpace()));
            percent.usedPercent = getPercent(partition.getTotalSpace(), partition.getTotalSpace() - partition.getUsableSpace());
            percent.freePercent = 100 - percent.usedPercent;

            percents[i] = percent;
        }

        result.addDefaultModel("partitions", percents);

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        result.addDefaultModel("networkInterfaces", networkInterfaces);
    }

    /**
     * 收集操作系统相关的内容
     *
     * @param result
     */
    private void step3(Result result) {
        com.sun.management.OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        result.addDefaultModel("operator", operatingSystemMXBean);
        result.addDefaultModel("totalPhysical", formatByte(operatingSystemMXBean.getTotalPhysicalMemorySize()));
        result.addDefaultModel("freePhysical", formatByte(operatingSystemMXBean.getFreePhysicalMemorySize()));
        result.addDefaultModel("totalSwap", formatByte(operatingSystemMXBean.getTotalSwapSpaceSize()));
        result.addDefaultModel("freeSwap", formatByte(operatingSystemMXBean.getFreeSwapSpaceSize()));
        result.addDefaultModel("commitVirtual", formatByte(operatingSystemMXBean.getCommittedVirtualMemorySize()));

    }

    /**
     * 收集编译器和运行时间相关的信息
     *
     * @param result
     */
    private void step2(Result result) {
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        result.addDefaultModel("compilation", compilationMXBean);
        result.addDefaultModel("compilationTime", formatUptime2(compilationMXBean.getTotalCompilationTime()));

        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        result.addDefaultModel("classLoad", classLoadingMXBean);


        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        result.addDefaultModel("startTime", new Date(runtimeMXBean.getStartTime()));
        result.addDefaultModel("uptime", formatUptime(runtimeMXBean.getUptime()));
        result.addDefaultModel("now", new Date());
        result.addDefaultModel("runtime", runtimeMXBean);

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        result.addDefaultModel("threads", threadMXBean);

    }

    /**
     * 收集Java内存相关的内容
     *
     * @param result
     */
    private void step1(Result result) {

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        result.addDefaultModel("heapMemory", memToMap(heapMemoryUsage));

        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        result.addDefaultModel("nonHeapMemory", memToMap(nonHeapMemoryUsage));


        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        List<Percent> mems = new ArrayList<Percent>();
        for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
            Percent percent = new Percent(garbageCollectorMXBean.getName(), formatUptime2(garbageCollectorMXBean.getCollectionTime()), Long.toString(garbageCollectorMXBean.getCollectionCount()), null);
            mems.add(percent);
        }
        result.addDefaultModel("garbages", mems);

        LinkedHashMap memoryPools = new LinkedHashMap();
        result.addDefaultModel("memoryPools", memoryPools);
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        List<MemoryPoolMXBean> heaps = new ArrayList();
        List<MemoryPoolMXBean> nonHeaps = new ArrayList();
        int totalHeaps=0,totalNonHeaps=0;
        for (MemoryPoolMXBean memoryPoolMXBean : memoryPoolMXBeans) {
            LinkedHashMap value = new LinkedHashMap();
            value.put("memoryPoolMXBean", memoryPoolMXBean);
            MemoryUsage usage = memoryPoolMXBean.getUsage();
            value.put("usage", memToMap(usage));
            value.put("peakUsage", memToMap(memoryPoolMXBean.getPeakUsage()));
            MemoryUsage collectionUsage = memoryPoolMXBean.getCollectionUsage();
            if (collectionUsage != null) {
                value.put("connectionUsage", memToMap(collectionUsage));
            }
            if (memoryPoolMXBean.getType()== MemoryType.HEAP) {
                heaps.add(memoryPoolMXBean);
                totalHeaps+=memoryPoolMXBean.getUsage().getMax();
            } else if(memoryPoolMXBean.getType()== MemoryType.NON_HEAP){
                nonHeaps.add(memoryPoolMXBean);
                totalNonHeaps+=memoryPoolMXBean.getUsage().getMax();
            }
            memoryPools.put(memoryPoolMXBean.getName(), value);
        }
        HashMap heapsMap = new HashMap();
        memoryPools.put("heaps", heapsMap);
        for (MemoryPoolMXBean heap : heaps) {
            heapsMap.put(heap.getName(), getPercent(totalHeaps, heap.getUsage().getMax()));
        }
        HashMap nooHeapsMap = new HashMap();
        memoryPools.put("nooHeaps", nooHeapsMap);
        for (MemoryPoolMXBean heap : nonHeaps) {
            nooHeapsMap.put(heap.getName(), getPercent(totalNonHeaps, heap.getUsage().getMax()));
        }
    }

    /**
     * 收集连接池相关的信息
     *
     * @param result
     */
    @SuppressWarnings("unchecked")
    public void step5(Result result) {
        Map sourceMap = new HashMap();
        try {
            final InitialContext initialContext = new InitialContext();
            final Context envContext = (Context) initialContext.lookup("java:comp/env/jdbc");
            Map dataSources = (Map) getValue(envContext, envContext.getClass().getDeclaredField("bindings"));
            Collection values = dataSources.values();
            for (Object namingEntry : values) {
                Class entry = namingEntry.getClass();
                Field keyField = entry.getDeclaredField("name");
                Object key = keyField.get(namingEntry);

                Field valueField = entry.getDeclaredField("value");
                Object dsObj = valueField.get(namingEntry);
                if (dsObj == null) {
                    continue;
                }
                if (dsObj.getClass().toString().indexOf("ResourceLinkRef") != -1) {
                    Reference ref = (Reference) dsObj;
                    RefAddr refAddr = ref.get("globalName");
                    if (refAddr == null) {
                        refAddr = ref.get("globalname");
                    }
                    if (refAddr == null) {
                        refAddr = ref.get("global");
                    }
                    if (refAddr != null) {
                        String globalName = refAddr.getContent().toString();
                        globalName = "java:comp/env/" + globalName;
                        dsObj = initialContext.lookup(globalName);
                        if (dsObj == null) {
                            log.info("==========数据源：" + globalName + "未获取到==========");
                            continue;
                        }
                    }
                }
                sourceMap.put(key, getDataSourceStatus((DataSource) dsObj));
            }
            result.addDefaultModel("dataSources", sourceMap);
        } catch (final Exception e) {
            log.error("get dataSource status error!", e);
        }

    }

    protected String getDataSourceStatus(DataSource source) {
        try {
            Object genericObjectPool = getValue(source, source.getClass().getDeclaredField("connectionPool"));
            if (genericObjectPool != null) {
                Class genericObjectPoolClass = genericObjectPool.getClass();
                Object maxActive = String.valueOf(getValue(genericObjectPool, genericObjectPoolClass.getDeclaredField("_maxActive")));
                String debugInfo = String.valueOf(methodValue(genericObjectPool, genericObjectPoolClass.getDeclaredMethod("debugInfo")));
                return "maxActive: " + maxActive + "\n" + debugInfo;
            }
        } catch (Exception e) {
            log.error("get dataSource status error!", e);
        }
        return null;
    }

    private Object methodValue(Object object, Method method) {
        final boolean accessible = method.isAccessible();
        try {
            if (!accessible) {
                method.setAccessible(true);
            }
            return method.invoke(object);
        } catch (Exception e) {
            log.error("get field value error! object=" + object + " field=" + method, e);
            return null;
        } finally {
            if (!accessible) {
                method.setAccessible(accessible);
            }
        }
    }

    private int getPercent(float max, float used) {
        return (int) ((used / max) * 100);
    }


    /**
     * ???????????
     *
     * @param value ?
     * @return ????
     */
    private String formatUptime(long value) {
        return Up.format(timeDeeps, value);
    }

    /**
     * ???????????
     *
     * @param value ?
     * @return ????
     */
    private String formatUptime2(long value) {
        return Up.format2(timeDeeps, value);
    }

    /**
     * 格式化纳秒时间显示
     *
     * @param value
     * @return
     */
    public String formatNsTime(long value) {
        return Up.format(timeDeeps, value);
    }

    /**
     * 格式化纳秒时间显示
     *
     * @param value
     * @return
     */
    public String formatNsTime2(long value) {
        return Up.format2(timeDeeps2, value);
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * ?????byte
     *
     * @param value ?
     * @return ????
     */
    private String formatByte(long value) {
        return Up.format2(byteDeeps, value);
    }


    @SuppressWarnings("unchecked")
    private Map memToMap(MemoryUsage heapMemoryUsage) {
        Map heap = new LinkedHashMap();
        heap.put("max", formatByte(heapMemoryUsage.getMax()));
        heap.put("init", formatByte(heapMemoryUsage.getInit()));
        heap.put("commit", formatByte(heapMemoryUsage.getCommitted()));
        heap.put("used", formatByte(heapMemoryUsage.getUsed()));
        heap.put("free", formatByte((heapMemoryUsage.getMax() - heapMemoryUsage.getUsed())));
        int usedPercent = getPercent(heapMemoryUsage.getMax(), heapMemoryUsage.getUsed());
        heap.put("usedPercent", usedPercent);
        heap.put("freePercent", 100 - usedPercent);
        return heap;
    }


    /**
     * ??????λ???????
     */
    static class Up {
        /**
         * ??λ???
         */
        public String name;
        /**
         * ????
         */
        public int unit;

        Up(String name, int unit) {
            this.name = name;
            this.unit = unit;
        }

        /**
         * 显示完整的，详细的格式化信息
         *
         * @param ups
         * @param value
         * @return
         */
        public static String format(Up[] ups, long value) {
            StringBuilder values = new StringBuilder();
            for (int i = 0, timeDeepsLength = ups.length; i < timeDeepsLength; i++) {
                Up deep = ups[i];
                if (i == timeDeepsLength - 1) {
                    values.insert(0, value + deep.name + " ");
                } else {
                    long remainder = value % deep.unit;
                    if (remainder > 0) {
                        values.insert(0, remainder + deep.name + " ");
                    }
                    value = value / deep.unit;
                    if (value < 1) {
                        break;
                    }
                }
            }
            return values.toString();
        }

        /**
         * 显示最大的格式化信息
         *
         * @param ups
         * @param value
         * @return
         */
        public static String format2(Up[] ups, long value) {
            long max = 1;
            for (int i = 0, timeDeepsLength = ups.length; i < timeDeepsLength; i++) {
                Up deep = ups[i];
                if (i == timeDeepsLength - 1) {
                    return decimalFormat.format(value / max) + deep.name;
                } else {
                    if (value / deep.unit < max) {
                        return decimalFormat.format((float) value / (float) max) + deep.name;
                    }
                }
                max *= deep.unit;
            }
            return null;
        }
    }


}