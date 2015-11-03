package com.liferay.devhacks.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableService;

/**
 * Provides the remote service utility for Note. This utility wraps
 * {@link com.liferay.devhacks.service.impl.NoteServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author James Falkner
 * @see NoteService
 * @see com.liferay.devhacks.service.base.NoteServiceBaseImpl
 * @see com.liferay.devhacks.service.impl.NoteServiceImpl
 * @generated
 */
public class NoteServiceUtil {
    private static NoteService _service;

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify this class directly. Add custom service methods to {@link com.liferay.devhacks.service.impl.NoteServiceImpl} and rerun ServiceBuilder to regenerate this class.
     */

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    public static java.lang.String getBeanIdentifier() {
        return getService().getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    public static void setBeanIdentifier(java.lang.String beanIdentifier) {
        getService().setBeanIdentifier(beanIdentifier);
    }

    public static java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return getService().invokeMethod(name, parameterTypes, arguments);
    }

    public static java.lang.String echo(java.lang.String name,
        java.lang.String note, java.lang.String signature)
        throws com.liferay.portal.kernel.exception.PortalException {
        return getService().echo(name, note, signature);
    }

    public static void clearService() {
        _service = null;
    }

    public static NoteService getService() {
        if (_service == null) {
            InvokableService invokableService = (InvokableService) PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
                    NoteService.class.getName());

            if (invokableService instanceof NoteService) {
                _service = (NoteService) invokableService;
            } else {
                _service = new NoteServiceClp(invokableService);
            }

            ReferenceRegistry.registerReference(NoteServiceUtil.class,
                "_service");
        }

        return _service;
    }

    /**
     * @deprecated As of 6.2.0
     */
    public void setService(NoteService service) {
    }
}
