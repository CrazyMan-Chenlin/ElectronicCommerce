package com.taotao.common.pojo;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author chenlin
 */
@Data
public class SearchResult implements Serializable {

    private List<SearchItem> searchItemList;

    private long recordCount;

    private long pageCount;
}
