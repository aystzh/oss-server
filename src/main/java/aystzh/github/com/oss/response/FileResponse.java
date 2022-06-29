

package aystzh.github.com.oss.response;


public class FileResponse {
    /***
     * id
     */
    private String name;
    /***
     * 在线url地址
     */
    private String url;
    /**
     * 存储路径名称
     */
    private String store;

    public FileResponse(String name, String url, String store) {
        this.name = name;
        this.url = url;
        this.store = store;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
