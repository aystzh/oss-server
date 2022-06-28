

package aystzh.github.com.oss.response;


public class FileResponse {
    /***
     * id
     */
    private String id;
    /***
     * 在线url地址
     */
    private String url;
    /**
     * 存储路径名称
     */
    private String store;

    public FileResponse(String id, String url, String store) {
        this.id = id;
        this.url = url;
        this.store = store;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}
