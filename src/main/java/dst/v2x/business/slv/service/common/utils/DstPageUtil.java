package dst.v2x.business.slv.service.common.utils;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dst.steed.vds.common.domain.request.PageQuery;
import com.dst.steed.vds.common.domain.response.PageDTO;

import java.util.Collections;
import java.util.List;

public class DstPageUtil {
    public DstPageUtil() {
    }

    public static <T> Page<T> toPage(PageQuery pageQuery) {
        Page<T> page = new Page();
        pageQuery.format();
        page.setSearchCount(pageQuery.isNeedCount());
        page.setCurrent(pageQuery.getPageNum());
        page.setSize(pageQuery.getPageSize());
        return page;
    }

    public static <D> PageDTO<D> toPageDTO(IPage<D> page) {
        List<D> records = page.getRecords();
        if (CollUtil.isEmpty(records)) {
            return PageDTO.of(Collections.emptyList(), page.getTotal(), page.getSize(), page.getCurrent());
        }
        return PageDTO.of(records, page.getTotal(), page.getSize(), page.getCurrent());
    }
}
