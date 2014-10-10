package com.qpp.service.common;

import com.qpp.action.BaseAction;
import com.qpp.dao.BaseDao;
import com.qpp.model.BaseReturn;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.List;

/**
 * Created by garyhui on 14-9-18.
 */
@Service
public class BaseResourceService{
    @Autowired
    private BaseDao baseDao;

    public BaseReturn getAll(Class priClass) {
        List list = baseDao.getAll(priClass);
        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @Cacheable(value="commonData")
    public BaseReturn getAllWithCache(Class priClass){
        return getAll(priClass);
    }
    public BaseReturn getAll(Class priClass,int from,int size){
        List list=baseDao.getAllByPage(priClass, from, size);
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setData(list);
        return baseReturn;
    }
    @Cacheable(value="commonData")
    public BaseReturn getAllCache(Class priClass,int from,int size){
        return getAll(priClass,from,size);
    }

    public BaseReturn getById(Class priClass,Serializable code,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        if (code==null){
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.inValid", null));
            return baseReturn;
        }
        Object resource=baseDao.getById(code, priClass);
        baseReturn.setData(resource);
        return baseReturn;
    }
    public BaseReturn getByQuery(String query,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        List resource=baseDao.getsByQuery(query);
        baseReturn.setData(resource);
        return baseReturn;
    }

    @Cacheable(value="commonData",key="#code")
    public BaseReturn getByIdWithCache(Class priClass,Serializable code,HttpServletRequest request){
        return getById(priClass,code,request);
    }
    @Cacheable(value="commonData",key="#key")
    public BaseReturn getByIdWithCacheKey(Class priClass,Serializable code,HttpServletRequest request,String key){
        return getById(priClass,code,request);
    }
    public BaseReturn deleteById(Class priClass,Serializable code,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        if (code==null){
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.inValid", null));
            return baseReturn;
        }
        Object resource=baseDao.getById(code,priClass);
        if (resource==null){
            baseReturn.setResult(BaseReturn.Err_data_empty);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.empty", null));
        }else{
            baseDao.delete(resource);
            baseReturn.setData(BaseAction.getMessageStatic(request, "common.success", null));
        }
        return baseReturn;
    }
    public BaseReturn updateById(Class priClass,Serializable code,Object obj,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        if (code==null){
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.inValid", null));
            return baseReturn;
        }
        Object resource=baseDao.getById(code,priClass);
        if (resource==null){
            baseReturn.setResult(BaseReturn.Err_data_empty);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.empty", null));
        }else{
            baseDao.update(obj);
            baseReturn.setData(BaseAction.getMessageStatic(request, "common.success", null));
        }
        return baseReturn;
    }

    public BaseReturn update(Object obj,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        baseDao.update(obj);
        baseReturn.setData(BaseAction.getMessageStatic(request, "common.success", null));
        return baseReturn;
    }
    public BaseReturn save(Object obj,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        baseDao.save(obj);
        baseReturn.setData(obj);
        return baseReturn;
    }

    public BaseReturn saveById(Class priClass,Serializable code,Object obj,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        if (code==null){
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.inValid", null));
            return baseReturn;
        }
        Object resource=baseDao.getById(code,priClass);
        if (resource!=null){
            baseReturn.setResult(BaseReturn.Err_data_duplicate);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.duplicate", null));
        }else{
            baseDao.save(obj);
            baseReturn.setData(obj);
        }
        return baseReturn;
    }
    public BaseReturn saveOrUpdate(Class priClass,Serializable code,Object obj,HttpServletRequest request){
        BaseReturn baseReturn=new BaseReturn();
        if (code==null){
            baseReturn.setResult(BaseReturn.Err_data_inValid);
            baseReturn.setErrMessage(BaseAction.getMessageStatic(request, "data.inValid", null));
            return baseReturn;
        }
        Object resource=baseDao.getById(code,priClass);
        if (resource!=null){
            baseDao.update(obj);
        }else{
            baseDao.save(obj);
        }
        baseReturn.setData(obj);
        return baseReturn;
    }

    public BaseReturn getFormatError(BindingResult result){
        List<FieldError> list=result.getFieldErrors();
        String error=null;
        for(FieldError fieldError:list){
            if (StringUtils.isEmpty(error))
                error=MessageFormat.format(fieldError.getDefaultMessage(),fieldError.getField(),fieldError.getRejectedValue());
            else
                error=error+","+MessageFormat.format(fieldError.getDefaultMessage(),fieldError.getField(),fieldError.getRejectedValue());
        }
        BaseReturn baseReturn=new BaseReturn();
        baseReturn.setResult(BaseReturn.Err_data_inValid);
        baseReturn.setErrMessage(error);
        return baseReturn;
    }

}
