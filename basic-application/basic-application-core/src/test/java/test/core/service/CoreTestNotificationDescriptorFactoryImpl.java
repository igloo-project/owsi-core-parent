package test.core.service;

import org.springframework.stereotype.Service;

import org.iglooproject.basicapp.core.business.notification.service.EmptyNotificationContentDescriptorFactoryImpl;

/**
 * Implémentation bouche-trou, uniquement pour combler la dépendance.
 */
@Service("CoreTestNotificationDescriptorFactory")
public class CoreTestNotificationDescriptorFactoryImpl extends EmptyNotificationContentDescriptorFactoryImpl {

}
