package com.liferay.devhacks.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Note}.
 * </p>
 *
 * @author James Falkner
 * @see Note
 * @generated
 */
public class NoteWrapper implements Note, ModelWrapper<Note> {
    private Note _note;

    public NoteWrapper(Note note) {
        _note = note;
    }

    @Override
    public Class<?> getModelClass() {
        return Note.class;
    }

    @Override
    public String getModelClassName() {
        return Note.class.getName();
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("name", getName());
        attributes.put("note", getNote());
        attributes.put("noteId", getNoteId());

        return attributes;
    }

    @Override
    public void setModelAttributes(Map<String, Object> attributes) {
        String name = (String) attributes.get("name");

        if (name != null) {
            setName(name);
        }

        String note = (String) attributes.get("note");

        if (note != null) {
            setNote(note);
        }

        Long noteId = (Long) attributes.get("noteId");

        if (noteId != null) {
            setNoteId(noteId);
        }
    }

    /**
    * Returns the primary key of this note.
    *
    * @return the primary key of this note
    */
    @Override
    public long getPrimaryKey() {
        return _note.getPrimaryKey();
    }

    /**
    * Sets the primary key of this note.
    *
    * @param primaryKey the primary key of this note
    */
    @Override
    public void setPrimaryKey(long primaryKey) {
        _note.setPrimaryKey(primaryKey);
    }

    /**
    * Returns the name of this note.
    *
    * @return the name of this note
    */
    @Override
    public java.lang.String getName() {
        return _note.getName();
    }

    /**
    * Sets the name of this note.
    *
    * @param name the name of this note
    */
    @Override
    public void setName(java.lang.String name) {
        _note.setName(name);
    }

    /**
    * Returns the note of this note.
    *
    * @return the note of this note
    */
    @Override
    public java.lang.String getNote() {
        return _note.getNote();
    }

    /**
    * Sets the note of this note.
    *
    * @param note the note of this note
    */
    @Override
    public void setNote(java.lang.String note) {
        _note.setNote(note);
    }

    /**
    * Returns the note ID of this note.
    *
    * @return the note ID of this note
    */
    @Override
    public long getNoteId() {
        return _note.getNoteId();
    }

    /**
    * Sets the note ID of this note.
    *
    * @param noteId the note ID of this note
    */
    @Override
    public void setNoteId(long noteId) {
        _note.setNoteId(noteId);
    }

    @Override
    public boolean isNew() {
        return _note.isNew();
    }

    @Override
    public void setNew(boolean n) {
        _note.setNew(n);
    }

    @Override
    public boolean isCachedModel() {
        return _note.isCachedModel();
    }

    @Override
    public void setCachedModel(boolean cachedModel) {
        _note.setCachedModel(cachedModel);
    }

    @Override
    public boolean isEscapedModel() {
        return _note.isEscapedModel();
    }

    @Override
    public java.io.Serializable getPrimaryKeyObj() {
        return _note.getPrimaryKeyObj();
    }

    @Override
    public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
        _note.setPrimaryKeyObj(primaryKeyObj);
    }

    @Override
    public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
        return _note.getExpandoBridge();
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.model.BaseModel<?> baseModel) {
        _note.setExpandoBridgeAttributes(baseModel);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
        _note.setExpandoBridgeAttributes(expandoBridge);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.service.ServiceContext serviceContext) {
        _note.setExpandoBridgeAttributes(serviceContext);
    }

    @Override
    public java.lang.Object clone() {
        return new NoteWrapper((Note) _note.clone());
    }

    @Override
    public int compareTo(com.liferay.devhacks.model.Note note) {
        return _note.compareTo(note);
    }

    @Override
    public int hashCode() {
        return _note.hashCode();
    }

    @Override
    public com.liferay.portal.model.CacheModel<com.liferay.devhacks.model.Note> toCacheModel() {
        return _note.toCacheModel();
    }

    @Override
    public com.liferay.devhacks.model.Note toEscapedModel() {
        return new NoteWrapper(_note.toEscapedModel());
    }

    @Override
    public com.liferay.devhacks.model.Note toUnescapedModel() {
        return new NoteWrapper(_note.toUnescapedModel());
    }

    @Override
    public java.lang.String toString() {
        return _note.toString();
    }

    @Override
    public java.lang.String toXmlString() {
        return _note.toXmlString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof NoteWrapper)) {
            return false;
        }

        NoteWrapper noteWrapper = (NoteWrapper) obj;

        if (Validator.equals(_note, noteWrapper._note)) {
            return true;
        }

        return false;
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
     */
    public Note getWrappedNote() {
        return _note;
    }

    @Override
    public Note getWrappedModel() {
        return _note;
    }

    @Override
    public void resetOriginalValues() {
        _note.resetOriginalValues();
    }
}
