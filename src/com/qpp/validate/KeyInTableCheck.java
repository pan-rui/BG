package com.qpp.validate;

import com.qpp.dao.BaseDao;
import com.qpp.listener.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.css.sac.ElementSelector;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

/**
 * Created by SZ_it123 on 2014/9/28.
 */
public class KeyInTableCheck implements ConstraintValidator<KeyInTable, Serializable> {
    @Autowired
    private BaseDao baseDao;
    private Class value;
    private String column;

    @Override
    public void initialize(KeyInTable keyInTable) {
        value=keyInTable.value();
        column=keyInTable.column();
    }

    @Override
    public boolean isValid(Serializable serializable, ConstraintValidatorContext constraintValidatorContext) {
        if (serializable==null) return false;
        Object obj;
        if (StringUtils.isEmpty(column))
            obj=baseDao.getById(serializable,value);
        else
            if (serializable instanceof Number)
                obj=baseDao.getByQuery("from "+value.getSimpleName()+" where "+column+"="+serializable);
            else
                obj=baseDao.getByQuery("from "+value.getSimpleName()+" where "+column+"='"+serializable+"'");
        if (obj!=null)
            return true;
        else
            return false;
    }
}
