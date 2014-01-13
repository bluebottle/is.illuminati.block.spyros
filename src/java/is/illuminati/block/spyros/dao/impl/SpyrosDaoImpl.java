package is.illuminati.block.spyros.dao.impl;

import is.illuminati.block.spyros.dao.SpyrosDao;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.idega.core.persistence.impl.GenericDaoImpl;

@Repository("spyrosDao")
@Transactional(readOnly = true)
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class SpyrosDaoImpl extends GenericDaoImpl implements SpyrosDao {

}