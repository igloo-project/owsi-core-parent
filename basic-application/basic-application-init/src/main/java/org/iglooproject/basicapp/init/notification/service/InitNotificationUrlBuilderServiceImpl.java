package org.iglooproject.basicapp.init.notification.service;

import org.springframework.stereotype.Service;

import org.iglooproject.basicapp.core.business.notification.service.EmptyNotificationUrlBuilderServiceImpl;

/**
 * Implémentation bouche-trou, uniquement pour combler la dépendance.
 */
@Service("initNotificationUrlBuilderService")
public class InitNotificationUrlBuilderServiceImpl extends EmptyNotificationUrlBuilderServiceImpl {

}
