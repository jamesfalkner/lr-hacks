package com.liferay.devhacks.service.persistence;

import com.liferay.devhacks.model.Note;

import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the note service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author James Falkner
 * @see NotePersistenceImpl
 * @see NoteUtil
 * @generated
 */
public interface NotePersistence extends BasePersistence<Note> {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this interface directly. Always use {@link NoteUtil} to access the note persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
     */

    /**
    * Caches the note in the entity cache if it is enabled.
    *
    * @param note the note
    */
    public void cacheResult(com.liferay.devhacks.model.Note note);

    /**
    * Caches the notes in the entity cache if it is enabled.
    *
    * @param notes the notes
    */
    public void cacheResult(
        java.util.List<com.liferay.devhacks.model.Note> notes);

    /**
    * Creates a new note with the primary key. Does not add the note to the database.
    *
    * @param noteId the primary key for the new note
    * @return the new note
    */
    public com.liferay.devhacks.model.Note create(long noteId);

    /**
    * Removes the note with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param noteId the primary key of the note
    * @return the note that was removed
    * @throws com.liferay.devhacks.NoSuchNoteException if a note with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.liferay.devhacks.model.Note remove(long noteId)
        throws com.liferay.devhacks.NoSuchNoteException,
            com.liferay.portal.kernel.exception.SystemException;

    public com.liferay.devhacks.model.Note updateImpl(
        com.liferay.devhacks.model.Note note)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the note with the primary key or throws a {@link com.liferay.devhacks.NoSuchNoteException} if it could not be found.
    *
    * @param noteId the primary key of the note
    * @return the note
    * @throws com.liferay.devhacks.NoSuchNoteException if a note with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.liferay.devhacks.model.Note findByPrimaryKey(long noteId)
        throws com.liferay.devhacks.NoSuchNoteException,
            com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the note with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param noteId the primary key of the note
    * @return the note, or <code>null</code> if a note with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public com.liferay.devhacks.model.Note fetchByPrimaryKey(long noteId)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns all the notes.
    *
    * @return the notes
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.liferay.devhacks.model.Note> findAll()
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns a range of all the notes.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.devhacks.model.impl.NoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param start the lower bound of the range of notes
    * @param end the upper bound of the range of notes (not inclusive)
    * @return the range of notes
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.liferay.devhacks.model.Note> findAll(int start,
        int end) throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns an ordered range of all the notes.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.devhacks.model.impl.NoteModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param start the lower bound of the range of notes
    * @param end the upper bound of the range of notes (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of notes
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<com.liferay.devhacks.model.Note> findAll(int start,
        int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Removes all the notes from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of notes.
    *
    * @return the number of notes
    * @throws SystemException if a system exception occurred
    */
    public int countAll()
        throws com.liferay.portal.kernel.exception.SystemException;
}
