package com.zl.sys.cache;

import com.zl.sys.domain.Dept;
import com.zl.sys.domain.User;
import com.zl.sys.vo.DeptVo;
import com.zl.sys.vo.UserVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

    private Log log = LogFactory.getLog(CacheAspect.class);

    private static final String CACHE_DEPT_PREFIX = "dept:";
    private static final String CACHE_USER_PREFIX = "user:";
    //声明一个缓存容器
    private Map<String, Object> CACHE_CONTAINER = new HashMap<>();

    //声明切面表达式--部门
    private static final String POINTCUT_DEPT_ADD = "execution(* com.zl.sys.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.zl.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.zl.sys.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_DELETE = "execution(* com.zl.sys.service.impl.DeptServiceImpl.removeById(..))";

    //声明切面表达式--用户
    private static final String POINTCUT_USER_ADD = "execution(* com.zl.sys.service.impl.UserServiceImpl.save(..))";
    private static final String POINTCUT_USER_UPDATE = "execution(* com.zl.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_GET = "execution(* com.zl.sys.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETE = "execution(* com.zl.sys.service.impl.UserServiceImpl.removeById(..))";


    /**
     * 部门查询切入
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer deptId = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object result1 = CACHE_CONTAINER.get(CACHE_DEPT_PREFIX + deptId);
        if (result1 != null) {
            log.info("已从缓存中获取部门数据"+CACHE_DEPT_PREFIX + deptId);
            return result1;
        } else {
            log.info("未从缓存中获取部门数据，查询数据库并放入缓存"+deptId);
            Dept result2 = (Dept) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_PREFIX + result2.getId(), result2);
            return result2;

        }
    }

    /**
     * 部门添加切入
     */
    @Around(value = POINTCUT_DEPT_ADD)
    public Object cacheDeptAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Dept dept = (Dept) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            CACHE_CONTAINER.put(CACHE_DEPT_PREFIX + dept.getId(), dept);
        }

        return isSuccess;
    }

    /**
     * 部门更新切入
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
            }
            BeanUtils.copyProperties(deptVo, dept);
            log.info("部门对象缓存已更新" + CACHE_DEPT_PREFIX + deptVo.getId());
            CACHE_CONTAINER.put(CACHE_DEPT_PREFIX + deptVo.getId(), dept);
        }

        return isSuccess;
    }


    /**
     * 部门删除切入
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer deptId = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();

        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_DEPT_PREFIX + deptId);
            log.info("部门对象从缓存删除" + CACHE_DEPT_PREFIX + deptId);
        }
        return isSuccess;
    }


    /**
     * 用户查询切入
     */
    @Around(value = POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer userId = (Integer) joinPoint.getArgs()[0];
        //从缓存里面取
        Object result1 = CACHE_CONTAINER.get(CACHE_USER_PREFIX + userId);
        if (result1 != null) {
            log.info("已从缓存中获取用户数据"+CACHE_USER_PREFIX + userId);
            return result1;
        } else {
            log.info("未从缓存中获取用户数据，查询数据库并放入缓存"+CACHE_USER_PREFIX + userId);
            User result2 = (User) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_USER_PREFIX + result2.getId(), result2);
            return result2;

        }
    }

    /**
     * 用户添加切入
     */
    @Around(value = POINTCUT_USER_ADD)
    public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User user = (User) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            CACHE_CONTAINER.put(CACHE_USER_PREFIX + user.getId(), user);
        }

        return isSuccess;
    }

    /**
     * 用户更新切入
     */
    @Around(value = POINTCUT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        User user1 = (User) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();

        if (isSuccess) {
            User user = (User) CACHE_CONTAINER.get(CACHE_USER_PREFIX + user1.getId());
            if (null == user) {
                user = new User();
            }
            BeanUtils.copyProperties(user1, user);
            log.info("用户对象缓存已更新" + CACHE_USER_PREFIX + user1.getId());
            CACHE_CONTAINER.put(CACHE_USER_PREFIX + user1.getId(), user);
        }

        return isSuccess;
    }


    /**
     * 用户删除切入
     */
    @Around(value = POINTCUT_USER_DELETE)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        //取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();

        if (isSuccess) {
            //删除缓存
            CACHE_CONTAINER.remove(CACHE_USER_PREFIX + id);
            log.info("用户对象从缓存删除" + CACHE_USER_PREFIX + id);
        }
        return isSuccess;
    }
}
