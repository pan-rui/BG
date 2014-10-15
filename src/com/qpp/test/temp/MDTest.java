package com.qpp.test.temp;

import com.alibaba.fastjson.JSON;
import com.qpp.dao.CartItemDao;
import com.qpp.model.TOrder;
import com.qpp.service.user.impl.CartJob;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by qpp on 8/11/2014.
 */
public class MDTest {

    public static void am(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update("Hello World,My name is Pan Ray".getBytes("UTF-8"));
        md.update("Security...".getBytes("UTF-8"));   //更新了  "Security".getBytes().length 长度的字节....
        byte[] res1 = md.digest();
        System.out.println(res1.length);
        StringBuffer sb = new StringBuffer();
        for (byte b : res1) {
            int b16=b&0xff;
            if(b16<16)
                sb.append(0);
//            sb.append(Integer.toHexString(b16));
            sb.append(Integer.toString(b16, 16));  //等同于上面....
        }
        System.out.println(sb.toString());
        System.out.println("encode\t" + URLEncoder.encode("a=aaa&b=bbb", "UTF-8"));

        StringTokenizer st = new StringTokenizer("a=aaa&b=bbb", "&");int i=0;
        while(st.hasMoreTokens()){
            System.out.println(++i);
            System.out.println(st.nextToken());
        }
        System.out.println("88888\t"+st.countTokens());
        System.out.println("Type \t"+new HashSet<TOrder>().getClass().getName());
        System.out.println("clild Type \t"+new HashSet<TOrder>().getClass().getTypeParameters()[0].getName());
        System.out.println(" TypeVar \t"+new HashSet<TOrder>().getClass().getTypeParameters()[0].getGenericDeclaration());

        System.out.println(" ArgmType \t" + (new HashSet<TOrder>().getClass().getTypeParameters()[0].getBounds()[0]));
        System.out.println("Date\t"+new Date());
    }

    public static void fd(String[] args) {
        Map<String, String> reslut = new HashMap<String, String>();
        reslut.put("isDefault", "true");
        reslut.put("address", "湖北省天门市");
        List<Map> list = new ArrayList<Map>();
        list.add(reslut);
        String jsonStr=JSON.toJSONString(list);
        System.out.println(jsonStr);
        Map<String, String> reslu = new HashMap<String, String>();
        reslu.put("isDefault", "false");
        reslu.put("address", "湖北省武汉市");
        List<Map> ll=JSON.parseArray(jsonStr, Map.class);
        ll.add(reslu);
        System.out.println(JSON.toJSONString(ll));
//        JobDetail jobDetail = new JobDetail("testJob", "testGroup", TestJob.class);
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class).withIdentity("testJob3", "testGroup3").storeDurably(false).build();
        jobDetail.getJobDataMap().put("cart", ll);
        jobDetail.getJobDataMap().put("dao",new CartItemDao());
//        SimpleTrigger simpleTrigger = new SimpleTrigger("testTrigger", "testGrou", new Date(System.currentTimeMillis()+5000), new Date(System.currentTimeMillis() + 50000), 2, 20000);
//        SimpleTrigger si1 = new SimpleTrigger("myTrigger", null, new Date(), null, SimpleTrigger.REPEAT_INDEFINITELY, 60L * 1000L);
//        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("testTrigger3", "testGroup3").startAt(new Date(System.currentTimeMillis()+1000)).withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(1).withIntervalInSeconds(5)).forJob(jobDetail).build();
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("testTrigger3", "testGroup3").startAt(new Date(System.currentTimeMillis()+1*60*1000)).forJob(jobDetail).build();
//        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger().withIdentity("testTrigger3", "testGroup3").startAt(new Date(System.currentTimeMillis()+1000)).withSchedule(CronScheduleBuilder.cronSchedule("*/5 33 9 ? * *")).build();

//        SimpleTriggerImpl simpleTrigger=new SimpleTriggerImpl();
//        simpleTrigger.setRepeatCount(10);
        try {
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    public static void ma(String[] args) throws SchedulerException {
//        XmlWebApplicationContext context=new XmlWebApplicationContext();
//        context.setConfigLocation("/WEB-INF/spring/applicationContext-baseSet.xml");
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("resources/abc.xml");
//        Object obj = context.getBean("jobDetail");
//        System.out.println(obj instanceof JobDetail);
//        System.out.println(((JobDetail) obj).getJobDataMap().entrySet().iterator().next().getKey());
//        System.out.println(((JobDetail) obj).getJobDataMap().entrySet().iterator().next().getValue());
//        System.out.println("自定义类任务");
//        JobDataMap dataMap=((JobDetail) obj).getJobDataMap();
//        JobDetail jobDetail = JobBuilder.newJob(CartJob.class).withIdentity("cartJob", "cartGroup").build();
        JobDetail jobDetail = JobBuilder.newJob(CartJob.class).build();
        JobDataMap dataMap=jobDetail.getJobDataMap();
        dataMap.put("dao", new CartItemDao());
        dataMap.put("obj", new ArrayList());
        dataMap.put("userId", "3fg3fsdfsdf");
//        StdScheduler scheduler = (StdScheduler) context.getBean("scheduler");
//        SimpleTriggerImpl trigger= (SimpleTriggerImpl) context.getBean("cartTrigger");
//        Trigger trigger = new TriggerUtil(1000, 1, new Date(System.currentTimeMillis()+3000), new Date(System.currentTimeMillis()+10000)).builderTrigger();
//        scheduler.clear();
        SimpleTrigger trigger= (SimpleTrigger) TriggerBuilder.newTrigger().startNow().withIdentity("testTrigge", "testGrou").build();
        Scheduler scheduler=new StdSchedulerFactory().getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }

    public static void main(String[] args) {
//        String path=System.getProperty("user.dir");
//        System.out.println(path);
//        ApplicationContext ctx=new FileSystemXmlApplicationContext(new String[]{path+"\\WebContent\\WEB-INF\\spring\\applicationContext-hibernate.xml",path+"\\WebContent\\WEB-INF\\spring\\applicationContext-basedao.xml",path+"\\WebContent\\WEB-INF\\spring\\applicationContext-baseSet.xml"});
//        HibernateTemplate template = (HibernateTemplate) ctx.getBean("hibernateTemplate");
//        template.loadAll(TConfig.class);
//        ConfigDao dao= (ConfigDao) ctx.getBean("configDao");
//
//        dao.getBySQL("select * from t_config");
        System.out.println(System.currentTimeMillis());
        System.out.println(new Date().getTime());
        System.out.println(new Timestamp(new Date().getTime()));
        System.out.println(JSON.parse(null)==null);
        System.out.println("-------------------");
        List arrayList = Arrays.asList(11l, 32l, 36l);
        System.out.println(arrayList instanceof ArrayList);
        System.out.println(arrayList.getClass());
        System.out.println(arrayList.contains(32l));
        System.out.println("---------------------");
        System.out.println("Long is Max:"+Long.MAX_VALUE);
        System.out.println(Integer.MAX_VALUE);
        System.out.println("--------------------------");
        System.out.println((short)1!=1);
        System.out.println("---------------");
//        try {
//            Object a= new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(null)); // NullPointerException
//        System.out.println(a);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
        System.out.println("潘正-->小车+老婆孩子");
        System.out.println("奋力追赶中...........");
        System.out.println("args = [" + args + "]");
        System.out.println("MDTest.main");
        System.out.println("arrayList = " + arrayList);
        System.out.println("15+0.5x");
        System.out.println("JobExcutaionContext");
        System.out.println(Math.atan(0.5d)*180/Math.PI);
        System.out.println("年轻无极限...........");
        System.out.println((8>>>2|1)==3);
        System.out.println((8>>>2&1)==0);
        new TreeMap<String,Object>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        });
        Properties properties = System.getProperties();
        Set sset=properties.keySet();
        for(Object obj:sset)
            System.out.println("key-> "+obj+"\tvalue-> "+properties.get(obj));
        System.out.println("----------env");
        Map map = System.getenv();
        for(Object obj:map.keySet())
            System.out.println("key-> "+obj+"\tvalue-> "+map.get(obj));
    }

}
