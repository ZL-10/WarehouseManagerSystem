package com.zl.business.cache;

import com.zl.business.domain.Customer;
import com.zl.business.vo.CustomerVo;
import com.zl.sys.domain.Dept;
import com.zl.sys.vo.DeptVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@EnableAspectJAutoProxy
public class BusinessCacheAspect {

    private static final String CACHE_CUSTOMER_PREFIX = "customer:";
    private Log log = LogFactory.getLog(BusinessCacheAspect.class);

    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = new HashMap<>();

    //声明切面表达式--客户
    private static final String POINTCUT_CUSTOMER_ADD = "execution(* com.zl.business.service.impl.CustomerServiceImpl.save(..))";
    private static final String POINTCUT_CUSTOMER_UPDATE = "execution(* com.zl.business.service.impl.CustomerServiceImpl.updateById(..))";
    private static final String POINTCUT_CUSTOMER_GET = "execution(* com.zl.business.service.impl.CustomerServiceImpl.getById(..))";
    private static final String POINTCUT_CUSTOMER_DELETE = "execution(* com.zl.business.service.impl.CustomerServiceImpl.removeById(..))";
    private static final String POINTCUT_CUSTOMER_BATCH_DELETE = "execution(* com.zl.business.service.impl.CustomerServiceImpl.removeByIds(..))";

    /**
     * 客户查询切入
     */
    @Around(value = POINTCUT_CUSTOMER_GET)
    public Object cacheCustomerGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer cid = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object result1 = CACHE_CONTAINER.get(CACHE_CUSTOMER_PREFIX + cid);
        if (result1 != null) {
            log.info("已从缓存中获取客户数据"+CACHE_CUSTOMER_PREFIX + cid);
            return result1;
        } else {
            log.info("未从缓存中获取客户数据，查询数据库并放入缓存"+cid);
            Customer result2 = (Customer) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PREFIX + result2.getId(), result2);
            return result2;

        }
    }

    /**
     * 客户添加切入
     */
    @Around(value = POINTCUT_CUSTOMER_ADD)
    public Object cacheCustomerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Customer customer = (Customer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PREFIX + customer.getId(), customer);
        }

        return isSuccess;
    }

    /**
     * 客户更新切入
     */
    @Around(value = POINTCUT_CUSTOMER_UPDATE)
    public Object cacheCustomerUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        CustomerVo customerVo = (CustomerVo) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();

        if (isSuccess) {
            Customer dept = (Customer) CACHE_CONTAINER.get(CACHE_CUSTOMER_PREFIX + customerVo.getId());
            if (null == dept) {
                dept = new Customer();
            }
            BeanUtils.copyProperties(customerVo, dept);
            log.info("客户对象缓存已更新" + CACHE_CUSTOMER_PREFIX + customerVo.getId());
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PREFIX + customerVo.getId(), dept);
        }

        return isSuccess;
    }


    /**
     * 客户删除切入
     */
    @Around(value = POINTCUT_CUSTOMER_DELETE)
    public Object cacheCustomerDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer cid = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();

        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_CUSTOMER_PREFIX + cid);
            log.info("客户对象从缓存删除" + CACHE_CUSTOMER_PREFIX + cid);
        }
        return isSuccess;
    }

    /**
     * 客户批量删除切入
     */
    @Around(value = POINTCUT_CUSTOMER_BATCH_DELETE)
    public Object cacheCustomerBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            for (Serializable id : idList) {
                // 删除缓存
                CACHE_CONTAINER.remove(CACHE_CUSTOMER_PREFIX + id);
                log.info("客户对象缓存已删除" + CACHE_CUSTOMER_PREFIX + id);
            }
        }
        return isSuccess;

    }

}
