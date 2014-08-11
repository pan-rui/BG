package com.qpp.util;

import java.io.File;
import java.io.FileInputStream;

import org.apache.log4j.Logger;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.FileInfo;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

public class FastdfsUtils {

	private static final Logger logger = Logger.getLogger(FastdfsUtils.class);

	static {
		String classPath = "";
		try {
			classPath = new File(FastdfsUtils.class.getResource("/").getFile())
					.getCanonicalPath();
			String configFilePath = classPath + File.separator
					+ "fdfs_client.conf";
			ClientGlobal.init(configFilePath);
		} catch (Exception e) {
			logger.error("init fastDfs fails", e);
		}
	}

	/**
	 * upload file
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile(String fileName) throws Exception {
		String fileExtName = "";
		if (fileName.contains(".")) {
			fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			logger.warn("Fail to upload file, because the format of filename is illegal.");
			return null;
		}
		File file = new File(fileName);
		TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);
		NameValuePair[] metaList = new NameValuePair[3];
		metaList[0] = new NameValuePair("fileName", fileName);
		metaList[1] = new NameValuePair("fileExtName", fileExtName);
		metaList[2] = new NameValuePair("fileLength", String.valueOf(file
				.length()));
		FileInputStream fis = new FileInputStream(file);
		byte[] file_buff = null;
		if (fis != null) {
			int len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}
		fis.close();
		String fileId = client.upload_file1(fileName, fileExtName, metaList);
		NameValuePair[] metaList1 = new NameValuePair[3];
		metaList1[0] = new NameValuePair("width", "50");
		metaList1[1] = new NameValuePair("heigth", "50");
		metaList1[2] = new NameValuePair("bgcolor","＃000000");
		storageServer = tracker.getFetchStorage1(trackerServer, fileId);
		logger.info("storageServer:" + storageServer);
//		if (storageServer != null) {
//			InputStream in = storageServer.getSocket().getInputStream();
//			byte[] header = new byte[10];
//			in.read(header);
//			logger.info(header[9]);
//		}
		StorageClient1 client2 = new StorageClient1(trackerServer, storageServer);
		String fileId2 = client2.upload_file1(fileId, "50×50", "E:\\workspace\\PdfTest\\res\\araraNegative_50.jpg", fileExtName, metaList1);
		//String fileId2 = client.upload_file1(fileId, "_50×50", file_buff, fileExtName, metaList1);
		logger.info("fileID:" + fileId2);
//		String file3 = ProtoCommon.genSlaveFilename(fileId, "_50×50", fileExtName);
//		logger.info("fileID:" + file3);
		trackerServer.close();
		//uploadSlaveFile(fileId, "_50×50", "E:\\workspace\\PdfTest\\res\\araraNegative_50.jpg");
		return fileId;
	}
	
	public static String uploadSlaveFile (String masterFileId, String prefixName, String slaveFilePath) throws Exception{
	    String slaveFileId = "";
	    String slaveFileExtName = "";
	    if (slaveFilePath.contains(".")) {
	        slaveFileExtName = slaveFilePath.substring(slaveFilePath.lastIndexOf(".") + 1);
	    } else {
	        logger.warn("Fail to upload file, because the format of filename is illegal.");
	        return slaveFileId;
	    }
	  
	    TrackerClient tracker = new TrackerClient();
		TrackerServer trackerServer = tracker.getConnection();
		StorageServer storageServer = null;
		StorageClient1 client = new StorageClient1(trackerServer, storageServer);
	    try {
	        slaveFileId = client.upload_file1(masterFileId, prefixName, slaveFilePath, slaveFileExtName, null);
	    } catch (Exception e) {
	        logger.warn("Upload file \"" + slaveFilePath + "\"fails");
	    }finally{
	        trackerServer.close();
	    }
	    logger.info("slaveFileId:" + slaveFileId);
	    return slaveFileId;
	}

	/**
	 * delete file
	 * 
	 * @param groupName
	 * @param remoteName
	 * @throws Exception
	 */
	public static void deleteFile(String filePath) throws Exception {
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		storageClient.delete_file1(filePath);
		trackerServer.close();
	}

	/**
	 * get file size
	 * @param groupName
	 * @param remoteName
	 * @return
	 * @throws Exception
	 */
	public static float getFileSize(String filePath) throws Exception {
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		FileInfo fi = storageClient.get_file_info1(filePath);
		float size = fi.getFileSize()/1024.0f;
		return size;
	}
}
