package com.liferay.devhacks.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link NoteService}.
 *
 * @author James Falkner
 * @see NoteService
 * @generated
 */
public class NoteServiceWrapper implements NoteService,
    ServiceWrapper<NoteService> {
    private NoteService _noteService;

    public NoteServiceWrapper(NoteService noteService) {
        _noteService = noteService;
    }

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    @Override
    public java.lang.String getBeanIdentifier() {
        return _noteService.getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    @Override
    public void setBeanIdentifier(java.lang.String beanIdentifier) {
        _noteService.setBeanIdentifier(beanIdentifier);
    }

    @Override
    public java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return _noteService.invokeMethod(name, parameterTypes, arguments);
    }

    @Override
    public java.lang.String echo(java.lang.String name, java.lang.String note,
        java.lang.String signature)
        throws com.liferay.portal.kernel.exception.PortalException {
        return _noteService.echo(name, note, signature);
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
     */
    public NoteService getWrappedNoteService() {
        return _noteService;
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
     */
    public void setWrappedNoteService(NoteService noteService) {
        _noteService = noteService;
    }

    @Override
    public NoteService getWrappedService() {
        return _noteService;
    }

    @Override
    public void setWrappedService(NoteService noteService) {
        _noteService = noteService;
    }
}
