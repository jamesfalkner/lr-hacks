package com.liferay.devhacks.model;

import com.liferay.devhacks.service.ClpSerializer;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;


public class NoteClp extends BaseModelImpl<Note> implements Note {
    private String _name;
    private String _note;
    private long _noteId;
    private BaseModel<?> _noteRemoteModel;
    private Class<?> _clpSerializerClass = com.liferay.devhacks.service.ClpSerializer.class;

    public NoteClp() {
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
    public long getPrimaryKey() {
        return _noteId;
    }

    @Override
    public void setPrimaryKey(long primaryKey) {
        setNoteId(primaryKey);
    }

    @Override
    public Serializable getPrimaryKeyObj() {
        return _noteId;
    }

    @Override
    public void setPrimaryKeyObj(Serializable primaryKeyObj) {
        setPrimaryKey(((Long) primaryKeyObj).longValue());
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

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public void setName(String name) {
        _name = name;

        if (_noteRemoteModel != null) {
            try {
                Class<?> clazz = _noteRemoteModel.getClass();

                Method method = clazz.getMethod("setName", String.class);

                method.invoke(_noteRemoteModel, name);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public String getNote() {
        return _note;
    }

    @Override
    public void setNote(String note) {
        _note = note;

        if (_noteRemoteModel != null) {
            try {
                Class<?> clazz = _noteRemoteModel.getClass();

                Method method = clazz.getMethod("setNote", String.class);

                method.invoke(_noteRemoteModel, note);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public long getNoteId() {
        return _noteId;
    }

    @Override
    public void setNoteId(long noteId) {
        _noteId = noteId;

        if (_noteRemoteModel != null) {
            try {
                Class<?> clazz = _noteRemoteModel.getClass();

                Method method = clazz.getMethod("setNoteId", long.class);

                method.invoke(_noteRemoteModel, noteId);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public BaseModel<?> getNoteRemoteModel() {
        return _noteRemoteModel;
    }

    public void setNoteRemoteModel(BaseModel<?> noteRemoteModel) {
        _noteRemoteModel = noteRemoteModel;
    }

    public Object invokeOnRemoteModel(String methodName,
        Class<?>[] parameterTypes, Object[] parameterValues)
        throws Exception {
        Object[] remoteParameterValues = new Object[parameterValues.length];

        for (int i = 0; i < parameterValues.length; i++) {
            if (parameterValues[i] != null) {
                remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
            }
        }

        Class<?> remoteModelClass = _noteRemoteModel.getClass();

        ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

        Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].isPrimitive()) {
                remoteParameterTypes[i] = parameterTypes[i];
            } else {
                String parameterTypeName = parameterTypes[i].getName();

                remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
            }
        }

        Method method = remoteModelClass.getMethod(methodName,
                remoteParameterTypes);

        Object returnValue = method.invoke(_noteRemoteModel,
                remoteParameterValues);

        if (returnValue != null) {
            returnValue = ClpSerializer.translateOutput(returnValue);
        }

        return returnValue;
    }

    @Override
    public Note toEscapedModel() {
        return (Note) ProxyUtil.newProxyInstance(Note.class.getClassLoader(),
            new Class[] { Note.class }, new AutoEscapeBeanHandler(this));
    }

    @Override
    public Object clone() {
        NoteClp clone = new NoteClp();

        clone.setName(getName());
        clone.setNote(getNote());
        clone.setNoteId(getNoteId());

        return clone;
    }

    @Override
    public int compareTo(Note note) {
        long primaryKey = note.getPrimaryKey();

        if (getPrimaryKey() < primaryKey) {
            return -1;
        } else if (getPrimaryKey() > primaryKey) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof NoteClp)) {
            return false;
        }

        NoteClp note = (NoteClp) obj;

        long primaryKey = note.getPrimaryKey();

        if (getPrimaryKey() == primaryKey) {
            return true;
        } else {
            return false;
        }
    }

    public Class<?> getClpSerializerClass() {
        return _clpSerializerClass;
    }

    @Override
    public int hashCode() {
        return (int) getPrimaryKey();
    }

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{name=");
        sb.append(getName());
        sb.append(", note=");
        sb.append(getNote());
        sb.append(", noteId=");
        sb.append(getNoteId());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public String toXmlString() {
        StringBundler sb = new StringBundler(13);

        sb.append("<model><model-name>");
        sb.append("com.liferay.devhacks.model.Note");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>name</column-name><column-value><![CDATA[");
        sb.append(getName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>note</column-name><column-value><![CDATA[");
        sb.append(getNote());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>noteId</column-name><column-value><![CDATA[");
        sb.append(getNoteId());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
