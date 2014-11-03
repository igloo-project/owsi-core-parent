package fr.openwide.core.basicapp.core.business.user.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.openwide.core.basicapp.core.business.user.dao.IUserDao;
import fr.openwide.core.basicapp.core.business.user.model.User;
import fr.openwide.core.basicapp.core.business.user.model.UserSearchParameters;
import fr.openwide.core.basicapp.core.business.user.model.atomic.UserPasswordRecoveryRequestInitiator;
import fr.openwide.core.basicapp.core.business.user.model.atomic.UserPasswordRecoveryRequestType;
import fr.openwide.core.basicapp.core.business.user.model.embeddable.UserPasswordRecoveryRequest;
import fr.openwide.core.basicapp.core.config.application.BasicApplicationConfigurer;
import fr.openwide.core.jpa.exception.SecurityServiceException;
import fr.openwide.core.jpa.exception.ServiceException;
import fr.openwide.core.jpa.security.business.person.model.GenericUser_;
import fr.openwide.core.jpa.security.business.person.service.GenericSimpleUserServiceImpl;
import fr.openwide.core.spring.util.StringUtils;

@Service("personService")
public class UserServiceImpl extends GenericSimpleUserServiceImpl<User> implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private BasicApplicationConfigurer configurer;
	
	@Autowired
	public UserServiceImpl(IUserDao userDao) {
		super(userDao);
		this.userDao = userDao;
	}

	@Override
	public List<User> listByUserName(String userName) {
		return listByField(GenericUser_.userName, userName);
	}
	
	@Override
	public <U extends User> List<U> search(Class<U> clazz, UserSearchParameters searchParameters, Integer limit, Integer offset) throws ParseException {
		return userDao.search(clazz, searchParameters, limit, offset);
	}
	
	@Override
	public <U extends User> int count(Class<U> clazz, UserSearchParameters searchParameters) throws ParseException {
		return userDao.count(clazz, searchParameters);
	}
	
	@Override
	public User getByEmailCaseInsensitive(String email) {
		if (!StringUtils.hasText(StringUtils.lowerCase(email))) {
			return null;
		}
		return userDao.getByEmailCaseInsensitive(email);
	}
	
	@Override
	public UserPasswordRecoveryRequest initiatePasswordRecoveryRequest(User user, UserPasswordRecoveryRequestType type,
			UserPasswordRecoveryRequestInitiator initiator) throws ServiceException, SecurityServiceException {
		UserPasswordRecoveryRequest passwordRecoveryRequest = new UserPasswordRecoveryRequest();
		
		Date now = new Date();
		
		passwordRecoveryRequest.setToken(getPasswordChangeRequestToken(user, now));
		passwordRecoveryRequest.setCreationDate(now);
		passwordRecoveryRequest.setType(type);
		passwordRecoveryRequest.setInitiator(initiator);
		
		user.setPasswordRecoveryRequest(passwordRecoveryRequest);
		update(user);
		
		// TODO FLA notification
		
		return passwordRecoveryRequest;
	}
	
	private String getPasswordChangeRequestToken(User user, Date date) {
		return DigestUtils.sha256Hex(String.format("%1$s - %2$s - %3$s", user.getId(),
				configurer.getSecurityPasswordRecoveryRequestTokenSalt(), date));
	}
}
