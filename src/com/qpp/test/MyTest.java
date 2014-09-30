package com.qpp.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.qpp.dao.BaseDao;
import com.qpp.model.ApiLog;
import com.qpp.model.AppKey;
import com.qpp.model.TCountry;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: gary,
 * Time: 下午5:36
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:D:/work/java/BGM/WebContent/WEB-INF/spring/applicationContext*.xml"})
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=false)//,
public class MyTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HibernateTemplate hibernateTemplate;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private MongoTemplate mongoTemplate;


    protected final Logger loger = Logger.getLogger(this.getClass().getName());
    public void freeTest(){
        //request.getSession().getServletContext().getRealPath("/"),Struts=this.getServletContect().getRealPath("/");
        String path=System.getProperty("user.dir");
        Configuration cfg=new   Configuration();
        try{
            cfg.setDirectoryForTemplateLoading(new File(path+"\\WebContent\\template"));
            Map root=new HashMap();
            root.put("user","Gary");
            Template plate=cfg.getTemplate("register.html");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //Writer out1 = new OutputStreamWriter(baos);
            plate.process(root, new OutputStreamWriter(out));
            //out1.flush();
            String aa=out.toString();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void jsonTest(){
    	try{
    		String test=FileUtils.readFileToString(new File("d:\\temp\\ret.json"));
    		ObjectMapper mapper =new ObjectMapper();
    		JsonNode json=mapper.readTree(test);
    		ArrayNode nameNode =(ArrayNode) json.path("Data");
    		//JSONObject object =JSON.parseObject(test);
    		System.out.println(nameNode.get(1));
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }


    public void test_Upload() throws Exception{
            String classPath = new File(MyTest.class.getResource("/").getFile()).getCanonicalPath();
            String configFilePath = classPath + File.separator + "fdfs_client.conf";
            System.out.println("配置文件:" + configFilePath);
            ClientGlobal.init(configFilePath);

            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            NameValuePair[] meta_list = new NameValuePair[3];
            meta_list[0] = new NameValuePair("width", "120");
            meta_list[1] = new NameValuePair("heigth", "120");
            meta_list[2] = new NameValuePair("author", "gary");

//      byte[] file_buff = "F:\\pic.jpg".getBytes(ClientGlobal.g_charset);
            File file = new File("d:\\temp\\fastdfs.jpg");
            FileInputStream fis = new FileInputStream(file);
            byte[] file_buff = null;
            if(fis != null){
                int len = fis.available();
                file_buff = new byte[len];
                fis.read(file_buff);
            }
            System.out.println("file length: " + file_buff.length);

            String group_name = null;
            StorageServer[] storageServers = trackerClient.getStoreStorages(trackerServer, group_name);
            if (storageServers == null) {
                System.err.println("get store storage servers fail, error code: " + storageClient.getErrorCode());
            }else{
                System.err.println("store storage servers count: " + storageServers.length);
                for (int k = 0; k < storageServers.length; k++){
                    System.err.println(k + 1 + ". " + storageServers[k].getInetSocketAddress().getAddress().getHostAddress() + ":" + storageServers[k].getInetSocketAddress().getPort());
                }
                System.err.println("");
            }

            long startTime = System.currentTimeMillis();
            String[] results = storageClient.upload_file(file_buff, "jpg", meta_list);
            System.out.println("upload_file time used: " + (System.currentTimeMillis() - startTime) + " ms");

            if (results == null){
                System.err.println("upload file fail, error code: " + storageClient.getErrorCode());
                return;
            }

            group_name = results[0];
            String remote_filename = results[1];
            System.err.println("group_name: " + group_name + ", remote_filename: " + remote_filename);
            System.err.println(storageClient.get_file_info(group_name, remote_filename));

            ServerInfo[] servers = trackerClient.getFetchStorages(trackerServer, group_name, remote_filename);
            if (servers == null){
                System.err.println("get storage servers fail, error code: " + trackerClient.getErrorCode());
            } else {
                System.err.println("storage servers count: " + servers.length);
                for (int k = 0; k < servers.length; k++){
                    System.err.println(k + 1 + ". " + servers[k].getIpAddr() + ":" + servers[k].getPort());
                }
                System.err.println("");
            }
        }
        @Test
        public void TestGet() throws Exception {
            String classPath = new File(MyTest.class.getResource("/").getFile()).getCanonicalPath();
            String configFilePath = classPath + File.separator + "fdfs_client.conf";
            ClientGlobal.init(configFilePath);
            TrackerClient trackerClient = new TrackerClient();
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = null;
            StorageClient storageClient = new StorageClient(trackerServer, storageServer);

            String group_name = "g1";
            String remote_filename = "M00/65/2B/wKgaD1P661CAVogmAAEGHjXvk2k564.jpg";
            FileInfo fi = storageClient.get_file_info(group_name, remote_filename);
            String sourceIpAddr = fi.getSourceIpAddr();
            long size = fi.getFileSize();
            System.out.println("ip:" + sourceIpAddr + ",size:" + size);
        }
    @Test
    //@Transactional(propagation= Propagation.REQUIRES_NEW,noRollbackFor=Exception.class)//,rollbackFor = Exception.class
    public void test(){
       //String list=restTemplate.getForObject("http://localhost:8080/common/country/cn", String.class);
       //System.out.print(list);
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
    @Test
    //@Transactional(propagation= Propagation.REQUIRES_NEW,noRollbackFor=Exception.class)//,rollbackFor = Exception.class
    public void testDao(){
        List<TCountry> list=(List<TCountry> )hibernateTemplate.find("from TCountry t where countryCode=? or countryCode=?","CN","US");
        System.out.print(list);
    }
    @Test
    public void testMongoDao(){
        Date date=new Date();
        date.setTime(1411465057427L);
        String[] pattern = new String[]{"yyyy-MM","yyyyMM","yyyy/MM",
                "yyyyMMdd","yyyy-MM-dd","yyyy/MM/dd",
                "yyyyMMddHHmmss",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy-MM-dd HH:mm",
                "yyyy/MM/dd HH:mm:ss"};
        Date sDate=new Date();
        Date eDate=new Date();
        try{
            sDate= DateUtils.parseDate("2014-09-21", pattern);
            eDate= DateUtils.parseDate("2014-09-24", pattern);
            System.out.println("Init:"+date+",time:"+date.getTime());
            System.out.println("Usee:"+sDate+",time:"+sDate.getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        //List<ApiLog> list=mongoTemplate.find(new Query(Criteria.where("createDate").gte(sDate)),ApiLog.class);
        List<ApiLog> list=mongoTemplate.find(new Query(Criteria.where("createDate").exists(true).andOperator(Criteria.where("createDate").gte(sDate),Criteria.where("createDate").lte(eDate))),ApiLog.class);
        //List<ApiLog> list=mongoTemplate.findAll(ApiLog.class);
        System.out.println("size:" + list.size());
        System.out.println(AppKey.class.getSimpleName());
/*
        for(ApiLog apiLog:list){
            System.out.println("size:"+ JsonTool.getJsonString(apiLog));
        }
*/
    }

    public static void main(String[] args) {    	
    	//System.out.println(URLEncoder.encode("http://localhost:8080/login.hyml"));
    	//System.out.println(URLDecoder.decode("%7B%22object%22%3A%22https%3A%2F%2Fdevelopers.facebook.com%2Fdocs%2F%22%7D"));
		//System.out.println(MessageFormat.format("How are you,{0}","gary"));
        System.getProperties().put("proxySet", "true");        
        System.getProperties().put("proxyHost", "127.0.0.1");
        System.getProperties().put("proxyPort", "8580");
        String path=System.getProperty("user.dir");
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
        //FileSystemXmlApplicationContext cxf=new FileSystemXmlApplicationContext(path+"/WebContent/WEB-INF/spring/spring.xml");
        //StateDao stateDao=(StateDao)cxf.getBean("StateDao");
        //String aa=cxf.getMessage("data.empty",null,null);
        //System.out.println(aa);
        //MemCachedClient memcachedClient=(MemCachedClient)cxf.getBean("memcachedClient");
        //System.out.println(memcachedClient.get("MGJmNmZiMWE3OTgxYWQ5MWYyZmNkYzExMTIwZWMxOTBjZDE2ZjQyYjcwYmFjMjZmYzA0MDJjODQ1YjMxNzhmNDY0OWJjZDQxMWY5MmRiM2E4YjRmMjc3MTU4YTQ1MjU0"));
//        org.springframework.http.converter.json.MappingJackson2HttpMessageConverter aa;
//        aa.setObjectMapper();


    }
}

