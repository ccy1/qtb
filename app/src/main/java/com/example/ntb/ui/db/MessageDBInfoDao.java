package com.example.ntb.ui.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.example.ntb.ui.city.bean.MessageDBInfo;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "MESSAGE_DBINFO".
*/
public class MessageDBInfoDao extends AbstractDao<MessageDBInfo, Long> {

    public static final String TABLENAME = "MESSAGE_DBINFO";

    /**
     * Properties of entity MessageDBInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property MsgId = new Property(1, String.class, "msgId", false, "MSG_ID");
    }


    public MessageDBInfoDao(DaoConfig config) {
        super(config);
    }
    
    public MessageDBInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MESSAGE_DBINFO\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"MSG_ID\" TEXT);"); // 1: msgId
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MESSAGE_DBINFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MessageDBInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String msgId = entity.getMsgId();
        if (msgId != null) {
            stmt.bindString(2, msgId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MessageDBInfo entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String msgId = entity.getMsgId();
        if (msgId != null) {
            stmt.bindString(2, msgId);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MessageDBInfo readEntity(Cursor cursor, int offset) {
        MessageDBInfo entity = new MessageDBInfo( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // msgId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MessageDBInfo entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setMsgId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MessageDBInfo entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MessageDBInfo entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MessageDBInfo entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}