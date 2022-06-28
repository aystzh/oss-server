package aystzh.github.com.oss.entities;

import aystzh.github.com.jpa.common.bean.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by zhanghuan on 2022/6/28.
 */
@Getter
@Setter
@Entity
@Table(name = "oss_file_info")
public class OssFileInfoEntity extends BaseEntity {

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "store_path")
    private String storePath;

    private String url;

    @Column(name = "user_id")
    private String userId;

    private String type;

    @Column(name = "byte_str")
    private String byteStr;

    private Integer len;

    @Column(name = "from_ip")
    private String fromIp;

}
