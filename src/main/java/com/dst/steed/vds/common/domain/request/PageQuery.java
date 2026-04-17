package com.dst.steed.vds.common.domain.request;

import lombok.Data;

@Data
public class PageQuery {
    private long pageNum = 1;
    private long pageSize = 10;
    private boolean needCount = true;

    public void format() {
        if (pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
    }

    public boolean isNeedCount() {
        return needCount;
    }

    public long getPageNum() {
        return pageNum;
    }

    public long getPageSize() {
        return pageSize;
    }
}
