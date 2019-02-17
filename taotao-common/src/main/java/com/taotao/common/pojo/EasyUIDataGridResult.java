package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenlin
 */
public class EasyUIDataGridResult implements Serializable {
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


    private Integer total;
    private List rows;

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
