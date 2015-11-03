package com.liferay.devhacks.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.devhacks.service.http.NoteServiceSoap}.
 *
 * @author James Falkner
 * @see com.liferay.devhacks.service.http.NoteServiceSoap
 * @generated
 */
public class NoteSoap implements Serializable {
    private String _name;
    private String _note;
    private long _noteId;

    public NoteSoap() {
    }

    public static NoteSoap toSoapModel(Note model) {
        NoteSoap soapModel = new NoteSoap();

        soapModel.setName(model.getName());
        soapModel.setNote(model.getNote());
        soapModel.setNoteId(model.getNoteId());

        return soapModel;
    }

    public static NoteSoap[] toSoapModels(Note[] models) {
        NoteSoap[] soapModels = new NoteSoap[models.length];

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModel(models[i]);
        }

        return soapModels;
    }

    public static NoteSoap[][] toSoapModels(Note[][] models) {
        NoteSoap[][] soapModels = null;

        if (models.length > 0) {
            soapModels = new NoteSoap[models.length][models[0].length];
        } else {
            soapModels = new NoteSoap[0][0];
        }

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModels(models[i]);
        }

        return soapModels;
    }

    public static NoteSoap[] toSoapModels(List<Note> models) {
        List<NoteSoap> soapModels = new ArrayList<NoteSoap>(models.size());

        for (Note model : models) {
            soapModels.add(toSoapModel(model));
        }

        return soapModels.toArray(new NoteSoap[soapModels.size()]);
    }

    public long getPrimaryKey() {
        return _noteId;
    }

    public void setPrimaryKey(long pk) {
        setNoteId(pk);
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getNote() {
        return _note;
    }

    public void setNote(String note) {
        _note = note;
    }

    public long getNoteId() {
        return _noteId;
    }

    public void setNoteId(long noteId) {
        _noteId = noteId;
    }
}
