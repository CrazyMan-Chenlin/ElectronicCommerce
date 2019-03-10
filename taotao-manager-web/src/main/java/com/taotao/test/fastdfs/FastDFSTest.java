package com.taotao.test.fastdfs;

import com.taotao.managerweb.util.FastDFSClient;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @author chenlin
 */
public class FastDFSTest {
    @Test
    public void testFileUpload() throws Exception {
        String path = FastDFSTest.class.getResource("/resource/client.conf").getPath();
        //1.加载配置文件,配置文件内容就是tracker服务地址
        ClientGlobal.init(path);
        //2.创建一个TrackerClient对象。直接new一个
        TrackerClient trackerClient = new TrackerClient();
        // 3.使用TrackerClient对象创建连接，获得一个TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        // 4.创建一个StorageServer的引用，值为null
        StorageServer storageServer = null;
        // 5.创建一个StorageClient对象，需要两个参数TrackerServer对象、StorageServer的引用
        StorageClient storageClient = new StorageClient(trackerServer, storageServer);
        // 6、使用StorageClient对象上传图片。
        //扩展名不带“.”
        String[] strings = storageClient.upload_file("C:\\Users\\chenlin\\Pictures\\Camera Roll/WIN_20190123_12_22_25_Pro.jpg", "jpg", null);
        // 7、返回数组。包含组名和图片的路径。
        for (String string : strings) {
            System.out.println(string);
        }
    }
    @Test
    public void testFastDfsClient() throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("client.conf");
        String file = fastDFSClient.uploadFile("C:\\Users\\chenlin\\Pictures\\Saved Pictures/kobe.jpg");
        System.out.println(file);
    }
}
