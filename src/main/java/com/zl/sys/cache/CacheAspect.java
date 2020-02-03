package com.zl.sys.cache;

import com.zl.sys.domain.Dept;
import com.zl.sys.vo.DeptVo;
import org.apache.shiro.crypto.hash.Hash;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    private static final String CACHE_DEPT_PREFIX = "dept:";
    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = new HashMap<>();

    //声明切面表达式
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.zl.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.zl.sys.service.impl.DeptServiceImpl.getOne(..))";
    private static final String POINTCUT_DEPT_DELETE = "execution(* com.zl.sys.service.impl.DeptServiceImpl.removeById(..))";


    /**
     * 查询切入
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object result1 = CACHE_CONTAINER.get(CACHE_DEPT_PREFIX + object);
        if (result1 != null) {
            return result1;
        } else {
            Dept result2 = (Dept) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_PREFIX + result2.getId(), result2);
            return result2;

        }
    }

    /**
     * 更新切入
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        DeptVo deptVo = (DeptVo) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();

        if (isSuccess) {
            Dept dept = (Dept) CACHE_CONTAINER.get(CACHE_DEPT_PREFIX + deptVo.getId());
            if (null == dept) {
                dept = new Dept();
                BeanUtils.copyProperties(deptVo, dept);
                CACHE_CONTAINER.put(CACHE_DEPT_PREFIX + deptVo.getId(), dept);
            }
        }

        return isSuccess;
    }


    /**
     * 删除切入
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();

        if (isSuccess) {
           //删除缓存
            CACHE_CONTAINER.remove(CACHE_DEPT_PREFIX+id);
        }
        return isSuccess;
    }
}
