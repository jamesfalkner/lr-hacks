package com.liferay.devhacks.model.impl;

import com.liferay.devhacks.model.Note;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Note in entity cache.
 *
 * @author James Falkner
 * @see Note
 * @generated
 */
public class NoteCacheModel implements CacheModel<Note>, Externalizable {
    public String name;
    public String note;
    public long noteId;

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(7);

        sb.append("{name=");
        sb.append(name);
        sb.append(", note=");
        sb.append(note);
        sb.append(", noteId=");
        sb.append(noteId);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public Note toEntityModel() {
        NoteImpl noteImpl = new NoteImpl();

        if (name == null) {
            noteImpl.setName(StringPool.BLANK);
        } else {
            noteImpl.setName(name);
        }

        if (note == null) {
            noteImpl.setNote(StringPool.BLANK);
        } else {
            noteImpl.setNote(note);
        }

        noteImpl.setNoteId(noteId);

        noteImpl.resetOriginalValues();

        return noteImpl;
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        name = objectInput.readUTF();
        note = objectInput.readUTF();
        noteId = objectInput.readLong();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput)
        throws IOException {
        if (name == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(name);
        }

        if (note == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(note);
        }

        objectOutput.writeLong(noteId);
    }
}
