package com.qpp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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
			classPath = new File(FastdfsUtils.class.getResource("/").getFile()).getCanonicalPath();
			String configFilePath = classPath + File.separator	+ "resources/fdfs_client.conf";
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
	public static String uploadFile(String fileName, InputStream inputstream) throws Exception {
		String fileExtName = "";
		if (fileName.contains(".")) {
			fileExtName = fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			logger.warn("Fail to upload file, because the format of filename is illegal.");
			return null;
		}
		FileInputStream fis = (FileInputStream) inputstream;
		byte[] file_buff = null;
        int len=0;
		if (fis != null) {
			len = fis.available();
			file_buff = new byte[len];
			fis.read(file_buff);
		}
		fis.close();
        NameValuePair[] metaList = new NameValuePair[3];
        metaList[0] = new NameValuePair("fileName", fileName);
        metaList[1] = new NameValuePair("fileExtName", fileExtName);
        metaList[2] = new NameValuePair("fileLength",String.valueOf(len));
		String fileId = uploadFileBybyte (file_buff, fileExtName, metaList);
		return fileId;
	}
	
	public static String uploadFileBybyte (byte[] buff, String picExp, NameValuePair[] metaList) throws Exception {
		TrackerServer trackerServer = initTrackerServer();
		StorageClient1 client = initStorageClient(trackerServer);
		String fileId = client.upload_file1(buff, picExp, metaList);
		if (fileId != null) {
			List<ImageThumbnail> list = ImageThumbnail.getList();
			for (int i = 0; i < list.size(); i++) {
				ImageThumbnail itn = list.get(i);
				String fileId2 = client.upload_file1(fileId, itn.getName(), ImageUtils.resize(buff, picExp, itn.getWidth(), itn.getHeight()), picExp, null);
				logger.info("Master:"+fileId+",Child:" + fileId2);
			}
		}
		trackerServer.close();
		return fileId;
	}

    /**
     *
     * @param filePath
     * @throws Exception
     */
	public static void deleteFile(String filePath) throws Exception {
		TrackerServer trackerServer = initTrackerServer();
		StorageClient1 storageClient = initStorageClient(trackerServer);
		storageClient.delete_file1(filePath);
		List<ImageThumbnail> list = ImageThumbnail.getList();
		for (int i = 0; i < list.size(); i++) {
			ImageThumbnail itn = list.get(i);
			String photoId = filePath.substring(0, filePath.lastIndexOf(".")) + itn.getName() + filePath.substring(filePath.lastIndexOf("."));
			storageClient.delete_file1(photoId);
		}
		trackerServer.close();
	}

	/**
	 * get file size
	 * @param filePath
	 * @return size
	 * @throws Exception
	 */
	public static float getFileSize(String filePath) throws Exception {
		TrackerServer trackerServer = initTrackerServer();
		StorageClient1 storageClient = initStorageClient(trackerServer);
		FileInfo fi = storageClient.get_file_info1(filePath);
		float size = fi.getFileSize()/1024.0f;
		trackerServer.close();
		return size;
	}
	
	public static TrackerServer initTrackerServer() throws Exception {
		TrackerClient trackerClient = new TrackerClient();
		TrackerServer trackerServer = trackerClient.getConnection();
		return trackerServer;
	}
	
	public static StorageClient1 initStorageClient (TrackerServer trackerServer) {
		StorageServer storageServer = null;
		StorageClient1 storageClient = new StorageClient1(trackerServer, storageServer);
		return storageClient;
	}

    public static void main(String[] args) {
        File file=new File("d:\\temp\\fastdfs.jpg");
        try{
            InputStream is=new FileInputStream(file);
            FastdfsUtils.uploadFile(file.getName(),is);
        }catch (Exception e){

        }
    }
}
