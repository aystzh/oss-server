

package aystzh.github.com.oss.response;


import java.io.Serializable;

public class StorageInfoResponse implements Serializable {
    /***
     * id
     */
    private String fileName;
    /***
     * 在线url地址
     */
    private String filePath;
    /**
     * 存储路径名称
     */
    private String pathDir;
    /**
     * 存储路径名称
     */
    private String bucketName;

    private String objectName;

    private String mongodbId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getPathDir() {
        return pathDir;
    }

    public void setPathDir(String pathDir) {
        this.pathDir = pathDir;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getMongodbId() {
        return mongodbId;
    }

    public void setMongodbId(String mongodbId) {
        this.mongodbId = mongodbId;
    }
}
