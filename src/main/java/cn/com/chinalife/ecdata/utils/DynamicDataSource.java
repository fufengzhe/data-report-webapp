package cn.com.chinalife.ecdata.utils;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * Created by xiexiangyu on 2018/3/7.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDbType();
    }
}
