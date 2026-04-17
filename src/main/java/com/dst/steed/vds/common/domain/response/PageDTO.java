package com.dst.steed.vds.common.domain.response;

import lombok.Data;
import java.util.List;

@Data
public class PageDTO<T> {
    private List<T> list;
    private long total;
    private long size;
    private long current;
    private long totalPage;

    public static <T> PageDTO<T> of(List<T> list, long total, long size, long current) {
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setList(list);
        pageDTO.setTotal(total);
        pageDTO.setSize(size);
        pageDTO.setCurrent(current);
        if (size > 0) {
            pageDTO.setTotalPage((total + size - 1) / size);
        }
        return pageDTO;
    }
}
