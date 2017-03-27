package com.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pgwt on 2017/3/27.
 */
@Aspect
public class DataSourceAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    @Around("@annotation(dataSourceChange)")
    public Object doAround(ProceedingJoinPoint pjp, DataSourceChange dataSourceChange) throws Exception {
        Object retVal = null;
        boolean selectedDataSource = false;
        try {
            if (null != dataSourceChange) {
                selectedDataSource = true;
                if (dataSourceChange.slave()) {
                    DynamicDataSource.useSlave();
                } else {
                    DynamicDataSource.useMaster();
                }
            }
            retVal = pjp.proceed();
        } catch (Throwable e) {
            LOGGER.warn("数据源切换错误", e);
            throw new Exception("数据源切换错误", e);
        } finally {
            if (selectedDataSource) {
                DynamicDataSource.reset();
            }
        }
        return retVal;
    }

}
