package com.taotao.common.pojo;
import lombok.Data;
import java.io.Serializable;
/**
 * @author chenlin
 */
@Data
public class SearchItem implements Serializable {
    private String id;
    private String title;
    private String sellPoint;
    private long price;

    public String[] getImages() {
            if (image !=null && !"".equals(image)){
                String[] images = image.split(",");
                return images;
            }
            return null;
    }

    private String image;
    private String itemCatName;
    private String itemDesc;

}
